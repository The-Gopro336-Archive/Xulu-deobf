package com.elementars.eclient.mixin.mixins;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumHand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin({EntityLivingBase.class})
public abstract class MixinEntityLivingBase extends MixinEntity {
   @Shadow
   public void swingArm(EnumHand var1) {
   }
}
