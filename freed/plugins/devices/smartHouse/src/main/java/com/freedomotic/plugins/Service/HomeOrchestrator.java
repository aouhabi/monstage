package com.freedomotic.plugins.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.freedomotic.plugins.devices.smartHouse.SmartHouse;
import com.freedomotic.plugins.devices.smartHouse.application.MyOntology;
import com.freedomotic.things.EnvObjectLogic;

public class HomeOrchestrator extends Service {

	private List<Orchestrator> listOrchestre = new ArrayList<Orchestrator>();
	private List<Service> listeService = new ArrayList<Service>();

	public HomeOrchestrator(String name,String id) {
		super(name,id);
		// TODO Auto-generated constructor stub
	}
	public void addOrchestre(Orchestrator o) {
		this.listOrchestre.add(o);
	}
	public void executeServices(SmartHouse s, MyOntology onto) {
		// pour trié les orchestrateur; le premier de la liste est plus prioritaire
		Collections.sort(listOrchestre, new Comparator<Orchestrator>() {
	        public int compare(Orchestrator s2, Orchestrator s1)
	        {
	            return  s2.getPriorite().compareTo(s1.getPriorite());
	        }
	    });
		for (Orchestrator orchestrator : this.listOrchestre) {
			this.listeService.addAll(orchestrator.getListService());
			orchestrator.orchestrate(s, onto);
		}
		// une fois sortie de la boucle on libere tous les verrous 
		this.releaseLock(this.listeService, s);
	}
	private void releaseLock(List<Service> listService, SmartHouse s) {
		for (Service service : listService) {
			if (service.getIdActuator() != "") {
				EnvObjectLogic obj = s.getApi().things().findOne(service.getIdActuator());
				obj.setLock(false);
			}
		}
	}
}
