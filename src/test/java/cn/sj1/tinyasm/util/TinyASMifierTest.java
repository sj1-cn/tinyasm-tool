package cn.sj1.tinyasm.util;

import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

public class TinyASMifierTest {

	@Test
	public void testSimpleSample() throws Exception {
		Class<?> expectedClazz = SimpleSample.class;
		String codeActual = TinyAsmTestUtils.tinyasmToString(expectedClazz);
		String codeExpected = TinyAsmTestUtils.toString(expectedClazz);

		assertNotEquals("Code", codeExpected, codeActual);
	}

	@Test
	public void test_LabelSample() throws Exception {
		Class<?> expectedClazz = LabelSample.class;

		String codeActual = TinyAsmTestUtils.tinyasmToString(expectedClazz);
		String codeExpected = TinyAsmTestUtils.toString(expectedClazz);

		assertNotEquals("Code", codeExpected, codeActual);
	}

	@Test
	public void test_Pojo() throws Exception {
		Class<?> expectedClazz = Pojo.class;

		String codeActual = TinyAsmTestUtils.tinyasmToString(expectedClazz);
		String codeExpected = TinyAsmTestUtils.toString(expectedClazz);

		assertNotEquals("Code", codeExpected, codeActual);
	}

}
