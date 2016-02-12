/* Name: Paul Nguyen
 * UIN: 676552517
 * Project 1
 * CS 342
 */

import java.util.Scanner;

public class Simulator {
	
	//Keep track of whoever is dealing
	protected int game;
	
	//Keep track of penalty points
	protected int playerPoint;
	protected int computerPoint;
	
	//Create our 8 piles needed for the game
	protected LaydownPile p1;
	protected LaydownPile p2;
	protected LaydownPile p3;
	protected LaydownPile p4;
	
	protected KingLaydownPile p5;
	protected KingLaydownPile p6;
	protected KingLaydownPile p7;
	protected KingLaydownPile p8;
	
	//Create two piles for the player
	protected PlayerHand player;
	protected PlayerHand computer;
	
	//Create a deck for the game
	protected Deck deck;
	
	//Constructor for simulator, initialize memory for each instance variable
	public Simulator()
	{
		game = 0;
		//round = 0;
		playerPoint = 0;
		computerPoint = 0;
		
		//Create all the piles
		
		deck = new Deck();
		
		p1 = new LaydownPile();
		p2 = new LaydownPile();
		p3 = new LaydownPile();
		p4 = new LaydownPile();
		
		p5 = new KingLaydownPile();
		p6 = new KingLaydownPile();
		p7 = new KingLaydownPile();
		p8 = new KingLaydownPile();
		
		player = new PlayerHand();
		computer = new PlayerHand();
		
		//Create all the players
	}
	
	
	public static void main(String[] args)
	{
		boolean test = true;
		while(test)
		{
			//Create and setup the game
			Simulator sim = new Simulator();
			Scanner s = new Scanner(System.in);
			
			while ((sim.playerPoint < 25) && (sim.computerPoint < 25))
			{
				sim.newGame();
				sim.display(sim);
				
				//If the dealer is the player, computer goes first
				if (sim.game % 2 == 0)
				{
					if (!sim.computerTurn())
					{
						if (!sim.deck.isEmpty())
							sim.deck.deal(sim.computer);
					}
					sim.display(sim);
				}
				
				//Operates the command from user input
				sim.command(s, sim);
				
				//When a game is over display the score board
				System.out.println("\nPlayer's penalty points: " + sim.playerPoint);
				System.out.println("Computer's penalty points: " + sim.computerPoint + "\n");
				
				//reset the game for next round
				sim.reset();
			}
			
			//See if the user wants to start a new game
			System.out.println("Do you want to start a new game? (yes/no)");
			while (s.hasNext())
			{
				String input = s.next();
				if (input.equals("yes") == true)
				{
					test = true;
					break;
				}
				else if (input.equals("no") == true)
				{
					test = false;
					System.out.println("Goodbye!");
					break;
				}
				else
				{
					System.out.println("Invalid input, please try again.");
				}
			}
		}
		
		
	}
	
	//Display the interface for the user
	public void display(Simulator sim)
	{
		//Show all the piles
		System.out.print("Pile 1: ");
		sim.p1.printPile();
		System.out.print("Pile 2: ");
		sim.p2.printPile();
		System.out.print("Pile 3: ");
		sim.p3.printPile();
		System.out.print("Pile 4: ");
		sim.p4.printPile();
		System.out.print("Pile 5: ");
		sim.p5.printPile();
		System.out.print("Pile 6: ");
		sim.p6.printPile();
		System.out.print("Pile 7: ");
		sim.p7.printPile();
		System.out.print("Pile 8: ");
		sim.p8.printPile();
		
		//Display players hand and computer count of cards
		System.out.println("Computer Player has " + this.computer.size + " cards");
		System.out.print("Your hand: ");
		sim.player.printPile();
		System.out.print("Move> ");
	}
	
