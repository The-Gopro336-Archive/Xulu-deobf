package com.elementars.eclient.mixin.mixins;

import com.elementars.eclient.event.events.EventDrinkPotion;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({ItemPotion.class})
public class MixinItemPotion {
   @Inject(
      method = {"onItemUseFinish"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void onItemUseFinish(ItemStack var1, World var2, EntityLivingBase var3, CallbackInfoReturnable var4) {
      EventDrinkPotion var5 = new EventDrinkPotion(var3, var1);
      var5.call();
   }
}
