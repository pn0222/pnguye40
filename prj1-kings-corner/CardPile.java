/* Name: Paul Nguyen
 * UIN: 676552517
 * Project 1
 * CS 342
 */

public class CardPile 
{
	//Instance variable
	protected Card[] cArr;
	protected int size;
	
	//Constructor for CardPile; first parameter is the size, second parameter is the capacity of that slot
	CardPile(int i, int cap)
	{
		size = i;
		cArr = new Card[cap];
	}
	

	//add a card to this pile
	public void addCard(Card card)
	{
		cArr[size] = card;
		size++;
	}
	
	//Check if this pile is empty
	public boolean isEmpty()
	{
		return (size == 0);
	}
	
	//Get the current size value
	public int getSize()
	{
		return size;
	}
	
	//Return the top card of the pile
	public Card getTop()
	{
		if (isEmpty())
		{
			System.out.println("Error: Pile is empty.");
			return null;
		}
		int temp = size - 1;
		return cArr[temp];
	}
	
	//Check if this pile has a certain card
	public boolean contain(Card card)
	{
		for (int i = 0; i < size; i++)
		{
			if (card == cArr[i])
				return true;
		}
		return false;
	}
	
	//Check if this pile has a certain card
	public boolean contain(String cVal)
	{
		if (cVal.length() != 2)
		{
			System.out.println("Error: Invalid card.");
			return false;
		}
		char cRank = cVal.charAt(0);
		char cSuit = cVal.charAt(1);
		
		int number;
		int suit;
		
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
			suit = 0;
		}
		else if (cSuit == 'D' || cSuit == 'd')
		{
			suit = 1;
		}
		else if (cSuit == 'H' || cSuit == 'h')
		{
			suit = 2;
		}
		else if (cSuit == 'S' || cSuit == 's')
		{
			suit = 3;
		}
		else
		{
			System.out.println("Error: Invalid suit.");
			return false;
		}
		
		for (int i = 0; i < size; i++)
		{
			if (cArr[i].number == number && cArr[i].suit == suit)
				return true;
		}
		return false;
	}
	
	//Print all the cards in the pile
	public void printPile()
	{
		String number = null;
		String suit = null;
		
		for (int i = 0; i < this.size; i++)
		{
			if (cArr[i].number == 1)
				number = "A";
			else if (cArr[i].number == 2)
				number = "2";
			else if (cArr[i].number == 3)
				number = "3";
			else if (cArr[i].number == 4)
				number = "4";
			else if (cArr[i].number == 5)
				number = "5";
			else if (cArr[i].number == 6)
				number = "6";
			else if (cArr[i].number == 7)
				number = "7";
			else if (cArr[i].number == 8)
				number = "8";
			else if (cArr[i].number == 9)
				number = "9";
			else if (cArr[i].number == 10)
				number = "T";
			else if (cArr[i].number == 11)
				number = "J";
			else if (cArr[i].number == 12)
				number = "Q";
			else if (cArr[i].number == 13)
				number = "K";
			
			if (cArr[i].suit == 0)
				suit = "C";
			else if (cArr[i].suit == 1)
				suit = "D";
			else if (cArr[i].suit == 2)
				suit = "H";
			else if (cArr[i].suit == 3)
				suit = "S";
			
			System.out.print(number + suit + " ");
			
		}
		System.out.println("");
	}
	
	//Remove a specified card from the pile
	public void remove(Card card)
	{
		if (!contain(card))
		{
			System.out.println("Error: Card does not exist.");
		}
		
		//Remove card from pile
		for (int i  = 0; i < size; i++)
		{	
			if (cArr[i] == card)
			{
				//Shift the rest over one slot
				for (; i < size; i++)
				{
					cArr[i] = cArr[i + 1];
				}
			}
		}
		size--;
	}
}
