package com.barview;

import java.io.StringReader;
import java.util.ArrayList;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import android.app.ListActivity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.barview.constants.BarviewConstants;
import com.barview.models.Favorite;
import com.barview.rest.RestClient;
import com.barview.rest.RestClient.RequestMethod;
import com.barview.xml.XMLHandler;

public class FavoritesActivity extends ListActivity {
	
	static ArrayList<Favorite> favorites;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		favorites = new ArrayList<Favorite>();
		
		ListView lv = (ListView) getListView();
		lv.setTextFilterEnabled(true);
		
		lv.setOnItemClickListener(new OnItemClickListener() {
		    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		      // When clicked, show a toast with the TextView text
		    	Toast.makeText(getApplicationContext(), ((TextView) view).getText(),
		    	Toast.LENGTH_SHORT).show();
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
		
		String[] favesArray = new String[favorites.size()];
		int i= 0;
		for(Favorite f : favorites)
			favesArray[i++] = f.getBarName();
			
		
		setListAdapter(new ArrayAdapter<String>(this, R.layout.row, favesArray));
	}
	
	
	protected void setFavorites(ArrayList<Favorite> f) {
		favorites = f;
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
		
		@Override
		protected String doInBackground(String... params) {
			RestClient client = new RestClient(BarviewConstants.FAVORITES_URL_DEV);
			client.AddHeader("User_id", "dmac");
			
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
				Log.e("doInBackground", e.getMessage());
			}
			
			return response;
		}
		
		protected void onPostExecute(String result) {
			
		}
	}
}
