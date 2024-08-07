package com.elementars.eclient.util;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;

public class ColorUtils {
   public static int toRGBA(float[] var0) {
      if (var0.length != 4) {
         throw new IllegalArgumentException("colors[] must have a length of 4!");
      } else {
         return toRGBA(var0[0], var0[1], var0[2], var0[3]);
      }
   }

   public static int toRGBA(double[] var0) {
      if (var0.length != 4) {
         throw new IllegalArgumentException("colors[] must have a length of 4!");
      } else {
         return toRGBA((float)var0[0], (float)var0[1], (float)var0[2], (float)var0[3]);
      }
   }

   public static int toRGBA(double var0, double var2, double var4, double var6) {
      return toRGBA((float)var0, (float)var2, (float)var4, (float)var6);
   }

   public int colorToHex(Color var1) {
      return Integer.decode(String.valueOf((new StringBuilder()).append("0x").append(Integer.toHexString(var1.getRGB()).substring(2))));
   }

   public String getColorNameFromHex(int var1) {
      int var2 = (var1 & 16711680) >> 16;
      int var3 = (var1 & '\uff00') >> 8;
      int var4 = var1 & 255;
      return this.getColorNameFromRgb(var2, var3, var4);
   }

   public static int toRGBA(int var0, int var1, int var2, int var3) {
      return (var0 << 16) + (var1 << 8) + (var2 << 0) + (var3 << 24);
   }

   public static int toRGBA(float var0, float var1, float var2, float var3) {
      return toRGBA((int)(var0 * 255.0F), (int)(var1 * 255.0F), (int)(var2 * 255.0F), (int)(var3 * 255.0F));
   }

   public String getColorNameFromColor(Color var1) {
      return this.getColorNameFromRgb(var1.getRed(), var1.getGreen(), var1.getBlue());
   }

