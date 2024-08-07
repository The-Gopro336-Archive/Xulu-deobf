package com.elementars.eclient.guirewrite.elements;

import com.elementars.eclient.guirewrite.Element;
import dev.xulu.settings.Value;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.player.EntityPlayer;
import org.lwjgl.input.Mouse;

public class Player extends Element {
   // $FF: synthetic field
   private final Value scale = this.register(new Value("Scale", this, 30, 1, 100));
   // $FF: synthetic field
   private final Value noLook = this.register(new Value("Look Mode", this, "Mouse", new String[]{"Mouse", "Free", "None"}));

   public Player() {
      super("Player");
   }

   public void drawPlayer(EntityPlayer var1, int var2, int var3) {
      EntityPlayer var4 = var1;
      GlStateManager.pushMatrix();
      GlStateManager.color(1.0F, 1.0F, 1.0F);
      RenderHelper.enableStandardItemLighting();
      GlStateManager.enableAlpha();
      GlStateManager.shadeModel(7424);
      GlStateManager.enableAlpha();
      GlStateManager.enableDepth();
      GlStateManager.rotate(0.0F, 0.0F, 5.0F, 0.0F);
      GlStateManager.enableColorMaterial();
      GlStateManager.pushMatrix();
      GlStateManager.translate((float)(var2 + 16), (float)(var3 + 55), 50.0F);
      GlStateManager.scale(-1.0F * (float)(Integer)this.scale.getValue(), 1.0F * (float)(Integer)this.scale.getValue(), 1.0F * (float)(Integer)this.scale.getValue());
      GlStateManager.rotate(180.0F, 0.0F, 0.0F, 1.0F);
      GlStateManager.rotate(135.0F, 0.0F, 1.0F, 0.0F);
      RenderHelper.enableStandardItemLighting();
      GlStateManager.rotate(-135.0F, 0.0F, 1.0F, 0.0F);
      GlStateManager.rotate(-((float)Math.atan((double)((float)var3 / 40.0F))) * 20.0F, 1.0F, 0.0F, 0.0F);
      GlStateManager.translate(0.0F, 0.0F, 0.0F);
      RenderManager var5 = mc.getRenderManager();
      var5.setPlayerViewY(180.0F);
      var5.setRenderShadow(false);

      try {
         var5.renderEntity(var4, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F, false);
      } catch (Exception var7) {
      }

      var5.setRenderShadow(true);
      GlStateManager.popMatrix();
      RenderHelper.disableStandardItemLighting();
      GlStateManager.disableRescaleNormal();
      GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
      GlStateManager.disableTexture2D();
      GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
      GlStateManager.depthFunc(515);
      GlStateManager.resetColor();
      GlStateManager.disableDepth();
      GlStateManager.popMatrix();
   }

   public void onEnable() {
      this.width = 34.0D;
      this.height = 63.0D;
      super.onEnable();
   }

   public void onRender() {
      ScaledResolution var1 = new ScaledResolution(mc);
      if (mc.player != null) {
         if (mc.gameSettings.thirdPersonView == 0) {
            GlStateManager.pushMatrix();
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            if (((String)this.noLook.getValue()).equalsIgnoreCase("Free")) {
               this.drawPlayer(mc.player, (int)this.x, (int)this.y);
            } else {
               GuiInventory.drawEntityOnScreen((int)this.x + 17, (int)this.y + 60, 30, ((String)this.noLook.getValue()).equalsIgnoreCase("None") ? 0.0F : (float)this.x - (float)Mouse.getX(), ((String)this.noLook.getValue()).equalsIgnoreCase("None") ? 0.0F : (float)(-var1.getScaledHeight()) + (float)Mouse.getY(), mc.player);
            }

            GlStateManager.popMatrix();
         }
      }
   }
}
