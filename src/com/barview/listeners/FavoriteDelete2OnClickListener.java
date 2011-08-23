package com.barview.listeners;

import java.util.ArrayList;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.barview.FavoritesActivity;
import com.barview.R;
import com.barview.models.Favorite;
import com.barview.rest.FavoriteDeleter;

/**
 * What a fucking mess.
 * 
 * This click listener is in charge of handling clicks on the Dialog Alert for favorite deletes.
 * Unfortunately, I've tasked this class with not only invoking the REST call, but also maintaining
 * data structures for the bar ids and favorites lists that live in the FavoritesActivity.
 * 
 * @author dmaclean
 *
 */
public class FavoriteDelete2OnClickListener implements OnClickListener {

	/**
	 * The activity context.
	 */
	private Context context;
	
	/**
	 * The row position of the delete button being clicked.
	 */
	private int position;
	
	/**
	 * The list of favorites currently available.
	 */
	private ArrayList<Favorite> favorites;
	
	/**
	 * The list of bar ids that match up with the list rows.
	 */
	private ArrayList<String> barIds;
	
	/**
	 * A reference to the favorites activity that invoked this.
	 */
	private FavoritesActivity favoritesActivity;
	
	public FavoriteDelete2OnClickListener(	Context context, 
											int position, 
											ArrayList<Favorite> favorites,
											ArrayList<String> barIds,
											FavoritesActivity favoritesActivity) {
		this.context = context;
		this.position = position;
		this.favorites = favorites;
		this.barIds = barIds;
		this.favoritesActivity = favoritesActivity;
	}
	
	/**
	 * The callback for the click handler.
	 * 
	 * This method will call the REST interface, update the favorites and bar ids lists (delete the item
	 * living at the index represented by "position"), and reload the array in the list adapter (this
	 * is why we need a reference to the FavoritesActivity - so we can call setListAdapter() ).
	 */
	public void onClick(DialogInterface dialog, int which) {
		// Just get out of here if the user clicked "NO".
		if(which == DialogInterface.BUTTON_NEGATIVE)
			return;
		
		FavoriteDeleter deleter = new FavoriteDeleter();
		deleter.execute(barIds.get(position));
		
		Favorite favorite = favorites.get(position);
		
		barIds.remove(position);
		favorites.remove(position);
		
		String[] favesArray = new String[favorites.size()];
		int i= 0;
		for(Favorite f : favorites)
			favesArray[i++] = f.getBarName();
			
		favoritesActivity.setListAdapter(new ArrayAdapter<String>(favoritesActivity, R.layout.row, favesArray));
		
		Toast toast = Toast.makeText(context, "Successfully deleted " + favorite.getBarName() + " from favorites.", Toast.LENGTH_SHORT);
		toast.show();
	}

}
