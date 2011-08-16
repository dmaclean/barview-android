package com.barview.listeners;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.util.Log;

import com.barview.DetailActivity;
import com.barview.constants.BarviewConstants;


public class MapLookupClickListener implements OnClickListener {

	Context context;
	
	String barId;
	String barName;
	
	public MapLookupClickListener(String barId, String barName) {
		this.barId = barId;
		this.barName = barName;
	}
	
	
	public void onClick(DialogInterface dialog, int which) {
		Log.i(MapLookupClickListener.class.getName(), "CLickd!");
		Intent intent = new Intent(context, DetailActivity.class);
    	intent.putExtra(BarviewConstants.BAR_ID, barId);
    	intent.putExtra(BarviewConstants.BAR_NAME, barName);
    	
    	context.startActivity(intent);
	}

	public void setContext(Context context) {
		this.context = context;
	}

	
}
