package com.davesone.vis.video;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import marvin.gui.MarvinImagePanel;
import marvin.image.MarvinImage;
import marvin.video.MarvinVideoInterfaceException;

/**
 * A canvas onto which marvinimages can be added in the form of dynamic
 * frames. Extends MarvinImagePanel, which extends JPanel
 * @author Owner
 *
 */
public class MarvinPanelCanvas extends MarvinImagePanel{
	
	private MarvinImage masterImage, bgImage;
	
	private ArrayList<VideoFramelet> framelets;
	
	private int width, height;
	
	public MarvinPanelCanvas(int w, int h) {
		super();
		width = w; height = h;
		masterImage = new MarvinImage(width, height);
		bgImage = new MarvinImage(width, height);
		setBackground(Color.BLACK);
		framelets = new ArrayList<>();
		
	}
	
	public void render() {
		for (VideoFramelet f : framelets) {
			if(f.isVisible) {
				f.render();
				addFrameletToImage(f);
				masterImage.update();
//				validate();
				repaint();
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
	
	/**
	 * Add a framelet object without filter
	 */
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
	
	public void addFramelet(String filepath, String marvinpluginpath) {
		addFramelet(filepath);
		framelets.get(framelets.size()-1).addMarvinPlugin(marvinpluginpath);//TODO check if that -1 is needed
	}
	
	public void removeFramelet(VideoFramelet f) {
		framelets.remove(f);
	}
	
	private void addFrameletToImage(VideoFramelet f) {
		int x1 = (int) f.getSize().getWidth();
		int y1 = (int) f.getSize().getHeight();
		for(int x = f.getX(); x < f.getX() + x1; x++) {
			for(int y = f.getY(); y <f.getY() + y1; y++) {
				masterImage.setIntColor(x, y, f.getImage().getIntColor(x - f.getX(), y - f.getY()));				
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
	
	@Override
	public void update(){
		masterImage.update();
		repaint();
	}
}
