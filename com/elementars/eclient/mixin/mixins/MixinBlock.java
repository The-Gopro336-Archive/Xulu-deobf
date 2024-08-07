package com.elementars.eclient.mixin.mixins;

import com.elementars.eclient.module.render.Xray;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(
   value = {Block.class},
   priority = 9999
)
public class MixinBlock {
   @Inject(
      method = {"isFullCube"},
      at = {@At("HEAD")},
      cancellable = true
   )
   public void isFullCube(IBlockState var1, CallbackInfoReturnable var2) {
      try {
         if (Xray.INSTANCE != null && Xray.INSTANCE.isToggled()) {
            var2.setReturnValue(Xray.shouldXray((Block)Block.class.cast(this)));
            var2.cancel();
         }
      } catch (Exception var4) {
      }

   }

   @Inject(
      method = {"shouldSideBeRendered"},
      at = {@At("HEAD")},
      cancellable = true
   )
   public void shouldSideBeRendered(IBlockState var1, IBlockAccess var2, BlockPos var3, EnumFacing var4, CallbackInfoReturnable var5) {
      try {
         if (Xray.INSTANCE != null && Xray.INSTANCE.isToggled()) {
            var5.setReturnValue(Xray.shouldXray((Block)Block.class.cast(this)));
         }
      } catch (Exception var7) {
      }

   }

   @Inject(
      method = {"isOpaqueCube"},
      at = {@At("HEAD")},
      cancellable = true
   )
   public void isOpaqueCube(IBlockState var1, CallbackInfoReturnable var2) {
      try {
         if (Xray.INSTANCE != null && Xray.INSTANCE.isToggled()) {
            var2.setReturnValue(Xray.shouldXray((Block)Block.class.cast(this)));
         }
      } catch (Exception var4) {
      }

   }
}
