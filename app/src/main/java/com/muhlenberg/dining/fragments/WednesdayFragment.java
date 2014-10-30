package com.muhlenberg.dining.fragments;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

import org.xmlpull.v1.XmlPullParserException;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.Spannable;
import android.text.Spanned;
import android.text.style.BackgroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.TextView;

import com.muhlenberg.dining.DiningGridAdapter;
import com.muhlenberg.dining.NewNodeXmlParser;
import com.muhlenberg.dining.NewNodeXmlParser.DiningPeriod;
import com.muhlenberg.dining.NewNodeXmlParser.DiningStation;
import com.muhlenberg.dining.R;
 
/**
 * This is going to need some fixing up if we use this design
 * @author jmankhan
 *
 */
public class WednesdayFragment extends Fragment implements OnClickListener
{
	private final int BACKCOLOR = Color.rgb(222, 213, 179);
	private int index = -1;
	private TextView itemText, viewAll;
	private CharSequence removedText="Breakfast";
 	
	private TextView[] mealTabs = new TextView[3];
	private ArrayList<ArrayList<DiningStation>> allStations;
	private ArrayList<ArrayList<TextView>> allStationButtons;
	private String[] titles = {"Breakfast", "Lunch", "Dinner"};
	private GridView grid;
	private DiningGridAdapter[] adapters = new DiningGridAdapter[3];
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) 
    {
    	View rootView = inflater.inflate(R.layout.wednesday_fragment, container, false);
    	rootView.setBackgroundColor(BACKCOLOR);
    	return rootView;
    }
    
    public void onResume()
    {
    	super.onResume();
    	
    	NewNodeXmlParser na=null;
    	
    	try 
    	{
			na = new NewNodeXmlParser(getActivity().getAssets().open("muhlenberg_dining.xml"), Calendar.WEDNESDAY);
			na.parse();
		}
    	catch (XmlPullParserException e) {e.printStackTrace();} 
    	catch (IOException e) {e.printStackTrace();}
    	
    	mealTabs[0] = (TextView) getActivity().findViewById(R.id.wednesdayBreakfast);
    	mealTabs[1] = (TextView) getActivity().findViewById(R.id.wednesdayLunch);
    	mealTabs[2] = (TextView) getActivity().findViewById(R.id.wednesdayDinner);
    	
    	for(int i=0;i<mealTabs.length;i++)
    	{
    		mealTabs[i].setText(Html.fromHtml("<b>" + titles[i] + "</b"));
    		mealTabs[i].setTextColor(Color.WHITE);
    		mealTabs[i].setBackgroundColor(Color.RED);
    		mealTabs[i].setOnClickListener(this);
    	}
    	
    	ArrayList<DiningPeriod> allPeriods = na.getDay().getPeriods();
    	allStations = new ArrayList<ArrayList<DiningStation>>();
    	allStationButtons = new ArrayList<ArrayList<TextView>>();
    	
    	//allStations contains 3 ArrayLists (usually), one for each meal
    	//each of these arraylists contains the names for all the available stations for that day
    	//e.g. allStations.get(0) contains the stations available for breakfast
    	for(int i=0;i<allPeriods.size();i++)
    	{
    		ArrayList<DiningStation> newStations = new ArrayList<DiningStation>();
    		for(int j=0;j<allPeriods.get(i).getStations().size();j++)
    		{
    			newStations.add(allPeriods.get(i).getStations().get(j));
    		}
    		allStations.add(newStations);
    	}
    	
    	for(int i=0;i<allStations.size();i++)
    	{
    		ArrayList<TextView> mealButtonList = new ArrayList<TextView>();
    		for(int j=0;j<allStations.get(i).size();j++)
    		{
    			TextView b = new TextView(getActivity());
    			b.setText(allStations.get(i).get(j).getName());
    			mealButtonList.add(b);
    		}
    		allStationButtons.add(mealButtonList);
    	}
    	
    	adapters[0] = new DiningGridAdapter(getActivity(), allStationButtons.get(0));
    	adapters[1] = new DiningGridAdapter(getActivity(), allStationButtons.get(1));
    	adapters[2] = new DiningGridAdapter(getActivity(), allStationButtons.get(2));
    	
    	grid = (GridView) getActivity().findViewById(R.id.wednesdayGrid);

    	//auto select breakfast
    	grid.setAdapter(adapters[0]);
    	mealTabs[0].setBackgroundColor(BACKCOLOR);
    	mealTabs[0].setTextColor(Color.RED);
    	index = 0;

    	itemText = (TextView) getActivity().findViewById(R.id.wednesdayItems);

    	grid.setOnItemClickListener(new OnItemClickListener()
    	{
			@Override
			public void onItemClick(AdapterView<?> parent, View view,int position, long id) 
			{
				grid.setVisibility(View.GONE);
				removedText=mealTabs[index].getText();
				mealTabs[index].setText("Back to\n"+mealTabs[index].getText());
				
				itemText.setText("");
				ArrayList<String> items = allStations.get(index).get(position).getItems();
				for(String s:items)
				{	
					Spanned text = Html.fromHtml("<b><big>" + s + "</b></big><br/>");
					itemText.append(text);
				}
				
				itemText.setVisibility(View.VISIBLE);
				
			}
    		
    	});
    	
    	viewAll = (TextView) getActivity().findViewById(R.id.wednesdayViewAll);
    	viewAll.setOnClickListener(this);
    }

	@Override
	public void onClick(View v) 
	{
		mealTabs[index].setText(titles[index]);  
		if(v.equals(mealTabs[0]))
			index=0;
		if(v.equals(mealTabs[1]))
			index=1;
		if(v.equals(mealTabs[2]))
			index=2;

		if(v.equals(viewAll))
		{
			grid.setVisibility(View.GONE);
			viewAll.setVisibility(View.GONE);
			for(DiningStation ds:allStations.get(index))
			{
				for(String s:ds.getItems())
				{
					itemText.append(s + "\n");
				}
			}
			itemText.setVisibility(View.VISIBLE);
		}
		for(TextView tv:mealTabs)
		{
			tv.setBackgroundColor(Color.RED);
			tv.setTextColor(Color.WHITE);
			if(tv.equals(mealTabs[index]))
			{
				tv.setBackgroundColor(BACKCOLOR);
				tv.setTextColor(Color.RED);				
			}
		}
		
		grid.setVisibility(View.VISIBLE);
		itemText.setVisibility(View.GONE);
		grid.setAdapter(adapters[index]);
		
	}
	
}