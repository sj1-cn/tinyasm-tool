package cn.sj1.tinyasm.tools;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.objectweb.asm.Type;
import org.objectweb.asm.signature.SignatureVisitor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

final class ClassSignature extends SignatureVisitor {
	static Logger logger = LoggerFactory.getLogger(ClassSignature.class);

	int level = 0;
	boolean array = false;

	String indent() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < level; i++) {
			sb.append('\t');
		}
		return sb.toString();
	}

	List<StringBuilder> paramsClassList = new ArrayList<>();
	StringBuilder returnClass;
	StringBuilder superClass;
	List<StringBuilder> interfacesClassList = new ArrayList<>();
	List<StringBuilder> typeParameterClassList = new ArrayList<>();;

	ClassSignature(int api, Map<String, String> referedTypes) {
		super(api);
		this.referedTypes = referedTypes;
	}

	String header = "root";

	@Override
	public void visitFormalTypeParameter(String name) {
		logger.trace("{}visitFormalTypeParameter({})", indent(), name);
		sb = new StringBuilder();
		typeParameterClassList.add(sb);
		sb.append("\"");
		sb.append(name);
		sb.append("\"");
		sb.append(",");
		array = false;
		typeArgument = DEFAULT_TypeArgument;
		super.visitFormalTypeParameter(name);
	}

	@Override
	public SignatureVisitor visitClassBound() { // L
		logger.trace("{}visitClassBound()", indent());
		return this;
	}

	@Override
	public SignatureVisitor visitInterfaceBound() {
		logger.trace("{}visitInterfaceBound()", indent());
		return this;
	}

	@Override
	public SignatureVisitor visitSuperclass() {
		logger.trace("{}visitSuperclass()", indent());
		sb = superClass = new StringBuilder();
		array = false;
		typeArgument = DEFAULT_TypeArgument;
		return this;
	}

	@Override
	public SignatureVisitor visitInterface() {
		logger.trace("{}visitInterface()", indent());
		sb = new StringBuilder();
		interfacesClassList.add(sb);
		array = false;
		typeArgument = DEFAULT_TypeArgument;
		return this;
	}

	@Override
	public SignatureVisitor visitParameterType() {
		logger.trace("{}visitParameterType()", indent());
		sb = new StringBuilder();
		paramsClassList.add(sb);
		array = false;
		typeArgument = DEFAULT_TypeArgument;
		return this;
	}

	@Override
	public SignatureVisitor visitReturnType() {
		logger.trace("{}visitReturnType()", indent());
		sb = returnClass = new StringBuilder();
		array = false;
		typeArgument = DEFAULT_TypeArgument;
		return this;
	}

	@Override
	public SignatureVisitor visitExceptionType() {
		logger.trace("{}visitExceptionType()", indent());
		return this;
	}

	@Override
	public void visitBaseType(char descriptor) {
		logger.trace("{}visitBaseType({})", indent(), descriptor);
		sb.append("Clazz.of(");
		Type type = Type.getType(String.valueOf(descriptor));
		sb.append(type.getClassName());
		if (array) {
			sb.append("[]");
		}
		sb.append(".class");
		sb.append(')');
	}

	@Override
	public void visitTypeVariable(String name) {
		logger.trace("{}visitTypeVariable({})", indent(), name);
		if (typeArgument > DEFAULT_TypeArgument) {
			sb.append("Clazz.typeArgument(");
			if (typeArgument != '=') {
				sb.append("'");
				sb.append(typeArgument);
				sb.append("'");
				sb.append(",");
			}
			sb.append("Clazz.typeVariableOf(\"");
			sb.append(name);
			sb.append("\"");
			if (array) {
				sb.append(",true");
			}
			sb.append(")");
			sb.append(")");

		} else {
			sb.append("Clazz.typeVariableOf(\"");
			sb.append(name);
			sb.append("\"");
			if (array) {
				sb.append(",true");
			}
			sb.append(")");
		}
		typeArgument = DEFAULT_TypeArgument;
	}

	@Override
	public SignatureVisitor visitArrayType() {
		array = true;
		logger.trace("{}visitArrayType()", indent());
		return this;
	}

	static String toSimpleName(String str) {
		return str.substring(str.lastIndexOf('.') + 1, str.length());
	}

	@Override
	public void visitClassType(String name) {
		logger.trace("{}visitClassType({})", indent(), name);
		level++;
		if (typeArgument > DEFAULT_TypeArgument && typeArgument != '=') {
			sb.append("Clazz.typeArgument(");
			if (typeArgument != '=') {
				sb.append("'");
				sb.append(typeArgument);
				sb.append("'");
				sb.append(",");
			}
			String className = name.replace('/', '.');
			referedTypes.put(name.replace('/', '.'), "");
			sb.append(toSimpleName(className));
			if (array) {
				sb.append("[]");
			}
			sb.append(".class");
		} else {
			sb.append("Clazz.of(");
			String className = name.replace('/', '.');
			referedTypes.put(name.replace('/', '.'), "");
			sb.append(toSimpleName(className));
			if (array) {
				sb.append("[]");
			}
			sb.append(".class");
		}
		typeArgument = DEFAULT_TypeArgument;
	}

	@Override
	public void visitInnerClassType(String name) {
		logger.trace("{}visitInnerClassType({})", indent(), name);
	}

	final static char DEFAULT_TypeArgument = (char) 0;
	char typeArgument = DEFAULT_TypeArgument;

	@Override
	public void visitTypeArgument() {
		logger.trace("{}visitTypeArgument()", indent());
		array = false;
		sb.append(", Clazz.typeUnboundedTypeArgument()");
	}

	@Override
	public SignatureVisitor visitTypeArgument(char wildcard) {
		logger.trace("{}visitTypeArgument({})", indent(), wildcard);
		sb.append(",");
		typeArgument = wildcard;
		return this;
	}

	@Override
	public void visitEnd() {
		sb.append(")");
		array = false;
		level--;
		logger.trace("{}visitEnd()", indent());
		super.visitEnd();
	}

	StringBuilder sb;
	Map<String, String> referedTypes;

	@Override
	public String toString() {
		return sb.toString();
	}
}