   private ArrayList initColorList() {
      ArrayList var1 = new ArrayList();
      var1.add(new ColorUtils.ColorName("AliceBlue", 240, 248, 255));
      var1.add(new ColorUtils.ColorName("AntiqueWhite", 250, 235, 215));
      var1.add(new ColorUtils.ColorName("Aqua", 0, 255, 255));
      var1.add(new ColorUtils.ColorName("Aquamarine", 127, 255, 212));
      var1.add(new ColorUtils.ColorName("Azure", 240, 255, 255));
      var1.add(new ColorUtils.ColorName("Beige", 245, 245, 220));
      var1.add(new ColorUtils.ColorName("Bisque", 255, 228, 196));
      var1.add(new ColorUtils.ColorName("Black", 0, 0, 0));
      var1.add(new ColorUtils.ColorName("BlanchedAlmond", 255, 235, 205));
      var1.add(new ColorUtils.ColorName("Blue", 0, 0, 255));
      var1.add(new ColorUtils.ColorName("BlueViolet", 138, 43, 226));
      var1.add(new ColorUtils.ColorName("Brown", 165, 42, 42));
      var1.add(new ColorUtils.ColorName("BurlyWood", 222, 184, 135));
      var1.add(new ColorUtils.ColorName("CadetBlue", 95, 158, 160));
      var1.add(new ColorUtils.ColorName("Chartreuse", 127, 255, 0));
      var1.add(new ColorUtils.ColorName("Chocolate", 210, 105, 30));
      var1.add(new ColorUtils.ColorName("Coral", 255, 127, 80));
      var1.add(new ColorUtils.ColorName("CornflowerBlue", 100, 149, 237));
      var1.add(new ColorUtils.ColorName("Cornsilk", 255, 248, 220));
      var1.add(new ColorUtils.ColorName("Crimson", 220, 20, 60));
      var1.add(new ColorUtils.ColorName("Cyan", 0, 255, 255));
      var1.add(new ColorUtils.ColorName("DarkBlue", 0, 0, 139));
      var1.add(new ColorUtils.ColorName("DarkCyan", 0, 139, 139));
      var1.add(new ColorUtils.ColorName("DarkGoldenRod", 184, 134, 11));
      var1.add(new ColorUtils.ColorName("DarkGray", 169, 169, 169));
      var1.add(new ColorUtils.ColorName("DarkGreen", 0, 100, 0));
      var1.add(new ColorUtils.ColorName("DarkKhaki", 189, 183, 107));
      var1.add(new ColorUtils.ColorName("DarkMagenta", 139, 0, 139));
      var1.add(new ColorUtils.ColorName("DarkOliveGreen", 85, 107, 47));
      var1.add(new ColorUtils.ColorName("DarkOrange", 255, 140, 0));
      var1.add(new ColorUtils.ColorName("DarkOrchid", 153, 50, 204));
      var1.add(new ColorUtils.ColorName("DarkRed", 139, 0, 0));
      var1.add(new ColorUtils.ColorName("DarkSalmon", 233, 150, 122));
      var1.add(new ColorUtils.ColorName("DarkSeaGreen", 143, 188, 143));
      var1.add(new ColorUtils.ColorName("DarkSlateBlue", 72, 61, 139));
      var1.add(new ColorUtils.ColorName("DarkSlateGray", 47, 79, 79));
      var1.add(new ColorUtils.ColorName("DarkTurquoise", 0, 206, 209));
      var1.add(new ColorUtils.ColorName("DarkViolet", 148, 0, 211));
      var1.add(new ColorUtils.ColorName("DeepPink", 255, 20, 147));
      var1.add(new ColorUtils.ColorName("DeepSkyBlue", 0, 191, 255));
      var1.add(new ColorUtils.ColorName("DimGray", 105, 105, 105));
      var1.add(new ColorUtils.ColorName("DodgerBlue", 30, 144, 255));
      var1.add(new ColorUtils.ColorName("FireBrick", 178, 34, 34));
      var1.add(new ColorUtils.ColorName("FloralWhite", 255, 250, 240));
      var1.add(new ColorUtils.ColorName("ForestGreen", 34, 139, 34));
      var1.add(new ColorUtils.ColorName("Fuchsia", 255, 0, 255));
      var1.add(new ColorUtils.ColorName("Gainsboro", 220, 220, 220));
      var1.add(new ColorUtils.ColorName("GhostWhite", 248, 248, 255));
      var1.add(new ColorUtils.ColorName("Gold", 255, 215, 0));
      var1.add(new ColorUtils.ColorName("GoldenRod", 218, 165, 32));
      var1.add(new ColorUtils.ColorName("Gray", 128, 128, 128));
      var1.add(new ColorUtils.ColorName("Green", 0, 128, 0));
      var1.add(new ColorUtils.ColorName("GreenYellow", 173, 255, 47));
      var1.add(new ColorUtils.ColorName("HoneyDew", 240, 255, 240));
      var1.add(new ColorUtils.ColorName("HotPink", 255, 105, 180));
      var1.add(new ColorUtils.ColorName("IndianRed", 205, 92, 92));
      var1.add(new ColorUtils.ColorName("Indigo", 75, 0, 130));
      var1.add(new ColorUtils.ColorName("Ivory", 255, 255, 240));
      var1.add(new ColorUtils.ColorName("Khaki", 240, 230, 140));
      var1.add(new ColorUtils.ColorName("Lavender", 230, 230, 250));
      var1.add(new ColorUtils.ColorName("LavenderBlush", 255, 240, 245));
      var1.add(new ColorUtils.ColorName("LawnGreen", 124, 252, 0));
      var1.add(new ColorUtils.ColorName("LemonChiffon", 255, 250, 205));
      var1.add(new ColorUtils.ColorName("LightBlue", 173, 216, 230));
      var1.add(new ColorUtils.ColorName("LightCoral", 240, 128, 128));
      var1.add(new ColorUtils.ColorName("LightCyan", 224, 255, 255));
      var1.add(new ColorUtils.ColorName("LightGoldenRodYellow", 250, 250, 210));
      var1.add(new ColorUtils.ColorName("LightGray", 211, 211, 211));
      var1.add(new ColorUtils.ColorName("LightGreen", 144, 238, 144));
      var1.add(new ColorUtils.ColorName("LightPink", 255, 182, 193));
      var1.add(new ColorUtils.ColorName("LightSalmon", 255, 160, 122));
      var1.add(new ColorUtils.ColorName("LightSeaGreen", 32, 178, 170));
      var1.add(new ColorUtils.ColorName("LightSkyBlue", 135, 206, 250));
      var1.add(new ColorUtils.ColorName("LightSlateGray", 119, 136, 153));
      var1.add(new ColorUtils.ColorName("LightSteelBlue", 176, 196, 222));
      var1.add(new ColorUtils.ColorName("LightYellow", 255, 255, 224));
      var1.add(new ColorUtils.ColorName("Lime", 0, 255, 0));
      var1.add(new ColorUtils.ColorName("LimeGreen", 50, 205, 50));
      var1.add(new ColorUtils.ColorName("Linen", 250, 240, 230));
      var1.add(new ColorUtils.ColorName("Magenta", 255, 0, 255));
      var1.add(new ColorUtils.ColorName("Maroon", 128, 0, 0));
      var1.add(new ColorUtils.ColorName("MediumAquaMarine", 102, 205, 170));
      var1.add(new ColorUtils.ColorName("MediumBlue", 0, 0, 205));
      var1.add(new ColorUtils.ColorName("MediumOrchid", 186, 85, 211));
      var1.add(new ColorUtils.ColorName("MediumPurple", 147, 112, 219));
      var1.add(new ColorUtils.ColorName("MediumSeaGreen", 60, 179, 113));
      var1.add(new ColorUtils.ColorName("MediumSlateBlue", 123, 104, 238));
      var1.add(new ColorUtils.ColorName("MediumSpringGreen", 0, 250, 154));
      var1.add(new ColorUtils.ColorName("MediumTurquoise", 72, 209, 204));
      var1.add(new ColorUtils.ColorName("MediumVioletRed", 199, 21, 133));
      var1.add(new ColorUtils.ColorName("MidnightBlue", 25, 25, 112));
      var1.add(new ColorUtils.ColorName("MintCream", 245, 255, 250));
      var1.add(new ColorUtils.ColorName("MistyRose", 255, 228, 225));
      var1.add(new ColorUtils.ColorName("Moccasin", 255, 228, 181));
      var1.add(new ColorUtils.ColorName("NavajoWhite", 255, 222, 173));
      var1.add(new ColorUtils.ColorName("Navy", 0, 0, 128));
      var1.add(new ColorUtils.ColorName("OldLace", 253, 245, 230));
      var1.add(new ColorUtils.ColorName("Olive", 128, 128, 0));
      var1.add(new ColorUtils.ColorName("OliveDrab", 107, 142, 35));
      var1.add(new ColorUtils.ColorName("Orange", 255, 165, 0));
      var1.add(new ColorUtils.ColorName("OrangeRed", 255, 69, 0));
      var1.add(new ColorUtils.ColorName("Orchid", 218, 112, 214));
      var1.add(new ColorUtils.ColorName("PaleGoldenRod", 238, 232, 170));
      var1.add(new ColorUtils.ColorName("PaleGreen", 152, 251, 152));
      var1.add(new ColorUtils.ColorName("PaleTurquoise", 175, 238, 238));
      var1.add(new ColorUtils.ColorName("PaleVioletRed", 219, 112, 147));
      var1.add(new ColorUtils.ColorName("PapayaWhip", 255, 239, 213));
      var1.add(new ColorUtils.ColorName("PeachPuff", 255, 218, 185));
      var1.add(new ColorUtils.ColorName("Peru", 205, 133, 63));
      var1.add(new ColorUtils.ColorName("Pink", 255, 192, 203));
      var1.add(new ColorUtils.ColorName("Plum", 221, 160, 221));
      var1.add(new ColorUtils.ColorName("PowderBlue", 176, 224, 230));
      var1.add(new ColorUtils.ColorName("Purple", 128, 0, 128));
      var1.add(new ColorUtils.ColorName("Red", 255, 0, 0));
      var1.add(new ColorUtils.ColorName("RosyBrown", 188, 143, 143));
      var1.add(new ColorUtils.ColorName("RoyalBlue", 65, 105, 225));
      var1.add(new ColorUtils.ColorName("SaddleBrown", 139, 69, 19));
      var1.add(new ColorUtils.ColorName("Salmon", 250, 128, 114));
      var1.add(new ColorUtils.ColorName("SandyBrown", 244, 164, 96));
      var1.add(new ColorUtils.ColorName("SeaGreen", 46, 139, 87));
      var1.add(new ColorUtils.ColorName("SeaShell", 255, 245, 238));
      var1.add(new ColorUtils.ColorName("Sienna", 160, 82, 45));
      var1.add(new ColorUtils.ColorName("Silver", 192, 192, 192));
      var1.add(new ColorUtils.ColorName("SkyBlue", 135, 206, 235));
      var1.add(new ColorUtils.ColorName("SlateBlue", 106, 90, 205));
      var1.add(new ColorUtils.ColorName("SlateGray", 112, 128, 144));
      var1.add(new ColorUtils.ColorName("Snow", 255, 250, 250));
      var1.add(new ColorUtils.ColorName("SpringGreen", 0, 255, 127));
      var1.add(new ColorUtils.ColorName("SteelBlue", 70, 130, 180));
      var1.add(new ColorUtils.ColorName("Tan", 210, 180, 140));
      var1.add(new ColorUtils.ColorName("Teal", 0, 128, 128));
      var1.add(new ColorUtils.ColorName("Thistle", 216, 191, 216));
      var1.add(new ColorUtils.ColorName("Tomato", 255, 99, 71));
      var1.add(new ColorUtils.ColorName("Turquoise", 64, 224, 208));
      var1.add(new ColorUtils.ColorName("Violet", 238, 130, 238));
      var1.add(new ColorUtils.ColorName("Wheat", 245, 222, 179));
      var1.add(new ColorUtils.ColorName("White", 255, 255, 255));
      var1.add(new ColorUtils.ColorName("WhiteSmoke", 245, 245, 245));
      var1.add(new ColorUtils.ColorName("Yellow", 255, 255, 0));
      var1.add(new ColorUtils.ColorName("YellowGreen", 154, 205, 50));
      return var1;
   }

