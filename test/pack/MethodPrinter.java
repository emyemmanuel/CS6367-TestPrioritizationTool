package test.pack;

import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class MethodPrinter extends MethodVisitor implements Opcodes {
    String mName;
	String cName;
	int line;
	
    public MethodPrinter(final MethodVisitor mv, String cName, String mName) {
            super(ASM5, mv);
            this.cName=cName;
            this.mName=mName;

    }
		
	@Override
	public void visitLineNumber(int line, Label start) {
		this.line = line;
		//System.out.println(mName);
		mv.visitLdcInsn(mName + ":" + line);
		mv.visitMethodInsn(INVOKESTATIC, "test/pack/StatementCoverageData", "lineExecuted", "(Ljava/lang/String;)V", false);
		super.visitLineNumber(line, start);

	}

//	@Override
//	public void visitLabel(Label label) {
//		
//		mv.visitLdcInsn(cName + ":" + line);
//		mv.visitMethodInsn(INVOKESTATIC, "test/pack/StatementCoverageData", "lineExecuted", "(Ljava/lang/String;)V", false);
//		super.visitLabel(label);
//	}
}