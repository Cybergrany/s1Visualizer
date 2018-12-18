package com.davesone.vis.graphics.fx;

import java.io.File;
import java.util.ArrayList;

import org.marvinproject.image.test.plugin.Plugin;

import com.davesone.vis.core.Debug;

import marvin.plugin.MarvinImagePlugin;
import marvin.plugin.MarvinPlugin;
import marvin.util.MarvinJarLoader;

public class PluginLoader {
	
	public static String pluginDir = "./marvin/plugins/image/";
	
	public ArrayList<PluginContainer> plugins;
	
	public PluginLoader() {
		
		File dir = new File(pluginDir);
		
		plugins = new ArrayList<>();
		
		for (final File p:dir.listFiles()) {
			PluginContainer pc = loadImagePlugin(p.getPath());
			if (pc!=null)
				plugins.add(pc);
		}
		
		Debug.printMessage("Total " + plugins.size() + " plugins loaded");
	}
	
	public static void main(String[]args) {
		new PluginLoader();
	}
	
	/**
	 * Slightly modified from MarvinPluginLoader
	 * @return
	 */
	private PluginContainer loadImagePlugin(String path) {
		String classname = path.replace(".jar", "");
		if(classname.lastIndexOf(".") != -1) {
			classname = classname.substring(classname.lastIndexOf(".")+1);
		}
		classname = classname.substring(0,1).toUpperCase()+classname.substring(1);
		
		if(!path.substring(path.length()-4, path.length()).equals(".jar")){
			path = path + ".jar";
		}
		
		MarvinJarLoader l;
		MarvinImagePlugin i;
		
		try {
			l = new MarvinJarLoader(path);
			i = (MarvinImagePlugin)(MarvinPlugin)l.getObject(classname);
		}catch (ClassCastException e){
			System.err.println("Failed to load plugin \"" + classname + "\". Is it really a plugin?");
			return null;
		}
		Debug.printMessage("Loaded Plugin \"" + classname + "\"");
		return new PluginContainer(i, classname);
	}
}
