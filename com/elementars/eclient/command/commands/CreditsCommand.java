package com.elementars.eclient.command.commands;

import com.elementars.eclient.command.Command;

public class CreditsCommand extends Command {
   // $FF: synthetic field
   String[] credits = new String[]{"Sago", "WeWide", "Nemac", "Jumpy/Xdolf", "Naughty", "John", "Mtnl"};

   public void execute(String[] var1) {
      if (var1.length > 1 && var1[1].equalsIgnoreCase("help")) {
         this.showSyntax(var1[0]);
      } else {
         sendChatMessage("Here's a list of people who helped brainstorm ideas for the client:");
         String var2 = "";
         boolean var3 = true;
         String[] var4 = this.credits;
         int var5 = var4.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            String var7 = var4[var6];
            if (var3) {
               var2 = var7;
            } else {
               var2 = String.valueOf((new StringBuilder()).append(var2).append(", ").append(var7));
            }

            var3 = false;
         }

         sendChatMessage(var2);
      }
   }

   public CreditsCommand() {
      super("credits", "Shows the people who helped come up with ideas for modules and ect.", new String[0]);
   }
}
