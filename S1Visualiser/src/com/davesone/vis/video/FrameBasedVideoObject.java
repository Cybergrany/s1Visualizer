package com.davesone.vis.video;

import marvin.image.MarvinImage;

public interface FrameBasedVideoObject {
	
	MarvinImage getImage();
	
	void tick();
	void render();
	
	int getWidth();
	int getHeight();
}
