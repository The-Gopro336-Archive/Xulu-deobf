package com.elementars.eclient.mixin.mixins;

import com.elementars.eclient.Xulu;
import com.elementars.eclient.module.ModuleManager;
import com.elementars.eclient.module.render.Chams;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.EntityLiving;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({RenderLiving.class})
public class MixinRenderLiving {
   @Inject(
      method = {"doRender"},
      at = {@At("HEAD")}
   )
   private void injectChamsPre(EntityLiving var1, double var2, double var4, double var6, float var8, float var9, CallbackInfo var10) {
      if (Xulu.MODULE_MANAGER.getModuleByName("Chams").isToggled() && Chams.renderChams(var1)) {
         GL11.glEnable(32823);
         GL11.glPolygonOffset(1.0F, -1000000.0F);
      }

   }

   @Inject(
      method = {"doRender"},
      at = {@At("RETURN")}
   )
   private void injectChamsPost(EntityLiving var1, double var2, double var4, double var6, float var8, float var9, CallbackInfo var10) {
      if (ModuleManager.isModuleEnabled("Chams") && Chams.renderChams(var1)) {
         GL11.glPolygonOffset(1.0F, 1000000.0F);
         GL11.glDisable(32823);
      }

   }
}
