package com.elementars.eclient.command.commands;

import com.elementars.eclient.Xulu;
import com.elementars.eclient.command.Command;

public class SaveCommand extends Command {
   public void execute(String[] var1) {
      if (var1.length > 1 && var1[1].equalsIgnoreCase("help")) {
         this.showSyntax(var1[0]);
      } else {
         Xulu.save();
         sendChatMessage("Config saved!");
      }
   }

   public SaveCommand() {
      super("save", "Saves the config", new String[0]);
   }
}
