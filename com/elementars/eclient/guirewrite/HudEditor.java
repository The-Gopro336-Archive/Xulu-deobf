package com.elementars.eclient.guirewrite;

import com.elementars.eclient.Xulu;
import com.elementars.eclient.module.Category;
import com.elementars.eclient.module.Module;

public class HudEditor extends Module {
   public void onEnable() {
      mc.displayGuiScreen(Xulu.hud);
      this.toggle();
   }

   public void setup() {
      this.setKey(0);
   }

   public HudEditor() {
      super("HudEditor", "Editor for HUD elements", 0, Category.CORE, false);
   }
}
