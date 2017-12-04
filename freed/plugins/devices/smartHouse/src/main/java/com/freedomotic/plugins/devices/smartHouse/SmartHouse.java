/**
 *
 * Copyright (c) 2009-2016 Freedomotic team http://freedomotic.com
 *
 * This file is part of Freedomotic
 *
 * This Program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2, or (at your option) any later version.
 *
 * This Program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * Freedomotic; see the file COPYING. If not, see
 * <http://www.gnu.org/licenses/>.
 */
package com.freedomotic.plugins.devices.smartHouse;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.stream.IntStream;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.freedomotic.api.EventTemplate;
import com.freedomotic.api.Protocol;
import com.freedomotic.behaviors.BehaviorLogic;
import com.freedomotic.environment.EnvironmentLogic;
import com.freedomotic.environment.Room;
import com.freedomotic.events.ObjectHasChangedBehavior;
import com.freedomotic.events.ZoneHasChanged;
import com.freedomotic.exceptions.UnableToExecuteException;
import com.freedomotic.model.ds.Config;
import com.freedomotic.model.geometry.FreedomPoint;
import com.freedomotic.model.object.EnvObject;
import com.freedomotic.plugins.Service.ElementaryService;
import com.freedomotic.plugins.Service.HomeOrchestrator;
import com.freedomotic.plugins.Service.Orchestrator;
import com.freedomotic.plugins.concepts.Query;
import com.freedomotic.plugins.concepts.AirQltySenConcept;
import com.freedomotic.plugins.concepts.Concept.concept;
import com.freedomotic.plugins.concepts.LightSensor.position;
import com.freedomotic.plugins.concepts.Concept.objectProperties;
import com.freedomotic.plugins.concepts.Curtain;
import com.freedomotic.plugins.concepts.Heating;
import com.freedomotic.plugins.concepts.Lamp;
import com.freedomotic.plugins.concepts.LightSensor;
import com.freedomotic.plugins.concepts.Location.location;
import com.freedomotic.plugins.concepts.Motionsensor;
import com.freedomotic.plugins.concepts.TemperatureSensor;
import com.freedomotic.plugins.concepts.Window;
import com.freedomotic.plugins.devices.smartHouse.application.MyOntology;
import com.freedomotic.plugins.devices.smartHouse.tools.JenaEngine;
import com.freedomotic.reactions.Command;
import com.freedomotic.things.EnvObjectLogic;
import com.freedomotic.things.ThingRepository;
import com.freedomotic.things.impl.AirConditioner;
import com.freedomotic.things.impl.AirQualitySensor;
import com.freedomotic.things.impl.GenericSensor;
import com.freedomotic.things.impl.Light;
import com.freedomotic.things.impl.MotionSensor;
import com.freedomotic.things.impl.Person;
import com.freedomotic.things.impl.Thermometer;
import com.google.inject.Inject;
import com.hp.hpl.jena.rdf.model.Resource;

/**
 *
 * @author Mauro Cicolella
 */
public class SmartHouse extends Protocol {

	private static final Logger LOG = LoggerFactory.getLogger(SmartHouse.class.getName());
	final int POLLING_WAIT;

	@Inject
	private ThingRepository thingsRepository;
	 static MyOntology onto = null;

	public SmartHouse() {
		// every plugin needs a name and a manifest XML file
		super("SmartHouse", "/smartHouse/smartHouseManifest.xml");
		// read a property from the manifest file below which is in
		// FREEDOMOTIC_FOLDER/plugins/devices/com.freedomotic.hello/hello-world.xml
		POLLING_WAIT = configuration.getIntProperty("time-between-reads", 2000);
		// POLLING_WAIT is the value of the property "time-between-reads" or 2000
		// millisecs,
		// default value if the property does not exist in the manifest
		setPollingWait(POLLING_WAIT); // millisecs interval between hardware device status reads
	}

		@Override
	protected void onShowGui() {
		/**
		 * uncomment the line below to add a GUI to this plugin the GUI can be started
		 * with a right-click on plugin list on the desktop frontend
		 * (com.freedomotic.jfrontend plugin)
		 */
		// bindGuiToPlugin(new HelloWorldGui(this));
	}

