package com.musoni;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
	
	public void login(View view){
		EditText fieldUsername = (EditText) findViewById(R.id.UsernameEditText);
		EditText fieldPassword = (EditText) findViewById(R.id.PasswordEditText);
		
		String username = fieldUsername.getText().toString();
		String password = fieldPassword.getText().toString();
		
		//System.out.println("wow");
		final LogInActivity newThis = this;
				
		IService service = ServiceFactory.getService();
		
		//Intent intent = new Intent(this, Welcome.class);
		//startActivity(intent);
		
		
		ResultHandler logInResult = new ResultHandler(){
			
		

			@Override
			public void success() {
				Intent intent = new Intent(getApplicationContext(), Welcome.class);
				startActivity(intent);
				
			}

			@Override
			public void fail() {
				//Toast.makeText(getApplicationContext(), "failed", Toast.LENGTH_LONG).show();
				TextView warning = new TextView(getApplicationContext());
				warning.setText("@string/warningMessage");
				
			}

			@Override
			public void timeout() {
				// TODO Auto-generated method stub
				
			}};
		service.authenticate(username, password, logInResult);
		
	}

}
