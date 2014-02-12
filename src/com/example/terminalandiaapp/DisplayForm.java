package com.example.terminalandiaapp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;


import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import com.google.gson.Gson;
import com.terminalandiaapp.adapters.TerminalAdapter;
import com.terminalandiaapp.entities.Airplane;
import com.terminalandiaapp.entities.Bus;
import com.terminalandiaapp.entities.Vehicle;
import com.terminalandiaapp.entities.Vessel;
import com.terminalandiaapp.serializedTerminals.Response;
import com.terminalandiaapp.serializedTerminals.Result;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.ProgressBar;

import static com.terminalandiaapp.commons.Util.*;

public class DisplayForm extends Activity {
	
	private static final String TAG = "DisplayForm";
	ListView list;
	private String responseJSON = null;
	private Gson gson = new Gson();
	private ProgressBar pb;
	private TerminalAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout2);
		
		list = (ListView) this.findViewById(R.id.listTerminals);
		
		Bundle extras = this.getIntent().getExtras();
		
		adapter = new TerminalAdapter(this , new ArrayList<Vehicle>());

		pb = (ProgressBar) findViewById(R.id.progressBar1);		
		list.setAdapter(adapter);
		
		list.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Vehicle v = (Vehicle) adapter.getList().get(arg2);
				String type = null;
				if(v instanceof Vessel){
					type = ((Vessel)v).getType();
				}else if(v instanceof Bus){
					type = ((Bus)v).getType();
				}else if(v instanceof Airplane){
					type = ((Airplane)v).getType();
				}
				
				Intent i = new Intent(DisplayForm.this , DisplaySched.class);
				i.putExtra("termId", v.getId());
				i.putExtra("type", type);
				startActivity(i);
			}
			
		});
		/* if we pass data onto this activity, then that means 
		 * we have to search and filter it using the data passed
		 * otherwise we just search all of them
		*/
		if(extras!= null && extras.containsKey("vehicle_type")){
			HashMap<String , String> map = new HashMap<String, String>();
			
		}else{
			HashMap<String , String> map = new HashMap<String , String>();
			map.put("dummy_key", "dummy_value");
			new AsyncTerminalCall(map).execute("PopulateTerminals");
		}
		
/*		HashMap<String , String> map = new HashMap<String , String>();
		map.put("test_key_1", "test_value_1");
		map.put("test_key_2", "test_value_2");
		map.put("test_key_3", "test_value_3");
		map.put("test_key_4", "test_value_4");
		
		invokeJSONWS(map , "ok");*/
		
	}
	
	
	
	private class AsyncTerminalCall extends AsyncTask<String , Void , Void>{

		private HashMap<String , String> map;
		public AsyncTerminalCall(HashMap<String , String> map){
			this.map = map;
		}
		
		@Override
		protected Void doInBackground(String... params) {
			// TODO Auto-generated method stub
			invokeJSONWS(map , params[0]);
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			Response response = gson.fromJson(responseJSON , Response.class);
			List<Result> results = response.results;
			
			for(Result r : results){
				Vehicle vehicle = null;
				if(r.type.equals("Vessel")){
					vehicle = new Vessel(r.id , r.name , r.phone, r.email , r.address);
				}else if(r.type.equals("Bus")){
					vehicle = new Bus(r.id, r.name , r.phone , r.email , r.address);
				}else if(r.type.equals("Airplane")){
					vehicle = new Airplane(r.id , r.name , r.phone , r.email, r.address);
				}
				
				adapter.add(vehicle);
			}
			pb.setVisibility(View.GONE);
			Log.d(TAG, adapter.getAll().size() + " = size");
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
		}
		
		
		
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		getMenuInflater().inflate(R.menu.search, menu);
		return super.onCreateOptionsMenu(menu);
	}
	
	
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch(item.getItemId()){
		case R.id.search: 
				Intent i = new Intent(this, SearchForm.class);
				startActivity(i);
			break;
		}
		return super.onOptionsItemSelected(item);
	}



	private void invokeJSONWS(HashMap<String, String> values , String methName){
		// Create request
		SoapObject request = new SoapObject(NAMESPACE, methName);
		// Property which holds input parameters
		PropertyInfo paramPI = null;
				
		for( Entry<String , String > entry : values.entrySet()){	
			
			Log.d(TAG, entry.getKey() + " " + entry.getValue());
			
			paramPI = new PropertyInfo();
			// Set Name
			paramPI.setName(entry.getKey());
			// Set Value
			paramPI.setValue(entry.getValue());
			// Set dataType
			paramPI.setType(String.class);
			// Add the property to request object
			request.addProperty(paramPI);
		}
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
