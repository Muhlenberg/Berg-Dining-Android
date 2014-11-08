package viewpager;

import com.example.muhlenbergdiningx.DiningFragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;
import android.util.SparseArray;
import android.view.ViewGroup;

public class DiningPagerAdapter extends FragmentStatePagerAdapter
{
	private SparseArray<Fragment> activeFragments;

	public DiningPagerAdapter(FragmentManager fm) 
	{
		super(fm);
		activeFragments = new SparseArray<Fragment>();
	}

	@Override
	public Fragment getItem(int pos) 
	{
		Log.d("adapter", "created dining frag " + pos);
		return new DiningFragment(pos);
	}

	@Override
	public int getCount() {
		return 7;
	}
	
	//source: http://stackoverflow.com/questions/8785221/retrieve-a-fragment-from-a-viewpager
	//need this stuff to fix a viewpager issue described below
	//http://stackoverflow.com/questions/10853611/viewpager-with-fragments-onpause-onresume
	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		Fragment fragment = (Fragment) super.instantiateItem(container, position);
		activeFragments.put(position, fragment);
		return fragment;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		activeFragments.remove(position);
		super.destroyItem(container, position, object);
	}

	public Fragment getActiveFragment(int position) {
		return activeFragments.get(position);
	}
}
