package fragments;

import java.util.ArrayList;

import parsers.DiningXmlParser;
import parsers.DiningXmlParser.DiningContacts;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import com.example.muhlenbergdiningx.ContactsGridAdapter;
import com.example.muhlenbergdiningx.R;

public class ContactsFragment extends Fragment
{
	private DiningXmlParser parser;
	
	public ContactsFragment()
	{
		super();
	}
	
	public static ContactsFragment newInstance(DiningXmlParser parser)
	{
		ContactsFragment f = new ContactsFragment();
		Bundle bundle = new Bundle();
		bundle.putParcelable("parser", parser);
		f.setArguments(bundle);
		return f;
	}
	
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		parser = getArguments().getParcelable("parser");
	}
	
	public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle savedInstance)
	{
		View rootView;
		rootView = inflater.inflate(R.layout.contactsfragment, viewGroup, false);
		setup(rootView);
		rootView.setBackgroundColor(getResources().getColor(R.color.tan));
		return rootView;
	}
	
	public void setup(View v)
	{
		ArrayList<DiningContacts> contacts = parser.getContacts();
		ArrayList<String> contactsString = new ArrayList<String>();
		GridView gv = (GridView) v.findViewById(R.id.contactsGrid);
		
		contactsString.add("Name");
		contactsString.add("Title");
		contactsString.add("@muhlenberg.edu");
		
		for(int i=0;i<contacts.size();i++)
		{
			contactsString.add(contacts.get(i).getName());
			contactsString.add(contacts.get(i).getTitle());
			contactsString.add(contacts.get(i).getEmail().substring(0, contacts.get(i).getEmail().indexOf('@')));
		}
		
		ContactsGridAdapter adapter = new ContactsGridAdapter(v.getContext(), contactsString);
		gv.setAdapter(adapter);
	}
}
