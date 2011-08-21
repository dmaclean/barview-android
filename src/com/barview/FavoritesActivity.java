package com.barview;

import java.io.StringReader;
import java.util.ArrayList;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import com.barview.constants.BarviewConstants;
import com.barview.listeners.FavoriteDeleteOnClickListener;
import com.barview.models.Favorite;
import com.barview.rest.RestClient;
import com.barview.rest.RestClient.RequestMethod;
import com.barview.xml.XMLHandler;

public class FavoritesActivity extends ListActivity {
	
	static ArrayList<Favorite> favorites;
	
	private ArrayList<String> barIds;
	
	FavoritesActivity favoritesClass = this;
	
	private FavoriteAdapter m_adapter;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		favorites = new ArrayList<Favorite>();
		barIds = new ArrayList<String>();
		
		ListView lv = (ListView) getListView();
		lv.setTextFilterEnabled(true);
		
		lv.setOnItemClickListener(new OnItemClickListener() {
		    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		    	Intent intent = new Intent(favoritesClass, DetailActivity.class);
		    	intent.putExtra(BarviewConstants.BAR_ID, favorites.get(position).getBarId());
		    	intent.putExtra(BarviewConstants.BAR_NAME, favorites.get(position).getBarName());
		    	
		    	startActivity(intent);
		    }
		});
		
		this.m_adapter = new FavoriteAdapter(this, R.layout.row, favorites);
        setListAdapter(this.m_adapter);
	}
	
	/**
	 * Run the FavoriteFetcher thread to get a fresh copy of the user's favorites
	 * and reset the list with them.
	 */
	protected void onResume() {
		super.onResume();
		
		Log.i("FavoritesActivity", "in onResume");
		
		FavoriteFetcher ff = new FavoriteFetcher();
		ff.execute("dmac");
		
		
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
				Log.e("doInBackground", "An error occurred: " + e.getMessage());
			}
			
			return response;
		}
		
		protected void onPostExecute(String result) {
			// Clear out the bar ids because we have a new list
			barIds.clear();
			m_adapter.clear();
			
			for(Favorite f : favorites) {
				Log.i("FavoritesActivity", "Added favorite - " + f.getBarName());
				
				barIds.add(f.getBarId());
				m_adapter.add(f);
			}
			
			m_adapter.notifyDataSetChanged();
		}
	}
	
	/**
	 * A customized adapter for the favorites list.  This helps facilitate a custom view
	 * for each row.
	 * 
	 * @author dmaclean
	 *
	 */
	public class FavoriteAdapter extends ArrayAdapter<Favorite> {

		private ArrayList<Favorite> items;
		
		private Context context;

		public FavoriteAdapter(Context context, int textViewResourceId, ArrayList<Favorite> items) {
			super(context, textViewResourceId, items);
			this.items = items;
			this.context = context;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View v = convertView;
			if (v == null) {
				LayoutInflater vi = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				v = vi.inflate(R.layout.row, null);
			}
			Favorite o = items.get(position);
			if (o != null) {
				TextView tt = (TextView) v.findViewById(R.id.toptext);
//				TextView bt = (TextView) v.findViewById(R.id.bottomtext);
				
				if (tt != null) {
					tt.setText(o.getBarName());
				}
//				if(bt != null) {
//					bt.setText("Status: "+ o.getAddress());
//				}
				
				Button delete = (Button) v.findViewById(R.id.favesDeleteButton);
				FavoriteDeleteOnClickListener listener = 
						new FavoriteDeleteOnClickListener(context, position, items, barIds, m_adapter);
				delete.setOnClickListener(listener);
			}
			return v;
		}
	}
}
