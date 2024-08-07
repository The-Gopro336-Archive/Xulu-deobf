package com.elementars.eclient.mixin.mixins;

import com.elementars.eclient.Xulu;
import net.minecraft.block.BlockSoulSand;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({BlockSoulSand.class})
public class MixinBlockSoulSand {
   @Inject(
      method = {"onEntityCollision"},
      at = {@At("HEAD")},
      cancellable = true
   )
   public void onEntityCollidedWithBlock(World var1, BlockPos var2, IBlockState var3, Entity var4, CallbackInfo var5) {
      if (Xulu.MODULE_MANAGER.getModuleByName("NoSlowDown").isToggled()) {
         var5.cancel();
      }

   }
}
