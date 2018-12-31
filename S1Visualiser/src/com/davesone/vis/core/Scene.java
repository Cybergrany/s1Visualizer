package com.davesone.vis.core;

import java.util.ArrayList;

import com.davesone.vis.triggers.Element;
import com.davesone.vis.video.VideoFramelet;

/**
 * Contains a collection of elements, such as videoframelets,
 * and their triggers
 * @author Owner
 *
 */
public class Scene {
	
	private ArrayList<Element> elements;
	
	public Scene() {
		elements = new ArrayList<Element>();
	}
	
	public void addElement(Element t) {
		elements.add(t);
	}
	
	public ArrayList<Element> getElements() {
		return elements;
	}
}
