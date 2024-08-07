package org.newdawn.slick.util.pathfinding;

import java.io.Serializable;
import java.util.ArrayList;

public class Path implements Serializable {
   // $FF: synthetic field
   private static final long serialVersionUID = 1L;
   // $FF: synthetic field
   private ArrayList steps = new ArrayList();

   public void appendStep(int var1, int var2) {
      this.steps.add(new Path.Step(var1, var2));
   }

   public int getX(int var1) {
      return this.getStep(var1).x;
   }

   public int getY(int var1) {
      return this.getStep(var1).y;
   }

   public int getLength() {
      return this.steps.size();
   }

   public Path.Step getStep(int var1) {
      return (Path.Step)this.steps.get(var1);
   }

   public void prependStep(int var1, int var2) {
      this.steps.add(0, new Path.Step(var1, var2));
   }

   public boolean contains(int var1, int var2) {
      return this.steps.contains(new Path.Step(var1, var2));
   }

   public class Step implements Serializable {
      // $FF: synthetic field
      private int x;
      // $FF: synthetic field
      private int y;

      public int getY() {
         return this.y;
      }

      public int hashCode() {
         return this.x * this.y;
      }

      public boolean equals(Object var1) {
         if (!(var1 instanceof Path.Step)) {
            return false;
         } else {
            Path.Step var2 = (Path.Step)var1;
            return var2.x == this.x && var2.y == this.y;
         }
      }

      public Step(int var2, int var3) {
         this.x = var2;
         this.y = var3;
      }

      public int getX() {
         return this.x;
      }
   }
}
