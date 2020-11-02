package anotacije.definicije;

import java.lang.annotation.*;

/**
 * Anotacija je dostupna runtime
 */
@Retention(RetentionPolicy.RUNTIME)
/**
 * Mozemo anotirati samo atribute ovom anotacijom
 */
@Target(ElementType.FIELD)
public @interface AttributeInfo {

	/**
	 * Kada anotacija ima samo jedan parametar, nije neophodno navesti ga u konstruktoru.
	 * Podrazumevani parametar se naziva value(). Moze biti bilo kog tipa.
	 *
	 * @return
	 */
	String value();
}