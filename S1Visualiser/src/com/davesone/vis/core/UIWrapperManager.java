package com.davesone.vis.core;

import com.davesone.vis.triggers.Element;
import com.davesone.vis.video.VideoFramelet;

/**
 * A class contaning statics and methods to help convert UI elements to usable objects
 * @author Owner
 *
 */
public class UIWrapperManager {
	
	public static String[] elementFlavoursPublic = {"Video Framelet"};
	
	public static Element stringToElement(String input) throws IllegalArgumentException{
		//Sanitize string
		String s = input.replaceAll("\\s+","").toUpperCase();
		switch(s) {
			case "VIDEOFRAMELET":
				return new VideoFramelet();
		}
		throw new IllegalArgumentException();
	}
}
