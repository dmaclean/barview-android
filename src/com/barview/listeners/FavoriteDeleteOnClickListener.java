package com.barview.listeners;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.barview.adapters.FavoriteAdapter;
import com.barview.models.Favorite;
import com.barview.rest.FavoriteDeleter;

public class FavoriteDeleteOnClickListener implements OnClickListener {

	/**
	 * The activity context.
	 */
	private Context context;
	
	/**
	 * The row position of the delete button being clicked.
	 */
	private int position;
	
	private ArrayList<Favorite> favorites;
	
	private ArrayList<String> barIds;
	
	private FavoriteAdapter adapter;
	
	public FavoriteDeleteOnClickListener(	Context context, 
											int position, 
											ArrayList<Favorite> favorites,
											ArrayList<String> barIds,
											FavoriteAdapter adapter) {
		this.context = context;
		this.position = position;
		this.favorites = favorites;
		this.barIds = barIds;
		this.adapter = adapter;
	}
	

	public void onClick(View v) {
		FavoriteDeleter deleter = new FavoriteDeleter();
		deleter.execute(barIds.get(position));
		
		Favorite favorite = adapter.getItem(position);
		
		/*
		 * The call to the REST service was successful.  Now we need to update the interface objects
		 * by deleting the entry from the bar ids list as well as the row in the adapter list.
		 * 
		 * Finally, we'll set a Toast to alert the user that the entry has been deleted.
		 */
//		if(deleter.getResult().equals(FavoriteDeleter.SUCCESS)) {
			barIds.remove(position);
			adapter.remove(favorite);
			adapter.notifyDataSetChanged();
			
			Toast toast = Toast.makeText(context, "Successfully deleted " + favorite.getBarName() + " from favorites.", Toast.LENGTH_SHORT);
			toast.show();
//		}
//		else {
//			Toast toast = Toast.makeText(context, "Unable to delete " + favorite.getBarName() + " from favorites.", Toast.LENGTH_SHORT);
//			toast.show();
//		}
	}


	public void setContext(Context context) {
		this.context = context;
	}


	public void setPosition(int position) {
		this.position = position;
	}


	public void setFavorites(ArrayList<Favorite> favorites) {
		this.favorites = favorites;
	}
	

}
