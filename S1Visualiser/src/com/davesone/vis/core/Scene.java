package com.davesone.vis.core;

import java.io.Serializable;

import com.davesone.vis.video.elements.Element;

/**
 * Contains a collection of elements, such as videoframelets,
 * and their triggers
 * @author Owner
 *
 */
public class Scene {
	
	private TextAndObjectList<Element> elementList;
	
	public Scene() {
		elementList = new TextAndObjectList<Element>();
	}
	
	public void addElement(Element e) {
		elementList.addElement(e, e.getElementType().name());
	}
	
	public TextAndObjectList<Element> getElementList(){
		return elementList;
	}
	
}
