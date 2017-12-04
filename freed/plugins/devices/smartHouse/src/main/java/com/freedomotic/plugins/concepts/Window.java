package com.freedomotic.plugins.concepts;

import com.freedomotic.plugins.concepts.Concept.dataProperties;

public class Window extends ConnectedObject {

	public Window(String name, String id) {
		super(name, id);
		// TODO Auto-generated constructor stub
	}
	
	private boolean windowState = false;

	public boolean isWindowState() {
		return windowState;
	}

	public void setWindowState(boolean windowState) {
		this.windowState = windowState;
		this.addDataProperie(dataProperties.windowState, windowState);
		
	}
	
	
}
