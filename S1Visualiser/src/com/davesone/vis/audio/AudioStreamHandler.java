package com.davesone.vis.audio;

import java.util.Vector;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.TargetDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.sound.sampled.Mixer.Info;

import com.davesone.vis.core.Debug;
import com.davesone.vis.core.Values;

import be.tarsos.dsp.AudioDispatcher;
import be.tarsos.dsp.io.jvm.JVMAudioInputStream;

public class AudioStreamHandler {
	
	private AudioDispatcher dispatcher;//will be used to feed processors
	private Mixer mixer;
	
	private Thread dispatchThread;
	
	public float sampleRate = Values.sampleRate;
	public int bufferSize = Values.bufferSize, bufferOverlap = Values.bufferOverlap;
	
	public Vector<Mixer.Info> getMixerInfo(final boolean supportsPlayback, final boolean supportsRecording) {
		final Vector<Mixer.Info> infos = new Vector<Mixer.Info>();
		final Mixer.Info[] mixers = AudioSystem.getMixerInfo();
		
		for (final Info mixerinfo : mixers) {
			if (supportsRecording && AudioSystem.getMixer(mixerinfo).getTargetLineInfo().length != 0) {
				// Mixer capable of recording audio if target LineWavelet length != 0
				infos.add(mixerinfo);
			} else if (supportsPlayback && AudioSystem.getMixer(mixerinfo).getSourceLineInfo().length != 0) {
				// Mixer capable of audio play back if source LineWavelet length != 0
				infos.add(mixerinfo);
			}
		}
		return infos;
	}
	
	//TODO currently stops the current dispatcher, add logic to maybe add more
	//dispatcher.addAudioProcessor(new whatever) to add stuff
	public void setNewMixer(Mixer m) throws LineUnavailableException, UnsupportedAudioFileException{
		
		if(dispatcher!= null){
			dispatcher.stop();
		}
		
		mixer = m;
		
		Debug.printMessage("Started listening to: " + mixer.getMixerInfo().getName());
		
		final AudioFormat format = new AudioFormat(sampleRate, 16, 1, true, true);
		final DataLine.Info dataLineInfo = new DataLine.Info(TargetDataLine.class, format);
		TargetDataLine line;
		line = (TargetDataLine) mixer.getLine(dataLineInfo);
		final int numberOfSamples = bufferSize;
		line.open(format, numberOfSamples);
		line.start();
		final AudioInputStream stream = new AudioInputStream(line);

		JVMAudioInputStream audioStream = new JVMAudioInputStream(stream);
		// create a new dispatcher
		dispatcher = new AudioDispatcher(audioStream, bufferSize, bufferOverlap);
		
		// run the dispatcher (on a new thread).
		dispatchThread = new Thread(dispatcher,"Audio dispatching");
		dispatchThread.start();
	}
	
	public AudioDispatcher getDispatcher() {
		return dispatcher;
	}
	
	//TODO probably not needed
	public void setDispatcher(AudioDispatcher d) {
		dispatcher = d;
	}
		
	public Mixer getMixer() {
		return mixer;
	}
	
	public Thread getDispatchThread() {
		return dispatchThread;
	}
	
	public String getStreamSource() {
		return mixer.getMixerInfo().getName();
	}
}
