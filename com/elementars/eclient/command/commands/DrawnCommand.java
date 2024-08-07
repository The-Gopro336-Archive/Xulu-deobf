package com.elementars.eclient.command.commands;

import com.elementars.eclient.Xulu;
import com.elementars.eclient.command.Command;
import com.elementars.eclient.module.Module;

public class DrawnCommand extends Command {
   public DrawnCommand() {
      super("drawn", "toggles if a module is drawn on array list", new String[0]);
   }

   public void execute(String[] var1) {
      if (var1.length < 2) {
         sendChatMessage("Please specify which module you want drawn/undrawn");
      } else if (var1[1].equalsIgnoreCase("help")) {
         this.showSyntax(var1[0]);
      } else {
         Module var2 = Xulu.MODULE_MANAGER.getModuleByName(var1[1]);
         if (var2 == null) {
            sendChatMessage("Module not found.");
         } else {
            var2.setDrawn(!var2.isDrawn());
            sendChatMessage(String.valueOf((new StringBuilder()).append(var2.getDisplayName()).append(var2.isDrawn() ? " drawn" : " undrawn")));
         }
      }
   }
}
