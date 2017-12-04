package com.freedomotic.plugins.Service;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import com.freedomotic.plugins.concepts.Concept.concept;
import com.freedomotic.plugins.concepts.ConnectedObject;
import com.freedomotic.plugins.concepts.Query;
import com.freedomotic.plugins.devices.smartHouse.SmartHouse;
import com.freedomotic.plugins.devices.smartHouse.application.MyOntology;
import com.freedomotic.plugins.devices.smartHouse.tools.JenaEngine;
public class Orchestrator extends Service  {
	private List<ElementaryService> listService = new ArrayList<ElementaryService>();
	private int prioritee ;
	public Orchestrator(String name ,String id ,int prioritee) {
		super(name,id);
		this.prioritee =prioritee ;
	}
	public void addServiceToOrchestrate(ElementaryService service) {
		this.addObjectProperie(objectProperties.orchestrate, service.getName());
		listService.add(service);
	}

	public List<ElementaryService> getListService() {
		return listService;
	}
	public String getPriorite() {
		return ""+prioritee;
	}
	public void setPriorite(int prioritee) {
		this.prioritee = prioritee;
	}
	protected void orchestrate(SmartHouse s, MyOntology onto) {
		boolean test = false;
		int i = 0;
		 //trier les service par rapport à la priorité
		Collections.sort(listService, new Comparator<ElementaryService>() {
	        public int compare(ElementaryService s2, ElementaryService s1)
	        {
	            return  s2.getPriorite().compareTo(s1.getPriorite());
	        }
	    });
		// execution des services par rapport à leurs priorités 
		while (test == false && i <= listService.size()-1) {
			test = this.listService.get(i).executeAction(s, onto);
					//pour executer les service si il ont la meme priorité
					 while(((i< listService.size()-1))&& (listService.get(i).getPriorite().equals(listService.get(i+1).getPriorite()))) {
						test=this.listService.get(i+1).executeAction(s, onto) || test;	
						i++;
					}
			i++;	
		}	
	}
}
