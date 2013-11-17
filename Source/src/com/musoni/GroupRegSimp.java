package com.musoni;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.musoni.service.IService;
import com.musoni.service.ResultHandler;
import com.musoni.service.ServiceFactory;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;

public class GroupRegSimp extends Activity {
	private Spinner branch_name_spinner;
	
	
	List<JSONObject> clients = new ArrayList<JSONObject>();
	
	Set<Integer> clientToAdd = new HashSet<Integer>();
	
	protected LinkedList<Map<String, String>> items = new LinkedList<Map<String, String>>();
	Map<String, String> name2id = new HashMap<String, String>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_group_reg_simp);
		
		addItemsOnbranch_name_spinner();
		
		SimpleAdapter adapter = new SimpleAdapter(this, items, android.R.layout.simple_list_item_activated_2, new String[] {"name"}, new int[] { android.R.id.text1 });
		ListView listView = (ListView) this.findViewById(R.id.listView);
		listView.setAdapter(adapter);
		/*listView.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int pos,
					long id) {
				try {
					clientToAdd.add(clients.get(pos).getInt("id"));
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}			
		});*/
		//LisViewAdapter adapter = new LisViewAdapter(items, this);
		
		
		IService service = ServiceFactory.getService();
		service.getClients(new ResultHandler(){

			@Override
			public void success() {
				JSONObject ret = this.getResult();
				int tot;
				try {
					tot = ret.getInt("totalFilteredRecords");
					JSONArray jarr = ret.getJSONArray("pageItems");
					for (int i = 0; i < tot; ++ i) {
						clients.add(jarr.getJSONObject(i));
					}

					
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
				
				for (int i = 0; i < clients.size(); ++ i) {
					try {
						String fullname = null;
						if (clients.get(i).toString().contains("fullname")) fullname = clients.get(i).getString("fullname");
						else if (clients.get(i).toString().contains("lastname") && clients.get(i).toString().contains("firstname"))
							fullname = clients.get(i).getString("firstname") + " " + clients.get(i).getString("lastname");
						else continue;
						
						Map<String, String> item = new HashMap<String, String>();
						item.put("name", fullname);
						items.addFirst(item);
						
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
				
			}

			@Override
			public void fail() {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void timeout() {
				// TODO Auto-generated method stub
				
			}
			
			
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.group_reg_simp, menu);
		return true;
	}
	
	  // add items into spinner dynamically
	  public void addItemsOnbranch_name_spinner() {
		 // branch_name_spinner = (Spinner) findViewById(R.id.branch_name_spinner);
		  //branch_name_spinner.setOnItemSelectedListener(new CustomOnItemSelectedListener());
	  }
	  
	  int getOfficeID(String x) {
		  return 1;
	  }
	  
	  int getStaffID(String x) {
		  return 6;
	  }
	  
	  int getClientID(String x) {
		  return 124;
	  }
	  
	  public void submitClientRegistry(View view) {
		  
		  String name = ((EditText) findViewById(R.id.gname)).getText().toString();
		  String officeid = ((Spinner) findViewById(R.id.branch_name_spinner)).getSelectedItem().toString();
		  String staffid = ((Spinner) findViewById(R.id.loan_officer_spinner)).getSelectedItem().toString();;
		  String registration = ((EditText) findViewById(R.id.editText4)).getText().toString();;
		  JSONArray jarr = new JSONArray();
		  ListView lv = ((ListView) findViewById(R.id.listView));
		  for (Integer i: clientToAdd) {
			  jarr.put("" + getClientID(lv.getItemAtPosition(i).toString()));
		  }
		  
		  JSONObject json = new JSONObject();
		  
		  try {
			  json.put("name", name);
			  json.put("officeId", "" + getOfficeID(officeid));
			  //json.put("staffId", "" + getStaffID(staffid));
			  //json.put("clientMembers", jarr);
			  json.put("active", false);
		  } catch(Exception e) {
			  e.printStackTrace();
			  Toast.makeText(getApplicationContext(), "Submit Fail", Toast.LENGTH_LONG).show();
		  }
		  
		  IService service = ServiceFactory.getService();
		  Toast.makeText(this, "Click", Toast.LENGTH_LONG).show();
		  service.registerGroup(json, new ResultHandler() {

			@Override
			public void success() {
				// TODO Auto-generated method stub
				Toast.makeText(getApplication(), "Sucess! The Group is registered now.", Toast.LENGTH_LONG).show();
			}

			@Override
			public void fail() {
				// TODO Auto-generated method stub
				Toast.makeText(getApplication(), "Failed! The Group Name Conflicts.", Toast.LENGTH_LONG).show();
			}

			@Override
			public void timeout() {
				// TODO Auto-generated method stub
				
			}
			  
		  });
		  
		 // Toast.makeText(this, branchName, Toast.LENGTH_LONG).show();
	  }

}
