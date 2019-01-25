//package com.davesone.vis.triggers;
//
//public class TriggerSetting {
//	
//	private String[] snames;
//	private double[] svalues, maxvalues, minvalues;
//	
//	public TriggerSetting(String[] names) {
//		snames = names;
//	}
//	
//	public TriggerSetting(String[] names, double[] values) {
//		snames = names;
//		svalues = values;
//	}
//	
//	public TriggerSetting(String[] names, double[] values, double[]maxv, double[]minv) {
//		snames = names;
//		svalues = values;
//		maxvalues = maxv;
//		minvalues = minv;
//	}
//	
//	public String getSettingString(int index) {
//		return snames[index];
//	}
//	
//	public double getSettingValue(int index) throws NullPointerException {
//		if(svalues == null) {
//			throw new NullPointerException();
//		}
//		return svalues[index];
//	}
//	
//	public void setSettingValue(int index, double value) {
//		svalues[index] = value;
//	}
//	
//	public double getMax(int index) {
//		if(maxvalues != null)
//		return maxvalues[index];
//		return -1;
//	}
//	
//	public double getMin(int index) {
//		if (minvalues != null)
//		return minvalues[index];
//		return -1;
//	}
//	
//	public String[] getNames() {
//		return snames;
//	}
//	
//	public double[]getValues(){
//		return svalues;
//	}
//	
//	public int getSize() {
//		return svalues.length;
//	}
//}
