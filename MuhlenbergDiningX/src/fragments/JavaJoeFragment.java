package fragments;

import java.util.ArrayList;

import listview.DiningListviewAdapter;
import parsers.MiscParser;
import parsers.MiscParser.mDiningLocation;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.muhlenbergdiningx.R;

public class JavaJoeFragment extends Fragment
{
	private final int LOCATION = 3;
	private MiscParser parser;
	
	private ListView jjList;
	private DiningListviewAdapter adapter;
	private ArrayList<String> itemString;
	
	public JavaJoeFragment()
	{
		super();
	}
	
	public static JavaJoeFragment newInstance(MiscParser parser)
	{
		JavaJoeFragment m = new JavaJoeFragment();
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
		rootView = inflater.inflate(R.layout.javajoefragment, viewGroup, false);
		setup(rootView);
		rootView.setBackgroundColor(getResources().getColor(R.color.tan));
		return rootView;
	}
	
	private void setup(View v)
	{
		jjList = (ListView) v.findViewById(R.id.javajoeList);
		ArrayList<mDiningLocation> locations = parser.getLocations();
		itemString = new ArrayList<String>();
		
		for(int i=0;i<locations.get(LOCATION).size();i++)
		{
			itemString.add(parser.getLocations().get(LOCATION).get(i));
		}
	}
	
	public void onResume()
	{
		super.onResume();
		adapter = new DiningListviewAdapter(getActivity(), itemString, false);
		jjList.setAdapter(adapter);
	}
}
