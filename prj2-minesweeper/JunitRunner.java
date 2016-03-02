//package com.javacodegeeks.junit;
/* CS 342 - Patrick Troy
 * Project : Minesweeper v2.2 JUnit Testing
 * Kyle Tulipano - ktulip2
 * Paul Nguyen - pnguye40
 * 2/22/2016
 */
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class JunitRunner {

 public static void main(String[] args) {

  Result result = JUnitCore.runClasses( mineTest.class );
  for (Failure fail : result.getFailures()) {
   System.out.println(fail.toString());
  }
  if (result.wasSuccessful()) {
   System.out.println("All tests finished successfully...");
  }
  else
    
   System.out.println("Not all test have pass successfully...");
 }
}
