package zju.czr;
 
import java.lang.instrument.Instrumentation;
import java.util.Arrays;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.NotFoundException;
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


    public static void premain(String agentArgs, Instrumentation inst) {
        String inputs[] = agentArgs.split(";");
        pocValue = inputs[0].split("=",2)[1];
        targetClass = inputs[1].split("=",2)[1];
        targetMethod = inputs[2].split("=",2)[1];
        targetCall = inputs[3].split("=",2)[1];
        finalClass = inputs[4].split("=",2)[1];
        finalMethod = inputs[5].split("=",2)[1];
        finalCall = inputs[6].split("=",2)[1];
        System.out.println("poc value is "+pocValue);
        index = Integer.parseInt(inputs[7].split("=")[1]);
        inst.addTransformer(new Instrumenter());
    }

    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> clazz,
            java.security.ProtectionDomain domain, byte[] bytes) throws IllegalClassFormatException {
        
            ClassPool pool = ClassPool.getDefault();
            try {
                CtClass ctClass = pool.makeClass(new ByteArrayInputStream(bytes));
                if(ctClass.getName().contains(targetClass)){   //逻辑错误，需要使用equals
                    CtMethod ctMethod = ctClass.getDeclaredMethod(targetMethod);
                    ctMethod.instrument(new ExprEditor() {
                        public void edit(MethodCall m) throws CannotCompileException {
                            if (m.getMethodName().equals(targetCall)) {      
                                String signature = m.getSignature();
                                String[] parts = signature.split("\\(");
                                String[] paramTypes = parts[1].replace(")", "").split(";");
                                int paramCount = paramTypes.length-1;
                                for (int i = 0; i < paramCount; i++) {
                                    paramTypes[i] = "$" + (i+1);
                                }
                                paramTypes = Arrays.copyOf(paramTypes, paramTypes.length - 1);
                                String code = String.format("{ $0.%s(%s); $_ = $proceed($$);}", targetCall, String.join(",", paramTypes));
                                String replaceCode = code.replace("$" + (index), "\"" + pocValue.replace("\n", "\\n") + "\"");
                                m.replace(replaceCode);
                            }
                        }
                    });
                }

                else if(ctClass.getName().contains(finalClass)){
                    CtMethod ctMethod2 = ctClass.getDeclaredMethod(finalMethod);
                    ctMethod2.instrument(new ExprEditor() {
                        public void edit(MethodCall m) throws CannotCompileException {
                            if (m.getMethodName().equals(finalCall)) {
                                m.replace("{ System.out.println(\"Value of the target argument is: \" + $1); $_ = $proceed($$); }"); //pass
                            }
                        }
                    });
                }
                
                bytes = ctClass.toBytecode();
            } catch (IOException | NotFoundException | CannotCompileException e) {
                e.printStackTrace();
            }
            return bytes;
    }
   
}