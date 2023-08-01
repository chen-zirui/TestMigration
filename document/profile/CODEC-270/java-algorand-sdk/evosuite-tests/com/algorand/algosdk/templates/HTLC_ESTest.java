/*
 * This file was automatically generated by EvoSuite
 * Sat Jul 29 06:56:30 GMT 2023
 */

package com.algorand.algosdk.templates;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import com.algorand.algosdk.templates.HTLC;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class) @EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = true, useJEE = true) 
public class HTLC_ESTest extends HTLC_ESTest_scaffolding {

  @Test(timeout = 4000)
  public void test0()  throws Throwable  {
      // Undeclared exception!
      try { 
        HTLC.MakeHTLC("keccak256", "pad must not be in alphabet or whitespace", "keccak256", "keccak256", (-1165973358), (-1165973358));
        fail("Expecting exception: IllegalArgumentException");
      
      } catch(IllegalArgumentException e) {
         //
         // Input string is an invalid address. Wrong length
         //
         verifyException("com.algorand.algosdk.crypto.Address", e);
      }
  }

  @Test(timeout = 4000)
  public void test1()  throws Throwable  {
      // Undeclared exception!
      try { 
        HTLC.MakeHTLC((String) null, "com.algorand.algosdk.templates.HTLC", (String) null, "com.algorand.algosdk.templates.HTLC", (-72), (-72));
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
      }
  }

  @Test(timeout = 4000)
  public void test2()  throws Throwable  {
      // Undeclared exception!
      try { 
        HTLC.MakeHTLC("sha25@", "sha25@", "sha25@", "sha25@", (-1479732488), (-1479732488));
        fail("Expecting exception: RuntimeException");
      
      } catch(RuntimeException e) {
         //
         // invalid hash function supplied
         //
         verifyException("com.algorand.algosdk.templates.HTLC", e);
      }
  }

  @Test(timeout = 4000)
  public void test3()  throws Throwable  {
      // Undeclared exception!
      try { 
        HTLC.MakeHTLC("p\"d mustTnot be in alphabetBor whitespace", "p\"d mustTnot be in alphabetBor whitespace", "sha256", "p\"d mustTnot be in alphabetBor whitespace", (-1165973340), (-1165973340));
        fail("Expecting exception: IllegalArgumentException");
      
      } catch(IllegalArgumentException e) {
         //
         // Last encoded character (before the paddings if any) is a valid base 32 alphabet but not a possible value
         //
         verifyException("org.apache.commons.codec.binary.Base32", e);
      }
  }

  @Test(timeout = 4000)
  public void test4()  throws Throwable  {
      // Undeclared exception!
      try { 
        HTLC.MakeHTLC("YOmnU", "YOmnU", "keccak256", "YOmnU", 1684, 0);
        fail("Expecting exception: IllegalArgumentException");
      
      } catch(IllegalArgumentException e) {
         //
         // Input string is an invalid address. Wrong length
         //
         verifyException("com.algorand.algosdk.crypto.Address", e);
      }
  }

  @Test(timeout = 4000)
  public void test5()  throws Throwable  {
      HTLC hTLC0 = new HTLC();
  }
}