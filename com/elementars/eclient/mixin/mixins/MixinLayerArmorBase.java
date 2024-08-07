package com.elementars.eclient.mixin.mixins;

import com.elementars.eclient.Xulu;
import com.elementars.eclient.module.render.EnchantColor;
import com.elementars.eclient.module.render.NoRender;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.layers.LayerArmorBase;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({LayerArmorBase.class})
public class MixinLayerArmorBase {
   @Redirect(
      method = {"renderEnchantedGlint"},
      at = @At(
   value = "INVOKE",
   target = "net/minecraft/client/renderer/GlStateManager.color(FFFF)V",
   ordinal = 1
)
   )
   private static void renderEnchantedGlint(float var0, float var1, float var2, float var3) {
      GlStateManager.color(Xulu.MODULE_MANAGER.getModule(EnchantColor.class).isToggled() ? (float)EnchantColor.getColor(1L, 1.0F).getRed() : var0, Xulu.MODULE_MANAGER.getModule(EnchantColor.class).isToggled() ? (float)EnchantColor.getColor(1L, 1.0F).getGreen() : var1, Xulu.MODULE_MANAGER.getModule(EnchantColor.class).isToggled() ? (float)EnchantColor.getColor(1L, 1.0F).getBlue() : var2, Xulu.MODULE_MANAGER.getModule(EnchantColor.class).isToggled() ? (float)EnchantColor.getColor(1L, 1.0F).getAlpha() : var3);
   }

   @Inject(
      method = {"renderEnchantedGlint"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private static void renderGlint(RenderLivingBase var0, EntityLivingBase var1, ModelBase var2, float var3, float var4, float var5, float var6, float var7, float var8, float var9, CallbackInfo var10) {
      if ((Boolean)((NoRender)Xulu.MODULE_MANAGER.getModuleT(NoRender.class)).armor.getValue() && Xulu.MODULE_MANAGER.getModule(NoRender.class).isToggled()) {
         var10.cancel();
      }

   }

   @Inject(
      method = {"renderArmorLayer"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void renderArmorLayer(EntityLivingBase var1, float var2, float var3, float var4, float var5, float var6, float var7, float var8, EntityEquipmentSlot var9, CallbackInfo var10) {
      if ((Boolean)((NoRender)Xulu.MODULE_MANAGER.getModuleT(NoRender.class)).armor.getValue() && Xulu.MODULE_MANAGER.getModule(NoRender.class).isToggled()) {
         var10.cancel();
      }

   }

   @Redirect(
      method = {"renderArmorLayer"},
      at = @At(
   value = "INVOKE",
   target = "Lnet/minecraft/client/renderer/GlStateManager;color(FFFF)V",
   ordinal = 1
)
   )
   private void color(float var1, float var2, float var3, float var4) {
      NoRender var5 = (NoRender)Xulu.MODULE_MANAGER.getModuleT(NoRender.class);
      if ((Boolean)var5.armorTrans.getValue() && var5.isToggled()) {
         GlStateManager.color(var1, var2, var3, (float)(Integer)var5.alpha.getValue() / 255.0F);
      } else {
         GlStateManager.color(var1, var2, var3, var4);
      }

   }

   @Redirect(
      method = {"renderArmorLayer"},
      at = @At(
   value = "INVOKE",
   target = "Lnet/minecraft/client/renderer/GlStateManager;color(FFFF)V",
   ordinal = 2
)
   )
   private void color2(float var1, float var2, float var3, float var4) {
      NoRender var5 = (NoRender)Xulu.MODULE_MANAGER.getModuleT(NoRender.class);
      if ((Boolean)var5.armorTrans.getValue() && var5.isToggled()) {
         GlStateManager.color(var1, var2, var3, (float)(Integer)var5.alpha.getValue() / 255.0F);
      } else {
         GlStateManager.color(var1, var2, var3, var4);
      }

   }
}
