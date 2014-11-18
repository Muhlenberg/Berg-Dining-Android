package navdrawer;

public class DiningNavItem 
{
	private String title;
	private int icon;
	private boolean isSpinner;
	
	public DiningNavItem(String title, int icon)
	{
		this.title 	= title;
		this.icon 	= icon;
		isSpinner = false;
	}
	
	public DiningNavItem(int icon)
	{
		this.icon=icon;
		title=null;
		isSpinner=false;
	}
	public DiningNavItem(boolean isSpinner, int icon)
	{
		title = null;
		
		this.icon = icon;
		this.isSpinner = isSpinner;
	}
	
	public String getTitle()
	{
		return title;
	}
	
	public int getIcon()
	{
		return icon;
	}
	
	public boolean isSpinner()
	{
		return isSpinner;
	}
	
	public void setTitle(String s)
	{
		title=s;
	}
	
	public void setIcon(int i)
	{
		icon=i;
	}
}
