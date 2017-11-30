package com.smartbuilding;

import java.io.IOException;
import java.net.URI;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import org.glassfish.jersey.client.ClientConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootApplication
public class ProjetWebApplication {

	public static void main(String[] args) throws JsonProcessingException, IOException {
		//SpringApplication.run(ProjetWebApplication.class, args);
		
		
		/**
		 * 
		 * test client rest en locale
		 * 
		 * 
		 */
		// TODO Auto-generated method stub
		ClientConfig config = new ClientConfig();
		Client client = ClientBuilder.newClient(config);
		//HttpAuthenticationFeature feature = HttpAuthenticationFeature.basic("admin"	, "admin");

	
		
		WebTarget target = client.target(getBaseURI());
		
		
		String response1 = target.
				path("/listeService/donnees").
				request().
				accept(MediaType.APPLICATION_JSON_TYPE	).
				get(String.class).toString();
		String response = target.
				path("/capteur/donnees").
				request().
				accept(MediaType.APPLICATION_JSON_TYPE	).
				get(String.class).toString();
		ObjectMapper mapper = new ObjectMapper();
		JsonNode obj = mapper.readTree(response1);
		JsonNode obj1 = mapper.readTree(response);
		System.out.println(obj1);
		System.out.println(obj);


	}
	private static URI getBaseURI() {
        return UriBuilder.fromUri("http://localhost:8070/ProjetWeb/service/").build();
    }
	
}
