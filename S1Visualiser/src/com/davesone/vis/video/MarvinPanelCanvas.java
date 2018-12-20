package com.davesone.vis.video;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import com.davesone.vis.video.plugins.PluginContainer;

import marvin.gui.MarvinImagePanel;
import marvin.image.MarvinImage;
import marvin.video.MarvinVideoInterfaceException;

/**
 * A canvas onto which marvinimages can be added in the form of dynamic
 * frames. Extends MarvinImagePanel, which extends JPanel
 * Important note: this is a canvas, not a dynamic object itself
 * @author Owner
 *
 */
public class MarvinPanelCanvas extends MarvinImagePanel implements FrameBasedVideoObject{
	
	private MarvinImage masterImage, bgImage;
	
	private ArrayList<VideoFramelet> framelets;
	
	private int width, height;
	
	public MarvinPanelCanvas(int w, int h) {
		super();
		width = w; height = h;
		masterImage = new MarvinImage(width, height);
		bgImage = new MarvinImage(width, height);
		
		setBgColor(Color.BLACK);//TODO TEMP
		
		framelets = new ArrayList<>();
		
	}
	
	public void tick() {
		for(VideoFramelet f: framelets) {
			f.tick();
		}
	}
	
	public void render() {
		int i = 0;
		for (VideoFramelet f : framelets) {
			if(f.isVisible) {
				f.render();
				if(i == 0) {
					addFrameletToImage(f, null);
				}else {
					addFrameletToImage(f, framelets.get(i-1));
				}
				masterImage.update();
				repaint();
				i++;
			}
		}
	}
	
	@Override
	public MarvinImage getImage() {
		return masterImage;
	}
	
	public void setBgColor(Color c) {
		bgImage.fillRect(0, 0, width, height, Color.BLACK);
		bgImage.update();
	}
	
	public void addFramelet(String filepath) {
		try {
			VideoFramelet f = new VideoFramelet(filepath);
			framelets.add(f);
		} catch (MarvinVideoInterfaceException e) {
			// TODO error handle
			e.printStackTrace();
		}		
	}
	
	public void addFramelet(VideoFramelet f) {
		framelets.add(f);
	}
	
	public void removeFramelet(VideoFramelet f) {
		framelets.remove(f);
	}
	
	private void addFrameletToImage(VideoFramelet f, VideoFramelet prevF) {
		int x1 = (int) f.getSize().getWidth();
		int y1 = (int) f.getSize().getHeight();
		for(int x = f.getX(); x < f.getX() + x1; x++) {
			for(int y = f.getY(); y <f.getY() + y1; y++) {
				if(x < getWidth() && y < getHeight() && x - f.getX() < x1 && y - f.getY() < y1) {
					if(prevF == null) {
						masterImage.setIntColor(x, y, f.getImage().getIntColor(x - f.getX(), y - f.getY()));
					}else {
						if(!(prevF.intersects(x, y))) {
							masterImage.setIntColor(x, y, f.getImage().getIntColor(x - f.getX(), y - f.getY()));
						}
					}
				}
			}
		}
	}
	
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		
		if(masterImage != null){
			g.drawImage(bgImage.getBufferedImage(), 0, 0, this);
			g.drawImage(masterImage.getBufferedImage(), 0,0,this);
		}
	}
}
