package com.musoni.tasks;

import java.sql.Timestamp;
import java.util.Date;

import org.json.JSONObject;

import com.musoni.service.IService;
import com.musoni.service.ResultHandler;

public class GroupRegisterTask extends GenericTask {
	
	public GroupRegisterTask(JSONObject query) {
		super(null, query, new Timestamp(new Date().getTime()), false);
	}

	public GroupRegisterTask(int id, JSONObject query, Timestamp time, boolean completed) {
		super(id, query, time, completed);
	}

	@Override
	public int getTaskType() {
		return TaskReflector.GROUP_REGISTER;
	}

	@Override
	public void perform(IService service, ResultHandler handler) {
		service.registerGroup(query, handler);
	}
}
