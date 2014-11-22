package com.example.muhlenbergdiningx;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;

import navdrawer.DiningNavItem;
import navdrawer.DiningNavigationAdapter;

import org.xmlpull.v1.XmlPullParserException;

import parsers.DiningXmlParser;
import parsers.GQParser;
import parsers.MiscParser;
import viewpager.DiningPagerAdapter;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v4.widget.DrawerLayout;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ListView;
import fragments.AboutFragment;
import fragments.ContactsFragment;
import fragments.GQFragment;
import fragments.HoursFragment;
import fragments.JavaJoeFragment;
import fragments.LSCCafeFragment;
import fragments.ViewPagerFragment;

public class MainActivity extends FragmentActivity implements ActionBar.TabListener, OnPageChangeListener, OnItemSelectedListener
{
	private final String urlAddress = "http://stackoverflow.com/feeds/tag?tagnames=android&sort=newest";
	private final String savePath = Environment.getExternalStorageState() + "/Dining Downloads";

	public static int screenWidth, screenHeight;

	private ViewPager viewPager;
	private DiningPagerAdapter pagerAdapter;
	private ActionBar actionBar; 

	private DrawerLayout drawerLayout;
	public ListView drawerList;
	private ActionBarDrawerToggle drawerToggle;

	private String[] navMenuTitles;
	private TypedArray navMenuIcons;
	private ArrayList<DiningNavItem> navDrawerItems;
	private DiningNavigationAdapter adapter;

	private CharSequence title;
	private String[] tabs = {"Mo", "Tu", "We", "Th", "Fr", "Sa", "Su"};

	public DiningXmlParser parser;
	public MiscParser mParser;
	public GQParser gParser;

	private Menu optionsMenu;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		title = "Muhlenberg Dining";

		//network begin
		setRefreshActionButtonState(true);
		DownloadXML dxml = new DownloadXML(this);
		dxml.execute(urlAddress);
		//network end

		//make parser and pass it to other things
		makeParsers();

