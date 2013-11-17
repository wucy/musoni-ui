package com.musoni.test;

import org.json.JSONException;
import org.json.JSONObject;

import com.musoni.storage.Storage;
import com.musoni.tasks.ClientRegisterTask;
import com.musoni.tasks.ITask;
import com.musoni.tasks.TaskQueue;

import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;

public class DBTest extends AndroidTestCase {
	
	
	public void testDB() throws JSONException {
	
		RenamingDelegatingContext context = new RenamingDelegatingContext(getContext(), "test_");
		
		Storage storage = new Storage(context);
		
		JSONObject json = new JSONObject("{a:1}");
		
		ITask task1 = new ClientRegisterTask(json);
		
		storage.insertTask(task1);
		
		TaskQueue queue = TaskQueue.loadByUserName("code4good", storage);
		
		ITask task = queue.toList().get(0);
		assertEquals(json.toString(), task.getStringParams());
	}
	
}
