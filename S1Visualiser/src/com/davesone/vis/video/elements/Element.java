package com.davesone.vis.video.elements;

import java.awt.Dimension;
import java.io.Serializable;
import java.util.ArrayList;

import com.davesone.vis.triggers.TriggerAction;

import marvin.gui.MarvinAttributesPanel;
import marvin.util.MarvinAttributes;

/**
 * Element- a display element which makes up a scene
 * @author Owner
 *
 */
public interface Element {
	
	/**
	 * Type of element, for sorting
	 * objects into their respective places
	 * in the render window
	 */
	public enum elementFlavour {VIDEOFRAMELET, BACKGROUND};
	public elementFlavour getElementType();
	
	public MarvinAttributesPanel getAttributesPanel();
	public MarvinAttributes getAttributes();
	
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
	 */
//	public void move(int distance, int timex);//@param timex: How long to move distance
//	public void pulseSize(Dimension maxSize, Dimension minSize, int timex);
	
	
	
}
