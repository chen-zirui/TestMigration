/*
 * This file was automatically generated by EvoSuite
 * Sat Jul 29 07:05:10 GMT 2023
 */

package com.esri.geoportal.base.util;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import com.esri.geoportal.base.util.Val;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class) @EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = true, useJEE = true) 
public class Val_ESTest extends Val_ESTest_scaffolding {

  @Test(timeout = 4000)
  public void test00()  throws Throwable  {
      String[] stringArray0 = Val.tokenize("\"!^vhm|EU~(A", "/LD;-KE[4V~_", false);
      assertNotNull(stringArray0);
  }

  @Test(timeout = 4000)
  public void test01()  throws Throwable  {
      Long long0 = new Long(731L);
      Long long1 = Val.chkLong("0", long0);
      assertEquals(0L, (long)long1);
  }

  @Test(timeout = 4000)
  public void test02()  throws Throwable  {
      String string0 = Val.urlEncode("/YPqm");
      assertEquals("%2FYPqm", string0);
  }

  @Test(timeout = 4000)
  public void test03()  throws Throwable  {
      String string0 = Val.unescapeUnicode("");
      assertEquals("", string0);
  }

  @Test(timeout = 4000)
  public void test04()  throws Throwable  {
      String string0 = Val.unescapeOctalToUnicode("");
      assertEquals("", string0);
  }

  @Test(timeout = 4000)
  public void test05()  throws Throwable  {
      String string0 = Val.unescapeOctal("false");
      assertEquals("false", string0);
  }

  @Test(timeout = 4000)
  public void test06()  throws Throwable  {
      String string0 = Val.unescapeNunmericEntity("");
      assertEquals("", string0);
  }

  @Test(timeout = 4000)
  public void test07()  throws Throwable  {
      String string0 = Val.unescapeHtml4("m?HQ--?W7d67/~'");
      assertEquals("m?HQ--?W7d67/~'", string0);
  }

  @Test(timeout = 4000)
  public void test08()  throws Throwable  {
      String string0 = Val.unescape("");
      assertEquals("", string0);
  }

  @Test(timeout = 4000)
  public void test09()  throws Throwable  {
      String string0 = Val.trim("");
      assertEquals("", string0);
  }

  @Test(timeout = 4000)
  public void test10()  throws Throwable  {
      String string0 = Val.chkStr("", "");
      assertEquals("", string0);
  }

  @Test(timeout = 4000)
  public void test11()  throws Throwable  {
      Long long0 = new Long(0L);
      Long long1 = Val.chkLong("", long0);
      assertEquals(0L, (long)long1);
  }

  @Test(timeout = 4000)
  public void test12()  throws Throwable  {
      Long long0 = new Long(300L);
      Long long1 = Val.chkLong("r%'mnsjs|`9p", long0);
      assertEquals(300L, (long)long1);
  }

  @Test(timeout = 4000)
  public void test13()  throws Throwable  {
      Integer integer0 = new Integer((-2651));
      Integer integer1 = Val.chkInt("", integer0);
      assertEquals((-2651), (int)integer1);
  }

  @Test(timeout = 4000)
  public void test14()  throws Throwable  {
      Integer integer0 = new Integer(1286);
      Integer integer1 = Val.chkInt("lP=MeV43Y6]&uIt&w", integer0);
      assertEquals(1286, (int)integer1);
  }

  @Test(timeout = 4000)
  public void test15()  throws Throwable  {
      Double double0 = Val.chkDbl("", (Double) null);
      assertNull(double0);
  }

  @Test(timeout = 4000)
  public void test16()  throws Throwable  {
      Double double0 = new Double(0.0);
      Double double1 = Val.chkDbl("{NDdq,YegN*6MyD|Ov", double0);
      assertEquals(0.0, (double)double1, 0.01);
  }

  @Test(timeout = 4000)
  public void test17()  throws Throwable  {
      Val.UNICODE = null;
      // Undeclared exception!
      try { 
        Val.unescapeUnicode("Zt!I1");
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
         verifyException("com.esri.geoportal.base.util.Val", e);
      }
  }

  @Test(timeout = 4000)
  public void test18()  throws Throwable  {
      Val.OCTAL = null;
      // Undeclared exception!
      try { 
        Val.unescapeOctal("y)Zl4Fd");
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
         verifyException("com.esri.geoportal.base.util.Val", e);
      }
  }

