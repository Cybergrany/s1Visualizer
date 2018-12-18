package com.davesone.vis.core;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

public class Localization {
	
	public static String toLocalString(Object info){
		if(!isWindows())
			return info.toString();
		
		String defaultEncoding = Charset.defaultCharset().toString();
		
		try{
			return new String(info.toString().getBytes("windows-1252"), defaultEncoding);
		}catch(UnsupportedEncodingException ex){
			return info.toString();
		}
	}
	
	private static String OS = null;
	
	public static String getOsName(){
		if(OS == null)
			OS = System.getProperty("os.name");
	    return OS;
	}public static boolean isWindows(){
	   return getOsName().startsWith("Windows");
	}
}
