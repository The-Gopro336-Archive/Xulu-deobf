package org.newdawn.slick.util.xml;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import org.newdawn.slick.util.Log;
import org.newdawn.slick.util.ResourceLoader;

public class ObjectTreeParser {
   // $FF: synthetic field
   private String addMethod = "add";
   // $FF: synthetic field
   private ArrayList ignored = new ArrayList();
   // $FF: synthetic field
   private String defaultPackage;
   // $FF: synthetic field
   private HashMap nameToClass = new HashMap();

   public void addIgnoredElement(String var1) {
      this.ignored.add(var1);
   }

   private Object traverse(XMLElement var1, Object var2) throws SlickXMLException {
      String var3 = var1.getName();
      if (this.ignored.contains(var3)) {
         return null;
      } else {
         Class var4;
         if (var2 == null) {
            var4 = this.getClassForElementName(var3);
         } else {
            var4 = var2.getClass();
         }

         if (var4 == null) {
            throw new SlickXMLException(String.valueOf((new StringBuilder()).append("Unable to map element ").append(var3).append(" to a class, define the mapping")));
         } else {
            try {
               if (var2 == null) {
                  var2 = var4.newInstance();
                  Method var5 = this.getMethod(var4, "setXMLElementName", new Class[]{String.class});
                  if (var5 != null) {
                     this.invoke(var5, var2, new Object[]{var3});
                  }

                  Method var6 = this.getMethod(var4, "setXMLElementContent", new Class[]{String.class});
                  if (var6 != null) {
                     this.invoke(var6, var2, new Object[]{var1.getContent()});
                  }
               }

               String[] var14 = var1.getAttributeNames();

               String var10;
               for(int var15 = 0; var15 < var14.length; ++var15) {
                  String var7 = String.valueOf((new StringBuilder()).append("set").append(var14[var15]));
                  Method var8 = this.findMethod(var4, var7);
                  if (var8 == null) {
                     Field var9 = this.findField(var4, var14[var15]);
                     if (var9 != null) {
                        var10 = var1.getAttribute(var14[var15]);
                        Object var11 = this.typeValue(var10, var9.getType());
                        this.setField(var9, var2, var11);
                     } else {
                        Log.info(String.valueOf((new StringBuilder()).append("Unable to find property on: ").append(var4).append(" for attribute: ").append(var14[var15])));
                     }
                  } else {
                     String var19 = var1.getAttribute(var14[var15]);
                     Object var21 = this.typeValue(var19, var8.getParameterTypes()[0]);
                     this.invoke(var8, var2, new Object[]{var21});
                  }
               }

               XMLElementList var16 = var1.getChildren();

               for(int var17 = 0; var17 < var16.size(); ++var17) {
                  XMLElement var18 = var16.get(var17);
                  Object var20 = this.traverse(var18);
                  if (var20 != null) {
                     var10 = this.addMethod;
                     Method var22 = this.findMethod(var4, var10, var20.getClass());
                     if (var22 == null) {
                        Log.info(String.valueOf((new StringBuilder()).append("Unable to find method to add: ").append(var20).append(" to ").append(var4)));
                     } else {
                        this.invoke(var22, var2, new Object[]{var20});
                     }
                  }
               }

               return var2;
            } catch (InstantiationException var12) {
               throw new SlickXMLException(String.valueOf((new StringBuilder()).append("Unable to instance ").append(var4).append(" for element ").append(var3).append(", no zero parameter constructor?")), var12);
            } catch (IllegalAccessException var13) {
               throw new SlickXMLException(String.valueOf((new StringBuilder()).append("Unable to instance ").append(var4).append(" for element ").append(var3).append(", no zero parameter constructor?")), var13);
            }
         }
      }
   }

   private Class getClassForElementName(String var1) {
      Class var2 = (Class)this.nameToClass.get(var1);
      if (var2 != null) {
         return var2;
      } else {
         if (this.defaultPackage != null) {
            try {
               return Class.forName(String.valueOf((new StringBuilder()).append(this.defaultPackage).append(".").append(var1)));
            } catch (ClassNotFoundException var4) {
            }
         }

         return null;
      }
   }

   private Method findMethod(Class var1, String var2) {
      Method[] var3 = var1.getDeclaredMethods();

      for(int var4 = 0; var4 < var3.length; ++var4) {
         if (var3[var4].getName().equalsIgnoreCase(var2)) {
            Method var5 = var3[var4];
            Class[] var6 = var5.getParameterTypes();
            if (var6.length == 1) {
               return var5;
            }
         }
      }

      return null;
   }

   public Object parseOnto(String var1, Object var2) throws SlickXMLException {
      return this.parseOnto(var1, ResourceLoader.getResourceAsStream(var1), var2);
   }

   public void setAddMethodName(String var1) {
      this.addMethod = var1;
   }

