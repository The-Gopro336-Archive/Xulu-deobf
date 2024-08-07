package com.elementars.eclient.mixin.mixins;

import com.elementars.eclient.event.events.EventReceivePacket;
import com.elementars.eclient.event.events.EventSendPacket;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({NetworkManager.class})
public class MixinNetworkManager {
   @Inject(
      method = {"channelRead0"},
      at = {@At("HEAD")},
      cancellable = true
   )
   public void IchannelRead0(ChannelHandlerContext var1, Packet var2, CallbackInfo var3) {
      EventReceivePacket var4 = new EventReceivePacket(var2);
      var4.call();
      if (var4.isCancelled()) {
         var3.cancel();
      }

   }

   @Inject(
      method = {"sendPacket(Lnet/minecraft/network/Packet;)V"},
      at = {@At("HEAD")},
      cancellable = true
   )
   public void IsendPacket(Packet var1, CallbackInfo var2) {
      EventSendPacket var3 = new EventSendPacket(var1);
      var3.call();
      if (var3.isCancelled()) {
         var2.cancel();
      }

   }
}
