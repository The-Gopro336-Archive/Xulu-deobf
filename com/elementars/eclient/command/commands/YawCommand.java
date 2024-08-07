package com.elementars.eclient.command.commands;

import com.elementars.eclient.command.Command;
import com.elementars.eclient.util.Wrapper;

public class YawCommand extends Command {
   public YawCommand() {
      super("setyaw", "Sets the yaw of the player", new String[0]);
   }

   public void execute(String[] var1) {
      if (var1.length < 2) {
         sendChatMessage("Please specify the yaw!");
      } else {
         Wrapper.getMinecraft().player.rotationYaw = (float)Integer.valueOf(var1[1]);
      }
   }
}
