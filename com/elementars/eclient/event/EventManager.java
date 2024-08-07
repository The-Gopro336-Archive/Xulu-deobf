package com.elementars.eclient.event;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class EventManager {
   // $FF: synthetic field
   private Map REGISTRY_MAP = new HashMap();

   public void unregister(Object var1, Class var2) {
      if (this.REGISTRY_MAP.containsKey(var2)) {
         Iterator var3 = ((ArrayHelper)this.REGISTRY_MAP.get(var2)).iterator();

         while(var3.hasNext()) {
            Data var4 = (Data)var3.next();
            if (var4.source.equals(var1)) {
               ((ArrayHelper)this.REGISTRY_MAP.get(var2)).remove(var4);
            }
         }

         this.cleanMap(true);
      }

   }

   public void register(Object var1) {
      Method[] var2 = var1.getClass().getDeclaredMethods();
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         Method var5 = var2[var4];
         if (!this.isMethodBad(var5)) {
            this.register(var5, var1);
         }
      }

   }

   public ArrayHelper get(Class var1) {
      return (ArrayHelper)this.REGISTRY_MAP.get(var1);
   }

   private void sortListValue(Class var1) {
      ArrayHelper var2 = new ArrayHelper();
      byte[] var3 = Priority.VALUE_ARRAY;
      int var4 = var3.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         byte var6 = var3[var5];
         Iterator var7 = ((ArrayHelper)this.REGISTRY_MAP.get(var1)).iterator();

         while(var7.hasNext()) {
            Data var8 = (Data)var7.next();
            if (var8.priority == var6) {
               var2.add(var8);
            }
         }
      }

      this.REGISTRY_MAP.put(var1, var2);
   }

   public void register(Object var1, Class var2) {
      Method[] var3 = var1.getClass().getDeclaredMethods();
      int var4 = var3.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         Method var6 = var3[var5];
         if (!this.isMethodBad(var6, var2)) {
            this.register(var6, var1);
         }
      }

   }

   public void unregister(Object var1) {
      Iterator var2 = this.REGISTRY_MAP.values().iterator();

      while(var2.hasNext()) {
         ArrayHelper var3 = (ArrayHelper)var2.next();
         Iterator var4 = var3.iterator();

         while(var4.hasNext()) {
            Data var5 = (Data)var4.next();
            if (var5.source.equals(var1)) {
               var3.remove(var5);
            }
         }
      }

      this.cleanMap(true);
   }

   private void register(Method var1, Object var2) {
      Class var3 = var1.getParameterTypes()[0];
      final Data var4 = new Data(var2, var1, ((EventTarget)var1.getAnnotation(EventTarget.class)).value());
      if (!var4.target.isAccessible()) {
         var4.target.setAccessible(true);
      }

      if (this.REGISTRY_MAP.containsKey(var3)) {
         if (!((ArrayHelper)this.REGISTRY_MAP.get(var3)).contains(var4)) {
            ((ArrayHelper)this.REGISTRY_MAP.get(var3)).add(var4);
            this.sortListValue(var3);
         }
      } else {
         this.REGISTRY_MAP.put(var3, new ArrayHelper() {
            {
               this.add(var4);
            }
         });
      }

   }

   public void removeEnty(Class var1) {
      Iterator var2 = this.REGISTRY_MAP.entrySet().iterator();

      while(var2.hasNext()) {
         if (((Class)((Entry)var2.next()).getKey()).equals(var1)) {
            var2.remove();
            break;
         }
      }

   }

   private boolean isMethodBad(Method var1) {
      return var1.getParameterTypes().length != 1 || !var1.isAnnotationPresent(EventTarget.class);
   }

   private boolean isMethodBad(Method var1, Class var2) {
      return this.isMethodBad(var1) || var1.getParameterTypes()[0].equals(var2);
   }

   public void cleanMap(boolean var1) {
      Iterator var2 = this.REGISTRY_MAP.entrySet().iterator();

      while(true) {
         do {
            if (!var2.hasNext()) {
               return;
            }
         } while(var1 && !((ArrayHelper)((Entry)var2.next()).getValue()).isEmpty());

         var2.remove();
      }
   }

   public void shutdown() {
      this.REGISTRY_MAP.clear();
   }
}
