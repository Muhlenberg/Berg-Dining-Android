package listview;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.muhlenbergdiningx.DiningImageView;
import com.example.muhlenbergdiningx.R;

public class DiningListviewAdapter extends BaseAdapter
{
	private Context context;
	private ArrayList<String> items;
	private boolean showIcon;
	
	public DiningListviewAdapter(Context c, ArrayList<String> i)
	{
		context	= c;
		items 	= i;
		showIcon=true;
	}

	public DiningListviewAdapter(Context c, ArrayList<String>i, boolean s)
	{
		context = c;
		items   = i;
		showIcon= s;
	}
	
	@Override
	public int getCount() {
		return items.size();
	}

	@Override
	public Object getItem(int position) {
		return items.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@SuppressLint("NewApi")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) 
	{
		if(convertView == null)
		{
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.dininglistviewitem, parent, false);
		}
		
		TextView text = (TextView) convertView.findViewById(R.id.itemListText);
		text.setText(items.get(position));

		convertView.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v) 
			{
				AlertDialog.Builder builder = new AlertDialog.Builder(context);
				builder.setMessage("Calories:\nFat:\nSugars:\n");
				builder.setNegativeButton(null, null);
				builder.setPositiveButton("OK", new DialogInterface.OnClickListener(){
					@Override
					public void onClick(DialogInterface dialog, int which) {
					}
				});
				builder.create().show();
			}
			
		});
		
		if(!showIcon)
		{
			DiningImageView icon = (DiningImageView) convertView.findViewById(R.id.itemListIcon);
			icon.setImageAlpha(00);
			convertView.setOnClickListener(null);
		}

		return convertView;
	}

	public void setItems(ArrayList<String> newItems)
	{
		items = newItems;
	}

}
