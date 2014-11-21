package fragments;

import java.util.ArrayList;

import parsers.MiscParser;
import parsers.MiscParser.mDiningLocation;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.muhlenbergdiningx.R;

public class JavaJoeFragment extends Fragment
{
	private final int LOCATION = 3;
	private MiscParser parser;
	
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
		TextView javajoeText = (TextView) v.findViewById(R.id.javajoeText);
		ArrayList<mDiningLocation> locations = parser.getLocations();
		for(int i=0;i<locations.get(LOCATION).size();i++)
		{
			javajoeText.append(locations.get(LOCATION).get(i) + "\n");
		}
	}
}
