package com.elementars.eclient.mixin.mixins;

import com.elementars.eclient.Xulu;
import com.elementars.eclient.module.misc.Avoid;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCactus;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({BlockCactus.class})
public class MixinBlockCactus {
   @Inject(
      method = {"getCollisionBoundingBox"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void getCollision(IBlockState var1, IBlockAccess var2, BlockPos var3, CallbackInfoReturnable var4) {
      if (Xulu.MODULE_MANAGER.getModule(Avoid.class).isToggled() && (Boolean)Avoid.cactus.getValue()) {
         var4.setReturnValue(Block.FULL_BLOCK_AABB);
         var4.cancel();
      }

   }
}
