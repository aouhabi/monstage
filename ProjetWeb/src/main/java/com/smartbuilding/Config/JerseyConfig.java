package com.smartbuilding.Config;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

import com.smartbuilding.Service.Service;

@Component
@ApplicationPath("/service")
public class JerseyConfig extends ResourceConfig {
	   public JerseyConfig() {
		register(Service.class);
	   }
	} 