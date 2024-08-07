package org.newdawn.slick.util.pathfinding.navmesh;

import java.util.ArrayList;

public class NavMesh {
   // $FF: synthetic field
   private ArrayList spaces = new ArrayList();

   public void addSpace(Space var1) {
      this.spaces.add(var1);
   }

   private void optimize(NavPath var1) {
      int var2 = 0;

      while(var2 < var1.length() - 2) {
         float var3 = var1.getX(var2);
         float var4 = var1.getY(var2);
         float var5 = var1.getX(var2 + 2);
         float var6 = var1.getY(var2 + 2);
         if (this.isClear(var3, var4, var5, var6, 0.1F)) {
            var1.remove(var2 + 1);
         } else {
            ++var2;
         }
      }

   }

   public NavMesh(ArrayList var1) {
      this.spaces.addAll(var1);
   }

   private boolean isClear(float var1, float var2, float var3, float var4, float var5) {
      float var6 = var3 - var1;
      float var7 = var4 - var2;
      float var8 = (float)Math.sqrt((double)(var6 * var6 + var7 * var7));
      var6 *= var5;
      var6 /= var8;
      var7 *= var5;
      var7 /= var8;
      int var9 = (int)(var8 / var5);

      for(int var10 = 0; var10 < var9; ++var10) {
         float var11 = var1 + var6 * (float)var10;
         float var12 = var2 + var7 * (float)var10;
         if (this.findSpace(var11, var12) == null) {
            return false;
         }
      }

      return true;
   }

   public Space findSpace(float var1, float var2) {
      for(int var3 = 0; var3 < this.spaces.size(); ++var3) {
         Space var4 = this.getSpace(var3);
         if (var4.contains(var1, var2)) {
            return var4;
         }
      }

      return null;
   }

   public int getSpaceCount() {
      return this.spaces.size();
   }

   public Space getSpace(int var1) {
      return (Space)this.spaces.get(var1);
   }

   public NavPath findPath(float var1, float var2, float var3, float var4, boolean var5) {
      Space var6 = this.findSpace(var1, var2);
      Space var7 = this.findSpace(var3, var4);
      if (var6 != null && var7 != null) {
         for(int var8 = 0; var8 < this.spaces.size(); ++var8) {
            ((Space)this.spaces.get(var8)).clearCost();
         }

         var7.fill(var6, var3, var4, 0.0F);
         if (var7.getCost() == Float.MAX_VALUE) {
            return null;
         } else if (var6.getCost() == Float.MAX_VALUE) {
            return null;
         } else {
            NavPath var9 = new NavPath();
            var9.push(new Link(var1, var2, (Space)null));
            if (var6.pickLowestCost(var7, var9)) {
               var9.push(new Link(var3, var4, (Space)null));
               if (var5) {
                  this.optimize(var9);
               }

               return var9;
            } else {
               return null;
            }
         }
      } else {
         return null;
      }
   }

   public NavMesh() {
   }
}
