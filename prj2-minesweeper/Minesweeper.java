/* CS 342 - Patrick Troy
 * Project : Minesweeper v2.2
 * Kyle Tulipano - ktulip2
 * Paul Nguyen - pnguye40
 * 2/22/2016
 */

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.util.Random;
import javax.swing.Timer;
import java.io.*;
import java.util.*;

public class Minesweeper extends JFrame implements MouseListener, ActionListener
{
 
 //Instance variable for the game, minesweeper
 ImageIcon[] icn;   //Image array to be allocated
 ImageIcon[] nums;   //Image of numbers array to be allocated
 
 MineButton[] minefield;  //Button object in 10 x 10 grid
 
 JPanel mineboard, top;  //Create panel for top menu and the minesweeper board
 JMenu fmenu;
 
 int numMines;    //Book keeping for number of mines
 int[] mineLocs;    //Book keeping for mine location
 
 //Setup the frame for the menu bar
 JMenuBar menu;
 JMenu game, help;
 JMenuItem about, dHelp, exit, reset, resetTopTen, topTen;
 
 //Setup for the top bar: Number of mines left | Reset button | Timer
 JButton resetBtn;   //Reset button
 MineLabel mineLabel;  //Display number of mines left
 static Timer timer;   //Book keeping for the timer
 TimerLabel timeLabel;  //Display the timer
 
 //Setup for the top ten scoreboard
 JFrame topTenLabel;   //Create a frame for top ten scoreboard 
 JList<String> list;   //Jlist to be inserted into JFrame
 String[] data;    //Book keeping for the top ten scores
 UserScore[] scoreboard;  //Book keeping for the user's score
 
 //Constructor for minesweeper, set all the components required for the game
 public Minesweeper()
 {
  //Set text at top of window
  super("Minesweeper v2.2");
  
  //Exit the program when pressing x at top right of window
  setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  
  //Declare number of mines in the game
  numMines = 10;
  
  //Initialize the mineboard's panel
  mineboard = new JPanel(new GridLayout(10, 10));
  
  //Allocate memory for MineButton array, 10 x 10 = 100
  minefield = new MineButton[100];
  
  //Allocate array to book keeping the locations of the mines
  mineLocs = new int[numMines];
    
  
  
  //Initialize the game's icons
  initIcons();
  
  //Initializez the top ten scoreboard
  topTenLabel = new JFrame("Top Ten Scoreboard");  //Insert the window's title
  topTenLabel.setSize(200, 250);      //Set the default size for the top ten scoreboard
  scoreboard = new UserScore[512];     //The game will hold up to 512 scores
  data = new String[10];        //Allocate 10 strings for top ten scoreboard
  list = new JList<String>(data);      //Update the data to the list to be displayed
  
  
  //Create the top panel that includes the timer, reset button, and number of mines left
  top = new JPanel(new GridLayout());  
  
  //Setup the label that keeps tracks the number of mines left
  mineLabel = new MineLabel();
  
  //Setup the reset button with icon
  resetBtn = new JButton(new ImageIcon("img/head_glasses.gif"));
  resetBtn.setPreferredSize(new Dimension(20, 20));
  resetBtn.addActionListener(this);
  
  
  //Setup the timer label, updates when the timer action event encounters
  timeLabel = new TimerLabel();
  
  //Push the counter for mines left, the reset button, and timer label onto top panel
  top.add(mineLabel);
  top.add(resetBtn);
  top.add(timeLabel);
  
  //Setup the timer to count every second
  timer = new Timer(1000, this);
  
  //setup individual buttons with default unpushed blank
  //icon and add mouse listeners, then add buttons to the board
  //Setup each button with the default setting as unpressed buttons, also add moust listener to each button
  for (int i = 0; i < 100; i++)
  {
   minefield[i] = new MineButton ("", i);   //Initialize each button to default setting
   minefield[i].addMouseListener(this);   //Add a mouse listener to this button
   minefield[i].setIcon(icn[9]);     //The button starts with an unpressed icon
   mineboard.add(minefield[i]);     //Add the button to the 10 x 10 grid
  }
  
  //Randomize the mine location
  setMines(mineLocs);
  
  //Adjust each button's number of mine nearby
  setNums(mineLocs);
  
  //Setup the panel up for the top panel and the grid panel
  getContentPane().setLayout(new BorderLayout());
  setSize(250, 300);
  setResizable(false);
  getContentPane().add(mineboard, BorderLayout.CENTER);
  getContentPane().add(top, BorderLayout.NORTH);
 }
 
