package com.freedomotic.plugins.concepts;

public class Curtain extends ConnectedObject {
	public Curtain(String name , String id) {
		super(name,id);
		// TODO Auto-generated constructor stub
	}

	private boolean curtainState = false;

	public boolean isCurtainState() {
		return curtainState;
	}

	public void setCurtainState(boolean curtainState) {
		this.curtainState = curtainState;
		this.addDataProperie(dataProperties.curtainState, curtainState);
	}
}
