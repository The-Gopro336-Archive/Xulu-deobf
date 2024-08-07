package com.elementars.eclient.module.misc;

import com.elementars.eclient.event.EventTarget;
import com.elementars.eclient.event.events.EventSendPacket;
import com.elementars.eclient.module.Category;
import com.elementars.eclient.module.Module;
import dev.xulu.settings.Value;
import net.minecraft.network.play.client.CPacketChatMessage;

public class CustomChat extends Module {
   // $FF: synthetic field
   private final Value commands = this.register(new Value("Commands", this, false));
   // $FF: synthetic field
   private final Value mode = this.register(new Value("2b2t Mode", this, false));
   // $FF: synthetic field
   private final String suffix2 = " | X U L U";
   // $FF: synthetic field
   private final String suffix1 = " ⏐ ᙭ ᑌ ᒪ ᑌ";

   public CustomChat() {
      super("CustomChat", "Appends XULU to the end of your chat messages", 0, Category.MISC, true);
   }

   @EventTarget
   public void onPacket(EventSendPacket var1) {
      if (var1.getPacket() instanceof CPacketChatMessage) {
         String var2 = ((CPacketChatMessage)var1.getPacket()).getMessage();
         if (var2.startsWith("/") && !(Boolean)this.commands.getValue()) {
            return;
         }

         var2 = String.valueOf((new StringBuilder()).append(var2).append((Boolean)this.mode.getValue() ? " | X U L U" : " ⏐ ᙭ ᑌ ᒪ ᑌ"));
         if (var2.length() >= 256) {
            var2 = var2.substring(0, 256);
         }

         ((CPacketChatMessage)var1.getPacket()).message = var2;
      }

   }
}
