package kz.greetgo.depinject.generator.line2;

import kz.greetgo.depinject.Bean;
import kz.greetgo.depinject.BeanGetter;
import kz.greetgo.depinject.generator.line1.LineOneInterface;

@Bean
public class LineBeanTwo implements LineTwoInterface {

  public BeanGetter<LineOneInterface> lineOneInterface;

  @Override
  public void helloFromLineTwoInterface() {

    System.out.println("helloFromLineTwoInterface   <<---->>   [ <{- ! -}> ] ");

    lineOneInterface.get().helloFromLineOne();

  }
}
