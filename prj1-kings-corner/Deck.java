/* Name: Paul Nguyen
 * UIN: 676552517
 * Project 1
 * CS 342
 */

import java.util.Random;

//Same as a DrawPile
public class Deck extends CardPile{
	
	//Constructor for class Deck
	public Deck()
	{
		//initial size = 52; capacity = 52;
		super(52, 52);
		
		int index = 0;
	
		//0 - Club, 1 - Diamond, 2 - Heart, 3 - Spade
		for (int i = 0; i < 4; i++)
		{
			/* For loop to generate the rank/number
			 * A - 1
			 * K - 13
			 * Q - 12
			 * J - 11
			 * T - 10
			 */
			for (int j = 1; j <= 13; j++)
			{
				Card card = new Card(j, i);
				super.cArr[index] = card;
				index++;
			}
		}
	}
	
	//This function shuffles the deck
	public void shuffle()
	{
		Card temp;
		
		//Randomize the deck
		for (int i = 0; i < super.size; i++)
		{
			Random rand = new Random();
			int r = rand.nextInt(52);
		
			temp = super.cArr[i];
			super.cArr[i] = super.cArr[r];
			super.cArr[r] = temp;
		}
	}
	
	//This function deals a card to a designated pile
	public void deal(CardPile pile)
	{
		//Deal out from the top of the deck
		super.size--;
		Card temp = super.cArr[size];
		super.cArr[size] = null;
		
		pile.addCard(temp);
	}
}
