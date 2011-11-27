package com.barview;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;

import com.barview.constants.BarviewConstants;
import com.barview.listeners.FavoriteDelete2OnClickListener;
import com.barview.mobile.BarviewMobileUtility;
import com.barview.models.Favorite;
import com.barview.rest.RestClient;
import com.barview.rest.RestClient.RequestMethod;
import com.barview.utilities.BarviewUtilities;
import com.barview.utilities.FacebookUtility;
import com.barview.xml.XMLHandler;

public class FavoritesActivity extends ListActivity {
	
	static ArrayList<Favorite> favorites;
	
	private static ArrayList<String> barIds;
	private static boolean barsLoaded;
	
	FavoritesActivity favoritesClass = this;
	
	CountDownLatch latch;
	
	/*
	 * Static initializer for the favorites and barIds ArrayLists.  The initialization
	 * is necessary here because isFavorite() can be called statically, which means that
	 * if a user goes directly to an Activity that calls this (Map Lookup) before going
	 * into the FavoritesActivity (where they would normally be initialized in onCreate()
	 * then the array would be null and a NullPointerException would result.
	 */
	static {
		favorites = new ArrayList<Favorite>();
		barIds = new ArrayList<String>();
		
		barsLoaded = false;
	}
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		ListView lv = (ListView) getListView();
		lv.setTextFilterEnabled(true);
		
		/*
		 * Set up the OnItemClickListener to launch the Details activity when a row is clicked.
		 */
		lv.setOnItemClickListener(new OnItemClickListener() {
		    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		    	Intent intent = new Intent(favoritesClass, DetailActivity.class);
		    	intent.putExtra(BarviewConstants.BAR_ID, favorites.get(position).getBarId());
		    	intent.putExtra(BarviewConstants.BAR_NAME, favorites.get(position).getBarName());
		    	
		    	startActivity(intent);
		    }
		});
		
		/*
		 * Set up the OnItemLongClickListener to launch a dialog window prompting the user
		 * to delete the favorite if they want.
		 */
		lv.setOnItemLongClickListener(new OnItemLongClickListener() {

			public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
				Favorite f = favorites.get(position);
				
				// This listener is a disaster.  I'm passing all sorts of shit into it, but I can't think of a 
				// better way to update the interface and maintain the data structures.
				FavoriteDelete2OnClickListener listener = new FavoriteDelete2OnClickListener(parent.getContext(), position, favorites, barIds, favoritesClass);
				
				AlertDialog.Builder dialog = new AlertDialog.Builder(parent.getContext());
				dialog.setTitle("Delete " + f.getBarName() + " from favorites?");
				dialog.setPositiveButton("YES", listener);
				dialog.setNegativeButton("NO", listener);
				dialog.show();
				
				return true;
			}
			
		});
	}
	
	/**
	 * Run the FavoriteFetcher thread to get a fresh copy of the user's favorites
	 * and reset the list with them.
	 */
	protected void onResume() {
		super.onResume();
		
		FavoriteFetcher ff = new FavoriteFetcher();
		ff.execute("dmac");
		
		
	}
	
	
	protected void setFavorites(ArrayList<Favorite> f) {
		favorites = f;
	}
	
	public static void setBarIds(ArrayList<String> b) {
		barIds = b;
	}
	
	/**
	 * Determine if a bar is a favorite of the user.  This method should only be called
	 * in situations where the user is logged in.
	 * 
	 * @param barId
	 * @return
	 */
	public static boolean isFavorite(String barId) {
		return barIds.contains(barId);
	}
	
	/**
	 * An AsyncTask class that contacts the Barview server with the user id to
	 * get their favorites as an XML string.  Once retrieved, this string will
	 * be parsed by a SAX parser into individual Favorite objects.  Finally,
	 * we'll grab the ArrayList of Favorite objects so it can be put into the list.
	 * 
	 * @author dmaclean
	 *
	 */
	class FavoriteFetcher extends AsyncTask<String, Integer, String> {

		String response = "";
		
		ProgressDialog dialog;
		
		public FavoriteFetcher() {
			dialog = new ProgressDialog(favoritesClass);
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
	            XMLHandler myExampleHandler = new XMLHandler();
	            xr.setContentHandler(myExampleHandler);
	           
	            /* Parse the xml-data from our URL. */
	            xr.parse(new InputSource(new StringReader(response)));
	            /* Parsing has finished. */

	            /* Our ExampleHandler now provides the parsed data to us. */
	            setFavorites(           myExampleHandler.getParsedFavorites());
			}
			catch(Exception e) {
				Log.e(FavoriteFetcher.class.getName(), "doInBackground - An error occurred: " + e.getMessage());
			}
			
			return response;
		}
		
		protected void onPostExecute(String result) {
			if(result.equals("Not logged in")) {
				Toast toast = Toast.makeText(favoritesClass, com.barview.R.string.toast_notLoggedIn, Toast.LENGTH_LONG);
				toast.show();
			}
			
			// Clear out the bar ids because we have a new list
			barIds.clear();
			
			String[] favesArray = new String[favorites.size()];
			int i= 0;
			for(Favorite f : favorites) {
				favesArray[i++] = f.getBarName();
				Log.i("FavoritesActivity", "Added favorite - " + f.getBarName());
				
				barIds.add(f.getBarId());
			}
			
			if(favorites.size() == 0) {
				ArrayList<String> l = new ArrayList<String>();
				l.add("You don't have any favorite bars.");
				setListAdapter(new ArrayAdapter<String>(favoritesClass, R.layout.row, l));
			}
			else
				setListAdapter(new ArrayAdapter<String>(favoritesClass, R.layout.row, favesArray));
			barsLoaded = true;
			
			if (dialog.isShowing())
                dialog.dismiss();
			
			if(latch != null)
				latch.countDown();
		}
	}
	
	public void setCountDownLatch(CountDownLatch latch) {
		this.latch = latch;
	}
}
