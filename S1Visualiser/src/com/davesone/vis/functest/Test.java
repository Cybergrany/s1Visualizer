package com.davesone.vis.functest;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import com.davesone.vis.audio.AudioProcessorHandler;
import com.davesone.vis.audio.AudioStreamHandler;
import com.davesone.vis.audio.OnsetDetect;
import com.davesone.vis.audio.PercussionDetect;
import com.davesone.vis.core.Debug;
import com.davesone.vis.ui.panels.MixerChooserPanel;

public class Test extends JFrame{
	
	private AudioStreamHandler handler;
	private AudioProcessorHandler ap;
	
	public Test() {
		this.setLayout(new BorderLayout());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Test");
		
		handler = new AudioStreamHandler();
		ap = new AudioProcessorHandler(handler);
		
		JPanel inputPanel = new MixerChooserPanel(handler, 150, 100);
		JButton testButton = new JButton("Percussion detect");
		testButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				ap = new PercussionDetect(handler, 50, 10);
				Debug.printMessage("Perc detect");
			}
		});
		
		JButton testButton2 = new JButton("Onset detect");
		testButton2.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				ap = new OnsetDetect(handler, .4, 0.07,-60);
				Debug.printMessage("Onset detect");
			}
		});
		
		
		this.add(inputPanel, BorderLayout.NORTH);
		this.add(testButton, BorderLayout.EAST);
		this.add(testButton2, BorderLayout.WEST);
	}
	
	public static void main (String[] args) throws InterruptedException, InvocationTargetException {
		
		SwingUtilities.invokeAndWait(new Runnable() {
			@Override
			public void run() {
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
				} catch (Exception e) {
					//ignore failure to set default look en feel;
				}
				JFrame frame = new Test();
				frame.pack();
				frame.setSize(640, 480);
				frame.setVisible(true);
			}
		});
		
	}
}
