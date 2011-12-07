package com.barview.rest;

import com.barview.rest.RestClient.RequestMethod;
import com.barview.utilities.BarviewUtilities;
import com.barview.utilities.FacebookUtility;

import android.os.AsyncTask;

public class FacebookDataUpdater extends AsyncTask<String, Integer, String> {

	@Override
	protected String doInBackground(String... params) {
		RestClient client = new RestClient(BarviewUtilities.getFacebookUpdateURLForRunMode());
		client.AddParam("json", FacebookUtility.getJSON().toString());
		try {
			client.Execute(RequestMethod.POST);
			String response = client.getResponse();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

}
