package com.davesone.vis.triggers;

import com.davesone.vis.audio.AudioProcessorHandler;
import com.davesone.vis.audio.AudioStreamHandler;
import com.davesone.vis.core.Debug;

import be.tarsos.dsp.AudioProcessor;
import be.tarsos.dsp.onsets.ComplexOnsetDetector;
import be.tarsos.dsp.onsets.OnsetHandler;

public class OnsetDetect extends AudioProcessorHandler implements OnsetHandler{
	
	private AudioProcessor processor;
	
	private String[] settingnames = new String[] {"peakThreshold", "minimumInterOnsetInterval", "silenceThreshold"};
	private double[] defsettingvals = new double[] { .4, .07, -60};
	private double peakThreshold, minimumInterOnsetInterval, silenceThreshold;
	
	public OnsetDetect(AudioStreamHandler h) {
		super(h);
		settings = new TriggerSetting(settingnames, defsettingvals);
	}
	
	public void start() throws TriggerException{
		try {
			onSettingUpdate();
			addAudioProcessor(newComplexOnsetDetector(peakThreshold, silenceThreshold));
		}catch (Exception e) {
			throw new TriggerException("Failed to setup trigger", e);
		}
	}
	
	public void onSettingUpdate() throws TriggerException{
		try {
			
		}catch(Exception e) {
			peakThreshold = settings.getSettingValue(0);
			minimumInterOnsetInterval = settings.getSettingValue(1);
			silenceThreshold = settings.getSettingValue(2);
			refreshAudioProcessor(processor, newComplexOnsetDetector(peakThreshold, silenceThreshold));
		}
	}
	
	
	//Replaced by settings
//	public double getPeakThreshold() {
//		return peakThreshold;
//	}
//	
//	public void setPeakThreshold(double d) {
//		peakThreshold = d;
//		refreshAudioProcessor(processor, newComplexOnsetDetector(peakThreshold, silenceThreshold));
//	}
//	
//	public double getSilenceThreshold() {
//		return silenceThreshold;
//	}
//	
//	public void setSilenceThreshold(double d) {
//		silenceThreshold = d;
//		refreshAudioProcessor(processor, newComplexOnsetDetector(peakThreshold, silenceThreshold));
//	}

	@Override
	public void handleOnset(double time, double salience) {
//		Debug.printMessage("Detected Perc");
		triggered = true;
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
