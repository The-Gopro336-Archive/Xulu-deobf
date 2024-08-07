package com.elementars.eclient.module.misc;

import com.elementars.eclient.module.Category;
import com.elementars.eclient.module.Module;

public class NoPacketKick extends Module {
   // $FF: synthetic field
   private static NoPacketKick INSTANCE;

   public static boolean isEnabled() {
      return INSTANCE.isToggled();
   }

   public NoPacketKick() {
      super("NoPacketKick", "Prevents packet kicks", 0, Category.MISC, true);
      INSTANCE = this;
   }
}
