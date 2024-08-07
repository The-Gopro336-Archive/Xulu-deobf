package org.newdawn.slick.geom;

import java.util.ArrayList;

public class BasicTriangulator implements Triangulator {
   // $FF: synthetic field
   private boolean tried;
   // $FF: synthetic field
   private static final float EPSILON = 1.0E-10F;
   // $FF: synthetic field
   private BasicTriangulator.PointList tris = new BasicTriangulator.PointList();
   // $FF: synthetic field
   private BasicTriangulator.PointList poly = new BasicTriangulator.PointList();

   public int getPolyPointCount() {
      return this.poly.size();
   }

   private boolean snip(BasicTriangulator.PointList var1, int var2, int var3, int var4, int var5, int[] var6) {
      float var8 = var1.get(var6[var2]).getX();
      float var9 = var1.get(var6[var2]).getY();
      float var10 = var1.get(var6[var3]).getX();
      float var11 = var1.get(var6[var3]).getY();
      float var12 = var1.get(var6[var4]).getX();
      float var13 = var1.get(var6[var4]).getY();
      if (1.0E-10F > (var10 - var8) * (var13 - var9) - (var11 - var9) * (var12 - var8)) {
         return false;
      } else {
         for(int var7 = 0; var7 < var5; ++var7) {
            if (var7 != var2 && var7 != var3 && var7 != var4) {
               float var14 = var1.get(var6[var7]).getX();
               float var15 = var1.get(var6[var7]).getY();
               if (this.insideTriangle(var8, var9, var10, var11, var12, var13, var14, var15)) {
                  return false;
               }
            }
         }

         return true;
      }
   }

   public float[] getTrianglePoint(int var1, int var2) {
      if (!this.tried) {
         throw new RuntimeException("Call triangulate() before accessing triangles");
      } else {
         return this.tris.get(var1 * 3 + var2).toArray();
      }
   }

   public boolean triangulate() {
      this.tried = true;
      boolean var1 = this.process(this.poly, this.tris);
      return var1;
   }

   private float area(BasicTriangulator.PointList var1) {
      int var2 = var1.size();
      float var3 = 0.0F;
      int var4 = var2 - 1;

      for(int var5 = 0; var5 < var2; var4 = var5++) {
         BasicTriangulator.Point var6 = var1.get(var4);
         BasicTriangulator.Point var7 = var1.get(var5);
         var3 += var6.getX() * var7.getY() - var7.getX() * var6.getY();
      }

      return var3 * 0.5F;
   }

   public float[] getPolyPoint(int var1) {
      return new float[]{this.poly.get(var1).x, this.poly.get(var1).y};
   }

   public void startHole() {
   }

   private boolean insideTriangle(float var1, float var2, float var3, float var4, float var5, float var6, float var7, float var8) {
      float var9 = var5 - var3;
      float var10 = var6 - var4;
      float var11 = var1 - var5;
      float var12 = var2 - var6;
      float var13 = var3 - var1;
      float var14 = var4 - var2;
      float var15 = var7 - var1;
      float var16 = var8 - var2;
      float var17 = var7 - var3;
      float var18 = var8 - var4;
      float var19 = var7 - var5;
      float var20 = var8 - var6;
      float var23 = var9 * var18 - var10 * var17;
      float var21 = var13 * var16 - var14 * var15;
      float var22 = var11 * var20 - var12 * var19;
      return var23 >= 0.0F && var22 >= 0.0F && var21 >= 0.0F;
   }

   private boolean process(BasicTriangulator.PointList var1, BasicTriangulator.PointList var2) {
      var2.clear();
      int var3 = var1.size();
      if (var3 < 3) {
         return false;
      } else {
         int[] var4 = new int[var3];
         int var5;
         if (0.0F < this.area(var1)) {
            for(var5 = 0; var5 < var3; var4[var5] = var5++) {
            }
         } else {
            for(var5 = 0; var5 < var3; ++var5) {
               var4[var5] = var3 - 1 - var5;
            }
         }

         var5 = var3;
         int var6 = 2 * var3;
         int var7 = 0;
         int var8 = var3 - 1;

         while(true) {
            int var9;
            int var10;
            do {
               if (var5 <= 2) {
                  return true;
               }

               if (0 >= var6--) {
                  return false;
               }

               var9 = var8;
               if (var5 <= var8) {
                  var9 = 0;
               }

               var8 = var9 + 1;
               if (var5 <= var8) {
                  var8 = 0;
               }

               var10 = var8 + 1;
               if (var5 <= var10) {
                  var10 = 0;
               }
            } while(!this.snip(var1, var9, var8, var10, var5, var4));

            int var11 = var4[var9];
            int var12 = var4[var8];
            int var13 = var4[var10];
            var2.add(var1.get(var11));
            var2.add(var1.get(var12));
            var2.add(var1.get(var13));
            ++var7;
            int var14 = var8;

            for(int var15 = var8 + 1; var15 < var5; ++var15) {
               var4[var14] = var4[var15];
               ++var14;
            }

            --var5;
            var6 = 2 * var5;
         }
      }
   }

   public int getTriangleCount() {
      if (!this.tried) {
         throw new RuntimeException("Call triangulate() before accessing triangles");
      } else {
         return this.tris.size() / 3;
      }
   }

   public void addPolyPoint(float var1, float var2) {
      BasicTriangulator.Point var3 = new BasicTriangulator.Point(var1, var2);
      if (!this.poly.contains(var3)) {
         this.poly.add(var3);
      }

   }

   private class Point {
      // $FF: synthetic field
      private float y;
      // $FF: synthetic field
      private float[] array;
      // $FF: synthetic field
      private float x;

      public int hashCode() {
         return (int)(this.x * this.y * 31.0F);
      }

      public float[] toArray() {
         return this.array;
      }

      public float getY() {
         return this.y;
      }

      public boolean equals(Object var1) {
         if (!(var1 instanceof BasicTriangulator.Point)) {
            return false;
         } else {
            BasicTriangulator.Point var2 = (BasicTriangulator.Point)var1;
            return var2.x == this.x && var2.y == this.y;
         }
      }

      public Point(float var2, float var3) {
         this.x = var2;
         this.y = var3;
         this.array = new float[]{var2, var3};
      }

      public float getX() {
         return this.x;
      }
   }

   private class PointList {
      // $FF: synthetic field
      private ArrayList points = new ArrayList();

      public void clear() {
         this.points.clear();
      }

      public void remove(BasicTriangulator.Point var1) {
         this.points.remove(var1);
      }

      public int size() {
         return this.points.size();
      }

      public boolean contains(BasicTriangulator.Point var1) {
         return this.points.contains(var1);
      }

      public PointList() {
      }

      public void add(BasicTriangulator.Point var1) {
         this.points.add(var1);
      }

      public BasicTriangulator.Point get(int var1) {
         return (BasicTriangulator.Point)this.points.get(var1);
      }
   }
}