 //Main function, initialize the minesweeper with a menubar and start the timer
 public static void main (String [] args)
 {
  JFrame.setDefaultLookAndFeelDecorated(true);
  Minesweeper mine = new Minesweeper();
  mine.setJMenuBar(mine.setMenu());   //Link the menubar to minesweeper
  timer.start();        //Start timer
  mine.setVisible(true);      //Display the board
 }
  
 //Initialize the image icon and number icon array, the icon/number panel are used by the game
 void initIcons()
 {
  //Allocate icons' array
  icn = new ImageIcon[15];

  icn[0] = new ImageIcon("img/button_pressed.gif");    //0 mines nearby
  icn[1] = new ImageIcon("img/button_1.gif");      //1 mines nearby
  icn[2] = new ImageIcon("img/button_2.gif");      //2 mines nearby
  icn[3] = new ImageIcon("img/button_3.gif");      //3 mines nearby
  icn[4] = new ImageIcon("img/button_4.gif");      //4 mines nearby
  icn[5] = new ImageIcon("img/button_5.gif");      //5 mines nearby
  icn[6] = new ImageIcon("img/button_6.gif");      //6 mines nearby
  icn[7] = new ImageIcon("img/button_7.gif");      //7 mines nearby
  icn[8] = new ImageIcon("img/button_8.gif");      //8 mines nearby

  icn[9] = new ImageIcon("img/button_normal.gif");    //Unpressed button
  icn[10] = new ImageIcon("img/button_bomb_blown.gif");   //Blown-up mine
  icn[11] = new ImageIcon("img/button_bomb_pressed.gif");   //Blown-up mine pressed by user
  icn[12] = new ImageIcon("img/button_bomb_x.gif");    //Flagged location that was not a mine
  icn[13] = new ImageIcon("img/button_flag.gif");     //Unpressed flag location
  icn[14] = new ImageIcon("img/button_question.gif");    //Question mark that is still unpressed
  
  //Allocate numbers' array
  nums = new ImageIcon[10];
  for (int i = 0; i < 10; i++)
  {
   //Set the numbers' array for 0 - 9
   nums[i] = new ImageIcon("img/countdown_" + i + ".gif");
  }
 }
 
 
 
 //Track all events when the mouse is clicked
 public void mouseClicked(MouseEvent e)
 {
  int mouseButton = e.getButton();     //Book keeping to see which mouse click was clicked
  MineButton b = (MineButton)e.getSource();   //Get the button that was clicked
  
  //If the user left clicks do the following
  if (mouseButton == MouseEvent.BUTTON1)
  {
   //If the button is allowed to be clicked
   if (b.isClickable())
   {
    //If the button is a mine
    if (b.isMine())
    {
     //End the game by exploding the mines and the user loses
     endGame(b.getIndex());
    }
    
    //If the button is not a mine
    else 
    {
     //Propogate all the zeroes at that point         
     propogateZeros(b.getIndex());
     
     //If the game has 90 pressed buttons the game ends and the user wins
     if (checkIfGameComplete())
     {
      endGame();
     }
    }
   }
  }
  
  //If the user right click and the button is clickable by right click
  else if (mouseButton == MouseEvent.BUTTON3 && b.isRClickable())
  {
   //If the current button status is empty then change it to a flag, also only 10 flags can be placed
   if (b.getFlag() == ' ' && mineLabel.getNumFlags() > 0)
   {
    //Set the status to flag for mine
    b.setFlag('F');
    
    //Update tracking for number of mines left
    mineLabel.subFlag();  //Remove number of mines left since we set a flag
    mineLabel.updateUp();
   }
   
   //If the current button status is a flag then change it to a question mark
   else if (b.getFlag() == 'F')
   {
    //Set the status to question mark if the mine is there or not
    b.setFlag('?');
    
    //Update tracking for number of mines left
    mineLabel.addFlag();  //Add number of mines left since we removed a flag
    mineLabel.updateUp();
   }
   
   //If the current button status is a question mark then change it to an empty status
   else 
   {
    //Set the status to empty space
    b.setFlag(' ');
   }
  }
 }
 
