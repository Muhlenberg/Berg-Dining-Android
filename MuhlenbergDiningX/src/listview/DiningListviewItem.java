package listview;

import android.widget.TextView;

import com.example.muhlenbergdiningx.DiningImageView;

public class DiningListviewItem 
{
	private TextView text;
	private DiningImageView icon;
	
	public DiningListviewItem(TextView t)
	{
		text = t;
	}
	
	public DiningListviewItem(TextView t, DiningImageView i)
	{
		text = t;
		icon = i;
	}
	
	public TextView getTextView()
	{
		return text;
	}
	
	public DiningImageView getIcon()
	{
		return icon;
	}
}
