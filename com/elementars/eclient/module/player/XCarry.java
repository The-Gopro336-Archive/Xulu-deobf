package com.elementars.eclient.module.player;

import com.elementars.eclient.event.EventTarget;
import com.elementars.eclient.event.events.CloseInventoryEvent;
import com.elementars.eclient.event.events.EventSendPacket;
import com.elementars.eclient.module.Category;
import com.elementars.eclient.module.Module;
import net.minecraft.network.play.client.CPacketCloseWindow;

public class XCarry extends Module {
   public XCarry() {
      super("XCarry", "Holds things in your crafting menu", 0, Category.PLAYER, true);
   }

   @EventTarget
   public void onPacket(EventSendPacket var1) {
      if (var1.getPacket() instanceof CPacketCloseWindow) {
         CPacketCloseWindow var2 = (CPacketCloseWindow)var1.getPacket();
         var1.setCancelled(var2.windowId == 0);
      }

   }

   @EventTarget
   public void onClose(CloseInventoryEvent var1) {
      var1.setCancelled(true);
   }
}
