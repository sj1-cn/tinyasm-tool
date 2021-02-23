package cc1sj.tinyasm.util;

import static cc1sj.tinyasm.util.RefineCode.excludeLineNumber;
import static cc1sj.tinyasm.util.RefineCode.skipToString;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
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

public class TinyAsmTestUtils {

	public static String tinyasmToString(Class<?> clazz) {
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

	public static Class<?> loadClass(File file, String className) {
		String fileUrl = "file:/" + file.getParent();
		System.out.println(fileUrl);
		try {
			URL[] urls = new URL[] { new URL(fileUrl) };
			URLClassLoader ul = new URLClassLoader(urls, ClassLoader.getSystemClassLoader());
			Class<?> c = ul.loadClass(className);
//			System.out.println(c.newInstance().getClass().getName());
//			Object o = c.newInstance();
			ul.close();
			return c;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public static void complie2Class(File file) {
		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
		StandardJavaFileManager fileMgr = compiler.getStandardFileManager(null, null, null);
		Iterable<? extends JavaFileObject> units = fileMgr.getJavaFileObjects(file);
		List<String> optionList = Arrays.asList("-d", "target/test-classes");

		JavaCompiler.CompilationTask t = compiler.getTask(null, fileMgr, null, optionList, null, units);
		t.call();
		try {
			fileMgr.close();
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public static void writeToFile(String str, File file) {
		try {
			FileOutputStream os = new FileOutputStream(file);
			os.write(str.getBytes("utf-8"));
			os.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public static byte[] dumpTinyAsm(Class<?> expectedClazz) {

		try {
			String expectClazzName = expectedClazz.getName();
			String tingasmCreatedDumpCode = TinyAsmTestUtils.tinyasmToString(expectedClazz);
			System.out.println(tingasmCreatedDumpCode);

			String dumpClazz = expectClazzName + "TinyAsmDump";

			File file = new File("src/test/java", dumpClazz.replace('.', '/') + ".java");
			writeToFile(tingasmCreatedDumpCode, file);
			complie2Class(file);
			Class<?> clazz = loadClass(file, dumpClazz);
			byte[] code = (byte[]) clazz.getMethod("dump").invoke(null);
			return code;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public static String tinyasmToString(String clazz) {
		try {
			ClassReader cr = new ClassReader(clazz);
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			ClassVisitor visitor = new TraceClassVisitor(null, new TinyASMifier(), pw);
			cr.accept(visitor, ClassReader.EXPAND_FRAMES);
			return skipToString(excludeLineNumber(sw.toString()));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
