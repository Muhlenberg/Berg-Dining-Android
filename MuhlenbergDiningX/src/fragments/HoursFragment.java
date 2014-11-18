package fragments;

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
import com.example.muhlenbergdiningx.R.color;
import com.example.muhlenbergdiningx.R.id;
import com.example.muhlenbergdiningx.R.layout;

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
		hoursText.setMovementMethod(new ScrollingMovementMethod());
		hoursText.setText("");
		
		Spanned text;
		DiningHours hours;
		for(int i=0;i<parser.getLocationCount();i++)
		{
			hours = parser.getLocations().get(i).getHours();
			text = Html.fromHtml(hours.getHours());
			text = (Spanned) text.subSequence(TextUtils.indexOf(text, "}")+1, text.length());
			hoursText.append(parser.getLocations().get(i).getName() + "\n" + text);
		}
		
	}
	}
