package com.davesone.vis.core;

import org.marvinproject.image.test.plugin.Plugin;

import com.davesone.vis.audio.AudioInputManager;
import com.davesone.vis.triggers.TriggerThread;
import com.davesone.vis.ui.MainFrame;
import com.davesone.vis.video.plugins.PluginLoader;

public class Main {

	private MainFrame mainFrame;
	private PluginLoader pluginLoader;
	private AudioInputManager audioManager;
	private ShowManager showManager;

	public Main() {
		init();
		mainFrame = new MainFrame(this);
		mainFrame.setVisible(true);
	}
	
	public void init() {
		pluginLoader = new PluginLoader();//Loads plugins from directory
		audioManager = new AudioInputManager();
	}
	
	public static void main(String[] args) {
		Main main = new Main();
	}
	
	/**
	 * Init the show, to be called from main frame
	 */
	public void initShow(TextAndObjectList<Scene> sceneList) {
		showManager = new ShowManager(sceneList);
	}
	
	public AudioInputManager getAudioManager() {
		return audioManager;
	}
	
	public PluginLoader getAvailablePlugins() {
		return pluginLoader;
	}
	
}
