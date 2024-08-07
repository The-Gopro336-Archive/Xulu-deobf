package org.newdawn.slick.util;

import org.newdawn.slick.Color;
import org.newdawn.slick.Font;

public class FontUtils {
   public static void drawCenter(Font var0, String var1, int var2, int var3, int var4) {
      drawString(var0, var1, 2, var2, var3, var4, Color.white);
   }

   public static void drawLeft(Font var0, String var1, int var2, int var3) {
      drawString(var0, var1, 1, var2, var3, 0, Color.white);
   }

   public static void drawRight(Font var0, String var1, int var2, int var3, int var4, Color var5) {
      drawString(var0, var1, 3, var2, var3, var4, var5);
   }

   private static int drawJustifiedSpaceSeparatedSubstrings(Font var0, String var1, int var2, int var3, int var4) {
      int var5 = 0;
      boolean var6 = false;

      int var7;
      int var9;
      for(var7 = var2; var5 < var1.length(); var5 = var9 + 1) {
         var9 = var1.indexOf(32, var5);
         if (var9 == -1) {
            var9 = var1.length();
         }

         String var8 = var1.substring(var5, var9);
         var0.drawString((float)var7, (float)var3, var8);
         var7 += var0.getWidth(var8) + var4;
      }

      return var7;
   }

   public static void drawCenter(Font var0, String var1, int var2, int var3, int var4, Color var5) {
      drawString(var0, var1, 2, var2, var3, var4, var5);
   }

   private static int calculateWidthOfJustifiedSpaceInPixels(Font var0, String var1, int var2) {
      int var3 = 0;
      int var4 = 0;

      while(var4 < var1.length()) {
         if (var1.charAt(var4++) == ' ') {
            ++var3;
         }
      }

      if (var3 > 0) {
         var3 = (var2 + var0.getWidth(" ") * var3) / var3;
      }

      return var3;
   }

   public static void drawRight(Font var0, String var1, int var2, int var3, int var4) {
      drawString(var0, var1, 3, var2, var3, var4, Color.white);
   }

   public static final int drawString(Font var0, String var1, int var2, int var3, int var4, int var5, Color var6) {
      byte var7 = 0;
      if (var2 == 1) {
         var0.drawString((float)var3, (float)var4, var1, var6);
      } else if (var2 == 2) {
         var0.drawString((float)(var3 + var5 / 2 - var0.getWidth(var1) / 2), (float)var4, var1, var6);
      } else if (var2 == 3) {
         var0.drawString((float)(var3 + var5 - var0.getWidth(var1)), (float)var4, var1, var6);
      } else if (var2 == 4) {
         int var8 = var5 - var0.getWidth(var1);
         if (var8 <= 0) {
            var0.drawString((float)var3, (float)var4, var1, var6);
         }

         return drawJustifiedSpaceSeparatedSubstrings(var0, var1, var3, var4, calculateWidthOfJustifiedSpaceInPixels(var0, var1, var8));
      }

      return var7;
   }

   public class Alignment {
      // $FF: synthetic field
      public static final int CENTER = 2;
      // $FF: synthetic field
      public static final int JUSTIFY = 4;
      // $FF: synthetic field
      public static final int RIGHT = 3;
      // $FF: synthetic field
      public static final int LEFT = 1;
   }
}
