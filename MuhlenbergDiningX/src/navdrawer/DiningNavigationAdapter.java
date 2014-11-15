package navdrawer;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.muhlenbergdiningx.R;
import com.example.muhlenbergdiningx.DiningXmlParser.DiningLocation;

public class DiningNavigationAdapter extends BaseAdapter
{
	private Context context;
	private ArrayList<DiningNavItem> items;
	
	public DiningNavigationAdapter(Context c, ArrayList<DiningNavItem> i)
	{
		context = c;
		items = i;
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
		
		TextView title = (TextView) convertView.findViewById(R.id.title);
		ImageView icon = (ImageView) convertView.findViewById(R.id.icon);
		
		icon.setImageResource(items.get(position).getIcon());
		title.setText(items.get(position).getTitle());
		
		return convertView;
	}

}
