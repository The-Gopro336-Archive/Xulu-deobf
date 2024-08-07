package org.newdawn.slick.geom;

import java.io.Serializable;

public abstract class Shape implements Serializable {
   // $FF: synthetic field
   protected float maxX;
   // $FF: synthetic field
   protected float minY;
   // $FF: synthetic field
   protected float x;
   // $FF: synthetic field
   protected float maxY;
   // $FF: synthetic field
   protected float y;
   // $FF: synthetic field
   protected float[] points;
   // $FF: synthetic field
   protected boolean trianglesDirty;
   // $FF: synthetic field
   protected float boundingCircleRadius;
   // $FF: synthetic field
   protected float minX;
   // $FF: synthetic field
   protected boolean pointsDirty = true;
   // $FF: synthetic field
   protected float[] center;
   // $FF: synthetic field
   protected transient Triangulator tris;

   public void setCenterX(float var1) {
      if (this.points == null || this.center == null) {
         this.checkPoints();
      }

      float var2 = var1 - this.getCenterX();
      this.setX(this.x + var2);
   }

   public Shape[] subtract(Shape var1) {
      return (new GeomUtil()).subtract(this, var1);
   }

   public boolean hasVertex(float var1, float var2) {
      if (this.points.length == 0) {
         return false;
      } else {
         this.checkPoints();

         for(int var3 = 0; var3 < this.points.length; var3 += 2) {
            if (this.points[var3] == var1 && this.points[var3 + 1] == var2) {
               return true;
            }
         }

         return false;
      }
   }

   protected void calculateTriangles() {
      if (this.trianglesDirty || this.tris == null) {
         if (this.points.length >= 6) {
            boolean var1 = true;
            float var2 = 0.0F;

            int var3;
            for(var3 = 0; var3 < this.points.length / 2 - 1; ++var3) {
               float var4 = this.points[var3 * 2];
               float var5 = this.points[var3 * 2 + 1];
               float var6 = this.points[var3 * 2 + 2];
               float var7 = this.points[var3 * 2 + 3];
               var2 += var4 * var7 - var5 * var6;
            }

            var2 /= 2.0F;
            var1 = var2 > 0.0F;
            this.tris = new NeatTriangulator();

            for(var3 = 0; var3 < this.points.length; var3 += 2) {
               this.tris.addPolyPoint(this.points[var3], this.points[var3 + 1]);
            }

            this.tris.triangulate();
         }

         this.trianglesDirty = false;
      }
   }

   public boolean closed() {
      return true;
   }

   public float getCenterX() {
      this.checkPoints();
      return this.center[0];
   }

   public boolean intersects(Shape var1) {
      this.checkPoints();
      boolean var2 = false;
      float[] var3 = this.getPoints();
      float[] var4 = var1.getPoints();
      int var5 = var3.length;
      int var6 = var4.length;
      if (!this.closed()) {
         var5 -= 2;
      }

      if (!var1.closed()) {
         var6 -= 2;
      }

      for(int var11 = 0; var11 < var5; var11 += 2) {
         int var12 = var11 + 2;
         if (var12 >= var3.length) {
            var12 = 0;
         }

         for(int var13 = 0; var13 < var6; var13 += 2) {
            int var14 = var13 + 2;
            if (var14 >= var4.length) {
               var14 = 0;
            }

            double var7 = ((double)(var3[var12] - var3[var11]) * (double)(var4[var13 + 1] - var3[var11 + 1]) - (double)((var3[var12 + 1] - var3[var11 + 1]) * (var4[var13] - var3[var11]))) / (double)((var3[var12 + 1] - var3[var11 + 1]) * (var4[var14] - var4[var13]) - (var3[var12] - var3[var11]) * (var4[var14 + 1] - var4[var13 + 1]));
            double var9 = ((double)(var4[var14] - var4[var13]) * (double)(var4[var13 + 1] - var3[var11 + 1]) - (double)((var4[var14 + 1] - var4[var13 + 1]) * (var4[var13] - var3[var11]))) / (double)((var3[var12 + 1] - var3[var11 + 1]) * (var4[var14] - var4[var13]) - (var3[var12] - var3[var11]) * (var4[var14 + 1] - var4[var13 + 1]));
            if (var7 >= 0.0D && var7 <= 1.0D && var9 >= 0.0D && var9 <= 1.0D) {
               var2 = true;
               break;
            }
         }

         if (var2) {
            break;
         }
      }

      return var2;
   }

   public Vector2f getLocation() {
      return new Vector2f(this.getX(), this.getY());
   }

   public float[] getPoints() {
      this.checkPoints();
      return this.points;
   }

   public void setLocation(float var1, float var2) {
      this.setX(var1);
      this.setY(var2);
   }

   public float[] getCenter() {
      this.checkPoints();
      return this.center;
   }

