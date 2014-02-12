package com.terminalandiaapp.commons;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

public class ConnectionDetector {

	
	private static final String TAG = "Connection Detector";

	public static boolean isConnectedToInternet(Context context){
	    boolean haveConnectedWifi = false;
	    boolean haveConnectedMobile = false;

	    ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo[] netInfo = cm.getAllNetworkInfo();
	    for (NetworkInfo ni : netInfo) {
	        if (ni.getTypeName().equalsIgnoreCase("WIFI"))
	            if (ni.isConnected())
	                haveConnectedWifi = true;
	        if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
	            if (ni.isConnected())
	                haveConnectedMobile = true;
	        
	        if(ni.isFailover()){
	        	return false;
	        }
	        if(!ni.isAvailable()){
	        	return false;
	        }
	        if(ni.isConnectedOrConnecting()){
	        	return false;
	        }
	        Log.d(TAG, ni.toString() + "");
	    }
	    return haveConnectedWifi || haveConnectedMobile;
	}
}
