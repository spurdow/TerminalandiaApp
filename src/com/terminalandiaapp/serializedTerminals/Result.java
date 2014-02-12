package com.terminalandiaapp.serializedTerminals;

import com.google.gson.annotations.SerializedName;

public class Result {
	 @SerializedName("termId")
	 public long id;
	 
	 @SerializedName("termName")
	 public String name;
	 
	 @SerializedName("termType")
	 public String type;
	 
	 @SerializedName("telNo")
	 public String phone;
	 
	 @SerializedName("email")
	 public String email;
	 
	 @SerializedName("addr")
	 public String address;
	 
	 
}
