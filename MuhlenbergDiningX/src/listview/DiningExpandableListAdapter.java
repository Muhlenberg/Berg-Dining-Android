package listview;

import java.util.ArrayList;

import parsers.DiningXmlParser.DiningStation;
import parsers.GQParser.GQDiningStation;
import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckedTextView;
import android.widget.TextView;

import com.example.muhlenbergdiningx.R;

public class DiningExpandableListAdapter extends BaseExpandableListAdapter
{
	private Context context;
	private ArrayList<GQDiningStation> stations;
	
	public DiningExpandableListAdapter(FragmentActivity activity, ArrayList<GQDiningStation> stations2) 
	{
		context = activity;
		stations = stations2;
	}

	@Override
	public int getGroupCount() {
		return stations.size();
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		// TODO Auto-generated method stub
		return stations.get(groupPosition).size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getGroupId(int groupPosition) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) 
	{
		if(convertView==null)
		{
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.expandlistgroup, parent, false);
		}
		
	    ((CheckedTextView) convertView).setText(stations.get(groupPosition).getName());
	    ((CheckedTextView) convertView).setChecked(isExpanded);

		return convertView;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) 
	{
		if(convertView==null)
		{
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.expandlistrow, parent, false);	
		}
		
		TextView text = (TextView) convertView.findViewById(R.id.expandlistText);
		text.setText(stations.get(groupPosition).get(childPosition));
		
		return convertView;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return false;
	}

}
