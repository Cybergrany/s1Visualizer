package com.davesone.vis.audio;

import be.tarsos.dsp.AudioDispatcher;
import be.tarsos.dsp.AudioEvent;
import be.tarsos.dsp.AudioProcessor;

public class AudioProcessorHandler implements AudioProcessor{
	
	private AudioDispatcher dispatcher;
	
	public float sampleRate, bufferSize;
	
	public AudioProcessorHandler(AudioStreamHandler h) {
		dispatcher = h.getDispatcher();
		sampleRate = h.sampleRate;
		bufferSize = h.bufferSize;
	}
	
	public void addAudioProcessor(AudioProcessor a) {
		dispatcher.addAudioProcessor(a);
	}
	
	public void refreshAudioProcessor(AudioProcessor oldProcessor, AudioProcessor newProcessor) {
		try {
			dispatcher.removeAudioProcessor(oldProcessor);
		}catch (NullPointerException e){}
		dispatcher.addAudioProcessor(newProcessor);
	}
	
	public AudioDispatcher getDispatcher() {
		return dispatcher;
	}

	@Override
	public boolean process(AudioEvent audioEvent) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void processingFinished() {
		// TODO Auto-generated method stub
		
	}
	
}
