package com.elementars.eclient.module.render;

import com.elementars.eclient.command.Command;
import com.elementars.eclient.event.EventTarget;
import com.elementars.eclient.event.events.EventReceivePacket;
import com.elementars.eclient.module.Category;
import com.elementars.eclient.module.Module;
import com.elementars.eclient.util.ColorTextUtils;
import dev.xulu.settings.Value;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import net.minecraft.network.play.server.SPacketChat;
import net.minecraft.util.text.ChatType;
import net.minecraft.util.text.TextComponentString;

public class Chat extends Module {
   // $FF: synthetic field
   public final Value customFont = this.register(new Value("Custom Font", this, false));
   // $FF: synthetic field
   private final Value namemode = this.register(new Value("Highlight Mode", this, "Highlight", new ArrayList(Arrays.asList("Highlight", "Hide"))));
   // $FF: synthetic field
   private final Value bracketmode;
   // $FF: synthetic field
   public static Chat INSTANCE;
   // $FF: synthetic field
   private final Value mode;
   // $FF: synthetic field
   public static Value nochatshadow;
   // $FF: synthetic field
   private final Value color;
   // $FF: synthetic field
   private final Value playername = this.register(new Value("Player Tag", this, "<Player>", new String[]{"<Player>", "[Player]:", "Player:", "Player ->"}));
   // $FF: synthetic field
   public static TextComponentString componentStringOld;
   // $FF: synthetic field
   private final Value timestamps;
   // $FF: synthetic field
   private final Value playerColor;
   // $FF: synthetic field
   private final Value ncb = this.register(new Value("No Chat Background", this, false));
   // $FF: synthetic field
   private final Value namehighlight = this.register(new Value("Name Highlight", this, false));

   public Chat() {
      super("Chat", "Tampers with chat", 0, Category.RENDER, true);
      this.playerColor = this.register(new Value("Player Color", this, "White", ColorTextUtils.colors));
      this.timestamps = this.register(new Value("Time Stamps", this, false));
      this.mode = this.register(new Value("24 Hour Time", this, false));
      this.bracketmode = this.register(new Value("Bracket Type", this, "<>", new ArrayList(Arrays.asList("()", "<>", "[]", "{}"))));
      this.color = this.register(new Value("Color", this, "LightGray", ColorTextUtils.colors));
      nochatshadow = this.register(new Value("No Chat Shadow", this, false));
      INSTANCE = this;
   }

   @EventTarget
   public void onPacket(EventReceivePacket var1) {
      if (var1.getPacket() instanceof SPacketChat) {
         SPacketChat var2 = (SPacketChat)var1.getPacket();
         if (var2.getType() != ChatType.GAME_INFO && this.tryProcessChat(var2.getChatComponent().getFormattedText(), var2.getChatComponent().getUnformattedText())) {
            var1.setCancelled(true);
         }
      }

   }

   private boolean tryProcessChat(String var1, String var2) {
      String[] var4 = var1.split(" ");
      String[] var5 = var2.split(" ");
      var4[0] = var5[0];
      String var6;
      if (var4[0].startsWith("<") && var4[0].endsWith(">")) {
         var4[0] = var4[0].replaceAll("<", "");
         var4[0] = var4[0].replaceAll(">", "");
         var4[0] = String.valueOf((new StringBuilder()).append(Command.SECTIONSIGN()).append(ColorTextUtils.getColor((String)this.playerColor.getValue()).substring(1)).append(var4[0]).append(Command.SECTIONSIGN()).append("r"));
         int var7;
         if (((String)this.playername.getValue()).equalsIgnoreCase("<Player>")) {
            var6 = String.valueOf((new StringBuilder()).append("<").append(var4[0]).append(">"));

            for(var7 = 1; var7 < var4.length; ++var7) {
               var6 = String.valueOf((new StringBuilder()).append(var6).append(" ").append(var4[var7]));
            }

            var1 = var6;
         } else if (((String)this.playername.getValue()).equalsIgnoreCase("[Player]:")) {
            var6 = String.valueOf((new StringBuilder()).append("[").append(var4[0]).append("]:"));

            for(var7 = 1; var7 < var4.length; ++var7) {
               var6 = String.valueOf((new StringBuilder()).append(var6).append(" ").append(var4[var7]));
            }

            var1 = var6;
         } else if (((String)this.playername.getValue()).equalsIgnoreCase("Player:")) {
            var6 = String.valueOf((new StringBuilder()).append(var4[0]).append(":"));

            for(var7 = 1; var7 < var4.length; ++var7) {
               var6 = String.valueOf((new StringBuilder()).append(var6).append(" ").append(var4[var7]));
            }

            var1 = var6;
         } else if (((String)this.playername.getValue()).equalsIgnoreCase("Player ->")) {
            var6 = String.valueOf((new StringBuilder()).append(var4[0]).append(" ->"));

            for(var7 = 1; var7 < var4.length; ++var7) {
               var6 = String.valueOf((new StringBuilder()).append(var6).append(" ").append(var4[var7]));
            }

            var1 = var6;
         } else {
            var6 = String.valueOf((new StringBuilder()).append("<").append(var4[0]).append(">"));

            for(var7 = 1; var7 < var4.length; ++var7) {
               var6 = String.valueOf((new StringBuilder()).append(var6).append(" ").append(var4[var7]));
            }

            var1 = var6;
         }
      }

      String var3 = var1;
      if ((Boolean)this.timestamps.getValue()) {
         var6 = "";
         if ((Boolean)this.mode.getValue()) {
            var6 = (new SimpleDateFormat("k:mm")).format(new Date());
         } else {
            var6 = (new SimpleDateFormat("h:mm a")).format(new Date());
         }

         if (((String)this.bracketmode.getValue()).equalsIgnoreCase("<>")) {
            var3 = String.valueOf((new StringBuilder()).append("§").append(ColorTextUtils.getColor((String)this.color.getValue()).substring(1)).append("<").append(var6).append(">§r ").append(var1));
         } else if (((String)this.bracketmode.getValue()).equalsIgnoreCase("()")) {
            var3 = String.valueOf((new StringBuilder()).append("§").append(ColorTextUtils.getColor((String)this.color.getValue()).substring(1)).append("(").append(var6).append(")§r ").append(var1));
         } else if (((String)this.bracketmode.getValue()).equalsIgnoreCase("[]")) {
            var3 = String.valueOf((new StringBuilder()).append("§").append(ColorTextUtils.getColor((String)this.color.getValue()).substring(1)).append("[").append(var6).append("]§r ").append(var1));
         } else if (((String)this.bracketmode.getValue()).equalsIgnoreCase("{}")) {
            var3 = String.valueOf((new StringBuilder()).append("§").append(ColorTextUtils.getColor((String)this.color.getValue()).substring(1)).append("{").append(var6).append("}§r ").append(var1));
         }
      }

      if ((Boolean)this.namehighlight.getValue()) {
         if (mc.player == null) {
            return false;
         }

         if (((String)this.namemode.getValue()).equalsIgnoreCase("Hide")) {
            var3 = var3.replace(mc.player.getName(), "HIDDEN");
         } else {
            var3 = var3.replace(mc.player.getName(), String.valueOf((new StringBuilder()).append("§b").append(mc.player.getName()).append("§r")));
         }
      }

      Command.sendRawChatMessage(var3);
      return true;
   }
}
