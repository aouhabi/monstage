package com.freedomotic.plugins.devices.smartHouse;

import java.util.Scanner;

import com.fasterxml.jackson.databind.JsonNode;
import com.freedomotic.plugins.Service.ElementaryService;
import com.freedomotic.plugins.Service.HomeOrchestrator;
import com.freedomotic.plugins.Service.Orchestrator;
import com.freedomotic.plugins.concepts.Concept.concept;
import com.freedomotic.plugins.concepts.Location.location;
import com.freedomotic.plugins.devices.smartHouse.application.MyOntology;

public class Test {

	public Test(MyOntology onto, SmartHouse s, JsonNode donnesServices) {
		//on instancie un orchestrateur de groupe objectif
		HomeOrchestrator homeOrchestrator =new  HomeOrchestrator("homeOrchestrator","id");	
		/*
		 * groupe objectif "service lumiere"
		 */
		if(donnesServices.get("servicelumiere") != null) {
			Orchestrator serviceLumiere = new Orchestrator("serviceLumiere","sl",donnesServices.get("servicelumiere").get("priorite").asInt());
			serviceLumiere.setExecutionContext(location.Bedroom);
				//service elementaire "sln" 
				if(donnesServices.get("servicelumiere").get("sln") != null) {
					ElementaryService sln = new ElementaryService("serviceLumierN","sln",donnesServices.get("servicelumiere").get("sln").asInt());
					sln.setExecutionContext(location.Bedroom);
					sln.setTargetObject(concept.Curtain);
					sln.saveToOntology(concept.BrightnessControlService, onto);
					sln.setRulePath("sln.txt");
					// on ajoute "sln" au groupe objectif pour y etre orchestrer
					serviceLumiere.addServiceToOrchestrate(sln);
				}
				//service elementaire "sla" 
				if(donnesServices.get("servicelumiere").get("sla") != null) {
					ElementaryService sla = new ElementaryService("serviceLumierA","sla",donnesServices.get("servicelumiere").get("sla").asInt());
					sla.setExecutionContext(location.Bedroom);
					sla.setExecutionContext(location.Kitchen);
					sla.setTargetObject(concept.Lamp);
					sla.saveToOntology(concept.BrightnessControlService, onto);
					sla.setRulePath("sla.txt");
					// on ajoute "sla" au groupe objectif pour y etre orchestrer
					serviceLumiere.addServiceToOrchestrate(sla);
				}
				// on ajoute le groupe objectif "servicelumiere" pour y etre orchestrer par homeOrchestrator
				homeOrchestrator.addOrchestre(serviceLumiere);
		}
		
		if(donnesServices.get("serviceCanicule") != null) {
			Orchestrator serviceCanicule = new Orchestrator("servicecanicule","sc",donnesServices.get("serviceCanicule").get("priorite").asInt());
			serviceCanicule.setExecutionContext(location.Bedroom);
			
			if(donnesServices.get("serviceCanicule").get("sca") != null) {
				ElementaryService sca = new ElementaryService("serviceCanicule","sca",donnesServices.get("serviceCanicule").get("sca").asInt());
				sca.setExecutionContext(location.Bedroom);
				sca.setTargetObject(concept.Curtain);
				sca.saveToOntology(concept.TemperatureControlService, onto);
				sca.setRulePath("sca.txt");
				
				serviceCanicule.addServiceToOrchestrate(sca);
			}
			if(donnesServices.get("serviceCanicule").get("sch") != null) {
				ElementaryService sch = new ElementaryService("serviceChauffage","sch",donnesServices.get("serviceCanicule").get("sch").asInt());
				sch.setExecutionContext(location.Bedroom);
				sch.setTargetObject(concept.Heating);
				sch.saveToOntology(concept.TemperatureControlService, onto);
				sch.setRulePath("sch.txt");
				
				serviceCanicule.addServiceToOrchestrate(sch);
				
			}
			homeOrchestrator.addOrchestre(serviceCanicule);
		}
		
		if(donnesServices.get("serviceEconomie") != null) {
			Orchestrator serviceEconomie = new Orchestrator("service economie","se",donnesServices.get("serviceEconomie").get("priorite").asInt());
			serviceEconomie.setExecutionContext(location.Bedroom);
			if(donnesServices.get("serviceEconomie").get("sle1") != null) {
				ElementaryService sle1 = new ElementaryService("sle1","sel1",donnesServices.get("serviceEconomie").get("sle1").asInt());
				sle1.setExecutionContext(location.Bedroom);
				sle1.setTargetObject(concept.Lamp);
				sle1.setRulePath("sel1.txt");
				sle1.saveToOntology(concept.BrightnessControlService, onto);
				
				serviceEconomie.addServiceToOrchestrate(sle1);
			}
			if(donnesServices.get("serviceEconomie").get("sech") != null) {
				ElementaryService sech = new ElementaryService("sech","sech",donnesServices.get("serviceEconomie").get("sech").asInt());
				sech.setExecutionContext(location.Bedroom);
				sech.setTargetObject(concept.Heating);
				sech.setRulePath("sech.txt");
				sech.saveToOntology(concept.TemperatureControlService, onto);
				
				serviceEconomie.addServiceToOrchestrate(sech);
			}
			
			homeOrchestrator.addOrchestre(serviceEconomie);
		}
		
		if(donnesServices.get("serviceAire") != null) {
			Orchestrator serviceAir = new Orchestrator("serviceAir", "aire", donnesServices.get("serviceAire").get("priorite").asInt());
			serviceAir.setExecutionContext(location.Bedroom);
			if(donnesServices.get("serviceAire").get("sqa") != null) {
				ElementaryService sqa = new ElementaryService("sqa", "idd",  donnesServices.get("serviceAire").get("sqa").asInt());
				sqa.setExecutionContext(location.Bedroom);
				sqa.setTargetObject(concept.Window);
				sqa.setRulePath("sqa.txt");
				sqa.saveToOntology(concept.AirQualityControlService, onto);
				
				serviceAir.addServiceToOrchestrate(sqa);
			}
			
			homeOrchestrator.addOrchestre(serviceAir);
		}
		
		if(donnesServices.get("servicesecu") != null) {
			Orchestrator serviceSecu = new Orchestrator("serviceSecu", "dddd", donnesServices.get("servicesecu").get("priorite").asInt());
			serviceSecu.setExecutionContext(location.Bedroom);
			ElementaryService ss = new  ElementaryService("sec", "1", 1);
			ss.setExecutionContext(location.Bedroom);
			ss.setTargetObject(concept.Window);
			ss.setRulePath("sec.txt");
			ss.saveToOntology(concept.SecurityControlService, onto);
			
			serviceSecu.addServiceToOrchestrate(ss);
			homeOrchestrator.addOrchestre(serviceSecu);	
		}
		
		// appele a la methode executeServices pour orchestrer et executer les services
	    homeOrchestrator.executeServices(s, onto);	
	}	
}
