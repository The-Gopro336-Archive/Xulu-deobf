package com.elementars.eclient.guirewrite.elements;

import com.elementars.eclient.guirewrite.Element;
import com.elementars.eclient.util.ColorUtils;
import dev.xulu.settings.Value;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class InvPreview extends Element {
   // $FF: synthetic field
   private Value background = this.register(new Value("Background", this, "Texture", new String[]{"Texture", "Transparent", "None"}));
   // $FF: synthetic field
   private static final ResourceLocation box = new ResourceLocation("textures/gui/container/generic_54.png");

   private static void postboxrender() {
      GlStateManager.disableBlend();
      GlStateManager.disableDepth();
      GlStateManager.disableLighting();
      GlStateManager.enableDepth();
      GlStateManager.enableAlpha();
      GlStateManager.popMatrix();
      GL11.glPopMatrix();
   }

   private static void postitemrender() {
      GlStateManager.scale(1.0F, 1.0F, 1.0F);
      RenderHelper.disableStandardItemLighting();
      GlStateManager.enableAlpha();
      GlStateManager.disableBlend();
      GlStateManager.disableLighting();
      GlStateManager.scale(0.5D, 0.5D, 0.5D);
      GlStateManager.disableDepth();
      GlStateManager.enableDepth();
      GlStateManager.scale(2.0F, 2.0F, 2.0F);
      GL11.glPopMatrix();
   }

   private void itemrender(NonNullList var1, int var2, int var3) {
      int var4 = var1.size();

      for(int var5 = 9; var5 < var4; ++var5) {
         int var6 = var2 + 1 + var5 % 9 * 18;
         int var7 = var3 + 1 + (var5 / 9 - 1) * 18;
         preitemrender();
         mc.getRenderItem().renderItemAndEffectIntoGUI((ItemStack)var1.get(var5), var6, var7);
         mc.getRenderItem().renderItemOverlays(mc.fontRenderer, (ItemStack)var1.get(var5), var6, var7);
         postitemrender();
      }

   }

   public void onRender() {
      NonNullList var1 = mc.player.inventory.mainInventory;
      if (((String)this.background.getValue()).equalsIgnoreCase("Texture")) {
         this.boxrender((int)this.x, (int)this.y);
      } else if (((String)this.background.getValue()).equalsIgnoreCase("Transparent")) {
         Gui.drawRect((int)this.x, (int)this.y, (int)this.x + (int)this.width, (int)this.y + (int)this.height, ColorUtils.changeAlpha(-15592942, 100));
      }

      this.itemrender(var1, (int)this.x, (int)this.y);
   }

   public void onEnable() {
      this.width = 162.0D;
      this.height = 54.0D;
      super.onEnable();
   }

   public InvPreview() {
      super("InvPreview");
   }

   private void boxrender(int var1, int var2) {
      preboxrender();
      mc.renderEngine.bindTexture(box);
      mc.ingameGUI.drawTexturedModalRect(var1, var2, 7, 17, 162, 54);
      postboxrender();
   }

   private static void preboxrender() {
      GL11.glPushMatrix();
      GlStateManager.pushMatrix();
      GlStateManager.disableAlpha();
      GlStateManager.clear(256);
      GlStateManager.enableBlend();
   }

   private static void preitemrender() {
      GL11.glPushMatrix();
      GL11.glDepthMask(true);
      GlStateManager.clear(256);
      GlStateManager.disableDepth();
      GlStateManager.enableDepth();
      RenderHelper.enableStandardItemLighting();
      GlStateManager.scale(1.0F, 1.0F, 0.01F);
   }
}
