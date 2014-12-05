package parsers;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import com.example.muhlenbergdiningx.MainActivity;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import android.util.Xml;

public class DiningXmlParser implements Parcelable
{
	private ArrayList<DiningLocation> locations = new ArrayList<DiningLocation>();
	private ArrayList<DiningContacts> contacts = new ArrayList<DiningContacts>();
	private DiningDate date = null;
	
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
				{
					if(parser.getAttributeValue(1).equalsIgnoreCase("Sunday"))
					{
						date = new DiningDate(parser.getAttributeValue(0));
					}
					day = new DiningDay(parser.getAttributeValue(1));
				}
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
		MainActivity.doneParsing=true;
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
	
	public static class DiningDate
	{
		private String maxDate;
		public DiningDate(String maxDate)
		{
			this.maxDate = maxDate;
		}
		
		public String getMaxDate()
		{
			try {
				return formatDate(maxDate);
			} catch (ParseException e) {e.printStackTrace();}
			
			return "error returning date";
		}
		
		//sample date from xml
		//"Sunday - September 28, 2014"
		//returns 6 digit String in MMDDYY format
		private String formatDate(String date) throws ParseException
		{
			if(date.contains("-"))
			{
				String parts[] = date.split("-"); //[Sunday] - [September 28, 2014]
				date = parts[1].trim();
				
				String parts2[] = date.split("\\s"); //[September] [28,] [2014]
				String month = parts2[0];
				String day = parts2[1].substring(0, 2).trim();
				String year = parts2[2].substring(2, parts2.length+1).trim();
						
				Calendar cal = Calendar.getInstance();
				cal.setTime(new SimpleDateFormat("MMM", Locale.ENGLISH).parse(month));
				int monthInt = cal.get(Calendar.MONTH) + 1;
				
				date = Integer.toString(monthInt)+day+year;
			}
			return date;
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
	
	public DiningDate getDate()
	{
		return date;
	}
	
	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
	}
}