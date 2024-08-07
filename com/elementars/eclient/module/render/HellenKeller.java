package com.elementars.eclient.module.render;

import com.elementars.eclient.module.Category;
import com.elementars.eclient.module.Module;
import java.awt.Color;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.SoundCategory;

public class HellenKeller extends Module {
   // $FF: synthetic field
   float masterLevel;

   public void onRender() {
      GlStateManager.pushMatrix();
      Gui.drawRect(0, 0, mc.displayWidth, mc.displayHeight, (new Color(0, 0, 0, 255)).getRGB());
      GlStateManager.popMatrix();
   }

   public void onEnable() {
      this.masterLevel = mc.gameSettings.getSoundLevel(SoundCategory.MASTER);
      mc.gameSettings.setSoundLevel(SoundCategory.MASTER, 0.0F);
   }

   public void onDisable() {
      mc.gameSettings.setSoundLevel(SoundCategory.MASTER, this.masterLevel);
   }

   public HellenKeller() {
      super("HellenKeller", "Play like Hellen Keller", 0, Category.RENDER, true);
   }
}
