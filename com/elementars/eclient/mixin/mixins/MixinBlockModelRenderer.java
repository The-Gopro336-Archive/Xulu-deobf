package com.elementars.eclient.mixin.mixins;

import com.elementars.eclient.event.events.EventRenderBlock;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.BlockModelRenderer;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({BlockModelRenderer.class})
public class MixinBlockModelRenderer {
   @Inject(
      method = {"renderModel(Lnet/minecraft/world/IBlockAccess;Lnet/minecraft/client/renderer/block/model/IBakedModel;Lnet/minecraft/block/state/IBlockState;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/client/renderer/BufferBuilder;ZJ)Z"},
      at = {@At("HEAD")},
      cancellable = true
   )
   public void renderModel(IBlockAccess var1, IBakedModel var2, IBlockState var3, BlockPos var4, BufferBuilder var5, boolean var6, long var7, CallbackInfoReturnable var9) {
      EventRenderBlock var10 = new EventRenderBlock(var1, var2, var3, var4, var5, var6, var7);
      var10.call();
      if (var10.isCancelled()) {
         var9.setReturnValue(var10.isRenderable());
      }

   }
}
