package com.musoni.tasks;

import java.sql.Timestamp;
import java.util.Date;

import org.json.JSONObject;

import com.musoni.service.IService;
import com.musoni.service.ResultHandler;

public class ClientRegisterTask extends GenericTask {

	public ClientRegisterTask(JSONObject query) {
		super(null, query, new Timestamp(new Date().getTime()), false);
	}
	
	public ClientRegisterTask(int id, JSONObject query, Timestamp time, boolean completed) {
		super(id, query, time, completed);
	}
	
	@Override
	public void perform(IService service, ResultHandler handler) {
		service.registerClient(query, handler);
	}

	@Override
	public int getTaskType() {
		return TaskReflector.CLIENT_REGISTER;
	}
}