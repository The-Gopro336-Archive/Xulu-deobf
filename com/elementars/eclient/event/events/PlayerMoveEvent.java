package com.elementars.eclient.event.events;

import com.elementars.eclient.event.Event;
import net.minecraft.entity.MoverType;

public class PlayerMoveEvent extends Event {
   // $FF: synthetic field
   private MoverType type;
   // $FF: synthetic field
   private double x;
   // $FF: synthetic field
   private double z;
   // $FF: synthetic field
   private double y;

   public double getY() {
      return this.y;
   }

   public double getZ() {
      return this.z;
   }

   public PlayerMoveEvent(MoverType var1, double var2, double var4, double var6) {
      this.type = var1;
      this.x = var2;
      this.y = var4;
      this.z = var6;
   }

   public void setX(double var1) {
      this.x = var1;
   }

   public void setY(double var1) {
      this.y = var1;
   }

   public MoverType getType() {
      return this.type;
   }

   public void setZ(double var1) {
      this.z = var1;
   }

   public void setType(MoverType var1) {
      this.type = var1;
   }

   public double getX() {
      return this.x;
   }
}
