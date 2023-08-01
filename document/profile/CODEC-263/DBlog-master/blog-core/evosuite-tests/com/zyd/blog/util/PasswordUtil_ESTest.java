/*
 * This file was automatically generated by EvoSuite
 * Sat Jul 29 06:51:27 GMT 2023
 */

package com.zyd.blog.util;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import com.zyd.blog.util.PasswordUtil;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class) @EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = true, useJEE = true) 
public class PasswordUtil_ESTest extends PasswordUtil_ESTest_scaffolding {

  @Test(timeout = 4000)
  public void test0()  throws Throwable  {
      String string0 = PasswordUtil.decrypt("", "929123f8f17944e8b0a531045453e1f1");
      assertEquals("", string0);
  }

  @Test(timeout = 4000)
  public void test1()  throws Throwable  {
      try { 
        PasswordUtil.encrypt((String) null, "~hl>");
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
         verifyException("com.zyd.blog.util.AesUtil", e);
      }
  }

  @Test(timeout = 4000)
  public void test2()  throws Throwable  {
      try { 
        PasswordUtil.decrypt("^3:QcvB}J", "^3:QcvB}J");
        fail("Expecting exception: IllegalBlockSizeException");
      
      } catch(IllegalBlockSizeException e) {
      }
  }

  @Test(timeout = 4000)
  public void test3()  throws Throwable  {
      try { 
        PasswordUtil.decrypt("P8pdX4NmuMpxOT2jpD04/g==", "Gk");
        fail("Expecting exception: BadPaddingException");
      
      } catch(BadPaddingException e) {
      }
  }

  @Test(timeout = 4000)
  public void test4()  throws Throwable  {
      try { 
        PasswordUtil.decrypt("kX3^3'RcS", (String) null);
        fail("Expecting exception: IllegalArgumentException");
      
      } catch(IllegalArgumentException e) {
         //
         // Last encoded character (before the paddings if any) is a valid base 64 alphabet but not a possible value
         //
         verifyException("org.apache.commons.codec.binary.Base64", e);
      }
  }

  @Test(timeout = 4000)
  public void test5()  throws Throwable  {
      String string0 = PasswordUtil.decrypt("mnFHdS3Pl5LWgAOp14BkDPABPTPFHocUg/zJ98KF1buZC5QGCiPg0TZOWxn8dO+q", "929123f8f17944e8b0a531045453e1f1");
      assertEquals("929123f8f17944e8b0a531045453e1f1", string0);
  }

  @Test(timeout = 4000)
  public void test6()  throws Throwable  {
      PasswordUtil passwordUtil0 = new PasswordUtil();
  }

  @Test(timeout = 4000)
  public void test7()  throws Throwable  {
      String string0 = PasswordUtil.encrypt("!x`z(L v8J", "");
      assertEquals("qAxpLnZNF2RKv05bxWhQCg==", string0);
  }
}
