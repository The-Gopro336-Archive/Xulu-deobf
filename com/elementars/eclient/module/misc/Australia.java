package com.elementars.eclient.module.misc;

import com.elementars.eclient.module.Category;
import com.elementars.eclient.module.Module;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

public class Australia extends Module {
   public void onUpdate() {
      if (OpenGlHelper.shadersSupported && mc.getRenderViewEntity() instanceof EntityPlayer) {
         if (mc.entityRenderer.getShaderGroup() != null) {
            mc.entityRenderer.getShaderGroup().deleteShaderGroup();
         }

         try {
            mc.entityRenderer.loadShader(new ResourceLocation("shaders/post/flip.json"));
         } catch (Exception var3) {
            var3.printStackTrace();
         }
      } else if (mc.entityRenderer.getShaderGroup() != null && mc.currentScreen == null) {
         mc.entityRenderer.getShaderGroup().deleteShaderGroup();
      }

      mc.player.setFire(1);
   }

   public Australia() {
      super("Australia", "Best Module", 0, Category.MISC, true);
   }
}
