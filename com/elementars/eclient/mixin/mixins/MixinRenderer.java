package com.elementars.eclient.mixin.mixins;

import com.elementars.eclient.Xulu;
import com.elementars.eclient.module.render.OutlineESP;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin({Render.class})
public abstract class MixinRenderer {
   @Shadow
   protected abstract boolean bindEntityTexture(Entity var1);

   @Redirect(
      method = {"doRender"},
      at = @At(
   value = "FIELD",
   target = "Lnet/minecraft/client/renderer/entity/Render;renderOutlines:Z"
)
   )
   public boolean doRender(Render var1) {
      return Xulu.MODULE_MANAGER.getModuleByName("OutlineESP").isToggled() && OutlineESP.krOE;
   }
}
