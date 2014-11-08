package com.example.muhlenbergdiningx;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.util.Log;
import android.util.Xml;

public class DiningXmlParser
{
	private ArrayList<DiningLocation> locations = new ArrayList<DiningLocation>();
	public DiningXmlParser(InputStream is) throws XmlPullParserException, IOException
	{
		XmlPullParser parser = Xml.newPullParser();
		parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
		parser.setInput(is, null);
		parser.nextTag();
		
		String dayName = getDay();
		
		DiningLocation location = null;
		DiningDay day = null;
		DiningPeriod period = null;
		DiningStation station = null;
		DiningItem item = null;
		
		boolean correctDay=false;
		int eventType;
		while((eventType = parser.getEventType()) != XmlPullParser.END_DOCUMENT)
		{
			if(eventType == XmlPullParser.START_TAG)
			{
				if(parser.getName().equalsIgnoreCase("location"))
					location = new DiningLocation(parser.getAttributeValue(0));
				else if(parser.getName().equalsIgnoreCase("day") && parser.getAttributeValue(1).contains(dayName))
				{
					day = new DiningDay(parser.getAttributeValue(1));
					correctDay=true;
				}
				else if(parser.getName().equalsIgnoreCase("period"))
					period = new DiningPeriod(parser.getAttributeValue(0));
				else if(parser.getName().equalsIgnoreCase("station"))
					station = new DiningStation(parser.getAttributeValue(0));
				else if(parser.getName().equalsIgnoreCase("item"))
					item = new DiningItem(parser.getAttributeValue(0));
			}
			else if(eventType == XmlPullParser.END_TAG)
			{
				if(parser.getName().equalsIgnoreCase("item")&&correctDay)
					station.add(item);
				else if(parser.getName().equalsIgnoreCase("station")&&correctDay)
					period.add(station);
				else if(parser.getName().equalsIgnoreCase("period")&&correctDay)
					day.add(period);
				else if(parser.getName().equalsIgnoreCase("day")&&correctDay)
					location.add(day);
				else if(parser.getName().equalsIgnoreCase("location")&&correctDay)
					locations.add(location);
			}
			
			parser.next();
		}
		
		is.close();
	}
	
	private String getDay()
	{
		Calendar cal = Calendar.getInstance();
		int day = cal.get(Calendar.DAY_OF_WEEK);
		
		switch(day)
		{
		case Calendar.MONDAY: return "Monday";
		case Calendar.TUESDAY: return "Tuesday";
		case Calendar.WEDNESDAY: return "Wednesday";
		case Calendar.THURSDAY: return "Thursday";
		case Calendar.FRIDAY: return "Friday";
		case Calendar.SATURDAY: return "Saturday";
		case Calendar.SUNDAY: return "Sunday";
		default: return "what";
		}
	}
	
	public static class DiningLocation
	{
		private String name;
		private ArrayList<DiningDay> days;
		
		public DiningLocation(String name)
		{
			this.name = name;
			days = new ArrayList<DiningDay>();
		}
		
		public void add(DiningDay d)
		{
			days.add(d);
		}
		
		public DiningDay get(int day)
		{
			return days.get(day);
		}
		
		public String getName()
		{
			return name;
		}
	}
	public static class DiningDay
	{
		private String name;
		private ArrayList<DiningPeriod> periods;
		
		public DiningDay(String name)
		{
			this.name = name;
			periods = new ArrayList<DiningPeriod>();
		}
		
		public void add(DiningPeriod p)
		{
			periods.add(p);
		}
		
		public DiningPeriod get(int period)
		{
			return periods.get(period);
		}
		
		public String getName()
		{
			return name;
		}
		
		public int size()
		{
			return periods.size();
		}
	}
	public static class DiningPeriod
	{
		private String name;
		private ArrayList<DiningStation> stations;
		
		public DiningPeriod(String name)
		{
			this.name = name;
			stations = new ArrayList<DiningStation>();
		}
		
		public void add(DiningStation s)
		{
			stations.add(s);
		}
		
		public DiningStation get(int station)
		{
			return stations.get(station);
		}
		
		public String getName()
		{
			return name;
		}
		
		public int size()
		{
			return stations.size();
		}
	}
	public static class DiningStation
	{
		private String name;
		private ArrayList<DiningItem> items;
		
		public DiningStation(String name)
		{
			this.name = name;
			items = new ArrayList<DiningItem>();
		}
		
		public void add(DiningItem i)
		{
			items.add(i);
		}
		
		public DiningItem get(int item)
		{
			return items.get(item);
		}
		
		public String getName()
		{
			return name;
		}
		
		public int size()
		{
			return items.size();
		}
	}
	public static class DiningItem
	{
		private String name;
		private String description;
		
		public DiningItem(String name)
		{
			this.name = name;
			description = "";
		}
		
		public void setDescription(String s)
		{
			description = s;
		}
		
		public String getDescription()
		{
			return description;
		}
		
		public String getName()
		{
			return name;
		}
	}
	
	public ArrayList<DiningLocation> getLocations()
	{
		return locations;
	}
	
	public int getLocationCount()
	{
		return locations.size();
	}
	
	public int getPeriodCount(int location, int day)
	{
		return locations.get(location).get(day).size();
	}
}