 //Track all the actions performed, from timer and menubar
 public void actionPerformed(ActionEvent e)
 {
  //Get the action being performed
  Object source = e.getSource();
  
  //Reset the game when the reset button is press or the reset in the menubar
  if (reset == source || resetBtn == source)
  {
   reset();
  }
  
  //Exit the game when the user press exit in the menu bar
  else if (exit == source)
  {
   System.exit(0);
  }
  
  //creates topten file if it doesnt exist, and opens it
  //in a window to show top 10 scores
  //Operate the top ten scoreboard
  else if (topTen == source)
  {
   File file = new File("topten.txt");
   
   //If file does not exist, create "topten.txt"
   if (!file.exists())
   {
    FileWriter fw;
    try 
    {
     fw = new FileWriter(file, false);
     PrintWriter pw = new PrintWriter(fw, false);
     pw.flush();
     pw.close();
    }
    
    //If writing the file failed
    catch (IOException e1) 
    {
     System.out.println("File does not exist.");
    }
   }
   
   try 
   {
    int i = 0;
    
    //Scan the "topten.txt" file
    Scanner sc = new Scanner(file);
    while (sc.hasNext())
    {
     //Initialize the name and time to be stored
     String uName;
     int uTime = -1;
     
     //Get the name from the "topten.txt" file
     uName = sc.next();
     
     //Get the time from the "topten.txt" file
     if (sc.hasNextInt())
      uTime = sc.nextInt();
     
     //Initialize the score for this user
     scoreboard[i] = new UserScore(uName, uTime);
     
     i++;
    }
    //Close the scanner
    sc.close();
    
    //Sort the scores from the "topten.txt" file
    sort(scoreboard);
    
    int j = 0;
    
    //Get the top ten scores
    while (j < 10 && scoreboard[j] != null)
    {
     data[j] = scoreboard[j].printuser(j + 1);
     j++;
    }
    
   }
   
   //If scanner the files failed
   catch (FileNotFoundException e1) 
   {
    System.out.println("File does not exist.");
   }
   
   //Adjust the height of the list panel
   list.setFixedCellHeight(20);
   
   //Add the list to the top ten panel
   topTenLabel.add(list);
   
   //Open of the top ten panel
   topTenLabel.setVisible(true);
  }
  
  //Reset the top ten scoreboard
  else if (resetTopTen == source)
  {
   int i = 0;
   
   //Reset the data for the top ten scores
   while (i < 10)
   {
    data[i] = "";
    i++;
   }
   
   int j = 0;
   //Reset the array that holds information from "topten.txt"
   while (j < 512 && scoreboard[j] != null)
   {
    scoreboard[j] = null;
    j++;
   }
   
   //Reset the list
   list.setListData(data);
   
   //Disable the top ten scoreboard
   topTenLabel.setVisible(false);
   
   //Clear the "topten.txt" file
   FileWriter fw;
   try 
   {
    fw = new FileWriter("topten.txt", false);
    PrintWriter pw = new PrintWriter(fw, false);
    pw.flush();
    pw.close();
   }
   
   //If clearing the files failed
   catch (IOException e1) 
   {
    System.out.println("File does not exist.");
   }
  }
  
  //If help is clicked, displays help message
  else if (dHelp == source)
  {
   JOptionPane.showMessageDialog(dHelp, "Goal of the game is to not click on a mine.\n"
   + "The number displayed is the number of bombs adjacent to the button.\n"
   + "You can right click to set a flag as a marker for a mine.\n"
   + "Clear the board without clicking any mines to win.", "Help", JOptionPane.PLAIN_MESSAGE);
  }
  
  //If about is clicked, displays development information
  else if (about == source)
  {
   JOptionPane.showMessageDialog(about, "Project: Minesweeper v2.2\n"
   + "Kyle Tulipano - ktulip2\n"
   + "Paul Nguyen - pnguye40\n", "About", JOptionPane.PLAIN_MESSAGE);
  }
  
  //Update the timer every second and adjust the timer label
  else if (timer == source)
  {
   timeLabel.update();
  }
 }

