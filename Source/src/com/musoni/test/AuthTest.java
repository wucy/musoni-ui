package com.musoni.test;

import org.json.JSONException;
import org.json.JSONObject;

import android.test.AndroidTestCase;

import com.musoni.service.IService;
import com.musoni.service.ResultHandler;
import com.musoni.service.ServiceFactory;

public class AuthTest extends AndroidTestCase {
	
	public void testAuth() throws JSONException {
		IService service = ServiceFactory.getService();
		JSONObject json = new JSONObject("{ \"officeId\": 1, \"firstname\": \"Petra\",\"lastname\": \"Yton\", 	\"externalId\": \"786YYH7\", 	\"dateFormat\": \"dd MMMM yyyy\", 	\"locale\": \"en\", 	\"active\": true,	\"activationDate\": \"04 March 2009\"} ");

		service.authenticate("code4good", "UK2013", new ResultHandler() {

			@Override
			public void success() {
				System.out.println(getResult().toString());
			}

			@Override
			public void fail() {
				// TODO Auto-generated method stub
				System.out.println("Failed");
				assertEquals(getReason(), "");
			}

			@Override
			public void timeout() {
				// TODO Auto-generated method stub
				System.out.println("Timeout");
				assertEquals(getReason(), "");
			}
			
		});
		
		
		service.registerClient(json, new ResultHandler() {

			@Override
			public void success() {
				// TODO Auto-generated method stub
				String r = this.getResult().toString();
				System.out.println(r);
			}

			@Override
			public void fail() {
				String r = this.getReason();
				System.out.println(r);
				
			}

			@Override
			public void timeout() {
				// TODO Auto-generated method stub
				
			}
			
		});
	}
}
