package com.elementars.eclient.mixin.mixins;

import com.elementars.eclient.event.events.EventClickBlock;
import com.elementars.eclient.event.events.EventPlayerDamageBlock;
import com.elementars.eclient.event.events.EventResetBlockRemoving;
import com.elementars.eclient.module.player.TpsSync;
import com.elementars.eclient.util.LagCompensator;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({PlayerControllerMP.class})
public class MixinPlayerControllerMP {
   @Redirect(
      method = {"onPlayerDamageBlock"},
      at = @At(
   value = "INVOKE",
   target = "Lnet/minecraft/block/state/IBlockState;getPlayerRelativeBlockHardness(Lnet/minecraft/entity/player/EntityPlayer;Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;)F"
)
   )
   float getPlayerRelativeBlockHardness(IBlockState var1, EntityPlayer var2, World var3, BlockPos var4) {
      return var1.getPlayerRelativeBlockHardness(var2, var3, var4) * (TpsSync.isSync() ? LagCompensator.INSTANCE.getTickRate() / 20.0F : 1.0F);
   }

   @Inject(
      method = {"onPlayerDamageBlock"},
      at = {@At("HEAD")},
      cancellable = true
   )
   public void test(BlockPos var1, EnumFacing var2, CallbackInfoReturnable var3) {
      EventPlayerDamageBlock var4 = new EventPlayerDamageBlock(var1, var2);
      var4.call();
      if (var4.isCancelled()) {
         var3.cancel();
      }

   }

   @Inject(
      method = {"clickBlock"},
      at = {@At("HEAD")},
      cancellable = true
   )
   public void onClickBlock(BlockPos var1, EnumFacing var2, CallbackInfoReturnable var3) {
      EventClickBlock var4 = new EventClickBlock(var1, var2);
      var4.call();
      if (var4.isCancelled()) {
         var3.cancel();
      }

   }

   @Inject(
      method = {"resetBlockRemoving"},
      at = {@At("HEAD")},
      cancellable = true
   )
   public void onBlockDestroyed(CallbackInfo var1) {
      EventResetBlockRemoving var2 = new EventResetBlockRemoving();
      var2.call();
      if (var2.isCancelled()) {
         var1.cancel();
      }

   }
}
