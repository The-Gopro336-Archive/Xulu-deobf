package com.elementars.eclient.command.commands;

import com.elementars.eclient.command.Command;
import com.elementars.eclient.module.player.SelfLogoutSpot;

public class LogspotCommand extends Command {
   public void execute(String[] var1) {
      if (var1.length == 1) {
         sendChatMessage("Please specify a server IP");
      } else {
         if (!SelfLogoutSpot.INSTANCE.logoutMap.isEmpty() && SelfLogoutSpot.INSTANCE.logoutMap.get(var1[1]) != null) {
            sendChatMessage(String.valueOf((new StringBuilder()).append("Your logout spot is - ").append((String)SelfLogoutSpot.INSTANCE.logoutMap.get(var1[1]))));
         } else {
            sendChatMessage("Your logout spot is not saved for that server!");
         }

      }
   }

   public LogspotCommand() {
      super("logspot", "Shows your logout spot from a given server", new String[0]);
   }
}
