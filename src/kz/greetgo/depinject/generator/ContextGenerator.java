package kz.greetgo.depinject.generator;

import kz.greetgo.depinject.Bean;
import kz.greetgo.depinject.BeanConfig;
import kz.greetgo.depinject.BeanScanner;
import kz.greetgo.depinject.Include;
import org.reflections.Reflections;
import org.reflections.scanners.ResourcesScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;

import java.io.PrintStream;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class ContextGenerator {

  private static class BeanDefinition {
    final Class<?> beanClass;

    Method beanCreatorMethod;
    Class<?> beanClassForCreatorMethod;

    public BeanDefinition(Class<?> beanClass) {
      this.beanClass = beanClass;
    }

    @Override
    public String toString() {
      if (beanCreatorMethod == null)
        return "BeanDefinition CLASS -> " + beanClass.getSimpleName();
      return "BeanDefinition METHOD "
        + beanCreatorMethod.getName() + " -> " + beanClassForCreatorMethod.getSimpleName();
    }
  }


  public static void generateContextClass(Class<?> contextInterface, PrintStream to) {
    Include include = contextInterface.getAnnotation(Include.class);
    if (include == null) throw new RuntimeException("No annotation Include in " + contextInterface);

    Map<Class<?>, BeanDefinition> map = new HashMap<>();

    includeDefinitions(map, include);

    Collection<BeanDefinition> values = map.values();
    for (BeanDefinition value : values) {
      System.out.println(value);
    }
  }

  private static void includeDefinitions(Map<Class<?>, BeanDefinition> map, Include include) {
    for (Class<?> configClass : include.value()) {
      appendDefinitions(map, configClass);
    }
  }

  private static void appendDefinitions(Map<Class<?>, BeanDefinition> map, Class<?> configClass) {
    BeanConfig configAnn = configClass.getAnnotation(BeanConfig.class);
    if (configAnn == null) throw new RuntimeException("No BeanConfig in " + configClass);

    {
      Include include = configClass.getAnnotation(Include.class);
      if (include != null) includeDefinitions(map, include);
    }

    BeanScanner beanScanner = configClass.getAnnotation(BeanScanner.class);
    if (beanScanner != null) {
      scanAndAppendBeans(map, configClass);
    }
  }

  private static void scanAndAppendBeans(Map<Class<?>, BeanDefinition> map, Class<?> configClass) {
    Reflections reflections = new Reflections(new ConfigurationBuilder()
      .setScanners(new SubTypesScanner(false), new ResourcesScanner())
      .setUrls(ClasspathHelper.forClassLoader(configClass.getClassLoader()))
      .filterInputsBy(new FilterBuilder().include(FilterBuilder.prefix(configClass.getPackage().getName()))));

    for (Class<?> aClass : reflections.getSubTypesOf(Object.class)) {
      Bean bean = aClass.getAnnotation(Bean.class);
      if (bean == null) continue;

      if (map.containsKey(aClass)) throw new RuntimeException("Already selected for Bean in class " + aClass);

      map.put(aClass, new BeanDefinition(aClass));

      for (Method method : aClass.getMethods()) {
        Bean methodBean = method.getDeclaredAnnotation(Bean.class);
        if (methodBean == null) continue;

        if (method.getParameterCount() > 0) throw new RuntimeException("Contains parameters the method " + method);

        Class<?> beanClass = method.getReturnType();
        if (map.containsKey(beanClass)) throw new RuntimeException("Already selected for Bean in method: " + method);

        BeanDefinition beanDefinition = new BeanDefinition(beanClass);
        beanDefinition.beanCreatorMethod = method;
        beanDefinition.beanClassForCreatorMethod = aClass;
        map.put(beanClass, beanDefinition);
      }
    }
  }

}
