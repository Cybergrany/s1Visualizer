package com.davesone.vis.triggers;

public class TriggerException extends Exception{
	
	public TriggerException(String message, Throwable e) {
		super(message, e);
	}
	
	public TriggerException(String message) {
		super(message);
	}
}
