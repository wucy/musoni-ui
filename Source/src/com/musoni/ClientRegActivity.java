package com.musoni;

import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.view.Menu;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class ClientRegActivity extends Activity {

	
	TabHost tabHost;
	
	final int __TOT_STEPS = 6;
	
	private void setupSpec(TabSpec tabSpec, TabHost tabHost, int id, String indicator) {
		tabSpec.setContent(id);
		tabSpec.setIndicator(indicator);
		tabHost.addTab(tabSpec);
		
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_client_reg);
		
		final ActionBar actionBar = getActionBar();
		BarInflator.inflateActionBar(actionBar);
		
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
	    TabSpec specs5 = tabHost.newTabSpec("5");
	    setupSpec(specs5, tabHost, R.id.tab5, "5");
	    TabSpec specs6 = tabHost.newTabSpec("6");
	    setupSpec(specs6, tabHost, R.id.tab6, "6");
	     
	   
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.client_reg, menu);
		return true;
	}

}
