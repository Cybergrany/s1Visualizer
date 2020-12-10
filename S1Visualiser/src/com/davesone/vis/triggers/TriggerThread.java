package com.davesone.vis.triggers;

import java.util.ArrayList;

import com.davesone.vis.video.elements.Element;

/**
 * A thread that simply checks if a trigger has been sent, and then
 * invokes any required trigger actions
 * @author Owner
 *
 */
public class TriggerThread implements Runnable{
	
	private Thread listeningThread;
	private Triggerable targetPanel;//Panel showing trigger updates
	private Trigger trigger;
	private ArrayList<Element> triggerList;
	private boolean visualOnly = false;

	public TriggerThread(Trigger t, Triggerable p) {
		listeningThread = new Thread(this, "Trigger listening thread");
		visualOnly = true;
		targetPanel = p;
		trigger = t;
		trigger.setListeningThread(listeningThread);
	}
	
	/**
	 * Init the thread, running must be set to true before thread will start
	 * @param triggerableList
	 */
	public TriggerThread(ArrayList<Element> triggerableList) {
		triggerList = triggerableList;
		listeningThread = new Thread(this, "Trigger listening thread");
	}
	
	@Override
	public void run() {//TODO this thread is not synced with video thread, so triggers might end up a bit funny in timing
		while(true) {
			if(visualOnly) {
				synchronized (trigger.getListeningThread()) {
					try {
						trigger.getListeningThread().wait();
						targetPanel.triggerObject();
//						}
					}catch (InterruptedException e) {e.printStackTrace();}
				}
			}else{
//				for(Element t : triggerList) {
//					if(t.getTrigger().isTriggered()) {
//						t.getTriggerAction().onTrigger();
//						t.getTrigger().resetTrigger();//TODO timeouts etc
//					}
//				}
			}
		}
	}
	
	/**
	 * Call when a change to the list of triggerable objects is needed
	 */
	public void updateTriggerableList(ArrayList<Element> t) {
		triggerList = t;
	}
	
	public Trigger getTrigger() throws TriggerException{
		if(trigger == null) {
			throw new TriggerException("No single trigger set");
		}
		return trigger;
	}
	
	/**
	 * In case the thread needs to be accessed
	 * @return the listening thread
	 */
	public Thread getThread() {
		return listeningThread;
	}
	
	/**
	 * Returns the listening thread belonging to the trigger
	 * @return
	 */
	public Thread getTriggerListeningThread() {
		return trigger.getListeningThread();
	}

}
