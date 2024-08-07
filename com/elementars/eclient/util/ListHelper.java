package com.elementars.eclient.util;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import javax.annotation.Nonnull;

public class ListHelper {
   public static String longest(@Nonnull String[] var0) {
      Objects.requireNonNull(var0);
      List var1 = Arrays.asList(var0);
      if (var1.isEmpty()) {
         return null;
      } else {
         String var2 = (String)var1.get(0);
         String[] var3 = var0;
         int var4 = var0.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            String var6 = var3[var5];
            if (var6.length() > var2.length()) {
               var2 = var6;
            }
         }

         return var2;
      }
   }

   public static String longest(@Nonnull List var0) {
      Objects.requireNonNull(var0);
      if (var0.isEmpty()) {
         return null;
      } else {
         String var1 = (String)var0.get(0);
         Iterator var2 = var0.iterator();

         while(var2.hasNext()) {
            String var3 = (String)var2.next();
            if (var3.length() > var1.length()) {
               var1 = var3;
            }
         }

         return var1;
      }
   }
}
