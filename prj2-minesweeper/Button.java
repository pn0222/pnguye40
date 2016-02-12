import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/*To add GUI: "extends JFrame implements ActionListener", source::bt2.java*/
public class Button {
	//Instance variable
protected boolean isPressed;
	protected boolean mine;
	protected int adjNumber;	//Display the number of mine adjacent to this button
	protected int status;		//Empty, M, ?
	protected String info;	//Placeholder to display in console
	protected Coord pos;		//This will be in-place of the 2 lines below… 
	//protected int x;		//Save the x Position
	//protected int y;		//Save the y Position

	/*Implemented GUI for this button*/
	
	
	//Default Constructor … never used
	public Button()
	{
		isPressed = false;
		mine = false;
		adjNumber = 0;
		status = 0;		//0 - Empty, 1 - M, 2 - ? 
		pos = null;
	}
	
	//Constructor for button
	public Button(int posX, int posY)
	{
		isPressed = false;
		mine = false;
		adjNumber = 0;
		status = 0;		//0 - Empty, 1 - M, 2 - ? 
		pos.setPosX(posX);
		pos.setPosY(posY);
	}

	//Attempt to set the button as a pressed
	public boolean pressButton()
	{
		if (!this.isPressed)
		{
			this.isPressed = true;
			return true;	//Attempt was successful
		}
		else
			return false;	//This button is already a mine, attempt unsuccessful
	}
	
	//Attempt to set the button as a mine
	public boolean setMine()
	{
		//If the mine does not exist then turn it on
		if (!mine)
		{
			this.mine = true;
			return true;	//Attempt was successful
		}
		else
			return false;	//This button is already a mine, attempt unsuccessful
	}

	//Check if this button is a mine
	public boolean isMine()
	{
		return mine;
	}
	
	//Increment this button's adjacent number by 1
	public void addAdjNumber()
	{
		adjNumber++;
	}
	
	//Get information about this button's adjacent number
	public int getAdjNumber()
	{
		return adjNumber;
	}

	//Set the status of the button: 0 - Empty, 1 - M, 2 - ?
	public void setStatus(int s)
	{
		if (s >= 0 && s <= 2)		//Check the boundary for status
			this.status = s;
		else
			return;
	}

	//Get the coordinates of this button
	public Coord getCoord()
	{
		return pos;
	}

/*Implement Image for the button*/

//Allows us to return a pair of numbers …
class Coord
{
	private int x;
	private int y;
	
	//Constructor for coordinates
	public Coord(int posX, int posY)
	{
		x = posX;
		y = posY;
	}
	//Set the value at position x
	public void setPosX(int posX)
	{
		x = posX;
	}
	
	//Set the value at position y
	public void setPosY(int posY)
	{
		y = posY;
	}

	//Return the value at position x
	public int getPosX()
	{
		return x;
	}
	
	//Return the value at position y
	public int getPosY()
	{
		return y;
	}
}//End of Coord class	


}//End of Button class
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/*To add GUI: "extends JFrame implements ActionListener", source::bt2.java*/
public class Button {
	//Instance variable
protected boolean isPressed;
	protected boolean mine;
	protected int adjNumber;	//Display the number of mine adjacent to this button
	protected int status;		//Empty, M, ?
	protected String info;	//Placeholder to display in console
	protected Coord pos;		//This will be in-place of the 2 lines below… 
	//protected int x;		//Save the x Position
	//protected int y;		//Save the y Position

	/*Implemented GUI for this button*/
	
	
	//Default Constructor … never used
	public Button()
	{
		isPressed = false;
		mine = false;
		adjNumber = 0;
		status = 0;		//0 - Empty, 1 - M, 2 - ? 
		pos = null;
	}
	
	//Constructor for button
	public Button(int posX, int posY)
	{
		isPressed = false;
		mine = false;
		adjNumber = 0;
		status = 0;		//0 - Empty, 1 - M, 2 - ? 
		pos.setPosX(posX);
		pos.setPosY(posY);
	}

	//Attempt to set the button as a pressed
	public boolean pressButton()
	{
		if (!this.isPressed)
		{
			this.isPressed = true;
			return true;	//Attempt was successful
		}
		else
			return false;	//This button is already a mine, attempt unsuccessful
	}
	
	//Attempt to set the button as a mine
	public boolean setMine()
	{
		//If the mine does not exist then turn it on
		if (!mine)
		{
			this.mine = true;
			return true;	//Attempt was successful
		}
		else
			return false;	//This button is already a mine, attempt unsuccessful
	}

	//Check if this button is a mine
	public boolean isMine()
	{
		return mine;
	}
	
	//Increment this button's adjacent number by 1
	public void addAdjNumber()
	{
		adjNumber++;
	}
	
	//Get information about this button's adjacent number
	public int getAdjNumber()
	{
		return adjNumber;
	}

	//Set the status of the button: 0 - Empty, 1 - M, 2 - ?
	public void setStatus(int s)
	{
		if (s >= 0 && s <= 2)		//Check the boundary for status
			this.status = s;
		else
			return;
	}

	//Get the coordinates of this button
	public Coord getCoord()
	{
		return pos;
	}

/*Implement Image for the button*/

//Allows us to return a pair of numbers …
class Coord
{
	private int x;
	private int y;
	
	//Constructor for coordinates
	public Coord(int posX, int posY)
	{
		x = posX;
		y = posY;
	}
	//Set the value at position x
	public void setPosX(int posX)
	{
		x = posX;
	}
	
	//Set the value at position y
	public void setPosY(int posY)
	{
		y = posY;
	}

	//Return the value at position x
	public int getPosX()
	{
		return x;
	}
	
	//Return the value at position y
	public int getPosY()
	{
		return y;
	}
}//End of Coord class	


}//End of Button class
