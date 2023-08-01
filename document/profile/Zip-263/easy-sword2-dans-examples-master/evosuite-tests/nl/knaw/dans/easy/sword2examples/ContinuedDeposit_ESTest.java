/*
 * This file was automatically generated by EvoSuite
 * Sat Jul 29 07:46:51 GMT 2023
 */

package nl.knaw.dans.easy.sword2examples;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.io.IOException;
import nl.knaw.dans.easy.sword2examples.ContinuedDeposit;
import org.apache.abdera.i18n.iri.IRI;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.System;
import org.evosuite.runtime.mock.java.io.MockFile;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class) @EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = true, useJEE = true) 
public class ContinuedDeposit_ESTest extends ContinuedDeposit_ESTest_scaffolding {

  @Test(timeout = 4000)
  public void test0()  throws Throwable  {
      String[] stringArray0 = new String[1];
      // Undeclared exception!
      try { 
        ContinuedDeposit.main(stringArray0);
        fail("Expecting exception: System.SystemExitException");
      
      } catch(System.SystemExitException e) {
         //
         // no message in exception (getMessage() returned null)
         //
         verifyException("org.evosuite.runtime.System", e);
      }
  }

  @Test(timeout = 4000)
  public void test1()  throws Throwable  {
      String[] stringArray0 = new String[5];
      stringArray0[0] = "z'ZJY";
      try { 
        ContinuedDeposit.main(stringArray0);
        fail("Expecting exception: NumberFormatException");
      
      } catch(NumberFormatException e) {
         //
         // null
         //
         verifyException("java.lang.Integer", e);
      }
  }

  @Test(timeout = 4000)
  public void test2()  throws Throwable  {
      try { 
        ContinuedDeposit.main((String[]) null);
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
         verifyException("nl.knaw.dans.easy.sword2examples.ContinuedDeposit", e);
      }
  }

  @Test(timeout = 4000)
  public void test3()  throws Throwable  {
      MockFile mockFile0 = new MockFile("", "Unable to set the last modification time for ");
      try { 
        ContinuedDeposit.depositPackage(mockFile0, (IRI) null, "Unable to set the last modification time for ", "", (-497));
        fail("Expecting exception: IOException");
      
      } catch(IOException e) {
         //
         // folder does not exist
         //
         verifyException("net.lingala.zip4j.ZipFile", e);
      }
  }

  @Test(timeout = 4000)
  public void test4()  throws Throwable  {
      MockFile mockFile0 = new MockFile("");
      try { 
        ContinuedDeposit.depositPackage(mockFile0, (IRI) null, "", "K)HDux9wZa", 2838);
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
         verifyException("nl.knaw.dans.easy.sword2examples.ContinuedDeposit", e);
      }
  }

  @Test(timeout = 4000)
  public void test5()  throws Throwable  {
      String[] stringArray0 = new String[6];
      // Undeclared exception!
      try { 
        ContinuedDeposit.main(stringArray0);
        fail("Expecting exception: System.SystemExitException");
      
      } catch(System.SystemExitException e) {
         //
         // no message in exception (getMessage() returned null)
         //
         verifyException("org.evosuite.runtime.System", e);
      }
  }

  @Test(timeout = 4000)
  public void test6()  throws Throwable  {
      String[] stringArray0 = new String[5];
      try { 
        ContinuedDeposit.main(stringArray0);
        fail("Expecting exception: RuntimeException");
      
      } catch(RuntimeException e) {
         //
         // java.lang.NullPointerException
         //
         verifyException("org.apache.abdera.i18n.iri.IRI", e);
      }
  }

  @Test(timeout = 4000)
  public void test7()  throws Throwable  {
      ContinuedDeposit continuedDeposit0 = new ContinuedDeposit();
  }
}
