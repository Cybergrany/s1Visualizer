package com.davesone.vis.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.TitledBorder;

import com.davesone.vis.core.Main;
import com.davesone.vis.video.PluginCompatible;
import com.davesone.vis.video.plugins.PluginContainer;
import com.davesone.vis.video.plugins.PluginLoader;

public class PluginLoaderPanel extends JPanel{
	
	private PluginLoader pLoader;
	private PluginCompatible target;
	
	
	public PluginLoaderPanel(int width, int height, PluginLoader loader, PluginCompatible obj) {
		super(new BorderLayout());
		
		target = obj;
		pLoader = loader;
		
		JPanel buttonPanel = new JPanel(new GridLayout(0, 1));
		ButtonGroup group = new ButtonGroup();
		this.setBorder(new TitledBorder("Choose a plugin to apply: "));
		
		for(PluginContainer p : pLoader.plugins) {
			JButton b = new JButton(p.getName());
			b.setBackground(Color.BLUE);
			b.setSize(60, 20);
			buttonPanel.add(b);
			group.add(b);
			b.setActionCommand(p.getName());
			b.addActionListener(setInput);
		}
		
		this.add(new JScrollPane(buttonPanel,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER),BorderLayout.CENTER);
		
	}
}