   private Field findField(Class var1, String var2) {
      Field[] var3 = var1.getDeclaredFields();

      for(int var4 = 0; var4 < var3.length; ++var4) {
         if (var3[var4].getName().equalsIgnoreCase(var2)) {
            if (var3[var4].getType().isPrimitive()) {
               return var3[var4];
            }

            if (var3[var4].getType() == String.class) {
               return var3[var4];
            }
         }
      }

      return null;
   }

   public ObjectTreeParser(String var1) {
      this.defaultPackage = var1;
   }

   private void invoke(Method var1, Object var2, Object[] var3) throws SlickXMLException {
      try {
         var1.setAccessible(true);
         var1.invoke(var2, var3);
      } catch (IllegalArgumentException var10) {
         throw new SlickXMLException(String.valueOf((new StringBuilder()).append("Failed to invoke: ").append(var1).append(" for an XML attribute, is it valid?")), var10);
      } catch (IllegalAccessException var11) {
         throw new SlickXMLException(String.valueOf((new StringBuilder()).append("Failed to invoke: ").append(var1).append(" for an XML attribute, is it valid?")), var11);
      } catch (InvocationTargetException var12) {
         throw new SlickXMLException(String.valueOf((new StringBuilder()).append("Failed to invoke: ").append(var1).append(" for an XML attribute, is it valid?")), var12);
      } finally {
         var1.setAccessible(false);
      }

   }

   public Object parse(String var1, InputStream var2) throws SlickXMLException {
      XMLParser var3 = new XMLParser();
      XMLElement var4 = var3.parse(var1, var2);
      return this.traverse(var4);
   }

   public Object parseOnto(String var1, InputStream var2, Object var3) throws SlickXMLException {
      XMLParser var4 = new XMLParser();
      XMLElement var5 = var4.parse(var1, var2);
      return this.traverse(var5, var3);
   }

   private Method findMethod(Class var1, String var2, Class var3) {
      Method[] var4 = var1.getDeclaredMethods();

      for(int var5 = 0; var5 < var4.length; ++var5) {
         if (var4[var5].getName().equalsIgnoreCase(var2)) {
            Method var6 = var4[var5];
            Class[] var7 = var6.getParameterTypes();
            if (var7.length == 1 && var6.getParameterTypes()[0].isAssignableFrom(var3)) {
               return var6;
            }
         }
      }

      return null;
   }

   private Object traverse(XMLElement var1) throws SlickXMLException {
      return this.traverse(var1, (Object)null);
   }

   public void setDefaultPackage(String var1) {
      this.defaultPackage = var1;
   }

   private Object typeValue(String var1, Class var2) throws SlickXMLException {
      if (var2 == String.class) {
         return var1;
      } else {
         try {
            var2 = this.mapPrimitive(var2);
            return var2.getConstructor(String.class).newInstance(var1);
         } catch (Exception var4) {
            throw new SlickXMLException(String.valueOf((new StringBuilder()).append("Failed to convert: ").append(var1).append(" to the expected primitive type: ").append(var2)), var4);
         }
      }
   }

   public Object parse(String var1) throws SlickXMLException {
      return this.parse(var1, ResourceLoader.getResourceAsStream(var1));
   }

   public ObjectTreeParser() {
   }

   private void setField(Field var1, Object var2, Object var3) throws SlickXMLException {
      try {
         var1.setAccessible(true);
         var1.set(var2, var3);
      } catch (IllegalArgumentException var9) {
         throw new SlickXMLException(String.valueOf((new StringBuilder()).append("Failed to set: ").append(var1).append(" for an XML attribute, is it valid?")), var9);
      } catch (IllegalAccessException var10) {
         throw new SlickXMLException(String.valueOf((new StringBuilder()).append("Failed to set: ").append(var1).append(" for an XML attribute, is it valid?")), var10);
      } finally {
         var1.setAccessible(false);
      }

   }

   private Method getMethod(Class var1, String var2, Class[] var3) {
      try {
         return var1.getMethod(var2, var3);
      } catch (SecurityException var6) {
         return null;
      } catch (NoSuchMethodException var7) {
         return null;
      }
   }

   private Class mapPrimitive(Class var1) {
      if (var1 == Integer.TYPE) {
         return Integer.class;
      } else if (var1 == Double.TYPE) {
         return Double.class;
      } else if (var1 == Float.TYPE) {
         return Float.class;
      } else if (var1 == Boolean.TYPE) {
         return Boolean.class;
      } else if (var1 == Long.TYPE) {
         return Long.class;
      } else {
         throw new RuntimeException(String.valueOf((new StringBuilder()).append("Unsupported primitive: ").append(var1)));
      }
   }

   public void addElementMapping(String var1, Class var2) {
      this.nameToClass.put(var1, var2);
   }
}
