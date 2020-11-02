package anotacije.definicije;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
/**
 * Samo klase se mogu anotiarti ovom anotacijom
 */
@Target(ElementType.TYPE)
/**
 * Oznacava da se anotacija automatski naledjuje
 */
@Inherited
public @interface ClassInfo {
	String author();

	String version();
}