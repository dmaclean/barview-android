package com.barview.mobile;

import java.io.StringReader;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.barview.FacebookActivity;
import com.barview.R;
import com.barview.constants.BarviewConstants;
import com.barview.rest.RestClient;
import com.barview.rest.RestClient.RequestMethod;
import com.barview.utilities.BarviewUtilities;
import com.barview.xml.BarviewLoginXMLHandler;

public class BarviewMobileLoginTask extends AsyncTask<String, Integer, String> {

	/**
	 * The Barview server response for our mobile user login request.
	 */
	String response = "";
	
	/**
	 * POJO for the user logging in.
	 */
	BarviewMobileUser user = BarviewMobileUtility.getUser();
	
	FacebookActivity activity;
	
	public static final String SUCCESS = "success";
	public static final String ERROR	= "error";
	
	private String result;
	
	private String action;
	
	@Override
	protected String doInBackground(String... params) {
		action = params[0];
		
		/*
		 * Log the mobile user into Bar-view
		 */
		if(action.equals(BarviewConstants.BARVIEW_LOGIN)) {
		
			RestClient client = new RestClient(BarviewUtilities.getBarviewLoginURLForRunMode());
			client.AddHeader(BarviewConstants.BARVIEW_USERNAME, params[1]);
			client.AddHeader(BarviewConstants.BARVIEW_PASSWORD, params[2]);
			
			try {
				client.Execute(RequestMethod.GET);
				response = client.getResponse();
			} catch (Exception e) {
				e.printStackTrace();
				result = ERROR;
				return result;
			}
			
	
			try {
				/* Get a SAXParser from the SAXPArserFactory. */
	            SAXParserFactory spf = SAXParserFactory.newInstance();
	            SAXParser sp = spf.newSAXParser();
	
	            /* Get the XMLReader of the SAXParser we created. */
	            XMLReader xr = sp.getXMLReader();
	            /* Create a new ContentHandler and apply it to the XML-Reader*/
	            BarviewLoginXMLHandler myExampleHandler = new BarviewLoginXMLHandler();
	            myExampleHandler.setUser(user);
	            xr.setContentHandler(myExampleHandler);
	           
	            /* Parse the xml-data from our URL. */
	            xr.parse(new InputSource(new StringReader(response)));
	            /* Parsing has finished. */
	
	            /* Our ExampleHandler now provides the parsed data to us. */
	            user = myExampleHandler.getUser();
	            
	            if(user.getToken() != null) {
		            setPreferences();		// This uses the global user object, which has just been populated.
	            }
			}
			catch(Exception e) {
				Log.e("doInBackground", "An error occurred: " + e.getMessage());
			}
		}
		/*
		 * Log the mobile user out.
		 */
		else if(action.equals(BarviewConstants.BARVIEW_LOGOUT)) {
			RestClient client = new RestClient(BarviewUtilities.getBarviewLogoutURLForRunMode());
			client.AddHeader(BarviewConstants.BARVIEW_TOKEN_HEADER, params[1]);
			
			try {
				client.Execute(RequestMethod.GET);
				response = client.getResponse();
				
				if(!response.equals(""))
					throw new Exception("Error attempting to log out.");
				
				clearMobileUser();
				setPreferences();		// This uses the global "user" object, which we just cleared out.
			} catch (Exception e) {
				e.printStackTrace();
				result = ERROR;
				return result;
			}
		}
		
		return response;
	}
	
	protected void onPostExecute(String result) {
		if(action.equals(BarviewConstants.BARVIEW_LOGIN)) {
		
			/*
			 * We were able to log in.
			 * 
			 * Set all other logon buttons to invisible and change the text for
			 * the Barview button to "log out".
			 * 
			 * Also, save the BarviewMobileUser instance.
			 */
			if(user.getToken() != null && !result.equals(ERROR)) {
				Button fbLogonButton = (Button) activity.findViewById(R.id.fblogin);
				fbLogonButton.setVisibility(View.INVISIBLE);
				
				Button bvLogonButton = (Button) activity.findViewById(R.id.bvlogin);
				bvLogonButton.setText(R.string.bv_logout);
			}
			/*
			 * Unable to log in.
			 * 
			 * Show a Toast with the error to the user.
			 */
			else {
				Toast toast = Toast.makeText(activity.getApplicationContext(), "Unable to log into Bar-view.com", Toast.LENGTH_SHORT);
				toast.show();
			}
		}
		else if(action.equals(BarviewConstants.BARVIEW_LOGOUT)) {
			/*
			 * Logged out cleanly.
			 * 
			 * Make all non-Barview buttons visible and change the Bar-view text back to login
			 */
			if(!result.equals(ERROR)) {
				Button fbLogonButton = (Button) activity.findViewById(R.id.fblogin);
				fbLogonButton.setVisibility(View.VISIBLE);
				
				Button bvLogonButton = (Button) activity.findViewById(R.id.bvlogin);
				bvLogonButton.setText(R.string.bv_login);
			}
			// Errors occurred logging out.
			else {
				Toast toast = Toast.makeText(activity.getApplicationContext(), "Unable to log out of Bar-view.com", Toast.LENGTH_LONG);
				toast.show();
			}
		}
	}

	public void setActivity(FacebookActivity activity) {
		this.activity = activity;
	}
	
	public void setUser(BarviewMobileUser user) {
		this.user = user;
	}
	
	private void clearMobileUser() {
		BarviewMobileUser user = BarviewMobileUtility.getUser();
		user.setCity(null);
		user.setDob(null);
		user.setFirstName(null);
		user.setLastName(null);
		user.setState(null);
		user.setToken(null);
		user.setUserId(null);
	}
	
	private void setPreferences() {
		BarviewMobileUser user = BarviewMobileUtility.getUser();
		
		SharedPreferences settings = this.activity.getPreferences(Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(BarviewConstants.BARVIEW_FIRST_NAME, user.getFirstName());
        editor.putString(BarviewConstants.BARVIEW_LAST_NAME, user.getLastName());
        editor.putString(BarviewConstants.BARVIEW_EMAIL, user.getUserId());
        editor.putString(BarviewConstants.BARVIEW_DOB, user.getDob());
        editor.putString(BarviewConstants.BARVIEW_CITY, user.getCity());
        editor.putString(BarviewConstants.BARVIEW_STATE, user.getState());
        editor.putString(BarviewConstants.BARVIEW_TOKEN, user.getToken());
        editor.commit();
	}
}
