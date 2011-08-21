package com.barview.rest;

import android.os.AsyncTask;

import com.barview.constants.BarviewConstants;
import com.barview.rest.RestClient.RequestMethod;

public class FavoriteDeleter extends AsyncTask<String, Integer, String> {
	
	public static final String SUCCESS = "success";
	public static final String ERROR	= "error";
	
	private String result;

	@Override
	protected String doInBackground(String... params) {
		RestClient client = new RestClient(BarviewConstants.FAVORITE_URL_DEV + "/" + params[0]);
		client.AddHeader(BarviewConstants.REST_USER_ID, "dmac");
		
		try {
			client.Execute(RequestMethod.DELETE);
		} catch (Exception e) {
			e.printStackTrace();
			result = ERROR;
			return result;
		}
		
		result = SUCCESS;
		return result;
	}

	protected void onPostExecute(String result) {

	}

	public String getResult() {
		return result;
	}
}
