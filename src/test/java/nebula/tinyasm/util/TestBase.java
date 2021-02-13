package nebula.tinyasm.util;

import static nebula.tinyasm.util.RefineCode.excludeLineNumber;
import static nebula.tinyasm.util.RefineCode.skipToString;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.util.ASMifier;
import org.objectweb.asm.util.TraceClassVisitor;

public class TestBase {

	public static String toString(byte[] code) {
		try {
			ClassReader cr = new ClassReader(code);
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			ClassVisitor visitor = new TraceClassVisitor(null, new ASMifier(), pw);
			cr.accept(visitor, ClassReader.EXPAND_FRAMES);
			return skipToString(excludeLineNumber(sw.toString()));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	public static String toString(String className) {
		try {
			ClassReader cr = new ClassReader(className);
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			ClassVisitor visitor = new TraceClassVisitor(null, new ASMifier(), pw);
			cr.accept(visitor, ClassReader.EXPAND_FRAMES);
			return skipToString(excludeLineNumber(sw.toString()));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}