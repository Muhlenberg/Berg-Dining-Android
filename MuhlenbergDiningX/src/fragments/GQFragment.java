package fragments;

import parsers.GQParser;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.method.ScrollingMovementMethod;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.muhlenbergdiningx.R;

public class GQFragment extends Fragment
{
	private final int LOCATION = 1;
	private GQParser parser;
	
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
		TextView gqText = (TextView) v.findViewById(R.id.gqText);
		int stations = parser.getLocations().get(1).size();
		for(int i=0;i<stations;i++)
		{
			String stationName = parser.getLocations().get(LOCATION).get(i).getName();
			Spannable text = new SpannableString(stationName);
			text.setSpan(new ForegroundColorSpan(Color.RED), 0, stationName.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
			
			gqText.append(text);
			gqText.append("\n");//span makes things weird a bit
			for(int j=0;j<parser.getLocations().get(LOCATION).get(i).size(); j++)
			{
				gqText.append("\t\t" + parser.getLocations().get(LOCATION).get(i).get(j) + "\n");
			}
		}
	}
}
