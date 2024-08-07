package org.newdawn.slick.geom;

public class Line extends Shape {
   // $FF: synthetic field
   private boolean outerEdge;
   // $FF: synthetic field
   private Vector2f loc;
   // $FF: synthetic field
   private Vector2f other;
   // $FF: synthetic field
   private Vector2f proj;
   // $FF: synthetic field
   private Vector2f end;
   // $FF: synthetic field
   private Vector2f closest;
   // $FF: synthetic field
   private Vector2f v2;
   // $FF: synthetic field
   private boolean innerEdge;
   // $FF: synthetic field
   private float lenSquared;
   // $FF: synthetic field
   private Vector2f vec;
   // $FF: synthetic field
   private Vector2f v;
   // $FF: synthetic field
   private Vector2f start;

   public Line(float var1, float var2, float var3, float var4) {
      this(new Vector2f(var1, var2), new Vector2f(var3, var4));
   }

   public float getY() {
      return this.getY1();
   }

   public float getX1() {
      return this.start.getX();
   }

   public Vector2f getEnd() {
      return this.end;
   }

   public float length() {
      return this.vec.length();
   }

   public void getClosestPoint(Vector2f var1, Vector2f var2) {
      this.loc.set(var1);
      this.loc.sub(this.start);
      float var3 = this.vec.dot(this.loc);
      var3 /= this.vec.lengthSquared();
      if (var3 < 0.0F) {
         var2.set(this.start);
      } else if (var3 > 1.0F) {
         var2.set(this.end);
      } else {
         var2.x = this.start.getX() + var3 * this.vec.getX();
         var2.y = this.start.getY() + var3 * this.vec.getY();
      }
   }

   public Line(float[] var1, float[] var2) {
      this.loc = new Vector2f(0.0F, 0.0F);
      this.v = new Vector2f(0.0F, 0.0F);
      this.v2 = new Vector2f(0.0F, 0.0F);
      this.proj = new Vector2f(0.0F, 0.0F);
      this.closest = new Vector2f(0.0F, 0.0F);
      this.other = new Vector2f(0.0F, 0.0F);
      this.outerEdge = true;
      this.innerEdge = true;
      this.set(var1, var2);
   }

   public float distanceSquared(Vector2f var1) {
      this.getClosestPoint(var1, this.closest);
      this.closest.sub(var1);
      float var2 = this.closest.lengthSquared();
      return var2;
   }

   public float getX() {
      return this.getX1();
   }

   public float getDY() {
      return this.end.getY() - this.start.getY();
   }

   public float getY1() {
      return this.start.getY();
   }

   public float getX2() {
      return this.end.getX();
   }

   public boolean intersects(Shape var1) {
      return var1 instanceof Circle ? var1.intersects(this) : super.intersects(var1);
   }

   public String toString() {
      return String.valueOf((new StringBuilder()).append("[Line ").append(this.start).append(",").append(this.end).append("]"));
   }

   protected void createPoints() {
      this.points = new float[4];
      this.points[0] = this.getX1();
      this.points[1] = this.getY1();
      this.points[2] = this.getX2();
      this.points[3] = this.getY2();
   }

   public void set(float[] var1, float[] var2) {
      this.set(var1[0], var1[1], var2[0], var2[1]);
   }

   public float distance(Vector2f var1) {
      return (float)Math.sqrt((double)this.distanceSquared(var1));
   }

   public Line(Vector2f var1, Vector2f var2) {
      this.loc = new Vector2f(0.0F, 0.0F);
      this.v = new Vector2f(0.0F, 0.0F);
      this.v2 = new Vector2f(0.0F, 0.0F);
      this.proj = new Vector2f(0.0F, 0.0F);
      this.closest = new Vector2f(0.0F, 0.0F);
      this.other = new Vector2f(0.0F, 0.0F);
      this.outerEdge = true;
      this.innerEdge = true;
      this.set(var1, var2);
   }

   public Line(float var1, float var2, float var3, float var4, boolean var5) {
      this(new Vector2f(var1, var2), new Vector2f(var1 + var3, var2 + var4));
   }

   public Line(float var1, float var2) {
      this(var1, var2, true, true);
   }

   public Line(float var1, float var2, boolean var3, boolean var4) {
      this(0.0F, 0.0F, var1, var2);
   }

   public float getDX() {
      return this.end.getX() - this.start.getX();
   }

   public void set(float var1, float var2, float var3, float var4) {
      super.pointsDirty = true;
      this.start.set(var1, var2);
      this.end.set(var3, var4);
      float var5 = var3 - var1;
      float var6 = var4 - var2;
      this.vec.set(var5, var6);
      this.lenSquared = var5 * var5 + var6 * var6;
   }

   public float getY2() {
      return this.end.getY();
   }

   public Shape transform(Transform var1) {
      float[] var2 = new float[4];
      this.createPoints();
      var1.transform(this.points, 0, var2, 0, 2);
      return new Line(var2[0], var2[1], var2[2], var2[3]);
   }

   public boolean on(Vector2f var1) {
      this.getClosestPoint(var1, this.closest);
      return var1.equals(this.closest);
   }

   public Vector2f intersect(Line var1) {
      return this.intersect(var1, false);
   }

   public void set(Vector2f var1, Vector2f var2) {
      super.pointsDirty = true;
      if (this.start == null) {
         this.start = new Vector2f();
      }

      this.start.set(var1);
      if (this.end == null) {
         this.end = new Vector2f();
      }

      this.end.set(var2);
      this.vec = new Vector2f(var2);
      this.vec.sub(var1);
      this.lenSquared = this.vec.lengthSquared();
   }

   public boolean intersect(Line var1, boolean var2, Vector2f var3) {
      float var4 = this.end.getX() - this.start.getX();
      float var5 = var1.end.getX() - var1.start.getX();
      float var6 = this.end.getY() - this.start.getY();
      float var7 = var1.end.getY() - var1.start.getY();
      float var8 = var7 * var4 - var5 * var6;
      if (var8 == 0.0F) {
         return false;
      } else {
         float var9 = var5 * (this.start.getY() - var1.start.getY()) - var7 * (this.start.getX() - var1.start.getX());
         var9 /= var8;
         float var10 = var4 * (this.start.getY() - var1.start.getY()) - var6 * (this.start.getX() - var1.start.getX());
         var10 /= var8;
         if (!var2 || !(var9 < 0.0F) && !(var9 > 1.0F) && !(var10 < 0.0F) && !(var10 > 1.0F)) {
            float var12 = this.start.getX() + var9 * (this.end.getX() - this.start.getX());
            float var13 = this.start.getY() + var9 * (this.end.getY() - this.start.getY());
            var3.set(var12, var13);
            return true;
         } else {
            return false;
         }
      }
   }

   public float lengthSquared() {
      return this.vec.lengthSquared();
   }

   public Vector2f intersect(Line var1, boolean var2) {
      Vector2f var3 = new Vector2f();
      return !this.intersect(var1, var2, var3) ? null : var3;
   }

   public boolean closed() {
      return false;
   }

   public Vector2f getStart() {
      return this.start;
   }
}
