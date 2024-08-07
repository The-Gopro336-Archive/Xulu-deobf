package com.elementars.eclient.event.events;

import com.elementars.eclient.event.Event;
import net.minecraft.entity.EntityLivingBase;

public class LocalPlayerUpdateEvent extends Event {
   // $FF: synthetic field
   EntityLivingBase entityLivingBase;

   public LocalPlayerUpdateEvent(EntityLivingBase var1) {
      this.entityLivingBase = var1;
   }

   public EntityLivingBase getEntityLivingBase() {
      return this.entityLivingBase;
   }
}
