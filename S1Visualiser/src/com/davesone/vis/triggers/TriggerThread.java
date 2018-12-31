package com.davesone.vis.triggers;

import java.util.ArrayList;

/**
 * A thread that simply checks if a trigger has been sent, and then
 * invokes any required trigger actions
 * @author Owner
 *
 */
public class TriggerThread implements Runnable{
	
	public boolean running;
	private Thread tThread;
	private ArrayList<Element> triggerable;

	/**
	 * Init the thread, running must be set to true before thread will start
	 * @param triggerableList
	 */
	public TriggerThread(ArrayList<Element> triggerableList) {
		triggerable = triggerableList;
		tThread = new Thread(this, "Trigger listening thread");
	}
	
	@Override
	public void run() {//TODO this thread is not synced with video thread, so triggers might end up a bit funny in timing
		while(running) {
			for(Element t : triggerable) {
				if(t.getTrigger().isTriggered()) {
					t.getTriggerAction().onTrigger();
					t.getTrigger().resetTrigger();
				}
			}
		}
	}
	
	/**
	 * Call when a change to the list of triggerable objects is needed
	 */
	public void updateTriggerableList(ArrayList<Element> t) {
		triggerable = t;
	}
	
	/**
	 * In case the thread needs to be accessed
	 * @return the listening thread
	 */
	public Thread getThread() {
		return tThread;
	}

}
