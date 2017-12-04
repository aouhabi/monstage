package com.freedomotic.plugins.devices.smartHouse.application;

import java.util.List;
import java.util.Map;

import com.freedomotic.model.ds.Config;
import com.freedomotic.plugins.concepts.Concept;
import com.freedomotic.plugins.concepts.Concept.concept;
import com.freedomotic.plugins.concepts.Concept.targetState;
import com.freedomotic.plugins.concepts.Interface;
import com.freedomotic.plugins.concepts.Query;
import com.freedomotic.plugins.devices.smartHouse.SmartHouse;
import com.freedomotic.plugins.devices.smartHouse.tools.JenaEngine;
import com.freedomotic.things.EnvObjectLogic;
import com.freedomotic.things.impl.Curtain;
import com.freedomotic.things.impl.Light;
import com.hp.hpl.jena.rdf.model.InfModel;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.reasoner.Reasoner;
import com.hp.hpl.jena.reasoner.ReasonerRegistry;

public class MyOntology implements Interface {

	public static final String ns = "http://www.adel.org/adel#";
	public static String inputDataOntology = "data/ontologie/freedo.owl";// "data/freedo.owl";
	public static String inputRule = "data/ontologie/";// chemin pour la racine des fichier pour les regles";
	private Model model = JenaEngine.readModel(inputDataOntology);
	Reasoner reasoner = ReasonerRegistry.getOWLReasoner();
	Reasoner i = reasoner.bindSchema(model);
	InfModel l = ModelFactory.createInfModel(i, model);
	private  Model inferedModel ;;
	

	
	
	
	// on passe en paramettre le chemin d'un fichier qui posséde des regles pour avoir en sortie notre modele avec les inference 
	public Model getInferedModel(String path) {
		return this.inferedModel = JenaEngine.readInferencedModelFromRuleFile(l, inputRule+path);
	}


	@Override
	public void createObjectRelation(Concept c) {
		for (Map.Entry propertie : c.getObjectProperties().entrySet()) {
			List<String> listValue = (List<String>) propertie.getValue();
			for (String value : listValue) {
				JenaEngine.addValueOfObjectProperty(model, ns, c.getName(), propertie.getKey().toString(),
						value);
			}
		}
	}

	@Override
	public void createObjectInstance(concept cls, Concept c) {
		JenaEngine.createInstanceOfClass(model, ns, cls.toString(), c.getName());
		for (Map.Entry propertie : c.getDataProperties().entrySet()) {
			List<Object> listValue = (List<Object>) propertie.getValue();
			for (Object value : listValue) {
				JenaEngine.addValueOfDataTypeProperty(model, ns, c.getName(), propertie.getKey().toString(),
						value);

			}
		}

	}

}
