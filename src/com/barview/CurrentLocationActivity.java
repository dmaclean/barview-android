package com.barview;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import com.barview.rest.NearbyBarFetcher;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;

public class CurrentLocationActivity extends MapActivity implements LocationListener {

	MapView mapView;
	
	MapController mapController;
	
//	private NearbyBarFetcher fetcher;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.currentlocation);
		
		mapView = (MapView) findViewById(R.id.currloc_mapview);
		mapView.setBuiltInZoomControls(true);
		
		
		// Get a handle for the controller of our MapView.
		mapController = mapView.getController();
		
		// Set up the Location Manager
		LocationManager lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
		lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000L, 500.0f, this);
		
		
	}
	
	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}

	public void onLocationChanged(Location location) {
		if (location != null) {
			double lat = location.getLatitude();
			double lng = location.getLongitude();
			GeoPoint p = new GeoPoint((int) lat * 1000000, (int) lng * 1000000);
			mapController.animateTo(p);
			
			// Grab any nearby bars and annotate them on the map
			NearbyBarFetcher fetcher = new NearbyBarFetcher();
			fetcher.setMapView(mapView);
			fetcher.setMe(this);
			fetcher.execute(lat, lng);
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

}
