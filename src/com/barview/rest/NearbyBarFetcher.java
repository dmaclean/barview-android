package com.barview.rest;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;

import com.barview.MapLookupActivity;
import com.barview.R;
import com.barview.constants.BarviewConstants;
import com.barview.map.overlay.CustomItemizedOverlay;
import com.barview.models.Bar;
import com.barview.rest.RestClient.RequestMethod;
import com.barview.xml.NearbyBarXMLHandler;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

/**
 * An AsyncTask class that contacts the Barview server with the lat/lng to
 * get the nearby bars as an XML string.  Once retrieved, this string will
 * be parsed by a SAX parser into individual Bar objects.  Finally,
 * we'll grab the ArrayList of Bar objects and translate their lat/lng values
 * into clickable map annotations.
 * 
 * The AsyncTask has access to the UI thread so we can update the UI right
 * from here.
 * 
 * **** It might be a bit ugly passing elements of the UI into here instead
 * of just making this an inner class.  Could we keep the class as an inner
 * class and just refactor out the logic inside the methods?
 * ****
 * 
 * @author dmaclean
 *
 */
public class NearbyBarFetcher extends AsyncTask<Double, Integer, String> {

	/**
	 * The Barview server response for our nearby bar request.
	 */
	String response = "";
	
	/**
	 * The latitude value of our current location.
	 */
	double lat;
	
	/**
	 * The longitude value of our current location.
	 */
	double lng;
	
	/**
	 * A reference to the MapView object of the Activity that is executing this thread.
	 */
	private MapView mapView;
	
	/**
	 * A list of the nearby bars relative to the location of interest (the searched-for location
	 * for MapLookupActivity, and our current location for the CurrentLocationActivity).
	 */
	private ArrayList<Bar> bars;
	
	/**
	 * A reference to the MapActivity of the caller.  This exists so we can get a reference to
	 * the resources.
	 * 
	 * I'm not a huge fan of this being here....
	 */
	private MapActivity me;
	
	@Override
	protected String doInBackground(Double... params) {
		lat = params[0];
		lng = params[1];
		
		RestClient client = new RestClient(BarviewConstants.NEARBYBARS_URL_DEV);
		client.AddHeader("Latitude", String.valueOf(params[0]));
		client.AddHeader("Longitude", String.valueOf(params[1]));
		
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
            NearbyBarXMLHandler myExampleHandler = new NearbyBarXMLHandler();
            xr.setContentHandler(myExampleHandler);
           
            /* Parse the xml-data from our URL. */
            xr.parse(new InputSource(new StringReader(response)));
            /* Parsing has finished. */

            /* Our ExampleHandler now provides the parsed data to us. */
            bars = myExampleHandler.getNearbyBars();
		}
		catch(Exception e) {
			Log.e("doInBackground", "An error occurred: " + e.getMessage());
		}
		
		return response;
	}
	
	protected void onPostExecute(String result) {
		List<Overlay> mapOverlays = mapView.getOverlays();
		
		for(Bar b : bars) {
			Log.i(MapLookupActivity.class.getName(), b.getName());
			
			
	        Drawable drawable = me.getResources().getDrawable(R.drawable.icon);
	        CustomItemizedOverlay itemizedOverlay = new CustomItemizedOverlay(drawable, me);
	        itemizedOverlay.setBarId(b.getBarId());
	        
	        // The Bar object already has lat/lng set up with GeoPoint precision - no need to multiply.
	        GeoPoint point = new GeoPoint((int)b.getLat(), (int)b.getLng());
	        OverlayItem overlayitem = new OverlayItem(point, b.getName(), b.getAddress());
	        
	        itemizedOverlay.addOverlay(overlayitem);
	        mapOverlays.add(itemizedOverlay);
		}
		
		GeoPoint p = new GeoPoint(	(int) (lat * BarviewConstants.GEOPOINT_MULT), 
									(int) (lng * BarviewConstants.GEOPOINT_MULT));
		
		MapController mapController = mapView.getController();
		mapController.animateTo(p);
		mapController.setZoom(14);
	}

	public void setMapView(MapView mapView) {
		this.mapView = mapView;
	}

	public void setMe(MapActivity me) {
		this.me = me;
	}

}
