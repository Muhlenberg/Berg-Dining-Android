package com.example.muhlenbergdiningx;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.util.Xml;

public class WoodDiningParser 
{
	private String[] possiblePeriods = {"Breakfast", "Lunch", "Dinner", "Brunch"};
	private String[] possibleStations = {"Wildfire Grille", "Chef's Table", "Magellan's", "Croutons", "Chew Street Deli", "Mangia! Mangia!", 
			"Noshery North", "Noshery South"};
	private String dayName;
	
	private DiningDay day;
	private InputStream is;
	private boolean correctDay; //flag for end tag elements to not get processed until correct start tag is reached
	
	public WoodDiningParser(InputStream is, int dayPos)
	{
		dayName = convertDay(dayPos);
		this.is = is;
		correctDay=false;
	}

	public void parse() throws XmlPullParserException, IOException
	{
		XmlPullParser parser = Xml.newPullParser();
		parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
		parser.setInput(is, null);
		parser.nextTag();

		day = null;
		DiningPeriod period = null;
		DiningStation station = null;

		//while loop to find, organize, and save nodes 
		//increments through each element, identifies it by attribute title, creates class
		//next increment should lead to child node, which will create its own class until bottom level is reached (item)
		//add each child node to its parent class on each new element of that class (when an end tag is reached of that node)
		//cycle back up in saving procedure, and you now have a class of each element with a list of each of its child classes

		int eventType; //used to determine if element is beginning or ending
		while((eventType = parser.getEventType()) != XmlPullParser.END_DOCUMENT)
		{
			if(eventType == XmlPullParser.START_TAG)
			{
				if(parser.getName().equalsIgnoreCase("day") && parser.getAttributeValue(1).contains(dayName))
				{
					day = new DiningDay(dayName);
					correctDay=true;
				}
				
				else if(parser.getName().equalsIgnoreCase("period"))
				{
					for(String periodName : possiblePeriods)
					{
						if(parser.getAttributeValue(0).contains(periodName))
							period = new DiningPeriod(periodName);
					}	
				}
				else if(parser.getName().equalsIgnoreCase("station"))
				{
					for(String stationName : possibleStations)
					{
						if(parser.getAttributeValue(0).contains(stationName))
							station = new DiningStation(stationName);
					}	
				}
				
				if(parser.getName().equalsIgnoreCase("item") && station != null)
					station.add(parser.getAttributeValue(0));
			}
			else if(eventType == XmlPullParser.END_TAG)
			{
				if(parser.getName().equalsIgnoreCase("station")&&correctDay)
					period.add(station);
				else if(parser.getName().equalsIgnoreCase("period")&&correctDay)
					day.add(period);
				else if(parser.getName().equalsIgnoreCase("day")&&correctDay)
					break;
			}
			
			parser.next(); //next element
		}
		
		is.close();
	}
	
	/**
	 * converts integer day value into corresponding string
	 * @param day day of the week
	 * @return day of the week as a string
	 */
	private String convertDay()
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

	private String convertDay(int pos)
	{
		switch(pos)
		{
		case 0: return "Monday";
		case 1: return "Tuesday";
		case 2: return "Wednesday";
		case 3: return "Thursday";
		case 4: return "Friday";
		case 5: return "Saturday";
		case 6: return "Sunday";
		default:return "Monday";
		}
	}
	/**
	 * 
	 * @return requested day with processed info
	 */
	public DiningDay getDay()
	{
		return day;
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
		
		public String getName()
		{
			return name;
		}
		
		public ArrayList<DiningPeriod> getPeriods()
		{
			return periods;
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
		
		public String getName()
		{
			return name;
		}
		
		public ArrayList<DiningStation> getStations()
		{
			return stations;
		}
	}
	public static class DiningStation
	{
		private String name;
		private ArrayList<String> items;

		public DiningStation(String name)
		{
			this.name = name;

			items = new ArrayList<String>();
		}

		public void add(String s)
		{
			items.add(s);
		}
		
		public String getName()
		{
			return name;
		}
		
		public ArrayList<String> getItems()
		{
			return items;
		}
	}
}
