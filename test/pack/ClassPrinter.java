package test.pack;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

class ClassPrinter extends ClassVisitor implements Opcodes{

    private String cName;
	
	public ClassPrinter(final ClassVisitor cv, String cName) {
        super(ASM5, cv);
		this.cName=cName;
    }


    @Override
    public MethodVisitor visitMethod(final int access, final String name, final String desc, final String signature, final String[] exceptions) {
		MethodVisitor mv = cv.visitMethod(access, name, desc, signature, exceptions);
		return mv == null ? null : new MethodPrinter(mv, cName, name);
    }
	
}
