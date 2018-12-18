package com.davesone.vis.audio;

import com.davesone.vis.core.Debug;

import be.tarsos.dsp.AudioProcessor;
import be.tarsos.dsp.onsets.OnsetHandler;
import be.tarsos.dsp.onsets.PercussionOnsetDetector;

public class PercussionDetect extends AudioProcessorHandler implements OnsetHandler {
	
	private AudioProcessor processor;
	
	private double sensitivity;
	private double threshold;
	
	public PercussionDetect(AudioStreamHandler h, double sensitivty, double threshold) {
		super(h);
		this.sensitivity = sensitivty;
		this.threshold = threshold;
		addAudioProcessor(newPercussionProcessor(sensitivty, threshold));
	}
	public double getSensitivity() {
		return sensitivity;
	}
	
	//TODO as above
	public void setSensitivity(double s) {
		sensitivity = s;
		refreshAudioProcessor(processor, newPercussionProcessor(sensitivity, threshold));
	}
	
	public double getThreshold() {
		return threshold;
	}
	
	public void setThreshold(double t) {
		threshold = t;
		refreshAudioProcessor(processor, newPercussionProcessor(sensitivity, threshold));
	}
	
	/**
	 * Just to avoid having this written out too much
	 */
	private AudioProcessor newPercussionProcessor(double s, double t) {
		processor = new PercussionOnsetDetector(sampleRate, (int) bufferSize, this, s, t);
		return processor;
	}

	@Override
	public void handleOnset(double time, double salience) {
		Debug.printMessage("Percussion Detected");
	}
}