 //Set the menu bar, menu items, and set mnemonic
 public JMenuBar setMenu()
 {
  menu = new JMenuBar();
  game = new JMenu("Game");
  game.setMnemonic(KeyEvent.VK_G);
  
  reset = new JMenuItem("Reset");
  reset.setMnemonic(KeyEvent.VK_R);
  reset.addActionListener(this);
  
  topTen = new JMenuItem("Top ten");
  topTen.setMnemonic(KeyEvent.VK_T);
  topTen.addActionListener(this);
  
  resetTopTen = new JMenuItem("ReSet top ten");
  resetTopTen.setMnemonic(KeyEvent.VK_S);
  resetTopTen.addActionListener(this);
  
  exit = new JMenuItem("eXit");
  exit.setMnemonic(KeyEvent.VK_X);
  exit.addActionListener(this);
  
  game.add(reset);
  game.add(topTen);
  game.add(resetTopTen);
  game.add(exit);
  
  help = new JMenu("Help");
  help.setMnemonic(KeyEvent.VK_H);
  
  dHelp = new JMenuItem("heLp");
  dHelp.setMnemonic(KeyEvent.VK_L);
  dHelp.addActionListener(this);
  
  about = new JMenuItem("About");
  about.setMnemonic(KeyEvent.VK_A);
  about.addActionListener(this);
  
  help.add(dHelp);
  help.add(about);
  
  menu.add(game);
  menu.add(help);
  
  //return the new menubar
  return menu; 
 }
 
 //This function reset the game
 public void reset()
 {
  //Reset each buttons back to default
  for (int i = 0; i < 100; i++)
  {
   minefield[i].reset();
  }
  
  //Generate mines at random location
  setMines(mineLocs);
  
  //Increment mines nearby the button for each button
  setNums(mineLocs);
  
  //Reset the timer, timelabel, and minelabel
  timeLabel.zero();
  mineLabel.zero();
  
  //Start the timer when the game reset
  timer.start();
 }
 
 //Sort the user's scores
 public void sort(UserScore[] score)
 {
  int i = 0;
  while (i < 512 && score[i] != null)
  {
   int j = 0;
   while (j < 512 && score[j] != null)
   {
    //Sort in ascending order
    if (score[i].getTime() < score[j].getTime())
    {
     UserScore temp = score[i];
     score[i] = score[j];
     score[j] = temp;
    }
    j++;
   }
   i++;
  }
 }
 
