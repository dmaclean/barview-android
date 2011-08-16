package com.barview.map.overlay;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;

import com.barview.R;
import com.barview.listeners.MapLookupClickListener;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapView;
import com.google.android.maps.OverlayItem;

public class CustomItemizedOverlay extends ItemizedOverlay<OverlayItem> {
	   private ArrayList<OverlayItem> mapOverlays = new ArrayList<OverlayItem>();
	   
	   private Context context;
	   
//	   View imageView;
	   
//	   MapView map;
	   
	   private String barId;
	   
	   public CustomItemizedOverlay(Drawable defaultMarker) {
	        super(boundCenterBottom(defaultMarker));
	   }
	   
	   public CustomItemizedOverlay(Drawable defaultMarker, Context context) {
	        this(defaultMarker);
	        this.context = context;
	   }

	   @Override
	   protected OverlayItem createItem(int i) {
	      return mapOverlays.get(i);
	   }

	   @Override
	   public int size() {
	      return mapOverlays.size();
	   }
	   
	   @Override
	   protected boolean onTap(int index) {
	      OverlayItem item = mapOverlays.get(index);
	      
	      MapLookupClickListener listener = new MapLookupClickListener(barId, item.getTitle());
	      listener.setContext(context);
	      
	      AlertDialog.Builder dialog = new AlertDialog.Builder(context);
	      dialog.setTitle(item.getTitle());
	      dialog.setMessage(item.getSnippet());
	      dialog.setNeutralButton(R.string.goToImage, listener);
	      dialog.show();
	      
	      // Create a new ImageView and place the bar image in it.
//	      ImageView iv = new ImageView(context);
//	      ((ViewGroup)map.getParent()).addView(iv);
//	      
//	      
//	      // Create a click listener that hides the ImageView.
//	      iv.setOnClickListener(new OnClickListener() {
//	    	  public void onClick(View v) {
//	    		  v.setVisibility(View.INVISIBLE);
//	    	  } 
//	      });
//	      
//	      BarImageFetcher fetcher = new BarImageFetcher();
//	      fetcher.setImageView(iv);
//	      fetcher.execute("1");
	      
	      return true;
	   }
	   
	   public void addOverlay(OverlayItem overlay) {
		   mapOverlays.add(overlay);
		   this.populate();
	   }
	   
//	   public void setImageView(View imageView) {
//		   this.imageView = imageView;
//	   }

//	public void setMap(MapView map) {
//		this.map = map;
//	}

	public void setBarId(String barId) {
		this.barId = barId;
	}
}
