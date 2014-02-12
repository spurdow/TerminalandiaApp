package com.terminalandiaapp.commons;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;

public class Alert {
	public static int ERROR = 0x0;
	public static int SUCCESS = 0x01;
	
	
	public static void error(Context context , String title, String content){
		message(context, title,content , ERROR);
	}
	
	public static void success(Context context, String title, String content){
		message(context, title , content , SUCCESS);
	}
	
	public static void message(Context context , String title , String content , int type ){
		int icon = -1;
		if(type == ERROR){
			icon = android.R.drawable.ic_delete;
		}else if(type == SUCCESS){
			icon = android.R.drawable.ic_dialog_alert;
		}
		
		AlertDialog.Builder builder = new AlertDialog.Builder(context)
		.setTitle(title)
		.setMessage(content)
		.setIcon(icon)
		.setPositiveButton("Ok", new OnClickListener(){

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				dialog.dismiss();
			}
			
		});
		AlertDialog alert = builder.create();
		alert.show();
		
	}
}
