package com.davesone.vis.ui.panels;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.border.TitledBorder;

import com.davesone.vis.audio.AudioStreamHandler;
import com.davesone.vis.core.Localization;

public class MixerChooserPanel extends JPanel {
	
	private static final long serialVersionUID = 1L;
	
	Mixer mixer = null;
	AudioStreamHandler handler;
	
	public MixerChooserPanel(AudioStreamHandler h, int width, int height){
		
		super(new BorderLayout());
		
		handler = h;
		
		this.setBorder(new TitledBorder("1. Choose a microphone input"));
		JPanel buttonPanel = new JPanel(new GridLayout(0,1));
		ButtonGroup group = new ButtonGroup();
		
		for(Mixer.Info info : handler.getMixerInfo(false, true)){
			JRadioButton button = new JRadioButton();
			button.setText(Localization.toLocalString(info));//Might have to import tolocalString()from tarsos Shared.java
			buttonPanel.add(button);
			group.add(button);
			button.setActionCommand(info.toString());
			button.addActionListener(setInput);
		}
		
		this.add(new JScrollPane(buttonPanel,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER),BorderLayout.CENTER);
		this.setMaximumSize(new Dimension(width, height));
		this.setPreferredSize(new Dimension(width, height));
		
		this.addPropertyChangeListener("mixer", setProperty);
	}
	
	private ActionListener setInput = new ActionListener(){
		@Override
		public void actionPerformed(ActionEvent arg0) {
			for(Mixer.Info info : handler.getMixerInfo(false, true)){
				if(arg0.getActionCommand().equals(info.toString())){
					Mixer newValue = AudioSystem.getMixer(info);
					MixerChooserPanel.this.firePropertyChange("mixer", mixer, newValue);
					MixerChooserPanel.this.mixer = newValue;
					break;
				}
			}
		}
	};
	
	private PropertyChangeListener setProperty = new PropertyChangeListener() {
		@Override
		public void propertyChange(PropertyChangeEvent evt) {
				try {
					handler.setNewMixer((Mixer) evt.getNewValue());
				} catch (LineUnavailableException | UnsupportedAudioFileException e) {
					e.printStackTrace();
				}
		}
		
	};

}