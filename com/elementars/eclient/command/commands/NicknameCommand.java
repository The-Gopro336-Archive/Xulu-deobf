package com.elementars.eclient.command.commands;

import com.elementars.eclient.command.Command;
import com.elementars.eclient.friend.Nicknames;

public class NicknameCommand extends Command {
   public void execute(String[] var1) {
      if (var1.length < 2) {
         sendChatMessage("Usage: .nickname set/remove (name) (nickname)");
      } else {
         if (var1[1].equalsIgnoreCase("help")) {
            this.showSyntax(var1[0]);
         }

         if (var1.length < 3) {
            sendChatMessage("Specify a username");
         } else if (var1.length < 4 && !var1[1].equalsIgnoreCase("remove")) {
            sendChatMessage("Specify a nickname");
         } else {
            if (var1[1].equalsIgnoreCase("set")) {
               Nicknames.addNickname(var1[2], var1[3]);
               sendChatMessage(String.valueOf((new StringBuilder()).append("Set nickname for &b").append(var1[2])));
            } else if (var1[1].equalsIgnoreCase("remove")) {
               if (Nicknames.hasNickname(var1[2])) {
                  Nicknames.removeNickname(var1[2]);
                  sendChatMessage(String.valueOf((new StringBuilder()).append("Nickname has been removed for &b").append(var1[2])));
               } else {
                  sendChatMessage(String.valueOf((new StringBuilder()).append("&b").append(var1[2]).append("&f doesn't have a nickname")));
               }
            } else {
               sendChatMessage(String.valueOf((new StringBuilder()).append("Unknown attribute '").append(var1[1]).append("'")));
            }

         }
      }
   }

   public NicknameCommand() {
      super("nickname", "adds or deletes friends", new String[]{"set", "remove"});
   }
}
