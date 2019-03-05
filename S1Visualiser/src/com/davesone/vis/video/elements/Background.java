package com.davesone.vis.video.elements;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;

import com.davesone.vis.core.Debug;
import com.davesone.vis.triggers.TriggerAction;
import com.davesone.vis.triggers.TriggerException;
import com.davesone.vis.video.PluginCompatible;
import com.davesone.vis.video.plugins.PluginContainer;

import marvin.gui.MarvinAttributesPanel;
import marvin.util.MarvinAttributes;

public class Background extends VideoFramelet{

	private MarvinAttributes attributes;
	private MarvinAttributesPanel attributesPanel;
	private ArrayList<PluginContainer> plugins;
	
	private Color color = Color.BLACK;
	
	private boolean isVideo = false;
	private int width, height;
	
	public Background() {
		attributes = new MarvinAttributes();
		attributes.set("isvideo", true);
		attributes.set("vpath", "");
		plugins = new ArrayList<>();//init arraylist
	}
	
	@Override
	public elementFlavour getElementType() {
		return elementFlavour.BACKGROUND;
	}

	@Override
	public MarvinAttributesPanel getAttributesPanel() {
		if(attributesPanel == null){
			attributesPanel = new MarvinAttributesPanel();
			attributesPanel.addCheckBox("chckvid", "Is Video", "isvideo", attributes);
			attributesPanel.newComponentRow();
			attributesPanel.addLabel("lblPath", "Enter path of .mp4");
			attributesPanel.addTextField("fldPath", "vpath", attributes);
		}
		return attributesPanel;
	}
	
	public void init(int w, int h) {
		width = w; height = h;
		isVideo = (boolean) attributes.get("isvideo");
		
	}
	
	public void render() {
		
	}
	
	public void setColor(Color c) {
		color = c;
	}
	
	@Override
	public void addPlugin(PluginContainer p) {
		plugins.add(p);
	}

	@Override
	public void addPlugin(PluginContainer p, int index) {
		plugins.add(index, p);
	}

	@Override
	public void removePlugin(int index) {
		plugins.remove(index);
	}
	
	@Override
	public ArrayList<PluginContainer> getPlugins() {
		return plugins;
	}

	@Override
	public MarvinAttributes getAttributes() {
		return attributes;
	}

	@Override
	public void setTriggerAction(TriggerAction ta) {
		onIllegalOps();
	}

	@Override
	public TriggerAction getTriggerAction() {
		onIllegalOps();
		return null;
	}

	@Override
	public Dimension getSize() {
		return new Dimension(width, height);
	}

	@Override
	public void setSize(Dimension d) {
		onIllegalOps();
	}

	@Override
	public int getX() {
		return 0;
	}

	@Override
	public int getY() {
		return 0;
	}

	@Override
	public void setXY(int x, int y) {
		onIllegalOps();
	}

	@Override
	public void setVisibility(boolean tof) {
		onIllegalOps();
	}
	
	private void onIllegalOps() {
		Debug.printMessage("WARN: Attempt to illegally manipulate background: " + new Throwable().getStackTrace());
	}	

}
