package com.freedomotic.plugins.concepts;

public class LightSensor extends ConnectedObject {

	public LightSensor(String name , String id) {
		super(name, id);
		// TODO Auto-generated constructor stub
	}


	private position lightSensorPosition ;
	private double lightSensorValue= 0 ;
	
			
			public position getLightSensorPosition() {
		return lightSensorPosition;
	}


	public void setLightSensorPosition(position lightSensorPosition) {
		this.lightSensorPosition = lightSensorPosition;
		this.addDataProperie(dataProperties.lightSensorPosition, lightSensorPosition.toString());
	}


	public double getLightSensorValue() {
		return lightSensorValue;
	}


	public void setLightSensorValue(double lightSensorValue) {
		this.lightSensorValue = lightSensorValue;
		this.addDataProperie(dataProperties.lightSensorValue, lightSensorValue);
	}


			public enum position{
		 internal , external
	}
}