 //The parameter takes an integer into minefield array and propogates all zeroes from the parameter location and recursively call for adjacent zeroes
 private void propogateZeros(int i)
 {
  //Determine the location of the mine by x and y position
  int x = i % 10;
  int y = i / 10;
  
  //If the buttons are unclickable, or is a mine do nothting
  if (!minefield[i].isClickable() || minefield[i].isMine())
  {
   return;
  }
  
  //Otherwise if the number of the surrounding mines is not zero, reveal the button
  else if (minefield[i].getSurrMines()!=0)
  {
   minefield[i].reveal();
   return;
  }
  
  //Otherwise the button is clickable and the surrounded by 0 mines, so reveal adjacent buttons recursively
  else
  {
   minefield[i].reveal();  //Reveal current location
   
   //Top left corner tile case
   if (x == 0 && y == 0)
   {
    propogateZeros(i+1);
    propogateZeros(i+10);
    propogateZeros(i+11);
   }
   //Top right corner tile case
   else if (x == 0 && y == 9)
   {
    propogateZeros(i+1);
    propogateZeros(i-9);
    propogateZeros(i-10);
   }
   //Bottom left corner tile case
   else if (x == 9 && y == 0)
   {
    propogateZeros(i-1);
    propogateZeros(i+9);
    propogateZeros(i+10);
   }
   //Bottom right corner tile case
   else if (x == 9 && y == 9)
   {
    propogateZeros(i-1);
    propogateZeros(i-11);
    propogateZeros(i-10);
   }
   //Top row and not a corner
   else if (x == 0)
   {
    propogateZeros(i+1);
    propogateZeros(i-10);
    propogateZeros(i-9);
    propogateZeros(i+10);
    propogateZeros(i+11);
   }
   //Bottom row and not a corner
   else if (x == 9)
   {
    propogateZeros(i-1);
    propogateZeros(i-10);
    propogateZeros(i-11);
    propogateZeros(i+10);
    propogateZeros(i+9);
   }
   //Left side and not a corner
   else if (y == 0)
   {
    propogateZeros(i+1);
    propogateZeros(i-1);
    propogateZeros(i+9);
    propogateZeros(i+10);
    propogateZeros(i+11);
   }
   //Right side and not a corner
   else if (y == 9)
   {
    propogateZeros(i+1);
    propogateZeros(i-1);
    propogateZeros(i-9);
    propogateZeros(i-10);
    propogateZeros(i-11);
   }
   //Else we are somewhere in the center tile
   else
   {
    propogateZeros(i+1);
    propogateZeros(i-1);
    propogateZeros(i+9);
    propogateZeros(i+10);
    propogateZeros(i+11);
    propogateZeros(i-9);
    propogateZeros(i-10);
    propogateZeros(i-11);
   }
  }
 }
 
 //Check if the game is complete, which is when 90 buttons have been pressed
 boolean checkIfGameComplete()
 {
  int counter = 0;
  
  //Loop through each buttons to see if its been pressed
  for (int i = 0; i < 100; i++)
  {
   //For each button pressed, increment the counter
   if (minefield[i].isRevealed())
   {
    counter++;
   }
  }
  
  //If 90 buttons have pressed the user wins
  if (counter == 90)
   return true;
  
  //Otherwise the user have not won yet
  return false;
 }
 
 //Takes an allocated array of integers, and randomize the mine locations and store the info into the array
 private void setMines(int[] m)
 {
  //Setup 10 randomize mines
  for (int c = 0; c < numMines; c++)
  {
   //Randomize a number from 0 to 99
   Random r = new Random();
   int i = r.nextInt(100);
   
   //If there is already a mine in that location we reset c by 1 to try again  
   if (minefield[i].isMine())
   {

    c--;
   }
   
   //Set the mine
   else
   {
    minefield[i].setMine();
    m[c] = i;     //Book keeping of where the mine is located
   }
  }
 }
 
