package com.davesone.vis.video;

import java.io.File;
import java.nio.ByteBuffer;

import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.javacv.OpenCVFrameConverter;

import marvin.image.MarvinImage;
import marvin.video.MarvinVideoInterface;
import marvin.video.MarvinVideoInterfaceException;

/**
 * Created to deal with redundancies in Marvin's adapter
 * @author Owner
 *
 */
public class CustomMarvinJavaCVAdapter implements MarvinVideoInterface{
		private FFmpegFrameGrabber	grabber;
		
		private OpenCVFrameConverter.ToMat converter;
		
		private int				width;
		private int				height;
		private boolean			connected;
		private int[]			intArray;
		
		public CustomMarvinJavaCVAdapter(String path) {
			try {
				loadResource(path);
			} catch (MarvinVideoInterfaceException e) {
				System.err.println("Failed to load resource: " + path);
			}
		}
		
		@Override
		public void loadResource(String path) throws MarvinVideoInterfaceException{
			try{
				File f = new File(path);
				
				if(!f.exists())
					throw new MarvinVideoInterfaceException("File not found.", new Exception());
				
				grabber = FFmpegFrameGrabber.createDefault(f);
				
				converter = new OpenCVFrameConverter.ToMat();
				
				grabber.start();
				this.width = grabber.getImageWidth();
				this.height = grabber.getImageHeight();
				
				intArray = new int[height*width*4];
				
				grabber.setAudioChannels(0);
				
				connected = true;
			}
			catch(Exception e){
				throw new MarvinVideoInterfaceException("Error while trying to load resource", e);
			}
		}
		
		@Override
		public void disconnect() throws MarvinVideoInterfaceException {
			try{
				grabber.stop();
				connected = false;
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
		
		@Override
		public int getImageWidth(){
			return this.width;
		}
		
		@Override
		public int getImageHeight(){
			return this.height;
		}
		
		@Override
		public MarvinImage getFrame() throws MarvinVideoInterfaceException{
			try {
				if (grabber.getFrameNumber() < grabber.getLengthInFrames()-1) {
					Frame f = grabber.grabImage();
					if(f != null && f.image != null) {
						int[] arr = convertToIntArray(converter.convert(f));
						MarvinImage i = new MarvinImage(width, height);
						i.setIntColorArray(arr);
						return i;
					}
				}
			}catch(Exception e) {
				throw new MarvinVideoInterfaceException("Severe error while reading frame", e);
			}
			return null;
		}
		
		private int[] convertToIntArray(Mat img){
			int arr[] = new int[intArray.length];
			ByteBuffer buffer = img.getByteBuffer();
			for(int ii=0, bi=0; bi<buffer.limit()-3; ii++, bi+=3){
				arr[ii] = 0xFF000000 + (buffer.get(bi+2) << 16) + (buffer.get(bi+1) << 8) + buffer.get(bi);
			}
			return arr;
		}

		@Override
		public int getFrameNumber() {
			if(connected){
				return grabber.getFrameNumber();
			}
			return -1;
		}

		@Override
		public void setFrameNumber(int number) throws MarvinVideoInterfaceException {
			if(connected){
				try{
					grabber.setFrameNumber(number);
				}
				catch(Exception e){
					throw new MarvinVideoInterfaceException("Error while setting frame number", e);
				}
			}
		}

		@Override
		public void connect(int deviceIndex) throws MarvinVideoInterfaceException {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void connect(int deviceIndex, int width, int height) throws MarvinVideoInterfaceException {
			
			
		}
}
