package com.musoni;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.musoni.service.ResultHandler;
import com.musoni.service.ServiceFactory;

public class BarInflator {
	
	private static boolean pinOn = false;
	public static boolean getPinOn(){return pinOn;}
	public static void setPinOn(boolean b){pinOn = b;}
	private static boolean toggleOn = ServiceFactory.getService().isActive();
	private static boolean loggedOn = ServiceFactory.getService().isUserLoggedIn();
	public static void logOn(boolean b){loggedOn = b;}
	public static void inflateActionBar(final Activity activity){
		final ActionBar actionBar = activity.getActionBar();
		actionBar.setCustomView(R.layout.actionbar_custom_view_home);
	     actionBar.setDisplayShowTitleEnabled(true);
	     actionBar.setDisplayShowCustomEnabled(true);
	     actionBar.setDisplayUseLogoEnabled(false);
	     actionBar.setDisplayShowHomeEnabled(false);
	     
	     final ToggleButton buttonOne = (ToggleButton) activity.findViewById(R.id.togglebutton);
	     toggleOn = ServiceFactory.getService().isActive();
	     buttonOne.setChecked(toggleOn);
	 		buttonOne.setOnClickListener(new Button.OnClickListener() {
	 	    public void onClick(View v) {
	 	            if(toggleOn){
	 	            	buttonOne.setChecked(false);
	 	            	toggleOn = false;
	 	            	ServiceFactory.getService().forceOffline();
	 	            	Toast.makeText(activity, "You have gone offline, all changes will be made when you get online!", Toast.LENGTH_LONG).show();
	 	            }
	 	            else{
	 	            	buttonOne.setChecked(true);
	 	            	toggleOn = true;
	 	            	ServiceFactory.getService().forceOnline();
	 	            	Toast.makeText(activity, "You are online now, to submit your offline data, you need to submit something!", Toast.LENGTH_LONG).show();
	 	            }
	 	    }
	 	});
	 		
	 		final ToggleButton buttonTwo = (ToggleButton) activity.findViewById(R.id.buttonLoggedIn);
	 		loggedOn = ServiceFactory.getService().isUserLoggedIn();   //Update just in case it's different
		     buttonTwo.setChecked(loggedOn);
		 		buttonTwo.setOnClickListener(new Button.OnClickListener() {
				public void onClick(View v) {
					
					 buttonTwo.setChecked(loggedOn);
		 	            if(loggedOn){
		 	            	AlertDialog.Builder builder = new AlertDialog.Builder(activity);
		 	            
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
									buttonTwo.setChecked(false);
									 
									Intent intent = new Intent(activity,LogInActivity.class);
					 	            activity.startActivity(intent);
								}
							});
		 	           	AlertDialog dialog = builder.create();
	 	            	dialog.setMessage("Are you sure you want to log out? You cannot log back in if you're offline!");
		 	            	dialog.show();
		 	            }
		 	            else{
		 	            	if(pinOn){
		 	            		buttonTwo.setChecked(false);
		 	            	Intent intent = new Intent(activity,LogInActivity.class);
		 	            	activity.startActivity(intent);
		 	            	}
		 	            	else{
		 	            		//buttonTwo.setChecked(false);
		 	            	}
		 	            }
		 	    }
		 	});
	
	
	final Button buttonThree = (Button) activity.findViewById(R.id.synchronizeButton);
	buttonThree.setOnClickListener(new Button.OnClickListener() {
		public void onClick(View v) {
			ServiceFactory.getService().sendOfflineData(new ResultHandler(){

				@Override
				public void success() {
					Toast.makeText(activity, "You have successfully submited your offline data.", Toast.LENGTH_LONG).show();
				}

				@Override
				public void fail() {
					Toast.makeText(activity, "You are still offline or couldn't submit your data for another reason.", Toast.LENGTH_LONG).show();
					
				}

				@Override
				public void timeout() {
					// TODO Auto-generated method stub
					
				}
				
			});
		}
	});
	}
}

