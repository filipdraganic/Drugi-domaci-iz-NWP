package anotacije.definicije;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)

@Target(ElementType.TYPE)
@Bean(scope="prototype")
public @interface Component  {


}
