package org.newdawn.slick.geom;

public class NeatTriangulator implements Triangulator {
   // $FF: synthetic field
   private NeatTriangulator.Triangle[] triangles = new NeatTriangulator.Triangle[100];
   // $FF: synthetic field
   private int numTriangles = 0;
   // $FF: synthetic field
   static final float EPSILON = 1.0E-6F;
   // $FF: synthetic field
   private int numEdges = 0;
   // $FF: synthetic field
   private float[] pointsX = new float[100];
   // $FF: synthetic field
   private int numPoints = 0;
   // $FF: synthetic field
   private float[] pointsY = new float[100];
   // $FF: synthetic field
   private int[] V;
   // $FF: synthetic field
   private NeatTriangulator.Edge[] edges = new NeatTriangulator.Edge[100];
   // $FF: synthetic field
   private float offset = 1.0E-6F;

   private boolean snip(int var1, int var2, int var3, int var4) {
      float var5 = this.pointsX[this.V[var1]];
      float var6 = this.pointsY[this.V[var1]];
      float var7 = this.pointsX[this.V[var2]];
      float var8 = this.pointsY[this.V[var2]];
      float var9 = this.pointsX[this.V[var3]];
      float var10 = this.pointsY[this.V[var3]];
      if (1.0E-6F > (var7 - var5) * (var10 - var6) - (var8 - var6) * (var9 - var5)) {
         return false;
      } else {
         for(int var11 = 0; var11 < var4; ++var11) {
            if (var11 != var1 && var11 != var2 && var11 != var3) {
               float var12 = this.pointsX[this.V[var11]];
               float var13 = this.pointsY[this.V[var11]];
               if (insideTriangle(var5, var6, var7, var8, var9, var10, var12, var13)) {
                  return false;
               }
            }
         }

         return true;
      }
   }

   public void startHole() {
   }

   public void basicTriangulation() throws NeatTriangulator.InternalException {
      int var1 = this.numPoints;
      if (var1 >= 3) {
         this.numEdges = 0;
         this.numTriangles = 0;
         this.V = new int[var1];
         int var2;
         if (0.0D < (double)this.area()) {
            for(var2 = 0; var2 < var1; this.V[var2] = var2++) {
            }
         } else {
            for(var2 = 0; var2 < var1; ++var2) {
               this.V[var2] = this.numPoints - 1 - var2;
            }
         }

         var2 = 2 * var1;
         int var3 = var1 - 1;

         while(true) {
            int var4;
            int var5;
            do {
               if (var1 <= 2) {
                  this.V = null;
                  return;
               }

               if (0 >= var2--) {
                  throw new NeatTriangulator.InternalException("Bad polygon");
               }

               var4 = var3;
               if (var1 <= var3) {
                  var4 = 0;
               }

               var3 = var4 + 1;
               if (var1 <= var3) {
                  var3 = 0;
               }

               var5 = var3 + 1;
               if (var1 <= var5) {
                  var5 = 0;
               }
            } while(!this.snip(var4, var3, var5, var1));

            int var6 = this.V[var4];
            int var7 = this.V[var3];
            int var8 = this.V[var5];
            if (this.numTriangles == this.triangles.length) {
               NeatTriangulator.Triangle[] var9 = new NeatTriangulator.Triangle[this.triangles.length * 2];
               System.arraycopy(this.triangles, 0, var9, 0, this.numTriangles);
               this.triangles = var9;
            }

            this.triangles[this.numTriangles] = new NeatTriangulator.Triangle(var6, var7, var8);
            this.addEdge(var6, var7, this.numTriangles);
            this.addEdge(var7, var8, this.numTriangles);
            this.addEdge(var8, var6, this.numTriangles);
            ++this.numTriangles;
            int var11 = var3;

            for(int var10 = var3 + 1; var10 < var1; ++var10) {
               this.V[var11] = this.V[var10];
               ++var11;
            }

            --var1;
            var2 = 2 * var1;
         }
      }
   }

   public float[] getTrianglePoint(int var1, int var2) {
      float var3 = this.pointsX[this.triangles[var1].v[var2]];
      float var4 = this.pointsY[this.triangles[var1].v[var2]];
      return new float[]{var3, var4};
   }

   void markSuspect(int var1, int var2, boolean var3) throws NeatTriangulator.InternalException {
      int var4;
      if (0 > (var4 = this.findEdge(var1, var2))) {
         throw new NeatTriangulator.InternalException("Attempt to mark unknown edge");
      } else {
         this.edges[var4].suspect = var3;
      }
   }

