package com.davesone.vis.triggers;

import com.davesone.vis.audio.AudioProcessorHandler;
import com.davesone.vis.audio.AudioStreamHandler;
import com.davesone.vis.core.Debug;

import be.tarsos.dsp.AudioProcessor;
import be.tarsos.dsp.onsets.OnsetHandler;
import be.tarsos.dsp.onsets.PercussionOnsetDetector;

public class PercussionDetect extends AudioProcessorHandler implements OnsetHandler {
	
	private AudioProcessor processor;
	private String[] settingnames = new String[] {"sensitivity", "threshold"};
	private double[] defsettingvals = new double[] {50, 10};
	private double sensitivity;
	private double threshold;
	
	public PercussionDetect(AudioStreamHandler h) {
		super(h);
		settings = new TriggerSetting(settingnames, defsettingvals);
	}
	
	public void start() throws TriggerException{
		try {
			onSettingUpdate();
			addAudioProcessor(newPercussionProcessor(sensitivity, threshold));
		}catch (Exception e){
			throw new TriggerException("Failed to setup trigger", e);
		}
	}
	
	public void onSettingUpdate() throws TriggerException{
		try {
			sensitivity = settings.getSettingValue(0);
			threshold = settings.getSettingValue(1);
			refreshAudioProcessor(processor, newPercussionProcessor(sensitivity, threshold));
		}catch(Exception e) {
			throw new TriggerException("Failed to update trigger", e);
		}
	}
	
	//REPLACED BY SETTING CLASS
//	public double getSensitivity() {
//		return sensitivity;
//	}
//	
//	//TODO as above
//	public void setSensitivity(double s) {
//		sensitivity = s;
//		refreshAudioProcessor(processor, newPercussionProcessor(sensitivity, threshold));
//	}
//	
//	public double getThreshold() {
//		return threshold;
//	}
//	
//	public void setThreshold(double t) {
//		threshold = t;
//		refreshAudioProcessor(processor, newPercussionProcessor(sensitivity, threshold));
//	}
	
	/**
	 * Just to avoid having this written out too much
	 */
	private AudioProcessor newPercussionProcessor(double s, double t) {
		processor = new PercussionOnsetDetector(sampleRate, (int) bufferSize, this, s, t);
		return processor;
	}

	@Override
	public void handleOnset(double time, double salience) {
		triggered = true;
	}
}
