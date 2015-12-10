package kz.greetgo.depinject;

/**
 * Посредством этого интерфейса происходит связывание объекта
 *
 * @param <BeanClass> класс бина для связывания
 */
public interface BeanGetter<BeanClass> {
  BeanClass get();
}
