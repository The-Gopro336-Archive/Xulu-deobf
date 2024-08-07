package com.elementars.eclient.command.commands;

import com.elementars.eclient.Xulu;
import com.elementars.eclient.command.Command;
import com.elementars.eclient.dummy.DummyMod;
import com.elementars.eclient.module.Module;
import dev.xulu.newgui.NewGUI;
import dev.xulu.newgui.Panel;
import dev.xulu.newgui.elements.ModuleButton;
import java.util.Iterator;
import java.util.Objects;

public class DummyCommand extends Command {
   public void execute(String[] var1) {
      if (var1.length < 2) {
         sendChatMessage("Try .enemy add or .enemy del");
      } else {
         if (var1[1].equalsIgnoreCase("help")) {
            this.showSyntax(var1[0]);
            sendChatMessage("Usage 1: .dummymod add (name)");
            sendChatMessage("Usage 2: .dummymod add (name) (info)");
            sendChatMessage("Info is what is displayed in brackets in the featurelist");
         }

         if (var1.length < 3) {
            sendChatMessage("Specify a name");
         } else {
            DummyMod var2;
            Panel var3;
            Iterator var4;
            Module var5;
            Panel var6;
            boolean var8;
            if (var1.length == 3) {
               var2 = new DummyMod(var1[2]);
               if (var1[1].equalsIgnoreCase("add")) {
                  if (!Xulu.MODULE_MANAGER.getModules().contains(var2)) {
                     Xulu.MODULE_MANAGER.getModules().add(var2);
                     var3 = NewGUI.getPanelByName("Dummy");
                     if (var3 != null) {
                        var3.Elements.add(new ModuleButton(var2, var3));
                     }

                     sendChatMessage(String.valueOf((new StringBuilder()).append("Dummy mod ").append(var2.getName()).append(" added.")));
                  } else {
                     sendChatMessage(String.valueOf((new StringBuilder()).append(var2.getName()).append(" already exists!")));
                  }
               } else if (var1[1].equalsIgnoreCase("del")) {
                  var8 = false;
                  var4 = Xulu.MODULE_MANAGER.getModules().iterator();

                  while(var4.hasNext()) {
                     var5 = (Module)var4.next();
                     if (var5.getName().equalsIgnoreCase(var2.getName())) {
                        Xulu.MODULE_MANAGER.getModules().remove(var5);
                        var6 = NewGUI.getPanelByName("Dummy");
                        if (var6 != null) {
                           var6.Elements.stream().filter((var0) -> {
                              return var0 instanceof ModuleButton;
                           }).map((var0) -> {
                              return var0;
                           }).forEach((var2x) -> {
                              if (var2x.mod.getName().equalsIgnoreCase(var2.getName())) {
                                 var6.Elements.remove(var2x);
                              }

                           });
                        }

                        sendChatMessage(String.valueOf((new StringBuilder()).append("Dummy mod ").append(var2.getName()).append(" removed.")));
                        var8 = true;
                     }
                  }

                  if (!var8) {
                     sendChatMessage(String.valueOf((new StringBuilder()).append(var2.getName()).append(" isn't a mod!")));
                  }
               } else {
                  sendChatMessage(String.valueOf((new StringBuilder()).append("Unknown attribute '").append(var1[1]).append("'")));
               }
            }

            if (var1.length == 4) {
               var2 = new DummyMod(var1[2], var1[3]);
               if (var1[1].equalsIgnoreCase("add")) {
                  if (!Xulu.MODULE_MANAGER.getModules().contains(var2)) {
                     Xulu.MODULE_MANAGER.getModules().add(var2);
                     var3 = NewGUI.getPanelByName("Dummy");
                     if (var3 != null) {
                        var3.Elements.add(new ModuleButton(var2, var3));
                     }

                     sendChatMessage(String.valueOf((new StringBuilder()).append("Dummy mod ").append(var2.getName()).append(" added.")));
                  } else {
                     sendChatMessage(String.valueOf((new StringBuilder()).append(var2.getName()).append(" already exists!")));
                  }
               } else if (var1[1].equalsIgnoreCase("del")) {
                  var8 = false;
                  var4 = Xulu.MODULE_MANAGER.getModules().iterator();

                  while(var4.hasNext()) {
                     var5 = (Module)var4.next();
                     if (var5.getName().equalsIgnoreCase(var2.getName())) {
                        Xulu.MODULE_MANAGER.getModules().remove(var5);
                        var6 = NewGUI.getPanelByName("Dummy");
                        if (var6 != null) {
                           var6.Elements.stream().filter(Objects::nonNull).forEach((var2x) -> {
                              if (var2x.mod.getName().equalsIgnoreCase(var2.getName())) {
                                 var6.Elements.remove(var2x);
                              }

                           });
                        }

                        sendChatMessage(String.valueOf((new StringBuilder()).append("Dummy mod ").append(var2.getName()).append(" removed.")));
                        var8 = true;
                     }
                  }

                  if (!var8) {
                     sendChatMessage(String.valueOf((new StringBuilder()).append(var2.getName()).append(" isn't a mod!")));
                  }
               } else {
                  sendChatMessage(String.valueOf((new StringBuilder()).append("Unknown attribute '").append(var1[1]).append("'")));
               }
            }

         }
      }
   }

   public DummyCommand() {
      super("dummymod", "Makes some fake modules >:)", new String[]{"add", "del"});
   }
}
