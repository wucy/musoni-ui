package com.musoni;

import com.musoni.service.ServiceFactory;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	protected static final String PIN = "0000";
	protected static final String MASTER_PIN = "1234567890";
	protected static final int maxAttempts = 3;
	int numberOfFailedAttempts = 0;

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
		
		if(enteredPin.equals(MASTER_PIN))
		{
			numberOfFailedAttempts = 0;
			Toast.makeText(getApplicationContext(), "You have correctly entered the master pin!", Toast.LENGTH_LONG).show();
			enteredPin = PIN;
		}
		
		//checking if the PIN matches
		if(numberOfFailedAttempts<maxAttempts && enteredPin.equals(PIN)){
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
			numberOfFailedAttempts++;
			if(numberOfFailedAttempts>=maxAttempts)
				Toast.makeText(getApplicationContext(), "You have entered the wrong pin too many times! You have to enter the master password to unlock!", Toast.LENGTH_LONG).show();
			editText.setText("");
		}
	}
	
}
