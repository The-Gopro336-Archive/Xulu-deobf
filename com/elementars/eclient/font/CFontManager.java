package com.elementars.eclient.font;

import com.elementars.eclient.font.custom.CustomFont;
import com.elementars.eclient.util.Wrapper;
import java.awt.Font;
import java.util.regex.Pattern;

public class CFontManager {
   // $FF: synthetic field
   public static RainbowTextRenderer rainbowTextRenderer = new RainbowTextRenderer();
   // $FF: synthetic field
   private static final Pattern COLOR_CODE_PATTERN = Pattern.compile("§[0123456789abcdefklmnor]");
   // $FF: synthetic field
   private final int[] colorCodes = new int[]{0, 170, 43520, 43690, 11141120, 11141290, 16755200, 11184810, 5592405, 5592575, 5635925, 5636095, 16733525, 16733695, 16777045, 16777215};
   // $FF: synthetic field
   public static CustomFont customFont = new CustomFont(new Font("Verdana", 0, 18), true, false);
   // $FF: synthetic field
   public static XFontRenderer xFontRenderer = new XFontRenderer(new Font("Verdana", 0, 36), true, 8);

   public float drawStringWithShadow(String var1, double var2, double var4, int var6, boolean var7) {
      if (((String)com.elementars.eclient.module.core.CustomFont.customFontMode.getValue()).equalsIgnoreCase("Normal")) {
         return customFont.drawStringWithShadow(var1, var2, var4 - (double)((float)(Integer)com.elementars.eclient.module.core.CustomFont.fontOffset.getValue()), var6);
      } else if (((String)com.elementars.eclient.module.core.CustomFont.customFontMode.getValue()).equalsIgnoreCase("Xdolf")) {
         xFontRenderer.drawStringWithShadow(var1, (int)var2, (int)var4 - (Integer)com.elementars.eclient.module.core.CustomFont.fontOffset.getValue(), var6);
         return 0.0F;
      } else if (!((String)com.elementars.eclient.module.core.CustomFont.customFontMode.getValue()).equalsIgnoreCase("Rainbow")) {
         return 0.0F;
      } else {
         int var8 = var7 ? var6 : -1;
         int var9 = 0;
         char[] var10 = var1.toCharArray();
         String[] var11 = COLOR_CODE_PATTERN.split(var1);
         int var12 = 0;
         String[] var13 = var11;
         int var14 = var11.length;

         for(int var15 = 0; var15 < var14; ++var15) {
            String var16 = var13[var15];
            String[] var17 = var16.split("");
            String[] var18 = var17;
            int var19 = var17.length;

            int var20;
            for(var20 = 0; var20 < var19; ++var20) {
               String var21 = var18[var20];
               if (var9 == 0) {
                  var8 = rainbowTextRenderer.drawStringWithShadow(var21, (float)var2, (float)var4, var8);
               } else {
                  var8 = rainbowTextRenderer.updateRainbow(var8);
                  Wrapper.getMinecraft().fontRenderer.drawStringWithShadow(var21, (float)var2, (float)var4, var9);
               }

               try {
                  var2 += (double)rainbowTextRenderer.getCharWidth(var21.charAt(0));
               } catch (Exception var24) {
                  var24.printStackTrace();
               }

               ++var12;
            }

            if (var12 < var10.length) {
               char var25 = var10[var12];
               if (var25 == 167) {
                  char var26 = var10[var12 + 1];
                  var20 = "0123456789abcdef".indexOf(var26);
                  if (var20 < 0) {
                     if (var26 == 'r') {
                        var9 = 0;
                     }
                  } else {
                     var9 = this.colorCodes[var20];
                  }

                  var12 += 2;
               }
            }
         }

         return 0.0F;
      }
   }

   public float drawStringWithShadow(String var1, double var2, double var4, int var6) {
      return this.drawStringWithShadow(var1, var2, var4, var6, false);
   }

   public float drawCenteredString(String var1, float var2, float var3, int var4) {
      if (((String)com.elementars.eclient.module.core.CustomFont.customFontMode.getValue()).equalsIgnoreCase("Normal")) {
         return customFont.drawCenteredString(var1, var2, var3, var4);
      } else {
         xFontRenderer.drawCenteredString(var1, (int)var2, (int)var3, var4);
         return 0.0F;
      }
   }

   public float getHeight() {
      if (((String)com.elementars.eclient.module.core.CustomFont.customFontMode.getValue()).equalsIgnoreCase("Normal")) {
         return (float)customFont.getHeight();
      } else {
         return ((String)com.elementars.eclient.module.core.CustomFont.customFontMode.getValue()).equalsIgnoreCase("Rainbow") ? (float)rainbowTextRenderer.getHeight() : (float)xFontRenderer.getHeight();
      }
   }

   public float drawCenteredStringWithShadow(String var1, float var2, float var3, int var4) {
      if (((String)com.elementars.eclient.module.core.CustomFont.customFontMode.getValue()).equalsIgnoreCase("Normal")) {
         return customFont.drawCenteredStringWithShadow(var1, var2, var3, var4);
      } else {
         xFontRenderer.drawCenteredString(var1, (int)var2, (int)var3, var4, true);
         return 0.0F;
      }
   }

   public float drawString(String var1, double var2, double var4, int var6, boolean var7) {
      if (((String)com.elementars.eclient.module.core.CustomFont.customFontMode.getValue()).equalsIgnoreCase("Normal")) {
         return customFont.drawString(var1, var2, var4, var6, var7);
      } else {
         return var7 ? (float)xFontRenderer.drawStringWithShadow(var1, (float)var2, (float)var4, var6) : (float)xFontRenderer.drawString(var1, (float)var2, (float)var4, var6);
      }
   }

   public int getStringWidth(String var1) {
      if (((String)com.elementars.eclient.module.core.CustomFont.customFontMode.getValue()).equalsIgnoreCase("Normal")) {
         return customFont.getStringWidth(var1);
      } else if (((String)com.elementars.eclient.module.core.CustomFont.customFontMode.getValue()).equalsIgnoreCase("Rainbow")) {
         return rainbowTextRenderer.getStringWidth(var1);
      } else {
         return ((String)com.elementars.eclient.module.core.CustomFont.customFontMode.getValue()).equalsIgnoreCase("Xdolf") ? xFontRenderer.getStringWidth(var1) : 0;
      }
   }

   public float drawString(String var1, float var2, float var3, int var4) {
      return ((String)com.elementars.eclient.module.core.CustomFont.customFontMode.getValue()).equalsIgnoreCase("Normal") ? customFont.drawString(var1, var2, var3 - (float)(Integer)com.elementars.eclient.module.core.CustomFont.fontOffset.getValue(), var4) : (float)xFontRenderer.drawStringWithShadow(var1, var2, var3 - (float)(Integer)com.elementars.eclient.module.core.CustomFont.fontOffset.getValue(), var4);
   }
}
