package com.elementars.eclient.command.commands;

import com.elementars.eclient.command.Command;
import com.elementars.eclient.module.player.AntiVoid;

public class AntiVoidCommand extends Command {
   public void execute(String[] var1) {
      if (var1.length == 1) {
         sendChatMessage("Please specify a server IP");
      } else {
         if (!AntiVoid.INSTANCE.ipList.isEmpty() && AntiVoid.INSTANCE.ipList.contains(var1[1])) {
            sendChatMessage("You did fall below the Y level in AntiVoid! Be careful!");
         } else {
            sendChatMessage("You did not trigger AntiVoid on this server!");
         }

      }
   }

   public AntiVoidCommand() {
      super("antivoid", "Shows if you have logged from antivoid on a server", new String[0]);
   }
}
