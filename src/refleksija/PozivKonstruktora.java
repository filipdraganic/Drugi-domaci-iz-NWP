package refleksija;

import java.lang.reflect.Constructor;

public class PozivKonstruktora {
	public PozivKonstruktora(String s) {
		System.out.println("Konstruktor sa String parametrom: " + s);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void main(String[] args) {
		try {
			Class cl = Class.forName("refleksija.PozivKonstruktora");
			Constructor c = cl.getDeclaredConstructor(String.class);
			c.newInstance("tekst");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
