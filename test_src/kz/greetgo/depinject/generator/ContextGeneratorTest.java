package kz.greetgo.depinject.generator;


import kz.greetgo.depinject.BeanConfig;
import kz.greetgo.depinject.Include;
import kz.greetgo.depinject.generator.line1.Line1Config;
import kz.greetgo.depinject.generator.line2.Line2Config;
import kz.greetgo.depinject.generator.line2.LineTwoInterface;
import org.testng.annotations.Test;

public class ContextGeneratorTest {

  @BeanConfig
  @Include({Line1Config.class, Line2Config.class})
  private static class Config1 {
  }

  @Include(Config1.class)
  private interface LocalContext {
    LineTwoInterface getLineTwoInterface();
  }

  @Test
  public void generateContextClass() throws Exception {
    ContextGenerator.generateContextClass(LocalContext.class, System.out);
  }
}
