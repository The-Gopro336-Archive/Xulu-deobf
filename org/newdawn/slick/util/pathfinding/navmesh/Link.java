package org.newdawn.slick.util.pathfinding.navmesh;

public class Link {
   // $FF: synthetic field
   private Space target;
   // $FF: synthetic field
   private float py;
   // $FF: synthetic field
   private float px;

   public Space getTarget() {
      return this.target;
   }

   public float getX() {
      return this.px;
   }

   public float distance2(float var1, float var2) {
      float var3 = var1 - this.px;
      float var4 = var2 - this.py;
      return var3 * var3 + var4 * var4;
   }

   public Link(float var1, float var2, Space var3) {
      this.px = var1;
      this.py = var2;
      this.target = var3;
   }

   public float getY() {
      return this.py;
   }
}
