package com.elementars.eclient.mixin.mixins;

import com.elementars.eclient.Xulu;
import com.elementars.eclient.module.core.TitleScreenShader;
import com.elementars.eclient.util.GLSLSandboxShader;
import com.elementars.eclient.util.GLSLShaders;
import java.io.IOException;
import java.util.Random;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.multiplayer.GuiConnecting;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({GuiMainMenu.class})
public abstract class MixinGuiMainMenu extends GuiScreen {
   @Shadow
   protected abstract void renderSkybox(int var1, int var2, float var3);

   @Inject(
      method = {"initGui"},
      at = {@At("RETURN")},
      cancellable = true
   )
   public void initGui(CallbackInfo var1) {
      try {
         if (((String)((TitleScreenShader)Xulu.MODULE_MANAGER.getModuleT(TitleScreenShader.class)).mode.getValue()).equalsIgnoreCase("Random")) {
            Random var2 = new Random();
            GLSLShaders[] var3 = GLSLShaders.values();
            Xulu.backgroundShader = new GLSLSandboxShader(var3[var2.nextInt(var3.length)].get());
         } else {
            Xulu.backgroundShader = new GLSLSandboxShader(((GLSLShaders)((TitleScreenShader)Xulu.MODULE_MANAGER.getModuleT(TitleScreenShader.class)).shader.getValue()).get());
         }
      } catch (IOException var4) {
         throw new IllegalStateException("Failed to load background shader", var4);
      }

      this.buttonList.add(new GuiButton(932, 5, 55, this.fontRenderer.getStringWidth("2b2tpvp.net") + 10, 20, "2b2tpvp.net"));
      this.buttonList.add(new GuiButton(284, 5, 75, this.fontRenderer.getStringWidth("2b2t.org") + 10, 20, "2b2t.org"));
      Xulu.initTime = System.currentTimeMillis();
   }

   @Inject(
      method = {"actionPerformed"},
      at = {@At("HEAD")},
      cancellable = true
   )
   public void actionPerformed(GuiButton var1, CallbackInfo var2) {
      if (var1.id == 932) {
         this.mc.displayGuiScreen(new GuiConnecting(this, this.mc, "2b2tpvp.net", 25565));
      }

      if (var1.id == 284) {
         this.mc.displayGuiScreen(new GuiConnecting(this, this.mc, "2b2t.org", 25565));
      }

   }

   @Redirect(
      method = {"drawScreen"},
      at = @At(
   value = "INVOKE",
   target = "Lnet/minecraft/client/gui/GuiMainMenu;renderSkybox(IIF)V"
)
   )
   private void voided(GuiMainMenu var1, int var2, int var3, float var4) {
      if (!Xulu.MODULE_MANAGER.getModule(TitleScreenShader.class).isToggled()) {
         this.renderSkybox(var2, var3, var4);
      }

   }

   @Redirect(
      method = {"drawScreen"},
      at = @At(
   value = "INVOKE",
   target = "Lnet/minecraft/client/gui/GuiMainMenu;drawGradientRect(IIIIII)V",
   ordinal = 0
)
   )
   private void noRect1(GuiMainMenu var1, int var2, int var3, int var4, int var5, int var6, int var7) {
      if (!Xulu.MODULE_MANAGER.getModule(TitleScreenShader.class).isToggled()) {
         this.drawGradientRect(var2, var3, var4, var5, var6, var7);
      }

   }

   @Redirect(
      method = {"drawScreen"},
      at = @At(
   value = "INVOKE",
   target = "Lnet/minecraft/client/gui/GuiMainMenu;drawGradientRect(IIIIII)V",
   ordinal = 1
)
   )
   private void noRect2(GuiMainMenu var1, int var2, int var3, int var4, int var5, int var6, int var7) {
      if (!Xulu.MODULE_MANAGER.getModule(TitleScreenShader.class).isToggled()) {
         this.drawGradientRect(var2, var3, var4, var5, var6, var7);
      }

   }

   @Inject(
      method = {"drawScreen"},
      at = {@At("HEAD")},
      cancellable = true
   )
   public void drawScreenShader(int var1, int var2, float var3, CallbackInfo var4) {
      if (Xulu.MODULE_MANAGER.getModule(TitleScreenShader.class).isToggled()) {
         GlStateManager.disableCull();
         Xulu.backgroundShader.useShader(this.width * 2, this.height * 2, (float)(var1 * 2), (float)(var2 * 2), (float)(System.currentTimeMillis() - Xulu.initTime) / 1000.0F);
         GL11.glBegin(7);
         GL11.glVertex2f(-1.0F, -1.0F);
         GL11.glVertex2f(-1.0F, 1.0F);
         GL11.glVertex2f(1.0F, 1.0F);
         GL11.glVertex2f(1.0F, -1.0F);
         GL11.glEnd();
         GL20.glUseProgram(0);
      }

   }
}
