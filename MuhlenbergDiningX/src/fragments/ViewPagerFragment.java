package fragments;

import parsers.DiningXmlParser;
import viewpager.DiningPagerAdapter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.muhlenbergdiningx.MainActivity;
import com.example.muhlenbergdiningx.R;

public class ViewPagerFragment extends Fragment
{
	private DiningXmlParser parser;
	private ViewPager viewPager;
	private DiningPagerAdapter adapter;
	
	public ViewPagerFragment()
	{
		super();
	}
	
	public static ViewPagerFragment newInstance(DiningXmlParser parser)
	{
		ViewPagerFragment v = new ViewPagerFragment();
		Bundle bundle = new Bundle();
		bundle.putParcelable("parser", parser);
		v.setArguments(bundle);
		return v;
	}
	
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		parser = getArguments().getParcelable("parser");
	}
	
	public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle savedInstance)
	{
		View rootView = inflater.inflate(R.layout.viewpagerfragment, viewGroup, false);
		setup(rootView);
		return rootView;
	}
	
	private void setup(View v)
	{
		viewPager = (ViewPager) v.findViewById(R.id.viewPager);
		adapter = new DiningPagerAdapter(getActivity().getSupportFragmentManager(), parser);
		viewPager.setAdapter(adapter);
		viewPager.setOnPageChangeListener(((MainActivity)getActivity()));
		viewPager.setCurrentItem(MainActivity.getNumDay());
	}
	
	public ViewPager getViewPager()
	{
		return viewPager;
	}
}
