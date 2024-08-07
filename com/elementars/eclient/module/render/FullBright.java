package com.elementars.eclient.module.render;

import com.elementars.eclient.module.Category;
import com.elementars.eclient.module.Module;

public class FullBright extends Module {
   // $FF: synthetic field
   float oldBrightness;

   public FullBright() {
      super("Fullbright", "Permanent brightness", 0, Category.RENDER, true);
   }

   public void onEnable() {
      this.oldBrightness = mc.gameSettings.gammaSetting;
      mc.gameSettings.gammaSetting = 1000.0F;
   }

   public void onDisable() {
      mc.gameSettings.gammaSetting = this.oldBrightness;
   }

   public void onUpdate() {
      if (mc.gameSettings.gammaSetting != 1000.0F) {
         mc.gameSettings.gammaSetting = 1000.0F;
      }

   }
}
