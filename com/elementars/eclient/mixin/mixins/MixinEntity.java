package com.elementars.eclient.mixin.mixins;

import com.elementars.eclient.event.events.EntityEvent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MoverType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(
   value = {Entity.class},
   priority = 9999
)
public abstract class MixinEntity {
   @Shadow
   public float entityCollisionReduction;
   @Shadow
   public double posX;
   @Shadow
   public double posY;
   @Shadow
   public double posZ;
   @Shadow
   public double prevPosX;
   @Shadow
   public double prevPosY;
   @Shadow
   public double prevPosZ;
   @Shadow
   public double lastTickPosX;
   @Shadow
   public double lastTickPosY;
   @Shadow
   public double lastTickPosZ;
   @Shadow
   public float prevRotationYaw;
   @Shadow
   public float prevRotationPitch;
   @Shadow
   public float rotationPitch;
   @Shadow
   public float rotationYaw;
   @Shadow
   public boolean onGround;
   @Shadow
   public double motionX;
   @Shadow
   public double motionY;
   @Shadow
   public double motionZ;

   @Redirect(
      method = {"applyEntityCollision"},
      at = @At(
   value = "INVOKE",
   target = "Lnet/minecraft/entity/Entity;addVelocity(DDD)V"
)
   )
   public void addVelocity(Entity var1, double var2, double var4, double var6) {
      EntityEvent.EntityCollision var8 = new EntityEvent.EntityCollision(var1, var2, var4, var6);
      var8.call();
      if (!var8.isCancelled()) {
         var1.motionX += var2;
         var1.motionY += var4;
         var1.motionZ += var6;
         var1.isAirBorne = true;
      }
   }

   @Shadow
   public abstract boolean equals(Object var1);

   @Shadow
   public abstract boolean isSprinting();

   @Shadow
   public abstract boolean isRiding();

   @Shadow
   public void move(MoverType var1, double var2, double var4, double var6) {
   }
}
