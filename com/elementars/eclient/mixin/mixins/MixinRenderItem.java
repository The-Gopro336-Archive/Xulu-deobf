package com.elementars.eclient.mixin.mixins;

import com.elementars.eclient.Xulu;
import com.elementars.eclient.module.render.EnchantColor;
import com.elementars.eclient.module.render.ViewmodelChanger;
import com.elementars.eclient.util.Wrapper;
import java.awt.Color;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({RenderItem.class})
public class MixinRenderItem {
   @Shadow
   private void renderModel(IBakedModel var1, int var2, ItemStack var3) {
   }

   @ModifyArg(
      method = {"renderEffect"},
      at = @At(
   value = "INVOKE",
   target = "net/minecraft/client/renderer/RenderItem.renderModel(Lnet/minecraft/client/renderer/block/model/IBakedModel;I)V"
),
      index = 1
   )
   private int renderEffect(int var1) {
      return Xulu.MODULE_MANAGER.getModule(EnchantColor.class).isToggled() ? EnchantColor.getColor(1L, 1.0F).getRGB() : var1;
   }

   @Inject(
      method = {"renderItemModel"},
      at = {@At(
   value = "INVOKE",
   target = "Lnet/minecraft/client/renderer/RenderItem;renderItem(Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/renderer/block/model/IBakedModel;)V",
   shift = At.Shift.BEFORE
)}
   )
   private void test(ItemStack var1, IBakedModel var2, TransformType var3, boolean var4, CallbackInfo var5) {
      if (Xulu.MODULE_MANAGER.getModule(ViewmodelChanger.class) != null && ((ViewmodelChanger.HandMode)((ViewmodelChanger)Xulu.MODULE_MANAGER.getModuleT(ViewmodelChanger.class)).mode.getValue()).isOK(var4) && Xulu.MODULE_MANAGER.getModule(ViewmodelChanger.class).isToggled() && (Boolean)((ViewmodelChanger)Xulu.MODULE_MANAGER.getModuleT(ViewmodelChanger.class)).item.getValue() && (!(Boolean)((ViewmodelChanger)Xulu.MODULE_MANAGER.getModuleT(ViewmodelChanger.class)).pause.getValue() || !Wrapper.getMinecraft().player.isHandActive() || this.isHandGood(Wrapper.getMinecraft().player.getActiveHand(), var4))) {
         GlStateManager.scale((Float)((ViewmodelChanger)Xulu.MODULE_MANAGER.getModuleT(ViewmodelChanger.class)).sizex.getValue(), (Float)((ViewmodelChanger)Xulu.MODULE_MANAGER.getModuleT(ViewmodelChanger.class)).sizey.getValue(), (Float)((ViewmodelChanger)Xulu.MODULE_MANAGER.getModuleT(ViewmodelChanger.class)).sizez.getValue());
         GlStateManager.rotate((Float)((ViewmodelChanger)Xulu.MODULE_MANAGER.getModuleT(ViewmodelChanger.class)).x.getValue() * 360.0F, 1.0F, 0.0F, 0.0F);
         GlStateManager.rotate((Float)((ViewmodelChanger)Xulu.MODULE_MANAGER.getModuleT(ViewmodelChanger.class)).y.getValue() * 360.0F, 0.0F, 1.0F, 0.0F);
         GlStateManager.rotate((Float)((ViewmodelChanger)Xulu.MODULE_MANAGER.getModuleT(ViewmodelChanger.class)).z.getValue() * 360.0F, 0.0F, 0.0F, 1.0F);
         GlStateManager.translate((Float)((ViewmodelChanger)Xulu.MODULE_MANAGER.getModuleT(ViewmodelChanger.class)).posX.getValue(), (Float)((ViewmodelChanger)Xulu.MODULE_MANAGER.getModuleT(ViewmodelChanger.class)).posY.getValue(), (Float)((ViewmodelChanger)Xulu.MODULE_MANAGER.getModuleT(ViewmodelChanger.class)).posZ.getValue());
      }

   }

   private boolean isHandGood(EnumHand var1, boolean var2) {
      switch(var1) {
      case MAIN_HAND:
         return var2;
      case OFF_HAND:
         return !var2;
      default:
         return false;
      }
   }

   @Redirect(
      method = {"renderItem(Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/renderer/block/model/IBakedModel;)V"},
      at = @At(
   value = "INVOKE",
   target = "Lnet/minecraft/client/renderer/GlStateManager;color(FFFF)V"
)
   )
   private void color(float var1, float var2, float var3, float var4) {
      if (Xulu.MODULE_MANAGER.getModule(ViewmodelChanger.class) != null && Xulu.MODULE_MANAGER.getModule(ViewmodelChanger.class).isToggled()) {
         GlStateManager.color(var1, var2, var3, (Float)((ViewmodelChanger)Xulu.MODULE_MANAGER.getModuleT(ViewmodelChanger.class)).alpha.getValue());
      } else {
         GlStateManager.color(var1, var2, var3, var4);
      }

   }

   @Redirect(
      method = {"renderItem(Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/renderer/block/model/IBakedModel;)V"},
      at = @At(
   value = "INVOKE",
   target = "Lnet/minecraft/client/renderer/RenderItem;renderModel(Lnet/minecraft/client/renderer/block/model/IBakedModel;Lnet/minecraft/item/ItemStack;)V"
)
   )
   private void renderModelColor(RenderItem var1, IBakedModel var2, ItemStack var3) {
      if (Xulu.MODULE_MANAGER.getModule(ViewmodelChanger.class) != null && Xulu.MODULE_MANAGER.getModule(ViewmodelChanger.class).isToggled()) {
         this.renderModel(var2, (new Color(1.0F, 1.0F, 1.0F, (Float)((ViewmodelChanger)Xulu.MODULE_MANAGER.getModuleT(ViewmodelChanger.class)).alpha.getValue())).getRGB(), var3);
      } else {
         this.renderModel(var2, -1, var3);
      }

   }
}