 //Set the number of mines nearby each button
 private void setNums(int[] m)
 {
  for (int c = 0; c < numMines; c++)
  {
   //Look at each mine location and adjust the adjacent buttons
   int curr = m[c];
   
   //Get the proper location of the mines in x and y position
   int x = curr % 10;
   int y = curr / 10;
   
   //If the mine is in bottom right
   if (y == 9 && x == 9)
   {
    minefield[curr-1].incCount();
    minefield[curr-10].incCount();
    minefield[curr-11].incCount();
   }
   
   //Mine in bottom left
   else if (y == 9 && x == 0)
   {
    minefield[curr+1].incCount();
    minefield[curr-9].incCount();
    minefield[curr-10].incCount();
   }
   
   //Mine in top right
   else if (y == 0 && x == 9)
   {
    minefield[curr-1].incCount();
    minefield[curr+9].incCount();
    minefield[curr+10].incCount();
   }
   
   //Mine in top left
   else if (y == 0 && x == 0)
   {
    minefield[curr+1].incCount();
    minefield[curr+10].incCount();
    minefield[curr+11].incCount();
   }
   
   //Mine on top edge
   else if (y == 0)
   {
    minefield[curr+1].incCount();
    minefield[curr-1].incCount();
    minefield[curr+9].incCount();
    minefield[curr+10].incCount();
    minefield[curr+11].incCount();
   }
   
   //Mine on left edge
   else if (x == 0)
   {
    minefield[curr+1].incCount();
    minefield[curr-10].incCount();
    minefield[curr+10].incCount();
    minefield[curr+11].incCount();
    minefield[curr-9].incCount();
   }
   //Mine on bottom edge
   else if (y == 9)
   {
    minefield[curr+1].incCount();
    minefield[curr-1].incCount();
    minefield[curr-9].incCount();
    minefield[curr-10].incCount();
    minefield[curr-11].incCount();
   }
   //Mine on right edge
   else if (x == 9)
   {
    minefield[curr-1].incCount();
    minefield[curr-11].incCount();
    minefield[curr-10].incCount();
    minefield[curr+9].incCount();
    minefield[curr+10].incCount();
   }
   //Mine in center
   else{
    minefield[curr+1].incCount();
    minefield[curr-1].incCount();
    minefield[curr+9].incCount();
    minefield[curr-9].incCount();
    minefield[curr+10].incCount();
    minefield[curr-10].incCount();
    minefield[curr+11].incCount();
    minefield[curr-11].incCount();
   }
  }
 }
 /*fcn endGame(1)
 * calls detonate, disables buttons, 
 * and sets flags that werent bombs to be crossed out bombs
 * disables buttons so they cant be clicked
 * then stops the timer
 * endgame(0)
 * if called with no parameters it ends the game as if the user won
 * so no bomb icons are written/detonated, then writes user high score
 * if time is less than a high score.
 */
 
 //If called end game with no parameters it ends the game as if the user won
 void endGame()
 {
  //Stop the timer
  timer.stop();
  
  //Change the number of mines left to 00
  mineLabel.endgame();
  
  //Set each bombs as flagged
  for (int i = 0; i< 10; i++)
   minefield[mineLocs[i]].setIcon(icn[13]);
  
  //Disable every button in the game
  for (int q = 0; q < 100; q++)
   minefield[q]._disable();
  
  //Popup a window for user to input their names
  getUserNameViaPopUp();
 }
 
 //If called end game with a parameter the user has clicked that bomb at that location and the user loses
 void endGame(int i)
 {
  //Stop the timer
  timer.stop();
  
  //Detonate all the bombs
  detonate(i);
  
  for (int q = 0; q < 100; q++)
  {
   //Disable every button in the game
   minefield[q]._disable();
   
   //If the button is set as a flag and it is not a mine, change the icon to invalid flag
   if ((minefield[q].getFlag() == 'F') && !(minefield[q].isMine()))
    minefield[q].setIcon(icn[12]);
  }
 }
 
 /* fcn: detonate
 * reveals all mines
 * red mine button is one that was pressed
 * displays mine with X if a flag was placed
 * on a button that was not a mine.
 */
 
