package com.musoni.storage;

import java.util.List;

import com.musoni.service.ServiceFactory;
import com.musoni.tasks.ITask;
import com.musoni.tasks.TaskQueue;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Storage extends SQLiteOpenHelper {
	
	private static final String DB_NAME = "musoni";
	
	private static final int DB_VERSION = 1;
	
	private static final String TABLE_TASKS = "tasks";
	
	private static final String CREATE_TASKS_TABLE = "CREATE TABLE " + TABLE_TASKS + " (id INTEGER PRIMARY KEY, user VARCHAR(100), type INTEGER NOT NULL, query TEXT, completed BOOLEAN, submit_time TIMESTAMP NOT NULL)";
	
	public Storage(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
	}
	
	public Cursor getTaskQueue(String userName) {
		SQLiteDatabase db = this.getWritableDatabase();
		return db.rawQuery("SELECT * FROM tasks WHERE completed = false", new String[]{});
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_TASKS_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_TASKS);
		onCreate(db);
	}
	
	public void saveIntoStorage(TaskQueue taskQueue) {
		List<ITask> tasks = taskQueue.toList();
		for (ITask task: tasks) 
			if (task.getId() == null)
				insertTask(task);
			else
				updateTask(task);
	}
	
	public void insertTask(ITask task) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("user", ServiceFactory.getService().getUsername());
		values.put("type", task.getTaskType());
		values.put("query", task.getStringParams());
		values.put("completed", task.isCompleted());
		values.put("submit_time", task.getSubmitTime().toString());
		
		db.insert(TABLE_TASKS, null, values);
	}
	
	public void updateTask(ITask task) {
		SQLiteDatabase db = this.getWritableDatabase();
		
		ContentValues values = new ContentValues();
		values.put("user", ServiceFactory.getService().getUsername());
		values.put("type", task.getTaskType());
		values.put("query", task.getStringParams());
		values.put("completed", task.isCompleted());
		values.put("submit_time", task.getSubmitTime().toString());
		
		db.update(TABLE_TASKS, values, "id = ?", new String[]{String.valueOf(task.getId())});
	}
}