	//Perform commands base on user input
	private void command(Scanner s, Simulator sim)
	{		
		while (s.hasNext())
		{
			String cmd = s.next();
			
			//If the user input Q or q then terminate the program
			if ((cmd.equals("Q") == true) || (cmd.equals("q") == true))
			{
				System.out.println("Are you sure? (Y/N)");
				Scanner input = new Scanner(System.in);
				String test = input.next();
				
				//Re-question the user
				if ((test.equals("Y")== true) || (test.equals("y")== true))
				{
					System.out.println("Goodbye!");
					System.exit(1);
					input.close();
				}
			}
			
			//If the user input H or h then display all the user command
			else if ((cmd.equals("H") == true) || (cmd.equals("h") == true))
			{
				showCommands();
			}

			//If the user input A or a then display information about this program
			else if ((cmd.equals("A") == true) || (cmd.equals("a") == true))
			{
				showAbout();
			}
			
			//If the user input D or d then draw a card for the user
			else if ((cmd.equals("D")== true) || (cmd.equals("d") == true))
			{
				drawCard(player, s);
				//Computer turn, if computer did not win, computer draws a card
				if (!computerTurn())
				{
					if (!this.deck.isEmpty())
						this.deck.deal(computer);
				}
				display(sim);
			}
			
			//If the user input L or l then attempt to lay down the card
			else if ((cmd.equals("L")== true) || (cmd.equals("l") == true))
			{
				layCard(player, s);
				display(sim);
			}
			
			//If the user input M or m then attempt to merge the two piles down
			else if ((cmd.equals("M")== true) || (cmd.equals("m") == true))
			{
				mergePile(s);
				display(sim);
			}
			
			//Other command will produce an error.
			else
				System.out.println("Command is not known: " + cmd);
			
			//If the player has no cards, the player wins
			if (this.player.getSize() == 0)
			{
				System.out.println("YOU WIN!");
				this.computerPoint += computer.penaltyPoint();	
				return;
			}
			
			//If the computer has no cards, the computer wins
			if (this.computer.getSize() == 0)
			{
				System.out.println("COMPUTER WIN!");
				this.playerPoint += player.penaltyPoint();
				return;
			}
			
			s.nextLine();			
		}
		
	}
	
	//Display the help command
	private void showCommands()
	{
		System.out.println("The command for this game are:");
		System.out.println("Q - Quit the program");
		System.out.println("H - Show command");
		System.out.println("D - Draw a card from the deck");
		System.out.println("A - About this program");
		System.out.println("L <Card> <Pile> - Lay a Card in a Pile");
		System.out.println("M <Pile1> <Pile2> - Merge Pile1 into Pile2");
	}
	
	//Display information about this game
	private void showAbout()
	{
		System.out.println("**King's Corner**");
		System.out.println("First person to reach 25 penalty points loses. Each round ends when");
		System.out.println("a player has no cards, and the other player will get 1 penalty points");
		System.out.println("for each card and 10 penalty point for a \"king\".");
	}

	//Reset the game
	private void reset()
	{
		deck = new Deck();
		
		p1 = new LaydownPile();
		p2 = new LaydownPile();
		p3 = new LaydownPile();
		p4 = new LaydownPile();
		
		p5 = new KingLaydownPile();
		p6 = new KingLaydownPile();
		p7 = new KingLaydownPile();
		p8 = new KingLaydownPile();
		
		player = new PlayerHand();
		computer = new PlayerHand();
	}
	
	//Deal out star
	private void newGame()
	{
		//Shuffle the deck
		deck.shuffle();
		
		//Computer deals the first game
		if (game % 2 == 0)
		{
			for (int i = 0; i < 7; i++)
			{
				deck.deal(player);
				deck.deal(computer);	
			}
		}
		//Player deals every old game
		else
		{
			for (int i = 0; i < 7; i++)
			{
				deck.deal(computer);
				deck.deal(player);	
			}
		}
		
		//Deal one card to pile 1-4
		deck.deal(p1);
		deck.deal(p2);
		deck.deal(p3);
		deck.deal(p4);
		
		//round++;
		game++;
	}
	
	//The player draws a Card
	private void drawCard(PlayerHand p, Scanner s)
	{
		//Check if the deck is empty
		if (deck.getSize() == 0)
		{
			System.out.println("Error: Deck is empty.");
			System.out.println("Do you want to quit the program? (Y/N)");
			
			while(s.hasNext())
			{
				String temp = s.next();
				
				if ((temp.equals("Y") == true) || (temp.equals("y") == true))
				{
					System.out.println("Game Over");
					System.exit(1);
				}
				else if ((temp.equals("N") == true) || (temp.equals("n") == true))
				{
					return;
				}
				else
				{
					System.out.println("Invalid input.");
				}
			}
		}
		//draw the top card of the draw pile
		else
			deck.deal(p);
		
	}
	
