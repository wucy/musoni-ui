package com.musoni.tasks;

import java.util.LinkedList;
import java.util.List;
import android.database.Cursor;

import com.musoni.service.IService;
import com.musoni.service.ResultHandler;
import com.musoni.service.ServiceFactory;
import com.musoni.storage.Storage;

public class TaskQueue {
	
	private LinkedList<ITask> queue;
	
	private String userName;
	
	public static TaskQueue loadByUserName(String username, Storage db) {
		
		TaskQueue ret = new TaskQueue(username);
		
		Cursor cursor = db.getTaskQueue(username);
		
		while(cursor.moveToNext())  {
			ITask cur = TaskReflector.reflect(cursor);
			if (cur != null)
				ret.enqueue(cur);
		}
		return ret;
	}

	private TaskQueue(String userName) {
		this.userName = userName;
		this.queue = new LinkedList<ITask>();
	}
	
	public void enqueue(ITask task) {
		queue.add(task);
	}
	
	public void submitTasks(ResultHandler handler) throws Exception {
		IService service = ServiceFactory.getService();
		for (ITask task: queue) {
			if (!task.isCompleted())
				task.perform(service, handler);
		}
	}
	
	public List<ITask> toList() {
		return queue;
	}
	public String getUserName() {
		return userName;
	}

	public void clearCompleted() {
		// TODO
	}
}
