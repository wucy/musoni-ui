package com.musoni;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.EditText;

import com.musoni.service.IService;
import com.musoni.service.ResultHandler;
import com.musoni.service.ServiceFactory;

public class LogInActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_log_in);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.log_in, menu);
		return true;
	}
	
	public void login(){
		EditText fieldUsername = (EditText) findViewById(R.id.UsernameEditText);
		EditText fieldPassword = (EditText) findViewById(R.id.PasswordEditText);
		
		String username = fieldUsername.getText().toString();
		String password = fieldPassword.getText().toString();
				
		IService service = ServiceFactory.getService();
		
		ResultHandler logInResult = new ResultHandler(){
			
		

			@Override
			public void success() {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void fail() {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void timeout() {
				// TODO Auto-generated method stub
				
			}};
		service.authenticate(username, password, logInResult);
		
	}

}
