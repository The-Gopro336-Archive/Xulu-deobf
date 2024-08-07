package com.elementars.eclient.command.commands;

import com.elementars.eclient.Xulu;
import com.elementars.eclient.command.Command;
import com.elementars.eclient.module.Module;
import org.lwjgl.input.Keyboard;

public class BindCommand extends Command {
   public void execute(String[] var1) {
      if (var1.length < 2) {
         sendChatMessage("Please specify which module you want bound");
      } else if (var1[1].equalsIgnoreCase("help")) {
         this.showSyntax(var1[0]);
      } else if (var1.length < 3) {
         sendChatMessage("Please specify the key you would like to bind");
      } else {
         Module var2 = Xulu.MODULE_MANAGER.getModuleByName(var1[1]);
         if (var2 == null) {
            sendChatMessage("Module not found.");
         } else {
            var2.setKey(Keyboard.getKeyIndex(var1[2].toUpperCase()));
            sendChatMessage(String.valueOf((new StringBuilder()).append(var2.getDisplayName()).append(" bound to ").append(var1[2].toUpperCase())));
         }
      }
   }

   public BindCommand() {
      super("bind", "binds a module to a key", new String[0]);
   }
}
