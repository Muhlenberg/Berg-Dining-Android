package navdrawer;


public class DiningNavItem 
{
	private String title;
	private int icon;
	
	public DiningNavItem(String title, int icon)
	{
		this.title = title;
		this.icon = icon;
	}
	
	public String getTitle()
	{
		return title;
	}
	
	public int getIcon()
	{
		return icon;
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
