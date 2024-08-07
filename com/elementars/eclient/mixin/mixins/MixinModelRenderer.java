package com.elementars.eclient.mixin.mixins;

import com.elementars.eclient.Xulu;
import com.elementars.eclient.module.render.ViewmodelChanger;
import com.elementars.eclient.util.Wrapper;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({ModelRenderer.class})
public class MixinModelRenderer {
   @Inject(
      method = {"render"},
      at = {@At(
   value = "INVOKE",
   target = "Lnet/minecraft/client/renderer/GlStateManager;callList(I)V",
   shift = At.Shift.BEFORE
)}
   )
   private void test(float var1, CallbackInfo var2) {
      if (ModelRenderer.class.cast(this) == Wrapper.getMinecraft().renderManager.playerRenderer.getMainModel().bipedRightArm) {
         if (Xulu.MODULE_MANAGER.getModule(ViewmodelChanger.class) != null && (Boolean)((ViewmodelChanger)Xulu.MODULE_MANAGER.getModuleT(ViewmodelChanger.class)).hand.getValue() && Xulu.MODULE_MANAGER.getModule(ViewmodelChanger.class).isToggled()) {
            GlStateManager.scale((Float)((ViewmodelChanger)Xulu.MODULE_MANAGER.getModuleT(ViewmodelChanger.class)).sizex.getValue(), (Float)((ViewmodelChanger)Xulu.MODULE_MANAGER.getModuleT(ViewmodelChanger.class)).sizey.getValue(), (Float)((ViewmodelChanger)Xulu.MODULE_MANAGER.getModuleT(ViewmodelChanger.class)).sizez.getValue());
            GlStateManager.translate((Float)((ViewmodelChanger)Xulu.MODULE_MANAGER.getModuleT(ViewmodelChanger.class)).posX.getValue(), (Float)((ViewmodelChanger)Xulu.MODULE_MANAGER.getModuleT(ViewmodelChanger.class)).posY.getValue(), (Float)((ViewmodelChanger)Xulu.MODULE_MANAGER.getModuleT(ViewmodelChanger.class)).posZ.getValue());
         }
      } else if (ModelRenderer.class.cast(this) == Wrapper.getMinecraft().renderManager.playerRenderer.getMainModel().bipedRightArmwear && Xulu.MODULE_MANAGER.getModule(ViewmodelChanger.class) != null && (Boolean)((ViewmodelChanger)Xulu.MODULE_MANAGER.getModuleT(ViewmodelChanger.class)).hand.getValue() && Xulu.MODULE_MANAGER.getModule(ViewmodelChanger.class).isToggled()) {
         GlStateManager.scale((Float)((ViewmodelChanger)Xulu.MODULE_MANAGER.getModuleT(ViewmodelChanger.class)).sizex.getValue(), (Float)((ViewmodelChanger)Xulu.MODULE_MANAGER.getModuleT(ViewmodelChanger.class)).sizey.getValue(), (Float)((ViewmodelChanger)Xulu.MODULE_MANAGER.getModuleT(ViewmodelChanger.class)).sizez.getValue());
         GlStateManager.translate((Float)((ViewmodelChanger)Xulu.MODULE_MANAGER.getModuleT(ViewmodelChanger.class)).posX.getValue(), (Float)((ViewmodelChanger)Xulu.MODULE_MANAGER.getModuleT(ViewmodelChanger.class)).posY.getValue(), (Float)((ViewmodelChanger)Xulu.MODULE_MANAGER.getModuleT(ViewmodelChanger.class)).posZ.getValue());
      }

   }
}
