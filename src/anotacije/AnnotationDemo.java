package anotacije;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;

import anotacije.definicije.AttributeInfo;
import anotacije.definicije.ClassInfo;
import anotacije.definicije.MethodInfo;

@ClassInfo(author = "Pera", version = "1.2.3")
public class AnnotationDemo {

	@AttributeInfo("Ovo je neki atribut")
	int att = 1;

	@MethodInfo(version = "1.0")
	public void f() {
		System.out.println("Ovo je metoda f().");
	}

//	@SuppressWarnings({ "unchecked", "rawtypes" })
//	public static void main(String[] args) {
//		try {
//			Class cl = Class.forName("anotacije.AnnotationDemo");
//			ClassInfo ci = (ClassInfo) cl.getAnnotation(ClassInfo.class);
//			System.out.println("Anotacija ove klase: " + ci);
//			System.out.println("Autor: " + ci.author());
//			System.out.println("Verzija: " + ci.version());
//
//			Method m = cl.getDeclaredMethod("f");
//			MethodInfo mi = m.getAnnotation(MethodInfo.class);
//			System.out.println("Anotacija metode f(): " + mi);
//			System.out.println("Depricated: " + mi.depricated());
//			System.out.println("Version: " + mi.version());
//
//			Field fl = cl.getDeclaredField("att");
//			AttributeInfo ai = fl.getAnnotation(AttributeInfo.class);
//			System.out.println("Anotacija atributa att: " + ai);
//			System.out.println("value: " + ai.value());
//
//			Class child = Class.forName("anotacije.Child");
//			ClassInfo cci = (ClassInfo) child.getAnnotation(ClassInfo.class);
//			System.out.println("Anotacija child klase: " + cci);
//			System.out.println("Autor child klase: " + cci.author());
//			System.out.println("Verzija child klase: " + cci.version());
//
//			System.out.println(Arrays.toString(Package.getPackages()));
//
//		} catch (Exception ex) {
//			ex.printStackTrace();
//		}
//	}
}
