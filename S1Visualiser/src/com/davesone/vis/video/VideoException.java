package com.davesone.vis.video;

public class VideoException extends Exception{
	
	public VideoException(String message, Throwable e) {
		super(message, e);
	}
	
	public VideoException(String message) {
		super(message);
	}
}
