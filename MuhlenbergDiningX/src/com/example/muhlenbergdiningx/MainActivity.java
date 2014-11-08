package com.example.muhlenbergdiningx;

import java.util.ArrayList;
import java.util.Calendar;

import navdrawer.DiningNavItem;
import navdrawer.DiningNavigationAdapter;
import viewpager.DiningPagerAdapter;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.FragmentTransaction;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
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
import android.widget.ListView;

public class MainActivity extends FragmentActivity implements ActionBar.TabListener, OnPageChangeListener
{
	public static int screenWidth, screenHeight;

	private ViewPager viewPager;
	private DiningPagerAdapter pagerAdapter;
	private ActionBar actionBar; 

	private DrawerLayout drawerLayout;
	private ListView drawerList;
	private ActionBarDrawerToggle drawerToggle;

	private String[] navMenuTitles;
	private TypedArray navMenuIcons;
	private ArrayList<DiningNavItem> navDrawerItems;
	private DiningNavigationAdapter adapter;

	private CharSequence title;
	private String[] tabs = {"Mo", "Tu", "We", "Th", "Fr", "Sa", "Su"};
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		title = "Muhlenberg Dining";

		//viewpager and actionbar
		viewPager = (ViewPager) findViewById(R.id.viewPager);
		pagerAdapter = new DiningPagerAdapter(getSupportFragmentManager());
		actionBar = getActionBar();

		viewPager.setAdapter(pagerAdapter);
		viewPager.setOnPageChangeListener(this);
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionBar.setBackgroundDrawable(new ColorDrawable(Color.RED));
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
		navDrawerItems.add(new DiningNavItem(navMenuTitles[0], navMenuIcons.getResourceId(0, -1)));
		navDrawerItems.add(new DiningNavItem(navMenuTitles[1], navMenuIcons.getResourceId(1, -1)));
		navDrawerItems.add(new DiningNavItem(navMenuTitles[2], navMenuIcons.getResourceId(2, -1)));
		navDrawerItems.add(new DiningNavItem(navMenuTitles[3], navMenuIcons.getResourceId(3, -1)));

		navMenuIcons.recycle();

		adapter = new DiningNavigationAdapter(getApplicationContext(), navDrawerItems);
		drawerList.setAdapter(adapter);

		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);

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

		if (savedInstanceState == null) {
			displayView(0);
		}
		
		
		//get some display metrics for use in gridview
		DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);

		screenWidth = metrics.widthPixels;
		screenHeight = metrics.heightPixels;

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
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
		if(viewPager.getCurrentItem() == 0)
			super.onBackPressed();
		else
			viewPager.setCurrentItem(viewPager.getCurrentItem()-1);
	}

	private void displayView(int position) {
        // update the main content by replacing fragments
        Fragment fragment = null;
        switch (position) {
        case 0:
            fragment = new DiningFragment(getNumDay());
            break;
        case 1:
            break;
        case 2:
            break;
        case 3:
            break;
        default:
            break;
        }
 
        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.frame_container, fragment).commit();
 
            // update selected item and title, then close the drawer
            drawerList.setItemChecked(position, true);
            drawerList.setSelection(position);
            setTitle(navMenuTitles[position]);
            drawerLayout.closeDrawer(drawerList);
        } else {
            Log.d("MainActivity", "navigation drawer fragment error");
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
		default: return "Monday";
		}
	}
	private String getDay()
	{
		Calendar cal = Calendar.getInstance();
		int currentDay = cal.get(Calendar.DAY_OF_WEEK);
		switch(currentDay)
		{
		case Calendar.SUNDAY: 	return tabs[6];
		case Calendar.MONDAY: 	return tabs[0];
		case Calendar.TUESDAY: 	return tabs[1];
		case Calendar.WEDNESDAY:return tabs[2];
		case Calendar.THURSDAY: return tabs[3];
		case Calendar.FRIDAY: 	return tabs[4];
		case Calendar.SATURDAY: return tabs[5];
		default: return tabs[0];
		}
	}
	
	private int getNumDay()
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
		viewPager.setCurrentItem(tab.getPosition());
		setTitle(convertDay(tab.getPosition()));
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
}