package com.elementars.eclient.command.commands;

import com.elementars.eclient.Xulu;
import com.elementars.eclient.command.Command;

public class ReloadCommand extends Command {
   public ReloadCommand() {
      super("reload", "Reloads the config", new String[0]);
   }

   public void execute(String[] var1) {
      if (var1.length > 1 && var1[1].equalsIgnoreCase("help")) {
         this.showSyntax(var1[0]);
      } else {
         Xulu.load();
         sendChatMessage("Config reloaded!");
      }
   }
}
