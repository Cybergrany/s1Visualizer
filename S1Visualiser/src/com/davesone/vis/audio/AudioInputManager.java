package com.davesone.vis.audio;

import com.davesone.vis.triggers.Trigger;
import com.davesone.vis.triggers.TriggerException;
import com.davesone.vis.triggers.TriggerThread;

import be.tarsos.dsp.AudioProcessor;

/**
 * This manager will likely be running for all parts of the program,
 * to allow selection of mixers and display of levels
 * @author Owner
 *
 */
public class AudioInputManager {
	
	/**
	 * Check if streams have started
	 */
	public boolean initialized = false;
	
	private AudioProcessorHandler apHandler;
	private AudioStreamHandler asHandler;
	private TriggerThread listeningThread;
	
	public AudioInputManager() {
		asHandler = new AudioStreamHandler();
		apHandler = new AudioProcessorHandler();
	}
	
	public void initStreams() throws TriggerException {
		apHandler.init(asHandler);
		initialized = true;
	}
	
	public void setAudioTrigger(Trigger t) {
		apHandler = t.getHandler();
	}
	
	public AudioProcessorHandler getAudioProcessorHandler() {
		return apHandler;
	}
	
	public AudioStreamHandler getAudioStreamHandler() {
		return asHandler;
	}
	
	public void setProcessor(Trigger t) {
		try {
			t.getHandler().init(asHandler);
		} catch (TriggerException e) {
			e.printStackTrace();
		}
		apHandler.refreshAudioProcessor(t.getHandler());
	}
	
	public TriggerThread getListeningTriggerThread() throws TriggerException{
		if(listeningThread == null) {
			throw new TriggerException("Thread not initialized");
		}
		return listeningThread;
	}
	
	public void setListeningTriggerThread(TriggerThread t) throws TriggerException{
		listeningThread = t;
		setProcessor(t.getTrigger());
		listeningThread.getThread().start();
	}
	
}
