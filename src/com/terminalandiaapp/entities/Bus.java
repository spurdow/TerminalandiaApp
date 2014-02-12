package com.terminalandiaapp.entities;

public class Bus extends Vehicle {

	private String type;
	public Bus(long id, String name, String phone, String email, String address) {
		super(id, name, phone, email, address);
		// TODO Auto-generated constructor stub
		type = "Bus";
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	

}
