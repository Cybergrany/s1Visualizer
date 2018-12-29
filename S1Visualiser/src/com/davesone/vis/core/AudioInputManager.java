package com.davesone.vis.core;

import com.davesone.vis.audio.AudioProcessorHandler;
import com.davesone.vis.audio.AudioStreamHandler;
import com.davesone.vis.triggers.Trigger;

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
	
	public AudioInputManager() {
		
	}
	
	public void initStreams() {
		asHandler = new AudioStreamHandler();
		apHandler = new AudioProcessorHandler(asHandler);
		initialized = true;
	}
	
	public void setAudioTrigger(Trigger t) {
		apHandler = t.getHandler();
	}
	
	public AudioProcessorHandler getProcHandler() {
		return apHandler;
	}
	
	/**
	 * For passing into mixer select tool
	 * @return
	 */
	public AudioStreamHandler getStreamHandler() {
		return asHandler;
	}
}
