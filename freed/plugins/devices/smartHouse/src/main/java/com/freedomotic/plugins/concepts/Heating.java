package com.freedomotic.plugins.concepts;

import com.freedomotic.plugins.concepts.Concept.dataProperties;

public class Heating extends ConnectedObject {
	
	private boolean healtingState =false ;
	private double temperatureValue = 0 ;
	public Heating(String name, String id) {
		super(name, id);
		// TODO Auto-generated constructor stub
	}
	public boolean isHealtingState() {
		return healtingState;
	}
	public void setHealtingState(boolean healtingState) {
		this.healtingState = healtingState;
		this.addDataProperie(dataProperties.heatingState, healtingState);
	}
	public double getTemperatureValue() {
		return temperatureValue;
	}
	public void setTemperatureValue(double temperatureValue) {
		this.temperatureValue = temperatureValue;
		this.addDataProperie(dataProperties.temepratureValue, temperatureValue);
	}
   
}
