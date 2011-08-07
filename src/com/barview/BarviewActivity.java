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
        
//        startActivity(new Intent(this, FavoritesActivity.class).addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)	);
        
        Resources resources = getResources();
        TabHost tabHost = getTabHost();
        TabHost.TabSpec spec;
        Intent intent;
        
        // Create an Intent to launch an Activity for the tab (to be reused)
        intent = new Intent().setClass(this, FavoritesActivity.class);
        
        // Initialize a TabSpec for each tab and add it to the TabHost
        spec = tabHost.newTabSpec("favorites")
        				.setIndicator("Favorites", resources.getDrawable(R.drawable.ic_tab_favorites))
        				.setContent(intent);
        
        tabHost.addTab(spec);
    }
}