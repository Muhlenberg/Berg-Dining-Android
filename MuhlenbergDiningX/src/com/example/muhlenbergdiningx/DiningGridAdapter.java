package com.example.muhlenbergdiningx;

import java.util.ArrayList;

import parsers.DiningXmlParser.DiningStation;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class DiningGridAdapter extends BaseAdapter
{
	private final int ROWS = 6;
	private ArrayList<DiningStation> stations;
	private Context context;
	
	public DiningGridAdapter(Context c, ArrayList<DiningStation> s)
	{
		context = c;
		stations = s;
	}
	
	@Override
	public int getCount() {
		return stations.size();
	}

	@Override
	public Object getItem(int position) {
		return stations.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) 
	{
		TextView stationTextView;
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		if(convertView==null)
		{
			stationTextView=(TextView) inflater.inflate(R.layout.dininggridbutton, parent, false);
			stationTextView.setText(stations.get(position).getName());
			stationTextView.setMinimumHeight(MainActivity.screenHeight/ROWS);
		}
		else
			stationTextView=(TextView)convertView;
		
		return stationTextView;
	}

}
