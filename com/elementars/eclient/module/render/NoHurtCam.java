package com.elementars.eclient.module.render;

import com.elementars.eclient.module.Category;
import com.elementars.eclient.module.Module;

public class NoHurtCam extends Module {
   // $FF: synthetic field
   private static NoHurtCam INSTANCE;

   public NoHurtCam() {
      super("NoHurtCam", "Disables the hurt cam", 0, Category.RENDER, true);
      INSTANCE = this;
   }

   public static boolean shouldDisable() {
      return INSTANCE != null && INSTANCE.isToggled();
   }
}
