/* CS 342 - Patrick Troy
 * Project : Minesweeper v2.2 JUnit Testing
 * Kyle Tulipano - ktulip2
 * Paul Nguyen - pnguye40
 * 3/1/2016
 */

import java.awt.event.*;
import static org.junit.Assert.*;
import org.junit.Test;
import java.io.File;
import java.awt.Robot;
import java.io.FileWriter;
import java.util.*;

public class mineTest
{
 //Check the proper length for the array to hold the image icons
 @Test
 public void testIcnLen()
 {
  Minesweeper m = new Minesweeper();
  assertEquals("Icon array is not defined", m.icn.length, 15);
 }

 //Check all the mines are within boundaries of the grid
 @Test
 public void testMineLocation()
 {
  Minesweeper m = new Minesweeper();
  for (int i = 0; i < 10; i++)  
   if (m.mineLocs[i] > 99 || m.mineLocs[i] < 0)
   {
    assertTrue("A mine is not in the boundaries.", false);
    return;
   }
  assertTrue("A mine is not in the boundaries.", true);
 }

 //Check if the image folder exist to access the images required for the game
 @Test
 public void checkImgFolder()
 {
  File i = new File("./img");
  assertTrue("Cannot locate image folder '/img'!", i.exists());
 }

 //check all image icons because the names are hardcoded game is impossible without them
 @Test
 public void checkIconsExist()
 {
  File i = new File("./img/button_1.gif");
  assertTrue("Cannot locate icon image './img/button_1.gif!'", i.exists());
  i = new File("./img/button_2.gif");
  assertTrue("Cannot locate icon image './img/button_2.gif!'", i.exists());
  i = new File("./img/button_3.gif");
  assertTrue("Cannot locate icon image './img/button_3.gif!'", i.exists());
  i = new File("./img/button_4.gif");
  assertTrue("Cannot locate icon image './img/button_4.gif!'", i.exists());
  i = new File("./img/button_5.gif");
  assertTrue("Cannot locate icon image './img/button_5.gif!'", i.exists());
  i = new File("./img/button_6.gif");
  assertTrue("Cannot locate icon image './img/button_6.gif!'", i.exists());
  i = new File("./img/button_7.gif");
  assertTrue("Cannot locate icon image './img/button_7.gif!'", i.exists());
  i = new File("./img/button_8.gif");
  assertTrue("Cannot locate icon image './img/button_8.gif!'", i.exists());
  i = new File("./img/countdown_0.gif");
  assertTrue("Cannot locate icon image './img/contdown_0.gif!'", i.exists());
  i = new File("./img/countdown_1.gif");
  assertTrue("Cannot locate icon image './img/contdown_1.gif!'", i.exists());
  i = new File("./img/countdown_2.gif");
  assertTrue("Cannot locate icon image './img/contdown_2.gif!'", i.exists());
  i = new File("./img/countdown_3.gif");
  assertTrue("Cannot locate icon image './img/contdown_3.gif!'", i.exists());
  i = new File("./img/countdown_4.gif");
  assertTrue("Cannot locate icon image './img/contdown_4.gif!'", i.exists());
  i = new File("./img/countdown_5.gif");
  assertTrue("Cannot locate icon image './img/contdown_5.gif!'", i.exists());
  i = new File("./img/countdown_6.gif");
  assertTrue("Cannot locate icon image './img/contdown_6.gif!'", i.exists());
  i = new File("./img/countdown_7.gif");
  assertTrue("Cannot locate icon image './img/contdown_7.gif!'", i.exists());
  i = new File("./img/countdown_8.gif");
  assertTrue("Cannot locate icon image './img/contdown_8.gif!'", i.exists());
  i = new File("./img/countdown_9.gif");
  assertTrue("Cannot locate icon image './img/contdown_9.gif!'", i.exists());
  i = new File("./img/button_normal.gif");
  assertTrue("Cannot locate icon image './img/button_normal.gif!'", i.exists());
  i = new File("./img/button_pressed.gif");
  assertTrue("Cannot locate icon image './img/button_pressed.gif!'", i.exists());
  i = new File("./img/button_question.gif");
  assertTrue("Cannot locate icon image './img/button_question.gif!'", i.exists());
  i = new File("./img/button_flag.gif");
  assertTrue("Cannot locate icon image './img/button_flag.gif!'", i.exists());
  i = new File("./img/button_bomb_blown.gif");
  assertTrue("Cannot locate icon image './img/button_bomb_blown.gif!'", i.exists());
  i = new File("./img/button_bomb_pressed.gif");
  assertTrue("Cannot locate icon image './img/button_bomb_pressed.gif!'", i.exists());
  i = new File("./img/button_bomb_x.gif");
  assertTrue("Cannot locate icon image './img/button_bomb_x.gif!'", i.exists());
 }