   public void addPolyPoint(float var1, float var2) {
      for(int var3 = 0; var3 < this.numPoints; ++var3) {
         if (this.pointsX[var3] == var1 && this.pointsY[var3] == var2) {
            var2 += this.offset;
            this.offset += 1.0E-6F;
         }
      }

      if (this.numPoints == this.pointsX.length) {
         float[] var4 = new float[this.numPoints * 2];
         System.arraycopy(this.pointsX, 0, var4, 0, this.numPoints);
         this.pointsX = var4;
         var4 = new float[this.numPoints * 2];
         System.arraycopy(this.pointsY, 0, var4, 0, this.numPoints);
         this.pointsY = var4;
      }

      this.pointsX[this.numPoints] = var1;
      this.pointsY[this.numPoints] = var2;
      ++this.numPoints;
   }

   public int getTriangleCount() {
      return this.numTriangles;
   }

   private void addEdge(int var1, int var2, int var3) {
      int var4 = this.findEdge(var1, var2);
      int var5;
      int var6;
      NeatTriangulator.Edge var7;
      if (var4 < 0) {
         if (this.numEdges == this.edges.length) {
            NeatTriangulator.Edge[] var8 = new NeatTriangulator.Edge[this.edges.length * 2];
            System.arraycopy(this.edges, 0, var8, 0, this.numEdges);
            this.edges = var8;
         }

         var5 = -1;
         var6 = -1;
         var4 = this.numEdges++;
         var7 = this.edges[var4] = new NeatTriangulator.Edge();
      } else {
         var7 = this.edges[var4];
         var5 = var7.t0;
         var6 = var7.t1;
      }

      int var9;
      int var10;
      if (var1 < var2) {
         var10 = var1;
         var9 = var2;
         var5 = var3;
      } else {
         var10 = var2;
         var9 = var1;
         var6 = var3;
      }

      var7.v0 = var10;
      var7.v1 = var9;
      var7.t0 = var5;
      var7.t1 = var6;
      var7.suspect = true;
   }

   public boolean triangulate() {
      try {
         this.basicTriangulation();
         return true;
      } catch (NeatTriangulator.InternalException var2) {
         this.numEdges = 0;
         return false;
      }
   }

   private static boolean insideTriangle(float var0, float var1, float var2, float var3, float var4, float var5, float var6, float var7) {
      float var8 = var4 - var2;
      float var9 = var5 - var3;
      float var10 = var0 - var4;
      float var11 = var1 - var5;
      float var12 = var2 - var0;
      float var13 = var3 - var1;
      float var14 = var6 - var0;
      float var15 = var7 - var1;
      float var16 = var6 - var2;
      float var17 = var7 - var3;
      float var18 = var6 - var4;
      float var19 = var7 - var5;
      float var20 = var8 * var17 - var9 * var16;
      float var21 = var12 * var15 - var13 * var14;
      float var22 = var10 * var19 - var11 * var18;
      return (double)var20 >= 0.0D && (double)var22 >= 0.0D && (double)var21 >= 0.0D;
   }

   private float area() {
      float var1 = 0.0F;
      int var2 = this.numPoints - 1;

      for(int var3 = 0; var3 < this.numPoints; var2 = var3++) {
         var1 += this.pointsX[var2] * this.pointsY[var3] - this.pointsY[var2] * this.pointsX[var3];
      }

      return var1 * 0.5F;
   }

   private static float rho(float var0, float var1, float var2, float var3, float var4, float var5) {
      float var6 = var4 - var2;
      float var7 = var5 - var3;
      float var8 = var0 - var4;
      float var9 = var1 - var5;
      float var10 = var6 * var9 - var7 * var8;
      if (var10 > 0.0F) {
         if (var10 < 1.0E-6F) {
            var10 = 1.0E-6F;
         }

         float var11 = var6 * var6;
         float var12 = var7 * var7;
         float var13 = var8 * var8;
         float var14 = var9 * var9;
         float var15 = var2 - var0;
         float var16 = var3 - var1;
         float var17 = var15 * var15;
         float var18 = var16 * var16;
         return (var11 + var12) * (var13 + var14) * (var17 + var18) / (var10 * var10);
      } else {
         return -1.0F;
      }
   }

