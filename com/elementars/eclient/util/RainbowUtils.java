package com.elementars.eclient.util;

import com.elementars.eclient.module.core.Global;
import java.awt.Color;

public class RainbowUtils {
   // $FF: synthetic field
   static float hue = 0.01F;
   // $FF: synthetic field
   public static int r;
   // $FF: synthetic field
   private static int rgb;
   // $FF: synthetic field
   public static int a;
   // $FF: synthetic field
   public static int b;
   // $FF: synthetic field
   public static int g;

   public static void updateRainbow() {
      rgb = Color.HSBtoRGB(hue, (float)(Integer)Global.rainbowSaturation.getValue() / 255.0F, (float)(Integer)Global.rainbowLightness.getValue() / 255.0F);
      a = rgb >>> 24 & 255;
      r = rgb >>> 16 & 255;
      g = rgb >>> 8 & 255;
      b = rgb & 255;
      hue += (float)(Integer)Global.rainbowspeed.getValue() / 1000.0F;
      if (hue > 1.0F) {
         --hue;
      }

   }
}
