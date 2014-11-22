package viewpager;

import parsers.DiningXmlParser;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import fragments.DiningFragment;

public class DiningPagerAdapter extends FragmentStatePagerAdapter
{
	private DiningXmlParser parser;
	
	public DiningPagerAdapter(FragmentManager fm, DiningXmlParser p) 
	{
		super(fm);
		parser = p;
	}

	@Override
	public Fragment getItem(int pos) 
	{
		return DiningFragment.newInstance(pos, parser);
	}

	@Override
	public int getCount() {
		return 7;
	}
}	
