package com.elementars.eclient.util;

import java.util.ArrayList;

public class ColorTextUtils {
   // $FF: synthetic field
   public static ArrayList colors;

   public static String getColor(String var0) {
      String var1;
      if (var0.equalsIgnoreCase("White")) {
         var1 = "&f";
      } else if (var0.equalsIgnoreCase("Red")) {
         var1 = "&4";
      } else if (var0.equalsIgnoreCase("Blue")) {
         var1 = "&1";
      } else if (var0.equalsIgnoreCase("Cyan")) {
         var1 = "&3";
      } else if (var0.equalsIgnoreCase("Pink")) {
         var1 = "&d";
      } else if (var0.equalsIgnoreCase("Black")) {
         var1 = "&0";
      } else if (var0.equalsIgnoreCase("Green")) {
         var1 = "&2";
      } else if (var0.equalsIgnoreCase("Purple")) {
         var1 = "&5";
      } else if (var0.equalsIgnoreCase("Yellow")) {
         var1 = "&e";
      } else if (var0.equalsIgnoreCase("LightRed")) {
         var1 = "&c";
      } else if (var0.equalsIgnoreCase("LightBlue")) {
         var1 = "&b";
      } else if (var0.equalsIgnoreCase("LightGreen")) {
         var1 = "&a";
      } else if (var0.equalsIgnoreCase("Gold")) {
         var1 = "&6";
      } else if (var0.equalsIgnoreCase("Gray")) {
         var1 = "&8";
      } else if (var0.equalsIgnoreCase("Lavender")) {
         var1 = "&9";
      } else if (var0.equalsIgnoreCase("LightGray")) {
         var1 = "&7";
      } else {
         var1 = "&r";
      }

      return var1;
   }

   public static void initColors() {
      colors = new ArrayList();
      colors.add("White");
      colors.add("Black");
      colors.add("Blue");
      colors.add("Green");
      colors.add("Cyan");
      colors.add("Red");
      colors.add("Purple");
      colors.add("Gold");
      colors.add("LightGray");
      colors.add("Gray");
      colors.add("Lavender");
      colors.add("LightGreen");
      colors.add("LightBlue");
      colors.add("LightRed");
      colors.add("Pink");
      colors.add("Yellow");
   }
}
