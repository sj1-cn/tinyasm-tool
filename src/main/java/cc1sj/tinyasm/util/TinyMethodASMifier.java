package cc1sj.tinyasm.util;

import org.objectweb.asm.MethodVisitor;

public class TinyMethodASMifier extends MethodVisitor {

	public TinyMethodASMifier(int api, MethodVisitor methodVisitor) {
		super(api, methodVisitor);
		// TODO Auto-generated constructor stub
	}

	public TinyMethodASMifier(int api) {
		super(api);
		// TODO Auto-generated constructor stub
	}

}
