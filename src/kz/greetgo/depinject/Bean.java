package kz.greetgo.depinject;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Этой аннотацией помечают класс, чтобы он стал бином, или метод, который возвращает бин
 */
@Target({METHOD, TYPE})
@Retention(RUNTIME)
public @interface Bean {
}
