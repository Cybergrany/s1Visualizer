package com.davesone.vis.graphics;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;

import com.davesone.vis.audio.AudioProcessorHandler;
import com.davesone.vis.audio.AudioStreamHandler;
import com.davesone.vis.core.Debug;
import com.davesone.vis.triggers.TriggerException;

import be.tarsos.dsp.AudioEvent;
import be.tarsos.dsp.AudioProcessor;
import be.tarsos.dsp.Oscilloscope;
import be.tarsos.dsp.Oscilloscope.OscilloscopeEventHandler;

public class OscilloscopeRenderer extends AudioProcessorHandler implements OscilloscopeEventHandler{
	
	private float data[];
	private Component parentDisplay;
	
	public OscilloscopeRenderer() {
		
	}
	
	public void init(AudioStreamHandler h, Component c) throws TriggerException{
		parentDisplay = c;
		try {
			super.init(h);
			addAudioProcessor(new Oscilloscope(this));
		}catch(Exception e) {
			throw new TriggerException("Failed to setup Oscilloscope", e);
		}
	}
	
	public void refreshProcessor() {
		refreshAudioProcessor(new Oscilloscope(this));
	}
	
	public void render(Graphics g, Component c) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, c.getWidth(), c.getHeight());
		g.setColor(Color.white);
		if(data != null) {
			float w = c.getWidth();
			float h = c.getHeight();
			float halfH = h / 2;
			for(int i = 0; i < data.length; i += 4) {
				g.drawLine((int)(data[i] * w), (int)(halfH - data[i+1] * h), (int)(data[i+2] * w), (int)(halfH - data[i+3]*h));
			}
		}
	}

	@Override
	public void handleEvent(float[] data, AudioEvent event) {
		this.data = data;
		parentDisplay.repaint();
	}
}
