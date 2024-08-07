package com.elementars.eclient.module.render;

import com.elementars.eclient.module.Category;
import com.elementars.eclient.module.Module;

public class Cape extends Module {
   // $FF: synthetic field
   public static Cape INSTANCE;

   public static boolean isEnabled() {
      return INSTANCE.isToggled();
   }

   public Cape() {
      super("Capes", "Shows Xulu capes", 0, Category.CORE, true);
      INSTANCE = this;
   }
}
