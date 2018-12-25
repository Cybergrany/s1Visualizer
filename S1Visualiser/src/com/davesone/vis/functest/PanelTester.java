package com.davesone.vis.functest;

import java.awt.BorderLayout;

import javax.swing.JFrame;

import com.davesone.vis.core.Main;
import com.davesone.vis.ui.panels.PluginLoaderPanel;
import com.davesone.vis.ui.panels.TimelinePanel;
import com.davesone.vis.video.plugins.PluginLoader;

public class PanelTester extends JFrame{
	
	public PanelTester() {
		super("TEst");
		setSize(1000,700);
		
//		PluginLoader pl = new PluginLoader();
		
//		PluginLoaderPanel p = new PluginLoaderPanel(100, 20, pl);
		
		TimelinePanel p = new TimelinePanel(getWidth()/2, getHeight());
		setLayout(new BorderLayout());
		add(p);
		
		setVisible(true);
	}
	
	public static void main(String[]args) {
		PanelTester p = new PanelTester();
		p.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

}
