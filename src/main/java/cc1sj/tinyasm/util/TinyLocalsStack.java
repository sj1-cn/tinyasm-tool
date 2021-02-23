package cc1sj.tinyasm.util;

import java.util.Iterator;
import java.util.Stack;

import org.objectweb.asm.Label;
import org.objectweb.asm.Type;

//import nebula.tinyasm.Annotation;

public class TinyLocalsStack implements Iterable<TinyLocalsStack.Var> {

	public static class Var {
		int access;
		public Label startFrom;

		public Object value;

//		public Annotation annotation;
		Type type;
		String name;
		String signature = null;

//		public void set(String s) {
//			this.signature = s;
//		}

		public void setSignature(String signature) {
			this.signature = signature;
		}

		public String getSignature() {
			return signature;
		}

		public Var(String name, Type type) {
			this(0, name, type);
		}

		public Var(int access, String name, Type type) {
			this.access = access;
			this.name = name;
			this.type = type;
		}
//
//		public Var(Annotation annotation, String name, Type clazz) {
//			this(0, name, clazz);
//			this.annotation = annotation;
//		}

		public Var(String name, Type type, Label startFrom) {
			this(0, name, type);
			this.startFrom = startFrom;
		}

		public int locals = 0;

		int count = 0;
		public boolean defined;

		@Override
		public String toString() {
			return this.name != null && this.name.length() > 0 ? this.name : "var" + locals;
		}
	}

	Stack<Var> stack = new Stack<>();

	Stack<Integer> locals = new Stack<>();

	public Var getByLocal(int index) {
		return stack.get(locals.get(index));
	}

	public Iterator<Var> iterator() {
		return stack.iterator();
	}

	public Var accessLoad(int index, int size) {
		Var var;
		if (locals.size() > index) {
			int stackIndex = locals.get(index);
			if (stackIndex >= 0) {
				var = stack.get(stackIndex);
			} else {
				stackIndex = stack.size();
				var = new Var(null, null);
				var.locals = index;
				stack.push(var);
				locals.set(index, stackIndex);
				for (int i = 1; i < size; i++) {
					locals.set(index + i, -i);
				}
			}
		} else {
			if (locals.size() < index) {
				for (int i = locals.size(); i < index; i++) {
					locals.push(-i);
				}
			}

			int stackIndex = stack.size();
			var = new Var(null, null);
			var.locals = index;
			locals.push(stackIndex);
			for (int i = 1; i < size; i++) {
				locals.push(-i);
			}
			stack.push(var);

		}
		var.count++;
		return var;
	}

	public Var accessStore(int index, int size) {
		Var var;
		if (locals.size() > index) {
			int stackIndex = locals.get(index);
			if (stackIndex >= 0) {
				var = stack.get(stackIndex);
			} else {
				stackIndex = stack.size();
				var = new Var(null, null);
				var.locals = index;
				stack.push(var);
				locals.set(index, stackIndex);
				for (int i = 1; i < size; i++) {
					locals.set(index + i, -i);
				}
			}
		} else {
			if (locals.size() < index) {
				for (int i = locals.size(); i < index; i++) {
					locals.push(-i);
				}
			}

			int stackIndex = stack.size();
			var = new Var(null, null);
			var.locals = index;
			locals.push(stackIndex);
			for (int i = 1; i < size; i++) {
				locals.push(-i);
			}
			stack.push(var);

		}
		var.count++;
		return var;
	}

	public Var pushDefined(String name, Type clazz) {
		Var var = new Var(name, clazz);
		var.defined = true;
		return push(name, var);
	}

	public Var pushUndefined(String name) {
		return push(name, new Var(name, null));
	}

//	public Var push(Annotation annotation, String name, Type clazz) {
//		Var var = new Var(annotation, name, clazz);
//		return push(name, var);
//	}

	private Var push(String name, Var var) {
		var.count++;
		var.locals = locals.size();
		for (int i = 0; i < var.type.getSize(); i++) {
			locals.push(stack.size());
		}
		stack.push(var);
		return var;
	}

	public Var push(String name, Type clazz, Label label) {
		Var var = new Var(name, clazz, label);
		return push(name, var);
	}

	public int size() {
		return locals.size();
	}
}
