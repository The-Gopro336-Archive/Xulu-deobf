package org.newdawn.slick.util.pathfinding.navmesh;

import java.util.ArrayList;
import java.util.HashMap;

public class Space {
   // $FF: synthetic field
   private float width;
   // $FF: synthetic field
   private float x;
   // $FF: synthetic field
   private HashMap links = new HashMap();
   // $FF: synthetic field
   private float y;
   // $FF: synthetic field
   private float cost;
   // $FF: synthetic field
   private ArrayList linksList = new ArrayList();
   // $FF: synthetic field
   private float height;

   public float getHeight() {
      return this.height;
   }

   public void fill(Space var1, float var2, float var3, float var4) {
      if (!(var4 >= this.cost)) {
         this.cost = var4;
         if (var1 != this) {
            for(int var5 = 0; var5 < this.getLinkCount(); ++var5) {
               Link var6 = this.getLink(var5);
               float var7 = var6.distance2(var2, var3);
               float var8 = var4 + var7;
               var6.getTarget().fill(var1, var6.getX(), var6.getY(), var8);
            }

         }
      }
   }

   public void clearCost() {
      this.cost = Float.MAX_VALUE;
   }

   public float getX() {
      return this.x;
   }

   public boolean hasJoinedEdge(Space var1) {
      if (this.inTolerance(this.x, var1.x + var1.width) || this.inTolerance(this.x + this.width, var1.x)) {
         if (this.y >= var1.y && this.y <= var1.y + var1.height) {
            return true;
         }

         if (this.y + this.height >= var1.y && this.y + this.height <= var1.y + var1.height) {
            return true;
         }

         if (var1.y >= this.y && var1.y <= this.y + this.height) {
            return true;
         }

         if (var1.y + var1.height >= this.y && var1.y + var1.height <= this.y + this.height) {
            return true;
         }
      }

      if (this.inTolerance(this.y, var1.y + var1.height) || this.inTolerance(this.y + this.height, var1.y)) {
         if (this.x >= var1.x && this.x <= var1.x + var1.width) {
            return true;
         }

         if (this.x + this.width >= var1.x && this.x + this.width <= var1.x + var1.width) {
            return true;
         }

         if (var1.x >= this.x && var1.x <= this.x + this.width) {
            return true;
         }

         if (var1.x + var1.width >= this.x && var1.x + var1.width <= this.x + this.width) {
            return true;
         }
      }

      return false;
   }

   public Link getLink(int var1) {
      return (Link)this.linksList.get(var1);
   }

   public int getLinkCount() {
      return this.linksList.size();
   }

   public float getCost() {
      return this.cost;
   }

   public Space merge(Space var1) {
      float var2 = Math.min(this.x, var1.x);
      float var3 = Math.min(this.y, var1.y);
      float var4 = this.width + var1.width;
      float var5 = this.height + var1.height;
      if (this.x == var1.x) {
         var4 = this.width;
      } else {
         var5 = this.height;
      }

      return new Space(var2, var3, var4, var5);
   }

   public float getWidth() {
      return this.width;
   }

   public float getY() {
      return this.y;
   }

   public boolean pickLowestCost(Space var1, NavPath var2) {
      if (var1 == this) {
         return true;
      } else if (this.links.size() == 0) {
         return false;
      } else {
         Link var3 = null;

         for(int var4 = 0; var4 < this.getLinkCount(); ++var4) {
            Link var5 = this.getLink(var4);
            if (var3 == null || var5.getTarget().getCost() < var3.getTarget().getCost()) {
               var3 = var5;
            }
         }

         var2.push(var3);
         return var3.getTarget().pickLowestCost(var1, var2);
      }
   }

   public void link(Space var1) {
      float var2;
      float var3;
      float var4;
      float var5;
      Link var6;
      if (this.inTolerance(this.x, var1.x + var1.width) || this.inTolerance(this.x + this.width, var1.x)) {
         var2 = this.x;
         if (this.x + this.width == var1.x) {
            var2 = this.x + this.width;
         }

         var3 = Math.max(this.y, var1.y);
         var4 = Math.min(this.y + this.height, var1.y + var1.height);
         var5 = var3 + (var4 - var3) / 2.0F;
         var6 = new Link(var2, var5, var1);
         this.links.put(var1, var6);
         this.linksList.add(var6);
      }

      if (this.inTolerance(this.y, var1.y + var1.height) || this.inTolerance(this.y + this.height, var1.y)) {
         var2 = this.y;
         if (this.y + this.height == var1.y) {
            var2 = this.y + this.height;
         }

         var3 = Math.max(this.x, var1.x);
         var4 = Math.min(this.x + this.width, var1.x + var1.width);
         var5 = var3 + (var4 - var3) / 2.0F;
         var6 = new Link(var5, var2, var1);
         this.links.put(var1, var6);
         this.linksList.add(var6);
      }

   }

   private boolean inTolerance(float var1, float var2) {
      return var1 == var2;
   }

   public boolean contains(float var1, float var2) {
      return var1 >= this.x && var1 < this.x + this.width && var2 >= this.y && var2 < this.y + this.height;
   }

   public String toString() {
      return String.valueOf((new StringBuilder()).append("[Space ").append(this.x).append(",").append(this.y).append(" ").append(this.width).append(",").append(this.height).append("]"));
   }

   public boolean canMerge(Space var1) {
      if (!this.hasJoinedEdge(var1)) {
         return false;
      } else if (this.x == var1.x && this.width == var1.width) {
         return true;
      } else {
         return this.y == var1.y && this.height == var1.height;
      }
   }

   public Space(float var1, float var2, float var3, float var4) {
      this.x = var1;
      this.y = var2;
      this.width = var3;
      this.height = var4;
   }
}
