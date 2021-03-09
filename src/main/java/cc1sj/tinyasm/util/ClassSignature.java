package cc1sj.tinyasm.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
	List<StringBuilder> interfacesClassList = new ArrayList<>();
	List<StringBuilder> typeParameterClassList = new ArrayList<>();;

	StringBuilder sb;
	Map<String, String> referedTypes;

	ClassSignature(int api, Map<String, String> referedTypes) {
		super(api);
		this.referedTypes = referedTypes;
	}

	@Override
	public void visitFormalTypeParameter(String name) {
		sb = new StringBuilder();
		typeParameterClassList.add(sb);
		TinyASMifier.logger.trace("{}visitFormalTypeParameter({})", indent(), name);
		sb.append("\"");
		sb.append(name);
		sb.append("\"");
		sb.append(",");
		super.visitFormalTypeParameter(name);
	}

	@Override
	public SignatureVisitor visitClassBound() {
		TinyASMifier.logger.trace("{}visitClassBound()", indent());
//		level++;
		return super.visitClassBound();
	}

	@Override
	public SignatureVisitor visitInterfaceBound() {
		TinyASMifier.logger.trace("{}visitInterfaceBound()", indent());
		return super.visitInterfaceBound();
	}

	@Override
	public SignatureVisitor visitSuperclass() {
		sb = superClass = new StringBuilder();
		TinyASMifier.logger.trace("{}visitSuperclass()", indent());
//		level++;
		return super.visitSuperclass();
	}

	@Override
	public SignatureVisitor visitInterface() {
		sb = new StringBuilder();
		interfacesClassList.add(sb);
//			sb.append(",");
		TinyASMifier.logger.trace("{}visitInterface()", indent());
//		level++;
		return super.visitInterface();
	}

	@Override
	public SignatureVisitor visitParameterType() {
		sb = new StringBuilder();
		paramsClass.add(sb);
		TinyASMifier.logger.trace("{}visitParameterType()", indent());
//		level++;
		return super.visitParameterType();
	}

	@Override
	public SignatureVisitor visitReturnType() {
		sb = returnClass = new StringBuilder();
		TinyASMifier.logger.trace("{}visitReturnType()", indent());
//		level++;
		return super.visitReturnType();
	}

	@Override
	public SignatureVisitor visitExceptionType() {
		TinyASMifier.logger.trace("{}visitExceptionType()", indent());
		return super.visitExceptionType();
	}

	@Override
	public void visitBaseType(char descriptor) {
		TinyASMifier.logger.trace("{}visitBaseType({})", indent(), descriptor);
		super.visitBaseType(descriptor);
	}

	@Override
	public void visitTypeVariable(String name) {
		sb.append("Clazz.typeVariableOf(\"");
		sb.append(name);
		sb.append("\"");
		if (array) {
			sb.append(",true");
		}
		sb.append(")");
		TinyASMifier.logger.trace("{}visitTypeVariable({})", indent(), name);
		super.visitTypeVariable(name);
	}

	@Override
	public SignatureVisitor visitArrayType() {
		array = true;
		TinyASMifier.logger.trace("{}visitArrayType()", indent());
		return super.visitArrayType();
	}

	static String toSimpleName(String str) {
		return str.substring(str.lastIndexOf('.') + 1, str.length());
	}

	@Override
	public void visitClassType(String name) {
		sb.append("Clazz.of(");
		String className = name.replace('/', '.');
		referedTypes.put(name.replace('/', '.'), "");
		sb.append(toSimpleName(className));
		if (array) {
			sb.append("[]");
		}
		sb.append(".class");

		TinyASMifier.logger.trace("{}visitClassType({})", indent(), name);
		level++;
		super.visitClassType(name);
	}

	@Override
	public void visitInnerClassType(String name) {
		TinyASMifier.logger.trace("{}visitInnerClassType({})", indent(), name);
		super.visitInnerClassType(name);
	}

	@Override
	public void visitTypeArgument() {
		TinyASMifier.logger.trace("{}visitTypeArgument()", indent());
		array = false;
		sb.append(", Clazz.typeUnboundedVariable()");
		super.visitTypeArgument();
	}

	@Override
	public SignatureVisitor visitTypeArgument(char wildcard) {
		sb.append(",");
		TinyASMifier.logger.trace("{}visitTypeArgument({})", indent(), wildcard);
//		level++;
		return super.visitTypeArgument(wildcard);
	}

	@Override
	public void visitEnd() {
		array = false;
		sb.append(")");
		level--;
		TinyASMifier.logger.trace("{}visitEnd()", indent());
		super.visitEnd();
	}
}