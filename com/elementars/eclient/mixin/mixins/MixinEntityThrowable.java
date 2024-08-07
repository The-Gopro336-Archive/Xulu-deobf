package com.elementars.eclient.mixin.mixins;

import com.elementars.eclient.event.events.EventThrow;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.EntityThrowable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({EntityThrowable.class})
public class MixinEntityThrowable {
   @Inject(
      method = {"shoot(Lnet/minecraft/entity/Entity;FFFFF)V"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void shoot(Entity var1, float var2, float var3, float var4, float var5, float var6, CallbackInfo var7) {
      EventThrow var8 = new EventThrow(var1, (EntityThrowable)EntityThrowable.class.cast(this), var3);
      var8.call();
      if (var8.isCancelled()) {
         var7.cancel();
      }

   }
}
