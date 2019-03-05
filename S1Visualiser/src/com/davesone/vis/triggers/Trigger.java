package com.davesone.vis.triggers;

import java.io.Serializable;

import com.davesone.vis.audio.AudioProcessorHandler;

import marvin.gui.MarvinAttributesPanel;

public interface Trigger extends Serializable{
	
	public void onTrigger();
	
	/**
	 * Get the listening thread
	 */
	
	public Thread getListeningThread();
	
	public void setListeningThread(Thread t);
	
	/**
	 * Used to get the attached processing handler
	 * @return
	 */
	public AudioProcessorHandler getHandler();
	
	public MarvinAttributesPanel getAttributesPanel();
	
	public void setAttributesPanel(MarvinAttributesPanel p);
	
	/**
	 * Prints current setting values, 
	 * useful for validation
	 */
	public void printSettingValues();
	
	/**
	 * Called every time the trigger's settings are updated
	 */
	public void onSettingsUpdate();

}
