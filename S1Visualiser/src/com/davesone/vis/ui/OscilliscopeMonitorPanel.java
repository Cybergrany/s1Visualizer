package com.davesone.vis.ui;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

import com.davesone.vis.audio.AudioStreamHandler;
import com.davesone.vis.graphics.OscilloscopeRenderer;
import com.davesone.vis.triggers.TriggerException;

public class OscilliscopeMonitorPanel extends JPanel {

	private OscilloscopeRenderer oscilliscope;
	private boolean initialized = false;
	
	
	public OscilliscopeMonitorPanel() {
		setMinimumSize(new Dimension(80, 60));
		oscilliscope = new OscilloscopeRenderer();
	}
	
	public void init(AudioStreamHandler h) throws TriggerException {
		oscilliscope.init(h, this);
		initialized = true;
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		oscilliscope.render(g, this);	
	}
	
	public boolean isInitialized() {
		return initialized;
	}
	
	public void refreshOscilloscope() {
		oscilliscope.refreshProcessor();
//		validate();
//		repaint();
	}

}
