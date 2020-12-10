package com.davesone.vis.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;

import com.davesone.vis.core.ShowManager;

/**
 * Will control events on outputframe
 * @author Owner
 *
 */
public class VideoOutputControlFrame extends JFrame{

	private static final long serialVersionUID = 8877280716654821090L;
	
	private ShowManager manager;
	
	private JPanel statusbar;
	private JLabel statuslabel;
	
	public VideoOutputControlFrame(ShowManager m) {
		manager = m;
		initElements();
		initLayout();
	}
	
	public void updateStatusBar(String s) {
		statuslabel.setText(s);
	}
	
	private void initElements() {
		statusbar = new JPanel();
	}
	
	private void initLayout() {
		setTitle("S1 Viz Show Control");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, 400, 400);
		setLayout(new BorderLayout());
		
		
		statusbar.setBorder(new BevelBorder(BevelBorder.LOWERED));
		statusbar.setPreferredSize(new Dimension(getWidth(), 16));
		statusbar.setLayout(new BoxLayout(statusbar, BoxLayout.X_AXIS));
		statuslabel = new JLabel("Status bar");
		statuslabel.setHorizontalAlignment(SwingConstants.LEFT);
		statusbar.add(statuslabel);
		
		add(statusbar, BorderLayout.SOUTH);
		
		setVisible(true);
	}

}
