package com.elementars.eclient.command.commands;

import com.elementars.eclient.Xulu;
import com.elementars.eclient.command.Command;
import com.elementars.eclient.macro.Macro;
import org.lwjgl.input.Keyboard;

public class MacroCommand extends Command {
   public MacroCommand() {
      super("macro", "Manages macros", new String[]{"add"});
   }

   public void execute(String[] var1) {
      if (var1.length > 1 && var1[1].equalsIgnoreCase("help")) {
         this.showSyntax(var1[0]);
      } else if (var1.length < 2) {
         sendChatMessage("Specify an option. Try doing .macro help to see command options");
      } else {
         if (var1[1].equalsIgnoreCase("add")) {
            if (var1.length < 3) {
               sendChatMessage("Please specify a key.");
               return;
            }

            if (var1.length < 4) {
               sendChatMessage("Needs more arguments!");
               return;
            }

            try {
               String var2 = var1[2];
               String var3 = "";
               String[] var4 = var1;
               int var5 = var1.length;

               for(int var6 = 0; var6 < var5; ++var6) {
                  String var7 = var4[var6];
                  if (!var7.equalsIgnoreCase("macro") && !var7.equalsIgnoreCase("add") && !var7.equalsIgnoreCase(var2)) {
                     var3 = String.valueOf((new StringBuilder()).append(var3).append(" ").append(var7));
                  }
               }

               var3 = var3.substring(1);
               int var11 = Keyboard.getKeyIndex(var2.toUpperCase());
               sendChatMessage(String.valueOf((new StringBuilder()).append("Message = ").append(var3).append(":Key = ").append(var2).append(":actual key = ").append(var11)));
               if (Keyboard.getKeyName(var11) != null) {
                  if (!Xulu.MACRO_MANAGER.getMacros().contains(new Macro(var3, var11))) {
                     Xulu.MACRO_MANAGER.addMacro(var3, var11);
                  }

                  sendChatMessage(String.valueOf((new StringBuilder()).append("Added Macro with the key ").append(Keyboard.getKeyName(var11))));
               }
            } catch (StringIndexOutOfBoundsException var9) {
               sendChatMessage("Unknown arguments!");
            }
         } else if (var1[1].equalsIgnoreCase("del")) {
            if (var1.length < 3) {
               sendChatMessage("Please specify a key.");
               return;
            }

            try {
               int var10 = Keyboard.getKeyIndex(var1[2].toUpperCase());
               Xulu.MACRO_MANAGER.delMacro(var10);
               sendChatMessage(String.valueOf((new StringBuilder()).append("Deleted Macro with the key ").append(var1[2].toUpperCase())));
            } catch (Exception var8) {
               sendChatMessage("Error occured while removing macro!");
            }
         } else {
            sendChatMessage("Unknown arguments!");
         }

      }
   }
}
