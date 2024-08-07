package com.elementars.eclient.mixin.mixins;

import com.elementars.eclient.Xulu;
import com.elementars.eclient.module.ModuleManager;
import com.elementars.eclient.module.render.Chat;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiNewChat;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin({GuiNewChat.class})
public abstract class MixinGuiNewChat {
   @Redirect(
      method = {"drawChat"},
      at = @At(
   value = "INVOKE",
   target = "Lnet/minecraft/client/gui/GuiNewChat;drawRect(IIIII)V"
)
   )
   private void drawRectBackgroundClean(int var1, int var2, int var3, int var4, int var5) {
      if (!ModuleManager.isModuleEnabled("Chat") || !(Boolean)Xulu.VALUE_MANAGER.getValueByMod(Xulu.MODULE_MANAGER.getModuleT(Chat.class), "No Chat Background").getValue()) {
         Gui.drawRect(var1, var2, var3, var4, var5);
      }

   }

   @Redirect(
      method = {"drawChat"},
      at = @At(
   value = "INVOKE",
   target = "Lnet/minecraft/client/gui/FontRenderer;drawStringWithShadow(Ljava/lang/String;FFI)I"
)
   )
   private int drawStringWithShadowMaybe(FontRenderer var1, String var2, float var3, float var4, int var5) {
      if (!Chat.INSTANCE.isToggled()) {
         return var1.drawStringWithShadow(var2, var3, var4, var5);
      } else if ((Boolean)Chat.INSTANCE.customFont.getValue()) {
         return (Boolean)Chat.nochatshadow.getValue() ? (int)Xulu.cFontRenderer.drawString(var2, var3, var4, var5) : (int)Xulu.cFontRenderer.drawStringWithShadow(var2, (double)var3, (double)var4, var5);
      } else {
         return (Boolean)Chat.nochatshadow.getValue() ? var1.drawString(var2, (int)var3, (int)var4, var5) : var1.drawStringWithShadow(var2, var3, var4, var5);
      }
   }
}
