package com.elementars.eclient.mixin.mixins;

import com.elementars.eclient.event.events.CloseInventoryEvent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ContainerPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({ContainerPlayer.class})
public class MixinContainerPlayer {
   @Inject(
      method = {"onContainerClosed"},
      at = {@At("HEAD")},
      cancellable = true
   )
   public void getPlayerName(EntityPlayer var1, CallbackInfo var2) {
      CloseInventoryEvent var3 = new CloseInventoryEvent();
      var3.call();
      if (var3.isCancelled()) {
         var2.cancel();
      }

   }
}
