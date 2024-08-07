package com.elementars.eclient.mixin.mixins;

import com.elementars.eclient.Xulu;
import com.elementars.eclient.module.misc.NoEntityTrace;
import com.elementars.eclient.module.render.NoHurtCam;
import com.google.common.base.Predicate;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(
   value = {EntityRenderer.class},
   priority = Integer.MAX_VALUE
)
public class MixinEntityRenderer {
   private boolean nightVision = false;

   @Redirect(
      method = {"orientCamera"},
      at = @At(
   value = "INVOKE",
   target = "Lnet/minecraft/client/multiplayer/WorldClient;rayTraceBlocks(Lnet/minecraft/util/math/Vec3d;Lnet/minecraft/util/math/Vec3d;)Lnet/minecraft/util/math/RayTraceResult;"
)
   )
   public RayTraceResult rayTraceBlocks(WorldClient var1, Vec3d var2, Vec3d var3) {
      return Xulu.MODULE_MANAGER.getModuleByName("CameraClip").isToggled() ? null : var1.rayTraceBlocks(var2, var3);
   }

   @Inject(
      method = {"hurtCameraEffect"},
      at = {@At("HEAD")},
      cancellable = true
   )
   public void hurtCameraEffect(float var1, CallbackInfo var2) {
      if (NoHurtCam.shouldDisable()) {
         var2.cancel();
      }

   }

   @Redirect(
      method = {"getMouseOver"},
      at = @At(
   value = "INVOKE",
   target = "Lnet/minecraft/client/multiplayer/WorldClient;getEntitiesInAABBexcluding(Lnet/minecraft/entity/Entity;Lnet/minecraft/util/math/AxisAlignedBB;Lcom/google/common/base/Predicate;)Ljava/util/List;"
)
   )
   public List getEntitiesInAABBexcluding(WorldClient var1, Entity var2, AxisAlignedBB var3, Predicate var4) {
      return (List)(NoEntityTrace.shouldBlock() ? new ArrayList() : var1.getEntitiesInAABBexcluding(var2, var3, var4));
   }
}
