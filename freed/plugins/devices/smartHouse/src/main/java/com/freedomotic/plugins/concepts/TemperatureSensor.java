package com.freedomotic.plugins.concepts;

public class TemperatureSensor extends ConnectedObject {

	public TemperatureSensor(String name,String id) {
		super(name, id);
		// TODO Auto-generated constructor stub
	}

	private double ambTempSensorValue=0 ;

	public double getAmbTempSensorValue() {
		return ambTempSensorValue;
	}

	public void setAmbTempSensorValue(double ambTempSensorValue) {
		this.ambTempSensorValue = ambTempSensorValue;
		this.addDataProperie(dataProperties.ambTempSensorValue, ambTempSensorValue);
	}
	
}
