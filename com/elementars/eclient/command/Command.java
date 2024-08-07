package com.elementars.eclient.command;

import com.elementars.eclient.module.core.Global;
import com.elementars.eclient.util.ColorTextUtils;
import com.elementars.eclient.util.Helper;
import com.elementars.eclient.util.Wrapper;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentBase;

public class Command implements Helper {
   // $FF: synthetic field
   private static String prefix;
   // $FF: synthetic field
   protected String[] syntax;
   // $FF: synthetic field
   protected String name;
   // $FF: synthetic field
   protected String description;

   private static String[] getBrackets(String var0) {
      if (var0.equalsIgnoreCase("[]")) {
         return new String[]{"[", "]"};
      } else if (var0.equalsIgnoreCase("<>")) {
         return new String[]{"<", ">"};
      } else if (var0.equalsIgnoreCase("()")) {
         return new String[]{"(", ")"};
      } else if (var0.equalsIgnoreCase("{}")) {
         return new String[]{"{", "}"};
      } else {
         return var0.equalsIgnoreCase("-==-") ? new String[]{"-=", "=-"} : new String[]{"[", "]"};
      }
   }

   public void execute(String[] var1) {
   }

   public static void sendRawChatMessage(String var0) {
      try {
         Wrapper.getPlayer().sendMessage(new Command.ChatMessage(var0));
      } catch (Exception var2) {
         var2.printStackTrace();
      }

   }

   public static char SECTIONSIGN() {
      return '§';
   }

   public String getName() {
      return this.name;
   }

   public static void setPrefix(String var0) {
      prefix = var0;
   }

   public String getDescription() {
      return this.description;
   }

   public void showSyntax(String var1) {
      sendChatMessage(String.valueOf((new StringBuilder()).append("Options for ").append(var1)));
      if (this.syntax.length == 0) {
         sendChatMessage("No options for this command.");
      } else {
         String[] var2 = this.syntax;
         int var3 = var2.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            String var5 = var2[var4];
            sendChatMessage(String.valueOf((new StringBuilder()).append(" - ").append(var5)));
         }

      }
   }

   public void syntaxCheck(String[] var1) {
      if (var1.length > 1 && var1[1].equalsIgnoreCase("help")) {
         this.showSyntax(var1[0]);
      } else {
         this.execute(var1);
      }
   }

   public static String getPrefix() {
      return prefix;
   }

   public String[] getSyntax() {
      return this.syntax;
   }

   public static void sendStringChatMessage(String[] var0) {
      sendChatMessage("");
      String[] var1 = var0;
      int var2 = var0.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         String var4 = var1[var3];
         sendRawChatMessage(var4);
      }

   }

   public Command(String var1, String var2, String[] var3) {
      this.name = var1;
      this.description = var2;
      this.syntax = var3;
      prefix = ".";
   }

   public static void sendChatMessage(String var0) {
      sendRawChatMessage(String.valueOf((new StringBuilder()).append(ColorTextUtils.getColor((String)Global.command2.getValue())).append(getBrackets((String)Global.command3.getValue())[0]).append(ColorTextUtils.getColor((String)Global.command1.getValue())).append("Xulu").append(ColorTextUtils.getColor((String)Global.command2.getValue())).append(getBrackets((String)Global.command3.getValue())[1]).append(" &r").append(var0)));
   }

   public static class ChatMessage extends TextComponentBase {
      // $FF: synthetic field
      String text;

      public ITextComponent createCopy() {
         return new Command.ChatMessage(this.text);
      }

      public String getUnformattedComponentText() {
         return this.text;
      }

      public ChatMessage(String var1) {
         Pattern var2 = Pattern.compile("&[0123456789abcdefrlosmk]");
         Matcher var3 = var2.matcher(var1);
         StringBuffer var4 = new StringBuffer();

         while(var3.find()) {
            String var5 = String.valueOf((new StringBuilder()).append("§").append(var3.group().substring(1)));
            var3.appendReplacement(var4, var5);
         }

         var3.appendTail(var4);
         this.text = var4.toString();
      }
   }
}
