package com.elementars.eclient.mixin.mixins;

import com.elementars.eclient.command.Command;
import com.elementars.eclient.util.Wrapper;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiTextField;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({GuiChat.class})
public abstract class MixinGuiChat {
   @Shadow
   protected GuiTextField inputField;
   @Shadow
   public String historyBuffer;
   @Shadow
   public int sentHistoryCursor;

   @Shadow
   public abstract void initGui();

   @Inject(
      method = {"Lnet/minecraft/client/gui/GuiChat;keyTyped(CI)V"},
      at = {@At("RETURN")}
   )
   public void returnKeyTyped(char var1, int var2, CallbackInfo var3) {
      if (Wrapper.getMinecraft().currentScreen instanceof GuiChat) {
         if (this.inputField.getText().startsWith(Command.getPrefix())) {
         }

      }
   }
}
