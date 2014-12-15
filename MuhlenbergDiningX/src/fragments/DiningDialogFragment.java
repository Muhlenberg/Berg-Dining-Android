package fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

public class DiningDialogFragment extends DialogFragment
{
	private boolean shouldDownload=false;
	
	public Dialog onCreateDialong(Bundle savedInstance)
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setMessage("New menu available! Would you like to update now?");
		builder.setPositiveButton("Update", new OnClickListener()
		{
			@Override
			public void onClick(DialogInterface dialog, int which) 
			{
				shouldDownload=true;
			}
			
		});
		builder.setNegativeButton("Nope", new OnClickListener()
		{
			@Override
			public void onClick(DialogInterface dialog, int which) 
			{
				shouldDownload=false;
			}
			
		});
		return builder.create();
	}
	
	public boolean shouldDownload()
	{
		return shouldDownload;
	}
}
