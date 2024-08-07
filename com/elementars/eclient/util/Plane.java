package com.elementars.eclient.util;

public class Plane {
   // $FF: synthetic field
   private final double y;
   // $FF: synthetic field
   private final double x;
   // $FF: synthetic field
   private final boolean visible;

   public boolean isVisible() {
      return this.visible;
   }

   public Plane(double var1, double var3, boolean var5) {
      this.x = var1;
      this.y = var3;
      this.visible = var5;
   }

   public double getX() {
      return this.x;
   }

   public double getY() {
      return this.y;
   }
}
