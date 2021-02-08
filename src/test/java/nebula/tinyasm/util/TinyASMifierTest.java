package nebula.tinyasm.util;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TinyASMifierTest extends TinyASMifierTestBase {

	@Test
	public void testSimpleSample() throws Exception {
		Class<?> expectedClazz = SimpleSample.class;
		String codeExpected = toString(expectedClazz);

		try {
			String codeActual = toString(makeAndDump(expectedClazz));

			assertEquals("Code", codeExpected, codeActual);
		} finally {
			
			System.out.println(codeExpected);
			
		}

	}
	@Test
	public void test_LabelSample() throws Exception {
		Class<?> expectedClazz = LabelSample.class;

		String codeActual = toString(makeAndDump(expectedClazz));
		String codeExpected = toString(expectedClazz);

		assertEquals("Code", codeExpected, codeActual);
	}
	

	@Test
	public void test_Pojo() throws Exception {
		Class<?> expectedClazz = Pojo.class;

		String codeActual = toString(makeAndDump(expectedClazz));
		String codeExpected = toString(expectedClazz);

		assertEquals("Code", codeExpected, codeActual);
	}
	
	
	
	
	
}