  @Test(timeout = 4000)
  public void test19()  throws Throwable  {
      Val.NUMERIC = null;
      // Undeclared exception!
      try { 
        Val.unescapeNunmericEntity("false");
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
         verifyException("com.esri.geoportal.base.util.Val", e);
      }
  }

  @Test(timeout = 4000)
  public void test20()  throws Throwable  {
      String string0 = Val.unescapeHtml4("");
      assertEquals("", string0);
  }

  @Test(timeout = 4000)
  public void test21()  throws Throwable  {
      String string0 = Val.trim((String) null);
      assertNull(string0);
  }

  @Test(timeout = 4000)
  public void test22()  throws Throwable  {
      String string0 = Val.trim("=tEErA}q2-G");
      assertEquals("=tEErA}q2-G", string0);
  }

  @Test(timeout = 4000)
  public void test23()  throws Throwable  {
      String string0 = Val.chkStr("b%M2z8nmu3(", "al+)?;(Vr$?8T3n0");
      assertEquals("b%M2z8nmu3(", string0);
  }

  @Test(timeout = 4000)
  public void test24()  throws Throwable  {
      String string0 = Val.chkStr((String) null, (String) null);
      assertNull(string0);
  }

  @Test(timeout = 4000)
  public void test25()  throws Throwable  {
      String string0 = Val.unescape("&pound;");
      assertEquals("\u00A3", string0);
  }

  @Test(timeout = 4000)
  public void test26()  throws Throwable  {
      String string0 = Val.unescape((String) null);
      assertNull(string0);
  }

  @Test(timeout = 4000)
  public void test27()  throws Throwable  {
      String string0 = Val.unescapeOctalToUnicode("false");
      assertEquals("false", string0);
  }

  @Test(timeout = 4000)
  public void test28()  throws Throwable  {
      String string0 = Val.unescapeOctalToUnicode((String) null);
      assertNull(string0);
  }

  @Test(timeout = 4000)
  public void test29()  throws Throwable  {
      String string0 = Val.unescapeUnicode("Zt!I1");
      assertEquals("Zt!I1", string0);
  }

  @Test(timeout = 4000)
  public void test30()  throws Throwable  {
      String string0 = Val.unescapeUnicode((String) null);
      assertNull(string0);
  }

  @Test(timeout = 4000)
  public void test31()  throws Throwable  {
      String string0 = Val.unescapeOctal("");
      assertEquals("", string0);
  }

  @Test(timeout = 4000)
  public void test32()  throws Throwable  {
      String string0 = Val.unescapeOctal((String) null);
      assertNull(string0);
  }

  @Test(timeout = 4000)
  public void test33()  throws Throwable  {
      String string0 = Val.unescapeNunmericEntity("false");
      assertEquals("false", string0);
  }

  @Test(timeout = 4000)
  public void test34()  throws Throwable  {
      String string0 = Val.unescapeNunmericEntity((String) null);
      assertNull(string0);
  }

  @Test(timeout = 4000)
  public void test35()  throws Throwable  {
      String string0 = Val.unescapeHtml4((String) null);
      assertNull(string0);
  }

  @Test(timeout = 4000)
  public void test36()  throws Throwable  {
      String string0 = Val.urlEncode("");
      assertEquals("", string0);
  }

  @Test(timeout = 4000)
  public void test37()  throws Throwable  {
      String string0 = Val.urlEncode((String) null);
      assertNull(string0);
  }

  @Test(timeout = 4000)
  public void test38()  throws Throwable  {
      String[] stringArray0 = Val.tokenize("ct", (String) null, true);
      assertNotNull(stringArray0);
  }

  @Test(timeout = 4000)
  public void test39()  throws Throwable  {
      String[] stringArray0 = Val.tokenize((String) null, "\\[0-8]{2,3}", true);
      assertNotNull(stringArray0);
  }

  @Test(timeout = 4000)
  public void test40()  throws Throwable  {
      byte[] byteArray0 = new byte[2];
      String string0 = Val.base64Encode(byteArray0, "");
      assertEquals("AAA=", string0);
  }

