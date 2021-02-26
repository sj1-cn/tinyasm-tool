package cc1sj.tinyasm.util;

import java.util.ArrayList;
import java.util.List;

import org.objectweb.asm.ClassVisitor;

public class TinyClassASMifier extends pr {

	public final List<Object> text;

	public TinyClassASMifier(int api, ClassVisitor classVisitor) {
		super(api, classVisitor);
		text = new ArrayList<>();
	}

	public TinyClassASMifier(int api) {
		super(api);
		text = new ArrayList<>();
	}

}
