package com.davesone.vis.triggers;

/**
 * An object that reacts to triggers,
 * used in {@link TriggerThread} to
 * send notice to the monitoring object
 * @author Owner
 *
 */
public interface Triggerable {
	
	public void triggerObject();

}
