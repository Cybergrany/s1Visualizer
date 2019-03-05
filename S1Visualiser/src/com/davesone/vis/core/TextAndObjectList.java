package com.davesone.vis.core;

import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JList;

/**
 * Wrapper for object lists
 * @author Owner
 *
 */
public class TextAndObjectList<listelements>{
	
	private  ArrayList<listelements> elementlist;
	private DefaultListModel<String> elementnames;
	private JList list;
	
	public TextAndObjectList() {
		elementlist = new ArrayList<listelements>();
		elementnames = new DefaultListModel<String>();
		list = new JList(elementnames);
	}
	
	public JList<listelements> getJList(){
		return list;
	}
	
	public DefaultListModel<String> getListModel(){
		return elementnames;
	}
	
	public String getName(int index) {
		return elementnames.getElementAt(index);
	}
	
	public listelements getElement(int index) {
		return elementlist.get(index);
	}
	
	public ArrayList<listelements> getElements(){
		return elementlist;
	}
	
	public String[] getNames() {
		String[] s = new String[elementnames.size()];
		
		for(int i = 0; i < s.length; i++) {
			s[i] = elementnames.get(i);
		}
		
		return s;
	}
	
	/**
	 * Adds an element with a default name (pos of element)
	 * @param index
	 * @param element
	 */
	public void addElement(listelements element, String prefix) {
		elementlist.add(element);
		elementnames.addElement(prefix + " " + elementlist.size());
	}
	
	public void removeElement(int index) {
		elementlist.remove(index);
		elementnames.removeElementAt(index);
	}
	
	public int size() {
		return elementlist.size();
	}
	
	/**
	 * Swaps the position of two elements
	 * @param index1
	 * @param index2
	 */
	public void swapElement(int index1, int index2) {
		listelements e1 = elementlist.get(index1);
		listelements e2 = elementlist.get(index2);
		String s1 = elementnames.getElementAt(index1);
		String s2 = elementnames.getElementAt(index2);
		
		elementlist.set(index1, e2);
		elementnames.set(index1, s2);
//		elementnames.add(index1, s2);
		elementlist.set(index2, e1);
		elementnames.set(index2, s1);
//		elementnames.add(index2, s1);
	}
	
}
