package com.musoni;

import com.musoni.service.ServiceFactory;
import com.musoni.storage.Storage;
import com.musoni.tasks.TaskQueue;

import android.os.Bundle;
import android.app.ListActivity;
import android.view.Menu;

public class QueueTaskActivity extends ListActivity {

	private Storage storage;
	
	private TaskQueue taskQueue;
	
	private String username;
	
	private TaskQueueAdapter adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_queue_task);
		username = ServiceFactory.getService().getUsername();
		init();
	}
	
	private void init() {
		storage = new Storage(this);
		taskQueue = TaskQueue.loadByUserName(username, storage);
		adapter = new TaskQueueAdapter(this, taskQueue);
		this.setListAdapter(adapter);
		this.getListView().setPadding(20, 20, 20, 20);
	}

	@Override
	protected void onResume() {
		super.onResume();		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.queue_task, menu);
		return true;
	}

}
