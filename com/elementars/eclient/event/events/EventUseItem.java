package com.elementars.eclient.event.events;

import com.elementars.eclient.event.Event;
import net.minecraft.entity.player.EntityPlayer;

public class EventUseItem extends Event {
   // $FF: synthetic field
   EntityPlayer player;

   public EntityPlayer getPlayer() {
      return this.player;
   }

   public EventUseItem(EntityPlayer var1) {
      this.player = var1;
   }
}
