import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;

public class Board {
	//Instance variable
	protected int row;		//Bookkeeping for row
protected int col;		//Bookkeeping for col
	protected Button[][] board;
	public int mineLeft;		//Number of mine left, needs to be modified
	protected timer;		//Timer to be displayed
	
	//Default Constructor
	public Board()
	{
		int row = 10;
		int col = 10;
		board = new Button[row][col];
		mineLeft = 0;
		timer = 0;
	}
	
	//Initialize a new game
	public void newGame()
	{
		//Initialize each button
		for (int i = 0; i < row; i++)
		{
			for (int j = 0; j < col; j++)
			{
				board[i][j] = new Button(i, j);
			}
		}

		Random r = new Random();
		int x, y;
		
		//Randomly place 10 mines on the board
		for (int i = 0; i < 10; i++)
		{
			x = r.nextInt(row);
			y = r.nextInt(col);
			
			//If setting the mine is successful
			if(board[x][y].setMine())
			{
				//Mine successfully added, increment number of mine in the game
				incAdjNumber(x, y);
				mineLeft++;
			}
			else
			{
				//Mine did not get set, so decrement the counter
				i--;
			}
		}
		//Debug Information
		//System.out.println(mineLeft);
	}
	
	//Increment adjacent buttons to this mine
	public void incAdjNumber(int x, int y)
	{
		board[x][y].addAdjNumber();	//Redundant, because this position is a bomb

		//Check for out-of-bounds before attempting to access button
		if ((x + 1) < row)
			board[x + 1][y].addAdjNumber();
		if ((x - 1) >= 0)
			board[x - 1][y].addAdjNumber();
		if ((y + 1) < col)
			board[x][y + 1].addAdjNumber();
		if ((y - 1) >= 0)
			board[x][y - 1].addAdjNumber();
		if (((x + 1) < row) && ((y + 1) < col))
			board[x + 1][y + 1].addAdjNumber();
		if (((x - 1) >= 0) && ((y - 1) >= 0))
			board[x - 1][y - 1].addAdjNumber();
		if (((x + 1) < row) && ((y - 1) >= 0))
			board[x + 1][y - 1].addAdjNumber();
		if (((x - 1) >= 0) && ((y + 1) < col))
			board[x - 1][y + 1].addAdjNumber();
	}

	/*Implement the setup for info*/
	
	/*Implement a print function for the board in console*/
	/*Implement GUI for board*/
}//End of Board class
