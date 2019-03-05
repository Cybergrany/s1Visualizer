package com.davesone.vis.core;

import java.util.ArrayList;

import org.marvinproject.image.test.plugin.Plugin;

import com.davesone.vis.triggers.TriggerThread;
import com.davesone.vis.ui.VideoOutputFrame;
import com.davesone.vis.video.PluginCompatible;
import com.davesone.vis.video.elements.Element;
import com.davesone.vis.video.elements.VideoFramelet;
import com.davesone.vis.video.elements.Element.elementFlavour;
import com.davesone.vis.video.plugins.PluginContainer;

/**
 * Class that handles all threads and programs needed to display the main show
 * @author Owner
 *
 */
public class ShowManager {
	
	private VideoOutputFrame showFrame;
	private TextAndObjectList<Scene> scenes;//List of all scenes
	private int currentScene = 0;
	
	public ShowManager(TextAndObjectList<Scene> scenes) {
		this.scenes = scenes;
		Debug.printMessage("\n\nStarting a new show...");
		printSceneContents();
		initShow();
		startShow();//TODO Call from manager window
	}
	
	public void initShow() {
		showFrame = new VideoOutputFrame(Values.defaultShowWindowSize.width, Values.defaultShowWindowSize.height);
		
		ArrayList<VideoFramelet> framelets = new ArrayList<>();
		for(Element e : scenes.getElement(currentScene).getElementList().getElements()) {
			if(e.getElementType() == elementFlavour.VIDEOFRAMELET) {
				VideoFramelet f = (VideoFramelet)e;
				framelets.add(f);
			}
		}
		if(!framelets.isEmpty())
		showFrame.getManager().getCanvas().initFramelets(framelets);
	}
	
	/**
	 * Starts the show threads
	 */
	public void startShow() {
		showFrame.getManager().startThread();
	}
	
	private void printSceneContents() {
		Debug.printMessage("SCENES");
		for(int i = 0; i < scenes.getNames().length; i++) {
			System.out.printf("\n%s", scenes.getNames()[i]);
			if(scenes.getElement(i).getElementList() != null) {
				System.out.println("\nContaining elements:");
				for(int i1  = 0; i1 < scenes.getElement(i).getElementList().size(); i1++) {//Get list of elements in each scene
					System.out.printf("%s ", scenes.getElement(i).getElementList().getNames()[i1]);
					if(scenes.getElement(i).getElementList().getElement(i1) instanceof PluginCompatible){
						if(((PluginCompatible) (scenes.getElement(i).getElementList().getElement(i1))).getPlugins()!=null) {
							System.out.println("\nContaining plugins: ");
							for(int i2 = 0; i2 < ((PluginCompatible) (scenes.getElement(i).getElementList().getElement(i1))).getPlugins().size(); i2++) {
								System.out.printf("%s ", ((PluginCompatible) (scenes.getElement(i).getElementList().getElement(i1))).getPlugins().get(i2).getName());
							}
						}
					}
				}
			}
		}
	}
}
