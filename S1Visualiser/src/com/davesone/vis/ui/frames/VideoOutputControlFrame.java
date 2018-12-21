package com.davesone.vis.ui.frames;

import javax.swing.JFrame;

/**
 * Will control events on outputframe
 * @author Owner
 *
 */
public class VideoOutputControlFrame extends JFrame{

	private static final long serialVersionUID = 8877280716654821090L;
	
	private VideoOutputFrame outputFrame;
	
	public VideoOutputControlFrame(VideoOutputFrame vof) {
		outputFrame = vof;
	}

}
