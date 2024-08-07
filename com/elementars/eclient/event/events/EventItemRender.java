package com.elementars.eclient.event.events;

import com.elementars.eclient.event.Event;
import net.minecraft.client.renderer.culling.ICamera;
import net.minecraft.entity.Entity;

public class EventItemRender extends Event {
   // $FF: synthetic field
   public Entity entity;
   // $FF: synthetic field
   public ICamera camera;
   // $FF: synthetic field
   public float n;

   public EventItemRender(Entity var1, ICamera var2, float var3) {
      this.entity = var1;
      this.camera = var2;
      this.n = var3;
   }
}