 //Test the board has the proper amount of mines when initialize
 @Test
 public void testBoardSize()
 {
  Minesweeper m = new Minesweeper();
  assertEquals("game has incorrect number of mines", m.numMines, 10);
 }

 //Test each button is properly allocated
 @Test
 public void testMinefield()
 {
  Minesweeper m = new Minesweeper();
  for (int i = 0; i<100; i++)
  {
   assertFalse("minefield button " + i + "is null!", m.minefield[i] == null);
  }
 }

 //Test the top panel is properly initialize
 @Test
 public void testJPanel()
 {
  Minesweeper m = new Minesweeper();
  assertNotNull("Top JPanel not properly initialized", m.top);
  assertNotNull("Reset Button not properly initialized", m.resetBtn);
 }

 //Check the proper length for the array to hold the user's score
 @Test
 public void testUserScoreLen()
 {
  Minesweeper m = new Minesweeper();
  assertEquals("Userscore array is not defined", m.data.length, 10);
 }

 //Check the proper length for the array of the buffer
 @Test
 public void testBufferLen()
 {
  Minesweeper m = new Minesweeper();
  assertEquals("Userscore array is not defined", m.scoreboard.length, 512);
 }


 //Test getting the user's score/time
 @Test
 public void userScoreTime()
 {
  try
  {

   Minesweeper mine = new Minesweeper();
   mine.setJMenuBar(mine.setMenu());
   try
   {
    File file = new File("topten.txt");
    FileWriter fw;
    fw = new FileWriter(file, false);
    fw.write("bbbb 123");
    fw.close();
   }
   catch (java.io.IOException e)
   {
    e.printStackTrace();
   }
   
   mine.timer.start();
   mine.setVisible(true);
   Robot r = new Robot();
   r.keyPress(KeyEvent.VK_ALT);
   r.keyPress(KeyEvent.VK_G);
   r.delay(500);
   r.keyRelease(KeyEvent.VK_G);
   r.keyRelease(KeyEvent.VK_ALT);

   r.keyPress(KeyEvent.VK_T);
   r.delay(500);
   r.keyRelease(KeyEvent.VK_T);
   mine.topTenLabel.setVisible(false);
   r.delay(500);
   int time = mine.scoreboard[0].getTime();
   assertTrue("Getting the userscore's time is incorrect.", time == 123);

   r.keyPress(KeyEvent.VK_ALT);
   r.keyPress(KeyEvent.VK_G);
   r.delay(500);
   r.keyRelease(KeyEvent.VK_G);
   r.keyRelease(KeyEvent.VK_ALT);

   r.keyPress(KeyEvent.VK_S);
   r.delay(500);
   r.keyRelease(KeyEvent.VK_S);

   mine.setVisible(false);
   return;
  }
  catch (java.awt.AWTException e)
  {
   e.printStackTrace();
  }
 }



 //Test the print function for the user's score
 @Test
 public void userScorePrintTest()
 {
  try
  {
   Minesweeper mine = new Minesweeper();
   mine.setJMenuBar(mine.setMenu());
   try
   {
    File file = new File("topten.txt");
    FileWriter fw;
    fw = new FileWriter(file, false);
    fw.write("bbbb 123");
    fw.close();
   }
   catch (java.io.IOException e)
   {
    e.printStackTrace();
   }
   
   mine.timer.start();
   mine.setVisible(true);
   Robot r = new Robot();
   r.keyPress(KeyEvent.VK_ALT);
   r.keyPress(KeyEvent.VK_G);
   r.delay(500);
   r.keyRelease(KeyEvent.VK_G);
   r.keyRelease(KeyEvent.VK_ALT);

   r.keyPress(KeyEvent.VK_T);
   r.delay(500);
   r.keyRelease(KeyEvent.VK_T);
   mine.topTenLabel.setVisible(false);
   r.delay(500);
   String userinfo = mine.scoreboard[0].printuser(1);
   assertTrue("Getting the userscore's information is incorrect.", userinfo.equals("1. bbbb  ::  123"));

   r.keyPress(KeyEvent.VK_ALT);
   r.keyPress(KeyEvent.VK_G);
   r.delay(500);
   r.keyRelease(KeyEvent.VK_G);
   r.keyRelease(KeyEvent.VK_ALT);

   r.keyPress(KeyEvent.VK_S);
   r.delay(500);
   r.keyRelease(KeyEvent.VK_S);

   mine.setVisible(false);
   return;
  }
  catch (java.awt.AWTException e)
  {
   e.printStackTrace();
  }
 }


