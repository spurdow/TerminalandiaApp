package com.example.terminalandiaapp;

import java.util.ArrayList;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import com.google.gson.Gson;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

public class SearchForm extends Activity{
	
	private final String NAMESPACE = "http://tempuri.org/";
	private final String URL = "http://192.168.1.105:8095/Service.asmx";
	private final String SOAP_ACTION = "http://tempuri.org/";
	private String TAG = "PGGURU";
	private static String responseJSON;
	Spinner region;
	Spinner province;
	ProgressBar pg;
	String[] placelist;
	Gson gson = new Gson();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout1);
		//City Spinner control
				province = (Spinner) findViewById(R.id.spnProv);
				//Country Spinner control
				region = (Spinner) findViewById(R.id.spnReg);
				//Progress bar to be displayed until app gets web service response
				//pg = (ProgressBar) findViewById(R.id.progressBar1);
				//AysnTask class to handle Country WS call as separate UI Thread
				AsyncCountryWSCall task = new AsyncCountryWSCall();
				//Execute the task to set Country list in Country Spinner Control
				task.execute();
				
				region.setOnItemSelectedListener(new OnItemSelectedListener() {
					//When an item is selected from Country Spinner Control
					public void onItemSelected(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						// TODO Auto-generated method stub
						//Get the selected  item value
						String floor = region.getSelectedItem().toString();
						//AsynTask class to handle City WS call as separate UI Thread
						AsyncCityWSCall task = new AsyncCityWSCall();
						//Execute the task
						task.execute(floor);
					}

					public void onNothingSelected(AdapterView<?> arg0) {
						// TODO Auto-generated method stub
					}
				});
			}

	private class AsyncCountryWSCall extends AsyncTask<String, Void, Void> {
		@Override
		protected Void doInBackground(String... params) {
			Log.i(TAG, "doInBackground");
			//Invoke web method 'PopulateCountries' with dummy value
			invokeJSONWS("dummy","PopulateCountries");
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			Log.i(TAG, "onPostExecute");
			//Convert 'Countries' JSON response into String array using fromJSON method
			placelist = gson.fromJson(responseJSON, String[].class);
			//Assign the String array as Country Spinner Control's items
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),
					android.R.layout.simple_dropdown_item_1line, placelist);
			region.setAdapter(adapter);
			//Make the progress bar invisible
			if(pg != null)
				pg.setVisibility(View.INVISIBLE);
		}

		@Override
		protected void onPreExecute() {
			Log.i(TAG, "onPreExecute");
			//Display progress bar
			if(pg != null)
				pg.setVisibility(View.VISIBLE);
		}

		@Override
		protected void onProgressUpdate(Void... values) {
			Log.i(TAG, "onProgressUpdate");
		}

	}

	private class AsyncCityWSCall extends AsyncTask<String, Void, Void> {
		@Override
		protected Void doInBackground(String... params) {
			Log.i(TAG, "doInBackground");
			//Invoke web method 'PopulateCities'
			invokeJSONWS(params[0],"PopulateCities");
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			Log.i(TAG, "onPostExecute");
			//Convert 'Cities' JSON response into String array using fromJSON method
			placelist = gson.fromJson(responseJSON, String[].class);	
			//Assign the String array as Country Spinner Control's items
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),
					android.R.layout.simple_dropdown_item_1line, placelist);
			province.setAdapter(adapter);
			//Make the progress bar invisbile
			if(pg != null)
				pg.setVisibility(View.INVISIBLE);
		}

		@Override
		protected void onPreExecute() {
			Log.i(TAG, "onPreExecute");
			//Display the progress bar
			if( pg != null)
				pg.setVisibility(View.VISIBLE);
		}

		@Override
		protected void onProgressUpdate(Void... values) {
			Log.i(TAG, "onProgressUpdate");
			
		}

	}
	
	//Method which invoke web methods
	public void invokeJSONWS(String country, String methName) {
		// Create request
		SoapObject request = new SoapObject(NAMESPACE, methName);
		// Property which holds input parameters
		PropertyInfo paramPI = new PropertyInfo();
		// Set Name
		paramPI.setName("country");
		// Set Value
		paramPI.setValue(country);
		// Set dataType
		paramPI.setType(String.class);
		// Add the property to request object
		request.addProperty(paramPI);
		// Create envelope
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.dotNet = true;
		// Set output SOAP object
		envelope.setOutputSoapObject(request);
		// Create HTTP call object
		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

		//hello
		try {
			// Invole web service
			androidHttpTransport.call(SOAP_ACTION+methName, envelope);
			// Get the response
			SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
			// Assign it to static variable
			responseJSON = response.toString();

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Log.d(TAG, responseJSON + " response");
	}

	
}
