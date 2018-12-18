package com.davesone.vis.ui;

import javax.swing.JFrame;

import marvin.gui.MarvinImagePanel;

/***
 * Main video output frame, where the magic (might) happen
 * @author Owner
 *
 */
public class VideoOutputFrame extends JFrame{
	
	private static final long serialVersionUID = -7796086113712146782L;
	
	private MarvinImagePanel videoPanel;

	public VideoOutputFrame() {
		videoPanel = new MarvinImagePanel();
	}
}
