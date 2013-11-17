package com.musoni.service;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Base64;

import com.musoni.storage.Storage;
import com.musoni.tasks.GenericTask;
import com.musoni.tasks.GroupRegisterTask;
import com.musoni.tasks.ITask;
import com.musoni.tasks.SearchTask;
import com.musoni.tasks.TaskQueue;

public class InternetService implements IService {
	
	public InternetService()
	{
		
	}

	public class HandlerWrapper extends AsyncTask<Void,Void,Void> implements Runnable{
		
		private HttpUriRequest req = null;
		private ResultHandler result = null;
		private ITask task = null;
		private TaskQueue taskQueue = null;
		public HandlerWrapper(HttpUriRequest req, ResultHandler result) {
			this.req = req;
			this.result = result;
		}
		
		public void run() {
			
			try{
				
				JSONObject response =null;
				HttpResponse res = MusoniSSLSocketFactory.getNewHttpClient().execute(req);
					
				String retStr = EntityUtils.toString(res.getEntity());
				response = new JSONObject(retStr);
				
				
				if(response != null && !response.has("errors"))
				{
					
					if(response.has("authenticated"))
					{
						loggedIn = true;
						active = true;
						userId = response.getString("userId");
						username = response.getString("username");
						officeId = response.getInt("officeId");
					}
					
					result.setResult(response);
					result.setStatus(ResultHandler.SUCCESS);
				}
				else{
					result.setStatus(ResultHandler.ERROR);
					result.setResult(response.getJSONObject("errors"));
					result.setReason("Error has occured check result for detailed information");
					
				}
			}
			catch(Exception ex){
				taskQueue.enqueue(task);
				active = false;
				result.setStatus(ResultHandler.ERROR);
				result.setReason(ex.getMessage().toString());
			}
			
		}

		@Override
		protected Void doInBackground(Void... arg0) {
			run();
			return null;
		}
		
		@Override
		protected void onPostExecute(Void arg) {
			if (result.getStatus() == ResultHandler.SUCCESS)
				result.success();
			else
				result.fail();
		}
	}
	
	private void writeToStorage(GenericTask task)
	{
		if(con != null)
			new Storage(con).insertTask(task);
	}
	
	private Context con = null;
	
	public void setContext(Context con)
	{
		this.con = con;
	}
	
	private JSONObject readFromStorage(GenericTask search)
	{
		TaskQueue tq = new TaskQueue(username);
		
		List<ITask> tasks = tq.toList();
		
		for(ITask task : tasks)
		{
			try
			{
				boolean shouldReturn = true;
				JSONObject json = task.getJsonParams();
				
				while(json.keys().hasNext())
				{
					String key = json.keys().next().toString();
					if(search.getJsonParams().has(key))
					{
						if(json.getString(key).contains(search.getJsonParams().getString(key)))
							shouldReturn = shouldReturn && true;
						else
							shouldReturn = false;
					}
				}
				
				if(shouldReturn)
					return json;
			
			}
			catch(Exception ex)
			{
			}
			
			}
						
		
		
		return null;
	}
	
	private String authCode = null ;
	
	private String userId = null;
	
	private boolean wasForcedOffline = false;
	
	private boolean active = false;
	
	private boolean loggedIn = false;
	
	private String username = null;
	
	private int officeId = 1;

	private String password;
	
	public static final String baseURL = "https://mlite-demo.musoni.eu:8443/mifosng-provider/api/v1/";
	
	public static final String tenantIdentifier = "code4good";
		
	public void authenticate(String user, String password, ResultHandler result){
		username = user;
		this.password = password;
		authCode = new String(Base64.encode((user + ":" + password).getBytes(), Base64.DEFAULT)).trim();
		
		Map<String, String> params = new HashMap<String, String>();
		params.put("username", user);
		params.put("password", password);
		
		try {			
			getJSON("authentication", params, "post", null, null, result);			
		}
		catch(Exception e) {
		}
		
	}
	
