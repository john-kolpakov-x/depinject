package kz.greetgo.depinject;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Этой аннотацией помечают метод, который вызывается после встраивания в созданный объект всех инъекций, перед
 * его использованием
 */
@Target({METHOD})
@Retention(RUNTIME)
public @interface AfterInjectionComplete {
}
