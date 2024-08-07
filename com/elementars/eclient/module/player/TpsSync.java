package com.elementars.eclient.module.player;

import com.elementars.eclient.module.Category;
import com.elementars.eclient.module.Module;

public class TpsSync extends Module {
   // $FF: synthetic field
   private static TpsSync INSTANCE;

   public static boolean isSync() {
      return INSTANCE.isToggled();
   }

   public TpsSync() {
      super("TpsSync", "Syncs client with the tps", 0, Category.PLAYER, true);
      INSTANCE = this;
   }
}
