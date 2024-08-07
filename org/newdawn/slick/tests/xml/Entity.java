package org.newdawn.slick.tests.xml;

public class Entity {
   // $FF: synthetic field
   private float x;
   // $FF: synthetic field
   private Inventory invent;
   // $FF: synthetic field
   private float y;
   // $FF: synthetic field
   private Stats stats;

   private void add(Stats var1) {
      this.stats = var1;
   }

   private void add(Inventory var1) {
      this.invent = var1;
   }

   public void dump(String var1) {
      System.out.println(String.valueOf((new StringBuilder()).append(var1).append("Entity ").append(this.x).append(",").append(this.y)));
      this.invent.dump(String.valueOf((new StringBuilder()).append(var1).append("\t")));
      this.stats.dump(String.valueOf((new StringBuilder()).append(var1).append("\t")));
   }
}