   public void setCenterY(float var1) {
      if (this.points == null || this.center == null) {
         this.checkPoints();
      }

      float var2 = var1 - this.getCenterY();
      this.setY(this.y + var2);
   }

   public void setY(float var1) {
      if (var1 != this.y) {
         float var2 = var1 - this.y;
         this.y = var1;
         if (this.points == null || this.center == null) {
            this.checkPoints();
         }

         float[] var10000;
         for(int var3 = 0; var3 < this.points.length / 2; ++var3) {
            var10000 = this.points;
            var10000[var3 * 2 + 1] += var2;
         }

         var10000 = this.center;
         var10000[1] += var2;
         float var4 = var1 + var2;
         this.maxY += var2;
         this.minY += var2;
         this.trianglesDirty = true;
      }

   }

   public float getMaxX() {
      this.checkPoints();
      return this.maxX;
   }

   public boolean contains(float var1, float var2) {
      this.checkPoints();
      if (this.points.length == 0) {
         return false;
      } else {
         boolean var3 = false;
         int var12 = this.points.length;
         float var6 = this.points[var12 - 2];
         float var7 = this.points[var12 - 1];

         for(int var13 = 0; var13 < var12; var13 += 2) {
            float var4 = this.points[var13];
            float var5 = this.points[var13 + 1];
            float var8;
            float var9;
            float var10;
            float var11;
            if (var4 > var6) {
               var8 = var6;
               var10 = var4;
               var9 = var7;
               var11 = var5;
            } else {
               var8 = var4;
               var10 = var6;
               var9 = var5;
               var11 = var7;
            }

            if (var4 < var1 == var1 <= var6 && ((double)var2 - (double)var9) * (double)(var10 - var8) < ((double)var11 - (double)var9) * (double)(var1 - var8)) {
               var3 = !var3;
            }

            var6 = var4;
            var7 = var5;
         }

         return var3;
      }
   }

   protected void calculateRadius() {
      this.boundingCircleRadius = 0.0F;

      for(int var1 = 0; var1 < this.points.length; var1 += 2) {
         float var2 = (this.points[var1] - this.center[0]) * (this.points[var1] - this.center[0]) + (this.points[var1 + 1] - this.center[1]) * (this.points[var1 + 1] - this.center[1]);
         this.boundingCircleRadius = this.boundingCircleRadius > var2 ? this.boundingCircleRadius : var2;
      }

      this.boundingCircleRadius = (float)Math.sqrt((double)this.boundingCircleRadius);
   }

   private float[] getNormal(float[] var1, float[] var2) {
      float var3 = var1[0] - var2[0];
      float var4 = var1[1] - var2[1];
      float var5 = (float)Math.sqrt((double)(var3 * var3 + var4 * var4));
      var3 /= var5;
      var4 /= var5;
      return new float[]{-var4, var3};
   }

   public float getMaxY() {
      this.checkPoints();
      return this.maxY;
   }

   public void preCache() {
      this.checkPoints();
      this.getTriangles();
   }

   public boolean contains(Shape var1) {
      if (var1.intersects(this)) {
         return false;
      } else {
         for(int var2 = 0; var2 < var1.getPointCount(); ++var2) {
            float[] var3 = var1.getPoint(var2);
            if (!this.contains(var3[0], var3[1])) {
               return false;
            }
         }

         return true;
      }
   }

   public float[] getPoint(int var1) {
      this.checkPoints();
      float[] var2 = new float[]{this.points[var1 * 2], this.points[var1 * 2 + 1]};
      return var2;
   }

   public float[] getNormal(int var1) {
      float[] var2 = this.getPoint(var1);
      float[] var3 = this.getPoint(var1 - 1 < 0 ? this.getPointCount() - 1 : var1 - 1);
      float[] var4 = this.getPoint(var1 + 1 >= this.getPointCount() ? 0 : var1 + 1);
      float[] var5 = this.getNormal(var3, var2);
      float[] var6 = this.getNormal(var2, var4);
      if (var1 == 0 && !this.closed()) {
         return var6;
      } else if (var1 == this.getPointCount() - 1 && !this.closed()) {
         return var5;
      } else {
         float var7 = (var5[0] + var6[0]) / 2.0F;
         float var8 = (var5[1] + var6[1]) / 2.0F;
         float var9 = (float)Math.sqrt((double)(var7 * var7 + var8 * var8));
         return new float[]{var7 / var9, var8 / var9};
      }
   }

   public void setX(float var1) {
      if (var1 != this.x) {
         float var2 = var1 - this.x;
         this.x = var1;
         if (this.points == null || this.center == null) {
            this.checkPoints();
         }

         float[] var10000;
         for(int var3 = 0; var3 < this.points.length / 2; ++var3) {
            var10000 = this.points;
            var10000[var3 * 2] += var2;
         }

         var10000 = this.center;
         var10000[0] += var2;
         float var4 = var1 + var2;
         this.maxX += var2;
         this.minX += var2;
         this.trianglesDirty = true;
      }

   }

