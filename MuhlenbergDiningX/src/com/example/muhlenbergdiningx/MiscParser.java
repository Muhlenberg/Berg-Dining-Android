package com.example.muhlenbergdiningx;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Xml;

public class MiscParser implements Parcelable
{
	public MiscParser(InputStream is) throws XmlPullParserException, IOException
	{
		XmlPullParser parser = Xml.newPullParser();
		parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
		parser.setInput(is, null);
		parser.nextTag();
		
		mDiningLocation mLocation = null;
		String item = null;
		
		int eventType;
		while((eventType = parser.getEventType()) != XmlPullParser.END_DOCUMENT)
		{
			if(eventType == XmlPullParser.START_TAG)
			{
				if(parser.getName().equalsIgnoreCase("location"))
					mLocation = new mDiningLocation(parser.getAttributeValue(1));
				else if(parser.getName().equalsIgnoreCase("item"))
					item = parser.getAttributeValue(0);
			}
			else if(eventType == XmlPullParser.END_TAG)
			{
				if(parser.getName().equalsIgnoreCase("item"))
					mLocation.add(item);
				else if(parser.getName().equalsIgnoreCase("location"))
					continue;
			}
			parser.next();
		}
		is.close();
	}

	public static class mDiningLocation
	{
		private ArrayList<String>items;
		private String name;
		
		public mDiningLocation(String name)
		{
			this.name=name;
			items = new ArrayList<String>();
		}
		
		public void add(String item)
		{
			items.add(item);
		}
		
		public String get(int index)
		{
			return items.get(index);
		}
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		
	}
}