 //Detonates all the mines and reveals them, the parameter will display the location where the mine was pressed
 void detonate(int i)
 {
  for (int c = 0; c < numMines; c++)
  {
   //Show all the mines location
   if (i == mineLocs[c])
    minefield[i].setIcon(icn[10]);
   
   //Detonates each bomb
   else if(minefield[mineLocs[c]].getFlag() != 'F')
    minefield[mineLocs[c]].setIcon(icn[11]);
  }
 }
 
 
 //Prompt user for the name to put in the top ten scoreboard
 void getUserNameViaPopUp()
 {
  //Create a new frame for getting user name
  JFrame frame = new JFrame();
  String nameIn = (String)JOptionPane.showInputDialog(frame, 
  "Please input your name.", "You win!", 
  JOptionPane.PLAIN_MESSAGE);
  
  int i = nameIn.length();
  
  //Loop until user input something to prevent risk
  while (i == 0)
  {
   nameIn = (String)JOptionPane.showInputDialog(frame, 
   "Invalid input. Please input your name.", "You win!", 
   JOptionPane.PLAIN_MESSAGE);
   i = nameIn.length();
  }
  
  int temp = nameIn.length();
  String tempIn = null;
  
  //Check if the string length is more then 1
  if (temp > 1)
   //Remove all spaces
   tempIn = nameIn.replaceAll(" ", "");
  
  //Get the current time
  int timeIn = timeLabel.getTime();
  
  //Attempt to append the username and time to the file
 /* try(PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("topten.txt", true)))) 
  {
   out.println(tempIn + " " + timeIn);
  }
  
  //If appending to "topten.txt" failed
  catch (IOException e) 
  {
   System.out.println("File does not exist.");
  }*/
 }
 
 /*following four events are not used
 * but required as per implements MouseListener
 */
 //Following four events are not used but required as per implements MouseListener
 public void mouseEntered(MouseEvent e)
 {
 }
 public void mouseExited(MouseEvent e)
 {
 }
 public void mousePressed(MouseEvent e)
 {
 }
 public void mouseReleased(MouseEvent e)
 {
 }
 
 //Object class for the buttons in the 10 x 10 grid
 private class MineButton extends JButton
 {
  //Instance variable for this button
  int numSurroundingMines;  //Book keeping for mines around this button
  int index;      //Book keeping for location of the button
  boolean mine;     //Check if mine exist in this button
  boolean lClickable;    //Check if left click is allowed
  boolean rClickable;    //Check if right click is allowed
  boolean revealed;    //Check if this button is already revealed
  char rClickMark;    //Book keeping for setting the flag, question mark, or empty
  
  //Constructor for buttons
  MineButton(String s, int i)
  {
   super(s);
   index = i;
   mine = false;
   lClickable = true;
   rClickable = true;
   revealed = false;
   numSurroundingMines = 0;
   rClickMark = ' ';
  }
  
  //Return the index of the button
  int getIndex()
  {
   return index;
  }
  
  //Return if the button is a mine or not
  boolean isMine()
  {
   return mine;
  }
  
  //Set this button as a mine
  void setMine()
  {
   mine = true;
  }
  
  //Increment the number of mines around this button
  void incCount()
  {
   numSurroundingMines++;
  }
  
  //Return the number of mines around this button
  int getSurrMines()
  {
   return numSurroundingMines;
  }
  
  //Returns what kind of right click mark the button is showing
  char getFlag()
  {
   return rClickMark;
  }
  
  //Set the flag to either flag, question mark, or empty when user right clicks the button
  void setFlag(char c)
  {
   //Set the button mark as empty
   rClickMark = c;
   if (c == ' ')
   {
    lClickable = true;
    this.setIcon(icn[9]);
   }
   
   //Set the button mark as a flag
   else if (c == 'F')
   {
    lClickable = false;
    this.setIcon(icn[13]);
   }
   
   //Set the button mark as a question mark
   else
   {
    lClickable = false;
    this.setIcon(icn[14]);
   }
  }
  
  //Disable clicking for this button
  void _disable()
  {
   lClickable = false;
   rClickable = false;
  }
  
  //Return if this button is left clickable
  boolean isClickable()
  {
   return lClickable;
  }
  
  //Return if this button is right clickable
  boolean isRClickable()
  {
   return rClickable;
  }
  
  //Reveal the button, the number of mines around this button will be displayed
  void reveal()
  {
   //Disable clicking access for this button
   lClickable = false;
   rClickable = false;
   revealed = true;
   
   //Set the proper number base on the number of mines around this button
   this.setIcon(icn[numSurroundingMines]);
  }
  
