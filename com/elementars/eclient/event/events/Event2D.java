package com.elementars.eclient.event.events;

import com.elementars.eclient.event.Event;

public class Event2D extends Event {
   // $FF: synthetic field
   private float width;
   // $FF: synthetic field
   private float height;

   public float getWidth() {
      return this.width;
   }

   public Event2D(float var1, float var2) {
      this.width = var1;
      this.height = var2;
   }

   public float getHeight() {
      return this.height;
   }
}