   public Shape[] union(Shape var1) {
      return (new GeomUtil()).union(this, var1);
   }

   protected void findCenter() {
      this.center = new float[]{0.0F, 0.0F};
      int var1 = this.points.length;

      float[] var10000;
      for(int var2 = 0; var2 < var1; var2 += 2) {
         var10000 = this.center;
         var10000[0] += this.points[var2];
         var10000 = this.center;
         var10000[1] += this.points[var2 + 1];
      }

      var10000 = this.center;
      var10000[0] /= (float)(var1 / 2);
      var10000 = this.center;
      var10000[1] /= (float)(var1 / 2);
   }

   public float getCenterY() {
      this.checkPoints();
      return this.center[1];
   }

   public Shape prune() {
      Polygon var1 = new Polygon();

      for(int var2 = 0; var2 < this.getPointCount(); ++var2) {
         int var3 = var2 + 1 >= this.getPointCount() ? 0 : var2 + 1;
         int var4 = var2 - 1 < 0 ? this.getPointCount() - 1 : var2 - 1;
         float var5 = this.getPoint(var2)[0] - this.getPoint(var4)[0];
         float var6 = this.getPoint(var2)[1] - this.getPoint(var4)[1];
         float var7 = this.getPoint(var3)[0] - this.getPoint(var2)[0];
         float var8 = this.getPoint(var3)[1] - this.getPoint(var2)[1];
         float var9 = (float)Math.sqrt((double)(var5 * var5 + var6 * var6));
         float var10 = (float)Math.sqrt((double)(var7 * var7 + var8 * var8));
         var5 /= var9;
         var6 /= var9;
         var7 /= var10;
         var8 /= var10;
         if (var5 != var7 || var6 != var8) {
            var1.addPoint(this.getPoint(var2)[0], this.getPoint(var2)[1]);
         }
      }

      return var1;
   }

   public float getMinX() {
      this.checkPoints();
      return this.minX;
   }

   public float getBoundingCircleRadius() {
      this.checkPoints();
      return this.boundingCircleRadius;
   }

   public void increaseTriangulation() {
      this.checkPoints();
      this.calculateTriangles();
      this.tris = new OverTriangulator(this.tris);
   }

   public float getWidth() {
      return this.maxX - this.minX;
   }

   protected final void checkPoints() {
      if (this.pointsDirty) {
         this.createPoints();
         this.findCenter();
         this.calculateRadius();
         if (this.points.length > 0) {
            this.maxX = this.points[0];
            this.maxY = this.points[1];
            this.minX = this.points[0];
            this.minY = this.points[1];

            for(int var1 = 0; var1 < this.points.length / 2; ++var1) {
               this.maxX = Math.max(this.points[var1 * 2], this.maxX);
               this.maxY = Math.max(this.points[var1 * 2 + 1], this.maxY);
               this.minX = Math.min(this.points[var1 * 2], this.minX);
               this.minY = Math.min(this.points[var1 * 2 + 1], this.minY);
            }
         }

         this.pointsDirty = false;
         this.trianglesDirty = true;
      }

   }

   public Triangulator getTriangles() {
      this.checkPoints();
      this.calculateTriangles();
      return this.tris;
   }

   public int getPointCount() {
      this.checkPoints();
      return this.points.length / 2;
   }

   public abstract Shape transform(Transform var1);

   public float getY() {
      return this.y;
   }

   public float getHeight() {
      return this.maxY - this.minY;
   }

   public float getMinY() {
      this.checkPoints();
      return this.minY;
   }

   protected abstract void createPoints();

   public float getX() {
      return this.x;
   }

   public int indexOf(float var1, float var2) {
      for(int var3 = 0; var3 < this.points.length; var3 += 2) {
         if (this.points[var3] == var1 && this.points[var3 + 1] == var2) {
            return var3 / 2;
         }
      }

      return -1;
   }

   public void setLocation(Vector2f var1) {
      this.setX(var1.x);
      this.setY(var1.y);
   }

   public boolean includes(float var1, float var2) {
      if (this.points.length == 0) {
         return false;
      } else {
         this.checkPoints();
         Line var3 = new Line(0.0F, 0.0F, 0.0F, 0.0F);
         Vector2f var4 = new Vector2f(var1, var2);

         for(int var5 = 0; var5 < this.points.length; var5 += 2) {
            int var6 = var5 + 2;
            if (var6 >= this.points.length) {
               var6 = 0;
            }

            var3.set(this.points[var5], this.points[var5 + 1], this.points[var6], this.points[var6 + 1]);
            if (var3.on(var4)) {
               return true;
            }
         }

         return false;
      }
   }
}
