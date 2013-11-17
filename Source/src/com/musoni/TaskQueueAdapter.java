package com.musoni;

import java.util.ArrayList;
import java.util.List;

import com.musoni.tasks.ITask;
import com.musoni.tasks.TaskQueue;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;

public class TaskQueueAdapter implements ListAdapter {

	private TaskQueue taskQueue;
	
	private Context mContext;
	
	private LayoutInflater mInflater;
	
	protected List<DataSetObserver> observers;
	
	public TaskQueueAdapter(Context context, TaskQueue taskQueue) {
		this.taskQueue = taskQueue;
		this.mContext = context;
		this.observers = new ArrayList<DataSetObserver>();
		this.mInflater = LayoutInflater.from(context);
	}
	
	@Override
	public int getCount() {
		return taskQueue.toList().size();
	}

	@Override
	public Object getItem(int arg0) {
		return taskQueue.toList().get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return taskQueue.toList().get(arg0).getId();
	}

	@Override
	public int getItemViewType(int arg0) {
		return 0;
	}

	@Override
	public View getView(int pos, View convertView, ViewGroup paremt) {
		if (convertView == null)
			convertView = mInflater.inflate(R.layout.task_item, null);
		
		ITask task = taskQueue.toList().get(pos);
		
		
				
		return null;
	}

	@Override
	public int getViewTypeCount() {
		return 1;
	}

	@Override
	public boolean hasStableIds() {
		return true;
	}

	@Override
	public boolean isEmpty() {
		return taskQueue.toList().isEmpty();
	}

	@Override
	public void registerDataSetObserver(DataSetObserver arg) {
		observers.add(arg);
	}

	@Override
	public void unregisterDataSetObserver(DataSetObserver arg) {
		observers.add(arg);
	}

	@Override
	public boolean areAllItemsEnabled() {
		List<ITask> tasks = taskQueue.toList();
		for (ITask task: tasks)
			if (task.isCompleted())
				return false;
		return true;
	}

	@Override
	public boolean isEnabled(int arg) {
		return !taskQueue.toList().get(arg).isCompleted();
	}
	
	public void changed() {
		for(DataSetObserver obs: observers)
			obs.onChanged();
	}

}
