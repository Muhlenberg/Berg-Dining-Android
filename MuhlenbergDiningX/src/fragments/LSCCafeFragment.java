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

public class LSCCafeFragment extends Fragment
{
	private final int LOCATION = 2;
	private MiscParser parser;
	
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
		TextView lscText = (TextView) v.findViewById(R.id.lscText);
		lscText.setMovementMethod(new ScrollingMovementMethod());
		ArrayList<mDiningLocation> locations = parser.getLocations();
		for(int i=0;i<locations.get(LOCATION).size();i++)
		{
			lscText.append(locations.get(LOCATION).get(i) + "\n");
		}
	}
}
