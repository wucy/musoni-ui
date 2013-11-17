package com.musoni;

import org.json.JSONObject;

import com.musoni.service.IService;
import com.musoni.service.ResultHandler;
import com.musoni.service.ServiceFactory;

import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.Toast;
import android.widget.TabHost.TabSpec;

public class LoanApplication extends Activity {

	
	TabHost tabHost;
	
	final int __TOT_STEPS = 4;
	
	private void setupSpec(TabSpec tabSpec, TabHost tabHost, int id, String indicator) {
		tabSpec.setContent(id);
		tabSpec.setIndicator(indicator);
		tabHost.addTab(tabSpec);
		
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_loan_application);
		
		BarInflator.inflateActionBar(this);
		
		tabHost=(TabHost)findViewById(R.id.tabhost);
	    tabHost.setup();
	    
	    TabSpec specs1 = tabHost.newTabSpec("tab1");
	    setupSpec(specs1, tabHost, R.id.tab1, "1");
		TabSpec specs2 = tabHost.newTabSpec("2");
	    setupSpec(specs2, tabHost, R.id.tab2, "2");
	    TabSpec specs3 = tabHost.newTabSpec("3");
	    setupSpec(specs3, tabHost, R.id.tab3, "3");
	    TabSpec specs4 = tabHost.newTabSpec("4");
	    setupSpec(specs4, tabHost, R.id.tab4, "4");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.loan_application, menu);
		return true;
	}
	
	  public void submitClientRegistry(View view) {
		  
		  String loanSize = ((EditText)this.findViewById(R.id.loansize)).getText().toString();
		  String loanTerm = (String) ((Spinner)findViewById(R.id.loanTerm)).getSelectedItem();
		  String repayment = (String) ((Spinner)findViewById(R.id.repaymentEvery)).getSelectedItem();
		  String interestrate = (String) ((Spinner)findViewById(R.id.interestRate)).getSelectedItem();

		  String branchName = (String) ((Spinner)findViewById(R.id.branchnamespinner)).getSelectedItem();
		  String firstName = ((EditText) this.findViewById(R.id.firstname)).getText().toString();
		  String lastName = ((EditText)this.findViewById(R.id.familyname)).getText().toString();
		  String groupName = ((EditText)this.findViewById(R.id.groupname)).getText().toString();
		  String loanOfficer = ((EditText)this.findViewById(R.id.loanofficer)).getText().toString();
		//  String marital = (String) ((Spinner)findViewById(R.id.marital_status)).getSelectedItem();
		  JSONObject json = new JSONObject();
		  
//		  try {
//			  json.put("firstname", firstName);
//			  json.put("lastname", lastName);
//			  json.put("locale", "en");
//			  json.put("active", true);
//			  json.put("activationDate", "04 March 2009");
//		  	  json.put("dateFormat", "dd MMMM yyyy");
//		  	  json.put("officerId", 1);
//		  } catch(Exception e) {
//			  e.printStackTrace();
//			  Toast.makeText(getApplicationContext(), "Submit Fail", Toast.LENGTH_LONG).show();
//		  }
//		  IService service = ServiceFactory.getService();
//		  Toast.makeText(this, "Click", Toast.LENGTH_LONG).show();
//		  service.registerClient(json, new ResultHandler() {
//
//			@Override
//			public void success() {
//				// TODO Auto-generated method stub
//				Toast.makeText(getApplication(), "sucess", Toast.LENGTH_LONG).show();
//			}
//
//			@Override
//			public void fail() {
//				// TODO Auto-generated method stub
//				Toast.makeText(getApplication(), "fail "+ this.getReason(), Toast.LENGTH_LONG).show();
//			}
//
//			@Override
//			public void timeout() {
//				// TODO Auto-generated method stub
//				
//			}
			  
//		  });
		  
		 // Toast.makeText(this, branchName, Toast.LENGTH_LONG).show();
	  }


}