 //Test the topten.txt file exist
 @Test
 public void topTenTest()
 {
  try
  {
   Minesweeper mine = new Minesweeper();
   mine.setJMenuBar(mine.setMenu());
   mine.timer.start();
   mine.setVisible(true);
   Robot r = new Robot();
   r.delay(500);
   r.keyPress(KeyEvent.VK_ALT);
   r.keyPress(KeyEvent.VK_G);
   r.delay(500);
   r.keyRelease(KeyEvent.VK_G);
   r.keyRelease(KeyEvent.VK_ALT);
   r.keyPress(KeyEvent.VK_T);
   r.delay(500);
   r.keyRelease(KeyEvent.VK_T);
   File f = new File("topten.txt");
   assertTrue("Could not create topten.txt file!", f.exists());
   mine.topTenLabel.setVisible(false);

   r.delay(500);
   mine.setVisible(false);
   return;
  }
  catch (java.awt.AWTException e)
  {
   e.printStackTrace();
  }
 }

 //Test the reset top ten function
 @Test
 public void topTenTest2()
 {
  try
  {

   Minesweeper mine = new Minesweeper();
   mine.setJMenuBar(mine.setMenu());
   try
   {
    File file = new File("topten.txt");
    FileWriter fw;
    fw = new FileWriter(file, false);
    fw.write("aaaa 999");
    fw.close();
   }
   catch (java.io.IOException e)
   {
    e.printStackTrace();
   }
   
   mine.timer.start();
   mine.setVisible(true);
   Robot r = new Robot();
   r.keyPress(KeyEvent.VK_ALT);
   r.keyPress(KeyEvent.VK_G);
   r.delay(500);
   r.keyRelease(KeyEvent.VK_G);
   r.keyRelease(KeyEvent.VK_ALT);
   r.keyPress(KeyEvent.VK_S);
   r.delay(500);
   r.keyRelease(KeyEvent.VK_S);
   mine.topTenLabel.setVisible(false);
   r.delay(500);
   mine.setVisible(false);

   File file = new File ("topten.txt");
   try
   {
    Scanner sc = new Scanner(file);
    boolean test = sc.hasNext();
    assertFalse("Reset top ten has failed.", test);
    sc.close();
   }
   catch (java.io.FileNotFoundException e)
   {
    e.printStackTrace();
   }
   return;
  }
  catch (java.awt.AWTException e)
  {
   e.printStackTrace();
  }
 }


 //Test the reset button works properly
 @Test
 public void resetTest()
 {
  try
  {

   Minesweeper mine = new Minesweeper();
   mine.setJMenuBar(mine.setMenu());

   mine.timer.start();
   mine.setVisible(true);
   Robot r = new Robot();
   r.keyPress(KeyEvent.VK_ALT);
   r.keyPress(KeyEvent.VK_G);
   r.delay(2000);
   r.keyRelease(KeyEvent.VK_G);
   r.keyRelease(KeyEvent.VK_ALT);

   r.keyPress(KeyEvent.VK_R);
   r.delay(500);
   r.keyRelease(KeyEvent.VK_R);
   mine.setVisible(false);
   boolean test = mine.numMines == 10;
   assertTrue("Reset top ten has failed.", test);
   return;
  }
  catch (java.awt.AWTException e)
  {
   e.printStackTrace();
  }
 }


 //Test the checker that sees if the game is complete.
 @Test
 public void gameEndingTest()
 {
  Minesweeper mine = new Minesweeper();
  mine.setJMenuBar(mine.setMenu());
  assertFalse("Game should have not ended.", mine.checkIfGameComplete());
 }
}


