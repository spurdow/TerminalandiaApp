package com.terminalandiaapp.entities;

public class Vessel extends Vehicle {
	
	private String type;
	public Vessel(long id, String name, String phone, String email,
			String address) {
		super(id, name, phone, email, address);
		// TODO Auto-generated constructor stub
		type = "Vessel";
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	

}
