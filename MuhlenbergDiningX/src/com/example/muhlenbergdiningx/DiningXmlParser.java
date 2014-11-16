package com.example.muhlenbergdiningx;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import android.util.Xml;

public class DiningXmlParser implements Parcelable
{
	private ArrayList<DiningLocation> locations = new ArrayList<DiningLocation>();
	private ArrayList<DiningContacts> contacts = new ArrayList<DiningContacts>();
	
	public DiningXmlParser(InputStream is) throws XmlPullParserException, IOException
	{
		XmlPullParser parser = Xml.newPullParser();
		parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
		parser.setInput(is, null);
		parser.nextTag();
		
		DiningLocation location = null;
		DiningDay day = null;
		DiningPeriod period = null;
		DiningStation station = null;
		DiningItem item = null;
		
		DiningHours hours = null;
		DiningContacts contact = null;
		
		int eventType;
		while((eventType = parser.getEventType()) != XmlPullParser.END_DOCUMENT)
		{
			if(eventType == XmlPullParser.START_TAG)
			{
				if(parser.getName().equalsIgnoreCase("location"))
					location = new DiningLocation(parser.getAttributeValue(1));
				else if(parser.getName().equalsIgnoreCase("day"))
					day = new DiningDay(parser.getAttributeValue(1));
				else if(parser.getName().equalsIgnoreCase("period"))
					period = new DiningPeriod(parser.getAttributeValue(0));
				else if(parser.getName().equalsIgnoreCase("station"))
					station = new DiningStation(parser.getAttributeValue(0));
				else if(parser.getName().equalsIgnoreCase("item"))
					item = new DiningItem(parser.getAttributeValue(0));
				
				else if(parser.getName().equalsIgnoreCase("contact"))
					contact = new DiningContacts();
				else if(parser.getName().equalsIgnoreCase("name"))
					contact.setName(parser.nextText()); //reads text AFTER start tag
				else if(parser.getName().equalsIgnoreCase("title"))
					contact.setTitle(parser.nextText());
				else if(parser.getName().equalsIgnoreCase("email"))
					contact.setEmail(parser.nextText());
			}
			
			if(eventType == XmlPullParser.CDSECT)
				hours = new DiningHours(parser.getText());
			
			else if(eventType == XmlPullParser.END_TAG)
			{
				if(parser.getName().equalsIgnoreCase("item"))
					station.add(item);
				else if(parser.getName().equalsIgnoreCase("station"))
					period.add(station);
				else if(parser.getName().equalsIgnoreCase("period"))
					day.add(period);
				else if(parser.getName().equalsIgnoreCase("day"))
					location.add(day);
				else if(parser.getName().equalsIgnoreCase("location"))
					locations.add(location);
				else if(parser.getName().equalsIgnoreCase("hours"))
					location.setHours(hours);
				else if(parser.getName().equalsIgnoreCase("contact"))
					contacts.add(contact);
			}
			parser.nextToken();
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
		
		private DiningHours hours;
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

		public void setHours(DiningHours h)
		{
			hours = h;
		}
		
		public DiningHours getHours()
		{
			return hours;
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
	
	public static class DiningHours
	{
		private String hours;
		public DiningHours(String hours)
		{
			this.hours=hours;
		}
		
		public String getHours()
		{
			return hours;
		}
	}
	
	public static class DiningContacts
	{
		private String name, title, email;
		
		public DiningContacts()
		{
			name="name";
			title="title";
			email="email";
		}
		
		public DiningContacts(String name, String title, String email)
		{
			this.name = name;
			this.title = title;
			this.email = email;
		}
		
		public String getName()
		{
			return name;
		}
		
		public String getTitle()
		{
			return title;
		}
		
		public String getEmail()
		{
			return email;
		}
		
		public void setName(String n)
		{
			name=n;
		}
		public void setTitle(String t)
		{
			title=t;
		}
		public void setEmail(String e)
		{
			email=e;
		}
	}
	
	public ArrayList<DiningLocation> getLocations()
	{
		return locations;
	}

	public ArrayList<DiningContacts> getContacts()
	{
		return contacts;
	}
	public int getLocationCount()
	{
		return locations.size();
	}
	
	public int getPeriodCount(int location, int day)
	{
		return locations.get(location).get(day).size();
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