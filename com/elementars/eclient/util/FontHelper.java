package com.elementars.eclient.util;

import com.elementars.eclient.Xulu;
import com.elementars.eclient.command.Command;
import com.elementars.eclient.font.CFontManager;
import com.elementars.eclient.font.XFontRenderer;
import com.elementars.eclient.font.custom.CustomFont;
import java.awt.Font;

public class FontHelper {
   public static void setXdolfFontRenderer(String var0, int var1, int var2, boolean var3) {
      try {
         if (Xulu.getCorrectFont(var0) == null && !var0.equalsIgnoreCase("Xulu")) {
            Command.sendChatMessage("Invalid font!");
            return;
         }

         CFontManager.xFontRenderer = new XFontRenderer(new Font(Xulu.getCorrectFont(var0), var1, var2), var3, 8);
      } catch (Exception var5) {
         var5.printStackTrace();
      }

   }

   public static void setCFontRenderer(String var0, int var1, int var2, boolean var3, boolean var4) {
      try {
         if (Xulu.getCorrectFont(var0) == null) {
            Command.sendChatMessage("Invalid font!");
            return;
         }

         if (var0.equalsIgnoreCase("Comfortaa Regular")) {
            CFontManager.customFont = new CustomFont(new Font("Comfortaa Regular", var1, var2), var3, var4);
            return;
         }

         CFontManager.customFont = new CustomFont(new Font(Xulu.getCorrectFont(var0), var1, var2), var3, var4);
      } catch (Exception var7) {
         var7.printStackTrace();
      }

   }
}
