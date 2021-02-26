package cc1sj.tinyasm.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.objectweb.asm.Type;
import org.objectweb.asm.signature.SignatureVisitor;

final class ClassSignature extends SignatureVisitor {
	@Override
	public String toString() {
		return sb.toString();
	}

	int level = 0;
	boolean array = false;

	String indent() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < level; i++) {
			sb.append('\t');
		}
		return sb.toString();
	}

	List<StringBuilder> paramsClass = new ArrayList<>();
	StringBuilder returnClass;
	StringBuilder superClass;
	List<StringBuilder> interfacesClass = new ArrayList<>();
	List<StringBuilder> typeParameterClass = new ArrayList<>();;

	StringBuilder sb;
	Map<String, String> referedTypes;
	ClassSignature(int api, Map<String, String> referedTypes) {
		super(api);
		this.referedTypes = referedTypes;
	}

	@Override
	public void visitFormalTypeParameter(String name) {
		sb = new StringBuilder();
		typeParameterClass.add(sb);
		TinyASMifier.logger.debug("{}visitFormalTypeParameter({})", indent(), name);
		sb.append("\"");
		sb.append(name);
		sb.append("\"");
		sb.append(",");
		super.visitFormalTypeParameter(name);
	}

	@Override
	public SignatureVisitor visitClassBound() {
		TinyASMifier.logger.debug("{}visitClassBound()", indent());
//		level++;
		return super.visitClassBound();
	}

	@Override
	public SignatureVisitor visitInterfaceBound() {
		TinyASMifier.logger.debug("{}visitInterfaceBound()", indent());
		return super.visitInterfaceBound();
	}

	@Override
	public SignatureVisitor visitSuperclass() {
		sb = superClass = new StringBuilder();
		TinyASMifier.logger.debug("{}visitSuperclass()", indent());
//		level++;
		return super.visitSuperclass();
	}

	@Override
	public SignatureVisitor visitInterface() {
		sb = new StringBuilder();
		interfacesClass.add(sb);
//			sb.append(",");
		TinyASMifier.logger.debug("{}visitInterface()", indent());
//		level++;
		return super.visitInterface();
	}

	@Override
	public SignatureVisitor visitParameterType() {
		sb = new StringBuilder();
		paramsClass.add(sb);
		TinyASMifier.logger.debug("{}visitParameterType()", indent());
//		level++;
		return super.visitParameterType();
	}

	@Override
	public SignatureVisitor visitReturnType() {
		sb = returnClass = new StringBuilder();
		TinyASMifier.logger.debug("{}visitReturnType()", indent());
//		level++;
		return super.visitReturnType();
	}

	@Override
	public SignatureVisitor visitExceptionType() {
		TinyASMifier.logger.debug("{}visitExceptionType()", indent());
		return super.visitExceptionType();
	}

	@Override
	public void visitBaseType(char descriptor) {
		TinyASMifier.logger.debug("{}visitBaseType({})", indent(), descriptor);
		super.visitBaseType(descriptor);
	}

	@Override
	public void visitTypeVariable(String name) {
		sb.append("Clazz.typeVariableOf(\"");
		sb.append(name);
		sb.append("\")");
		TinyASMifier.logger.debug("{}visitTypeVariable({})", indent(), name);
		super.visitTypeVariable(name);
	}

	@Override
	public SignatureVisitor visitArrayType() {
		array = true;
		TinyASMifier.logger.debug("{}visitArrayType()", indent());
		return super.visitArrayType();
	}

	@Override
	public void visitClassType(String name) {
		sb.append("Clazz.of(");
//		sb.append(name.replace('/', '.'));
//		if (array) {
//			sb.append("[]");
//		}
//		sb.append(".class");
		sb.append(TinyASMifier.clazzOf(Type.getObjectType(name), referedTypes));
		
		TinyASMifier.logger.debug("{}visitClassType({})", indent(), name);
		level++;
		super.visitClassType(name);
	}

	@Override
	public void visitInnerClassType(String name) {
		TinyASMifier.logger.debug("{}visitInnerClassType({})", indent(), name);
		super.visitInnerClassType(name);
	}

	@Override
	public void visitTypeArgument() {
		TinyASMifier.logger.debug("{}visitTypeArgument()", indent());
		array = false;
		sb.append(",");
		super.visitTypeArgument();
	}

	@Override
	public SignatureVisitor visitTypeArgument(char wildcard) {
		sb.append(",");
		TinyASMifier.logger.debug("{}visitTypeArgument({})", indent(), wildcard);
//		level++;
		return super.visitTypeArgument(wildcard);
	}

	@Override
	public void visitEnd() {
		array = false;
		sb.append(")");
		level--;
		TinyASMifier.logger.debug("{}visitEnd()", indent());
		super.visitEnd();
	}
}