	//The player lays down a card into a pile
	private void layCard(PlayerHand p, Scanner s)
	{
		String cardValue;
		if (s.hasNext() == true)
			cardValue = s.next();
		else
		{
			System.out.println("Error.");
			return;
		}
		
		int pileNum;
		
		if (s.hasNextInt() == true)
			pileNum = s.nextInt();
		else
		{
			System.out.println("Error: Integer value expected.");
			return;
		}
		
		if (pileNum < 1 || pileNum > 8)
		{
			System.out.println("Error: Invalid pile");
			return;
		}
		
		LaydownPile pile = null;
		
		if (pileNum == 1)
			pile = p1;
		else if (pileNum == 2)
			pile = p2;
		else if (pileNum == 3)
			pile = p3;
		else if (pileNum == 4)
			pile = p4;
		else if (pileNum == 5)
			pile = p5;
		else if (pileNum == 6)
			pile = p6;
		else if (pileNum == 7)
			pile = p7;
		else if (pileNum == 8)
			pile = p8;
		
		//Check if the card is in player's hand
		if (!p.contain(cardValue))
		{
			System.out.println("Error: Card does not exist.");
			return;
		}
		
		//Check if the move is a valid play
		if (!pile.isValid(cardValue))
		{
			System.out.println("Error: Invalid play, please try again.");
			return;
		}
		Card card= p.getCard(cardValue);
		
		p.remove(card);
		pile.addCard(card);
	}
	
	//This function merge two piles together
	private void mergePile(Scanner s)
	{
		int pInt1, pInt2;
		if (s.hasNextInt() == true)
			pInt1 = s.nextInt();
		else
		{
			System.out.println("Error: Integer expected.");
			return;
		}
		
		if (s.hasNextInt() == true)
			pInt2 = s.nextInt();
		else
		{
			System.out.println("Error: Integer expected.");
			return;
		}
			
		//Determine pile1
		LaydownPile pile1;
		if (pInt1 == 1)
			pile1 = p1;
		else if (pInt1 == 2)
			pile1 = p2;
		else if (pInt1 == 3)
			pile1 = p3;
		else if (pInt1 == 4)
			pile1 = p4;
		else if (pInt1 == 5)
			pile1 = p5;
		else if (pInt1 == 6)
			pile1 = p6;
		else if (pInt1 == 7)
			pile1 = p7;
		else if (pInt1 == 8)
			pile1 = p8;
		else
		{
			System.out.println("Error: Pile is out of bounds");
			return;
		}
		
		//Determine pile2
		LaydownPile pile2;
		if (pInt2 == 1)
			pile2 = p1;
		else if (pInt2 == 2)
			pile2 = p2;
		else if (pInt2 == 3)
			pile2 = p3;
		else if (pInt2 == 4)
			pile2 = p4;
		else if (pInt2 == 5)
			pile2 = p5;
		else if (pInt2 == 6)
			pile2 = p6;
		else if (pInt2 == 7)
			pile2 = p7;
		else if (pInt2 == 8)
			pile2 = p8;
		else
		{
			System.out.println("Error: Pile is out of bounds");
			return;
		}
		
		//Attempting to merge the same pile
		if (pile1 == pile2)
			return;
		if (pile1.getSize() == 0)
			return;
		
		int i, j, mergeTot;
		if (pile2.isValid(pile1.cArr[0]))
		{
			i = pile2.getSize();
			mergeTot = pile1.getSize();
			for (j = 0; j < mergeTot; j++, i++)
			{
				
				pile2.cArr[i] = pile1.cArr[j];
				pile1.cArr[j] = null;
				pile2.size++;
				pile1.size--;
			}
			return;
		}

		//If we reach here, merge was not successful
		System.out.println("Error: Merge move is not valid");
		return;

	}
	
