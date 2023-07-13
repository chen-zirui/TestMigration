/*
 * This file was automatically generated by EvoSuite
 * Sat Oct 24 09:28:10 GMT 2015
 */

package org.evosuite.runtime.util;

import org.junit.Test;
import static org.junit.Assert.*;
import static shaded.org.evosuite.runtime.EvoAssertions.*;
import java.io.IOException;
import org.evosuite.runtime.util.ByteDataInputStream;
import org.junit.runner.RunWith;
import shaded.org.evosuite.runtime.EvoRunner;
import shaded.org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class) @EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true)
public class ByteDataInputStream_ESTest extends ByteDataInputStream_ESTest_scaffolding {

  @Test
  public void test00()  throws Throwable  {
      ByteDataInputStream byteDataInputStream0 = new ByteDataInputStream("1");
      long long0 = byteDataInputStream0.skip((byte)22);
      assertEquals(1L, long0);

      int int0 = byteDataInputStream0.available();
      assertEquals(0, int0);
  }

  @Test
  public void test01()  throws Throwable  {
      byte[] byteArray0 = new byte[1];
      ByteDataInputStream byteDataInputStream0 = new ByteDataInputStream(byteArray0);
      int int0 = byteDataInputStream0.available();
      assertEquals(1, int0);
  }

  @Test
  public void test02()  throws Throwable  {
      byte[] byteArray0 = new byte[1];
      ByteDataInputStream byteDataInputStream0 = new ByteDataInputStream(byteArray0);
      int int0 = byteDataInputStream0.read();
      int int1 = byteDataInputStream0.read();
      assertFalse(int1 == int0);
      assertEquals((-1), int1);
  }



  @Test
  public void test04()  throws Throwable  {
      ByteDataInputStream byteDataInputStream0 = null;
      try {
        byteDataInputStream0 = new ByteDataInputStream((String) null);
        fail("Expecting exception: NullPointerException");

      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
         assertThrownBy("org.evosuite.runtime.util.ByteDataInputStream", e);
      }
  }

  @Test
  public void test05()  throws Throwable  {
      ByteDataInputStream byteDataInputStream0 = new ByteDataInputStream("1");
      try {
        byteDataInputStream0.reset();
        fail("Expecting exception: IOException");

      } catch(IOException e) {
         //
         // mark/reset not supported
         //
         assertThrownBy("org.evosuite.runtime.util.ByteDataInputStream", e);
      }
  }

  @Test
  public void test06()  throws Throwable  {
      ByteDataInputStream byteDataInputStream0 = new ByteDataInputStream("1");
      boolean boolean0 = byteDataInputStream0.markSupported();
      assertFalse(boolean0);
  }

  @Test
  public void test07()  throws Throwable  {
      byte[] byteArray0 = new byte[6];
      ByteDataInputStream byteDataInputStream0 = new ByteDataInputStream(byteArray0);
      byteDataInputStream0.mark((byte)0);
      assertFalse(byteDataInputStream0.markSupported());
  }



  @Test
  public void test09()  throws Throwable  {
      ByteDataInputStream byteDataInputStream0 = null;
      try {
        byteDataInputStream0 = new ByteDataInputStream((byte[]) null);
        fail("Expecting exception: IllegalArgumentException");

      } catch(IllegalArgumentException e) {
         //
         // Null input
         //
         assertThrownBy("org.evosuite.runtime.util.ByteDataInputStream", e);
      }
  }

  @Test
  public void test10()  throws Throwable  {
      ByteDataInputStream byteDataInputStream0 = new ByteDataInputStream("}qOs");
      int int0 = byteDataInputStream0.read();
      assertEquals(125, int0);
  }
}