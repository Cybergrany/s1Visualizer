package com.davesone.vis.core;

import com.davesone.vis.triggers.Element;
import com.davesone.vis.triggers.OnsetDetect;
import com.davesone.vis.triggers.PercussionDetect;
import com.davesone.vis.triggers.Trigger;
import com.davesone.vis.video.VideoFramelet;

/**
 * A class contaning statics and methods to help convert UI elements to usable objects
 * @author Owner
 *
 */
public class UIWrapperManager {
	
	public static final String[] elementFlavoursPublic = {"Video Framelet"};//TODO maybe move to element interface
	public static final String[] elementFlavors = {"Onset Detect", "Percussion Detect"};//Modify this when a trigger is added
	
	public static Element stringToElement(String input) throws IllegalArgumentException{
		//Sanitize string
		String s = input.replaceAll("\\s+","").toUpperCase();
		switch(s) {
			case "VIDEOFRAMELET":
				return new VideoFramelet();
		}
		throw new IllegalArgumentException();
	}
	
	public static Trigger stringToTrigger(String input) throws IllegalArgumentException{
		//Sanitize string
		String s = input.replaceAll("\\s+","").toUpperCase();
		switch(s) {
			case "ONSETDETECT":
				return new OnsetDetect();
			case "PERCUSSIONDETECT":
				return new PercussionDetect();
		}
		throw new IllegalArgumentException();
	}
	
}
