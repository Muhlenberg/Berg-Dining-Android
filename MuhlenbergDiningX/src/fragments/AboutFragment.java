package fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.muhlenbergdiningx.R;

public class AboutFragment extends Fragment
{
	public AboutFragment()
	{
		super();
	}
	
	public static AboutFragment newInstance()
	{
		return new AboutFragment();
	}
	
	public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle savedInstance)
	{
		View rootView;
		rootView = inflater.inflate(R.layout.aboutfragment, viewGroup, false);
		setup(rootView);
		rootView.setBackgroundColor(getResources().getColor(R.color.tan));
		return rootView;
	}
	
	private void setup(View v)
	{
		TextView aboutText = (TextView) v.findViewById(R.id.aboutText);
		String about = "This app was developed by the Muhlenberg Computer Science Club for the "
				+ "purpose of benefitting hungry Muhlenberg Students and making the Dining Services information techonologies more accessible."
				+ " Somebody come up with a better about text";
		
		aboutText.setText(about);
	}
}
