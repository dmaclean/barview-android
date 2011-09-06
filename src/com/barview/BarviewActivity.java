package com.barview;

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
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
        spec = tabHost.newTabSpec("facebook")
        				.setIndicator("Facebook Login", resources.getDrawable(R.drawable.ic_tab_favorites))
        				.setContent(intent);
        
        tabHost.addTab(spec);
    }
}