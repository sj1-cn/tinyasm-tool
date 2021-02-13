package nebula.tinyasm.util;

import static nebula.tinyasm.util.RefineCode.excludeLineNumber;
import static nebula.tinyasm.util.RefineCode.skipToString;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;
import java.util.List;

import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.util.ASMifier;
import org.objectweb.asm.util.TraceClassVisitor;

public class TinyASMifierTestBase extends TestBase {


	public static String toString(Class<?> clazz) {
		try {
			ClassReader cr = new ClassReader(clazz.getName());
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			ClassVisitor visitor = new TraceClassVisitor(null, new ASMifier(), pw);
			cr.accept(visitor, ClassReader.EXPAND_FRAMES);
			return skipToString(excludeLineNumber(sw.toString()));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public String tinyasmToString(Class<?> clazz) {
		try {
			ClassReader cr = new ClassReader(clazz.getName());
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			ClassVisitor visitor = new TraceClassVisitor(null, new TinyASMifier(), pw);
			cr.accept(visitor, ClassReader.EXPAND_FRAMES);
			return skipToString(excludeLineNumber(sw.toString()));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	
//	protected String make(Class<?> expectedClazz) throws FileNotFoundException, IOException, UnsupportedEncodingException, ClassNotFoundException,
//			IllegalAccessException, InvocationTargetException, NoSuchMethodException {
//	
//		String expectClazzName = expectedClazz.getName();
//		String tingasmCreatedDumpCode = tinyasmToString(expectedClazz);
//		System.out.println(tingasmCreatedDumpCode);
//		return tingasmCreatedDumpCode;
//	}
//
//	protected String tinyasmToString(String clazz) {
//		try {
//			ClassReader cr = new ClassReader(clazz);
//			StringWriter sw = new StringWriter();
//			PrintWriter pw = new PrintWriter(sw);
//			ClassVisitor visitor = new TraceClassVisitor(null, new TinyASMifier(), pw);
//			cr.accept(visitor, ClassReader.EXPAND_FRAMES);
//			return skipToString(excludeLineNumber(sw.toString()));
//		} catch (Exception e) {
//			throw new RuntimeException(e);
//		}
//	}

}