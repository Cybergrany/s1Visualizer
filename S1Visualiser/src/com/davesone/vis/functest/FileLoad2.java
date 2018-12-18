package com.davesone.vis.functest;

import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber.Exception;

public class FileLoad2 {
	
	public FileLoad2() {
		try {
			FFmpegFrameGrabber grabber = FFmpegFrameGrabber.createDefault("./res/Realness.mp4");
			
			grabber.start();
			
			System.out.println("Total frames: "+grabber.getLengthInFrames());
			
//			try {
//				Thread.sleep(2000);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
			
			Frame f;
			
			while((f = grabber.grabImage()) != null) {
				System.out.println("grabbed frame at " + grabber.getFrameNumber());
				Thread.sleep(20);
			}
			
			grabber.stop();
			grabber.release();
			
			
		} catch (Exception | InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		FileLoad2 f = new FileLoad2();
	}

}
