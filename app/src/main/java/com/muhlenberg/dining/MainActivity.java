package com.muhlenberg.dining;

import java.util.Calendar;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;

/**
 * Muhlenberg app prototype
 * @author Jalal Khan
 *
 */
public class MainActivity extends FragmentActivity implements ActionBar.TabListener
{
	public static int screenWidth, screenHeight;
	
	private DiningViewPager viewPager; //manages tabs
	private TabsPagerAdapter mAdapter; //controls tabs
	private ActionBar actionBar; //displays tabs

	private String[] tabs = {"Mo", "Tu", "We", "Th", "Fr", "Sa", "Su"};

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		//create object references
		viewPager = (DiningViewPager) findViewById(R.id.main);
		actionBar = getActionBar();
		mAdapter = new TabsPagerAdapter(getSupportFragmentManager());
		
		//initiatlize
		viewPager.setAdapter(mAdapter);
		actionBar.setHomeButtonEnabled(false);
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionBar.setBackgroundDrawable(new ColorDrawable(Color.RED));
		actionBar.setStackedBackgroundDrawable(getResources().getDrawable(R.drawable.stacked_background));

		addListeners();

		//add tabs to actionbar, making current day selected automatically
		for (String tab_name : tabs) 
		{
			if(getDay().equalsIgnoreCase(tab_name))
				actionBar.addTab(actionBar.newTab().setText(tab_name).setTabListener(this), true);
			else
				actionBar.addTab(actionBar.newTab().setText(tab_name).setTabListener(this), false);
		}

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
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}


	//tablistener interface methods, we only use onTabSelected()
	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) 
	{
		viewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {

	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub

	}

	//conveniance method to declutter onCreate
	public void addListeners()
	{
		//viewpager listener
		viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener()
		{
			@Override
			public void onPageSelected(int position) {
				// on changing the page
				// make respected tab selected
				actionBar.setSelectedNavigationItem(position);
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
			}
		});

	}
	
	public String getDay()
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
}
