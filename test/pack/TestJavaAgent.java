package test.pack;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.ClassVisitor;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;
import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;

import javax.xml.transform.Transformer;

import org.objectweb.asm.ClassReader;
public class TestJavaAgent {
	public static void premain(String agentArgs, Instrumentation inst) {

	    inst.addTransformer(new ClassFileTransformer() {
	      public byte[] transform(ClassLoader classLoader, String s,
	          Class<?> aClass, ProtectionDomain protectionDomain, byte[] bytes)
	              throws IllegalClassFormatException {
	        
	        // ASM Code
	    	 if(s.startsWith("org/joda/time"))
	    	{
	    		 
	        ClassReader reader = new ClassReader(bytes);
	        ClassWriter writer = new ClassWriter(reader, ClassWriter.COMPUTE_MAXS);
	        ClassTransformVisitor visitor = new ClassTransformVisitor(writer);
	        reader.accept(visitor, 0);
	        return writer.toByteArray();
	    	 }
            return bytes;
	      }
	    });
	    
	    System.out.println("Premain called");

	  }

	}