	private HttpPost preparePost(String api, Map<String, String> urlParams) {
		
		StringBuilder sb = new StringBuilder();
		sb.append(api);
		sb.append("?");
		for(String key: urlParams.keySet()) {
			sb.append(key);
			sb.append("=");
			sb.append(urlParams.get(key));
			sb.append("&");
		}
		sb.append("tenantIdentifier=" + tenantIdentifier);
		
		String url = sb.toString();
		
		HttpPost post = new HttpPost(url);
		post.setHeader("Content-Type", "application/json");
		if (authCode != null)
			post.setHeader("Authorization", "Basic " + authCode);
		
		return post;
	}
	
	private HttpGet prepareGet(String api, Map<String, String> urlParams) {
		StringBuilder sb = new StringBuilder();
		sb.append(api);
		sb.append("?");
		for(String key: urlParams.keySet()) {
			sb.append(key);
			sb.append("=");
			sb.append(urlParams.get(key));
			sb.append("&");
		}
		sb.append("tenantIdentifier=" + tenantIdentifier);
		
		String url = sb.toString();
		
		HttpGet get = new HttpGet(url);
		get.setHeader("Content-Type", "application/json");
		if (authCode != null)
			get.setHeader("Authorization", "Basic " + authCode);
		
		return get;
	}
	
	@SuppressLint("DefaultLocale")
	public void getJSON(String apiUrl, Map<String, String> urlParams, String method, JSONObject prm, GenericTask task, ResultHandler result) throws Exception {
			apiUrl = baseURL + apiUrl;
			HttpUriRequest req = null;
			BasicHttpParams parameters = new BasicHttpParams();
			for(String key: urlParams.keySet()) 
				parameters.setParameter(key, urlParams.get(key));
			
			if (method.toLowerCase().equals("post")) {
			
				req = preparePost(apiUrl, urlParams);
				req.setParams(parameters);
				
				if (prm != null) {
					StringEntity p = new StringEntity(prm.toString());
					((HttpPost)req).setEntity(p);
				}
			}
			else {
				req = prepareGet(apiUrl, urlParams);
				req.setParams(parameters);		
			}
			
			HandlerWrapper hw = new HandlerWrapper(req, result);	
			hw.execute();
			//Handler hand = new Handler();
			//hand.post(hw);
		
	}
	
	
	public boolean isActive() {
		return this.active;
	}
	

	
	public void registerClient(JSONObject prm, ResultHandler result) {
		// TODO Auto-generated method stub
		
		try{
			//ClientRegisterTask task = new ClientRegisterTask(prm);
			 prm.put("officeId", this.officeId);
			 getJSON("clients", new HashMap<String, String>(), "POST", prm, null, result);
			
		}
		catch(Exception ex)
		{
			
		}
			
		
	}

	@Override
	public void searchClientsByName(String name, ResultHandler result) {
		
		try{
			HashMap<String, String> params = new HashMap<String, String>();
			params.put("query", name);
			params.put("resource", "clients");
			
			JSONObject prm = new JSONObject();
			
			SearchTask task = new SearchTask(prm);
			
			String[] s = name.split(" ");
			
			if(s.length>=2)
			{			
				prm.put("firstname", s[0]);
				prm.put("lastname", s[1]);
			}
			else
				prm.put("fullname", name);
			
			getJSON("search", params, "GET", null, task, result);
		}
		catch(Exception ex)
		{
			
		}
		
	}

	@Override
	public void searchClientsByID(Integer id, ResultHandler result) {
		try{
			HashMap<String, String> params = new HashMap<String, String>();
			params.put("query", id.toString());
			params.put("resource", "clients");
			getJSON("search", params, "GET", null, null, result);
		}
		catch(Exception ex)
		{
			
		}
		
	}

	@Override
	public void registerGroup(JSONObject prm, ResultHandler result) {
		// TODO Auto-generated method stub
		
		try{
			GroupRegisterTask task = new GroupRegisterTask(prm);
			getJSON("groups", new HashMap<String, String>(), "POST", prm, task, result);
		}
		catch(Exception ex)
		{
			
		}		
		
	}

