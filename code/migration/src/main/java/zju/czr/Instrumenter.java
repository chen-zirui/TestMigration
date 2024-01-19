package zju.czr;
 
import java.lang.instrument.Instrumentation;
import java.util.Arrays;
import java.util.Date;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.NotFoundException;
import javassist.bytecode.BadBytecode;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;

       

public class Instrumenter implements ClassFileTransformer {
    
    static String pocValue = "";
    static String targetClass = "";
    static String targetMethod = "";
    static String targetCall = "";
    static String finalClass = "";
    static String finalMethod = "";
    static String finalCall = "";
    static int index = 0;


    public static void premain(String agentArgs, Instrumentation inst) throws NotFoundException, CannotCompileException, IOException, BadBytecode {
        String inputs[] = agentArgs.split(";");
        finalClass = inputs[4].split("=",2)[1];
        finalMethod = inputs[5].split("=",2)[1];
        finalCall = inputs[6].split("=",2)[1];
        index = Integer.parseInt(inputs[7].split("=")[1]);
        inst.addTransformer(new Instrumenter());
    }

    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> clazz,
            java.security.ProtectionDomain domain, byte[] bytes) throws IllegalClassFormatException {
        
            ClassPool pool = ClassPool.getDefault();
            try {
                CtClass ctClass = pool.makeClass(new ByteArrayInputStream(bytes));
                if(ctClass.getName().equals(finalClass)){
                    CtMethod ctMethod2 = ctClass.getDeclaredMethod(finalMethod);
                    ctMethod2.instrument(new ExprEditor() {
                        public void edit(MethodCall m) throws CannotCompileException {
                            if (m.getMethodName().equals(finalCall)) {
                                m.replace("{ System.out.println(\"Value of target argument is: \" + $1); $_ = $proceed($$); }"); //pass
                            }
                        }
                    });
                }
               
                bytes = ctClass.toBytecode();
                
            } catch(NotFoundException | CannotCompileException e){
                return bytes;
            } 
            catch (IOException e) {
                e.printStackTrace();
            }
                return bytes;
    }
   
}