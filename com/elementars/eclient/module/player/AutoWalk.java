package com.elementars.eclient.module.player;

import com.elementars.eclient.module.Category;
import com.elementars.eclient.module.Module;
import net.minecraft.client.settings.KeyBinding;

public class AutoWalk extends Module {
   public void onUpdate() {
      KeyBinding.setKeyBindState(mc.gameSettings.keyBindForward.getKeyCode(), true);
   }

   public AutoWalk() {
      super("AutoWalk", "Automatically walks", 0, Category.PLAYER, true);
   }
}
