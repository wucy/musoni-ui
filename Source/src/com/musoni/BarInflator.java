package com.musoni;

import android.app.ActionBar;
import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.ToggleButton;

import com.musoni.service.ServiceFactory;

public class BarInflator {
	
	private static boolean toggleOn = false;
	public static void inflateActionBar(Activity activity){
		final ActionBar actionBar = activity.getActionBar();
		actionBar.setCustomView(R.layout.actionbar_custom_view_home);
	     actionBar.setDisplayShowTitleEnabled(true);
	     actionBar.setDisplayShowCustomEnabled(true);
	     actionBar.setDisplayUseLogoEnabled(false);
	     actionBar.setDisplayShowHomeEnabled(false);
	     
	     final ToggleButton buttonOne = (ToggleButton) activity.findViewById(R.id.togglebutton);
	     buttonOne.setChecked(toggleOn);
	 		buttonOne.setOnClickListener(new Button.OnClickListener() {
	 	    public void onClick(View v) {
	 	            if(toggleOn){
	 	            	buttonOne.setChecked(false);
	 	            	toggleOn = false;
	 	            	ServiceFactory.getService().forceOffline();
	 	            }
	 	            else{
	 	            	buttonOne.setChecked(true);
	 	            	toggleOn = true;
	 	            	ServiceFactory.getService().forceOnline();
	 	            }
	 	    }
	 	});
	}
}


