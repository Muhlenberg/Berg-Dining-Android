package fragments;

import java.util.ArrayList;

import listview.DiningListviewAdapter;
import parsers.MiscParser;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.muhlenbergdiningx.R;

public class LSCCafeFragment extends Fragment
{
	private final int LOCATION = 2;
	private MiscParser parser;
	
	private ListView lscList;
	private DiningListviewAdapter adapter;
	private ArrayList<String>itemString;
	
	public LSCCafeFragment()
	{
		super();
	}
	
	public static LSCCafeFragment newInstance(MiscParser parser)
	{
		LSCCafeFragment m = new LSCCafeFragment();
		Bundle bundle = new Bundle();
		bundle.putParcelable("parser", parser);
		m.setArguments(bundle);
		return m;
	}
	
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		parser = getArguments().getParcelable("parser");
	}
	
	public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle savedInstance)
	{
		View rootView;
		rootView = inflater.inflate(R.layout.lsccafefragment, viewGroup, false);
		setup(rootView);
		rootView.setBackgroundColor(getResources().getColor(R.color.tan));
		return rootView;
	}
	
	private void setup(View v)
	{
		lscList = (ListView) v.findViewById(R.id.lscList);
		itemString = parser.getLocations().get(LOCATION).getItems();
	}
	
	public void onResume()
	{
		super.onResume();
		
		adapter = new DiningListviewAdapter(getActivity(), itemString, false);
		lscList.setAdapter(adapter);
	}
}
