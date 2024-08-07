package org.reflections.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.reflections.ReflectionUtils;
import org.reflections.Reflections;
import org.reflections.ReflectionsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class Utils {
   public static List names(Collection var0) {
      return (List)var0.stream().map(Utils::name).collect(Collectors.toList());
   }

   public static String join(Collection var0, String var1) {
      return (String)var0.stream().map(Object::toString).collect(Collectors.joining(var1));
   }

   public static Set filter(Collection var0, Predicate... var1) {
      return (Set)var0.stream().filter(and(var1)).collect(Collectors.toSet());
   }

   public static void close(InputStream var0) {
      try {
         if (var0 != null) {
            var0.close();
         }
      } catch (IOException var2) {
         if (Reflections.log != null) {
            Reflections.log.warn("Could not close InputStream", var2);
         }
      }

   }

   public static Logger findLogger(Class var0) {
      try {
         Class.forName("org.slf4j.impl.StaticLoggerBinder");
         return LoggerFactory.getLogger(var0);
      } catch (Throwable var2) {
         return null;
      }
   }

   public static String repeat(String var0, int var1) {
      return (String)IntStream.range(0, var1).mapToObj((var1x) -> {
         return var0;
      }).collect(Collectors.joining());
   }

   public static Field getFieldFromString(String var0, ClassLoader... var1) {
      String var2 = var0.substring(0, var0.lastIndexOf(46));
      String var3 = var0.substring(var0.lastIndexOf(46) + 1);

      try {
         return ReflectionUtils.forName(var2, var1).getDeclaredField(var3);
      } catch (NoSuchFieldException var6) {
         throw new ReflectionsException(String.valueOf((new StringBuilder()).append("Can't resolve field named ").append(var3)), var6);
      }
   }

   public static Member getMemberFromDescriptor(String var0, ClassLoader... var1) throws ReflectionsException {
      int var2 = var0.lastIndexOf(40);
      String var3 = var2 != -1 ? var0.substring(0, var2) : var0;
      String var4 = var2 != -1 ? var0.substring(var2 + 1, var0.lastIndexOf(41)) : "";
      int var5 = Math.max(var3.lastIndexOf(46), var3.lastIndexOf("$"));
      String var6 = var3.substring(var3.lastIndexOf(32) + 1, var5);
      String var7 = var3.substring(var5 + 1);
      Class[] var8 = null;
      if (!isEmpty(var4)) {
         String[] var9 = var4.split(",");
         var8 = (Class[])Arrays.stream(var9).map((var1x) -> {
            return ReflectionUtils.forName(var1x.trim(), var1);
         }).toArray((var0x) -> {
            return new Class[var0x];
         });
      }

      Class var12 = ReflectionUtils.forName(var6, var1);

      while(var12 != null) {
         try {
            if (var0.contains("(")) {
               if (!isConstructor(var0)) {
                  return var12.isInterface() ? var12.getMethod(var7, var8) : var12.getDeclaredMethod(var7, var8);
               }

               return var12.isInterface() ? var12.getConstructor(var8) : var12.getDeclaredConstructor(var8);
            }

            return var12.isInterface() ? var12.getField(var7) : var12.getDeclaredField(var7);
         } catch (Exception var11) {
            var12 = var12.getSuperclass();
         }
      }

      throw new ReflectionsException(String.valueOf((new StringBuilder()).append("Can't resolve member named ").append(var7).append(" for class ").append(var6)));
   }

   public static String name(Class var0) {
      if (!var0.isArray()) {
         return var0.getName();
      } else {
         int var1;
         for(var1 = 0; var0.isArray(); var0 = var0.getComponentType()) {
            ++var1;
         }

         return String.valueOf((new StringBuilder()).append(var0.getName()).append(repeat("[]", var1)));
      }
   }

   public static boolean isConstructor(String var0) {
      return var0.contains("init>");
   }

   public static String name(Method var0) {
      return String.valueOf((new StringBuilder()).append(var0.getDeclaringClass().getName()).append(".").append(var0.getName()).append("(").append(join(names(var0.getParameterTypes()), ", ")).append(")"));
   }

   public static String name(Field var0) {
      return String.valueOf((new StringBuilder()).append(var0.getDeclaringClass().getName()).append(".").append(var0.getName()));
   }

   public static String name(Constructor var0) {
      return String.valueOf((new StringBuilder()).append(var0.getName()).append(".<init>(").append(join(names(var0.getParameterTypes()), ", ")).append(")"));
   }

   public static Set filter(Collection var0, Predicate var1) {
      return (Set)var0.stream().filter(var1).collect(Collectors.toSet());
   }

   public static Set getConstructorsFromDescriptors(Iterable var0, ClassLoader... var1) {
      HashSet var2 = new HashSet();
      Iterator var3 = var0.iterator();

      while(var3.hasNext()) {
         String var4 = (String)var3.next();
         if (isConstructor(var4)) {
            Constructor var5 = (Constructor)getMemberFromDescriptor(var4, var1);
            if (var5 != null) {
               var2.add(var5);
            }
         }
      }

      return var2;
   }

   public static String index(Class var0) {
      return var0.getSimpleName();
   }

   public static boolean isEmpty(String var0) {
      return var0 == null || var0.length() == 0;
   }

   public static Set getMembersFromDescriptors(Iterable var0, ClassLoader... var1) {
      HashSet var2 = new HashSet();
      Iterator var3 = var0.iterator();

      while(var3.hasNext()) {
         String var4 = (String)var3.next();

         try {
            var2.add(getMemberFromDescriptor(var4, var1));
         } catch (ReflectionsException var6) {
            throw new ReflectionsException(String.valueOf((new StringBuilder()).append("Can't resolve member named ").append(var4)), var6);
         }
      }

      return var2;
   }

   public static File prepareFile(String var0) {
      File var1 = new File(var0);
      File var2 = var1.getAbsoluteFile().getParentFile();
      if (!var2.exists()) {
         var2.mkdirs();
      }

      return var1;
   }

   public static Predicate and(Predicate... var0) {
      return (Predicate)Arrays.stream(var0).reduce((var0x) -> {
         return true;
      }, Predicate::and);
   }

   public static List names(Class... var0) {
      return names((Collection)Arrays.asList(var0));
   }

   public static Set getMethodsFromDescriptors(Iterable var0, ClassLoader... var1) {
      HashSet var2 = new HashSet();
      Iterator var3 = var0.iterator();

      while(var3.hasNext()) {
         String var4 = (String)var3.next();
         if (!isConstructor(var4)) {
            Method var5 = (Method)getMemberFromDescriptor(var4, var1);
            if (var5 != null) {
               var2.add(var5);
            }
         }
      }

      return var2;
   }

   public static Set filter(Object[] var0, Predicate... var1) {
      return (Set)Arrays.stream(var0).filter(and(var1)).collect(Collectors.toSet());
   }
}
