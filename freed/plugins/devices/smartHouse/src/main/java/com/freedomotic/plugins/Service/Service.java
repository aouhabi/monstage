package com.freedomotic.plugins.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MultiMap;

import com.freedomotic.model.ds.Config;
import com.freedomotic.plugins.concepts.Concept;
import com.freedomotic.plugins.concepts.Query;
import com.freedomotic.plugins.concepts.Concept.concept;
import com.freedomotic.plugins.concepts.Location.location;
import com.freedomotic.plugins.devices.smartHouse.SmartHouse;
import com.freedomotic.plugins.devices.smartHouse.application.MyOntology;
import com.freedomotic.plugins.devices.smartHouse.tools.JenaEngine;
import com.freedomotic.things.EnvObjectLogic;
import com.freedomotic.things.impl.*;

public abstract class Service extends Concept {

	protected location executionContext;
	private concept targetObject;
	private String idActuator ="" ;
	private String rulePath="" ;

	public concept getTargetObject() {
		return targetObject;
	}

	public void setTargetObject(concept targetObject) {
		this.targetObject = targetObject;
		this.addDataProperie(dataProperties.hasTarget, targetObject.toString());
	}

	protected String getIdActuator() {
		return idActuator;
	}

	public Service(String name,String id) {
		super(name,id);
		this.addDataProperie(dataProperties.hasName, name);
		// TODO Auto-generated constructor stub
	}

	public location getExecutionContext() {
		return executionContext;
	}

	public void setExecutionContext(location executionContext) {
		this.executionContext = executionContext;
		this.addObjectProperie(objectProperties.hasExecutionContext, executionContext.toString());
	}

	public String getRulePath() {
		return rulePath;
	}

	public void setRulePath(String rulePath) {
		this.rulePath = rulePath;
	}

	protected boolean executeAction(SmartHouse s, MyOntology onto) {

		boolean isExecute = false;
		//liste qui recupere les etas cible possible de type de l'actionneur cibler par le service "hasTarget"
		List<String> listTarget = (List<String>) Concept.targetStateMap().get(this.getTargetObject());
		// pour chaque etat cible on interroge l'ontologie si il existe un resultat
		for (String cpt : listTarget) {
			//requete qui recupere les instances des etats cible si il y'a eu des inferences
			String q = Query._select.replace("Target", cpt).replace("service", this.getName());
			
			Map<String, String> result = JenaEngine.executeQuery(onto.getInferedModel(this.getRulePath()), q);
			
			// pour chaque instance de l'inference on execute l'action adequat pour changer l'etats de l'actionneur sur freedomotic
			for (Map.Entry tuple : result.entrySet()) {
				// grace a l'id de l'actionneur recuperer dans le resulta de la requete on pourra le recuperer sur freedomotic et ainsi agir sur ce dernier
				EnvObjectLogic obj = s.getApi().things().findOne(tuple.getKey().toString());
					this.idActuator=tuple.getKey().toString();
				switch (obj.getClass().getSimpleName()) {
				case "Light":
					if (!obj.isLock()) {
						Light la = (Light) obj;
						if (tuple.getValue().equals("true")) {
							la.executePowerOn(new Config());
						} else if (tuple.getValue().equals("false")) {
							la.executePowerOff(new Config());
						}
						obj.setLock(true);
						isExecute = true;
					} else {
						isExecute = false;
					}
					break;
				case "Curtain":
					if (!obj.isLock()) {
						Curtain cu = (Curtain) obj;
						if (tuple.getValue().equals("true")) {
							cu.executePowerOn(new Config());
						} else if (tuple.getValue().equals("false")) {
							cu.executePowerOff(new Config());
						}
						obj.setLock(true);
						isExecute = true;
					} else {
						isExecute = false;
					}
					break;
				case "Window" :
					if (!obj.isLock()) {
						Window wn = (Window) obj ;
						if (tuple.getValue().equals("true")) {
							wn.executePowerOn(new Config());
						} else if (tuple.getValue().equals("false")) {
							wn.executePowerOff(new Config());
						}
						obj.setLock(true);
						isExecute = true;
					} else {
						isExecute = false;
					}
					break;
				case "AirConditioner" :
					if (!obj.isLock()) {
						AirConditioner air = (AirConditioner)obj ;
						if (tuple.getValue().equals("true")) {
							air.executePowerOn(new Config());
							air.setConditioningTemperature(21, new Config(), true);	
						}else if (tuple.getValue().equals("false")) {
							air.executePowerOff(new Config());
						}
						obj.setLock(true);
						isExecute = true;
					} else {
						isExecute = false;	
					}
				default:
					isExecute = true;
				}
			}
		}
		return isExecute;
	}
	protected void  releaseLock(String id, SmartHouse s) {
		if(id !="") {
			EnvObjectLogic obj = s.getApi().things().findOne(id);	
			obj.setLock(false);
		}
	}
}
