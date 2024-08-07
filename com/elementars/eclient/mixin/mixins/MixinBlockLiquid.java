package com.elementars.eclient.mixin.mixins;

import com.elementars.eclient.Xulu;
import com.elementars.eclient.module.misc.Avoid;
import com.elementars.eclient.module.misc.LiquidInteract;
import com.elementars.eclient.util.Wrapper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@SideOnly(Side.CLIENT)
@Mixin(
   value = {BlockLiquid.class},
   priority = 9999
)
public class MixinBlockLiquid {
   @Inject(
      method = {"modifyAcceleration"},
      at = {@At("HEAD")},
      cancellable = true
   )
   public void modifyAcceleration(World var1, BlockPos var2, Entity var3, Vec3d var4, CallbackInfoReturnable var5) {
      if (Xulu.MODULE_MANAGER.getModuleByName("Velocity").isToggled()) {
         var5.setReturnValue(var4);
         var5.cancel();
      }

   }

   @Inject(
      method = {"canCollideCheck"},
      at = {@At("RETURN")},
      cancellable = true,
      require = 1
   )
   private void IcanCollide(IBlockState var1, boolean var2, CallbackInfoReturnable var3) {
      var3.setReturnValue(LiquidInteract.INSTANCE.isToggled());
   }

   @Inject(
      method = {"getCollisionBoundingBox"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void getCollision(IBlockState var1, IBlockAccess var2, BlockPos var3, CallbackInfoReturnable var4) {
      if (Xulu.MODULE_MANAGER.getModule(Avoid.class).isToggled() && (Boolean)Avoid.lava.getValue() && (var1.getBlock() == Blocks.LAVA || var1.getBlock() == Blocks.FLOWING_LAVA) && Wrapper.getMinecraft().world.getBlockState((new BlockPos(Wrapper.getMinecraft().player.getPositionVector())).add(0, 1, 0)).getBlock() != Blocks.LAVA && Wrapper.getMinecraft().world.getBlockState((new BlockPos(Wrapper.getMinecraft().player.getPositionVector())).add(0, 1, 0)).getBlock() != Blocks.FLOWING_LAVA) {
         var4.setReturnValue(Block.FULL_BLOCK_AABB);
         var4.cancel();
      }

   }
}
