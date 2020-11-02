package refleksija;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

public class IzmenaAtributa {
	
	@SuppressWarnings("rawtypes")
	public static void main(String[] args) {
		try {
			Class cl = Class.forName("refleksija.TestKlasa");
			Field f = cl.getDeclaredField("a");
			Field f2 = cl.getDeclaredField("b");
			/**
			 * Nacin da instanciramo objekat nepoznatog tipa.
			 */
			Object obj = cl.newInstance();

			/**
			 * Da bismo mogli da pristupimo privatnom atributu.
			 */
			f.setAccessible(true);

			System.out.println("Vrednost atributa a je: " + f.getInt(obj));
			/**
			 * Prvi parametar je objekat cije polje setujemo, drugi je vrednost.
			 * Ako nije u pitanju primitiv, pozivamo metodu .set(Object object,Object value)
			 */
			f.setInt(obj, 5);
			System.out.println("Vrednost atributa a je: " + f.getInt(obj));
			f2.set(obj, "hello world!");

			System.out.println("Vrednost atributa b je: " + f2.get(obj));

			/**
			 * Drugi nacin za instanciranje objekta
			 */
			Class cl2 = Class.forName("refleksija.TestKlasaSaKonstruktorom");
			TestKlasaSaKonstruktorom constructor = null;
			try {
				constructor = (TestKlasaSaKonstruktorom) cl2.getConstructor(int.class).newInstance(10);
			} catch (NoSuchMethodException | InvocationTargetException e) {
				e.printStackTrace();
			}

			System.out.println("Vrednost atributa objekta kreiranog konstruktorom: " + constructor.a);

		} catch (NoSuchFieldException | SecurityException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}
	
}

class TestKlasa {
	private int a = 9;
	public String b = "hello";
}

class TestKlasaSaKonstruktorom {
	public int a;

	public TestKlasaSaKonstruktorom(int a){
		this.a = a;
	}
}