		//viewpager
		Fragment frag = ViewPagerFragment.newInstance(parser);
		viewPager = ((ViewPagerFragment) frag).getViewPager();
		getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, frag).commit();

		//actionbar
		actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionBar.setStackedBackgroundDrawable(getResources().getDrawable(R.drawable.stacked_background));

		//add tabs to actionbar, making current day selected automatically
		for (String tab_name : tabs) 
		{
			if(getDay().equalsIgnoreCase(tab_name))
				actionBar.addTab(actionBar.newTab().setText(tab_name).setTabListener(this), true);
			else
				actionBar.addTab(actionBar.newTab().setText(tab_name).setTabListener(this), false);
		}

		//navigation drawer
		navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);
		navMenuIcons = getResources().obtainTypedArray(R.array.nav_drawer_icons);

		drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		drawerList = (ListView) findViewById(R.id.dining_drawer);

		navDrawerItems = new ArrayList<DiningNavItem>();
		navDrawerItems.add(new DiningNavItem(true, navMenuIcons.getResourceId(0, -1))); //spinner
		navDrawerItems.add(new DiningNavItem(navMenuTitles[1], navMenuIcons.getResourceId(1, -1)));
		navDrawerItems.add(new DiningNavItem(navMenuTitles[2], navMenuIcons.getResourceId(2, -1)));
		navDrawerItems.add(new DiningNavItem(navMenuTitles[3], navMenuIcons.getResourceId(3, -1)));

		navMenuIcons.recycle();

		adapter = new DiningNavigationAdapter(this, navDrawerItems, parser, mParser);
		drawerList.setAdapter(adapter);

		drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.drawable.ic_drawer, R.string.app_name, R.string.app_name)
		{
			public void onDrawerClosed(View view) {
				getActionBar().setTitle(title);
				// calling onPrepareOptionsMenu() to show action bar icons
				invalidateOptionsMenu();
			}

			public void onDrawerOpened(View drawerView) {
				getActionBar().setTitle(title);
				// calling onPrepareOptionsMenu() to hide action bar icons
				invalidateOptionsMenu();
			}
		};
		drawerLayout.setDrawerListener(drawerToggle);
		drawerList.setOnItemClickListener(new NavMenuListener());


		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setHomeButtonEnabled(true);

		//get some display metrics for use in gridview
		DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);

		screenWidth = metrics.widthPixels;
		screenHeight = metrics.heightPixels;

	}

	public class NavMenuListener implements OnItemClickListener
	{
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) 
		{
			displayView(position);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		optionsMenu = menu;
		getMenuInflater().inflate(R.menu.main, menu);
		getMenuInflater().inflate(R.menu.dining_refresh, menu);
		return true;
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		// if nav drawer is opened, hide the action items
		boolean drawerOpen = drawerLayout.isDrawerOpen(drawerList);
		menu.findItem(R.id.action_settings).setVisible(!drawerOpen);
		return super.onPrepareOptionsMenu(menu);
	}

	@Override
	public void setTitle(CharSequence title) {
		this.title = title;
		getActionBar().setTitle(title);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (drawerToggle.onOptionsItemSelected(item)) 
			return true;

		// Handle action bar actions click
		switch (item.getItemId()) {
		case R.id.action_settings:
			return true;
		case R.id.refresh:
			DownloadXML dxml = new DownloadXML(this); dxml.execute(urlAddress);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		drawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Pass any configuration change to the drawer toggls
		drawerToggle.onConfigurationChanged(newConfig);
	}

	@Override
	public void onBackPressed()
	{

	}

	private void displayView(int position) {
		// update the main content by replacing fragments
		Fragment fragment = null;

		switch (position) 
		{
		//nav drawer, ignore 0 because spinner consumes click
		case 1: fragment = HoursFragment.newInstance(parser); actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD); 		 break;
		case 2: fragment = ContactsFragment.newInstance(parser); actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD); 	 break;
		case 3: fragment = AboutFragment.newInstance(); actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);			 break; 
		//spinner, passed to nav drawer listener  
		case 4: return;
		//current issue: viewpager gets replaced by fragment, then how do i get it back?
		//possible solution: put viewpager in fragment, replace said fragment back and forth
		case 5: fragment = ViewPagerFragment.newInstance(parser); actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);break;
		case 6: fragment = GQFragment.newInstance(gParser); actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD); 	 	 break;
		case 7: fragment = LSCCafeFragment.newInstance(mParser); actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD); 	 break;
		case 8: fragment = JavaJoeFragment.newInstance(mParser); actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD); 	 break;
		default: Log.d("nav drawer", "position out of range " + position); break;
		}

		if(position==5)
		{

		}
		if (fragment != null) 
		{
			FragmentManager fragmentManager = getSupportFragmentManager();
			fragmentManager.beginTransaction().replace(R.id.frame_container, fragment).commit();
		} 
		else 
			Log.d("MainActivity", "navigation drawer fragment error");

		// update selected item and title, then close the drawer
		drawerList.setItemChecked(position, true);
		drawerList.setSelection(position);
		setTitle(navMenuTitles[position]);
		drawerLayout.closeDrawer(drawerList);
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
		default: return "Monday";
		}
	}

	private String getDay()
	{
		return tabs[getNumDay()];
	}

	public static int getNumDay()
	{
		Calendar cal = Calendar.getInstance();
		int currentDay = cal.get(Calendar.DAY_OF_WEEK);
		switch(currentDay)
		{
		case Calendar.SUNDAY: 	return 6;
		case Calendar.MONDAY: 	return 0;
		case Calendar.TUESDAY: 	return 1;
		case Calendar.WEDNESDAY:return 2;
		case Calendar.THURSDAY: return 3;
		case Calendar.FRIDAY: 	return 4;
		case Calendar.SATURDAY: return 5;
		default: return 0;
		}
	}


	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) 
	{
		if(viewPager!=null)
		{
			viewPager.setCurrentItem(tab.getPosition());
			setTitle(convertDay(tab.getPosition()));
		}
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) 
	{
	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPageSelected(int pos) 
	{
		actionBar.selectTab(actionBar.getTabAt(pos));
	}


	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position, long id) 
	{
		displayView(position+4);
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {

	}

	//should we be using BufferedInputStream?
	public void makeParsers()
	{
		parser = null;
		mParser= null;
		gParser = null;
		InputStream is = null;
		try
		{
			is = getAssets().open("mDining.xml");
			parser = new DiningXmlParser(is);
		} 
		catch(IOException ioe) {ioe.printStackTrace(); }
		catch(XmlPullParserException e) {e.printStackTrace(); }

		try
		{
			is = getAssets().open("mDining.xml");
			mParser = new MiscParser(is);
		} 
		catch(IOException ioe) {ioe.printStackTrace(); }
		catch(XmlPullParserException e) {e.printStackTrace(); }

		try
		{
			is = getAssets().open("mDining.xml");
			gParser = new GQParser(is);
		} 
		catch(IOException ioe) {ioe.printStackTrace(); }
		catch(XmlPullParserException e) {e.printStackTrace(); }
	}

	public void setRefreshActionButtonState(final boolean refreshing) {
		if (optionsMenu != null) {
			final MenuItem refreshItem = optionsMenu.findItem(R.id.refresh);
			if (refreshItem != null) {
				if (refreshing) {
					refreshItem.setActionView(R.layout.actionbar_indeterminate_progress);
				} else {
					refreshItem.setActionView(null);
				}
			}
		}
	}


	/**
	 * Cannot download on main thread, create an async thread to do it instead
	 * Saves a file called Dining Downloads.xml to a private space that can be used by the app
	 * path and usability to be determined
	 * @author jmankhan
	 *
	 */
	private class DownloadXML extends AsyncTask<String, Void, String> 
	{
		private Context context;
		
		public DownloadXML(Context c)
		{
			context=c;
		}

		public void downloadFile()
		{
			try 
			{
				URL url = new URL(urlAddress);
				HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

				urlConnection.setRequestMethod("GET");
				urlConnection.setDoOutput(true);
				urlConnection.connect();

				File SDCardRoot = Environment.getExternalStorageDirectory();
				File file = new File(SDCardRoot,"diningdownloads.xml");

				FileOutputStream fileOutput = new FileOutputStream(file);
				InputStream inputStream = urlConnection.getInputStream();

				int totalSize = urlConnection.getContentLength();
				int downloadedSize = 0;

				byte[] buffer = new byte[1024];
				int bufferLength = 0; //used to store a temporary size of the buffer

				while ( (bufferLength = inputStream.read(buffer)) > 0 ) 
				{
					fileOutput.write(buffer, 0, bufferLength);
					downloadedSize += bufferLength;
				}
				fileOutput.close();
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}

		@Override
		protected String doInBackground(String... params) 
		{
			String str = "true";
			downloadFile();
			return str;
		}

		@Override
		protected void onPreExecute() 
		{
			super.onPreExecute();
			((MainActivity)context).setRefreshActionButtonState(true);
			Log.d("preexec", "changed image in pre exec");
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			((MainActivity)context).setRefreshActionButtonState(false);
			Log.d("postexec", "changed image in post exec");
		}
		
		
	}
}