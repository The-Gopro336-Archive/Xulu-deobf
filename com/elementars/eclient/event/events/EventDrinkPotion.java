package com.elementars.eclient.event.events;

import com.elementars.eclient.event.Event;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;

public class EventDrinkPotion extends Event {
   // $FF: synthetic field
   EntityLivingBase entityLivingBase;
   // $FF: synthetic field
   ItemStack stack;

   public ItemStack getStack() {
      return this.stack;
   }

   public EventDrinkPotion(EntityLivingBase var1, ItemStack var2) {
      this.entityLivingBase = var1;
      this.stack = var2;
   }

   public EntityLivingBase getEntityLivingBase() {
      return this.entityLivingBase;
   }
}
