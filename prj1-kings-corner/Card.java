/* Name: Paul Nguyen
 * UIN: 676552517
 * Project 1
 * CS 342
 */

public class Card {
	protected int number;
	//0 - Club, 1 - Diamond, 2 - Heart, 3 - Spade
	protected int suit;
	protected int isBlack;
	protected char rankDisplay;
	protected char suitDisplay;
	
	//Constructor for Card object with parameter being the number and suit
	Card(int n, int s)
	{
		number = n;
		suit = s;
		
		rankDisplay = rankDisplay(n);
		suitDisplay = suitDisplay(s);
		
		//Check if the suit is black or red
		//RED:{0, 1}	 BLACK:{2, 3}
		if (s == 0 || s == 3)
			isBlack = 1;
		else
			isBlack = 0;
	}
	
	char rankDisplay(int num)
	{
		if (num == 1)
			return 'A';
		else if (num == 2)
			return '2';
		else if (num == 3)
			return '3';
		else if (num == 4)
			return '4';
		else if (num == 5)
			return '5';
		else if (num == 6)
			return '6';
		else if (num == 7)
			return '7';
		else if (num == 8)
			return '8';
		else if (num == 9)
			return '9';
		else if (num == 10)
			return 'T';
		else if (num == 11)
			return 'J';
		else if (num == 12)
			return 'Q';
		else if (num == 13)
			return 'K';
		//Should not reach this point
		else
			return '?';
	}
	
	/*
	 * Suit Priority
	 * 0 - Club
	 * 1 - Diamond
	 * 2 - Hearts
	 * 3 - Spades
	 */
	
	//Get the proper display character
	char suitDisplay(int s)
	{
		if (s == 0)
			return 'C';
		else if (s == 1)
			return 'D';
		else if (s == 2)
			return 'H';
		else if (s == 3)
			return 'S';
		//Should not reach this point
		else
			return '?';
		
	}
	
	//Print the current card
	void printCard()
	{
		System.out.print(rankDisplay + "" + suitDisplay);
	}
}
