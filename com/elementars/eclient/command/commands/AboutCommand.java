package com.elementars.eclient.command.commands;

import com.elementars.eclient.command.Command;

public class AboutCommand extends Command {
   public void execute(String[] var1) {
      if (var1.length > 1 && var1[1].equalsIgnoreCase("help")) {
         this.showSyntax(var1[0]);
      } else {
         sendChatMessage("Xulu v1.5.2 by Elementars and John200410");
         sendChatMessage("Do .help to see a list of commands");
      }
   }

   public AboutCommand() {
      super("about", "Shows general information", new String[0]);
   }
}
