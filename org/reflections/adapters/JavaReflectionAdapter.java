package org.reflections.adapters;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.reflections.ReflectionUtils;
import org.reflections.util.Utils;
import org.reflections.vfs.Vfs;

public class JavaReflectionAdapter implements MetadataAdapter {
   public List getInterfacesNames(Class var1) {
      Class[] var2 = var1.getInterfaces();
      return var2 != null ? (List)Arrays.stream(var2).map(Class::getName).collect(Collectors.toList()) : Collections.emptyList();
   }

   public String getMethodFullKey(Class var1, Member var2) {
      return String.valueOf((new StringBuilder()).append(this.getClassName(var1)).append(".").append(this.getMethodKey(var1, var2)));
   }

   public boolean acceptsInput(String var1) {
      return var1.endsWith(".class");
   }

   public String getMethodName(Member var1) {
      return var1 instanceof Method ? var1.getName() : (var1 instanceof Constructor ? "<init>" : null);
   }

   private List getAnnotationNames(Annotation[] var1) {
      return (List)Arrays.stream(var1).map((var0) -> {
         return var0.annotationType().getName();
      }).collect(Collectors.toList());
   }

   public List getParameterNames(Member var1) {
      Class[] var2 = var1 instanceof Method ? ((Method)var1).getParameterTypes() : (var1 instanceof Constructor ? ((Constructor)var1).getParameterTypes() : null);
      return var2 != null ? (List)Arrays.stream(var2).map(JavaReflectionAdapter::getName).collect(Collectors.toList()) : Collections.emptyList();
   }

   public Class getOrCreateClassObject(Vfs.File var1) throws Exception {
      return this.getOrCreateClassObject(var1, (ClassLoader[])null);
   }

   public List getClassAnnotationNames(Class var1) {
      return this.getAnnotationNames(var1.getDeclaredAnnotations());
   }

   public List getMethodAnnotationNames(Member var1) {
      Annotation[] var2 = var1 instanceof Method ? ((Method)var1).getDeclaredAnnotations() : (var1 instanceof Constructor ? ((Constructor)var1).getDeclaredAnnotations() : null);
      return this.getAnnotationNames(var2);
   }

   public static String getName(Class var0) {
      if (var0.isArray()) {
         try {
            Class var1 = var0;

            int var2;
            for(var2 = 0; var1.isArray(); var1 = var1.getComponentType()) {
               ++var2;
            }

            return String.valueOf((new StringBuilder()).append(var1.getName()).append(Utils.repeat("[]", var2)));
         } catch (Throwable var3) {
         }
      }

      return var0.getName();
   }

   public List getFieldAnnotationNames(Field var1) {
      return this.getAnnotationNames(var1.getDeclaredAnnotations());
   }

   public boolean isPublic(Object var1) {
      Integer var2 = var1 instanceof Class ? ((Class)var1).getModifiers() : var1 instanceof Member ? ((Member)var1).getModifiers() : null;
      return var2 != null && Modifier.isPublic(var2);
   }

   public String getMethodModifier(Member var1) {
      return Modifier.toString(var1.getModifiers());
   }

   public String getSuperclassName(Class var1) {
      Class var2 = var1.getSuperclass();
      return var2 != null ? var2.getName() : "";
   }

   public List getFields(Class var1) {
      return Arrays.asList(var1.getDeclaredFields());
   }

   public String getReturnTypeName(Member var1) {
      return ((Method)var1).getReturnType().getName();
   }

   public List getParameterAnnotationNames(Member var1, int var2) {
      Annotation[][] var3 = var1 instanceof Method ? ((Method)var1).getParameterAnnotations() : (var1 instanceof Constructor ? ((Constructor)var1).getParameterAnnotations() : (Annotation[][])null);
      return this.getAnnotationNames(var3 != null ? var3[var2] : null);
   }

   public Class getOrCreateClassObject(Vfs.File var1, ClassLoader... var2) throws Exception {
      String var3 = var1.getRelativePath().replace("/", ".").replace(".class", "");
      return ReflectionUtils.forName(var3, var2);
   }

   public List getMethods(Class var1) {
      ArrayList var2 = new ArrayList();
      var2.addAll(Arrays.asList(var1.getDeclaredMethods()));
      var2.addAll(Arrays.asList(var1.getDeclaredConstructors()));
      return var2;
   }

   public String getMethodKey(Class var1, Member var2) {
      return String.valueOf((new StringBuilder()).append(this.getMethodName(var2)).append("(").append(Utils.join(this.getParameterNames(var2), ", ")).append(")"));
   }

   public String getClassName(Class var1) {
      return var1.getName();
   }

   public String getFieldName(Field var1) {
      return var1.getName();
   }
}