   public static final int changeAlpha(int var0, int var1) {
      var0 &= 16777215;
      return var1 << 24 | var0;
   }

   public String getColorNameFromRgb(int var1, int var2, int var3) {
      ArrayList var4 = this.initColorList();
      ColorUtils.ColorName var5 = null;
      int var6 = Integer.MAX_VALUE;
      Iterator var8 = var4.iterator();

      while(var8.hasNext()) {
         ColorUtils.ColorName var9 = (ColorUtils.ColorName)var8.next();
         int var7 = var9.computeMSE(var1, var2, var3);
         if (var7 < var6) {
            var6 = var7;
            var5 = var9;
         }
      }

      if (var5 != null) {
         return var5.getName();
      } else {
         return "No matched color name.";
      }
   }

   public static int[] toRGBAArray(int var0) {
      return new int[]{var0 >> 16 & 255, var0 >> 8 & 255, var0 & 255, var0 >> 24 & 255};
   }

   public class ColorName {
      // $FF: synthetic field
      public int b;
      // $FF: synthetic field
      public String name;
      // $FF: synthetic field
      public int r;
      // $FF: synthetic field
      public int g;

      public int getR() {
         return this.r;
      }

      public int getG() {
         return this.g;
      }

      public int getB() {
         return this.b;
      }

