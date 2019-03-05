package com.davesone.vis.triggers;

import com.davesone.vis.audio.AudioProcessorHandler;
import com.davesone.vis.audio.AudioStreamHandler;
import com.davesone.vis.core.Debug;

import be.tarsos.dsp.AudioProcessor;
import be.tarsos.dsp.onsets.OnsetHandler;
import be.tarsos.dsp.onsets.PercussionOnsetDetector;

public class PercussionDetect extends TriggerHandler implements OnsetHandler {
	
//	private String[] settingnames = new String[] {"sensitivity", "threshold"};
//	private double[] defsettingvals = new double[] {50, 10};
	private double sensitivity;
	private double threshold;
	
	public PercussionDetect() {
		super();
//		settings = new TriggerSetting(settingnames, defsettingvals);
		attributes.set("sensitivity", 50);
		attributes.set("threshold", 10);
		
		getAttributesPanel().addLabel("lblSens", "Sensitivity");
		getAttributesPanel().addHorizontalSlider("sldrSens", "sensitivity", 0, 100, 50, attributes);
		getAttributesPanel().newComponentRow();
		getAttributesPanel().addLabel("lblThresh", "Threshold");
		getAttributesPanel().addHorizontalSlider("sldrThresh", "threshold", 0, 100, 10, attributes);
	}
	
	public void init(AudioStreamHandler h) throws TriggerException{
		try {
			super.init(h);
			addAudioProcessor(newPercussionProcessor(sensitivity, threshold));
		}catch (Exception e){
			throw new TriggerException("Failed to setup trigger", e);
		}
	}
	
	public void onSettingsUpdate(){
		double s = (double) (int)attributes.get("sensitivity");
		double t = adjustForRange((double) (int)attributes.get("threshold"), 0, 20);
		sensitivity = s;
		threshold = t;
		refreshAudioProcessor(newPercussionProcessor(sensitivity, threshold));
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
		return new PercussionOnsetDetector(sampleRate, (int) bufferSize, this, s, t);
	}
	
	public void printSettingValues() {
		System.out.printf("Sensitivity: %f\tThreshold: %f", sensitivity, threshold);
	}

	@Override
	public void handleOnset(double time, double salience) {
		onTrigger();
	}
}