  //Return if this button is revealed
  boolean isRevealed()
  {
   return revealed;
  }

  //Reset this button back to its orginial state
  void reset()
  {
   mine = false;
   lClickable = true;
   rClickable = true;
   revealed = false;
   numSurroundingMines = 0;
   rClickMark = ' ';
   
   //Set this button back to unpressed image
   this.setIcon(icn[9]);
  }
 }
 
 //Object class for the timer and timer label
 private class TimerLabel extends JPanel
 {
  //Instance variable for timer label
  int time;  //Book keeping for the time
  JLabel time100; //Store 100's value of time
  JLabel time10;  //Store 10's value of time
  JLabel time1;   //Store 1's value of time
  
  //Constructor for timer label
  TimerLabel()
  {
   time = 0;         //Set time to 0
   
   //Set the label to 000
   time100 = new JLabel(nums[0]);
   time10 = new JLabel(nums[0]);
   time1 = new JLabel(nums[0]);
   
   //Add the image icon into timer label
   this.add(time100, BorderLayout.CENTER);
   this.add(time10, BorderLayout.CENTER);
   this.add(time1, BorderLayout.CENTER);
  }
  
  //Update the time and caps off at 999
  void update()
  {
   if (time < 999)
   {
    time++;  //Increment time every second
    
    //Calculate the time to get the right image icon
    int timeH = time / 100;
    int timeT = (time / 10) % 10;
    int timeO = (time % 100) % 10;
    
    //Modify the timer label icon base off the time
    time100.setIcon(nums[timeH]);
    time10.setIcon(nums[timeT]);
    time1.setIcon(nums[timeO]);
   }
  }
  
  //Zero out the timer
  void zero()
  {
   //Reset time to 0
   time = 0;
   
   //Reset timer label to 000
   time100.setIcon(nums[0]);
   time10.setIcon(nums[0]);
   time1.setIcon(nums[0]);
  }
  
  //Get the current time
  int getTime()
  {
   //Set the cap of the timer at 999
   if (time > 999)
    time = 999;
   
   return time;
  }
 }
 
 //Object class for number of mines left label
 private class MineLabel extends JPanel
 {
  //Instance variable for mines left label
  int numFlagsPlaceable;   //Book keeping for number of flags placed
  JLabel mine10;     //Store 10's value of mines left
  JLabel mine1;     //Store 1's value of mines left
  
  //Constructor for number of mines left label
  MineLabel()
  {
   numFlagsPlaceable = 10;   //Game starts with 10 mines
   mine10 = new JLabel(nums[1]); //Set the 10's value
   mine1 = new JLabel(nums[0]); //Set the 1's value
   
   //Add the image icon into mines left
   this.add(mine10);
   this.add(mine1);
  }
  
  //Update the number of mines left base on how many flags have been set
  void updateUp()
  {
   //If number of flag place is 10 then the label will be 10
   if (numFlagsPlaceable == 10)
   {
    mine10.setIcon(nums[1]);
    mine1.setIcon(nums[0]);
   }
   
   //Check the label of the mines left base on how many flags have been placed
   else
   {
    mine1.setIcon(nums[numFlagsPlaceable]);
    mine10.setIcon(nums[0]);
   }
  }
  
  //Reset the numbers of mine left back to 10
  void zero()
  {
   numFlagsPlaceable = 10;
   mine10.setIcon(nums[1]);
   mine1.setIcon(nums[0]);
  }
  
  //Decrement the number of flags placed
  void subFlag()
  {
   numFlagsPlaceable--;
  }
  
  //Increment the number of flags placed
  void addFlag()
  {
   numFlagsPlaceable++;
  }
  
  //Get the number of flags placed
  int getNumFlags()
  {
   return numFlagsPlaceable;
  }
  
  //When the game ends change the number of mines left to 00
  void endgame()
  {
   numFlagsPlaceable = 0;
   mine10.setIcon(nums[0]);
   mine1.setIcon(nums[0]);
  }
 }
}
