package com.elementars.eclient.mixin.mixins;

import com.elementars.eclient.Xulu;
import com.elementars.eclient.module.misc.AntiSound;
import net.minecraft.block.BlockPortal;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin({BlockPortal.class})
public class MixinBlockPortal {
   @Redirect(
      method = {"randomDisplayTick"},
      at = @At(
   value = "INVOKE",
   target = "Lnet/minecraft/world/World;playSound(DDDLnet/minecraft/util/SoundEvent;Lnet/minecraft/util/SoundCategory;FFZ)V"
)
   )
   private void onPortalSound(World var1, double var2, double var4, double var6, SoundEvent var8, SoundCategory var9, float var10, float var11, boolean var12) {
      if (!Xulu.MODULE_MANAGER.getModule(AntiSound.class).isToggled() || !(Boolean)((AntiSound)Xulu.MODULE_MANAGER.getModuleT(AntiSound.class)).portal.getValue()) {
         var1.playSound(var2, var4, var6, var8, var9, var10, var11, var12);
      }

   }
}
