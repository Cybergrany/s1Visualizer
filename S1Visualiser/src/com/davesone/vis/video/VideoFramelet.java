package com.davesone.vis.video;

import static com.davesone.vis.video.plugins.PluginCollection.scale;

import java.awt.Dimension;
import java.util.ArrayList;

import com.davesone.vis.core.Debug;
import com.davesone.vis.video.plugins.PluginContainer;

import marvin.image.MarvinImage;
import marvin.plugin.MarvinImagePlugin;
import marvin.util.MarvinPluginLoader;
import marvin.video.MarvinVideoInterfaceException;



/**
 * A floating frame to be drawn on the main canvas
 * @author Owner
 *
 */
public class VideoFramelet implements PluginCompatible, FrameBasedVideoObject{
	
	final int previewSize = 3;
	
	public boolean isVisible;
	
	private MarvinImage image;
	private MarvinImage[] preview;
	private CustomMarvinJavaCVAdapter adapter;
	private ArrayList<PluginContainer> plugins;
	
	private int xPos, yPos, width, origW, height, origH;
	
	//TODO make sure calling code handles any errors sufficiently 
	public VideoFramelet(String path) throws MarvinVideoInterfaceException {
		adapter = new CustomMarvinJavaCVAdapter(path);
		xPos = 0; yPos = 0;
		origW = width = adapter.getImageWidth(); origH = height = adapter.getImageHeight();
		
		image = adapter.getFrame();
		
		int framelength = adapter.getGrabber().getLengthInVideoFrames();
		preview = new MarvinImage[previewSize];
		
		//Setup previews
		for(int i = 0; i < previewSize;i++) {
			adapter.setFrameNumber(Math.round(framelength/(i+1)));
			preview[i] = adapter.getFrame();
		}
		
		adapter.setFrameNumber(0);
		plugins = new ArrayList<>();//init arraylist
		isVisible = true;
	}
	
	public void tick() {
		
	}
	
	public void render() {
		try {
			scale(adapter.getFrame(), image, width, height);
			if(!plugins.isEmpty()) {//Apply any marvin rendering effects
				for(PluginContainer p : plugins) {
					p.getPlugin().process(image.clone(), image);
				}
			}
		} catch (MarvinVideoInterfaceException e) {
			Debug.printError("Error rendering framelet");//TODO handle
		}
	}
	
	/**
	 * Returns true if a pixel intersects this framelet.
	 * @param xi Ensure this is relative to main canvas
	 * @param yi
	 * @return
	 */
	public boolean intersects(int xi, int yi) {
		int rX = getWidth() + getX(), rY = getHeight() + getY();
		
		if((xi < rX && yi < rY) & (xi > getX() && yi > getY()))
			return true;
		
		return false;
	}
	
	public void setVisible(boolean tof) {
		isVisible = tof;
	}
	
	public void resize(int w) {
		width = w;
		double factor = (double)origH/origW;
		height = (int)Math.ceil(factor)*w;
	}
	
	public void resize(int w, int h) {
		width = w;
		height = h;
	}
	
	public void setPosition(int x, int y) {
		xPos = x; yPos = y;
	}
	
	public Dimension getSize() {
		return new Dimension(width, height);
	}
	
	public int getX() {
		return xPos;
	}
	
	public int getY() {
		return yPos;
	}
	
	public void resetSize() {
		resize(origW, origH);
	}
	
	public MarvinImage getImage() {
		return image;
	}

	@Override
	public ArrayList<PluginContainer> getPlugins() {
		return plugins;
	}

	@Override
	public void addPlugin(PluginContainer p) {
		plugins.add(p);
		
	}

	@Override
	public void removePlugin(PluginContainer p) {
		plugins.remove(p);
	}

	@Override
	public int getWidth() {
		return width;
	}

	@Override
	public int getHeight() {
		return height;
	}
	
	public MarvinImage[] getPreviewImg() {
		return preview;
	}

}
