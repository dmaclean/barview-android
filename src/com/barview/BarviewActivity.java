package com.barview;

import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.widget.TabHost;

import com.barview.constants.BarviewConstants;
import com.barview.mobile.BarviewMobileUser;
import com.barview.mobile.BarviewMobileUtility;
import com.barview.utilities.FacebookUtility;
import com.facebook.android.DialogError;
import com.facebook.android.Facebook;
import com.facebook.android.FacebookError;
import com.facebook.android.Facebook.DialogListener;

public class BarviewActivity extends TabActivity {
	
	Facebook fb;		// Singleton, no harm if it's already there.
	SharedPreferences settings;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        Resources resources = getResources();
        TabHost tabHost = getTabHost();
        TabHost.TabSpec spec;
        Intent intent;
        
        // Create an Intent to launch an Activity for the tab (to be reused)
        intent = new Intent().setClass(this, FavoritesActivity.class);
        
        /*
         * Create a tab for the Favorites activity
         */
        spec = tabHost.newTabSpec("favorites")
        				.setIndicator("Favorites", resources.getDrawable(R.drawable.ic_tab_favorites))
        				.setContent(intent);
        
        tabHost.addTab(spec);
        
        
    
        /*
         * Create a tab for the Bar Lookup activity
         */
        intent = new Intent().setClass(this, MapLookupActivity.class);
        spec = tabHost.newTabSpec("maplookup")
        				.setIndicator("Bar Lookup", resources.getDrawable(R.drawable.ic_tab_favorites))
        				.setContent(intent);
        
        tabHost.addTab(spec);
        
        
        
        /*
         * Create a tab for the Current Location activity
         */
        intent = new Intent().setClass(this, CurrentLocationActivity.class);
        spec = tabHost.newTabSpec("currentlocation")
        				.setIndicator("Current Location", resources.getDrawable(R.drawable.ic_tab_favorites))
        				.setContent(intent);
        
        tabHost.addTab(spec);
        
        
        /*
         * Create a tab for the Facebook login activity
         */
        intent = new Intent().setClass(this, FacebookActivity.class);
        spec = tabHost.newTabSpec("login")
        				.setIndicator("Login", resources.getDrawable(R.drawable.ic_tab_favorites))
        				.setContent(intent);
        
        tabHost.addTab(spec);
    }
    
    /**
     * We use this callback to set up a currently-logged-in user if they have closed the application
     * and re-opened it.
     * 
     * For Facebook we need to (possibly) re-instantiate the object and populate it with the access token
     * and expires variables that were saved into Preferences.  Then, if the session happens to be invalid
     * then we re-authorize and save the new access token to Preferences.
     * 
     * For Barview we just have to fetch the BarviewUser object (possibly instantiating in the process) 
     * and populate it with all the data stored in Preferences, including the token.
     */
    public void onResume() {
    	super.onResume();
    	
    	/*
         * Determine if a user is already logged in.
         */
    	fb = FacebookUtility.getFacebook();
    	settings = getSharedPreferences(BarviewConstants.PREFS_NAME, Activity.MODE_PRIVATE);
    	
        String type = settings.getString(BarviewConstants.LOGIN_TYPE, "");
        
        // If facebook, just make an attribute call to refresh attributes.
        if(type.equals(BarviewConstants.LOGIN_TYPE_FACEBOOK)) {
        	
        	// Set the access token
        	String accessToken = settings.getString(FacebookUtility.FB_ACCESS_TOKEN, null);
        	long expires = settings.getLong(FacebookUtility.FB_EXPIRES, 0);
        	if(accessToken != null)
        		fb.setAccessToken(accessToken);
        	if(expires != 0)
        		fb.setAccessExpires(expires);
        	
        	/*
             * Only call authorize if the access_token has expired.
             */
            if(!fb.isSessionValid()) {

                fb.authorize(this, new String[] {}, new DialogListener() {
                    public void onComplete(Bundle values) {
                        SharedPreferences.Editor editor = settings.edit();
                        editor.putString(FacebookUtility.FB_ACCESS_TOKEN, fb.getAccessToken());
                        editor.putLong(FacebookUtility.FB_EXPIRES, fb.getAccessExpires());
                        editor.commit();
                    }
        
                    public void onFacebookError(FacebookError error) {}
        
                    public void onError(DialogError e) {}
        
                    public void onCancel() {}
                });
            }
        	
        	String fbId = FacebookUtility.getAttribute(FacebookUtility.FB_ID);
        	Log.i(BarviewActivity.class.getName(), "Refreshed attributes for Facebook user " + fbId);
        }
        // If Barview, populate the barview user object with preferences values.
        else if(type.equals(BarviewConstants.LOGIN_TYPE_BARVIEW)) {
        	BarviewMobileUser user = BarviewMobileUtility.getUser();
    		
            user.setFirstName(settings.getString(BarviewConstants.BARVIEW_FIRST_NAME, ""));
            user.setLastName(settings.getString(BarviewConstants.BARVIEW_LAST_NAME, ""));
            user.setUserId(settings.getString(BarviewConstants.BARVIEW_EMAIL, ""));
            user.setDob(settings.getString(BarviewConstants.BARVIEW_DOB, ""));
            user.setCity(settings.getString(BarviewConstants.BARVIEW_CITY, ""));
            user.setState(settings.getString(BarviewConstants.BARVIEW_STATE, ""));
            user.setToken(settings.getString(BarviewConstants.BARVIEW_TOKEN, ""));
        }
    }
}