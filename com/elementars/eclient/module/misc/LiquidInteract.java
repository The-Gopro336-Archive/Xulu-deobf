package com.elementars.eclient.module.misc;

import com.elementars.eclient.module.Category;
import com.elementars.eclient.module.Module;

public class LiquidInteract extends Module {
   // $FF: synthetic field
   public static LiquidInteract INSTANCE;

   public LiquidInteract() {
      super("LiquidInteract", "Allows you to place blocks on liquids", 0, Category.MISC, true);
      INSTANCE = this;
   }
}
