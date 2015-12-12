package kz.greetgo.depinject.generator.line1;

import kz.greetgo.depinject.Bean;

@Bean
public class LineBeanOne implements LineOneInterface {

  @Override
  public void helloFromLineOne() {
    System.out.println("helloFromLineOne - 111");
  }

}
