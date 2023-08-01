/**
 * Copyright (C) 2010-2016 Gordon Fraser, Andrea Arcuri and EvoSuite
 * contributors
 *
 * This file is part of EvoSuite.
 *
 * EvoSuite is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published
 * by the Free Software Foundation, either version 3.0 of the License, or
 * (at your option) any later version.
 *
 * EvoSuite is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with EvoSuite. If not, see <http://www.gnu.org/licenses/>.
 */
package org.evosuite.runtime.instrumentation;

import org.evosuite.runtime.RuntimeSettings;
import org.evosuite.runtime.util.ComputeClassWriter;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.commons.AnalyzerAdapter;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.LdcInsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.lang.reflect.Constructor;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.NotFoundException;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

/**
 * This class is responsible for the bytecode instrumentation
 * needed for the generated JUnit test cases.
 *
 * <p>
 * Note: the instrumentation will be part of the final JUnit tests and, as such, we should
 * only keep the instrumentation that affect the functional behavior (so, no branch distances, etc).
 *
 * Created by arcuri on 6/11/14.
 */

public class RuntimeInstrumentation {

	private static Logger logger = LoggerFactory.getLogger(RuntimeInstrumentation.class);

	/**
	 * If we are re-instrumenting a class, then we cannot change its
	 * signature: eg add new methods
	 *
	 * TODO: remove once we fix instrumentation
	 */
	private volatile boolean retransformingMode;

	/**
	 * This should ONLY be set by SystemTest
	 */
	private static boolean avoidInstrumentingShadedClasses = false;

	public RuntimeInstrumentation(){
		retransformingMode = false;
	}

	public void setRetransformingMode(boolean on){
		retransformingMode = on;
	}

	/**
	 * WARN: This should ONLY be called by SystemTest
	 */
	public static void setAvoidInstrumentingShadedClasses(boolean avoidInstrumentingShadedClasses) {
		RuntimeInstrumentation.avoidInstrumentingShadedClasses = avoidInstrumentingShadedClasses;
	}

	public static boolean getAvoidInstrumentingShadedClasses() {
		return RuntimeInstrumentation.avoidInstrumentingShadedClasses;
	}

	public static boolean checkIfCanInstrument(String className) {
		for (String s : ExcludedClasses.getPackagesShouldNotBeInstrumented()) {
			if (className.startsWith(s)) {
				return false;
			}
		}

		if(className.contains("EnhancerByMockito")){
			//very special case, as Mockito will create classes on the fly
			return false;
		}

		return true;
	}

	public boolean isAlreadyInstrumented(ClassReader reader) {
		ClassNode classNode = new ClassNode();

		int readFlags = ClassReader.SKIP_FRAMES | ClassReader.SKIP_CODE;
		reader.accept(classNode, readFlags);
		for(String interfaceName : ((List<String>)classNode.interfaces)) {
			if(InstrumentedClass.class.getName().equals(interfaceName.replace('/', '.')))
				return true;
		}
		return false;
	}

