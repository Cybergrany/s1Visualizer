package com.davesone.vis.audio;

import com.davesone.vis.core.Debug;

import be.tarsos.dsp.AudioProcessor;
import be.tarsos.dsp.onsets.ComplexOnsetDetector;
import be.tarsos.dsp.onsets.OnsetHandler;

public class OnsetDetect extends AudioProcessorHandler implements OnsetHandler {
	
	private AudioProcessor processor;
	
	private double peakThreshold, minimumInterOnsetInterval, silenceThreshold;
	
	public OnsetDetect(AudioStreamHandler h, double peakThreshold,double minimumInterOnsetInterval,double silenceThreshold) {
		super(h);
		this.peakThreshold = peakThreshold;
		this.minimumInterOnsetInterval = minimumInterOnsetInterval;
		this.silenceThreshold = silenceThreshold;
		addAudioProcessor(newComplexOnsetDetector(peakThreshold, silenceThreshold));
	}
	
	public double getPeakThreshold() {
		return peakThreshold;
	}
	
	public void setPeakThreshold(double d) {
		peakThreshold = d;
		refreshAudioProcessor(processor, newComplexOnsetDetector(peakThreshold, silenceThreshold));
	}
	
	public double getSilenceThreshold() {
		return silenceThreshold;
	}
	
	public void setSilenceThreshold(double d) {
		silenceThreshold = d;
		refreshAudioProcessor(processor, newComplexOnsetDetector(peakThreshold, silenceThreshold));
	}

	@Override
	public void handleOnset(double time, double salience) {
		Debug.printMessage("Detected Perc");
	}
	
	public AudioProcessor newComplexOnsetDetector(double pT, double sT) {
		processor = new ComplexOnsetDetector((int) bufferSize, pT, minimumInterOnsetInterval, sT);
		return processor;
	}
	
	@Override
	public void addAudioProcessor(AudioProcessor a) {
		try {
			getDispatcher().addAudioProcessor(a);
			((ComplexOnsetDetector) a ).setHandler(this);
		}catch(Exception e) {
			Debug.printError("Failed to add audioprocessor");
		}
	}

}
