package com.davesone.vis.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.TitledBorder;

import com.davesone.vis.core.Main;
import com.davesone.vis.video.plugins.PluginContainer;

public class PluginLoaderPanel extends JPanel{
	
	private ArrayList<PluginContainer> plugins;
	
	
	public PluginLoaderPanel(int width, int height) {
		super(new BorderLayout());
		JPanel buttonPanel = new JPanel(new GridLayout(0,1));
		ButtonGroup group = new ButtonGroup();
		
		plugins = Main.getInstance().getPlugins();
		
		this.setBorder(new TitledBorder("Choose a plugin to apply: "));
		
		for(PluginContainer p : plugins) {
			JButton b = new JButton(p.getName());
			buttonPanel.add(b);
			group.add(b);
			b.setActionCommand(p.getName());
			b.addActionListener(setInput);
		}
		
		this.add(new JScrollPane(buttonPanel,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER),BorderLayout.CENTER);
		this.setMaximumSize(new Dimension(width, height));
		this.setPreferredSize(new Dimension(width, height));
		
	}
	
	private ActionListener setInput = new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			
			
		}
	};
}