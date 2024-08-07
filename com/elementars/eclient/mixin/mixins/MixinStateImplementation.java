package com.elementars.eclient.mixin.mixins;

import com.elementars.eclient.event.events.AddCollisionBoxToListEvent;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.state.BlockStateContainer.StateImplementation;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin({StateImplementation.class})
public class MixinStateImplementation {
   @Shadow
   @Final
   private Block block;

   @Redirect(
      method = {"addCollisionBoxToList"},
      at = @At(
   value = "INVOKE",
   target = "Lnet/minecraft/block/Block;addCollisionBoxToList(Lnet/minecraft/block/state/IBlockState;Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/math/AxisAlignedBB;Ljava/util/List;Lnet/minecraft/entity/Entity;Z)V"
)
   )
   public void addCollisionBoxToList(Block var1, IBlockState var2, World var3, BlockPos var4, AxisAlignedBB var5, List var6, @Nullable Entity var7, boolean var8) {
      AddCollisionBoxToListEvent var9 = new AddCollisionBoxToListEvent(var1, var2, var3, var4, var5, var6, var7, var8);
      var9.call();
      if (!var9.isCancelled()) {
         this.block.addCollisionBoxToList(var2, var3, var4, var5, var6, var7, var8);
      }

   }
}
