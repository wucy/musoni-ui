package com.musoni.tasks;

import java.sql.Timestamp;
import java.util.Date;

import org.json.JSONObject;

import com.musoni.service.IService;
import com.musoni.service.ResultHandler;

public class SearchTask extends GenericTask {

	public SearchTask(JSONObject query) {
		super(null, query, new Timestamp(new Date().getTime()), false);
	}
	
		
	@Override
	public void perform(IService service, ResultHandler handler) {
		service.registerClient(query, handler);
	}

	@Override
	public int getTaskType() {
		return TaskReflector.SEARCH_TASK;
	}
}