package com.musoni.tasks;

import java.sql.Timestamp;

import org.json.JSONObject;

import android.database.Cursor;

public class TaskReflector {
	
	public static final int CLIENT_REGISTER = 0, GROUP_REGISTER = 1;
	
	public static ITask reflect(Cursor cursor) {
		int id = cursor.getInt(0);
		int type = cursor.getInt(2);
		String query = cursor.getString(3);
		boolean completed = Boolean.parseBoolean(cursor.getString(4));
		Timestamp timestamp = Timestamp.valueOf(cursor.getString(5));
		try {
			switch (type) {
				case CLIENT_REGISTER:
					return new ClientRegisterTask(id, new JSONObject(query), timestamp, completed);
				case GROUP_REGISTER:
					return new GroupRegisterTask(id, new JSONObject(query), timestamp, completed);
				default:
					return null;
			}
		}
		catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
}
