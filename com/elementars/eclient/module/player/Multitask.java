package com.elementars.eclient.module.player;

import com.elementars.eclient.event.EventTarget;
import com.elementars.eclient.event.events.AllowInteractEvent;
import com.elementars.eclient.module.Category;
import com.elementars.eclient.module.Module;

public class Multitask extends Module {
   @EventTarget
   public void onUseItem(AllowInteractEvent var1) {
      var1.usingItem = false;
   }

   public Multitask() {
      super("Multitask", "Credit to luchadora client", 0, Category.PLAYER, true);
   }
}
