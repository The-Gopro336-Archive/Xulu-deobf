package com.elementars.eclient.command.commands;

import com.elementars.eclient.command.Command;

public class PrefixCommand extends Command {
   public PrefixCommand() {
      super("prefix", "Changes the prefix for commands", new String[0]);
   }

   public void execute(String[] var1) {
      if (var1.length < 2) {
         sendChatMessage("Specify what prefix you would like to change to.");
         sendChatMessage(String.valueOf((new StringBuilder()).append("Current prefix is: ").append(getPrefix())));
      } else if (var1[1].equalsIgnoreCase("help")) {
         this.showSyntax(var1[0]);
      } else {
         setPrefix(var1[1]);
         sendChatMessage(String.valueOf((new StringBuilder()).append("Set the prefix to: ").append(getPrefix())));
      }
   }
}
