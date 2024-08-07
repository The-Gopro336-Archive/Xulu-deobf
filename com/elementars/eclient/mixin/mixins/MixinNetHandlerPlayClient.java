package com.elementars.eclient.mixin.mixins;

import com.elementars.eclient.Xulu;
import com.elementars.eclient.event.events.ChunkEvent;
import com.elementars.eclient.module.misc.AntiSound;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.network.play.server.SPacketChunkData;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.chunk.Chunk;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin({NetHandlerPlayClient.class})
public class MixinNetHandlerPlayClient {
   @Inject(
      method = {"handleChunkData"},
      at = {@At(
   value = "INVOKE",
   target = "Lnet/minecraft/world/chunk/Chunk;read(Lnet/minecraft/network/PacketBuffer;IZ)V"
)},
      locals = LocalCapture.CAPTURE_FAILHARD
   )
   private void read(SPacketChunkData var1, CallbackInfo var2, Chunk var3) {
      (new ChunkEvent(var3, var1)).call();
   }

   @Redirect(
      method = {"handleEntityStatus"},
      at = @At(
   value = "INVOKE",
   target = "Lnet/minecraft/client/multiplayer/WorldClient;playSound(DDDLnet/minecraft/util/SoundEvent;Lnet/minecraft/util/SoundCategory;FFZ)V"
)
   )
   private void playTotemSound(WorldClient var1, double var2, double var4, double var6, SoundEvent var8, SoundCategory var9, float var10, float var11, boolean var12) {
      if (!Xulu.MODULE_MANAGER.getModule(AntiSound.class).isToggled() || !(Boolean)((AntiSound)Xulu.MODULE_MANAGER.getModuleT(AntiSound.class)).totem.getValue()) {
         var1.playSound(var2, var4, var6, var8, var9, var10, var11, var12);
      }

   }
}
