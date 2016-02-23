/* CS 342 - Patrick Troy
 * Project : Minesweeper v2.2
 * Kyle Tulipano - ktulip2
 * Paul Nguyen - pnguye40
 * 2/22/2016
 */

public class UserScore
{
	//Instance variable for user's score
	String uName;		//Book keeping for the user's name
	int uTime;			//Book keeping for the user's time
	
	//Constructor for UserScore
	public UserScore(String n, int t)
	{
		uName = n;		//Store name
		uTime = t;		//Store time
	}
	
	//Creates a new string for Jlist
	public String printuser(int i)
	{
		String temp = i + ". " + uName + "  ::  " + uTime;
		return temp;
	}
	
	//Get the user's time
	public int getTime()
	{
		return uTime;
	}
}
