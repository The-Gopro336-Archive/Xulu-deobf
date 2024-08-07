package com.elementars.eclient.command.commands;

import com.elementars.eclient.Xulu;
import com.elementars.eclient.command.Command;
import com.elementars.eclient.module.render.Search;
import java.util.Iterator;
import net.minecraft.block.Block;

public class SearchCommand extends Command {
   public void execute(String[] var1) {
      if (var1.length > 1 && var1[1].equalsIgnoreCase("help")) {
         this.showSyntax(var1[0]);
      } else if (var1.length < 2) {
         sendChatMessage("Specify an option. Try doing .search help to see command options");
      } else {
         if (var1[1].equalsIgnoreCase("add")) {
            if (var1.length < 3) {
               sendChatMessage("Please specify a block.");
               return;
            }

            if (Search.addBlock(var1[2])) {
               sendChatMessage(String.valueOf((new StringBuilder()).append("Added ").append(var1[2]).append(" to Search!")));
               mc.renderGlobal.loadRenderers();
            } else {
               sendChatMessage("Unknown block!");
            }
         } else if (var1[1].equalsIgnoreCase("remove")) {
            if (var1.length < 3) {
               sendChatMessage("Please specify a block.");
               return;
            }

            if (Search.delBlock(var1[2])) {
               sendChatMessage(String.valueOf((new StringBuilder()).append("Removed ").append(var1[2]).append(" from Search!")));
               ((Search)Xulu.MODULE_MANAGER.getModuleT(Search.class)).posList.clear();
               mc.renderGlobal.loadRenderers();
            } else {
               sendChatMessage("Unknown block!");
            }
         } else if (var1[1].equalsIgnoreCase("list")) {
            sendChatMessage(String.valueOf((new StringBuilder()).append("Search blocks &7(").append(Search.getBLOCKS().size()).append(")&r: ")));
            String var2 = "";
            boolean var3 = true;

            for(Iterator var4 = Search.getBLOCKS().iterator(); var4.hasNext(); var3 = false) {
               Block var5 = (Block)var4.next();
               if (var3) {
                  var2 = var5.getLocalizedName();
               } else {
                  var2 = String.valueOf((new StringBuilder()).append(var2).append(", ").append(var5.getLocalizedName()));
               }
            }

            sendChatMessage(var2);
         } else {
            sendChatMessage("Unknown arguments!");
         }

      }
   }

   public SearchCommand() {
      super("search", "Manages Search", new String[]{"add", "remove", "list"});
   }
}
