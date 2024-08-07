package com.elementars.eclient.module.combat;

import com.elementars.eclient.module.Category;
import com.elementars.eclient.module.Module;
import com.elementars.eclient.util.Wrapper;
import net.minecraft.item.ItemExpBottle;

public class EXPFast extends Module {
   public void onUpdate() {
      if (this.isToggled()) {
         if (Wrapper.getMinecraft().player.inventory.getCurrentItem().getItem() instanceof ItemExpBottle) {
            Wrapper.getMinecraft().rightClickDelayTimer = 0;
         }

      }
   }

   public EXPFast() {
      super("EXPFast", "XP fast zoom", 0, Category.COMBAT, true);
   }
}
