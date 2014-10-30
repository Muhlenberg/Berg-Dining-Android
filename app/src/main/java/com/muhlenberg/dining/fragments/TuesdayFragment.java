package com.muhlenberg.dining.fragments;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

import org.xmlpull.v1.XmlPullParserException;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.Spanned;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.muhlenberg.dining.NewNodeXmlParser;
import com.muhlenberg.dining.NewNodeXmlParser.DiningPeriod;
import com.muhlenberg.dining.NewNodeXmlParser.DiningStation;
import com.muhlenberg.dining.R;

public class TuesdayFragment extends Fragment implements OnClickListener
{
	private final int NUM = 3;
	private final int BACKCOLOR = Color.rgb(222, 213, 179);
	
	private TextView[] meals = new TextView[NUM];
	private TextView[] mealContents = new TextView[NUM];
	private String titles[] = new String[NUM];
	private boolean contentIsShowing[] = new boolean[NUM];

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) 
	{
		View rootView = inflater.inflate(R.layout.tuesday_fragment, container, false);
		rootView.setBackgroundColor(BACKCOLOR);
		return rootView;
	}

	public void onResume()
	{
		super.onResume();
		NewNodeXmlParser na=null;
		try 
		{
			na = new NewNodeXmlParser(getActivity().getAssets().open("muhlenberg_dining.xml"), Calendar.TUESDAY);
			na.parse();
		} 
		catch (XmlPullParserException e) {e.printStackTrace();} 
		catch (IOException e) {e.printStackTrace();}

		//initialize
		meals[0] = (TextView) getActivity().findViewById(R.id.tuesdayBreakfast);
		meals[1] = (TextView) getActivity().findViewById(R.id.tuesdayLunch);
		meals[2] = (TextView) getActivity().findViewById(R.id.tuesdayDinner);

		mealContents[0] = (TextView) getActivity().findViewById(R.id.tuesdayBreakfastContent);
		mealContents[1] = (TextView) getActivity().findViewById(R.id.tuesdayLunchContent);
		mealContents[2] = (TextView) getActivity().findViewById(R.id.tuesdayDinnerContent);
		
		//initialize
		ArrayList<DiningPeriod> dp = na.getDay().getPeriods();
		for(int i=0;i<titles.length;i++)
		{
			titles[i] 			= dp.get(i).getName();
			contentIsShowing[i] = false;
			
			meals[i].setText(Html.fromHtml("<b><big>" + titles[i] + "</big></b>"));
			meals[i].setTextColor(Color.WHITE);
			meals[i].setBackgroundColor(Color.RED);
			meals[i].setOnClickListener(this);
			
			mealContents[i].setMovementMethod(new ScrollingMovementMethod());
			mealContents[i].setVisibility(View.GONE);
			mealContents[i].setBackgroundColor(BACKCOLOR);
		}

		int pass=-1;
		for(DiningPeriod dip : na.getDay().getPeriods())
		{
			pass++;
			for(DiningStation ds : dip.getStations())
			{
				Spanned text = Html.fromHtml("\t" + "<font color='blue'><big>" + ds.getName() + "</font></big><br/>");
				mealContents[pass].append(text);
				for(String i : ds.getItems())
				{
					mealContents[pass].append("\t\t" + i + "\n");
				}
			}
		}

	}

	@Override
	public void onClick(View v) 
	{
		int index = -1;
		if(v.getId() == R.id.tuesdayBreakfast)
		{
			index = 0;
			
			closeAllViews();
			if(!contentIsShowing[index])
			{
				meals[index].setBackgroundColor(BACKCOLOR);
				meals[index].setTextColor(Color.RED);
				mealContents[index].setVisibility(View.VISIBLE);
			}
			
			contentIsShowing[index] = !contentIsShowing[index];
		}
		if(v.getId() == R.id.tuesdayLunch)
		{
			index = 1;
			closeAllViews();
			{
				meals[index].setBackgroundColor(BACKCOLOR);
				meals[index].setTextColor(Color.RED);
				mealContents[index].setVisibility(View.VISIBLE);
			}			
			
			contentIsShowing[index] = !contentIsShowing[index];
		}
		if(v.getId() == R.id.tuesdayDinner)
		{
			index = 2;
			closeAllViews();
			{
				meals[index].setBackgroundColor(BACKCOLOR);
				meals[index].setTextColor(Color.RED);
				mealContents[index].setVisibility(View.VISIBLE);
			}
			
			contentIsShowing[index] = !contentIsShowing[index];
		}
	}

	
	public void closeAllViews()
	{
		for(int i=0;i<NUM;i++)
		{
			meals[i].setBackgroundColor(Color.RED);
			meals[i].setTextColor(Color.WHITE);
			mealContents[i].setVisibility(View.GONE);
			contentIsShowing[i] = false;
		}
	}
}