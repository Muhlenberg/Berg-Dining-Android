package com.muhlenberg.dining.fragments;

import java.io.IOException;
import java.util.Calendar;

import org.xmlpull.v1.XmlPullParserException;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.muhlenberg.dining.NewNodeXmlParser;
import com.muhlenberg.dining.R;
import com.muhlenberg.dining.NewNodeXmlParser.DiningPeriod;
import com.muhlenberg.dining.NewNodeXmlParser.DiningStation;

public class FridayFragment extends Fragment {
 
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.friday_fragment, container, false);
        
        return rootView;
    }
    
    public void onResume()
    {
    	super.onResume();
    	
    	NewNodeXmlParser na=null;
    	TextView tv = (TextView) getActivity().findViewById(R.id.fridayText);
    	
    	try 
    	{
			na = new NewNodeXmlParser(getActivity().getAssets().open("muhlenberg_dining.xml"), Calendar.FRIDAY);
			na.parse();
		} 
    	catch (XmlPullParserException e) {e.printStackTrace();} 
    	catch (IOException e) {e.printStackTrace();}
    	
    	//sort through node lists and display appropriately
    	for(DiningPeriod p: na.getDay().getPeriods())
		{
        	Spanned text = Html.fromHtml("<font color = #ff0000><b>" + p.getName() + "</b></font>");
        	tv.append("\n");
			tv.append(text);
			for(DiningStation s : p.getStations())
			{
				text = Html.fromHtml("<font color = #0000ff>" + s.getName() + "</font>");
				tv.append("\n" + "\t");
				tv.append(text);
				for(String i : s.getItems())
				{
					tv.setTextColor(Color.BLACK);
					tv.append("\n" + "\u0009" + "\u0009" + i);
				}
			}
		}
    }
}