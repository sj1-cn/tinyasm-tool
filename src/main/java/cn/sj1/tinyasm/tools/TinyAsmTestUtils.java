package cn.sj1.tinyasm.tools;

import static cn.sj1.tinyasm.tools.RefineCode.excludeLineNumber;
import static cn.sj1.tinyasm.tools.RefineCode.skipToString;

import java.io.File;
import java.io.FileInputStream;
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
	private static String TARGET_DEFAULT = "src/test/java";

	private static ThreadLocal<String> target = new ThreadLocal<String>();

	public static String getTarget() {
		if (target == null || target.get() == null) {
			target = new ThreadLocal<>();
			target.set(TARGET_DEFAULT);
			ensurePathExist(new File(TARGET_DEFAULT));
			return TARGET_DEFAULT;
		} else {
			return target.get();
		}
	}

	public static void setTarget(String path) {
		if (target == null) {
			target = new ThreadLocal<String>();
		}
		if (path.equals(target.get())) {

		} else {
			target.set(path);
			ensurePathExist(new File(path));
		}
	}

	private static void ensurePathExist(File path) {
		if (path.exists()) return;
		else {
			ensurePathExist(path.getParentFile());
			path.mkdir();
		}
	}

	public static String tinyasmToString(Class<?> clazz) {
		try {
			ClassReader cr = new ClassReader(clazz.getName());
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			ClassVisitor visitor = new TraceClassVisitor(null, new TinyASMifier(), pw);
			cr.accept(visitor, ClassReader.EXPAND_FRAMES);

			String strCode = sw.toString();
			writeCodeToFile(clazz, strCode);
			return skipToString(excludeLineNumber(strCode));

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static String tinyasmToString(Class<?> clazz, List<String> names, List<?> classes) {
		try {
			ClassReader cr = new ClassReader(clazz.getName());
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			ClassVisitor visitor = new TraceClassVisitor(null, new TinyASMifier(names, classes), pw);
			cr.accept(visitor, ClassReader.EXPAND_FRAMES);

			String strCode = sw.toString();
			writeCodeToFile(clazz, strCode);
			return skipToString(excludeLineNumber(strCode));

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected static void writeCodeToFile(Class<?> clazz, String strCode) {
		writeCodeToFile(clazz.getName(), strCode);
	}

	protected static void writeCodeToFile(String className, String strCode) {
		writeToFile(strCode, new File("tmp", System.currentTimeMillis() + className.replace('.', '_') + "_dump" + ".java"));
	}

	public static String toString(Class<?> clazz) {
		try {
			ClassReader cr = new ClassReader(clazz.getName());
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			ClassVisitor visitor = new TraceClassVisitor(null, new ASMifier(), pw);
			cr.accept(visitor, ClassReader.EXPAND_FRAMES);

			String strCode = sw.toString();
			writeCodeToFile(clazz, strCode);
			return skipToString(excludeLineNumber(strCode));

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static String toString(String className, byte[] code) {
		try {
			ClassReader cr = new ClassReader(code);
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			ClassVisitor visitor = new TraceClassVisitor(null, new ASMifier(), pw);
			cr.accept(visitor, ClassReader.EXPAND_FRAMES);

			String strCode = sw.toString();
			writeCodeToFile(className, strCode);
			return skipToString(excludeLineNumber(strCode));
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

			String strCode = sw.toString();

			writeCodeToFile(className, strCode);
			return skipToString(excludeLineNumber(strCode));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static Class<?> loadClass(File file, String className) {
		String fileUrl = "file:/" + file.getParent();
		try {
			URL[] urls = new URL[] { new URL(fileUrl) };
			URLClassLoader ul = new URLClassLoader(urls, ClassLoader.getSystemClassLoader());
			Class<?> c = ul.loadClass(className);
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
		ensurePathExist(file.getParentFile());
		try {
			FileOutputStream os = new FileOutputStream(file);
			os.write(str.getBytes("utf-8"));
			os.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public static String readJavaSourceFile(File file) {
		try {
			FileInputStream fis = new FileInputStream(file);
			byte[] data = new byte[(int) file.length()];
			fis.read(data);
			fis.close();

			return new String(data, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public static String readJavaSourceFile(String className) {
		return readJavaSourceFile(new File(getTarget(), className.replace('.', '/') + ".java"));
	}

	public static String readJavaSourceFile(Class<?> clazz) {
		return readJavaSourceFile(new File(getTarget(), clazz.getName().replace('.', '/') + ".java"));
	}

	public static void writeJavaSourceFile(String className, String code) {
		writeToFile(code, new File(getTarget(), className.replace('.', '/') + ".java"));
	}

	public static void writeJavaSourceFile(Class<?> clazz, String code) {
		writeToFile(code, new File(getTarget(), clazz.getName().replace('.', '/') + ".java"));
	}

	public static byte[] dumpTinyAsm(Class<?> expectedClazz) {

		try {
			String expectClazzName = expectedClazz.getName();
			String tingasmCreatedDumpCode = TinyAsmTestUtils.tinyasmToString(expectedClazz);

			String dumpClazz = expectClazzName + "TinyAsmDump";

			writeToFile(tingasmCreatedDumpCode, new File(getTarget(), dumpClazz.replace('.', '/') + ".java"));

			complie2Class(new File(getTarget(), dumpClazz.replace('.', '/') + ".java"));
			Class<?> clazz = loadClass(new File(getTarget(), dumpClazz.replace('.', '/') + ".java"), dumpClazz);
			byte[] code = (byte[]) clazz.getMethod("dump").invoke(null);
			return code;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public static byte[] dumpTinyAsm(Class<?> expectedClazz, String firstName, Object firstClass, String secondName, Object secondClass) {
		return dumpTinyAsm(expectedClazz, Arrays.asList(firstName, secondName), Arrays.asList(firstClass, secondClass));
	}

	public static byte[] dumpTinyAsm(Class<?> expectedClazz, String firstName, Object firstClass, String secondName, Object secondClass, String thirdName, Class<?> thirdClass) {
		return dumpTinyAsm(expectedClazz, Arrays.asList(firstName, secondName, thirdName), Arrays.asList(firstClass, secondClass, thirdClass));
	}

	public static byte[] dumpTinyAsm(Class<?> expectedClazz, String firstName, Object firstClass) {
		return dumpTinyAsm(expectedClazz, Arrays.asList(firstName), Arrays.asList(firstClass));
	}

	public static byte[] dumpTinyAsm(Class<?> expectedClazz, List<String> paramNames, List<? extends Object> paramVales) {

		try {
			String expectClazzName = expectedClazz.getName();
			String tingasmCreatedDumpCode = TinyAsmTestUtils.tinyasmToString(expectedClazz, paramNames, paramVales);

			String dumpClazz = expectClazzName + "TinyAsmDump";

			writeToFile(tingasmCreatedDumpCode, new File(getTarget(), dumpClazz.replace('.', '/') + ".java"));

			complie2Class(new File(getTarget(), dumpClazz.replace('.', '/') + ".java"));
			Class<?> clazz = loadClass(new File(getTarget(), dumpClazz.replace('.', '/') + ".java"), dumpClazz);
			Object instance = clazz.getConstructor().newInstance();

			Object[] params = new Object[paramVales.size() + 1];
			Class<?>[] paramClasses = new Class<?>[paramVales.size() + 1];
			params[0] = expectClazzName;
			paramClasses[0] = String.class;
			for (int i = 0; i < paramVales.size(); i++) {
				params[i + 1] = paramVales.get(i);
				paramClasses[i + 1] = paramVales.get(i).getClass();
			}

			byte[] code = (byte[]) clazz.getMethod("build", paramClasses).invoke(instance, params);
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
