package com.muhlenberg.dining;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

public class DiningGridAdapter extends BaseAdapter
{
	private final int ROWS = 6;
	
	private Context context;
	private ArrayList<TextView> buttons;
	private View grid;
	
	public DiningGridAdapter(Context c, ArrayList<TextView> b)
	{
		context = c;
		buttons = b;
	}
	
	@Override
	public int getCount() {
		return buttons.size();
	}

	@Override
	public Object getItem(int position) {
		return buttons.get(position);
	}

	@Override
	public long getItemId(int position) {
		return buttons.get(position).getId();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) 
	{
		LayoutInflater lf = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		if(convertView == null)
		{
			grid = new View(context);
			grid = lf.inflate(R.layout.dininggridbutton, null);
			
			TextView b = (TextView) grid.findViewById(R.id.diningbutton);
			b.setText(buttons.get(position).getText());
			b.setTextColor(Color.WHITE);
			b.setMinimumHeight(MainActivity.screenHeight/ROWS);
		}
		else
		{
			grid = convertView;
		}

		return grid;
	}
}