	//Computer algorithm to play the game
	private boolean computerTurn()
	{
		//Check if computer has any kings
		for (int i = 0; i < this.computer.size; i++)
		{
			//If the computer has zero cards, computer wins
			if (this.computer.isEmpty())
				return true;

			//Lay down any kings in the king's lay down pile
			if (this.computer.cArr[i].number == 13)
			{
				if (this.p5.isEmpty())
				{
					this.p5.addCard(this.computer.cArr[i]);
					
					System.out.print("Computer's Move: Lay down ");
					this.computer.cArr[i].printCard();
					System.out.println(" in Pile 5.");
					
					this.computer.remove(this.computer.cArr[i]);
					
				}
				else if (this.p6.isEmpty())
				{
					this.p6.addCard(this.computer.cArr[i]);
					
					System.out.print("Computer's Move: Lay down ");
					this.computer.cArr[i].printCard();
					System.out.println(" in Pile 6.");
					
					this.computer.remove(this.computer.cArr[i]);
				}
				else if (this.p7.isEmpty())
				{
					this.p7.addCard(this.computer.cArr[i]);
					
					System.out.print("Computer's Move: Lay down ");
					this.computer.cArr[i].printCard();
					System.out.println(" in Pile 7.");
					
					this.computer.remove(this.computer.cArr[i]);
				}
				else if (this.p8.isEmpty())
				{
					this.p8.addCard(this.computer.cArr[i]);
					
					System.out.print("Computer's Move: Lay down ");
					this.computer.cArr[i].printCard();
					System.out.println(" in Pile 8.");
					
					this.computer.remove(this.computer.cArr[i]);
				}
			}
		}
		
		LaydownPile pile1 = null;
		LaydownPile pile2 = null;
		
		for (int i = 0; i < 4; i++)
		{
			boolean test = true;
			//Check the base card of pile 1-4 for a king
			if (!p1.isEmpty() && this.p1.cArr[0].number == 13)
			{	
				pile1 = p1;
				System.out.print("Computer's Move: Merging Pile 1 to ");
			}
			else if (!p2.isEmpty() && this.p2.cArr[0].number == 13)
			{		
				pile1 = p2;
				System.out.print("Computer's Move: Merging Pile 2 to ");
			}
			else if (!p3.isEmpty() && this.p3.cArr[0].number == 13)
			{		
				pile1 = p3;
				System.out.print("Computer's Move: Merging Pile 3 to ");
			}
			else if (!p4.isEmpty() && this.p4.cArr[0].number == 13)
			{		
				pile1 = p4;
				System.out.print("Computer's Move: Merging Pile 4 to ");
			}
			else
				test = false;
			
			//If a king is the base of pile 1-4
			if (test)
			{
				if (this.p5.isEmpty())
				{
					pile2 = p5;
					System.out.println("Pile 5.");
				}
				else if (this.p6.isEmpty())
				{
					pile2 = p6;
					System.out.println("Pile 6.");
				}
				else if (this.p7.isEmpty())
				{
					pile2 = p7;
					System.out.println("Pile 7.");
				}
				else if (this.p8.isEmpty())
				{
					pile2 = p8;
					System.out.println("Pile 8.");
				}
				
				int mergeTot = pile1.getSize();
				
				for (int j = 0; j < mergeTot; j++)
				{
					pile2.cArr[j] = pile1.cArr[j];
					pile1.cArr[j] = null;
					pile1.size--;
					pile2.size++;
				}
			}
		}
		
		//If a pile can be moved onto another pile
		computerMerge();
		
		//Check if the computer can lay down any cards on a non-empty pile
		if (computerLay1())
			computerTurn();
		else
		{
			//Check if the computer can lay down any cards on a empty pile
			if (computerLay2())
				computerTurn();
		}
		
		//If the computer wins on this turn return true
		if (this.computer.isEmpty())
			return true;
		
		//Otherwise return false
		return false;
	}

