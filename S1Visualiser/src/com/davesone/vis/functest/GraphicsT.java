package com.davesone.vis.functest;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JFrame;

import com.davesone.vis.video.MarvinPanelCanvas;
import com.davesone.vis.video.elements.VideoFramelet;

import marvin.video.MarvinVideoInterfaceException;

public class GraphicsT extends JFrame implements Runnable{
	
	private MarvinPanelCanvas panel;
	private VideoFramelet vf, vf2, vf3;
	
	public GraphicsT() {
		super("test");
		setSize(1000, 800);
		
		panel = new MarvinPanelCanvas(getWidth(), getHeight());
		panel.initFrameletThread();
		
		try {
			vf = new VideoFramelet("./res/Realness.mp4");
			vf2 = new VideoFramelet("./res/Realness.mp4");

			vf3 = new VideoFramelet("./res/deeps.mp4");
		} catch (MarvinVideoInterfaceException e) {
			e.printStackTrace();
		}
		
		vf.setPosition(0, 0);
		vf2.setPosition(300, 300);
		vf3.setPosition(0, 0);
//		vf.resize(500);
//		vf2.resize(120);
//		vf2.resize(50);
		
		panel.addFramelet(vf);
		panel.addFramelet(vf2);
		panel.addFramelet(vf3);
		
		setLayout(new BorderLayout());
		add(panel, BorderLayout.CENTER);
		
		new Thread(this).start();
		setVisible(true);
		
	}
	
	public static void main(String[] args) {
		GraphicsT g = new GraphicsT();
		g.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	@Override
	public void run() {
		while(true) {
			panel.render();
		}
		
	}
	
}
