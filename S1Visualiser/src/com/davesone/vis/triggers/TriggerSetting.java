package com.davesone.vis.triggers;

import java.lang.reflect.Array;

public class TriggerSetting {
	
	private String[] snames;
	private double[] svalues;
	
	public TriggerSetting(String[] names) {
		snames = names;
	}
	
	public TriggerSetting(String[] names, double[] values) {
		snames = names;
		svalues = values;
	}
	
	public String getSettingString(int index) {
		return snames[index];
	}
	
	public double getSettingValue(int index) throws NullPointerException {
		if(svalues == null) {
			throw new NullPointerException();
		}
		return svalues[index];
	}
	
	public void setSettingValue(int index, double value) {
		svalues[index] = value;
	}
}
