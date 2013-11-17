package com.musoni;

import com.musoni.service.ServiceFactory;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends Activity {
	
	protected static String PIN = "0000";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		BarInflator.inflateActionBar(this);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void pinEnter(View view)
	{
		//Refering string entered into PIN editText view.
		EditText editText = (EditText) findViewById(R.id.editTextPin);
		
		//Extracting the PIN to the String object
		String enteredPin = (editText.getText().toString());
		
		//checking if the PIN matches
		if(enteredPin == PIN){
			BarInflator.setPinOn(true);
			//checking if the user is logged on
			if(!ServiceFactory.getService().isUserLoggedIn()){
			Intent intent = new Intent(this, LogInActivity.class);
			startActivity(intent);
			
			}
			else{
				Intent intent = new Intent(this, Welcome.class);
				startActivity(intent);
			}
		}
		else{
			editText.setText("");
		}
	}
	
}
