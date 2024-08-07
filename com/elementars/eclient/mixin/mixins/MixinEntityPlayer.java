package com.elementars.eclient.mixin.mixins;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryEnderChest;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin({EntityPlayer.class})
public abstract class MixinEntityPlayer extends MixinEntityLivingBase {
   @Shadow
   public InventoryEnderChest enderChest;
}
