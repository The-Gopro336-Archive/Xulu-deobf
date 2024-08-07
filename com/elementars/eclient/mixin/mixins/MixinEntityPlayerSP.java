package com.elementars.eclient.mixin.mixins;

import com.elementars.eclient.Xulu;
import com.elementars.eclient.event.Event;
import com.elementars.eclient.event.events.EventPreMotionUpdates;
import com.elementars.eclient.event.events.MotionEvent;
import com.elementars.eclient.event.events.MotionEventPost;
import com.elementars.eclient.event.events.PlayerMoveEvent;
import com.elementars.eclient.module.ModuleManager;
import com.elementars.eclient.module.misc.AntiSound;
import com.elementars.eclient.module.render.OffhandSwing;
import com.elementars.eclient.util.Wrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.entity.MoverType;
import net.minecraft.network.play.client.CPacketAnimation;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({EntityPlayerSP.class})
public abstract class MixinEntityPlayerSP extends MixinAbstractClientPlayer {
   @Final
   public NetHandlerPlayClient connection;

   @Shadow
   public abstract void move(MoverType var1, double var2, double var4, double var6);

   @Shadow
   public abstract void swingArm(EnumHand var1);

   @Redirect(
      method = {"onLivingUpdate"},
      at = @At(
   value = "INVOKE",
   target = "Lnet/minecraft/client/entity/EntityPlayerSP;closeScreen()V"
)
   )
   public void closeScreen(EntityPlayerSP var1) {
      if (!ModuleManager.isModuleEnabled("PortalChat")) {
         ;
      }
   }

   @Redirect(
      method = {"onLivingUpdate"},
      at = @At(
   value = "INVOKE",
   target = "Lnet/minecraft/client/Minecraft;displayGuiScreen(Lnet/minecraft/client/gui/GuiScreen;)V"
)
   )
   public void closeScreen(Minecraft var1, GuiScreen var2) {
      if (!Xulu.MODULE_MANAGER.getModuleByName("PortalChat").isToggled()) {
         ;
      }
   }

   @Inject(
      method = {"onUpdate"},
      at = {@At("RETURN")},
      cancellable = true
   )
   private void onUpdatePost(CallbackInfo var1) {
      if (Wrapper.getMinecraft().world.isBlockLoaded(new BlockPos(Wrapper.getMinecraft().player.posX, 0.0D, Wrapper.getMinecraft().player.posZ))) {
         EventPreMotionUpdates var2 = new EventPreMotionUpdates(Wrapper.getMinecraft().player.rotationYaw, Wrapper.getMinecraft().player.rotationPitch, Wrapper.getMinecraft().player.posY);
         var2.call();
         if (var2.isCancelled()) {
            var1.cancel();
         }
      }

   }

   @Inject(
      method = {"onUpdateWalkingPlayer"},
      at = {@At("HEAD")},
      cancellable = true
   )
   public void onUpdateWalkingPlayer(CallbackInfo var1) {
      MotionEvent var2 = new MotionEvent();
      var2.call();
      if (var2.isCancelled()) {
         var1.cancel();
      }

   }

   @Inject(
      method = {"onUpdateWalkingPlayer"},
      at = {@At("RETURN")}
   )
   public void onUpdateWalkingPlayerPost(CallbackInfo var1) {
      MotionEventPost var2 = new MotionEventPost();
      var2.call();
   }

   @Inject(
      method = {"move"},
      at = {@At("HEAD")},
      cancellable = true
   )
   public void move(MoverType var1, double var2, double var4, double var6, CallbackInfo var8) {
      PlayerMoveEvent var9 = new PlayerMoveEvent(var1, var2, var4, var6);
      var9.setState(Event.State.PRE);
      var9.call();
      if (var9.isCancelled()) {
         super.move(var1, var9.getX(), var9.getY(), var9.getZ());
         var8.cancel();
      }

   }

   @Inject(
      method = {"move"},
      at = {@At("RETURN")},
      cancellable = true
   )
   public void moveReturn(MoverType var1, double var2, double var4, double var6, CallbackInfo var8) {
      PlayerMoveEvent var9 = new PlayerMoveEvent(var1, var2, var4, var6);
      var9.setState(Event.State.POST);
      var9.call();
   }

   @Inject(
      method = {"swingArm"},
      at = {@At("HEAD")},
      cancellable = true
   )
   public void swingArm(EnumHand var1, CallbackInfo var2) {
      try {
         if (Xulu.MODULE_MANAGER.getModule(OffhandSwing.class).isToggled()) {
            super.swingArm(EnumHand.OFF_HAND);
            Wrapper.getMinecraft().player.connection.sendPacket(new CPacketAnimation(var1));
            var2.cancel();
         }
      } catch (Exception var4) {
         var4.printStackTrace();
      }

   }

   @Redirect(
      method = {"notifyDataManagerChange"},
      at = @At(
   value = "INVOKE",
   target = "Lnet/minecraft/client/audio/SoundHandler;playSound(Lnet/minecraft/client/audio/ISound;)V"
)
   )
   private void playElytraSound(SoundHandler var1, ISound var2) {
      if (!Xulu.MODULE_MANAGER.getModule(AntiSound.class).isToggled() || !(Boolean)((AntiSound)Xulu.MODULE_MANAGER.getModuleT(AntiSound.class)).elytra.getValue()) {
         var1.playSound(var2);
      }

   }
}
