package com.davesone.vis.core;

import com.davesone.vis.video.MarvinPanelCanvas;
import com.davesone.vis.video.VideoThread;

public class VideoManager{
	
	private VideoThread videoThread;
	private ShowManager manager;
	
	public VideoManager(ShowManager m) {
		manager = m;
	}
	
	public void initThread(int frameWidth, int frameHeight) {
		videoThread = new VideoThread(this, frameWidth, frameHeight);
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
	
	public ShowManager getShowManager() {
		return manager;
	}
	
}
