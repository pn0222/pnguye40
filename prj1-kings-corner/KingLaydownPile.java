/* Name: Paul Nguyen
 * UIN: 676552517
 * Project 1
 * CS 342
 */

public class KingLaydownPile extends LaydownPile {
	
	//Constructor for king lay down pile
	public KingLaydownPile()
	{
		//initial size = 0; capacity = 13;
		super();
	}

	//Check the card being placed down is valid
	public boolean isValid(String cVal)
	{
		if (cVal.length() > 2)
		{
			System.out.println("Error: Invalid card.");
		}
		char cRank = cVal.charAt(0);
		char cSuit = cVal.charAt(1);
		
		int number;
		int isBlack;
		
		if (cRank == 'A' || cRank == 'a')
		{
			number = 1;
		}
		else if (cRank == '2')
		{
			number = 2;
		}
		else if (cRank == '3')
		{
			number = 3;
		}
		else if (cRank == '4')
		{
			number = 4;
		}
		else if (cRank == '5')
		{
			number = 5;
		}
		else if (cRank == '6')
		{
			number = 6;
		}
		else if (cRank == '7')
		{
			number = 7;
		}
		else if (cRank == '8')
		{
			number = 8;
		}
		else if (cRank == '9')
		{
			number = 9;
		}
		
		else if (cRank == 'T' || cRank == 't')
		{
			number = 10;
		}
		else if (cRank == 'J' || cRank == 'j')
		{
			number = 11;
		}
		else if (cRank == 'Q' || cRank == 'q')
		{
			number = 12;
		}
		else if (cRank == 'K' || cRank == 'k')
		{
			number = 13;
		}
		else
		{
			System.out.println("Error: Invalid rank.");
			return false;
		}

		if (cSuit == 'C' || cSuit == 'c')
		{
			isBlack = 1;
		}
		else if (cSuit == 'D' || cSuit == 'd')
		{
			isBlack = 0;
		}
		else if (cSuit == 'H' || cSuit == 'h')
		{
			isBlack = 0;
		}
		else if (cSuit == 'S' || cSuit == 's')
		{
			isBlack = 1;
		}
		else
		{
			System.out.println("Error: Invalid suit.");
			return false;
		}
		
		//Check if the pile is empty, then any card can be lay down
		if(isEmpty() && (number != 13))
		{
			return false;
		}
		
		if(isEmpty() && (number == 13))
		{
			return true;
		}
		
		//Get the top card to find out next valid card
		Card temp = getTop();
		
		//Next card must be exactly 1 lower
		if ((number + 1) != temp.number)
			return false;
		
		//Next card must be opposite color
		if (temp.isBlack == isBlack)
			return false;
		
		//Passed all the test if we reach here
		return true;
	}

	//Check the card being placed down is valid
	public boolean isValid(Card card)
	{
		//If card does not exist return
		if (card == null)
			return true;
		
		//Check if the pile is empty, then any card can be lay down
		if(isEmpty() && (card.number != 13))
		{
			return false;
		}
		
		if(isEmpty() && (card.number == 13))
		{
			return true;
		}
		
		//Get the top card to find out next valid card
		Card temp = getTop();
		
		//Next card must be exactly 1 lower
		if ((card.number + 1) != temp.number)
			return false;
		
		//Next card must be opposite color
		if (card.isBlack == temp.isBlack)
			return false;
		
		//Passed all the test if we reach here
		return true;
	}

}
