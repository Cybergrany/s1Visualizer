package com.davesone.vis.ui;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

import javax.swing.JFrame;

import com.davesone.vis.core.ShowManager;
import com.davesone.vis.core.VideoManager;

/***
 * Main video output frame, where the magic (might) happen
 * @author Owner
 *
 */
public class VideoOutputFrame extends JFrame{
	
	private static final long serialVersionUID = -7796086113712146782L;
	
	private VideoManager manager;
	private GraphicsDevice device;
	
	private boolean fullscreen = false;

	/**
	 * Remember to call manager.startThread(); to start the rendering
	 * @param w
	 * @param h
	 */
	public VideoOutputFrame(ShowManager m, int w, int h) {
		super("S1 Visualiser");
		setSize(w, h);
		
		manager = new VideoManager(m);
		manager.initThread(w, h);
		
		device = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()[0];
		
//		setLayout(new BorderLayout());
		add(manager.getCanvas());
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	/**
	 * TODO don't think this needs to be exposed
	 * @return
	 */
	public VideoManager getManager() {
		return manager;
	}
	
	public void toggleFullscreen(GraphicsDevice d) {
		device = d;
		if(fullscreen) {
			device.setFullScreenWindow(null);
			setUndecorated(false);
		}else {
			device.setFullScreenWindow(this);
			setUndecorated(true);
		}
	}
	
	public void toggleFullscreen() {
		if(fullscreen) {
			device.setFullScreenWindow(null);
			setUndecorated(false);
		}else {
			device.setFullScreenWindow(this);
			setUndecorated(true);
		}
	}
	
}
