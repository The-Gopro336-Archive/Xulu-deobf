package com.elementars.eclient.command;

import com.elementars.eclient.command.commands.AboutCommand;
import com.elementars.eclient.command.commands.AntiVoidCommand;
import com.elementars.eclient.command.commands.BindCommand;
import com.elementars.eclient.command.commands.CreditsCommand;
import com.elementars.eclient.command.commands.CustomFontCommand;
import com.elementars.eclient.command.commands.DrawnCommand;
import com.elementars.eclient.command.commands.DummyCommand;
import com.elementars.eclient.command.commands.EnemyCommand;
import com.elementars.eclient.command.commands.FriendCommand;
import com.elementars.eclient.command.commands.HelpCommand;
import com.elementars.eclient.command.commands.LogspotCommand;
import com.elementars.eclient.command.commands.MacroCommand;
import com.elementars.eclient.command.commands.NicknameCommand;
import com.elementars.eclient.command.commands.PrefixCommand;
import com.elementars.eclient.command.commands.ReloadCommand;
import com.elementars.eclient.command.commands.SaveCommand;
import com.elementars.eclient.command.commands.SearchCommand;
import com.elementars.eclient.command.commands.SetCommand;
import com.elementars.eclient.command.commands.SetStringCommand;
import com.elementars.eclient.command.commands.ToggleCommand;
import com.elementars.eclient.command.commands.WaypointCommand;
import com.elementars.eclient.command.commands.XrayCommand;
import com.elementars.eclient.command.commands.YawCommand;
import com.elementars.eclient.util.Wrapper;
import java.util.ArrayList;
import java.util.Iterator;

public class CommandManager {
   // $FF: synthetic field
   public static ArrayList commands = new ArrayList();
   // $FF: synthetic field
   public static ArrayList rcommands = new ArrayList();

   public static void runCommand(String var0) {
      String[] var1 = var0.split(" ");

      for(int var2 = 0; var2 < var1.length; ++var2) {
         if (var1[var2] != null) {
            var1[var2] = var1[var2].replaceAll("<>", " ");
         }
      }

      try {
         Wrapper.getMinecraft().ingameGUI.getChatGUI().addToSentMessages(String.valueOf((new StringBuilder()).append(Command.getPrefix()).append(var0)));
         Iterator var5 = commands.iterator();

         while(var5.hasNext()) {
            Command var3 = (Command)var5.next();
            if (var3.getName().equalsIgnoreCase(var1[0])) {
               var3.syntaxCheck(var1);
               return;
            }
         }
      } catch (Exception var4) {
         Command.sendChatMessage("Error occured when running command!");
      }

      Command.sendChatMessage("Command not found. Try .help for a list of commands");
   }

   public void init() {
      commands.add(new AboutCommand());
      commands.add(new CreditsCommand());
      commands.add(new HelpCommand());
      commands.add(new MacroCommand());
      commands.add(new BindCommand());
      commands.add(new ToggleCommand());
      commands.add(new SetCommand());
      commands.add(new SetStringCommand());
      commands.add(new DrawnCommand());
      commands.add(new XrayCommand());
      commands.add(new SearchCommand());
      commands.add(new SaveCommand());
      commands.add(new ReloadCommand());
      commands.add(new PrefixCommand());
      commands.add(new FriendCommand());
      commands.add(new EnemyCommand());
      commands.add(new CustomFontCommand());
      commands.add(new YawCommand());
      commands.add(new LogspotCommand());
      commands.add(new WaypointCommand());
      commands.add(new AntiVoidCommand());
      commands.add(new DummyCommand());
      commands.add(new NicknameCommand());
   }

   public static ArrayList getCommands() {
      return commands;
   }
}
