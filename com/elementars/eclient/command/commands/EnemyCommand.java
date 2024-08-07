package com.elementars.eclient.command.commands;

import com.elementars.eclient.command.Command;
import com.elementars.eclient.enemy.Enemies;
import com.elementars.eclient.enemy.Enemy;

public class EnemyCommand extends Command {
   public EnemyCommand() {
      super("enemy", "adds or deletes enemies", new String[]{"add", "del"});
   }

   public void execute(String[] var1) {
      if (var1.length < 2) {
         sendChatMessage("Try .enemy add or .enemy del");
      } else {
         if (var1[1].equalsIgnoreCase("help")) {
            this.showSyntax(var1[0]);
         }

         if (var1.length < 3) {
            sendChatMessage("Specify a username");
         } else {
            Enemy var2 = new Enemy(var1[2]);
            if (var1[1].equalsIgnoreCase("add")) {
               if (!Enemies.getEnemies().contains(var2)) {
                  Enemies.addEnemy(var2.getUsername());
                  sendChatMessage(String.valueOf((new StringBuilder()).append(var2.getUsername()).append(" is now an enemy")));
               } else {
                  sendChatMessage(String.valueOf((new StringBuilder()).append(var2.getUsername()).append(" is already an enemy!")));
               }
            } else if (var1[1].equalsIgnoreCase("del")) {
               if (Enemies.getEnemyByName(var2.getUsername()) != null) {
                  Enemies.delEnemy(var2.getUsername());
                  sendChatMessage(String.valueOf((new StringBuilder()).append(var2.getUsername()).append(" is no longer an enemy")));
               } else {
                  sendChatMessage(String.valueOf((new StringBuilder()).append(var2.getUsername()).append(" isn't an enemy")));
               }
            } else {
               sendChatMessage(String.valueOf((new StringBuilder()).append("Unknown attribute '").append(var1[1]).append("'")));
            }

         }
      }
   }
}
