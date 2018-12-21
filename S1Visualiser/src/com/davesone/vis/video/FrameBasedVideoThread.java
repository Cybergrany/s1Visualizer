package com.davesone.vis.video;

public class FrameBasedVideoThread implements Runnable{
	
	private Thread mainThread;
	
	public MarvinPanelCanvas mainCanvas;
	
	private int ticksPs = 60, frameLimit = 90;
	private int fps = 0, ticks = 0;//Used to get current frames and ticks
	private long totalTicks = 0;
	
	public boolean running;
	
	public FrameBasedVideoThread(int frameWidth, int frameHeight) {
		
		mainCanvas = new MarvinPanelCanvas(frameWidth, frameHeight);
		mainCanvas.initFrameletThread();
		mainThread = new Thread(this, "Master Video Thread");
		
	}

	@Override
	public void run() {
		while(running) {
			long nsPerTick = (long) 1000000000 / ticksPs;
			long nsPerFrame = (long) 1000000000 / frameLimit;
			long lastTick = System.nanoTime() - nsPerTick;
			long lastFrame = System.nanoTime();
			long lastTimer = System.currentTimeMillis();
			fps = 0; ticks = 0;
			running = true;

			while (running) {
				if (System.currentTimeMillis() - lastTimer >= 1000) {
//					if(printFPS) {
						//Print the current fps and tps if printFPS is true
						System.out.printf("%d ticks, %d fps\n", ticks, fps);
//					}
					lastTimer = System.currentTimeMillis();
					ticks = fps = 0;
				}

				if (System.nanoTime() > lastTick + nsPerTick) {
					mainCanvas.tick(); //Tick canvas
					ticks++;
					totalTicks++;
					if(totalTicks >= Long.MAX_VALUE) {
						totalTicks = 0;
					}
					lastTick += nsPerTick;
				}

				if (System.nanoTime() > lastFrame + nsPerFrame && !(System.nanoTime() > lastTick + nsPerTick)) {
					mainCanvas.render();//Render Canvas
					fps++;

					lastFrame = lastFrame + nsPerFrame;
				}
				
				if ((lastTick + nsPerTick) - System.nanoTime() > 5 &&
					(lastFrame + nsPerFrame) - System.nanoTime() > 5) {
					try {
						Thread.sleep(0, 3);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
	
	public Thread getThread() {
		return mainThread;
	}
}