	public byte[] transformBytes(ClassLoader classLoader, String className,
			ClassReader reader, boolean skipInstrumentation) {

		String classNameWithDots = className.replace('/', '.');

		if (!checkIfCanInstrument(classNameWithDots)) {
			throw new IllegalArgumentException("Should not transform a shared class ("
					+ classNameWithDots + ")! Load by parent (JVM) classloader.");
		}

		int asmFlags = ClassWriter.COMPUTE_FRAMES;
		ClassWriter writer = new ComputeClassWriter(asmFlags);

		ClassVisitor cv = writer;

		if(!skipInstrumentation) {
			if (RuntimeSettings.resetStaticState && !retransformingMode) {
			/*
			 * FIXME: currently reset does add a new method, but that does no work
			 * when retransformingMode :(
			 */
				CreateClassResetClassAdapter resetClassAdapter = new CreateClassResetClassAdapter(cv, className, true);
				cv = resetClassAdapter;
			}

			if (RuntimeSettings.isUsingAnyMocking()) {
				cv = new MethodCallReplacementClassAdapter(cv, className, !retransformingMode);
			}

			cv = new KillSwitchClassAdapter(cv);

			cv = new RemoveFinalClassAdapter(cv);

			if (RuntimeSettings.maxNumberOfIterationsPerLoop >= 0) {
				cv = new LoopCounterClassAdapter(cv);
			}
		}
		ClassNode cn = new AnnotatedClassNode();

		int readFlags = ClassReader.SKIP_FRAMES;
		reader.accept(cn, readFlags);
		

		String fileDir = System.getenv("NOW_DIR")+"/info.txt";
		String poc = "";
		String methodName = "";
		String InsnName = "";
		int position = 0;
		int typecode = 0;
		String parameterType = "";
		
		
		try (BufferedReader fileReader = new BufferedReader(new FileReader(fileDir))) {
			poc = fileReader.readLine();
			position = Integer.valueOf(fileReader.readLine());
			typecode = Integer.valueOf(fileReader.readLine());
            methodName = fileReader.readLine();
            InsnName = fileReader.readLine();
			parameterType = fileReader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

		MethodNode targetMethod = null;
		List<MethodNode> methods = cn.methods;
		for (MethodNode method : methods) {
			targetMethod = method;
			AbstractInsnNode tokenUtilInsn = null;
			InsnList instructions = targetMethod.instructions;
			for (AbstractInsnNode insn = instructions.getFirst(); insn != null; insn = insn.getNext()) {
				if (insn.getOpcode() == Opcodes.INVOKESTATIC || insn.getOpcode() == Opcodes.INVOKEVIRTUAL || insn.getOpcode() == Opcodes.INVOKESPECIAL) {
					MethodInsnNode methodInsn = (MethodInsnNode) insn;
					if (methodInsn.owner.equals(methodName) && methodInsn.name.equals(InsnName)) {
						tokenUtilInsn = insn;
						break;
					}
				}
			}
			if (tokenUtilInsn != null) {
				InsnList beforeInsnList = new InsnList();
				AbstractInsnNode prevInsn = null;
				prevInsn = tokenUtilInsn.getPrevious();

				System.out.println("need"+String.valueOf(prevInsn.getOpcode())); 
				logger.info("need"+String.valueOf(prevInsn.getOpcode()));
				
				//定位
				for(int i = 0;i<position-1;i++){
					prevInsn = prevInsn.getPrevious();
					System.out.println("need"+String.valueOf(prevInsn.getOpcode())); 
					logger.info("need"+String.valueOf(prevInsn.getOpcode()));
				}
				

				if (prevInsn != null && (  prevInsn.getOpcode() == typecode) ) {   

					
					System.out.println("parameter type is"+parameterType);
					logger.info("parameter type is"+parameterType);

					if(parameterType.contains("File")){                         
						System.out.println("Files!!!");
						logger.info("Files!!!");

						LdcInsnNode newLdcInsn = new LdcInsnNode(new File(poc));
						
						instructions.insert(prevInsn, newLdcInsn);
						instructions.remove(prevInsn);
					}
					if(parameterType.contains("java.lang.String[]")){           
						String[] stringArray0 = new String[6];
						stringArray0[0] = poc;
						
					}
					else{
						LdcInsnNode newLdcInsn = new LdcInsnNode(poc);
						
						instructions.insert(prevInsn, newLdcInsn);
						instructions.remove(prevInsn);
					}
				}
				
				instructions.insertBefore(tokenUtilInsn, beforeInsnList);
			}
		}
				
		

		cv = new JSRInlinerClassVisitor(cv);

		try {
			cn.accept(cv);
		} catch (Throwable ex) {
			logger.error("Error while instrumenting class "+className+": "+ex.getMessage(),ex);
		}

		return writer.toByteArray();
	}

}