package com.elementars.eclient.command.commands;

import com.elementars.eclient.Xulu;
import com.elementars.eclient.command.Command;
import com.elementars.eclient.command.SetBox;
import com.elementars.eclient.module.Module;
import dev.xulu.settings.Value;

public class SetStringCommand extends Command {
   public void execute(String[] var1) {
      if (var1.length < 2) {
         sendChatMessage("Please specify a module");
      } else if (var1[1].equalsIgnoreCase("help")) {
         this.showSyntax(var1[0]);
      } else if (var1.length < 3) {
         sendChatMessage("Please specify which setting you would like to change");
      } else {
         Module var2 = Xulu.MODULE_MANAGER.getModuleByName(var1[1]);
         if (var2 == null) {
            sendChatMessage("Module not found!");
         } else {
            Value var3 = Xulu.VALUE_MANAGER.getValueByMod(var2, var1[2]);
            if (var3 == null) {
               sendChatMessage("Setting not found!");
            } else if (var3.getParentMod() != var2) {
               sendChatMessage(String.valueOf((new StringBuilder()).append(var2.getDisplayName()).append(" has no setting ").append(var3.getName())));
            } else {
               if (var3.isMode()) {
                  SetBox.initTextBox(var3);
               } else {
                  sendChatMessage(String.valueOf((new StringBuilder()).append(var3.getName()).append(" is not a text setting!")));
               }

            }
         }
      }
   }

   public SetStringCommand() {
      super("setstring", "Sets a string easier", new String[0]);
   }
}
