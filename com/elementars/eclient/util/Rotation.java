package com.elementars.eclient.util;

public class Rotation {
   // $FF: synthetic field
   private float pitch;
   // $FF: synthetic field
   private boolean active;
   // $FF: synthetic field
   private float yaw;

   public Rotation(float var1, float var2) {
      this.yaw = var1;
      this.pitch = var2;
   }

   public float getPitch() {
      return this.pitch;
   }

   public Rotation subtract(float var1, float var2) {
      this.yaw -= var1;
      this.pitch -= var2;
      return this;
   }

   public boolean isActive() {
      return this.active;
   }

   public Rotation setYaw(float var1) {
      this.active = true;
      this.yaw = var1;
      return this;
   }

   public Rotation add(float var1, float var2) {
      this.yaw += var1;
      this.pitch += var2;
      return this;
   }

   public float getYaw() {
      return this.yaw;
   }

   public Rotation setPitch(float var1) {
      this.active = true;
      this.pitch = var1;
      return this;
   }
}
