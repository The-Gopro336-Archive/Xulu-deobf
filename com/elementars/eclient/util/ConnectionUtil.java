package com.elementars.eclient.util;

import com.elementars.eclient.Xulu;
import com.elementars.eclient.event.EventTarget;
import com.elementars.eclient.event.events.EventPlayerConnect;
import com.elementars.eclient.event.events.EventReceivePacket;
import com.google.common.base.Strings;
import java.util.Objects;
import java.util.UUID;
import net.minecraft.network.play.server.SPacketPlayerListItem;
import net.minecraft.network.play.server.SPacketPlayerListItem.Action;

public class ConnectionUtil {
   // $FF: synthetic field
   public static ConnectionUtil INSTANCE;

   public ConnectionUtil() {
      Xulu.EVENT_MANAGER.register(this);
   }

   @EventTarget
   public void onScoreboardEvent(EventReceivePacket var1) {
      if (var1.getPacket() instanceof SPacketPlayerListItem) {
         SPacketPlayerListItem var2 = (SPacketPlayerListItem)var1.getPacket();
         if (!Action.ADD_PLAYER.equals(var2.getAction()) && !Action.REMOVE_PLAYER.equals(var2.getAction())) {
            return;
         }

         var2.getEntries().stream().filter(Objects::nonNull).filter((var0) -> {
            return !Strings.isNullOrEmpty(var0.getProfile().getName()) || var0.getProfile().getId() != null;
         }).forEach((var2x) -> {
            UUID var3 = var2x.getProfile().getId();
            String var4 = var2x.getProfile().getName();
            this.fireEvents(var2.getAction(), var3, var4);
         });
      }

   }

   private void fireEvents(Action var1, UUID var2, String var3) {
      if (var2 != null) {
         switch(var1) {
         case ADD_PLAYER:
            EventPlayerConnect.Join var5 = new EventPlayerConnect.Join(var2, var3);
            var5.call();
            break;
         case REMOVE_PLAYER:
            EventPlayerConnect.Leave var4 = new EventPlayerConnect.Leave(var2, var3);
            var4.call();
         }

      }
   }
}
