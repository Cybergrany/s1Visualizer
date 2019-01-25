package com.davesone.vis.audio;

import com.davesone.vis.triggers.Trigger;
import com.davesone.vis.triggers.TriggerException;

import be.tarsos.dsp.AudioDispatcher;
import be.tarsos.dsp.AudioEvent;
import be.tarsos.dsp.AudioProcessor;
import marvin.gui.MarvinAttributesPanel;
import marvin.util.MarvinAttributes;

/**
 * Implements trigger as any oject extending this will make use of methods
 * @author Owner
 *
 */
public class AudioProcessorHandler implements AudioProcessor, Trigger{//TODO the implementation of trigger is a bit messy
	
	private AudioDispatcher dispatcher;
	
	public float sampleRate, bufferSize;
	public boolean triggered = false;
//	public TriggerSetting settings;
	public MarvinAttributes attributes;
	private AudioProcessor processor;
	private MarvinAttributesPanel attributesPanel; 
	
	public AudioProcessorHandler() {
		attributes = new MarvinAttributes();
	}
	
	public void init(AudioStreamHandler h) throws TriggerException{
		dispatcher = h.getDispatcher();
		sampleRate = h.sampleRate;
		bufferSize = h.bufferSize;
	}
	
	public void addAudioProcessor(AudioProcessor a) {
		dispatcher.addAudioProcessor(a);
	}
	
	public void removeAudioProcessor(AudioProcessor a) {
		dispatcher.removeAudioProcessor(a);
	}
	
	public void refreshAudioProcessor(AudioProcessor newProcessor) {
		try {
			dispatcher.removeAudioProcessor(processor);
		}catch(NullPointerException e) {}
		processor = newProcessor;
		dispatcher.addAudioProcessor(processor);
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
	
	@Override
	public boolean isTriggered() {
		return triggered;
	}
	
	@Override
	public void resetTrigger() {
		triggered = false;
	}
	
	public AudioProcessorHandler getHandler() {
		return this;
	}
	
//	public void changeSetting(int index, double value) throws TriggerException{
//		try {
//			settings.setSettingValue(index, value);
//			onSettingUpdate();
//		}catch(Exception e) {
//			throw new TriggerException("Failed to set setting", e);
//		}
//	}
//
//	@Override
//	public TriggerSetting getSettings() {
//		return settings;
//	}

//	@Override
//	public void onSettingUpdate() throws TriggerException {
//		throw new TriggerException("Settings not initialized");
//	}

	@Override
	public void start() throws TriggerException {
		throw new TriggerException("Settings not initialized");
	}
	
	public MarvinAttributesPanel getAttributesPanel() {
		if(attributesPanel == null) {
			attributesPanel = new MarvinAttributesPanel();
			return attributesPanel;
		}
		return attributesPanel;
	}
	
}
