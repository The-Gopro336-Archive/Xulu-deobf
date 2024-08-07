package com.elementars.eclient.command.commands;

import com.elementars.eclient.Xulu;
import com.elementars.eclient.command.Command;
import com.elementars.eclient.module.Module;

public class ToggleCommand extends Command {
   public void execute(String[] var1) {
      if (var1.length > 1 && var1[1].equalsIgnoreCase("help")) {
         this.showSyntax(var1[0]);
      } else if (var1.length <= 1) {
         sendChatMessage("Not enough arguments!");
      } else {
         if (Xulu.MODULE_MANAGER.getModuleByName(var1[1]) != null) {
            Module var2 = Xulu.MODULE_MANAGER.getModuleByName(var1[1]);
            var2.toggle();
            sendChatMessage(String.valueOf((new StringBuilder()).append(var2.getName()).append(" toggled ").append(var2.isToggled() ? String.valueOf((new StringBuilder()).append(Command.SECTIONSIGN()).append("aON")) : String.valueOf((new StringBuilder()).append(Command.SECTIONSIGN()).append("cOFF")))));
         } else {
            sendChatMessage("Module not found.");
         }

      }
   }

   public ToggleCommand() {
      super("t", "Toggles modules", new String[0]);
   }
}
