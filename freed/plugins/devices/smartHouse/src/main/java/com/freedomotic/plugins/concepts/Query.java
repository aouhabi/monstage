package com.freedomotic.plugins.concepts;

import java.util.HashMap;
import java.util.Map;

public abstract class Query {
	
	public static final String header ="PREFIX ns: <http://www.adel.org/adel#>\n" + 
										"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> \n" + 
										"PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" + 
										"PREFIX owl: <http://www.w3.org/2002/07/owl#>\n" + 
										"PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>\n" + 
										"PREFIX tg:<http://www.turnguard.com/functions#>\n";
	
	
	public static final String _select =header+"SELECT  ?concept  ?id ?int \n" + 
			"WHERE {\n" + 
			" ?concept rdf:type ns:Target .\n" + 
			" ?concept ns:hasId ?id.\n" + 
			" ?concept ns:enable ?int. \n	"+
			" ?concept ns:hasIntention ns:service ."
			+ "}";
	
}
