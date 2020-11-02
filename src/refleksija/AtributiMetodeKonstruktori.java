package refleksija;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class AtributiMetodeKonstruktori {

	
	@SuppressWarnings("rawtypes")
	private AtributiMetodeKonstruktori(String className) {
		Class cl;
		try {
			cl = Class.forName(className);
			printFields(cl);
			System.out.println("==============================");
			printMethods(cl);
			System.out.println("==============================");
			printConstructors(cl);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@SuppressWarnings("rawtypes")
	private void printFields(Class cl) {
		/**
		 * Vraca sve atribute neke klase, i privatne i javne
		 */
		Field[] fields = cl.getDeclaredFields();
		for (Field f : fields) {
			/**
			 * getModifiers vraca int, pa ga moramo parsirati u string da bismo dobili naziv modifier-a
			 */
			String modifier = Modifier.toString(f.getModifiers());
			Class type = f.getType();
			String name = f.getName();
			System.out.println(modifier + " " + type + " " + name + "\n");
		}
	}

	@SuppressWarnings("rawtypes")
	private void printMethods(Class cl) {
		/**
		 * Vraca sve metode neke klase, i privatne i javne
		 */
		Method[] methods = cl.getDeclaredMethods();
		for (Method m : methods) {
			String modifier = Modifier.toString(m.getModifiers());
			Class retType = m.getReturnType();
			String name = m.getName();
			System.out.print(modifier + " " + retType + " " + name + "(");

			Class[] paramTypes = m.getParameterTypes();
			for (int i = 0; i < paramTypes.length; i++) {
				if (i > 0)
					System.out.print(", ");
				System.out.print(paramTypes[i].getName());
			}
			System.out.println(")");
		}
	}

	@SuppressWarnings("rawtypes")
	private void printConstructors(Class cl) {
		/**
		 * Vraca sve konstruktore neke klase
		 */
		Constructor[] consts = cl.getDeclaredConstructors();
		for (Constructor c : consts) {
			String modifier = Modifier.toString(c.getModifiers());
			String name = c.getName();
			System.out.print(modifier + " " + name + "(");

			Class[] paramTypes = c.getParameterTypes();
			for (int i = 0; i < paramTypes.length; i++) {
				if (i > 0)
					System.out.print(", ");
				System.out.print(paramTypes[i].getName());
			}
			System.out.println(")");
		}
	}

	public static void main(String[] args) {
		new AtributiMetodeKonstruktori("java.lang.String");
	}

}
