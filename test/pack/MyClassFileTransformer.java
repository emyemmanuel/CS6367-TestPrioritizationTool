package test.pack;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;

public class MyClassFileTransformer implements ClassFileTransformer{

   /* @Override
    public byte[] transform(ClassLoader classLoader, String s, Class<?> aClass, ProtectionDomain protectionDomain, byte[] bytes) throws IllegalClassFormatException 
    {
			if(s.startsWith("org/joda/time")){
            //if(s.startsWith("org/apache/commons/dbutils"))				
			//{	
            ClassReader reader = new ClassReader(bytes);
            ClassWriter writer = new ClassWriter(reader, ClassWriter.COMPUTE_MAXS);
            ClassPrinter visitor = new ClassPrinter(writer);
            reader.accept(visitor, 0);
            return writer.toByteArray();
			}			
			return bytes;
    }*/
	
	public byte[] transform(ClassLoader loader, String className, Class classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
		byte[] byteCode = classfileBuffer;
		if (className.startsWith("org/joda/time")) {
			ClassReader cr = new ClassReader(classfileBuffer);
			ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
			ClassPrinter ca = new ClassPrinter(cw, className);
			cr.accept(ca, 0);
			byteCode = cw.toByteArray();
		}
		return byteCode;
	}

}

