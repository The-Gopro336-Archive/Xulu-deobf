package com.elementars.eclient.font;

import com.elementars.eclient.module.core.Global;
import com.elementars.eclient.util.Helper;
import java.awt.Color;

public class RainbowTextRenderer implements Helper {
   // $FF: synthetic field
   public static int a;
   // $FF: synthetic field
   private static int rgb;
   // $FF: synthetic field
   public int FONT_HEIGHT = 9;
   // $FF: synthetic field
   public static int r;
   // $FF: synthetic field
   public static int g;
   // $FF: synthetic field
   public static int b;
   // $FF: synthetic field
   static float hue = 0.01F;

   public int updateRainbow(int var1) {
      float var2 = Color.RGBtoHSB((new Color(var1)).getRed(), (new Color(var1)).getGreen(), (new Color(var1)).getBlue(), (float[])null)[0];
      var2 += (float)(Integer)Global.rainbowAmount.getValue() / 1000.0F;
      if (var2 > 1.0F) {
         --var2;
      }

      return Color.HSBtoRGB(var2, (float)(Integer)Global.rainbowSaturation.getValue() / 255.0F, (float)(Integer)Global.rainbowLightness.getValue() / 255.0F);
   }

   public int getCharWidth(char var1) {
      return fontRenderer.getCharWidth(var1);
   }

   public void updateRainbow() {
      rgb = Color.HSBtoRGB(hue, (float)(Integer)Global.rainbowSaturation.getValue() / 255.0F, (float)(Integer)Global.rainbowLightness.getValue() / 255.0F);
      a = rgb >>> 24 & 255;
      r = rgb >>> 16 & 255;
      g = rgb >>> 8 & 255;
      b = rgb & 255;
      hue += 1.0E-5F;
      if (hue > 1.0F) {
         --hue;
      }

   }

   public int getStringWidth(String var1) {
      return fontRenderer.getStringWidth(var1);
   }

   public int drawStringWithShadow(String var1, float var2, float var3, int var4) {
      if (var4 == -1) {
         var4 = rgb;
         this.updateRainbow();
      } else {
         var4 = this.updateRainbow(var4);
      }

      fontRenderer.drawStringWithShadow(var1, var2, var3, var4);
      return var4;
   }

   public int drawString(String var1, int var2, int var3, int var4) {
      if (var4 == -1) {
         var4 = rgb;
      }

      this.updateRainbow();
      fontRenderer.drawString(var1, var2, var3, var4);
      return var4;
   }

   public int getHeight() {
      return this.FONT_HEIGHT;
   }
}
