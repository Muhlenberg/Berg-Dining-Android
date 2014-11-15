package navdrawer;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.muhlenbergdiningx.DiningImageView;
import com.example.muhlenbergdiningx.R;

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
		
		if(items.get(position).isSpinner())
		{
			DiningImageView icon = (DiningImageView) convertView.findViewById(R.id.icon);
			icon.setImageResource(items.get(position).getIcon());
			
			Spinner spinner = (Spinner) convertView.findViewById(R.id.spinner);
			ArrayAdapter<CharSequence> sadapter = ArrayAdapter.createFromResource(context, R.array.spinner_items, android.R.layout.simple_spinner_item);
			sadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			spinner.setAdapter(sadapter);
			spinner.setVisibility(View.VISIBLE);
//			spinner.setOnItemClickListener(new OnItemClickListener()
//			{
//				@Override
//				public void onItemClick(AdapterView<?> parent, View view, int position, long id) 
//				{
//					Toast.makeText(context, "clicked", Toast.LENGTH_SHORT).show();
//				}
//				
//			});
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

}
