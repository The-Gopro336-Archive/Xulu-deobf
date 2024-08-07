package com.elementars.eclient.module.movement;

import com.elementars.eclient.module.Category;
import com.elementars.eclient.module.Module;
import com.elementars.eclient.util.Wrapper;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.settings.KeyBinding;
import org.lwjgl.input.Keyboard;

public class GuiMove extends Module {
   public GuiMove() {
      super("GuiMove", "Move in gui menus", 0, Category.MOVEMENT, true);
   }

   public void onUpdate() {
      if (!(mc.currentScreen instanceof GuiChat) && mc.currentScreen != null) {
         int[] var1 = new int[]{mc.gameSettings.keyBindForward.getKeyCode(), mc.gameSettings.keyBindLeft.getKeyCode(), mc.gameSettings.keyBindRight.getKeyCode(), mc.gameSettings.keyBindBack.getKeyCode(), mc.gameSettings.keyBindJump.getKeyCode()};
         int[] var2 = var1;
         int var3 = var1.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            int var5 = var2[var4];
            if (Keyboard.isKeyDown(var5)) {
               KeyBinding.setKeyBindState(var5, true);
            } else {
               KeyBinding.setKeyBindState(var5, false);
            }
         }

         if (Wrapper.getMinecraft().currentScreen instanceof GuiContainer) {
            EntityPlayerSP var10000;
            if (Keyboard.isKeyDown(Integer.valueOf(200))) {
               var10000 = Wrapper.getMinecraft().player;
               var10000.rotationPitch -= 7.0F;
            }

            if (Keyboard.isKeyDown(Integer.valueOf(208))) {
               var10000 = Wrapper.getMinecraft().player;
               var10000.rotationPitch += 7.0F;
            }

            if (Keyboard.isKeyDown(Integer.valueOf(205))) {
               var10000 = Wrapper.getMinecraft().player;
               var10000.rotationYaw += 7.0F;
            }

            if (Keyboard.isKeyDown(Integer.valueOf(203))) {
               var10000 = Wrapper.getMinecraft().player;
               var10000.rotationYaw -= 7.0F;
            }

            if (Keyboard.isKeyDown(Wrapper.getMinecraft().gameSettings.keyBindSprint.getKeyCode())) {
               Wrapper.getMinecraft().player.setSprinting(true);
            }
         }

      }
   }
}
