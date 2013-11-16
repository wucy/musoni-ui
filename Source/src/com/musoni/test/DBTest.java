package com.musoni.test;

import org.json.JSONObject;

import com.musoni.storage.Storage;
import com.musoni.tasks.ClientRegisterTask;
import com.musoni.tasks.ITask;

import android.test.AndroidTestCase;

public class DBTest extends AndroidTestCase {
	Storage storage = new Storage(getContext());
	
	//ITask task1 = new ClientRegisterTask(new JSONObject("{a:1}"));
	
	
}
