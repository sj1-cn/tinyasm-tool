package cc1sj.tinyasm.util;

import org.objectweb.asm.FieldVisitor;

public class TinyFieldASMifier extends FieldVisitor {

	public TinyFieldASMifier(int api, FieldVisitor fieldVisitor) {
		super(api, fieldVisitor);
		// TODO Auto-generated constructor stub
	}

	public TinyFieldASMifier(int api) {
		super(api);
		// TODO Auto-generated constructor stub
	}

}
