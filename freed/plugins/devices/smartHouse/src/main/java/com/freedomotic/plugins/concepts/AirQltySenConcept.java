package com.freedomotic.plugins.concepts;



public class AirQltySenConcept extends ConnectedObject {
	
	private double airQualitySensorValue =0 ;
	
	public AirQltySenConcept(String name,String id) {
		super(name,id);
		// TODO Auto-generated constructor stub
	}

	public double getAirQualitySensorValue() {
		return airQualitySensorValue;
	}

	public void setAirqualitySensorValue(double airqualitySensorValue) {
		this.airQualitySensorValue = airqualitySensorValue;
		this.addDataProperie(dataProperties.airQualitySensorValue, airQualitySensorValue);
	}


	
	
}
