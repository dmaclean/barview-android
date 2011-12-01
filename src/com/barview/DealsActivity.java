package com.barview;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.barview.FavoritesActivity.FavoriteFetcher;
import com.barview.adapters.DealAdapter;
import com.barview.constants.BarviewConstants;
import com.barview.mobile.BarviewMobileUtility;
import com.barview.models.Deal;
import com.barview.rest.RestClient;
import com.barview.rest.RestClient.RequestMethod;
import com.barview.utilities.BarviewUtilities;
import com.barview.utilities.FacebookUtility;
import com.barview.xml.DealXMLHandler;

public class DealsActivity extends ListActivity {
	static ArrayList<Deal> deals;
	
	DealsActivity dealsActivity = this;
	
	CountDownLatch l;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		deals = new ArrayList<Deal>();
		
		ListView lv = (ListView) getListView();
		lv.setTextFilterEnabled(true);
	}
	
	/**
	 * (Re) fetch deals for the user.
	 */
	protected void onResume() {
		super.onResume();
		
		DealsFetcher fetcher = new DealsFetcher();
		fetcher.execute("");
	}
	
	/**
	 * Setter for the Deals array that is utilized within the AsyncTask thread.
	 * 
	 * @param d
	 */
	protected void setDeals(ArrayList<Deal> d) {
		deals = d;
	}
	
	/**
	 * An AsyncTask class that contacts the Barview server with the user id to
	 * get the deals of the bars in their favorites as an XML string.  Once retrieved, 
	 * this string will be parsed by a SAX parser into individual Deal objects.  Finally,
	 * we'll grab the ArrayList of Deal objects so it can be put into the list.
	 * 
	 * @author dmaclean
	 *
	 */
	class DealsFetcher extends AsyncTask<String, Integer, String> {

		String response = "";
		
		ProgressDialog dialog;
		
		public DealsFetcher() {
			dialog = new ProgressDialog(dealsActivity);
		}
		
		protected void onPreExecute() {
            this.dialog.setMessage("Retrieving deals from your favorite bars...");
            this.dialog.show();
        }
		
		@Override
		protected String doInBackground(String... params) {
			// User isn't logged in.  Don't do anything.
			if(!FacebookUtility.isLoggedIn() && !BarviewMobileUtility.isLoggedIn())
				return "Not logged in";
			
			RestClient client = new RestClient(BarviewUtilities.getEventsURLForRunMode());
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
	            DealXMLHandler dealHandler = new DealXMLHandler();
	            xr.setContentHandler(dealHandler);
	           
	            /* Parse the xml-data from our URL. */
	            xr.parse(new InputSource(new StringReader(response)));
	            /* Parsing has finished. */

	            /* Our ExampleHandler now provides the parsed data to us. */
	            setDeals(dealHandler.getDeals());
			}
			catch(Exception e) {
				Log.e(FavoriteFetcher.class.getName(), "doInBackground - An error occurred: " + e.getMessage());
			}
			
			return response;
		}
		
		protected void onPostExecute(String result) {
			if(result.equals("Not logged in")) {
				Toast toast = Toast.makeText(dealsActivity, R.string.toast_notLoggedIn, Toast.LENGTH_LONG);
				toast.show();
			}
			
			if(deals.size() == 0) {
				ArrayList<String> list = new ArrayList<String>();
				list.add("No deals available right now.");
				setListAdapter(new ArrayAdapter<String>(dealsActivity, R.layout.row, list));
			}
			else
				setListAdapter(new DealAdapter(dealsActivity, R.layout.deal_row, deals));
				
			
			// Need this check since the Latch is only created during unit testing!
			if(l != null)
				l.countDown();
			
			if(dialog.isShowing())
				dialog.dismiss();
		}
	}
	
	public void setCountDownLatch(CountDownLatch latch) {
		l = latch;
	}
}
