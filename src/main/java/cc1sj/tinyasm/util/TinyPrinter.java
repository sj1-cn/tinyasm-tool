package cc1sj.tinyasm.util;

import org.objectweb.asm.Attribute;
import org.objectweb.asm.Handle;
import org.objectweb.asm.Label;
import org.objectweb.asm.util.Printer;

public class TinyPrinter extends Printer {

	public TinyPrinter(int api) {
		super(api);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visitSource(String source, String debug) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visitOuterClass(String owner, String name, String descriptor) {
		// TODO Auto-generated method stub

	}

	@Override
	public Printer visitClassAnnotation(String descriptor, boolean visible) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void visitClassAttribute(Attribute attribute) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visitInnerClass(String name, String outerName, String innerName, int access) {
		// TODO Auto-generated method stub

	}

	@Override
	public Printer visitField(int access, String name, String descriptor, String signature, Object value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Printer visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void visitClassEnd() {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(String name, Object value) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visitEnum(String name, String descriptor, String value) {
		// TODO Auto-generated method stub

	}

	@Override
	public Printer visitAnnotation(String name, String descriptor) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Printer visitArray(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void visitAnnotationEnd() {
		// TODO Auto-generated method stub

	}

	@Override
	public Printer visitFieldAnnotation(String descriptor, boolean visible) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void visitFieldAttribute(Attribute attribute) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visitFieldEnd() {
		// TODO Auto-generated method stub

	}

	@Override
	public Printer visitAnnotationDefault() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Printer visitMethodAnnotation(String descriptor, boolean visible) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Printer visitParameterAnnotation(int parameter, String descriptor, boolean visible) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void visitMethodAttribute(Attribute attribute) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visitCode() {
		// TODO Auto-generated method stub

	}

	@Override
	public void visitFrame(int type, int nLocal, Object[] local, int nStack, Object[] stack) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visitInsn(int opcode) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visitIntInsn(int opcode, int operand) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visitVarInsn(int opcode, int var) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visitTypeInsn(int opcode, String type) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visitFieldInsn(int opcode, String owner, String name, String descriptor) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visitInvokeDynamicInsn(String name, String descriptor, Handle bootstrapMethodHandle, Object... bootstrapMethodArguments) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visitJumpInsn(int opcode, Label label) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visitLabel(Label label) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visitLdcInsn(Object value) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visitIincInsn(int var, int increment) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visitTableSwitchInsn(int min, int max, Label dflt, Label... labels) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visitLookupSwitchInsn(Label dflt, int[] keys, Label[] labels) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visitMultiANewArrayInsn(String descriptor, int numDimensions) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visitTryCatchBlock(Label start, Label end, Label handler, String type) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visitLocalVariable(String name, String descriptor, String signature, Label start, Label end, int index) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visitLineNumber(int line, Label start) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visitMaxs(int maxStack, int maxLocals) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visitMethodEnd() {
		// TODO Auto-generated method stub

	}

}
