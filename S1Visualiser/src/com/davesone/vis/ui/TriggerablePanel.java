package com.davesone.vis.ui;

import com.davesone.vis.triggers.TriggerThread;

/**
 * A panel that reacts to triggers,
 * used in {@link TriggerThread} to
 * send notice to the monitoring panel
 * @author Owner
 *
 */
public interface TriggerablePanel {
	
	public void triggerDisplay();

}
