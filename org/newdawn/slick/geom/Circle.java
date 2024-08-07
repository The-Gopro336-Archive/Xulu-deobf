package org.newdawn.slick.geom;

public class Circle extends Ellipse {
   // $FF: synthetic field
   public float radius;

   private strictfp boolean contains(Line var1) {
      return this.contains(var1.getX1(), var1.getY1()) && this.contains(var1.getX2(), var1.getY2());
   }

   protected strictfp void findCenter() {
      this.center = new float[2];
      this.center[0] = this.x + this.radius;
      this.center[1] = this.y + this.radius;
   }

   public strictfp Circle(float var1, float var2, float var3) {
      this(var1, var2, var3, 50);
   }

   protected strictfp void calculateRadius() {
      this.boundingCircleRadius = this.radius;
   }

   public strictfp boolean intersects(Shape var1) {
      if (var1 instanceof Circle) {
         Circle var2 = (Circle)var1;
         float var3 = this.getRadius() + var2.getRadius();
         if (Math.abs(var2.getCenterX() - this.getCenterX()) > var3) {
            return false;
         } else if (Math.abs(var2.getCenterY() - this.getCenterY()) > var3) {
            return false;
         } else {
            var3 *= var3;
            float var4 = Math.abs(var2.getCenterX() - this.getCenterX());
            float var5 = Math.abs(var2.getCenterY() - this.getCenterY());
            return var3 >= var4 * var4 + var5 * var5;
         }
      } else {
         return var1 instanceof Rectangle ? this.intersects((Rectangle)var1) : super.intersects(var1);
      }
   }

   public strictfp float getRadius() {
      return this.radius;
   }

   private strictfp boolean intersects(Line var1) {
      Vector2f var2 = new Vector2f(var1.getX1(), var1.getY1());
      Vector2f var3 = new Vector2f(var1.getX2(), var1.getY2());
      Vector2f var4 = new Vector2f(this.getCenterX(), this.getCenterY());
      Vector2f var6 = var3.copy().sub(var2);
      Vector2f var7 = var4.copy().sub(var2);
      float var8 = var6.length();
      float var9 = var7.dot(var6) / var8;
      Vector2f var5;
      if (var9 < 0.0F) {
         var5 = var2;
      } else if (var9 > var8) {
         var5 = var3;
      } else {
         Vector2f var10 = var6.copy().scale(var9 / var8);
         var5 = var2.copy().add(var10);
      }

      boolean var11 = var4.copy().sub(var5).lengthSquared() <= this.getRadius() * this.getRadius();
      return var11;
   }

   public strictfp float getCenterY() {
      return this.getY() + this.radius;
   }

   private strictfp boolean intersects(Rectangle var1) {
      if (var1.contains(this.x + this.radius, this.y + this.radius)) {
         return true;
      } else {
         float var4 = var1.getX();
         float var5 = var1.getY();
         float var6 = var1.getX() + var1.getWidth();
         float var7 = var1.getY() + var1.getHeight();
         Line[] var8 = new Line[]{new Line(var4, var5, var6, var5), new Line(var6, var5, var6, var7), new Line(var6, var7, var4, var7), new Line(var4, var7, var4, var5)};
         float var9 = this.getRadius() * this.getRadius();
         Vector2f var10 = new Vector2f(this.getCenterX(), this.getCenterY());

         for(int var11 = 0; var11 < 4; ++var11) {
            float var12 = var8[var11].distanceSquared(var10);
            if (var12 < var9) {
               return true;
            }
         }

         return false;
      }
   }

   public strictfp float getCenterX() {
      return this.getX() + this.radius;
   }

   public strictfp boolean contains(float var1, float var2) {
      float var3 = var1 - this.getCenterX();
      float var4 = var2 - this.getCenterY();
      return var3 * var3 + var4 * var4 < this.getRadius() * this.getRadius();
   }

   public strictfp void setRadius(float var1) {
      if (var1 != this.radius) {
         this.pointsDirty = true;
         this.radius = var1;
         this.setRadii(var1, var1);
      }

   }

   public strictfp float[] getCenter() {
      return new float[]{this.getCenterX(), this.getCenterY()};
   }

   public strictfp Circle(float var1, float var2, float var3, int var4) {
      super(var1, var2, var3, var3, var4);
      this.x = var1 - var3;
      this.y = var2 - var3;
      this.radius = var3;
      this.boundingCircleRadius = var3;
   }
}
