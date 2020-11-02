package refleksija;

import java.lang.reflect.Method;

public class PozivMetode {

	private int strlen(String s) {
		return s.length();
	}
	
	private static void f() {
		System.out.println("Metoda f");
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void invokeMethod() {
		try {
			Class cl = Class.forName("refleksija.PozivMetode");
			Method mtdF = cl.getDeclaredMethod("f");
			/**
			 * Pozivanje metode neke klase, bez instanciranja te klase.
			 */
			mtdF.invoke(null);

			Method mtd = cl.getDeclaredMethod("strlen", String.class);
			Object obj = cl.newInstance();
			/**
			 * Pozivanje metode instanciranog objekta.
			 */
			int len = (Integer) mtd.invoke(obj, "Test");
			System.out.println(len);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

//	public static void main(String[] args) {
//		new PozivMetode().invokeMethod();
//	}
}