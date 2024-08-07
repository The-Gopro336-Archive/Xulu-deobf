package com.elementars.eclient.module.misc;

import com.elementars.eclient.module.Category;
import com.elementars.eclient.module.Module;
import net.minecraft.client.gui.GuiGameOver;
import net.minecraft.client.gui.GuiScreen;

public class AntiDeathScreen extends Module {
   public AntiDeathScreen() {
      super("AntiDeathScreen", "AntiDeathScreen apparently", 0, Category.MISC, true);
   }

   public void onUpdate() {
      if (this.isToggled() && mc.currentScreen instanceof GuiGameOver && mc.player.getHealth() > 0.0F) {
         mc.player.respawnPlayer();
         mc.displayGuiScreen((GuiScreen)null);
      }

   }
}
