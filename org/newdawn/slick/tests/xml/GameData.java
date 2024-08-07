package org.newdawn.slick.tests.xml;

import java.util.ArrayList;

public class GameData {
   // $FF: synthetic field
   private ArrayList entities = new ArrayList();

   private void add(Entity var1) {
      this.entities.add(var1);
   }

   public void dump(String var1) {
      System.out.println(String.valueOf((new StringBuilder()).append(var1).append("GameData")));

      for(int var2 = 0; var2 < this.entities.size(); ++var2) {
         ((Entity)this.entities.get(var2)).dump(String.valueOf((new StringBuilder()).append(var1).append("\t")));
      }

   }
}
