package fragments;

import java.util.ArrayList;

import listview.DiningExpandableListAdapter;
import parsers.GQParser;
import parsers.GQParser.GQDiningStation;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.example.muhlenbergdiningx.R;

public class GQFragment extends Fragment
{
	private final int LOCATION = 1;
	private GQParser parser;
	
	private ExpandableListView gqList;
	private DiningExpandableListAdapter adapter;
	private ArrayList<GQDiningStation> stations;
	
	public GQFragment()
	{
		super();
	}
	
	public static GQFragment newInstance(GQParser parser)
	{
		GQFragment g = new GQFragment();
		Bundle bundle = new Bundle();
		bundle.putParcelable("parser", parser);
		g.setArguments(bundle);
		return g;
	}
	
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		parser = getArguments().getParcelable("parser");
	}
	
	public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle savedInstance)
	{
		View rootView;
		rootView = inflater.inflate(R.layout.gqfragment, viewGroup, false);
		setup(rootView);
		rootView.setBackgroundColor(getResources().getColor(R.color.tan));
		return rootView;
	}
	
	private void setup(View v)
	{
		gqList = (ExpandableListView)v.findViewById(R.id.gqList);
		stations = parser.getLocations().get(LOCATION).getStations();
	}
	
	public void onResume()
	{
		super.onResume();
		adapter = new DiningExpandableListAdapter(getActivity(), stations);
		gqList.setAdapter(adapter);
	}
}
