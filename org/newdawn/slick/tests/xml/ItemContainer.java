package org.newdawn.slick.tests.xml;

import java.util.ArrayList;

public class ItemContainer extends Item {
   // $FF: synthetic field
   private ArrayList items = new ArrayList();

   private void add(Item var1) {
      this.items.add(var1);
   }

   public void dump(String var1) {
      System.out.println(String.valueOf((new StringBuilder()).append(var1).append("Item Container ").append(this.name).append(",").append(this.condition)));

      for(int var2 = 0; var2 < this.items.size(); ++var2) {
         ((Item)this.items.get(var2)).dump(String.valueOf((new StringBuilder()).append(var1).append("\t")));
      }

   }

   private void setName(String var1) {
      this.name = var1;
   }

   private void setCondition(int var1) {
      this.condition = var1;
   }
}
