package com.elementars.eclient.enemy;

import io.netty.util.internal.ConcurrentSet;
import java.util.Iterator;

public class Enemies {
   // $FF: synthetic field
   private static ConcurrentSet enemies = new ConcurrentSet();

   public static Enemy getEnemyByName(String var0) {
      Iterator var1 = enemies.iterator();

      Enemy var2;
      do {
         if (!var1.hasNext()) {
            return null;
         }

         var2 = (Enemy)var1.next();
      } while(!var2.username.equalsIgnoreCase(var0));

      return var2;
   }

   public static boolean isEnemy(String var0) {
      return enemies.stream().anyMatch((var1) -> {
         return var1.username.equalsIgnoreCase(var0);
      });
   }

   public static void delEnemy(String var0) {
      enemies.remove(getEnemyByName(var0));
   }

   public static void addEnemy(String var0) {
      enemies.add(new Enemy(var0));
   }

   public static ConcurrentSet getEnemies() {
      return enemies;
   }
}
