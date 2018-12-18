package com.davesone.vis.video.plugins;

import marvin.plugin.MarvinImagePlugin;

public class PluginContainer {
	
	private MarvinImagePlugin plugin;
	
	private boolean hasAttributesPanel = false;
	
	private int args;
	
	private String name;
	
	
	public PluginContainer(MarvinImagePlugin p, String name) {
		plugin = p;
		try {
			if(p.getAttributesPanel()!=null) {
				hasAttributesPanel = true;
			}
		}catch (NullPointerException e) {}
	}
	
	public String getName() {
		return name;
	}
	
	public boolean hasAttributesPanel() {
		return hasAttributesPanel;
	}
	
	public MarvinImagePlugin getPlugin() {
		return plugin;
	}
	
	public int args() {
		return args;
	}
}
