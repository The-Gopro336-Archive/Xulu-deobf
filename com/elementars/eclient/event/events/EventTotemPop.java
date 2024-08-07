package com.elementars.eclient.event.events;

import com.elementars.eclient.event.Event;
import net.minecraft.entity.player.EntityPlayer;

public class EventTotemPop extends Event {
   // $FF: synthetic field
   EntityPlayer player;

   public EntityPlayer getPlayer() {
      return this.player;
   }

   public EventTotemPop(EntityPlayer var1) {
      this.player = var1;
   }
}
