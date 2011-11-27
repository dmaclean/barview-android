package com.barview.rest;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

import com.barview.FavoritesActivity;
import com.barview.constants.BarviewConstants;
import com.barview.mobile.BarviewMobileUtility;
import com.barview.models.Favorite;
import com.barview.rest.RestClient.RequestMethod;
import com.barview.utilities.BarviewUtilities;
import com.barview.utilities.FacebookUtility;
import com.barview.xml.XMLHandler;

public class FavoritesListUpdater extends AsyncTask<String, Integer, String> {

	String response = "";
	
	ArrayList<Favorite> favorites;
	
	CountDownLatch latch;
	
	ProgressDialog dialog;
	
	public FavoritesListUpdater(Activity activity) {
		dialog = new ProgressDialog(activity);
	}
	
	protected void onPreExecute() {
        this.dialog.setMessage("Retrieving your favorite bars.");
        this.dialog.show();
    }
	
	@Override
	protected String doInBackground(String... params) {
		// User isn't logged in.  Don't do anything.
		if(!FacebookUtility.isLoggedIn() && !BarviewMobileUtility.isLoggedIn())
			return "Not logged in";
		
		RestClient client = new RestClient(BarviewUtilities.getFavoritesURLForRunMode());
		if(FacebookUtility.isLoggedIn())
			client.AddHeader(BarviewConstants.REST_USER_ID, FacebookUtility.getRESTUserId());
		else if(BarviewMobileUtility.isLoggedIn())
			client.AddHeader(BarviewConstants.REST_USER_ID, BarviewMobileUtility.getUser().getUserId());
		
		try {
			client.Execute(RequestMethod.GET);
			response = client.getResponse();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			/* Get a SAXParser from the SAXPArserFactory. */
            SAXParserFactory spf = SAXParserFactory.newInstance();
            SAXParser sp = spf.newSAXParser();

            /* Get the XMLReader of the SAXParser we created. */
            XMLReader xr = sp.getXMLReader();
            /* Create a new ContentHandler and apply it to the XML-Reader*/
            XMLHandler favoritesHandler = new XMLHandler();
            xr.setContentHandler(favoritesHandler);
           
            /* Parse the xml-data from our URL. */
            xr.parse(new InputSource(new StringReader(response)));
            /* Parsing has finished. */

            /* Our ExampleHandler now provides the parsed data to us. */
            favorites = favoritesHandler.getParsedFavorites();
		}
		catch(Exception e) {
			Log.e(FavoritesListUpdater.class.getName(), "doInBackground - An error occurred: " + e.getMessage());
		}
		
		return response;
	}
	
	protected void onPostExecute(String result) {
		ArrayList<String> barIds = new ArrayList<String>();
		
		String[] favesArray = new String[favorites.size()];
		int i= 0;
		for(Favorite f : favorites) {
			favesArray[i++] = f.getBarName();
			Log.i("FavoritesActivity", "Added favorite - " + f.getBarName());
			
			barIds.add(f.getBarId());
		}
		
		FavoritesActivity.setBarIds(barIds);
		
		if(latch != null)
			latch.countDown();
		
		if (dialog.isShowing())
            dialog.dismiss();
	}
	
	public void setCountDownLatch(CountDownLatch latch) {
		this.latch = latch;
	}
}
