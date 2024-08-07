package com.elementars.eclient.event.events;

import com.elementars.eclient.event.Event;

public class Event3D extends Event {
   // $FF: synthetic field
   private float partialTicks;

   public float getPartialTicks() {
      return this.partialTicks;
   }

   public Event3D(float var1) {
      this.partialTicks = var1;
   }
}
