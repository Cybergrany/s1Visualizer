package com.davesone.vis.triggers;

import com.davesone.vis.audio.AudioProcessorHandler;
import com.davesone.vis.audio.AudioStreamHandler;
import com.davesone.vis.core.Debug;

import be.tarsos.dsp.AudioProcessor;
import be.tarsos.dsp.onsets.ComplexOnsetDetector;
import be.tarsos.dsp.onsets.OnsetHandler;
import marvin.gui.MarvinAttributesPanel;

public class OnsetDetect extends TriggerHandler implements OnsetHandler{
	
	
//	private String[] settingnames = new String[] {"peakThreshold", "minimumInterOnsetInterval", "silenceThreshold"};
//	private double[] defsettingvals = new double[] { .4, .07, -60};
	
	private double peakThreshold, minimumInterOnsetInterval, silenceThreshold;
	
	public OnsetDetect() {
		super();
//		settings = new TriggerSetting(settingnames, defsettingvals);
		attributes.set("peakThreshold", 40);//div by 100 for value
		attributes.set("minimumInterOnsetInterval", 7);//div by 100 for value0
		attributes.set("silenceThreshold", -60);//negate for value
		
		getAttributesPanel().addLabel("lblPeakThresh", "Peak Threshold");
		getAttributesPanel().addHorizontalSlider("sldrPeakThresh", "peakThreshold", 0, 100, 40, attributes);
		getAttributesPanel().newComponentRow();
		getAttributesPanel().addLabel("lblMinOnset", "Minimum Onset Interval");
		getAttributesPanel().addHorizontalSlider("sldrminOnset", "minimumInterOnsetInterval", 0, 10, 7, attributes);
		getAttributesPanel().newComponentRow();
		getAttributesPanel().addLabel("lblSilenceThresh", "Silence Threshold");
		getAttributesPanel().addHorizontalSlider("sldrSilenceThresh", "silenceThreshold", 0, 100, 60, attributes);
	}
	
	public void init(AudioStreamHandler h) throws TriggerException{
		try {
			super.init(h);
			addAudioProcessor(newComplexOnsetDetector(peakThreshold, silenceThreshold));
		}catch (Exception e) {
			throw new TriggerException("Failed to setup trigger", e);
		}
	}
	
	public void onSettingsUpdate(){
		peakThreshold = adjustForRange((double) (int)attributes.get("peakThreshold"), 10, 80)/ 100;
		minimumInterOnsetInterval = (double) (int)attributes.get("minimumInterOnsetInterval") / 100;
		silenceThreshold = -((double) (int) attributes.get("silenceThreshold"));
		refreshAudioProcessor(newComplexOnsetDetector(peakThreshold, silenceThreshold));
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
		onTrigger();
	}
	
	public AudioProcessor newComplexOnsetDetector(double pT, double sT) {
		return new ComplexOnsetDetector((int) bufferSize, pT, minimumInterOnsetInterval, sT);
	}
	
	@Override
	public void printSettingValues() {
		System.out.printf("Peak Threshold: %f \t"
				+ "Minimum Interval: %f \t"
				+ "Silence threshold: %f \t",peakThreshold, minimumInterOnsetInterval, silenceThreshold);
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
