package com.terminalandiaapp.serializedProvinces;

import com.google.gson.annotations.SerializedName;

public class Result {
	@SerializedName("provId")
	public long provId;
	
	@SerializedName("provName")
	public String provName;
}