	//This function is a helper function that determines if the computer can lay a card in a non-empty pile
	private boolean computerLay1()
	{
		//Step 3: Lay down a card on a non-empty pile
		for (int i = 0; i < this.computer.size; i++)
		{
			if (this.p1.isValid(this.computer.cArr[i]) && !p1.isEmpty())
			{
				System.out.print("Computer's Move: Lay down ");
				this.computer.cArr[i].printCard();
				System.out.println(" in Pile 1.");
				
				p1.addCard(this.computer.cArr[i]);
				this.computer.remove(this.computer.cArr[i]);
				return true;
			}
			else if (this.p2.isValid(this.computer.cArr[i]) && !p2.isEmpty())
			{
				System.out.print("Computer's Move: Lay down ");
				this.computer.cArr[i].printCard();
				System.out.println(" in Pile 2.");
				
				p2.addCard(this.computer.cArr[i]);
				this.computer.remove(this.computer.cArr[i]);
				return true;
			}
			else if (this.p3.isValid(this.computer.cArr[i]) && !p3.isEmpty())
			{
				System.out.print("Computer's Move: Lay down ");
				this.computer.cArr[i].printCard();
				System.out.println(" in Pile 3.");
				
				p3.addCard(this.computer.cArr[i]);
				this.computer.remove(this.computer.cArr[i]);
				return true;
			}
			else if (this.p4.isValid(this.computer.cArr[i]) && !p4.isEmpty())
			{
				System.out.print("Computer's Move: Lay down ");
				this.computer.cArr[i].printCard();
				System.out.println(" in Pile 4.");
				
				p4.addCard(this.computer.cArr[i]);
				this.computer.remove(this.computer.cArr[i]);
				return true;
			}
			else if (this.p5.isValid(this.computer.cArr[i]) && !p5.isEmpty())
			{
				System.out.print("Computer's Move: Lay down ");
				this.computer.cArr[i].printCard();
				System.out.println(" in Pile 5.");
				
				p5.addCard(this.computer.cArr[i]);
				this.computer.remove(this.computer.cArr[i]);
				return true;
			}
			else if (this.p6.isValid(this.computer.cArr[i]) && !p6.isEmpty())
			{
				System.out.print("Computer's Move: Lay down ");
				this.computer.cArr[i].printCard();
				System.out.println(" in Pile 6.");
				
				p6.addCard(this.computer.cArr[i]);
				this.computer.remove(this.computer.cArr[i]);
				return true;
			}
			else if (this.p7.isValid(this.computer.cArr[i]) && !p7.isEmpty())
			{
				System.out.print("Computer's Move: Lay down ");
				this.computer.cArr[i].printCard();
				System.out.println(" in Pile 7.");
				
				p7.addCard(this.computer.cArr[i]);
				this.computer.remove(this.computer.cArr[i]);
				return true;
			}
			else if (this.p8.isValid(this.computer.cArr[i]) && !p8.isEmpty())
			{
				System.out.print("Computer's Move: Lay down ");
				this.computer.cArr[i].printCard();
				System.out.println(" in Pile 8.");
				
				p8.addCard(this.computer.cArr[i]);
				this.computer.remove(this.computer.cArr[i]);
				return true;
			}
		}
		return false;
	}

	//This function is a helper function that determines if the computer can lay a card in a empty pile
	private boolean computerLay2()
	{
		//Lay down a card on a non-empty pile
		for (int i = 0; i < this.computer.size; i++)
		{
			if (this.p1.isEmpty())
			{
				System.out.print("Computer's Move: Lay down ");
				this.computer.cArr[i].printCard();
				System.out.println(" in Pile 1.");
				
				p1.addCard(this.computer.cArr[i]);
				this.computer.remove(this.computer.cArr[i]);
				return true;
			}
			else if (this.p2.isEmpty())
			{
				System.out.print("Computer's Move: Lay down ");
				this.computer.cArr[i].printCard();
				System.out.println(" in Pile 2.");
				
				p2.addCard(this.computer.cArr[i]);
				this.computer.remove(this.computer.cArr[i]);
				return true;
			}
			else if (this.p3.isEmpty())
			{
				System.out.print("Computer's Move: Lay down ");
				this.computer.cArr[i].printCard();
				System.out.println(" in Pile 3.");
				
				p3.addCard(this.computer.cArr[i]);
				this.computer.remove(this.computer.cArr[i]);
				return true;
			}
			else if (p4.isEmpty())
			{
				System.out.print("Computer's Move: Lay down ");
				this.computer.cArr[i].printCard();
				System.out.println(" in Pile 4.");
				
				p4.addCard(this.computer.cArr[i]);
				this.computer.remove(this.computer.cArr[i]);
				return true;
			}
		}
		return false;
	}

