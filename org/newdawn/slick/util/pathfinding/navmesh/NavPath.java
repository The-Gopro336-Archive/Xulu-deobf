package org.newdawn.slick.util.pathfinding.navmesh;

import java.util.ArrayList;

public class NavPath {
   // $FF: synthetic field
   private ArrayList links = new ArrayList();

   public float getX(int var1) {
      return ((Link)this.links.get(var1)).getX();
   }

   public String toString() {
      return String.valueOf((new StringBuilder()).append("[Path length=").append(this.length()).append("]"));
   }

   public float getY(int var1) {
      return ((Link)this.links.get(var1)).getY();
   }

   public void remove(int var1) {
      this.links.remove(var1);
   }

   public void push(Link var1) {
      this.links.add(var1);
   }

   public int length() {
      return this.links.size();
   }
}
