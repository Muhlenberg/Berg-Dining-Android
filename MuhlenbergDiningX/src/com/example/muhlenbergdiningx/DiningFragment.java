package com.example.muhlenbergdiningx;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParserException;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
	private boolean weekday;
	
	private GridView gv;
	private ArrayList<ArrayList<DiningStation>> stations;
	private ArrayList<TextView> meals;
	private DiningGridAdapter[] adapters;
	
	public DiningFragment(int day)
	{
		this.day=day;
	}
	
	public void setDay(int day)
	{
		this.day=day;
	}
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance)
	{
		View rootView=null;
		if(day!=5 && day!=6)
		{
			weekday=true;
			rootView = inflater.inflate(R.layout.weekday_fragment, container, false);
		}
		else
		{
			weekday=false;
			rootView = inflater.inflate(R.layout.weekend_fragment, container, false);
		}
		
		rootView.setBackgroundColor(getResources().getColor(R.color.tan));
		return rootView;
	}
	
	@Override
	public void onResume()
	{
		super.onResume();
		
		Toast.makeText(getActivity(), getTag(), Toast.LENGTH_SHORT).show();

		DiningXmlParser parser = null;
		InputStream is = null;
		try
		{
			is = getActivity().getAssets().open("mDining.xml");
			parser = new DiningXmlParser(is);
		} 
		catch(IOException ioe) {ioe.printStackTrace(); }
		catch(XmlPullParserException e) {e.printStackTrace(); }
		
	
		if(weekday)
			weekdaySetup(parser);
		else
			weekendSetup(parser);
		
	}
	
	private void weekdaySetup(DiningXmlParser parser)
	{
		meals = new ArrayList<TextView>(3);
		adapters = new DiningGridAdapter[3];
		stations = new ArrayList<ArrayList<DiningStation>>();
		gv = (GridView) getActivity().findViewById(R.id.weekdayGridView);
		
		meals.add((TextView) getActivity().findViewById(R.id.weekday_breakfast));
		meals.add((TextView) getActivity().findViewById(R.id.weekday_lunch));
		meals.add((TextView) getActivity().findViewById(R.id.weekday_dinner));
		
		//get all required info for periods and stations from parser and store in lists
		ArrayList<DiningStation> temp = new ArrayList<DiningStation>(); //temp list to get stations for each period, to be added to List<List<station>>
		for(int i=0;i<meals.size();i++)
		{
			meals.get(i).setText(parser.getLocations().get(0).get(0).get(i).getName());
			meals.get(i).setOnClickListener(this);
			
			for(int j=0;j<3;j++)
			{
				temp.add(parser.getLocations().get(0).get(0).get(i).get(j));
			}
			
			adapters[i] = new DiningGridAdapter(getActivity(), temp);
			stations.add(temp);
			temp = new ArrayList<DiningStation>();
		}
		temp.clear();

		//add stations info to gridview
		gv.setAdapter(adapters[0]);
		gv.setOnItemClickListener(this);
		
		setBackgroundForView(meals.get(0), R.drawable.diningperiodback_selected);
		meals.get(0).setTextColor(Color.WHITE);
	}
	
	
	private void weekendSetup(DiningXmlParser parser)
	{
		meals = new ArrayList<TextView>(2);
		adapters = new DiningGridAdapter[2];
		stations = new ArrayList<ArrayList<DiningStation>>();
		gv = (GridView) getActivity().findViewById(R.id.weekendGridView);
		
		meals.add((TextView) getActivity().findViewById(R.id.weekend_brunch));
		meals.add((TextView) getActivity().findViewById(R.id.weekend_dinner));

		//same loop as above
		ArrayList<DiningStation> temp = new ArrayList<DiningStation>();
		for(int i=0;i<meals.size();i++)
		{
			meals.get(i).setText(parser.getLocations().get(0).get(0).get(i).getName());
			meals.get(i).setOnClickListener(this);
			
			for(int j=0;j<parser.getLocations().get(0).get(0).get(i).size();j++)
			{
				temp.add(parser.getLocations().get(0).get(0).get(i).get(j));
			}
			
			adapters[i] = new DiningGridAdapter(getActivity(), temp);
			stations.add(temp);
			temp = new ArrayList<DiningStation>();
		}
		temp.clear();

		gv.setAdapter(adapters[0]);
		gv.setOnItemClickListener(this);
		
		setBackgroundForView(meals.get(0), R.drawable.diningperiodback_selected);
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
		
		//show selected state for clicked view
		((TextView)v).setTextColor(Color.RED);
		setBackgroundForView(v, R.drawable.diningperiodback_selected);
		
		gv.setAdapter(adapters[index]);
	}
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) 
	{
		
	}
}
