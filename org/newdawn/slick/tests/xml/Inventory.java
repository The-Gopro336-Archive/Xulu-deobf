package org.newdawn.slick.tests.xml;

import java.util.ArrayList;

public class Inventory {
   // $FF: synthetic field
   private ArrayList items = new ArrayList();

   public void dump(String var1) {
      System.out.println(String.valueOf((new StringBuilder()).append(var1).append("Inventory")));

      for(int var2 = 0; var2 < this.items.size(); ++var2) {
         ((Item)this.items.get(var2)).dump(String.valueOf((new StringBuilder()).append(var1).append("\t")));
      }

   }

   private void add(Item var1) {
      this.items.add(var1);
   }
}
