package com.musoni;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import com.musoni.service.IService;
import com.musoni.service.ResultHandler;
import com.musoni.service.ServiceFactory;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.Toast;

public class ClientRegActivity extends Activity {

	
	TabHost tabHost;
	
	final int __TOT_STEPS = 6;
	
	private RadioGroup radioSexGroup;
	private RadioButton radioClientTypeButton;
	private RadioGroup radioTypeGroup;
	private RadioButton radioSexButton;
	private Spinner spinner1;
	private Spinner spinner2;
	private Spinner spinner3;
	
	
	private void setupSpec(TabSpec tabSpec, TabHost tabHost, int id, String indicator) {
		tabSpec.setContent(id);
		tabSpec.setIndicator(indicator);
		tabHost.addTab(tabSpec);
		
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_client_reg);
		
		
		BarInflator.inflateActionBar(this);
		
		tabHost=(TabHost)findViewById(R.id.tabhost);
	    tabHost.setup();
	    
	    TabSpec specs1 = tabHost.newTabSpec("tab1");
	    setupSpec(specs1, tabHost, R.id.tab1, "1");
		TabSpec specs2 = tabHost.newTabSpec("2");
	    setupSpec(specs2, tabHost, R.id.tab2, "2");
//	    TabSpec specs3 = tabHost.newTabSpec("3");
//	    setupSpec(specs3, tabHost, R.id.tab3, "3");
//	    TabSpec specs4 = tabHost.newTabSpec("4");
//	    setupSpec(specs4, tabHost, R.id.tab4, "4");
//	    TabSpec specs5 = tabHost.newTabSpec("5");
//	    setupSpec(specs5, tabHost, R.id.tab5, "5");
//	    TabSpec specs6 = tabHost.newTabSpec("6");
//	    setupSpec(specs6, tabHost, R.id.tab6, "6");
	     
	    //addItemsonSpinnerMarital();
	    addListenerOnButton();
	    
	   
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.client_reg, menu);
		return true;
	}
	
	// add items into spinner dynamically
//	  public void addItemsonSpinnerMarital() {
//	 
//		spinner1 = (Spinner) findViewById(R.id.spinner1);
//		List<String> list = new ArrayList<String>();
//		list.add("list1");
//		list.add("list2");
//		list.add("list3");
//		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
//			android.R.layout.simple_spinner_item, list);
//		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//		spinner1.setAdapter(dataAdapter);
//	  }
	
	 public void addListenerOnButton() {
		 
			radioSexGroup = (RadioGroup) findViewById(R.id.radioSex);
			//btnDisplay = (Button) findViewById(R.id.btnDisplay);
		 
			//btnDisplay.setOnClickListener(new OnClickListener() {
		 
//				@Override
//				public void onClick(View v) {
//		 
//				        // get selected radio button from radioGroup
//					int selectedId = radioSexGroup.getCheckedRadioButtonId();
//		 
//					// find the radiobutton by returned id
//				        radioSexButton = (RadioButton) findViewById(selectedId);
//		 
//					Toast.makeText(ClientRegActivity.this,
//						radioSexButton.getText(), Toast.LENGTH_SHORT).show();
//		 
//				}
		 
		
		 
		  }
	 public void addListenerOnSpinner1ItemSelection() {
			//spinner3 = (Spinner) findViewById(R.id.spinner1);
			//spinner3.setOnItemSelectedListener(new CustomOnItemSelectedListener());
		  }
	 public void addListenerOnSpinner2ItemSelection() {
			//spinner3 = (Spinner) findViewById(R.id.spinner1);
			//spinner3.setOnItemSelectedListener(new CustomOnItemSelectedListener());
		  }
	  public void addListenerOnSpinner3ItemSelection() {
			//spinner3 = (Spinner) findViewById(R.id.spinner1);
			////spinner3.setOnItemSelectedListener(new CustomOnItemSelectedListener());
		  }
	  
	  public void submitClientRegistry(View view) {
		  radioTypeGroup = (RadioGroup) findViewById(R.id.radioTypeGroup);
		  // get selected radio button from radioGroup
		  int clientTypeId = radioTypeGroup.getCheckedRadioButtonId();
		  int sexGroupId = ((RadioGroup) findViewById(R.id.radioSex)).getCheckedRadioButtonId();
		  
		  // find the radiobutton by returned id
		  String radioClientType = ((RadioButton) findViewById(clientTypeId)).getText().toString();
		  String branchName = (String) ((Spinner)findViewById(R.id.branchnamespinner)).getSelectedItem();
		  String firstName = ((EditText) this.findViewById(R.id.firstname)).getText().toString();
		  String lastName = ((EditText)this.findViewById(R.id.familyname)).getText().toString();
		  String groupName = ((EditText)this.findViewById(R.id.groupname)).getText().toString();
		  String loanOfficer = ((EditText)this.findViewById(R.id.loanofficer)).getText().toString();
		  String gender = ((RadioButton) findViewById(sexGroupId)).getText().toString();
		  String marital = (String) ((Spinner)findViewById(R.id.marital_status)).getSelectedItem();
		  JSONObject json = new JSONObject();
		  
		  try {
			  json.put("firstname", firstName);
			  json.put("lastname", lastName);
			  json.put("locale", "en");
			  json.put("active", true);
			  json.put("activationDate", "04 March 2009");
		  	  json.put("dateFormat", "dd MMMM yyyy");
		  	  json.put("officerId", 1);
		  } catch(Exception e) {
			  e.printStackTrace();
			  Toast.makeText(getApplicationContext(), "Submit Fail", Toast.LENGTH_LONG).show();
		  }
		  IService service = ServiceFactory.getService();
		  Toast.makeText(this, "Click", Toast.LENGTH_LONG).show();
		  service.registerClient(json, new ResultHandler() {

			@Override
			public void success() {
				// TODO Auto-generated method stub
				Toast.makeText(getApplication(), "sucess", Toast.LENGTH_LONG).show();
			}

			@Override
			public void fail() {
				// TODO Auto-generated method stub
				Toast.makeText(getApplication(), "fail "+ this.getReason(), Toast.LENGTH_LONG).show();
			}

			@Override
			public void timeout() {
				// TODO Auto-generated method stub
				
			}
			  
		  });
		  
		 // Toast.makeText(this, branchName, Toast.LENGTH_LONG).show();
	  }

}
