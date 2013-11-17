package com.musoni;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ToggleButton;

import com.musoni.service.ServiceFactory;

public class BarInflator {
	
	private static boolean toggleOn = false;
	private static boolean loggedOn = false;
	public static void inflateActionBar(final Activity activity){
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
	 		
	 		final ToggleButton buttonTwo = (ToggleButton) activity.findViewById(R.id.buttonLoggedIn);
		     buttonTwo.setChecked(loggedOn);
		 		buttonTwo.setOnClickListener(new Button.OnClickListener() {
				public void onClick(View v) {
		 	            if(loggedOn){
		 	            	AlertDialog.Builder builder = new AlertDialog.Builder(activity);
		 	            	AlertDialog dialog = builder.create();
		 	            	dialog.setMessage("Are you sure you want to log out? You cannot log back in if you're offline!");
		 	            	builder.setNegativeButton("No",new DialogInterface.OnClickListener() {
		 	            		
								@Override
								public void onClick(DialogInterface dialog, int which) {
									dialog.dismiss();
								}
							});
		 	            	builder.setPositiveButton("Yes",new DialogInterface.OnClickListener() {
								
								@Override
								public void onClick(DialogInterface dialog, int which) {
									ServiceFactory.getService().logoff();
									
								}
							});
		 	            }
		 	            else{
		 	            	Intent intent = new Intent(activity,LogInActivity.class);
		 	            	activity.startActivity(intent);
		 	            }
		 	    }
		 	});
	}
}


