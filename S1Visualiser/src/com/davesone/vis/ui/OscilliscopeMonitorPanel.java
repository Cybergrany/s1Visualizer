package com.davesone.vis.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

import com.davesone.vis.audio.AudioStreamHandler;
import com.davesone.vis.graphics.OscilloscopeRenderer;
import com.davesone.vis.triggers.TriggerException;
import com.davesone.vis.triggers.TriggerThread;
import com.davesone.vis.triggers.Triggerable;

public class OscilliscopeMonitorPanel extends JPanel implements Triggerable{

	private static final long serialVersionUID = 387986032364795336L;
	private OscilloscopeRenderer oscilliscope;
	private boolean initialized = false, triggerHit = false;
	
	
	public OscilliscopeMonitorPanel() {
		setMinimumSize(new Dimension(80, 60));
		oscilliscope = new OscilloscopeRenderer();
	}
	
	public void init(AudioStreamHandler h) throws TriggerException {
		oscilliscope.init(h, this);
		initialized = true;
	}
	
	public void setListeningThread(TriggerThread t) {
		oscilliscope.setListeningThread(t.getThread());
	}
	
	public void paintComponent(Graphics g) {
		g.setColor(Color.BLACK);
		if(oscilliscope.hasListeningThread()) {
			synchronized(oscilliscope.getListeningThread()) {
				super.paintComponent(g);
				if(triggerHit) {
					g.setColor(Color.BLUE);
					triggerHit = false;
				}
				oscilliscope.render(g, this);
			}
		}else {
			super.paintComponent(g);
			oscilliscope.render(g, this);
		}
//		try {
//			synchronized(oscilliscope.getListeningThread()) {
//				super.paintComponent(g);
//				oscilliscope.render(g, this);	
//				
//				System.out.println(triggerHit);
//			}
//		}catch(NullPointerException e) {
//			super.paintComponent(g);
//			oscilliscope.render(g, this);
//		}
	}
	
	public boolean isInitialized() {
		return initialized;
	}
	
	public void triggerObject() {
		triggerHit = true;
		repaint();
	}
	
	public void refreshOscilloscope(AudioStreamHandler h) throws TriggerException {
		//TODO recreating the object is a bit of a dirty fix
		oscilliscope = new OscilloscopeRenderer();
		oscilliscope.init(h, this);
//		validate();
//		repaint();
	}

}
