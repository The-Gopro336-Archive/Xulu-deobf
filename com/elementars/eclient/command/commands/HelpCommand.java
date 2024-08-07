package com.elementars.eclient.command.commands;

import com.elementars.eclient.command.Command;
import com.elementars.eclient.command.CommandManager;
import java.util.Iterator;

public class HelpCommand extends Command {
   public HelpCommand() {
      super("help", "Shows a list of commands", new String[0]);
   }

   public void execute(String[] var1) {
      if (var1.length > 1 && var1[1].equalsIgnoreCase("help")) {
         this.showSyntax(var1[0]);
      } else {
         sendChatMessage("Here's a list of commands:");
         Iterator var2 = CommandManager.getCommands().iterator();

         while(var2.hasNext()) {
            Command var3 = (Command)var2.next();
            sendChatMessage(String.valueOf((new StringBuilder()).append(var3.getName()).append(" : ").append(var3.getDescription())));
         }

         sendChatMessage("Follow any command with help to see command options");
      }
   }
}
