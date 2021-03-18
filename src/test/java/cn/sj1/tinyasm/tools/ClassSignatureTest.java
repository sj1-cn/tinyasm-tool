package cn.sj1.tinyasm.tools;

import static org.junit.Assert.assertEquals;

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
		assertEquals("Clazz.of(Class.class, Clazz.typeUnboundedTypeArgument())", signatureVistor.superClass.toString());
	}

	@Test
	public void test2() {
		String signature = "Ljava/lang/Class<TT;>;";
		Map<String, String> tiny_referedTypes = new HashMap<String, String>();

		SignatureReader sr = new SignatureReader(signature);
		ClassSignature signatureVistor = new ClassSignature(Opcodes.ASM9, tiny_referedTypes);
		sr.accept(signatureVistor);
//		logger.trace("visitLocalVariable({} {}", name, signatureVistor.superClass);
		assertEquals("Clazz.of(Class.class,Clazz.typeArgument(Clazz.typeVariableOf(\"T\")))", signatureVistor.superClass.toString());

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
		assertEquals("Clazz.typeVariableOf(\"T\",true)", signatureVistor.paramsClassList.get(0).toString());
		assertEquals("Clazz.typeVariableOf(\"T\",true)", signatureVistor.returnClass.toString());

	}


	@Test
	public void test5() {
		String signature = "(Ljava/util/Collection<*>;)Z";
		Map<String, String> tiny_referedTypes = new HashMap<String, String>();

		SignatureReader sr = new SignatureReader(signature);
		ClassSignature signatureVistor = new ClassSignature(Opcodes.ASM9, tiny_referedTypes);
		sr.accept(signatureVistor);
//		logger.trace("visitLocalVariable({} {}", name, signatureVistor.superClass);
		assertEquals("[]", signatureVistor.typeParameterClassList.toString());
		assertEquals("Clazz.of(Collection.class, Clazz.typeUnboundedTypeArgument())", signatureVistor.paramsClassList.get(0).toString());
		assertEquals("Clazz.of(boolean.class)", signatureVistor.returnClass.toString());

	}
	
	

	@Test
	public void test_basetypessssss() {
		String signature = "(ILjava/util/Collection<+Lcc1sj/tinyasm/hero/helperclass/PojoClassSample;>;)Z";
		Map<String, String> tiny_referedTypes = new HashMap<String, String>();

		SignatureReader sr = new SignatureReader(signature);
		ClassSignature signatureVistor = new ClassSignature(Opcodes.ASM9, tiny_referedTypes);
		sr.accept(signatureVistor);
//		logger.trace("visitLocalVariable({} {}", name, signatureVistor.superClass);
		assertEquals("Clazz.of(boolean.class)", signatureVistor.returnClass.toString());
		assertEquals("[]", signatureVistor.typeParameterClassList.toString());
		assertEquals("Clazz.of(int.class)", signatureVistor.paramsClassList.get(0).toString());
		assertEquals("Clazz.of(Collection.class,Clazz.typeArgument('+',PojoClassSample.class))", signatureVistor.paramsClassList.get(1).toString());
		
//		
//		assertEquals("Z", classSignaturewwww.returnClazz.toString());
//
////		assertEquals(null, classSignaturewwww.returnClazz);
//		assertEquals("I", classSignaturewwww.paramsClazzes[0].toString());
//		assertEquals("Ljava/util/Collection<+Lcc1sj/tinyasm/hero/helperclass/PojoClassSample;>;", classSignaturewwww.paramsClazzes[1].toString());
////		assertEquals("Ljava/util/Collection<*>;", classSignaturewwww.paramsClazzes[0].signatureOf());
//		assertEquals(0, classSignaturewwww.interfaceClazzes.length);
//		assertEquals(null, classSignaturewwww.superClazz);
//		assertEquals(0, classSignaturewwww.typeParamenterClazzes.length);
	}
	

	@Test
	public void test_basetypessssss_() {
		String signature = "(Ljava/util/function/Consumer<-TE;>;)V";
		Map<String, String> tiny_referedTypes = new HashMap<String, String>();

		SignatureReader sr = new SignatureReader(signature);
		ClassSignature signatureVistor = new ClassSignature(Opcodes.ASM9, tiny_referedTypes);
		sr.accept(signatureVistor);
//		logger.trace("visitLocalVariable({} {}", name, signatureVistor.superClass);
		assertEquals("Clazz.of(void.class)", signatureVistor.returnClass.toString());
		assertEquals("[]", signatureVistor.typeParameterClassList.toString());
		assertEquals("Clazz.of(Consumer.class,Clazz.typeArgument('-',Clazz.typeVariableOf(\"E\")))", signatureVistor.paramsClassList.get(0).toString());

	}

	
//	signatureVistor.returnClass
}
