package com.example.muhlenbergdiningx;

import java.util.ArrayList;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ContactsGridAdapter extends BaseAdapter
{
	private ArrayList<String> contactInfo;
	private Context context;
	public ContactsGridAdapter(Context c, ArrayList<String>s)
	{
		context=c;
		contactInfo=s;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return contactInfo.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return contactInfo.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) 
	{
		TextView contactTextView = new TextView(context);
		contactTextView.setText(contactInfo.get(position));
		contactTextView.setMinimumHeight(MainActivity.screenHeight/10);
		contactTextView.setGravity(Gravity.CENTER_VERTICAL);
		return contactTextView;
	}
}
