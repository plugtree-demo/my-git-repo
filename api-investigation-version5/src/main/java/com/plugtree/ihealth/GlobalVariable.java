package com.plugtree.ihealth;

import java.util.LinkedList;
import java.util.List;

public class GlobalVariable {

	private List<String> values;

	public GlobalVariable() {
		this.values = new LinkedList<String>();
	}
	
	public void addValue(String value) {
		this.values.add(value);
	}
	
	public List<String> getValues() {
		return values;
	}
}
