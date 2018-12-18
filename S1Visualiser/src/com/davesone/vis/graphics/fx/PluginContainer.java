package com.davesone.vis.graphics.fx;

import marvin.plugin.MarvinImagePlugin;

public class PluginContainer {
	
	private MarvinImagePlugin plugin;
	
	private boolean loaded = false;
	
	private int args;
	
	private String name;
	
	
	public PluginContainer(MarvinImagePlugin p, String name) {
		plugin = p;
	}
	
	public String getName() {
		return name;
	}
	
	public MarvinImagePlugin getPlugin() {
		if (loaded)
			return plugin;
		return null;
	}
	
	public int args() {
		return args;
	}
}
