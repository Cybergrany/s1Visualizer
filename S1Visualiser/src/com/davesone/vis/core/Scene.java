package com.davesone.vis.core;

import com.davesone.vis.triggers.Element;

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
