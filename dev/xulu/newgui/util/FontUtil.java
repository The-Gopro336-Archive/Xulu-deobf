package dev.xulu.newgui.util;

import com.elementars.eclient.util.Helper;
import net.minecraft.util.StringUtils;

public class FontUtil implements Helper {
   public static void drawTotalCenteredStringWithShadow(String var0, double var1, double var3, int var5) {
      drawStringWithShadow(var0, var1 - (double)(fontRenderer.getStringWidth(var0) / 2), var3 - (double)((float)fontRenderer.FONT_HEIGHT / 2.0F), var5);
   }

   public static int getStringWidth(String var0) {
      return fontRenderer.getStringWidth(StringUtils.stripControlCodes(var0));
   }

   public static int getFontHeight() {
      return fontRenderer.FONT_HEIGHT;
   }

   public static void drawTotalCenteredString(String var0, double var1, double var3, int var5) {
      drawString(var0, var1 - (double)(fontRenderer.getStringWidth(var0) / 2), var3 - (double)(fontRenderer.FONT_HEIGHT / 2), var5);
   }

   public static void drawCenteredString(String var0, double var1, double var3, int var5) {
      drawString(var0, var1 - (double)(fontRenderer.getStringWidth(var0) / 2), var3, var5);
   }

   public static void drawString(String var0, double var1, double var3, int var5) {
      fontRenderer.drawString(var0, (int)var1, (int)var3, var5);
   }

   public static void drawStringWithShadow(String var0, double var1, double var3, int var5) {
      fontRenderer.drawStringWithShadow(var0, (float)var1, (float)var3, var5);
   }

   public static void drawCenteredStringWithShadow(String var0, double var1, double var3, int var5) {
      drawStringWithShadow(var0, var1 - (double)(fontRenderer.getStringWidth(var0) / 2), var3, var5);
   }
}
