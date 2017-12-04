package com.freedomotic.plugins.concepts;

public class Lamp extends ConnectedObject {
	
	private boolean lampState = false;

	public Lamp(String name, String id ) {
		super(name, id);
		// TODO Auto-generated constructor stub
	}

	public boolean isLampState() {
		return lampState;
	}

	public void setLampState(boolean lampState) {
		this.lampState = lampState;
		this.addDataProperie(dataProperties.lampState, lampState);
	}

}
