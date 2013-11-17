package com.musoni;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import java.lang.*;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.musoni.service.IService;
import com.musoni.service.ResultHandler;
import com.musoni.service.ServiceFactory;

public class LogInActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_log_in);
		
		final ActionBar actionBar = getActionBar();
		BarInflator.inflateActionBar(actionBar);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.log_in, menu);
		return true;
	}
	
	public void login(View view){
		EditText fieldUsername = (EditText) findViewById(R.id.UsernameEditText);
		EditText fieldPassword = (EditText) findViewById(R.id.PasswordEditText);
		
		String username = fieldUsername.getText().toString();
		String password = fieldPassword.getText().toString();
		
		//System.out.println("wow");
		final LogInActivity newThis = this;
				
		IService service = ServiceFactory.getService();		
		
		ResultHandler logInResult = new ResultHandler(){
			
		

			@Override
			public void success() {
				Intent intent = new Intent(getApplicationContext(), Welcome.class);
				startActivity(intent);
				
			}

			@Override
			public void fail() {
				TextView warning = (TextView) findViewById(R.id.warning);
				View view = newThis.getWindow().getDecorView();
				view.setBackgroundColor(0xffff0000);
				warning.setText("Couldn't match username and password.");
				
			}

			@Override
			public void timeout() {
				// TODO Auto-generated method stub
				
			}};
		service.authenticate(username, password, logInResult);
		
	}

}
