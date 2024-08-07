package com.elementars.eclient.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EnumUtil {
   public static ArrayList enumConverter(Class var0) {
      ArrayList var1 = new ArrayList();
      List var2 = Arrays.asList(var0.getEnumConstants());
      var2.forEach((var1x) -> {
         var1.add(toTitle(var1x.name()));
      });
      return var1;
   }

   public static String toTitle(String var0) {
      var0 = String.valueOf((new StringBuilder()).append(Character.toUpperCase(var0.toLowerCase().charAt(0))).append(var0.toLowerCase().substring(1)));
      return var0;
   }
}
