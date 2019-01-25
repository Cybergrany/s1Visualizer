package com.davesone.vis.triggers;

import com.davesone.vis.audio.AudioProcessorHandler;

import marvin.gui.MarvinAttributesPanel;

public interface Trigger {
	
	/**
	 * main trigger thread will listen for this
	 */
	public boolean isTriggered();
	
	/**
	 * Call when operation on triggeraction has been carried out
	 */
	public void resetTrigger();
	
	/**
	 * Used to get the attached processing handler
	 * @return
	 */
	public AudioProcessorHandler getHandler();
	
	public MarvinAttributesPanel getAttributesPanel();
	
	/**
	 * Used to display settings list and modify them
	 */
//	public TriggerSetting getSettings();
	
	/**
	 * Called to change a setting values. Calling code will check
	 * string array of getSettings to find index
	 */
//	public void changeSetting(int index, double value) throws TriggerException;
	
	/**
	 * Called every time values need to be refreshed
	 */
//	public void onSettingUpdate() throws TriggerException;
	
	/**
	 * Ensure that setting values are not null
	 */
	public void start() throws TriggerException;

}
