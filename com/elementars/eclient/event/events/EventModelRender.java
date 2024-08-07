package com.elementars.eclient.event.events;

import com.elementars.eclient.event.Event;
import net.minecraft.client.model.ModelBase;
import net.minecraft.entity.Entity;

public class EventModelRender extends Event {
   // $FF: synthetic field
   public ModelBase modelBase;
   // $FF: synthetic field
   public float ageInTicks;
   // $FF: synthetic field
   public float limbSwing;
   // $FF: synthetic field
   public float scaleFactor;
   // $FF: synthetic field
   public float headPitch;
   // $FF: synthetic field
   public float netHeadYaw;
   // $FF: synthetic field
   public float limbSwingAmount;
   // $FF: synthetic field
   public Entity entity;

   public EventModelRender(ModelBase var1, Entity var2, float var3, float var4, float var5, float var6, float var7, float var8) {
      this.modelBase = var1;
      this.entity = var2;
      this.limbSwing = var3;
      this.limbSwingAmount = var4;
      this.ageInTicks = var5;
      this.netHeadYaw = var6;
      this.headPitch = var7;
      this.scaleFactor = var8;
   }
}
