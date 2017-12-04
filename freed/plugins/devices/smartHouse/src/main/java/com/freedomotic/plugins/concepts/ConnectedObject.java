package com.freedomotic.plugins.concepts;

import com.freedomotic.plugins.concepts.Location.location;

public abstract class ConnectedObject extends Concept {

	private String location;

	public ConnectedObject(String name, String id) {
		super(name,id);
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
		this.addObjectProperie(objectProperties.hasLocation, location);
	}

}
