package com.davesone.vis.core;

/*
 * Prints whatever's happening
 * TODO implement in-program console
 */
public class Debug {
	
	public Debug() {
		
	}
	
	public static void printMessage(String s) {
		String classname = Thread.currentThread().getStackTrace()[2].getClassName();
		System.out.println("[" + classname + "]" + s);
	}
	
	public static void printMessage(String s, Object... args) {
		String classname = Thread.currentThread().getStackTrace()[2].getClassName();
		System.out.println("[" + classname + "]");
		System.out.printf(s, args);
	}
	
	public static void printError(String s) {
		String classname = Thread.currentThread().getStackTrace()[2].getClassName();
		System.out.println("[" + classname + "]" + s);
	}
}
