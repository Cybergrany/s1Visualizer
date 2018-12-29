package com.davesone.vis.core;

import com.davesone.vis.audio.AudioStreamHandler;
import com.davesone.vis.video.plugins.PluginLoader;

public class Main {
	
	private PluginLoader pluginLoader;
	private AudioStreamHandler audioHandler;
	private static Main instance;
	
	public Main() {
		init();
	}
	
	public void init() {
		pluginLoader = new PluginLoader();//Loads plugins from directory
		audioHandler = new AudioStreamHandler();//Inits the handler, a mixer must first be set for it to work
	}
	
	public static void main(String[] args) {
		instance = new Main();
	}
	
	public static Main getInstance() {
		return instance;
	}
}
