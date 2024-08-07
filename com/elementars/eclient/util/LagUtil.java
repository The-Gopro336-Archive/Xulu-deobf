package com.elementars.eclient.util;

import com.elementars.eclient.Xulu;
import com.elementars.eclient.event.EventTarget;
import com.elementars.eclient.event.events.EventReceivePacket;
import net.minecraft.network.play.server.SPacketTimeUpdate;

public class LagUtil {
   // $FF: synthetic field
   public static LagUtil INSTANCE;
   // $FF: synthetic field
   private long timeLastTimeUpdate = -1L;

   public LagUtil() {
      Xulu.EVENT_MANAGER.register(this);
   }

   public long getLastTimeDiff() {
      return this.timeLastTimeUpdate != -1L ? System.currentTimeMillis() - this.timeLastTimeUpdate : 0L;
   }

   @EventTarget
   public void onPacketPreceived(EventReceivePacket var1) {
      if (var1.getPacket() instanceof SPacketTimeUpdate) {
         this.timeLastTimeUpdate = System.currentTimeMillis();
         INSTANCE.timeLastTimeUpdate = this.timeLastTimeUpdate;
      }

   }
}
