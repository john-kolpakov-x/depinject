package kz.greetgo.depinject;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Этой аннотацией помечают класс-конфиг, с которого начинается поиск бинов в пакете рекурсивно внутрь
 */
@Target({METHOD, TYPE})
@Retention(RUNTIME)
public @interface BeanScanner {
}
