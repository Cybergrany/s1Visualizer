package com.davesone.vis.functest;

import java.awt.BorderLayout;

import javax.swing.JFrame;

import com.davesone.vis.video.MarvinPanelCanvas;
import com.davesone.vis.video.VideoFramelet;

import marvin.video.MarvinVideoInterfaceException;

public class GraphicsT extends JFrame implements Runnable{
	
	private MarvinPanelCanvas panel;
	private VideoFramelet vf, vf2;
	
	public GraphicsT() {
		super("test");
		setSize(1000, 800);
		
		panel = new MarvinPanelCanvas(getWidth(), getHeight());
		
		try {
			vf = new VideoFramelet("./res/driving.mp4");
			vf2 = new VideoFramelet("./res/driving.mp4");
		} catch (MarvinVideoInterfaceException e) {
			e.printStackTrace();
		}
		
		vf.setPosition(200, 100);
//		vf2.setPosition(300, 300);
//		vf2.resize(50);
		vf.resize(200);
		
		panel.addFramelet(vf);
//		panel.addFramelet(vf2);
		
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
