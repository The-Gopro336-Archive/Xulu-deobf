package com.elementars.eclient.mixin.mixins;

import com.elementars.eclient.module.render.ItemESP;
import net.minecraft.client.renderer.entity.RenderEntityItem;
import net.minecraft.entity.item.EntityItem;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({RenderEntityItem.class})
public class MixinRenderEntityItem {
   @Inject(
      method = {"doRender"},
      at = {@At("HEAD")}
   )
   private void injectChamsPre(EntityItem var1, double var2, double var4, double var6, float var8, float var9, CallbackInfo var10) {
      if (ItemESP.INSTANCE.isToggled() && ((String)ItemESP.INSTANCE.mode.getValue()).equalsIgnoreCase("chams")) {
         GL11.glEnable(32823);
         GL11.glPolygonOffset(1.0F, -1000000.0F);
      }

   }

   @Inject(
      method = {"doRender"},
      at = {@At("RETURN")}
   )
   private void injectChamsPost(EntityItem var1, double var2, double var4, double var6, float var8, float var9, CallbackInfo var10) {
      if (ItemESP.INSTANCE.isToggled() && ((String)ItemESP.INSTANCE.mode.getValue()).equalsIgnoreCase("chams")) {
         GL11.glPolygonOffset(1.0F, 1000000.0F);
         GL11.glDisable(32823);
      }

   }
}
