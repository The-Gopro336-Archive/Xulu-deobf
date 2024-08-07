package com.elementars.eclient.event.events;

import com.elementars.eclient.event.Event;
import com.elementars.eclient.util.Wrapper;

public class EventPreMotionUpdates extends Event {
   // $FF: synthetic field
   private boolean cancel;
   // $FF: synthetic field
   public double y;
   // $FF: synthetic field
   public float yaw;
   // $FF: synthetic field
   public float pitch;

   public EventPreMotionUpdates(float var1, float var2, double var3) {
      this.yaw = var1;
      this.pitch = var2;
      this.y = var3;
   }

   public void setYaw(float var1) {
      this.yaw = var1;
   }

   public double getY() {
      return this.y;
   }

   public void setCancelled(boolean var1) {
      this.cancel = var1;
   }

   public float getPitch() {
      return this.pitch;
   }

   public float getYaw() {
      return this.yaw;
   }

   public void setMotion(double var1, double var3, double var5) {
      Wrapper.getMinecraft().player.motionX = var1;
      Wrapper.getMinecraft().player.motionY = var3;
      Wrapper.getMinecraft().player.motionZ = var5;
   }

   public void setPitch(float var1) {
      this.pitch = var1;
   }

   public void setY(double var1) {
      this.y = var1;
   }
}
