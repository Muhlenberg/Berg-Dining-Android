package fragments;

import java.util.ArrayList;

import parsers.DiningXmlParser;
import parsers.DiningXmlParser.DiningHours;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.muhlenbergdiningx.R;

public class HoursFragment extends Fragment 
{
	private DiningXmlParser parser;

	public HoursFragment()
	{
		super();
	}

	public static HoursFragment newInstance(DiningXmlParser parser)
	{
		HoursFragment h = new HoursFragment();
		Bundle bundle = new Bundle();
		bundle.putParcelable("parser", parser);
		h.setArguments(bundle);
		return h;
	}

	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		parser = getArguments().getParcelable("parser");
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle savedInstanceState)
	{
		View rootView;
		rootView = inflater.inflate(R.layout.hoursfragment, viewGroup, false);
		setup(rootView);
		rootView.setBackgroundColor(getResources().getColor(R.color.tan));
		return rootView;
	}

	private void setup(View v)
	{
		TextView hoursText = (TextView) v.findViewById(R.id.hoursText);
		TextView hoursText2 = (TextView) v.findViewById(R.id.hoursText2);
		Spanned text;
		DiningHours hours;

		StringBuilder days = new StringBuilder();
		StringBuilder times = new StringBuilder();
		
		for(int i=0;i<parser.getLocationCount();i++)
		{
			hours = parser.getLocations().get(i).getHours();
			text = Html.fromHtml(hours.getHours());
			text = (Spanned) text.subSequence(TextUtils.indexOf(text, "}")+1, text.length());
			
			String adjusted = text.toString().replaceAll("(?m)^[ \t]*\r?\n", ""); //regex - eliminates empty lines
			String toRemove = adjusted.substring(17, 21);
			adjusted = adjusted.replace(toRemove, " ");
			adjusted = adjusted.replaceAll("to", "-");
			adjusted = adjusted.replaceAll(" ", "");

			//split up days and times so i can format better, evens are days, odds are times
			String[] dummy = adjusted.split("\\s");
			ArrayList<String> parts = new ArrayList<String>();
			for(String s:dummy)
				parts.add(s);
			
			for(int j=0;j<parts.size();j++)
			{
				if(j%2==0)
					days.append(parts.get(j) + "\n");
				else
					times.append(parts.get(j) + "\n");
			}
				
			String name = parser.getLocations().get(i).getName();
			if(i==0)
				name="Wood Dining";
			
			hoursText.append(name + "\n" + days.toString() + "\n");
			hoursText2.append("\n" + times.toString() + "\n");
			days = new StringBuilder();
			times = new StringBuilder();
		}
	}
}