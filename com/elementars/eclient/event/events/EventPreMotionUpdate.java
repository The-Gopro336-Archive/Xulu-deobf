package com.elementars.eclient.event.events;

import com.elementars.eclient.event.Event;

public class EventPreMotionUpdate extends Event {
   // $FF: synthetic field
   public double y;
   // $FF: synthetic field
   public double x;
   // $FF: synthetic field
   public double z;
   // $FF: synthetic field
   private float pitch;
   // $FF: synthetic field
   private float yaw;
   // $FF: synthetic field
   private boolean ground;

   public EventPreMotionUpdate(float var1, float var2, boolean var3, double var4, double var6, double var8) {
      this.yaw = var1;
      this.pitch = var2;
      this.ground = var3;
      this.x = var4;
      this.y = var6;
      this.z = var8;
   }

   public void setYaw(float var1) {
      this.yaw = var1;
   }

   public float getYaw() {
      return this.yaw;
   }

   public void setPitch(float var1) {
      this.pitch = var1;
   }

   public float getPitch() {
      return this.pitch;
   }

   public void setGround(boolean var1) {
      this.ground = var1;
   }

   public boolean onGround() {
      return this.ground;
   }
}
