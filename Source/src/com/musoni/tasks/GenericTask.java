package com.musoni.tasks;

import java.sql.Timestamp;

import org.json.JSONObject;

public abstract class GenericTask implements ITask {
	
	protected Integer id;
	
	protected JSONObject query;
	
	protected Timestamp submitTime;
	
	protected boolean completed;
	
	public GenericTask(Integer id, JSONObject query, Timestamp time, boolean completed) {
		this.id = id;
		this.query = query;
		this.submitTime = time;
		this.completed = completed;
	}
	
	@Override
	public JSONObject getJsonParams() {
		return query;
	}
	
	@Override
	public boolean isCompleted(){
		return completed;
	}

	@Override
	public String getStringParams() {
		return query.toString();
	}
	
	@Override
	public Timestamp getSubmitTime() {
		return this.submitTime;
	}

	@Override
	public Integer getId() {
		return this.id;
	}
}
