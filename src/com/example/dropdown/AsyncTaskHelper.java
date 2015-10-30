package com.example.dropdown;

import java.util.HashMap;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;

public class AsyncTaskHelper extends AsyncTask<String, Void, Void> {
  
	HttpResponse response;
	HttpUriRequest request;
	String url=null;
	String type;
	Handler handler;
	HashMap<String, String> map = new HashMap<String, String>();
	String result;
	String responsecode;
	HttpClient client;
	
	public AsyncTaskHelper()
	{
		super();
	}

	public AsyncTaskHelper(String type,String url, Handler handler)
	{
		super();
		this.url=url;
		this.type=type;
		this.handler=handler;
	}

	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		MainActivity.loadingSpinner.setVisibility(View.VISIBLE);
	}

	
    @Override
	protected Void doInBackground(String... params) {
    	try {
			
			switch(type)
			{
			case "GET" :
				request = new HttpGet(url);
				client = new DefaultHttpClient();
				response = client.execute(request);
				result = EntityUtils.toString(response.getEntity());
			    break;
			
			case "POST":
				request = new HttpPost(url);
				client = new DefaultHttpClient();
				request.addHeader("Accept", "application/json");
			    request.addHeader("Content-Type", "application/json");
				response = client.execute(request);
				result = EntityUtils.toString(response.getEntity());
                break;
                
			default:
                System.out.println("The type of HTTP request sent is wrong.Please specify the correct type");	
			}
			
			responsecode = String.valueOf(response.getStatusLine().getStatusCode());
			map.put("responsecode", responsecode);
			map.put("ResultString", result);
			
		}

		catch (Exception ex) {
		}
		
		return null;

	}
    
    @Override
	protected void onPostExecute(Void unused) {
//		Message msg = new Message();
//		msg.obj = map;
//		handler.dispatchMessage(msg);
		
		Message m = new Message();
        Bundle b = new Bundle();
        b.putString("result", result);
        b.putString("responsecode", responsecode);
        m.setData(b);
        handler.sendMessage(m);
	}

}
