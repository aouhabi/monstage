package com.freedomotic.plugins.concepts;

public class Motionsensor extends ConnectedObject {
	
	public Motionsensor(String name, String id) {
		super(name, id);
		// TODO Auto-generated constructor stub
	}

	private boolean motionSensorValue =false ;

	public boolean isMotionSensorValue() {
		return motionSensorValue;
	}

	public void setMotionSensorValue(boolean motionSensorValue) {
		this.motionSensorValue = motionSensorValue;
		this.addDataProperie(dataProperties.motionSensorValue, motionSensorValue);
	}
	

}
