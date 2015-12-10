package kz.greetgo.depinject;

/**
 * Этот интерфейс реализуют те бины, которые хотят пропускать через себя только что созданные бины,
 * но ещё не попавшие в контекст
 *
 * @param <BeanInterface> интерфейс бинов, которые будут здесь пропускаться (может быть и классом)
 */
public interface BeanPassing<BeanInterface> {
  BeanInterface passing(BeanInterface bean);
}