	//Helper function to see if any piles can be merged with another
	private void computerMerge()
	{
		if (this.p1.isValid(this.p2.cArr[0]) && !this.p2.isEmpty())
		{
			int i = this.p1.getSize();
			int mergeTot = this.p2.getSize();
			System.out.println("Computer's Move: Merging Pile 2 to Pile 1.");
			
			for (int j = 0; j < mergeTot; j++, i++)
			{
				this.p1.cArr[i] = this.p2.cArr[j];
				this.p2.cArr[j] = null;
				this.p1.size++;
				this.p2.size--;
			}
		}
		
		if (this.p1.isValid(this.p3.cArr[0]) && !this.p3.isEmpty())
		{
			int i = this.p1.getSize();
			int mergeTot = this.p3.getSize();
			System.out.println("Computer's Move: Merging Pile 3 to Pile 1.");
			
			for (int j = 0; j < mergeTot; j++, i++)
			{
				this.p1.cArr[i] = this.p3.cArr[j];
				this.p3.cArr[j] = null;
				this.p1.size++;
				this.p3.size--;
			}
		}
		
		if (this.p1.isValid(this.p4.cArr[0]) && !this.p4.isEmpty())
		{
			int i = this.p1.getSize();
			int mergeTot = this.p4.getSize();
			System.out.println("Computer's Move: Merging Pile 4 to Pile 1.");
			
			for (int j = 0; j < mergeTot; j++, i++)
			{
				this.p1.cArr[i] = this.p4.cArr[j];
				this.p4.cArr[j] = null;
				this.p1.size++;
				this.p4.size--;
			}
		}
		
		
		if (this.p2.isValid(this.p1.cArr[0]) && !this.p1.isEmpty())
		{
			int i = this.p2.getSize();
			int mergeTot = this.p1.getSize();
			System.out.println("Computer's Move: Merging Pile 1 to Pile 2.");
			
			for (int j = 0; j < mergeTot; j++, i++)
			{
				this.p2.cArr[i] = this.p1.cArr[j];
				this.p1.cArr[j] = null;
				this.p2.size++;
				this.p1.size--;
			}
		}
		
		if (this.p2.isValid(this.p3.cArr[0]) && !this.p3.isEmpty())
		{
			int i = this.p2.getSize();
			int mergeTot = this.p3.getSize();
			System.out.println("Computer's Move: Merging Pile 3 to Pile 2.");
			
			for (int j = 0; j < mergeTot; j++, i++)
			{
				this.p2.cArr[i] = this.p3.cArr[j];
				this.p3.cArr[j] = null;
				this.p2.size++;
				this.p3.size--;
			}
		}
		
		if (this.p2.isValid(this.p4.cArr[0]) && !this.p4.isEmpty())
		{
			int i = this.p2.getSize();
			int mergeTot = this.p4.getSize();
			System.out.println("Computer's Move: Merging Pile 4 to Pile 2.");
			
			for (int j = 0; j < mergeTot; j++, i++)
			{
				this.p2.cArr[i] = this.p4.cArr[j];
				this.p4.cArr[j] = null;
				this.p2.size++;
				this.p4.size--;
			}
		}
		
		if (this.p3.isValid(this.p1.cArr[0]) && !this.p1.isEmpty())
		{
			int i = this.p3.getSize();
			int mergeTot = this.p1.getSize();
			System.out.println("Computer's Move: Merging Pile 1 to Pile 3.");
			
			for (int j = 0; j < mergeTot; j++, i++)
			{
				this.p3.cArr[i] = this.p1.cArr[j];
				this.p1.cArr[j] = null;
				this.p3.size++;
				this.p1.size--;
			}
		}
		
		if (this.p3.isValid(this.p2.cArr[0]) && !this.p2.isEmpty())
		{
			int i = this.p3.getSize();
			int mergeTot = this.p2.getSize();
			System.out.println("Computer's Move: Merging Pile 2 to Pile 3.");
			
			for (int j = 0; j < mergeTot; j++, i++)
			{
				this.p3.cArr[i] = this.p2.cArr[j];
				this.p2.cArr[j] = null;
				this.p3.size++;
				this.p2.size--;
			}
		}
		
		if (this.p3.isValid(this.p4.cArr[0]) && !this.p4.isEmpty())
		{
			int i = this.p3.getSize();
			int mergeTot = this.p4.getSize();
			System.out.println("Computer's Move: Merging Pile 4 to Pile 3.");
			
			for (int j = 0; j < mergeTot; j++, i++)
			{
				this.p3.cArr[i] = this.p4.cArr[j];
				this.p4.cArr[j] = null;
				this.p3.size++;
				this.p4.size--;
			}
		}
		
		if (this.p4.isValid(this.p1.cArr[0]) && !this.p1.isEmpty())
		{
			int i = this.p4.getSize();
			int mergeTot = this.p1.getSize();
			System.out.println("Computer's Move: Merging Pile 1 to Pile 4.");
			
			for (int j = 0; j < mergeTot; j++, i++)
			{
				this.p4.cArr[i] = this.p1.cArr[j];
				this.p1.cArr[j] = null;
				this.p4.size++;
				this.p1.size--;
			}
		}
		
		if (this.p4.isValid(this.p2.cArr[0]) && !this.p2.isEmpty())
		{
			int i = this.p4.getSize();
			int mergeTot = this.p2.getSize();
			System.out.println("Computer's Move: Merging Pile 2 to Pile 4.");
			
			for (int j = 0; j < mergeTot; j++, i++)
			{
				this.p4.cArr[i] = this.p2.cArr[j];
				this.p2.cArr[j] = null;
				this.p4.size++;
				this.p2.size--;
			}
		}
		
		if (this.p4.isValid(this.p3.cArr[0]) && !this.p3.isEmpty())
		{
			int i = this.p4.getSize();
			int mergeTot = this.p3.getSize();
			System.out.println("Computer's Move: Merging Pile 3 to Pile 4.");
			
			for (int j = 0; j < mergeTot; j++, i++)
			{
				this.p4.cArr[i] = this.p3.cArr[j];
				this.p3.cArr[j] = null;
				this.p4.size++;
				this.p3.size--;
			}
		}
		
		if (this.p5.isValid(this.p1.cArr[0]) && !this.p1.isEmpty())
		{
			int i = this.p5.getSize();
			int mergeTot = this.p1.getSize();
			System.out.println("Computer's Move: Merging Pile 1 to Pile 5.");
			
			for (int j = 0; j < mergeTot; j++, i++)
			{
				this.p5.cArr[i] = this.p1.cArr[j];
				this.p1.cArr[j] = null;
				this.p5.size++;
				this.p1.size--;
			}
		}
		
		if (this.p5.isValid(this.p2.cArr[0]) && !this.p2.isEmpty())
		{
			int i = this.p5.getSize();
			int mergeTot = this.p2.getSize();
			System.out.println("Computer's Move: Merging Pile 2 to Pile 5.");
			
			for (int j = 0; j < mergeTot; j++, i++)
			{
				this.p5.cArr[i] = this.p2.cArr[j];
				this.p2.cArr[j] = null;
				this.p5.size++;
				this.p2.size--;
			}
		}
		
		if (this.p5.isValid(this.p3.cArr[0]) && !this.p3.isEmpty())
		{
			int i = this.p5.getSize();
			int mergeTot = this.p3.getSize();
			System.out.println("Computer's Move: Merging Pile 3 to Pile 5.");
			
			for (int j = 0; j < mergeTot; j++, i++)
			{
				this.p5.cArr[i] = this.p3.cArr[j];
				this.p3.cArr[j] = null;
				this.p5.size++;
				this.p3.size--;
			}
		}
		
		if (this.p5.isValid(this.p4.cArr[0]) && !this.p4.isEmpty())
		{
			int i = this.p5.getSize();
			int mergeTot = this.p4.getSize();
			System.out.println("Computer's Move: Merging Pile 4 to Pile 5.");
			
			for (int j = 0; j < mergeTot; j++, i++)
			{
				this.p5.cArr[i] = this.p4.cArr[j];
				this.p4.cArr[j] = null;
				this.p5.size++;
				this.p4.size--;
			}
		}
		
		if (this.p6.isValid(this.p1.cArr[0]) && !this.p1.isEmpty())
		{
			int i = this.p6.getSize();
			int mergeTot = this.p1.getSize();
			System.out.println("Computer's Move: Merging Pile 1 to Pile 6.");
			
			for (int j = 0; j < mergeTot; j++, i++)
			{
				this.p6.cArr[i] = this.p1.cArr[j];
				this.p1.cArr[j] = null;
				this.p6.size++;
				this.p1.size--;
			}
		}
		
		if (this.p6.isValid(this.p2.cArr[0]) && !this.p2.isEmpty())
		{
			int i = this.p6.getSize();
			int mergeTot = this.p2.getSize();
			System.out.println("Computer's Move: Merging Pile 2 to Pile 6.");
			
			for (int j = 0; j < mergeTot; j++, i++)
			{
				this.p6.cArr[i] = this.p2.cArr[j];
				this.p2.cArr[j] = null;
				this.p6.size++;
				this.p2.size--;
			}
		}
		
		if (this.p6.isValid(this.p3.cArr[0]) && !this.p3.isEmpty())
		{
			int i = this.p6.getSize();
			int mergeTot = this.p3.getSize();
			System.out.println("Computer's Move: Merging Pile 3 to Pile 6.");
			
			for (int j = 0; j < mergeTot; j++, i++)
			{
				this.p6.cArr[i] = this.p3.cArr[j];
				this.p3.cArr[j] = null;
				this.p6.size++;
				this.p3.size--;
			}
		}
		
		if (this.p6.isValid(this.p4.cArr[0]) && !this.p4.isEmpty())
		{
			int i = this.p6.getSize();
			int mergeTot = this.p4.getSize();
			System.out.println("Computer's Move: Merging Pile 4 to Pile 6.");
			
			for (int j = 0; j < mergeTot; j++, i++)
			{
				this.p6.cArr[i] = this.p4.cArr[j];
				this.p4.cArr[j] = null;
				this.p6.size++;
				this.p4.size--;
			}
		}
		
		if (this.p7.isValid(this.p1.cArr[0]) && !this.p1.isEmpty())
		{
			int i = this.p7.getSize();
			int mergeTot = this.p1.getSize();
			System.out.println("Computer's Move: Merging Pile 1 to Pile 7.");
			
			for (int j = 0; j < mergeTot; j++, i++)
			{
				this.p7.cArr[i] = this.p1.cArr[j];
				this.p1.cArr[j] = null;
				this.p7.size++;
				this.p1.size--;
			}
		}
		
		if (this.p7.isValid(this.p2.cArr[0]) && !this.p2.isEmpty())
		{
			int i = this.p7.getSize();
			int mergeTot = this.p2.getSize();
			System.out.println("Computer's Move: Merging Pile 2 to Pile 7.");
			
			for (int j = 0; j < mergeTot; j++, i++)
			{
				this.p7.cArr[i] = this.p2.cArr[j];
				this.p2.cArr[j] = null;
				this.p7.size++;
				this.p2.size--;
			}
		}
		
		if (this.p7.isValid(this.p3.cArr[0]) && !this.p3.isEmpty())
		{
			int i = this.p7.getSize();
			int mergeTot = this.p3.getSize();
			System.out.println("Computer's Move: Merging Pile 3 to Pile 7.");
			
			for (int j = 0; j < mergeTot; j++, i++)
			{
				this.p7.cArr[i] = this.p3.cArr[j];
				this.p3.cArr[j] = null;
				this.p7.size++;
				this.p3.size--;
			}
		}
		
		if (this.p7.isValid(this.p4.cArr[0]) && !this.p4.isEmpty())
		{
			int i = this.p7.getSize();
			int mergeTot = this.p4.getSize();
			System.out.println("Computer's Move: Merging Pile 4 to Pile 7.");
			
			for (int j = 0; j < mergeTot; j++, i++)
			{
				this.p7.cArr[i] = this.p4.cArr[j];
				this.p4.cArr[j] = null;
				this.p7.size++;
				this.p4.size--;
			}
		}
		
		if (this.p8.isValid(this.p1.cArr[0]) && !this.p1.isEmpty())
		{
			int i = this.p8.getSize();
			int mergeTot = this.p1.getSize();
			System.out.println("Computer's Move: Merging Pile 1 to Pile 8.");
			
			for (int j = 0; j < mergeTot; j++, i++)
			{
				this.p8.cArr[i] = this.p1.cArr[j];
				this.p1.cArr[j] = null;
				this.p8.size++;
				this.p1.size--;
			}
		}
		
		if (this.p8.isValid(this.p2.cArr[0]) && !this.p2.isEmpty())
		{
			int i = this.p8.getSize();
			int mergeTot = this.p2.getSize();
			System.out.println("Computer's Move: Merging Pile 2 to Pile 8.");
			
			for (int j = 0; j < mergeTot; j++, i++)
			{
				this.p8.cArr[i] = this.p2.cArr[j];
				this.p2.cArr[j] = null;
				this.p8.size++;
				this.p2.size--;
			}
		}
		
		if (this.p8.isValid(this.p3.cArr[0]) && !this.p3.isEmpty())
		{
			int i = this.p8.getSize();
			int mergeTot = this.p3.getSize();
			System.out.println("Computer's Move: Merging Pile 3 to Pile 8.");
			
			for (int j = 0; j < mergeTot; j++, i++)
			{
				this.p8.cArr[i] = this.p3.cArr[j];
				this.p3.cArr[j] = null;
				this.p8.size++;
				this.p3.size--;
			}
		}
		
		if (this.p8.isValid(this.p4.cArr[0]) && !this.p4.isEmpty())
		{
			int i = this.p8.getSize();
			int mergeTot = this.p4.getSize();
			System.out.println("Computer's Move: Merging Pile 4 to Pile 8.");
			
			for (int j = 0; j < mergeTot; j++, i++)
			{
				this.p8.cArr[i] = this.p4.cArr[j];
				this.p4.cArr[j] = null;
				this.p8.size++;
				this.p4.size--;
			}
		}
	}
	
}
