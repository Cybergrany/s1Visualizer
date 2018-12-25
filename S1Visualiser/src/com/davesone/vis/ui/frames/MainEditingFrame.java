package com.davesone.vis.ui.frames;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JScrollPane;

import com.davesone.vis.ui.panels.MainScreenOptionsPanel;
import com.davesone.vis.ui.panels.TimelinePanel;

/**
 * Main frame containing preview and editing elements
 * Show will be launched from here
 * @author Owner
 *
 */
public class MainEditingFrame extends JFrame{
	
	final Dimension fSize = new Dimension(1000, 700);
	
	//Window elements
	private MainScreenOptionsPanel optionsPanel;//The panel displaying options for elements
	private TimelinePanel timelinePanel;
	
	public MainEditingFrame() {
		super("S1 Visualizer");
		setLayout(new FlowLayout());
		setSize(fSize);
		
		setResizable(false);
		
		/**
		 * Borderlayout used for main frame,
		 * option screen changes will be using
		 * embedded cardlayouts (hopefully)
		 */
		
		int y = 50;
//		timelinePanel = new TimelinePanel((int)fSize.getWidth(), y);
		add(timelinePanel);
		setVisible(true);
	}
	
	/**
	 * TESTING GUI
	 * @param args
	 */
	public static void main(String[] args) {
		MainEditingFrame mef = new MainEditingFrame();
		mef.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	/**
	 * Updates the options panel with the options for the
	 * selected element
	 */
	public void UpdateOptionsPanel(MainScreenOptionsPanel p) {
		optionsPanel = p;//TODO the frame will probably have to repaint or update
	}
}
