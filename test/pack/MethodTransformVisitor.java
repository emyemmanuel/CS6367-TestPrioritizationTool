package test.pack;

import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

class MethodTransformVisitor extends MethodVisitor implements Opcodes {
String cName;
	
    public MethodTransformVisitor(final MethodVisitor mv, String className) {
            super(ASM5, mv);
            this.cName=className;
    }
		
	@Override
	public void visitLineNumber(int line, Label start) {
		    mv.visitLdcInsn(line);
			mv.visitLdcInsn(cName);
			mv.visitMethodInsn(INVOKESTATIC, "test/pack/StatementCoverageLayout", "lineExecuted", "(ILjava/lang/String;)V", false);
			super.visitLineNumber(line, start);
	}
}


//	int lineNumber;
//	String mName;
//	    public MethodTransformVisitor(final MethodVisitor mv, String name) {
//        super(ASM5, mv);
//        this.mName=name;
//    }
//    
//    @Override
//    public void visitLabel(Label label) {
// 	   if(lineNumber>0){
// 		   mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
// 		   mv.visitLdcInsn("line "+lineNumber+" executed");
// 		   mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
//
// 	   }
// 	   super.visitLabel(label);
//    }
// 
//    @Override
//    public void visitCode(){
//    	mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
//    	mv.visitLdcInsn(mName+" executed");
//    	mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
//    	super.visitCode();
//    } 
//    
//    @Override
//    public void visitLineNumber(int line, Label start) {
//    	if(lineNumber!=line){
//  		    mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
// 		    mv.visitLdcInsn(mName+":line "+line+" executed");
//    		mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
//    		lineNumber=line;
//       }
//    }  	
//}  


//    @Override
//    public void visitCode(){
//    	mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
//    	mv.visitLdcInsn(mName+" executed");
//    	mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
//    	super.visitCode();
//    }
    
    
//	@Override
//	public void visitLineNumber(int line, Label start) {
//	    mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
//    	mv.visitLdcInsn("line "+line+" executed");
//    	mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
//    	super.visitLineNumber(line, start);
//	}
//	
//	@Override
//    public void visitLabel(Label label){
//    	
//	}
	
//} 

