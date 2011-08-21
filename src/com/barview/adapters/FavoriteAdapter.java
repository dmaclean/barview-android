package com.barview.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.barview.R;
import com.barview.listeners.FavoriteDeleteOnClickListener;
import com.barview.models.Favorite;

public class FavoriteAdapter extends ArrayAdapter<Favorite> {
	private ArrayList<Favorite> items;
	
	private ArrayList<String> barIds;
	
	private Context context;

	public FavoriteAdapter(Context context, int textViewResourceId, ArrayList<Favorite> items) {
		super(context, textViewResourceId, items);
		this.items = items;
		this.context = context;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		if (v == null) {
			LayoutInflater vi = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = vi.inflate(R.layout.row, null);
		}
		Favorite o = items.get(position);
		if (o != null) {
			TextView tt = (TextView) v.findViewById(R.id.toptext);
			
			if (tt != null) {
				tt.setText(o.getBarName());
			}
			
			Button delete = (Button) v.findViewById(R.id.favesDeleteButton);
			FavoriteDeleteOnClickListener listener = new FavoriteDeleteOnClickListener(context, position, items, barIds, this);
			delete.setOnClickListener(listener);
		}
		return v;
	}

	public void setBarIds(ArrayList<String> barIds) {
		this.barIds = barIds;
	}
	
	
}
