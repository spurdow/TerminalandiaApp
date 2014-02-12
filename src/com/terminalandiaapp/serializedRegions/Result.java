package com.terminalandiaapp.serializedRegions;

import com.google.gson.annotations.SerializedName;



public class Result {
	@SerializedName("regNo")
	public long regNo;
	
	@SerializedName("regName")
	public String regName;
}
