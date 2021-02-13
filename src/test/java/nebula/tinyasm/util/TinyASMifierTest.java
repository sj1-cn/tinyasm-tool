package nebula.tinyasm.util;

import static nebula.tinyasm.util.RefineCode.excludeLineNumber;
import static nebula.tinyasm.util.RefineCode.skipToString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.junit.Test;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.util.ASMifier;
import org.objectweb.asm.util.TraceClassVisitor;

public class TinyASMifierTest extends TinyASMifierTestBase {

	@Test
	public void testSimpleSample() throws Exception {
		Class<?> expectedClazz = SimpleSample.class;
		String codeActual = tinyasmToString(expectedClazz);
		String codeExpected = toString(expectedClazz);

		assertNotEquals("Code", codeExpected, codeActual);
	}
	@Test
	public void test_LabelSample() throws Exception {
		Class<?> expectedClazz = LabelSample.class;

		String codeActual = tinyasmToString(expectedClazz);
		String codeExpected = toString(expectedClazz);

		assertNotEquals("Code", codeExpected, codeActual);
	}
	

	@Test
	public void test_Pojo() throws Exception {
		Class<?> expectedClazz = Pojo.class;

		String codeActual = tinyasmToString(expectedClazz);
		String codeExpected = toString(expectedClazz);

		assertNotEquals("Code", codeExpected, codeActual);
	}
	
	
	
	
}
