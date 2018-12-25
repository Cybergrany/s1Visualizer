package com.davesone.vis.ui;

import static com.davesone.vis.video.plugins.PluginCollection.scale;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JComponent;

import com.davesone.vis.core.Debug;
import com.davesone.vis.core.TimelineObject;
import com.davesone.vis.ui.frames.MainEditingFrame;
import com.davesone.vis.ui.panels.MainScreenOptionsPanel;

import marvin.image.MarvinImage;

public class TimelineFrame extends JComponent{
	
	public static enum Type {SCENE, TRANSITION};
	private Type type;
	
	final Dimension SCENE = new Dimension(40, 40), TRANSITION = new Dimension(20, 20);
	final String sceneThumbDefault = "./res/UI/sceneThumb", transThumbDefault = "./res/UI/transThumb";
	
	private MarvinImage thumbnail;
	private MainScreenOptionsPanel onClickPanel; //Jpanel that gets added to screen on click
	private MainEditingFrame mFrame;
	
	/**
	 * Create a new object with a thumbmail image.
	 * Image is automatically resized as per static
	 * dimension in classfile
	 * @param t
	 * @param thumb: use null to set to default image
	 * @param main: the containing main frame, used to call panel updates
	 */
	public TimelineFrame(Type t, MarvinImage thumb, TimelineObject o, MainEditingFrame frame) {
		type = t;
		onClickPanel = o.getOptionsPanel();
		mFrame = frame;
		
		if(thumb != null) {
			thumbnail = new MarvinImage();
			scale(thumb, thumbnail, (int)SCENE.getWidth());
		}
		
		switch (type) {
			case SCENE:
				initScene();
				break;
			case TRANSITION:
				initTransition();
				break;
		}
		
		addMouseListener(listener);
	}
	
	public void initScene() {
		if(thumbnail == null) {
			setThumbnail(new File(sceneThumbDefault));
		}
	}	
	
	public void initTransition() {
		if(thumbnail == null) {
			setThumbnail(new File(transThumbDefault));
		}
	}
	
	/**
	 * Call this to update the object, useful if thumbnail image
	 * needs to be rebuilt TODO might just use a setthumb() method
	 */
	public void update() {
		
	}
	
	/**
	 * Just to keep the code clean
	 * @param f
	 */
	public void setThumbnail(File f) {
		try {
			BufferedImage b = ImageIO.read(f);
			thumbnail = new MarvinImage(b);
		} catch (IOException e) {
			Debug.printError("Error loading timeline thumbnails");
		}
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		if(thumbnail != null){
			g.drawImage(thumbnail.getBufferedImage(), 0,0,this);
		}
	}
	
	private MouseListener listener = new MouseListener() {
		
		@Override
		public void mouseReleased(MouseEvent e) {}
		
		@Override
		public void mousePressed(MouseEvent e) {}
		
		@Override
		public void mouseExited(MouseEvent e) {}
		
		@Override
		public void mouseEntered(MouseEvent e) {}
		
		@Override
		public void mouseClicked(MouseEvent e) {
			mFrame.UpdateOptionsPanel(onClickPanel);
		}
	};
}
