package com.elementars.eclient.event.events;

import com.elementars.eclient.event.Event;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.EntityThrowable;

public class EventThrow extends Event {
   // $FF: synthetic field
   private EntityThrowable entity;
   // $FF: synthetic field
   private float rotation;
   // $FF: synthetic field
   private Entity thrower;

   public float getRotation() {
      return this.rotation;
   }

   public EntityThrowable getEntity() {
      return this.entity;
   }

   public Entity getThrower() {
      return this.thrower;
   }

   public EventThrow(Entity var1, EntityThrowable var2, float var3) {
      this.thrower = var1;
      this.entity = var2;
      this.rotation = var3;
   }
}