	@Override
	public void searchGroups(String groupName, ResultHandler result) {
		try{
			HashMap<String, String> params = new HashMap<String, String>();
			params.put("query", groupName);
			params.put("resource", "groups");
			
			JSONObject prm = new JSONObject();
			prm.put("name", groupName);
			
			SearchTask task = new SearchTask(prm);
			
			getJSON("search", params, "GET", null, task, result);
		}
		catch(Exception ex)
		{
		}
	}
	
	@Override
	public void searchGroups(Integer id, ResultHandler result) {
		try{
			HashMap<String, String> params = new HashMap<String, String>();
			params.put("query", id.toString());
			params.put("resource", "groups");
			getJSON("search", params, "GET", null, null, result);			
		}
		catch(Exception ex)
		{
		}
	}

	@Override
	public void applyLoan(JSONObject prm, ResultHandler result) {
		try{
			prm.put("locale", "en");
			prm.put("dateFormat", "dd MMMM yyyy");			
			
			Date now = (Date) Calendar.getInstance().getTime();
			String nowAsString = new SimpleDateFormat("dd MMMM yyyy").format(now);
			
			prm.put("submittedOnDate", nowAsString);
			
			getJSON("loans", new HashMap<String, String>(), "POST", prm, null, result);
		}
		catch(Exception ex)
		{
			
		}		
	}

	@Override
	public void getRepaymentSchedule(JSONObject prm, ResultHandler result) {
		try{
			HashMap<String, String> params = new HashMap<String, String>();
			params.put("command", "calculateLoanSchedule");
			
			getJSON("loans", params, "POST", prm, null, result);
							
		}
		catch(Exception ex)
		{
		}	
	}

	@Override
	public void businessVist(JSONObject prm, ResultHandler result) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void getOfficerDetails(JSONObject prm, ResultHandler result) {
		

		Map<String, String> params = new HashMap<String, String>();
		params.put("username", this.username);
		params.put("password", this.password);
		
		try {			
			getJSON(baseURL+"authentication", params, "post", null, null, result);			
		}
		catch(Exception e) {
		}
		
	}

	@Override
	public boolean isUserLoggedIn() {
		return this.loggedIn;
	}

	@Override
	public void updateClient(Integer clientId, JSONObject prm,
			ResultHandler result) {
		
		try{
			HashMap<String, String> params = new HashMap<String, String>();
						
			getJSON("clients/"+clientId.toString(), params, "PUT", prm, null, result);
							
		}
		catch(Exception ex)
		{
			
		}
		
	}

	@Override
	public void deleteClient(Integer clientId, ResultHandler result) {
		try{
			HashMap<String, String> params = new HashMap<String, String>();
			
			getJSON("clients/"+clientId.toString(), params, "DELETE", null, null, result);	
		}
		catch(Exception ex)
		{
			
		}
		
	}

	@Override
	public void assignStaff(Integer clientId, ResultHandler result) {
		try{
			HashMap<String, String> params = new HashMap<String, String>();
			
			JSONObject prm = new JSONObject().put("staffId", userId);
			
			getJSON("clients/"+clientId.toString(), params, "POST", prm, null, result);	
		}
		catch(Exception ex)
		{
		}
		
	}

	@SuppressLint("SimpleDateFormat")
	@Override
	public void activateClient(Integer clientId, ResultHandler result) {
		try{
			HashMap<String, String> params = new HashMap<String, String>();
			params.put("command", "activate");
			
			JSONObject prm = new JSONObject();
			prm.put("locale", "en");
			prm.put("dateFormat", "dd MMMM yyyy");			
			
			Date now = (Date) Calendar.getInstance().getTime();
			String nowAsString = new SimpleDateFormat("dd MMMM yyyy").format(now);
			
			prm.put("activationDate", nowAsString);
			
			getJSON("loans", params, "POST", prm, null, result);	
		}
		catch(Exception ex)
		{
			
		}
		
	}

	@Override
	public void addIDForClient(Integer clientId, JSONObject prm,
			ResultHandler result) {
		try{
			HashMap<String, String> params = new HashMap<String, String>();			
			getJSON("clients/"+clientId.toString()+"/identifiers", params, "POST", prm, null, result);
		}
		catch(Exception ex){
			
		}		
	}