   private void optimize() throws NeatTriangulator.InternalException {
      NeatTriangulator.Edge var1;
      while((var1 = this.chooseSuspect()) != null) {
         int var2 = var1.v0;
         int var3 = var1.v1;
         int var4 = var1.t0;
         int var5 = var1.t1;
         int var6 = -1;
         int var7 = -1;

         int var8;
         int var9;
         for(var8 = 0; var8 < 3; ++var8) {
            var9 = this.triangles[var4].v[var8];
            if (var2 != var9 && var3 != var9) {
               var7 = var9;
               break;
            }
         }

         for(var8 = 0; var8 < 3; ++var8) {
            var9 = this.triangles[var5].v[var8];
            if (var2 != var9 && var3 != var9) {
               var6 = var9;
               break;
            }
         }

         if (-1 != var6 && -1 != var7) {
            float var20 = this.pointsX[var2];
            float var21 = this.pointsY[var2];
            float var10 = this.pointsX[var6];
            float var11 = this.pointsY[var6];
            float var12 = this.pointsX[var3];
            float var13 = this.pointsY[var3];
            float var14 = this.pointsX[var7];
            float var15 = this.pointsY[var7];
            float var16 = rho(var20, var21, var10, var11, var12, var13);
            float var17 = rho(var20, var21, var12, var13, var14, var15);
            float var18 = rho(var10, var11, var12, var13, var14, var15);
            float var19 = rho(var10, var11, var14, var15, var20, var21);
            if (!(0.0F > var16) && !(0.0F > var17)) {
               if (!(0.0F <= var18) || !(0.0F <= var19)) {
                  continue;
               }

               if (var16 > var17) {
                  var16 = var17;
               }

               if (var18 > var19) {
                  var18 = var19;
               }

               if (var16 > var18) {
                  this.deleteEdge(var2, var3);
                  this.triangles[var4].v[0] = var6;
                  this.triangles[var4].v[1] = var3;
                  this.triangles[var4].v[2] = var7;
                  this.triangles[var5].v[0] = var6;
                  this.triangles[var5].v[1] = var7;
                  this.triangles[var5].v[2] = var2;
                  this.addEdge(var6, var3, var4);
                  this.addEdge(var3, var7, var4);
                  this.addEdge(var7, var6, var4);
                  this.addEdge(var7, var2, var5);
                  this.addEdge(var2, var6, var5);
                  this.addEdge(var6, var7, var5);
                  this.markSuspect(var6, var7, false);
               }
               continue;
            }

            throw new NeatTriangulator.InternalException("original triangles backwards");
         }

         throw new NeatTriangulator.InternalException("can't find quad");
      }

   }

   private void deleteEdge(int var1, int var2) throws NeatTriangulator.InternalException {
      int var3;
      if (0 > (var3 = this.findEdge(var1, var2))) {
         throw new NeatTriangulator.InternalException("Attempt to delete unknown edge");
      } else {
         this.edges[var3] = this.edges[--this.numEdges];
      }
   }

   private NeatTriangulator.Edge chooseSuspect() {
      for(int var1 = 0; var1 < this.numEdges; ++var1) {
         NeatTriangulator.Edge var2 = this.edges[var1];
         if (var2.suspect) {
            var2.suspect = false;
            if (var2.t0 >= 0 && var2.t1 >= 0) {
               return var2;
            }
         }
      }

      return null;
   }

   public void clear() {
      this.numPoints = 0;
      this.numEdges = 0;
      this.numTriangles = 0;
   }

   private int findEdge(int var1, int var2) {
      int var3;
      int var4;
      if (var1 < var2) {
         var3 = var1;
         var4 = var2;
      } else {
         var3 = var2;
         var4 = var1;
      }

      for(int var5 = 0; var5 < this.numEdges; ++var5) {
         if (this.edges[var5].v0 == var3 && this.edges[var5].v1 == var4) {
            return var5;
         }
      }

      return -1;
   }

   class Edge {
      // $FF: synthetic field
      int t0 = -1;
      // $FF: synthetic field
      int v1 = -1;
      // $FF: synthetic field
      boolean suspect;
      // $FF: synthetic field
      int t1 = -1;
      // $FF: synthetic field
      int v0 = -1;
   }

   class InternalException extends Exception {
      public InternalException(String var2) {
         super(var2);
      }
   }

   class Triangle {
      // $FF: synthetic field
      int[] v = new int[3];

      Triangle(int var2, int var3, int var4) {
         this.v[0] = var2;
         this.v[1] = var3;
         this.v[2] = var4;
      }
   }
}
