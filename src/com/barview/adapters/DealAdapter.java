package com.barview.adapters;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.barview.R;
import com.barview.models.Deal;

public class DealAdapter extends ArrayAdapter<Deal> {

	private ArrayList<Deal> deals;
	private ArrayList<String> dealBarNames;
	private LinkedHashMap<String, ArrayList<String>> dealsMap;
	private Context context;
	
	public DealAdapter(Context context, int textViewResourceId, ArrayList<Deal> deals) {
		super(context, textViewResourceId, deals);
		
		this.context = context;
		this.deals = deals;
		dealsMap = new LinkedHashMap<String, ArrayList<String>>();
		dealBarNames = new ArrayList<String>();
		
		for(Deal d : deals) {
			if(!dealsMap.containsKey(d.getBarName())) {
				ArrayList<String> a = new ArrayList<String>();
				dealsMap.put(d.getBarName(), a);
				dealBarNames.add(d.getBarName());
			}
		
			ArrayList<String> a = dealsMap.get(d.getBarName());
			a.add(d.getDetail());
		}
	}
	
	@Override
    public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		if (v == null) {
			LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = vi.inflate(R.layout.deal_row, null);
		}
		Deal d = deals.get(position);
		ArrayList<String> list = dealsMap.get(dealBarNames.get(position));
		if (d != null) {
			TextView tt = (TextView) v.findViewById(R.id.deal_bar);
			TextView bt = (TextView) v.findViewById(R.id.deal_detail);
			if (tt != null)
				tt.setText(dealBarNames.get(position));
			if(bt != null) {
				StringBuffer s = new StringBuffer();
				for(String detail : list)
					s.append(detail + "\n\n");
				String value = s.toString();
				bt.setText(value.substring(0, value.lastIndexOf("\n\n")));
			}
		}
		return v;
    }
	
	public int getCount() {
		return dealsMap.size();
	}
	
	public LinkedHashMap<String, ArrayList<String>> getDealsMap() {
		return dealsMap;
	}
}