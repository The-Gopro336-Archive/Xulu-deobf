package com.elementars.eclient.event.events;

import com.elementars.eclient.event.Event;
import javax.annotation.Nullable;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.entity.EntityLivingBase;

public class EventRenderEntity extends Event {
   // $FF: synthetic field
   RenderLivingBase renderer;
   // $FF: synthetic field
   EntityLivingBase entity;
   // $FF: synthetic field
   double x;
   // $FF: synthetic field
   ModelBase model;
   // $FF: synthetic field
   double y;
   // $FF: synthetic field
   double z;
   // $FF: synthetic field
   float entityYaw;
   // $FF: synthetic field
   float partialTicks;

   public float getPartialTicks() {
      return this.partialTicks;
   }

   public ModelBase getModel() {
      return this.model;
   }

   public EntityLivingBase getEntity() {
      return this.entity;
   }

   public double getZ() {
      return this.z;
   }

   public double getX() {
      return this.x;
   }

   public float getEntityYaw() {
      return this.entityYaw;
   }

   public RenderLivingBase getRenderer() {
      return this.renderer;
   }

   public double getY() {
      return this.y;
   }

   public EventRenderEntity(@Nullable RenderLivingBase var1, EntityLivingBase var2, @Nullable ModelBase var3, double var4, double var6, double var8, float var10, float var11) {
      this.renderer = var1;
      this.entity = var2;
      this.model = var3;
      this.x = var4;
      this.y = var6;
      this.z = var8;
      this.entityYaw = var10;
      this.partialTicks = var11;
   }
}
