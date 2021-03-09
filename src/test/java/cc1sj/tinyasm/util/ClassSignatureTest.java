package cc1sj.tinyasm.util;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.signature.SignatureReader;

public class ClassSignatureTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		String signature = "Ljava/lang/Class<*>;";
		Map<String, String> tiny_referedTypes = new HashMap<String, String>();

		SignatureReader sr = new SignatureReader(signature);
		ClassSignature signatureVistor = new ClassSignature(Opcodes.ASM9, tiny_referedTypes);
		sr.accept(signatureVistor);
//		logger.trace("visitLocalVariable({} {}", name, signatureVistor.superClass);
		assertEquals("Clazz.of(Class.class, Clazz.typeUnboundedVariable())", signatureVistor.superClass.toString());
	}

	@Test
	public void test2() {
		String signature = "Ljava/lang/Class<TT;>;";
		Map<String, String> tiny_referedTypes = new HashMap<String, String>();

		SignatureReader sr = new SignatureReader(signature);
		ClassSignature signatureVistor = new ClassSignature(Opcodes.ASM9, tiny_referedTypes);
		sr.accept(signatureVistor);
//		logger.trace("visitLocalVariable({} {}", name, signatureVistor.superClass);
		assertEquals("Clazz.of(Class.class,Clazz.typeVariableOf(\"T\"))", signatureVistor.superClass.toString());

	}

//	

	@Test
	public void test3() {
		String signature = "<T:Ljava/lang/Object;>([TT;)[TT;";
		Map<String, String> tiny_referedTypes = new HashMap<String, String>();

		SignatureReader sr = new SignatureReader(signature);
		ClassSignature signatureVistor = new ClassSignature(Opcodes.ASM9, tiny_referedTypes);
		sr.accept(signatureVistor);
//		logger.trace("visitLocalVariable({} {}", name, signatureVistor.superClass);
		assertEquals("[\"T\",Clazz.of(Object.class)]", signatureVistor.typeParameterClassList.toString());
		assertEquals("Clazz.typeVariableOf(\"T\",true)", signatureVistor.paramsClass.get(0).toString());
		assertEquals("Clazz.typeVariableOf(\"T\",true)", signatureVistor.returnClass.toString());

	}

}
