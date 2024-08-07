package com.elementars.eclient.mixin.mixins;

import com.elementars.eclient.module.core.Global;
import net.minecraft.client.gui.FontRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin({FontRenderer.class})
public class MixinFontRenderer {
   @Shadow
   private int renderString(String var1, float var2, float var3, int var4, boolean var5) {
      return 0;
   }

   @Redirect(
      method = {"drawStringWithShadow"},
      at = @At(
   value = "INVOKE",
   target = "Lnet/minecraft/client/gui/FontRenderer;drawString(Ljava/lang/String;FFIZ)I"
)
   )
   private int drawStringMaybeWithShadow(FontRenderer var1, String var2, float var3, float var4, int var5, boolean var6) {
      return (Boolean)Global.textShadow.getValue() ? var1.drawString(var2, var3, var4, var5, true) : var1.drawString(var2, var3, var4, var5, false);
   }

   @Redirect(
      method = {"drawString(Ljava/lang/String;FFIZ)I"},
      at = @At(
   value = "INVOKE",
   ordinal = 0,
   target = "Lnet/minecraft/client/gui/FontRenderer;renderString(Ljava/lang/String;FFIZ)I"
)
   )
   private int drawStringMore(FontRenderer var1, String var2, float var3, float var4, int var5, boolean var6) {
      if (Global.shortShadow != null && (Boolean)Global.shortShadow.getValue()) {
         var3 -= 0.4F;
         var4 -= 0.4F;
      }

      return this.renderString(var2, var3, var4, var5, var6);
   }
}
