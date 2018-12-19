package com.davesone.vis.video;

import java.util.ArrayList;

import com.davesone.vis.video.plugins.PluginContainer;

import marvin.image.MarvinImage;

/**
 * Denotes that plugins can be rendered
 * on this video object
 * @author Owner
 *
 */
public interface PluginCompatible {
	
	ArrayList<PluginContainer> getPlugins();
	
	void addPlugin(PluginContainer p);
	
	void removePlugin(PluginContainer p);

}
