package com.freedomotic.plugins.Service;

public  class ElementaryService extends Service {

	private int prioritee ;
	
	public ElementaryService(String name,String id ,int prioritee) {
		super(name,id);
		this.prioritee = prioritee;
	}
	public String getPriorite() {
		return ""+prioritee;
	}
	public void setPriorite(int prioritee) {
		this.prioritee = prioritee;
	}
}
