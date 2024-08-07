package com.elementars.eclient.mixin.mixins;

import com.elementars.eclient.cape.Capes;
import com.elementars.eclient.module.render.Cape;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.util.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({AbstractClientPlayer.class})
public abstract class MixinAbstractClientPlayer extends MixinEntityPlayer {
   private Minecraft minecraft = Minecraft.getMinecraft();

   @Shadow
   @Nullable
   protected abstract NetworkPlayerInfo getPlayerInfo();

   @Inject(
      method = {"getLocationCape"},
      at = {@At("HEAD")},
      cancellable = true
   )
   public void getLocationCape(CallbackInfoReturnable var1) {
      if (Cape.isEnabled()) {
         NetworkPlayerInfo var2 = this.getPlayerInfo();
         if (var2 != null && Capes.isCapeUser(var2.getGameProfile().getName())) {
            var1.setReturnValue(new ResourceLocation("textures/eclient/cape.png"));
         }
      }

   }
}
