package com.davesone.vis.triggers;

import java.awt.Dimension;
import java.util.ArrayList;

/**
 * Element- a display element which makes up a scene
 * @author Owner
 *
 */
public interface Element {
	
	/**
	 * Type of triggerable, for sorting
	 * objects into their respective places
	 * in the render window
	 */
	public enum triggerFlavour {VIDEOFRAMELET};
	public triggerFlavour getTriggerType();
	
	public Trigger getTrigger();
	
	public void setTrigger(Trigger t);
	
	public void setTriggerAction(TriggerAction ta);
	
	/**
	 * For now, objects will have one triggeraction, triggeraction classes can have multiple methods for
	 * alternate actions.
	 * @return
	 */
	public TriggerAction getTriggerAction();
	
	
	/**
	 * Object manipulation methods
	 */
	public Dimension getSize();
	public void setSize(Dimension d);
	public int getX(); public int getY();
	public void setXY(int x, int y);
	public void setVisibility(boolean tof);
	
	/**
	 * TODO these are to move to triggeraction classes
	 * @param distance
	 * @param timex
	 */
	public void move(int distance, int timex);//@param timex: How long to move distance
	public void pulseSize(Dimension maxSize, Dimension minSize, int timex);
	
	
	
}
