package com.example.muhlenbergdiningx;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParserException;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.muhlenbergdiningx.DiningXmlParser.DiningStation;

public class DiningFragment extends Fragment implements OnClickListener, OnItemClickListener
{
	private int day = -1;
	private int mealIndex=0;
	
	private GridView gv;
	private ArrayList<ArrayList<DiningStation>> stations;
	private ArrayList<TextView> meals;
	private DiningGridAdapter[] adapters;
	private TextView itemView;
	
	private DiningXmlParser parser;
	
	public DiningFragment()
	{
		super();
	}
	public static DiningFragment newInstance(int day, DiningXmlParser parser)
	{
		DiningFragment f = new DiningFragment();
		Bundle bundle = new Bundle();
		bundle.putInt("day", day);
		bundle.putParcelable("parser", parser);
		f.setArguments(bundle);
		return f;
	}
	
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		day = getArguments().getInt("day");
		parser = getArguments().getParcelable("parser");
	}
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance)
	{
		View rootView=null;

		rootView = inflater.inflate(R.layout.weekday_fragment, container, false);

		if(day!=5 && day!=6)
		{
			rootView = inflater.inflate(R.layout.weekday_fragment, container, false);
			weekdaySetup(rootView);
		}
		else
		{
			rootView = inflater.inflate(R.layout.weekend_fragment, container, false);
			weekendSetup(rootView);
		}
		
		rootView.setBackgroundColor(getResources().getColor(R.color.tan));
		setBackgroundForView(meals.get(0), R.drawable.diningperiodback_selected);
		meals.get(0).setTextColor(Color.RED);
		
		return rootView;
	}
	
	private void weekdaySetup(View v)
	{
		meals = new ArrayList<TextView>(3);
		adapters = new DiningGridAdapter[3];
		stations = new ArrayList<ArrayList<DiningStation>>();
		gv = (GridView) v.findViewById(R.id.weekdayGridView);
		itemView = (TextView) v.findViewById(R.id.weekdayItemView);
		itemView.setMovementMethod(new ScrollingMovementMethod());
		
		meals.add((TextView) v.findViewById(R.id.weekday_breakfast));
		meals.add((TextView) v.findViewById(R.id.weekday_lunch));
		meals.add((TextView) v.findViewById(R.id.weekday_dinner));
		
		int location=0;
		//get all required info for periods and stations from parser and store in lists
		ArrayList<DiningStation> temp = new ArrayList<DiningStation>(); //temp list to get stations for each period, to be added to List<List<station>>
		for(int i=0;i<meals.size();i++)
		{
			meals.get(i).setText(parser.getLocations().get(location).get(day).get(i).getName());
			meals.get(i).setTag(meals.get(i).getText());
			meals.get(i).setOnClickListener(this);
			
			for(int j=0;j<parser.getLocations().get(location).get(day).get(i).size();j++)
				temp.add(parser.getLocations().get(location).get(day).get(i).get(j));
			
			adapters[i] = new DiningGridAdapter(v.getContext(), temp);
			stations.add(temp);
			temp = new ArrayList<DiningStation>();
		}
		temp.clear();

		//add stations info to gridview
		gv.setAdapter(adapters[0]);
		gv.setOnItemClickListener(this);
	}
	
	private void weekendSetup(View v)
	{
		meals = new ArrayList<TextView>(2);
		adapters = new DiningGridAdapter[2];
		stations = new ArrayList<ArrayList<DiningStation>>();
		gv = (GridView) v.findViewById(R.id.weekendGridView);
		itemView = (TextView) v.findViewById(R.id.weekendItemView);
		itemView.setMovementMethod(new ScrollingMovementMethod());

		meals.add((TextView) v.findViewById(R.id.weekend_brunch));
		meals.add((TextView) v.findViewById(R.id.weekend_dinner));

		int location=0;
		//same loop as above
		ArrayList<DiningStation> temp = new ArrayList<DiningStation>();
		for(int i=0;i<meals.size();i++)
		{
			meals.get(i).setText(parser.getLocations().get(location).get(day).get(i).getName());
			meals.get(i).setOnClickListener(this);
			
			for(int j=0;j<parser.getLocations().get(location).get(day).get(i).size();j++)
				temp.add(parser.getLocations().get(location).get(day).get(i).get(j));
			
			adapters[i] = new DiningGridAdapter(v.getContext(), temp);
			stations.add(temp);
			temp = new ArrayList<DiningStation>();
		}
		temp.clear();

		gv.setAdapter(adapters[0]);
		gv.setOnItemClickListener(this);
	}
	
	@SuppressLint("NewApi")
	private void setBackgroundForView(View v, int backID)
	{
		int sdk = android.os.Build.VERSION.SDK_INT;
		if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) 
		    v.setBackgroundDrawable( getResources().getDrawable(backID));
		else 
		    v.setBackground( getResources().getDrawable(backID));
	}
	
	@SuppressLint("NewApi")
	@Override
	public void onClick(View v) 
	{
		//reset all view aesthetics
		for(TextView tv: meals)
		{
			tv.setTextColor(Color.WHITE);
			setBackgroundForView(tv, R.drawable.diningperiodback);
		}
		
		int index=-1;
		for(int i=0;i<meals.size();i++)
			if(v.equals(meals.get(i)))
				index=i;
		
		mealIndex=index;
		//show selected state for clicked view
		((TextView)v).setTextColor(Color.RED);
		setBackgroundForView(v, R.drawable.diningperiodback_selected);
		
		meals.get(index).setText((String) meals.get(index).getTag());
		
		gv.setVisibility(View.VISIBLE);
		itemView.setVisibility(View.GONE);
		
		gv.setAdapter(adapters[index]);
	}
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) 
	{
		DiningXmlParser parser = null;
		InputStream is = null;
		try
		{
			is = getActivity().getAssets().open("mDining.xml");
			parser = new DiningXmlParser(is);
		} 
		catch(IOException ioe) {ioe.printStackTrace(); }
		catch(XmlPullParserException e) {e.printStackTrace(); }
		
		itemView.setText("");
		for(int i=0;i<parser.getLocations().get(0).get(day).get(mealIndex).get(position).size();i++)
			itemView.append(parser.getLocations().get(0).get(day).get(mealIndex).get(position).get(i).getName()+"\n");
		
		gv.setVisibility(View.GONE);
		itemView.setVisibility(View.VISIBLE);
		
		meals.get(mealIndex).setText("Back");
		meals.get(mealIndex).setTextColor(Color.RED);
	}
}