	@Override
	public void getGroup(Integer groupId, ResultHandler result) {
		try{
			HashMap<String, String> params = new HashMap<String, String>();			
			getJSON("groups/"+groupId.toString(), params, "GET", null, null, result);
		}
		catch(Exception ex){
			
		}	
	}

	@Override
	public void updateGroup(Integer groupId, ResultHandler result) {
		try{
			HashMap<String, String> params = new HashMap<String, String>();			
			getJSON("groups/"+groupId.toString(), params, "PUT", null, null, result);
		}
		catch(Exception ex){
			
		}			
	}

	@Override
	public void deleteGroup(Integer groupId, ResultHandler result) {
		try{
			HashMap<String, String> params = new HashMap<String, String>();			
			getJSON("groups/"+groupId.toString(), params, "DELETE", null, null, result);
		}
		catch(Exception ex){
			
		}			
	}

	@Override
	public void associateClients(Integer groupId, JSONObject prm,
			ResultHandler result) {
		
		try{
			HashMap<String, String> params = new HashMap<String, String>();
			params.put("command", "associateClients");
			
			getJSON("groups/"+groupId.toString(), params, "POST", prm, null, result);
		}
		catch(Exception ex){
			
		}	
		
	}

	@Override
	public void disassociateClients(Integer groupId, JSONObject prm,
			ResultHandler result) {
		try{
			HashMap<String, String> params = new HashMap<String, String>();
			params.put("command", "disassociateClients");
			
			getJSON("groups/"+groupId.toString(), params, "POST", prm, null, result);
		}
		catch(Exception ex){
			
		}	
		
	}

	@Override
	public void getGroupAccounts(Integer groupId, ResultHandler result) {
		
		try{
			HashMap<String, String> params = new HashMap<String, String>();
			
			getJSON("groups/"+groupId.toString()+"/accounts", params, "GET", null, null, result);
		}
		catch(Exception ex){
			
		}	
		
	}

	@SuppressLint("SimpleDateFormat")
	@Override
	public void activateGroup(Integer groupId, ResultHandler result) {
		
		try{
			HashMap<String, String> params = new HashMap<String, String>();
			params.put("command", "activate");
			
			JSONObject prm = new JSONObject();
			prm.put("locale", "en");
			prm.put("dateFormat", "dd MMMM yyyy");			
			
			Date now = (Date) Calendar.getInstance().getTime();
			String nowAsString = new SimpleDateFormat("dd MMMM yyyy").format(now);
			
			prm.put("activationDate", nowAsString);
			
			getJSON("groups/"+groupId.toString(), params, "POST", prm,null, result);
		}
		catch(Exception ex){
			
		}	
		
	}

	@Override
	public void getLoan(Integer loanId, ResultHandler result) {
		
		try{
			HashMap<String, String> params = new HashMap<String, String>();
			
			getJSON("loans/"+loanId.toString(), params, "GET", null,null, result);
		}
		catch(Exception ex){
			
		}	
		
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return username;
	}

	@Override
	public void listIDsForClient(Integer clientId, JSONObject prm,
			ResultHandler result) {
		
		try{
			HashMap<String, String> params = new HashMap<String, String>();
			
			getJSON("clients/"+clientId.toString()+"/identifiers", params, "GET", null, null, result);
		}
		catch(Exception ex){
			
		}	
		
	}

	@Override
	public void forceOnline() {
		// TODO Auto-generated method stub
		if(wasForcedOffline)
			active = true;
	}

	@Override
	public void forceOffline() {
		// TODO Auto-generated method stub
		wasForcedOffline= true;
		active = false;
	}

	@Override
	public void logoff() {
		loggedIn = false;
		wasForcedOffline = false;
	
		
	}

	@Override
	public void getClientGroups(Integer clientId, ResultHandler result) {
		try{
			HashMap<String, String> params = new HashMap<String, String>();
			params.put("associations", "groupMembers");
			getJSON("clients/"+clientId.toString(), params, "GET", null, null, result);
		}
		catch(Exception ex)
		{
			
		}
		
	}

}
