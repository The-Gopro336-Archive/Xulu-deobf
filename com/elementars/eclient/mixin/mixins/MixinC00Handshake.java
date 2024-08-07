package com.elementars.eclient.mixin.mixins;

import com.elementars.eclient.Xulu;
import net.minecraft.network.EnumConnectionState;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.handshake.client.C00Handshake;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({C00Handshake.class})
public class MixinC00Handshake {
   @Shadow
   int protocolVersion;
   @Shadow
   String ip;
   @Shadow
   int port;
   @Shadow
   EnumConnectionState requestedState;

   @Inject(
      method = {"writePacketData"},
      at = {@At("HEAD")},
      cancellable = true
   )
   public void writePacketData(PacketBuffer var1, CallbackInfo var2) {
      if (Xulu.MODULE_MANAGER.getModuleByName("FakeVanilla").isToggled()) {
         var2.cancel();
         var1.writeVarInt(this.protocolVersion);
         var1.writeString(this.ip);
         var1.writeShort(this.port);
         var1.writeVarInt(this.requestedState.getId());
      }

   }
}
