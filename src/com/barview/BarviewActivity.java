package com.barview;

import com.barview.constants.BarviewConstants;
import com.barview.mobile.BarviewMobileUser;
import com.barview.mobile.BarviewMobileUtility;
import com.barview.utilities.FacebookUtility;

import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.widget.TabHost;

public class BarviewActivity extends TabActivity {
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
        
        
        
        /*
         * Determine if a user is already logged in.
         */
        SharedPreferences settings = getSharedPreferences(BarviewConstants.PREFS_NAME, Activity.MODE_PRIVATE);
        String type = settings.getString(BarviewConstants.LOGIN_TYPE, "");
        
        // If facebook, just make an attribute call to refresh attributes.
        if(type.equals(BarviewConstants.LOGIN_TYPE_FACEBOOK)) {
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