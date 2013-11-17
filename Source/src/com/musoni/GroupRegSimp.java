package com.musoni;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;

public class GroupRegSimp extends Activity {
	private Spinner branch_name_spinner;

	
	protected List<Map<String, String>> items = new ArrayList<Map<String, String>>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.activity_group_reg_simp);
		
		addItemsOnbranch_name_spinner();
		
		//////
		Map<String, String> itemsMap = new HashMap<String, String>();
		
		itemsMap.put("name", "Steve Jobs");
		
		items.add(itemsMap);
		
		itemsMap = new HashMap<String, String>();
		itemsMap.put("name", "Heming Shou");
		items.add(itemsMap);
		
		
		SimpleAdapter adapter = new SimpleAdapter(this, items, android.R.layout.simple_list_item_activated_2, new String[] {"name"}, new int[] { android.R.id.text1 });
		//ListView listView = (ListView) this.findViewById(R.id.listView);
		//listView.setAdapter(adapter);
		
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
		  branch_name_spinner.setOnItemSelectedListener(new CustomOnItemSelectedListener());
	  }

}
