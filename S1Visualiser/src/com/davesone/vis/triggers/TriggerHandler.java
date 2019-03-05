package com.davesone.vis.triggers;

import com.davesone.vis.audio.AudioProcessorHandler;
import com.davesone.vis.audio.AudioStreamHandler;

import be.tarsos.dsp.AudioProcessor;
import marvin.gui.MarvinAttributesPanel;
import marvin.util.MarvinAttributes;

/**
 * Combining Triggers with their {@link AudioProcessorHandler}
 * @author Owner
 *
 */
public class TriggerHandler extends AudioProcessorHandler implements Trigger{
	
	private Thread listeningThread;
	private MarvinAttributesPanel attributesPanel; 
	public transient MarvinAttributes attributes;
	private boolean hasListeningThread = false;
	
	public TriggerHandler(){
		super();
		attributes = new MarvinAttributes();
	}
	
	public void init(AudioStreamHandler h) throws TriggerException {
		super.init(h);
	}
	
	public void onSettingsUpdate() {
		
	}
	
	public Thread getListeningThread() {
		return listeningThread;
	}
	
	public void setListeningThread(Thread t) {
		hasListeningThread = true;
		listeningThread = t;
	}
	
	public boolean hasListeningThread() {
		return hasListeningThread;
	}
	
	public MarvinAttributesPanel getAttributesPanel() {
		if(attributesPanel == null) {
			attributesPanel = new MarvinAttributesPanel();
			return attributesPanel;
		}
		return attributesPanel;
	}
	
	public void setAttributesPanel(MarvinAttributesPanel p) {
		attributesPanel = p;
	}

	@Override
	public void printSettingValues() {
		System.out.println("No settings found");
	}

	@Override
	public void onTrigger() {
		synchronized (getListeningThread()) {
			getListeningThread().notify();
		}
	}
	
	/**
	 * Compensate for sliders only measuring 0 - 100
	 * @param min
	 * @param max
	 * @return
	 */
	public double adjustForRange(double input, int min, int max) {
		int range = max - min;
		int mod = 100/range;
		
		return input / mod;
	}
}

