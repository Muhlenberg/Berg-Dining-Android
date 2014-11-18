package navdrawer;

import java.util.ArrayList;
import java.util.Calendar;

import parsers.DiningXmlParser;
import parsers.MiscParser;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.muhlenbergdiningx.DiningImageView;
import com.example.muhlenbergdiningx.MainActivity;
import com.example.muhlenbergdiningx.R;

public class DiningNavigationAdapter extends BaseAdapter
{
	private Context context;
	private ArrayList<DiningNavItem> items;
	private DiningXmlParser parser;
	private MiscParser mParser;
	
	public DiningNavigationAdapter(Context c, ArrayList<DiningNavItem> i, DiningXmlParser p, MiscParser mp)
	{
		context = c;
		items = i;
		parser = p;
		mParser = mp;
	}
	@Override
	public int getCount() 
	{
		return items.size();
	}

	@Override
	public Object getItem(int position) 
	{
		return items.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) 
	{
		if (convertView == null) 
		{
            LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.drawer_list_item, null);
        }
		
		if(items.get(position).isSpinner())
		{
			DiningImageView icon = (DiningImageView) convertView.findViewById(R.id.icon);
			icon.setImageResource(items.get(position).getIcon());
			
			Spinner spinner = (Spinner) convertView.findViewById(R.id.spinner);
			ArrayAdapter<CharSequence> sadapter = ArrayAdapter.createFromResource(context, R.array.spinner_items, android.R.layout.simple_spinner_item);
			sadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			spinner.setAdapter(sadapter);
			spinner.setVisibility(View.VISIBLE);
			spinner.setOnItemSelectedListener(((MainActivity)context));
		}
		else
		{
			TextView title = (TextView) convertView.findViewById(R.id.title);
			DiningImageView icon = (DiningImageView) convertView.findViewById(R.id.icon);
			
			icon.setImageResource(items.get(position).getIcon());
			title.setText(items.get(position).getTitle());
			title.setVisibility(View.VISIBLE);
		}
		
		return convertView;
	}

	
	private int getNumDay()
	{
		Calendar cal = Calendar.getInstance();
		int currentDay = cal.get(Calendar.DAY_OF_WEEK);
		switch(currentDay)
		{
		case Calendar.SUNDAY: 	return 6;
		case Calendar.MONDAY: 	return 0;
		case Calendar.TUESDAY: 	return 1;
		case Calendar.WEDNESDAY:return 2;
		case Calendar.THURSDAY: return 3;
		case Calendar.FRIDAY: 	return 4;
		case Calendar.SATURDAY: return 5;
		default: return 0;
		}
	}
}
