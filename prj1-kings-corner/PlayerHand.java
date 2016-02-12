/* Name: Paul Nguyen
 * UIN: 676552517
 * Project 1
 * CS 342
 */

public class PlayerHand extends CardPile{
	
	//Constructor for player's hand
	public PlayerHand()
	{
		//initial size = 0; capacity = 8;
		super(0, 52);
	}
	
	//Get a certain card from player's hand
	public Card getCard(String cVal)
	{
		Card card = null;
		
		if (cVal.length() > 2)
		{
			System.out.println("Error: Invalid card.");
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
				return null;
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
			return null;
		}
		
		for (int i = 0; i < super.getSize(); i++)
		{
			if (super.cArr[i].number == number && super.cArr[i].suit == suit)
			{
				card = cArr[i];
			}
		}
		
		return card;
	}
	
	//Remove card from the player's hand
	public void remove(Card card)
	{
		if (!super.contain(card))
		{
			System.out.println("Error: Card does not exist.");
		}
		
		//Remove card from pile
		for (int i  = 0; i < super.size; i++)
		{	
			if (super.cArr[i] == card)
			{
				//Shift the rest over one slot
				for (; i < super.size; i++)
				{
					super.cArr[i] = super.cArr[i + 1];
				}
			}
		}
		super.size--;
	}
	
	//Sort the card in descending order, base by rank then follow by suit
	public void sort()
	{
		for (int i = 0; i < this.size; i++)
		{
			for (int j = 0; j < this.size; j++)
			{
				if (this.cArr[i].number > this.cArr[j].number)
				{
					Card temp = this.cArr[i];
					this.cArr[i] = this.cArr[j];
					this.cArr[j] = temp;
				}
				
				if (this.cArr[i].number == this.cArr[j].number)
				{
					if (this.cArr[i].suit > this.cArr[j].suit)
					{
						Card temp = this.cArr[i];
						this.cArr[i] = this.cArr[j];
						this.cArr[j] = temp;
					}
				}
			}
		}
	}
	
	//Determine the penalty point
	public int penaltyPoint()
	{
		int sum = 0;
		for (int i = 0; i < this.size; i++)
		{
			
			if(this.cArr[i].number == 13)
				sum += 10;
			else
				sum += 1;
		}
		return sum;
	}
	
	//Print the cards from players hand, in sorted error
	public void printPile()
	{
		String number = null;
		String suit = null;
		
		sort();
		
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
	
	
}
