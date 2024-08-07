package com.elementars.eclient.command.commands;

import com.elementars.eclient.Xulu;
import com.elementars.eclient.command.Command;
import com.elementars.eclient.font.CFontManager;

public class CustomFontCommand extends Command {
   public CustomFontCommand() {
      super("customfont", "Tweaks the custom font", new String[]{"default", "defaultxdolf", "defaultinfo", "currentfont", "currentfontxdolf", "fonts", "setfont", "setfontxdolf"});
   }

   public void execute(String[] var1) {
      if (var1.length > 1) {
         if (var1[1].equalsIgnoreCase("help")) {
            this.showSyntax(var1[0]);
            return;
         }

         if (var1[1].equalsIgnoreCase("default")) {
            Xulu.setcFontRendererDefault();
         }

         if (var1[1].equalsIgnoreCase("defaultxdolf")) {
            Xulu.setXdolfFontRendererDefault();
         }

         if (var1[1].equalsIgnoreCase("defaultinfo")) {
            sendChatMessage("Font: Verdana, Size: 18");
         }

         if (var1[1].equalsIgnoreCase("currentfont")) {
            sendChatMessage(String.valueOf((new StringBuilder()).append("Font: ").append(CFontManager.customFont.getFont().getFontName()).append(", Size: ").append(CFontManager.customFont.getFont().getSize())));
         }

         if (var1[1].equalsIgnoreCase("currentfontxdolf")) {
            sendChatMessage(String.valueOf((new StringBuilder()).append("Font: ").append(CFontManager.xFontRenderer.getFont().getFont().getName()).append(", Size: ").append(CFontManager.xFontRenderer.getFont().getFont().getSize())));
         }

         if (var1[1].equalsIgnoreCase("fonts")) {
            sendChatMessage("Fonts:");
            String var2 = "";
            boolean var3 = true;
            String[] var4 = Xulu.getFonts();
            int var5 = var4.length;

            for(int var6 = 0; var6 < var5; ++var6) {
               String var7 = var4[var6];
               if (var3) {
                  var2 = var7;
               } else {
                  var2 = String.valueOf((new StringBuilder()).append(var2).append(", ").append(var7));
               }

               var3 = false;
            }

            sendChatMessage(var2);
         }

         if (var1[1].equalsIgnoreCase("setfont")) {
            if (var1.length < 3) {
               sendChatMessage("Specify your font!");
               return;
            }

            if (var1.length < 4) {
               sendChatMessage("Specify your font size!");
               return;
            }

            if (Integer.parseInt(var1[3]) == 0) {
               return;
            }

            Xulu.setCFontRenderer(var1[2], Integer.parseInt(var1[3]));
         }

         if (var1[1].equalsIgnoreCase("setfontxdolf")) {
            if (var1.length < 3) {
               sendChatMessage("Specify your font!");
               return;
            }

            if (var1.length < 4) {
               sendChatMessage("Specify your font size!");
               return;
            }

            if (Integer.parseInt(var1[3]) == 0) {
               return;
            }

            Xulu.setXdolfFontRenderer(var1[2], Integer.parseInt(var1[3]));
         }
      } else {
         sendChatMessage("Do .customfont help for options");
      }

   }
}
