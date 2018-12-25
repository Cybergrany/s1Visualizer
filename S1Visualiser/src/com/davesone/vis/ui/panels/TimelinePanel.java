package com.davesone.vis.ui.panels;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.TextField;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.davesone.vis.ui.TimelineFrame;

public class TimelinePanel extends JPanel{
	
	private ArrayList<TimelineFrame> objects;
	
	/**
	 * For portability, use this objects setSize methods
	 * to scale.
	 */
	public TimelinePanel() {
		super();
		
		setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		objects = new ArrayList<>();//init
	}
	
	public void addTimelineObject(TimelineFrame f) {
		objects.add(f);
	}
	
	public void removeTimelineObject(TimelineFrame f) {
		objects.remove(f);
	}
	
	public ArrayList<TimelineFrame> getObjects(){
		return objects;
	}
	
//	 @Override
//	public void paintComponent(Graphics g) {
//		
//	}

}