      public ColorName(String var2, int var3, int var4, int var5) {
         this.r = var3;
         this.g = var4;
         this.b = var5;
         this.name = var2;
      }

      public String getName() {
         return this.name;
      }

      public int computeMSE(int var1, int var2, int var3) {
         return ((var1 - this.r) * (var1 - this.r) + (var2 - this.g) * (var2 - this.g) + (var3 - this.b) * (var3 - this.b)) / 3;
      }
   }

   public static class Colors {
      // $FF: synthetic field
      public static final int GRAY = ColorUtils.toRGBA(127, 127, 127, 255);
      // $FF: synthetic field
      public static final int WHITE = ColorUtils.toRGBA(255, 255, 255, 255);
      // $FF: synthetic field
      public static final int DARK_RED = ColorUtils.toRGBA(64, 0, 0, 255);
      // $FF: synthetic field
      public static final int BLACK = ColorUtils.toRGBA(0, 0, 0, 255);
      // $FF: synthetic field
      public static final int ORANGE = ColorUtils.toRGBA(255, 128, 0, 255);
      // $FF: synthetic field
      public static final int RAINBOW = Integer.MIN_VALUE;
      // $FF: synthetic field
      public static final int PURPLE = ColorUtils.toRGBA(163, 73, 163, 255);
      // $FF: synthetic field
      public static final int RED = ColorUtils.toRGBA(255, 0, 0, 255);
      // $FF: synthetic field
      public static final int BLUE = ColorUtils.toRGBA(0, 0, 255, 255);
      // $FF: synthetic field
      public static final int YELLOW = ColorUtils.toRGBA(255, 255, 0, 255);
      // $FF: synthetic field
      public static final int GREEN = ColorUtils.toRGBA(0, 255, 0, 255);
   }
}
