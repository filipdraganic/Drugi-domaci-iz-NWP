package anotacije.definicije;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
/**
 * Samo metode se mogu anotirati ovom anotacijom.
 */
@Target(ElementType.METHOD)
public @interface MethodInfo {
	/**
	 * Atribut anotacije moze imati podrazumevanu vrednost
	 */
	boolean depricated() default true;

	String version();
}