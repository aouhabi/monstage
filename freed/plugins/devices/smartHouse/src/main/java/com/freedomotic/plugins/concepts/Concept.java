package com.freedomotic.plugins.concepts;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.map.MultiValueMap;

import com.freedomotic.plugins.concepts.Concept.concept;
import com.freedomotic.plugins.devices.smartHouse.application.MyOntology;

public abstract class Concept {
	private String name;
	private String id;
	private MultiValueMap dataPropertie = new MultiValueMap();
	private MultiValueMap objectPropertie = new MultiValueMap();
	
	public final static List<String> CURTAIN= Arrays.asList("CurtainToDown","CurtainToUp");
	public  final static List<String> LAMP= Arrays.asList("LampToOn","LampToOff");
	public final static List<String> Heating = Arrays.asList("HeatingToTurnOn","HeatingToTurnOff");
	public final static List<String> Window = Arrays.asList("WindowToClose","WindowToOpen");
	
	//hashmap qui les lie les actionneurs a leur etats objectifs
	private  static HashMap<concept, List> targetState = new HashMap<concept, List>() ;
	// pour chaque actionneurs on lui associe ces etats objectifs
	public static HashMap targetStateMap () {
	
		targetState.put(concept.Curtain, CURTAIN);
		targetState.put(concept.Lamp, LAMP);
		targetState.put(concept.Heating, Heating);
		targetState.put(concept.Window, Window);
		return targetState;
	}

	public Concept(String name, String id ) {
		super();
		this.name = name;
		this.addDataProperie(dataProperties.hasName, name);
		this.id = id;
		this.addDataProperie(dataProperties.hasId, id);
	}

	public String getName() {
		return name;
	}

	public String getId() {
		return id;
	}

	
	public Map<String, Object> getDataProperties() {
		return dataPropertie;
	}


	public Map<String, String> getObjectProperties() {
		return objectPropertie;
	}
	
	
	protected void addDataProperie(dataProperties propertie, Object value) {
		this.dataPropertie.put(propertie.toString(), value);
	}

	protected void addObjectProperie(objectProperties propertie, String c) {
		this.objectPropertie.put(propertie.toString(), c);
	}

	public void saveToOntology(concept cls, MyOntology onto) {

		onto.createObjectInstance(cls, this);
		onto.createObjectRelation(this);
	}
	// enumerer les concepts de l'ontologie
	public static enum concept {
		Door, Lamp, Curtain, MotionSensor, LightSensor, AmbTempSensor, MeetingRoom, AccessControlService, 
		Window,AirQualitySensor,Heating,
		BrightnessControlService, TemperatureControlService,AirQualityControlService, OrchestratorService,
		SecurityControlService
	}
	// enumerer les dataProperties de l'ontologie
	protected static enum dataProperties {
		hasTarget, hasName, hasId, curtainState, doorState, lampState, ambTempSensorValue,
		lightSensorPosition, lightSensorValue, motionSensorValue,windowState,airQualitySensorValue,
		heatingState,temepratureValue
	}
	// enumerer les objectProperties de l'ontologie
	public static enum objectProperties {
		hasExecutionContext, hasIntention, hasLocation, hasServiceLink, orchestrate
	}
	// enumerer les targetStates de l'ontologie
	public static enum targetState {
		CurtainToUp , CurtainToDown , LampToOff , LampToOn, WindowToOpen,WindowToClose,HeatingToTurnON,HeatingToTurnOff
		
	}


}
