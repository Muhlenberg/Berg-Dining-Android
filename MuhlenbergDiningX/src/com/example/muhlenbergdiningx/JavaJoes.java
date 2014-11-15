package com.example.muhlenbergdiningx;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class JavaJoes extends Fragment
{
	private final int LOCATION = 5;
	
	private int day;
	private DiningXmlParser parser;
	
	public JavaJoes()
	{
		super();
	}
	
	public static JavaJoes newInstance(int d, DiningXmlParser p)
	{
		JavaJoes j = new JavaJoes();
		Bundle bundle = new Bundle();
		bundle.putInt("day", d);
		bundle.putParcelable("parser", p);
		j.setArguments(bundle);
		return j;
	}
	
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		day = getArguments().getInt("day");
		parser = getArguments().getParcelable("parser");
	}
	
	public View onCreate(LayoutInflater inflater, ViewGroup viewGroup, Bundle savedInstance)
	{
		View rootView = inflater.inflate(R.layout.javajoes, null);
		setup(rootView);
		rootView.setBackgroundColor(getResources().getColor(R.color.tan));
		return rootView;
	}
	
	private void setup(View v)
	{
		int size = parser.getLocations().get(LOCATION).get(0).get(0).get(0).size();
	}
}
