/*
 * This file was automatically generated by EvoSuite
 * Sat Jul 29 07:48:45 GMT 2023
 */

package nl.knaw.dans.easy.sword2examples;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import nl.knaw.dans.easy.sword2examples.Common;
import org.apache.abdera.model.Entry;
import org.apache.http.HttpEntity;
import org.apache.http.entity.BasicHttpEntity;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.System;
import org.evosuite.runtime.mock.java.io.MockFile;
import org.evosuite.runtime.mock.java.net.MockURI;
import org.evosuite.runtime.testdata.EvoSuiteFile;
import org.evosuite.runtime.testdata.FileSystemHandling;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class) @EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = true, useJEE = true) 
public class Common_ESTest extends Common_ESTest_scaffolding {

  @Test(timeout = 4000)
  public void test00()  throws Throwable  {
      byte[] byteArray0 = new byte[3];
      ByteArrayEntity byteArrayEntity0 = new ByteArrayEntity(byteArray0);
      String string0 = Common.readEntityAsString(byteArrayEntity0);
      assertEquals("\u0000\u0000\u0000", string0);
  }

  @Test(timeout = 4000)
  public void test01()  throws Throwable  {
      byte[] byteArray0 = new byte[0];
      ByteArrayEntity byteArrayEntity0 = new ByteArrayEntity(byteArray0);
      String string0 = Common.readEntityAsString(byteArrayEntity0);
      assertEquals("", string0);
  }

  @Test(timeout = 4000)
  public void test02()  throws Throwable  {
      try { 
        Common.zipDirectory((File) null, (File) null);
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
         verifyException("nl.knaw.dans.easy.sword2examples.Common", e);
      }
  }

  @Test(timeout = 4000)
  public void test03()  throws Throwable  {
      MockFile mockFile0 = new MockFile("");
      URI uRI0 = MockURI.create("");
      try { 
        Common.setBagIsVersionOf(mockFile0, uRI0);
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
         verifyException("nl.knaw.dans.easy.sword2examples.Common", e);
      }
  }

  @Test(timeout = 4000)
  public void test04()  throws Throwable  {
      // Undeclared exception!
      try { 
        Common.readEntityAsString((HttpEntity) null);
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
         verifyException("nl.knaw.dans.easy.sword2examples.Common", e);
      }
  }

  @Test(timeout = 4000)
  public void test05()  throws Throwable  {
      // Undeclared exception!
      try { 
        Common.getDois((Entry) null);
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
         verifyException("nl.knaw.dans.easy.sword2examples.Common", e);
      }
  }

  @Test(timeout = 4000)
  public void test06()  throws Throwable  {
      URI uRI0 = MockURI.URI((String) null, (String) null, (String) null);
      // Undeclared exception!
      Common.createHttpClient(uRI0, "", ",_d>H7JK]`lr");
  }

  @Test(timeout = 4000)
  public void test07()  throws Throwable  {
      // Undeclared exception!
      try { 
        Common.createHttpClient((URI) null, "dtw,", "dtw,");
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
         verifyException("org.evosuite.runtime.mock.java.net.MockURI", e);
      }
  }

  @Test(timeout = 4000)
  public void test08()  throws Throwable  {
      MockFile mockFile0 = new MockFile("", "");
      Common.copyToBagDirectoryInTarget(mockFile0);
  }

  @Test(timeout = 4000)
  public void test09()  throws Throwable  {
      try { 
        Common.copyToBagDirectoryInTarget((File) null);
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
         verifyException("nl.knaw.dans.easy.sword2examples.Common", e);
      }
  }

  @Test(timeout = 4000)
  public void test10()  throws Throwable  {
      MockFile mockFile0 = new MockFile("");
      mockFile0.setReadable(false);
      try { 
        Common.copyToBagDirectoryInTarget(mockFile0);
        fail("Expecting exception: IOException");
      
      } catch(IOException e) {
         //
         // Destination '/data1/ziruichen/TestMigration/resource/Zip-263/easy-sword2-dans-examples-master/target' directory cannot be created
         //
         verifyException("org.apache.commons.io.FileUtils", e);
      }
  }

  @Test(timeout = 4000)
  public void test11()  throws Throwable  {
      EvoSuiteFile evoSuiteFile0 = new EvoSuiteFile("/data1/ziruichen/TestMigration/resource/Zip-263/easy-sword2-dans-examples-master/ J");
      FileSystemHandling.createFolder(evoSuiteFile0);
      MockFile mockFile0 = new MockFile(" J", "");
      File file0 = Common.copyToBagDirectoryInTarget(mockFile0);
      try { 
        Common.copyToBagDirectoryInTarget(file0);
        fail("Expecting exception: FileNotFoundException");
      
      } catch(FileNotFoundException e) {
         //
         // Source '/data1/ziruichen/TestMigration/resource/Zip-263/easy-sword2-dans-examples-master/target/ J' does not exist
         //
         verifyException("org.apache.commons.io.FileUtils", e);
      }
  }

