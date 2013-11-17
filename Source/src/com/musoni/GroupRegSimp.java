package com.musoni;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import com.musoni.service.IService;
import com.musoni.service.ResultHandler;
import com.musoni.service.ServiceFactory;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;

public class GroupRegSimp extends Activity {
	private Spinner branch_name_spinner;

	
	protected List<Map<String, String>> items = new ArrayList<Map<String, String>>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_group_reg_simp);
		
		addItemsOnbranch_name_spinner();
		
		//////
		Map<String, String> itemsMap = new HashMap<String, String>();
		
		itemsMap.put("name", "Steve Jobs");
		
		items.add(itemsMap);
		
		itemsMap = new HashMap<String, String>();
		itemsMap.put("name", "Heming Shou");
		items.add(itemsMap);
		
		
		SimpleAdapter adapter = new SimpleAdapter(this, items, android.R.layout.simple_list_item_activated_2, new String[] {"name"}, new int[] { android.R.id.text1 });
		ListView listView = (ListView) this.findViewById(R.id.listView);
		listView.setAdapter(adapter);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.group_reg_simp, menu);
		return true;
	}
	
	  // add items into spinner dynamically
	  public void addItemsOnbranch_name_spinner() {
		  branch_name_spinner = (Spinner) findViewById(R.id.branch_name_spinner);
		  branch_name_spinner.setOnItemSelectedListener(new CustomOnItemSelectedListener());
	  }
	  
	  int getOfficeID(String x) {
		  return 1;
	  }
	  
	  int getStaffID(String x) {
		  return 1;
	  }
	  
	  public void submitClientRegistry(View view) {
		  
		  String name = ((EditText) findViewById(R.id.gname)).toString();
		  String officeid = ((Spinner) findViewById(R.id.branch_name_spinner)).getSelectedItem().toString();
		  String staffid = ((Spinner) findViewById(R.id.loan_officer_spinner)).getSelectedItem().toString();;
		  String registration = ((EditText) findViewById(R.id.editText4)).toString();;
		  JSONArray jarr = new JSONArray();
		  ListView lv = ((ListView) findViewById(R.id.listView));
		  for (int i = 0; i < lv.getCount(); ++ i) {
			  jarr.put(lv.getItemAtPosition(i).toString());
		  }
		  
		  JSONObject json = new JSONObject();
		  
		  try {
			  json.put("name", name);
			  json.put("officeid", getOfficeID(officeid));
			  json.put("staffid", getStaffID(staffid));
			  json.put("registration", registration);
			  json.put("clientMembers", jarr);
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
