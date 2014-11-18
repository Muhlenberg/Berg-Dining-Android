package parsers;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Xml;

public class GQParser implements Parcelable
{
	private ArrayList<GQDiningLocation> locations = new ArrayList<GQDiningLocation>();
	
	public GQParser(InputStream is) throws XmlPullParserException, IOException
	{
		XmlPullParser parser = Xml.newPullParser();
		parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
		parser.setInput(is, null);
		parser.nextTag();
		
		GQDiningLocation gqLocation=null;
		GQDiningStation gqStation = null;
		String item = null;
		
		int eventType;
		while((eventType = parser.getEventType()) != XmlPullParser.END_DOCUMENT)
		{
			if(eventType == XmlPullParser.START_TAG)
			{
				if(parser.getName().equalsIgnoreCase("location"))
					gqLocation = new GQDiningLocation(parser.getAttributeValue(1));
				else if(parser.getName().equalsIgnoreCase("station"))
					gqStation = new GQDiningStation(parser.getAttributeValue(0));
				else if(parser.getName().equalsIgnoreCase("item"))
					item = parser.getAttributeValue(0);
			}
			else if(eventType == XmlPullParser.END_TAG)
			{
				if(parser.getName().equalsIgnoreCase("item"))
					gqStation.add(item);
				else if(parser.getName().equalsIgnoreCase("station"))
					gqLocation.add(gqStation);
				else if(parser.getName().equalsIgnoreCase("location"))
					locations.add(gqLocation);
			}
			parser.next();
		}
		is.close();
	}

	public static class GQDiningLocation
	{
		private ArrayList<GQDiningStation>stations;
		private String name;
		
		public GQDiningLocation(String name)
		{
			this.name=name;
			stations = new ArrayList<GQDiningStation>();
		}
		
		public void add(GQDiningStation station)
		{
			stations.add(station);
		}
		
		public GQDiningStation get(int index)
		{
			return stations.get(index);
		}
		
		public int size()
		{
			return stations.size();
		}
		
	}
	
	public static class GQDiningStation
	{
		private ArrayList<String>items;
		private String name;

		public GQDiningStation(String name)
		{
			this.name = name;
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
		
		public int size()
		{
			return items.size();
		}
		
		public String getName()
		{
			return name;
		}
	}
	public ArrayList<GQDiningLocation> getLocations()
	{
		return locations;
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

