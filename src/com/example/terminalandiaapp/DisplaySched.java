package com.example.terminalandiaapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;
import static com.terminalandiaapp.commons.Util.*;
public class DisplaySched extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		WebView webview = new WebView(this);
		 // Let's display the progress in the activity title bar, like the
		 // browser app does.
		 getWindow().requestFeature(Window.FEATURE_PROGRESS);
		this.setContentView(webview);
		
		Bundle extras = getIntent().getExtras();
		
		long termId = extras.getLong("termId");
		String type = extras.getString("type");
		
		 webview.getSettings().setJavaScriptEnabled(true);

		 final Activity activity = this;
		 webview.setWebChromeClient(new WebChromeClient() {
		   public void onProgressChanged(WebView view, int progress) {
		     // Activities and WebViews measure progress with different scales.
		     // The progress meter will automatically disappear when we reach 100%
		     activity.setProgress(progress * 1000);
		   }
		 });
		 webview.setWebViewClient(new WebViewClient() {
		   public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
		     Toast.makeText(activity, "Oh no! " + errorCode + " " + description, Toast.LENGTH_SHORT).show();
		   }
		 });

		 webview.loadUrl(URL_SCHEDULE + "termId=" + termId + "&&type="+type);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		return super.onCreateOptionsMenu(menu);
	}
	
	

}
