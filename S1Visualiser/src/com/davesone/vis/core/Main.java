package com.davesone.vis.core;

import java.util.ArrayList;

import com.davesone.vis.video.plugins.PluginContainer;
import com.davesone.vis.video.plugins.PluginLoader;

public class Main {
	
	private PluginLoader pluginLoader;
	private static Main instance;
	
	public Main() {
		init();
	}
	
	public void init() {
		pluginLoader = new PluginLoader();//Loads plugins from directory
	}
	
	public static void main(String[] args) {
		instance = new Main();
	}
	
	public static Main getInstance() {
		return instance;
	}
	
	public ArrayList<PluginContainer> getPlugins(){
		return pluginLoader.plugins;
	}
}
