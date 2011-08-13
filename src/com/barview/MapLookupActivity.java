package com.barview;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.barview.constants.BarviewConstants;
import com.barview.map.overlay.CustomItemizedOverlay;
import com.barview.models.Bar;
import com.barview.rest.RestClient;
import com.barview.rest.RestClient.RequestMethod;
import com.barview.xml.NearbyBarXMLHandler;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

public class MapLookupActivity extends MapActivity implements LocationListener {
	
	LinearLayout linearLayout;
	MapView mapView;
	Button searchButton;
	EditText text;
	
	MapLookupActivity me = this;
	
	Geocoder geocoder = null;
	
	MapController mapController;
	
	static ArrayList<Bar> bars;
	
	private boolean foundLocation = false;
	
	private static final int latitudeE6 = 37985339;
    private static final int longitudeE6 = 23716735;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.barlookup);
		
		bars = new ArrayList<Bar>();
		
		geocoder = new Geocoder(this);

		mapView = (MapView) findViewById(R.id.mapview);
		mapView.setBuiltInZoomControls(true);
		
		text = (EditText) findViewById(R.id.editText1);
		
		searchButton = (Button) findViewById(R.id.button1);
		searchButton.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				try {
					String val = text.getText().toString();
					Log.i(MapLookupActivity.class.getName(), "Clicked search button. " + val);
					
					String response;
					
					List<Address> addressList = geocoder.getFromLocationName(val, 5);
					if(addressList!=null && addressList.size()>0) {
						double lat = addressList.get(0).getLatitude();
						double lng = addressList.get(0).getLongitude();
//						GeoPoint pt = new GeoPoint(lat,lng);
//						mapView.getController().setZoom(15);
//						mapView.getController().setCenter(pt);
						
						NearbyBarFetcher ff = new NearbyBarFetcher();
						ff.execute(lat, lng);
					}
					else {
						
					}
				}
				catch(Exception e) {
					Log.e("MapLookupActivity", e.getMessage());
				}
			}
		});
		
		
		
		LocationManager lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
		lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000L, 500.0f, this);
		
		
		List<Overlay> mapOverlays = mapView.getOverlays();
        Drawable drawable = this.getResources().getDrawable(R.drawable.icon);
        CustomItemizedOverlay itemizedOverlay = 
             new CustomItemizedOverlay(drawable, this);
        
        GeoPoint point = new GeoPoint(latitudeE6, longitudeE6);
        OverlayItem overlayitem = 
             new OverlayItem(point, "Hello", "I'm in Athens, Greece!");
        
        itemizedOverlay.addOverlay(overlayitem);
        mapOverlays.add(itemizedOverlay);
        
        mapController = mapView.getController();
        
        mapController.animateTo(point);
        mapController.setZoom(10);
	}

	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}

	public void onLocationChanged(Location location) {
		if (location != null && !foundLocation) {
			double lat = location.getLatitude();
			double lng = location.getLongitude();
			GeoPoint p = new GeoPoint((int) lat * 1000000, (int) lng * 1000000);
			mapController.animateTo(p);
			
			foundLocation = true;
		}
	}

	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * An AsyncTask class that contacts the Barview server with the lat/lng to
	 * get the nearby bars as an XML string.  Once retrieved, this string will
	 * be parsed by a SAX parser into individual Bar objects.  Finally,
	 * we'll grab the ArrayList of Bar objects so it can be put into the list.
	 * 
	 * @author dmaclean
	 *
	 */
	class NearbyBarFetcher extends AsyncTask<Double, Integer, String> {

		String response = "";
		
		double lat;
		double lng;
		
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
		        
		        // The Bar object already has lat/lng set up with GeoPoint precision - no need to multiply.
		        GeoPoint point = new GeoPoint((int)b.getLat(), (int)b.getLng());
		        OverlayItem overlayitem = new OverlayItem(point, b.getName(), b.getAddress());
		        
		        itemizedOverlay.addOverlay(overlayitem);
		        mapOverlays.add(itemizedOverlay);
			}
			
			GeoPoint p = new GeoPoint(	(int) (lat * BarviewConstants.GEOPOINT_MULT), 
										(int) (lng * BarviewConstants.GEOPOINT_MULT));
			mapController.animateTo(p);
			mapController.setZoom(14);
		}
	}
}