  @Test(timeout = 4000)
  public void test41()  throws Throwable  {
      byte[] byteArray0 = new byte[0];
      // Undeclared exception!
      try { 
        Val.base64Encode(byteArray0, "/'");
        fail("Expecting exception: RuntimeException");
      
      } catch(RuntimeException e) {
         //
         // java.io.UnsupportedEncodingException: /'
         //
         verifyException("com.esri.geoportal.base.util.Val", e);
      }
  }

  @Test(timeout = 4000)
  public void test42()  throws Throwable  {
      byte[] byteArray0 = new byte[0];
      String string0 = Val.base64Encode(byteArray0, (String) null);
      assertEquals("", string0);
  }

  @Test(timeout = 4000)
  public void test43()  throws Throwable  {
      String string0 = Val.base64Encode((byte[]) null, "&Tau;");
      assertNull(string0);
  }

  @Test(timeout = 4000)
  public void test44()  throws Throwable  {
      String string0 = Val.chkStr("", (String) null);
      assertNull(string0);
  }

  @Test(timeout = 4000)
  public void test45()  throws Throwable  {
      Long long0 = Val.chkLong("o,+lYh", (Long) null);
      assertNull(long0);
  }

  @Test(timeout = 4000)
  public void test46()  throws Throwable  {
      Long long0 = new Long((-1L));
      Long long1 = Val.chkLong("", long0);
      assertEquals((-1L), (long)long1);
  }

  @Test(timeout = 4000)
  public void test47()  throws Throwable  {
      Long long0 = new Long((-4901L));
      Long long1 = Val.chkLong((String) null, long0);
      assertEquals((-4901L), (long)long1);
  }

  @Test(timeout = 4000)
  public void test48()  throws Throwable  {
      Integer integer0 = new Integer(0);
      Integer integer1 = Val.chkInt("0", integer0);
      assertEquals(0, (int)integer1);
  }

  @Test(timeout = 4000)
  public void test49()  throws Throwable  {
      Integer integer0 = Val.chkInt((String) null, (Integer) null);
      assertNull(integer0);
  }

  @Test(timeout = 4000)
  public void test50()  throws Throwable  {
      Double double0 = new Double(2422.1692);
      Double double1 = Val.chkDbl(" ", double0);
      assertEquals(2422.1692, (double)double1, 0.01);
  }

  @Test(timeout = 4000)
  public void test51()  throws Throwable  {
      Double double0 = new Double((-1390.5415801));
      Double double1 = Val.chkDbl("b%M2z8nmu3(", double0);
      assertEquals((-1390.5415801), (double)double1, 0.01);
  }

  @Test(timeout = 4000)
  public void test52()  throws Throwable  {
      Double double0 = new Double(118.842394167565);
      Double double1 = Val.chkDbl((String) null, double0);
      assertEquals(118.842394167565, (double)double1, 0.01);
  }

  @Test(timeout = 4000)
  public void test53()  throws Throwable  {
      boolean boolean0 = Val.chkBool("&lceil;", true);
      assertTrue(boolean0);
  }

  @Test(timeout = 4000)
  public void test54()  throws Throwable  {
      boolean boolean0 = Val.chkBool("0", true);
      assertFalse(boolean0);
  }

  @Test(timeout = 4000)
  public void test55()  throws Throwable  {
      boolean boolean0 = Val.chkBool("off", false);
      assertFalse(boolean0);
  }

  @Test(timeout = 4000)
  public void test56()  throws Throwable  {
      boolean boolean0 = Val.chkBool("no", true);
      assertFalse(boolean0);
  }

  @Test(timeout = 4000)
  public void test57()  throws Throwable  {
      boolean boolean0 = Val.chkBool("y", true);
      assertTrue(boolean0);
  }

  @Test(timeout = 4000)
  public void test58()  throws Throwable  {
      boolean boolean0 = Val.chkBool("false", true);
      assertFalse(boolean0);
  }

  @Test(timeout = 4000)
  public void test59()  throws Throwable  {
      boolean boolean0 = Val.chkBool("1", true);
      assertTrue(boolean0);
  }

  @Test(timeout = 4000)
  public void test60()  throws Throwable  {
      boolean boolean0 = Val.chkBool((String) null, false);
      assertFalse(boolean0);
  }

  @Test(timeout = 4000)
  public void test61()  throws Throwable  {
      Val val0 = new Val();
  }
}
