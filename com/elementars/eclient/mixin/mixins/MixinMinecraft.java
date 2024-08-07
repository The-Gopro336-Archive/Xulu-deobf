package com.elementars.eclient.mixin.mixins;

import com.elementars.eclient.Xulu;
import com.elementars.eclient.event.events.AllowInteractEvent;
import com.elementars.eclient.event.events.EventKey;
import com.elementars.eclient.event.events.EventMiddleClick;
import com.elementars.eclient.module.core.TitleScreenShader;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.crash.CrashReport;
import org.lwjgl.input.Keyboard;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(
   value = {Minecraft.class},
   priority = 9999
)
public class MixinMinecraft {
   @Shadow
   public EntityRenderer entityRenderer;
   @Shadow
   public WorldClient world;
   @Shadow
   public GuiScreen currentScreen;
   @Shadow
   public GameSettings gameSettings;
   @Shadow
   public Framebuffer framebuffer;

   @Redirect(
      method = {"run"},
      at = @At(
   value = "INVOKE",
   target = "Lnet/minecraft/client/Minecraft;displayCrashReport(Lnet/minecraft/crash/CrashReport;)V"
)
   )
   public void displayCrashReport(Minecraft var1, CrashReport var2) {
      Xulu.save();
   }

   @Inject(
      method = {"middleClickMouse"},
      at = {@At("HEAD")}
   )
   private void middleClickMouse(CallbackInfo var1) {
      EventMiddleClick var2 = new EventMiddleClick();
      var2.call();
   }

   @Inject(
      method = {"shutdownMinecraftApplet"},
      at = {@At("HEAD")}
   )
   private void stopClient(CallbackInfo var1) {
      Xulu.INSTANCE.stopClient();
   }

   @Inject(
      method = {"runTickKeyboard"},
      at = {@At(
   value = "INVOKE",
   remap = false,
   target = "Lorg/lwjgl/input/Keyboard;getEventKey()I",
   ordinal = 0,
   shift = At.Shift.BEFORE
)}
   )
   private void onKeyboard(CallbackInfo var1) {
      int var2 = Keyboard.getEventKey() == 0 ? Keyboard.getEventCharacter() + 256 : Keyboard.getEventKey();
      if (Keyboard.getEventKeyState()) {
         EventKey var3 = new EventKey(var2);
         var3.call();
      }

   }

   @Inject(
      method = {"getLimitFramerate"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void getFrameRateXulu(CallbackInfoReturnable var1) {
      try {
         if (Xulu.MODULE_MANAGER.getModule(TitleScreenShader.class).isToggled()) {
            var1.setReturnValue(this.world == null && this.currentScreen != null ? (Integer)TitleScreenShader.fps.getValue() : this.gameSettings.limitFramerate);
         }
      } catch (Exception var3) {
         var3.printStackTrace();
      }

   }

   @Redirect(
      method = {"sendClickBlockToController"},
      at = @At(
   value = "INVOKE",
   target = "Lnet/minecraft/client/entity/EntityPlayerSP;isHandActive()Z"
)
   )
   private boolean isHandActiveWrapper(EntityPlayerSP var1) {
      AllowInteractEvent var2 = new AllowInteractEvent(var1.isHandActive());
      var2.call();
      return var2.usingItem;
   }

   @Redirect(
      method = {"rightClickMouse"},
      at = @At(
   value = "INVOKE",
   target = "Lnet/minecraft/client/multiplayer/PlayerControllerMP;getIsHittingBlock()Z",
   ordinal = 0
)
   )
   private boolean isHittingBlockWrapper(PlayerControllerMP var1) {
      AllowInteractEvent var2 = new AllowInteractEvent(var1.getIsHittingBlock());
      var2.call();
      return var2.usingItem;
   }
}