	@Override
	protected void onHideGui() {
		// implement here what to do when the this plugin GUI is closed
		// for example you can change the plugin description
		setDescription("My GUI is now hidden");
	}
	//methode pour gerer la position de l'utilisateur
	private void position(int pos) {
		Person p = null;
		Room r = null;
		Room rp = null;
		for (EnvObjectLogic object : getApi().things().findAll()) {
			if (object.getClass().toString().equals("class com.freedomotic.things.impl.Person")) {	
				p = (Person) object;
				if (pos == 1 && !getObjLocation(p).getPojo().getName().equals("Bedroom") ) {
					p = (Person) object;
					p.setLocation(187, 605);
					p.setChanged(true);
				} else if (pos == 2 && !getObjLocation(p).getPojo().getName().equals("Kitchen") )  {
					p = (Person) object;
					p.setLocation(601, 417);
					p.setChanged(true);	
					Room rd =new Room(null);
				} 
			}
		}

	}
	//methode pour pour changer l'etat du detecteur de presence 
	public void activeMotionSensors(String idMS) {
		
		MotionSensor ms = (MotionSensor) getApi().things().findOne(idMS);
		Room roomMs = getObjLocation(ms);
		List<EnvObjectLogic> objects = getApi().things().findAll();
		int i = 0;
		boolean test = false;
		while ((test == false) && (i < objects.size())) {			
			if (getObjLocation(objects.get(i)).getPojo().getName() == roomMs.getPojo().getName()) {
				if (objects.get(i).getClass().toString().equals("class com.freedomotic.things.impl.Person")) {
						test = true;
				} 
			}
			i++;
		}
		if (test) {
			ms.executePresence(configuration);		
		}else {
			ms.executeNoPresence(configuration);		
		}
	}
	//methode pour recuperer la localisation d'un objet
	public Room getObjLocation(EnvObjectLogic p) {
		for (EnvironmentLogic flate : this.getApi().environments().findAll()) {
			for (Room room : flate.getRooms()) {

				for (EnvObject obj : room.getPojo().getObjects()) {
					/* obj.getSimpleType().equals("user") */
					if (obj.getUUID() == p.getPojo().getUUID()) {

						return (Room) room;
					}
				}

			}
		}
		return null;
	}
	
	
	public void instanciateOntology(JsonNode donneesContexte) {
		
		 position(donneesContexte.get("location").asInt());
		
		
		LightSensor le = new LightSensor("lightSensorExterieure","le");
		le.setLightSensorPosition(position.external);
		le.setLightSensorValue(donneesContexte.get("le").asDouble());
		le.saveToOntology(concept.LightSensor, onto);

		for (EnvObjectLogic o : this.getApi().things().findAll()) {
			Room r = this.getObjLocation(o);
			if (r != null) {
				

				String cls = o.getClass().getSimpleName();
				String name = o.getPojo().getName();
				String location = r.getPojo().getName();
				String id = o.getPojo().getUUID();
				switch (cls) {
				case "MotionSensor":
					Motionsensor ms = new Motionsensor(name,id);

					activeMotionSensors(id);
					ms.setMotionSensorValue(Boolean.valueOf(o.getBehavior("msState").getValueAsString()));
					ms.setLocation(location);
					ms.saveToOntology(concept.MotionSensor, onto);
					break;
				case "GenericSensor":
					GenericSensor li = (GenericSensor) o;
					
					
					li.executeSetReadValue((int) (donneesContexte.get("li").asDouble() * 10), new Config());
					LightSensor ls = new LightSensor(name,id);
					ls.setLightSensorPosition(position.internal);
					ls.setLocation(location);
					ls.setLightSensorValue(Double.parseDouble(o.getBehavior("luminosity").getValueAsString()));
					ls.saveToOntology(concept.LightSensor, onto);
					break;

				case "Light":

					Lamp la = new Lamp(name,id);
					la.setLocation(location);
					la.setLampState(Boolean.valueOf(o.getBehavior("powered").getValueAsString()));

					la.saveToOntology(concept.Lamp, onto);
					break;
				case "Thermometer":
					Thermometer t = (Thermometer) o;
					t.executeSetTemperature((int) (donneesContexte.get("temp").asDouble() * 10), new Config());
					TemperatureSensor termo = new TemperatureSensor(name,id);
					termo.setLocation(location);
					termo.setAmbTempSensorValue(Double.parseDouble(o.getBehavior("temperature").getValueAsString()));
					termo.saveToOntology(concept.AmbTempSensor, onto);
					break;
				case "Curtain":
					Curtain cu = new Curtain(name,id);
					cu.setLocation(location);
					cu.setCurtainState(Boolean.valueOf(o.getBehavior("powered").getValueAsString()));
					cu.saveToOntology(concept.Curtain, onto);
					break;
				case "Window":
					Window wd = new Window(name,id);
					wd.setLocation(location);
					wd.setWindowState(Boolean.valueOf(o.getBehavior("powered").getValueAsString()));
					wd.saveToOntology(concept.Window, onto);
					break;
				case "AirQualitySensor" :
					
					AirQualitySensor air =(AirQualitySensor) o ;
					air.executeSetAirQuality((int) (donneesContexte.get("air").asDouble() * 10), new Config());
					AirQltySenConcept aq = new AirQltySenConcept(name,id);
					aq.setLocation(location);
					aq.setAirqualitySensorValue(Double.parseDouble(o.getBehavior("airQuality").getValueAsString()));
					aq.saveToOntology(concept.AirQualitySensor, onto);
					break;
				case "AirConditioner" :
					
					AirConditioner sch = (AirConditioner) o ;
					
					Heating h = new Heating(name,id);
					h.setLocation(location);
					h.setHealtingState(Boolean.valueOf(o.getBehavior("powered").getValueAsString()));
					h.saveToOntology(concept.Heating, onto);
				}
			}

		}

	}
	private static URI getBaseURI() {
       return UriBuilder.fromUri("http://localhost:8070/ProjetWeb/service/").build();
   }
	@Override
	protected void onRun() {
		JsonNode donneescontexte = null ;
		JsonNode donnesServices = null ;
		ClientConfig config = new ClientConfig();
		Client client = ClientBuilder.newClient(config);
		HttpAuthenticationFeature feature = HttpAuthenticationFeature.basic("admin"	, "admin");
		WebTarget target = client.target(getBaseURI());
		String donnees = target.
				path("/capteur/donnees").
				request().
				accept(MediaType.APPLICATION_JSON_TYPE	).
				get(String.class).toString();
		String services = target.
				path("/listeService/donnees").
				request().
				accept(MediaType.APPLICATION_JSON_TYPE	).
				get(String.class).toString();
		ObjectMapper mapper = new ObjectMapper();
		try {
			 donneescontexte = mapper.readTree(donnees);
			 donnesServices = mapper.readTree(services);
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		// charger une instance de l'ontologie
		 onto = new MyOntology();
		if( donneescontexte !=null) {
			System.err.println("les donneés des capteur sur la page web :"+donneescontexte);
			System.err.println("les donneés sur les service deployés    :"+donnesServices);
			// peupler l'ontologie avec tous les données du contexte
			this.instanciateOntology(donneescontexte);
			// tester l'ontologie avec les données des services 
			 Test InitServices = new Test(onto, this,donnesServices);
			 
			 System.out.println("********  appuyer sur Entrer pour continuer l'execution   **********");
			Scanner sc = new Scanner(System.in);
			String i= sc.nextLine() ;			
		}							
	}

	@Override
	protected void onStart() {

		LOG.info("SmartHome plugin started");
		this.start();

	}

	@Override
	protected void onStop() {
		LOG.info("SmartHome plugin stopped");
	}

	@Override
	protected void onCommand(Command c) throws IOException, UnableToExecuteException {
		LOG.info("SmartHome plugin receives a command called {} with parameters {}", c.getName(),
				c.getProperties().toString());
	}

	@Override
	protected boolean canExecute(Command c) {
		// don't mind this method for now
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	protected void onEvent(EventTemplate event) {
		if (event instanceof ObjectHasChangedBehavior) {
			// here what you want todo
			LOG.info("evenement", event.getEventName());
		} else if (event instanceof ZoneHasChanged) {
			// here what you want todo

		}
	}


	
}