  @Test(timeout = 4000)
  public void test12()  throws Throwable  {
      MockFile mockFile0 = new MockFile("");
      File file0 = Common.copyToBagDirectoryInTarget(mockFile0);
      try { 
        Common.zipDirectory(file0, mockFile0);
        fail("Expecting exception: IOException");
      
      } catch(IOException e) {
         //
         // org.evosuite.runtime.mock.java.lang.MockThrowable
         //
         verifyException("net.lingala.zip4j.ZipFile", e);
      }
  }

  @Test(timeout = 4000)
  public void test13()  throws Throwable  {
      MockFile mockFile0 = new MockFile("$=0>elP'jCFB");
      // Undeclared exception!
      try { 
        Common.copyToBagDirectoryInTarget(mockFile0);
        fail("Expecting exception: System.SystemExitException");
      
      } catch(System.SystemExitException e) {
         //
         // no message in exception (getMessage() returned null)
         //
         verifyException("org.evosuite.runtime.System", e);
      }
  }

  @Test(timeout = 4000)
  public void test14()  throws Throwable  {
      MockFile mockFile0 = new MockFile("(k-3jj{+//{%i=\"uey", "(k-3jj{+//{%i=\"uey");
      try { 
        Common.zipDirectory(mockFile0, mockFile0);
        fail("Expecting exception: IOException");
      
      } catch(IOException e) {
         //
         // folder does not exist
         //
         verifyException("net.lingala.zip4j.ZipFile", e);
      }
  }

  @Test(timeout = 4000)
  public void test15()  throws Throwable  {
      EvoSuiteFile evoSuiteFile0 = new EvoSuiteFile("/data1/ziruichen/TestMigration/resource/Zip-263/easy-sword2-dans-examples-master/x-auth-value.txt");
      FileSystemHandling.appendStringToFile(evoSuiteFile0, "-;WK.D:D=QpZ7^L/");
      try { 
        Common.trackDeposit((CloseableHttpClient) null, (URI) null);
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
         verifyException("nl.knaw.dans.easy.sword2examples.Common", e);
      }
  }

  @Test(timeout = 4000)
  public void test16()  throws Throwable  {
      try { 
        Common.trackDeposit((CloseableHttpClient) null, (URI) null);
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
         verifyException("nl.knaw.dans.easy.sword2examples.Common", e);
      }
  }

  @Test(timeout = 4000)
  public void test17()  throws Throwable  {
      BasicHttpEntity basicHttpEntity0 = new BasicHttpEntity();
      // Undeclared exception!
      try { 
        Common.readEntityAsString(basicHttpEntity0);
        fail("Expecting exception: IllegalStateException");
      
      } catch(IllegalStateException e) {
         //
         // Content has not been provided
         //
         verifyException("org.apache.http.util.Asserts", e);
      }
  }

  @Test(timeout = 4000)
  public void test18()  throws Throwable  {
      URI uRI0 = MockURI.URI("com.ctc.wstx.dtd.DefaultAttrValue", "r", "com.ctc.wstx.dtd.DefaultAttrValue", 1640, (String) null, "Cannot add another element to this Document as it already has a root element of: ", "Cannot add another element to this Document as it already has a root element of: ");
      // Undeclared exception!
      try { 
        Common.createHttpClient(uRI0, (String) null, "Cannot add another element to this Document as it already has a root element of: ");
        fail("Expecting exception: IllegalArgumentException");
      
      } catch(IllegalArgumentException e) {
         //
         // Username may not be null
         //
         verifyException("org.apache.http.util.Args", e);
      }
  }

  @Test(timeout = 4000)
  public void test19()  throws Throwable  {
      MockFile mockFile0 = new MockFile("FAILED", "FAILED");
      try { 
        Common.setBagIsVersionOf(mockFile0, (URI) null);
        fail("Expecting exception: RuntimeException");
      
      } catch(RuntimeException e) {
         //
         // /data1/ziruichen/TestMigration/resource/Zip-263/easy-sword2-dans-examples-master/FAILED/FAILED does not exist
         //
         verifyException("gov.loc.repository.bagit.filesystem.FileSystemFactory", e);
      }
  }

  @Test(timeout = 4000)
  public void test20()  throws Throwable  {
      Common common0 = new Common();
  }
}