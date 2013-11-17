package com.musoni.tasks;

import com.musoni.service.IService;
import com.musoni.service.ResultHandler;

import java.sql.Timestamp;

import org.json.JSONObject;

public interface ITask {
	
	public boolean isCompleted();
	
	public String getStringParams();
	
	public int getTaskType();
	
	public Timestamp getSubmitTime();
	
	public void perform(IService service, ResultHandler handler);

	public JSONObject getJsonParams();
	
	public Integer getId();
}
