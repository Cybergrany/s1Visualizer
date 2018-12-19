package com.davesone.vis.video;

public class FrameBasedVideoThread implements Runnable{
	
	private MarvinPanelCanvas mainCanvas;
	
	public FrameBasedVideoThread(int frameWidth, int frameHeight) {
		
		mainCanvas = new MarvinPanelCanvas(frameWidth, frameHeight);
		
		new Thread(this).start();
		
	}

	@Override
	public void run() {
		while(true) {
			mainCanvas.render();
		}
	}
	
	/**
	 * Use to add/get framelets etc
	 * @return
	 */
	public MarvinPanelCanvas getCurrentCanvas() {
		return mainCanvas;
	}
}
