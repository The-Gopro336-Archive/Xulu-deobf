package com.elementars.eclient.command.commands;

import com.elementars.eclient.command.Command;
import com.elementars.eclient.friend.Friend;
import com.elementars.eclient.friend.Friends;

public class FriendCommand extends Command {
   public void execute(String[] var1) {
      if (var1.length < 2) {
         sendChatMessage("Try .friend add or .friend del");
      } else {
         if (var1[1].equalsIgnoreCase("help")) {
            this.showSyntax(var1[0]);
         }

         if (var1.length < 3) {
            sendChatMessage("Specify a username");
         } else {
            Friend var2 = new Friend(var1[2]);
            if (var1[1].equalsIgnoreCase("add")) {
               if (!Friends.getFriends().contains(var2)) {
                  Friends.addFriend(var2.getUsername());
                  sendChatMessage(String.valueOf((new StringBuilder()).append(var2.getUsername()).append(" has been friended")));
               } else {
                  sendChatMessage(String.valueOf((new StringBuilder()).append(var2.getUsername()).append(" is already friended!")));
               }
            } else if (var1[1].equalsIgnoreCase("del")) {
               if (Friends.getFriendByName(var2.getUsername()) != null) {
                  Friends.delFriend(var2.getUsername());
                  sendChatMessage(String.valueOf((new StringBuilder()).append(var2.getUsername()).append(" has been unfriended")));
               } else {
                  sendChatMessage(String.valueOf((new StringBuilder()).append(var2.getUsername()).append(" isn't a friend")));
               }
            } else {
               sendChatMessage(String.valueOf((new StringBuilder()).append("Unknown attribute '").append(var1[1]).append("'")));
            }

         }
      }
   }

   public FriendCommand() {
      super("friend", "adds or deletes friends", new String[]{"add", "del"});
   }
}
