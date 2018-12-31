package com.davesone.vis.video;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.ArrayList;

import com.davesone.vis.core.Debug;
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
public class MarvinPanelCanvas extends MarvinImagePanel implements FrameBasedVideoObject, Runnable{
	
	private MarvinImage masterImage, bgImage;
	private CustomMarvinJavaCVAdapter bgAdapter;
	
	private Thread frameletRenderThread;
	
	private Color bgColor = Color.BLACK;
	
	private ArrayList<VideoFramelet> framelets;
	
	private int width, height;
	private boolean renderFramelets, bgisVideo = false;
	
	public MarvinPanelCanvas(int w, int h) {
		super();
		width = w; height = h;
		masterImage = new MarvinImage(width, height);
		bgImage = new MarvinImage(width, height);
		
		setBgColor(bgColor);//TODO TEMP
		
		framelets = new ArrayList<>();
		
		this.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				resize(e.getComponent().getWidth(), e.getComponent().getHeight());
			}
		});
		
	}
	
	public void initFrameletThread() {
		frameletRenderThread = new Thread(this, "Framelet Renderer");
		renderFramelets = false;
	}
	
	public void tick() {
		for(VideoFramelet f: framelets) {
			f.tick();
		}
	}
	
	public void render() {
		if(bgisVideo) {
			try {
				bgImage = bgAdapter.getFrame();
				bgImage.update();
			} catch (MarvinVideoInterfaceException e) {
				Debug.printError("Canvas background failed to play");
			}
		}
	}
	
	/**
	 * We run the framelet rendering on a separate thread to free up the main thread
	 * Care should be taken in tick methods to avoid requiring render-specific values
	 */
	@Override
	public void run() {
		while(renderFramelets) {
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
	}
	
	public void resize(int w, int h) {
		width = w; height = h;
		masterImage.setDimension(width, height);
		bgImage.setDimension(width, height);
		masterImage.updateColorArray();
		masterImage.update();
		bgImage.updateColorArray();
		bgImage.update();
		repaint();
//		System.out.println(width);
//		masterImage.getWidth();//For some reason calling this prevents the image array overflowing ¯\_(;-;)_/¯
	}
	
	@Override
	public MarvinImage getImage() {
		return masterImage;
	}
	
	public void setBgColor(Color c) {
		bgisVideo = false;
		bgImage.fillRect(0, 0, width, height, c);
		bgImage.update();
	}
	
	public void setBgVideo(String path) {
		bgAdapter = new CustomMarvinJavaCVAdapter(path);
		bgisVideo = true;
	}
	
	public void addFramelet(String filepath) {
		try {
			VideoFramelet f = new VideoFramelet(filepath);
			framelets.add(f);
		} catch (MarvinVideoInterfaceException e) {
			// TODO error handle
			e.printStackTrace();
		}
		if(!renderFramelets) {
			renderFramelets = true;
			frameletRenderThread.start();
		}
	}
	
	public void addFramelet(VideoFramelet f) {
		framelets.add(f);
		if(!renderFramelets) {
			renderFramelets = true;
			frameletRenderThread.start();
		}
	}
	
	public void removeFramelet(VideoFramelet f) {
		framelets.remove(f);
		if(framelets.size() == 0)
			renderFramelets = false;
	}
	
	private void addFrameletToImage(VideoFramelet f, VideoFramelet prevF) {
		for(int x = f.getX(); x < f.getX() + f.getSize().getWidth(); x++) {
			for(int y = f.getY(); y <f.getY() + f.getSize().getHeight(); y++) {
				if(x < width && y < height && x - f.getX() < f.getSize().getWidth() && y - f.getY() < f.getSize().getHeight() && ((width * height) <= masterImage.getIntColorArray().length)) {
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
