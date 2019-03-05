package com.davesone.vis.core;

import com.davesone.vis.video.VideoThread;
import com.davesone.vis.video.MarvinPanelCanvas;

public class VideoManager{
	
	private VideoThread videoThread;
	
	public void initThread(int frameWidth, int frameHeight) {
		videoThread = new VideoThread(frameWidth, frameHeight);
	}
	
	public void startThread() {
		videoThread.running = true;
		if(!(videoThread.getThread().isAlive()))
			videoThread.getThread().start();
	}
	
	public void stopThread() {
		videoThread.running = false;
	}
	
	/**
	 * Use this getter to perform actions on canvas objects
	 * @return
	 */
	public MarvinPanelCanvas getCanvas() {
		return videoThread.mainCanvas;
	}
	
}
