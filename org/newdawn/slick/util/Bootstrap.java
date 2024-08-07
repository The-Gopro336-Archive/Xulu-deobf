package org.newdawn.slick.util;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.Game;

public class Bootstrap {
   public static void runAsApplication(Game var0, int var1, int var2, boolean var3) {
      try {
         AppGameContainer var4 = new AppGameContainer(var0, var1, var2, var3);
         var4.start();
      } catch (Exception var5) {
         var5.printStackTrace();
      }

   }
}
