package com.elementars.eclient.command.commands;

import com.elementars.eclient.Xulu;
import com.elementars.eclient.command.Command;
import com.elementars.eclient.module.Module;
import com.elementars.eclient.util.Wrapper;
import dev.xulu.settings.Value;
import java.util.Iterator;

public class SetCommand extends Command {
   public void execute(String[] var1) {
      if (var1.length < 2) {
         sendChatMessage("Please specify a module");
      } else if (var1[1].equalsIgnoreCase("help")) {
         this.showSyntax(var1[0]);
      } else if (var1.length < 3) {
         sendChatMessage("Please specify which setting you would like to change");
      } else if (var1.length < 4) {
         sendChatMessage("Please enter a value you would like to set");
      } else {
         Module var2 = Xulu.MODULE_MANAGER.getModuleByName(var1[1]);
         if (var2 == null) {
            sendChatMessage("Module not found!");
         } else {
            Value var3 = null;
            Iterator var4 = Xulu.VALUE_MANAGER.getValuesByMod(var2).iterator();

            while(var4.hasNext()) {
               Value var5 = (Value)var4.next();
               if (var5.getName().equalsIgnoreCase(var1[2])) {
                  var3 = var5;
               }
            }

            if (var3 == null) {
               sendChatMessage("Setting not found!");
            } else if (var3.getParentMod() != var2) {
               sendChatMessage(String.valueOf((new StringBuilder()).append(var2.getDisplayName()).append(" has no setting ").append(var3.getName())));
            } else {
               try {
                  if (var3.isToggle()) {
                     var3.setValue(Boolean.parseBoolean(var1[3]));
                     sendChatMessage(String.valueOf((new StringBuilder()).append("Set ").append(var3.getName()).append(" to ").append(var1[3].toUpperCase())));
                  } else if (var3.isMode()) {
                     if (var3.getOptions().contains(var1[3])) {
                        var3.setValue(var1[3]);
                        sendChatMessage(String.valueOf((new StringBuilder()).append("Set ").append(var3.getName()).append(" to ").append(var1[3].toUpperCase())));
                     } else {
                        sendChatMessage(String.valueOf((new StringBuilder()).append("Option ").append(var1[3]).append(" not found!")));
                     }
                  } else if (var3.isNumber()) {
                     if (Wrapper.getFileManager().determineNumber(var3.getValue()).equalsIgnoreCase("INTEGER")) {
                        var3.setValue(Integer.parseInt(var1[3]));
                     } else if (Wrapper.getFileManager().determineNumber(var3.getValue()).equalsIgnoreCase("FLOAT")) {
                        var3.setValue(Float.parseFloat(var1[3]));
                     } else if (Wrapper.getFileManager().determineNumber(var3.getValue()).equalsIgnoreCase("DOUBLE")) {
                        var3.setValue(Double.parseDouble(var1[3]));
                     } else {
                        sendChatMessage("UNKNOWN NUMBER VALUE");
                     }

                     sendChatMessage(String.valueOf((new StringBuilder()).append("Set ").append(var3.getName()).append(" to ").append(var1[3])));
                  }
               } catch (Exception var6) {
                  var6.printStackTrace();
                  sendChatMessage("Error occured when setting value.");
               }

            }
         }
      }
   }

   public SetCommand() {
      super("set", "Sets the settings of a module", new String[]{Xulu.MODULE_MANAGER.getModules().toString()});
   }
}
