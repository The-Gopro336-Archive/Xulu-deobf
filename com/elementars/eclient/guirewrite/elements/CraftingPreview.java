package com.elementars.eclient.guirewrite.elements;

import com.elementars.eclient.guirewrite.Element;
import com.elementars.eclient.util.ColorUtils;
import dev.xulu.settings.Value;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class CraftingPreview extends Element {
   // $FF: synthetic field
   private static final ResourceLocation box = new ResourceLocation("textures/gui/container/generic_54.png");
   // $FF: synthetic field
   private Value background = this.register(new Value("Background", this, "Texture", new String[]{"Texture", "Transparent", "None"}));

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

   private static void preitemrender() {
      GL11.glPushMatrix();
      GL11.glDepthMask(true);
      GlStateManager.clear(256);
      GlStateManager.disableDepth();
      GlStateManager.enableDepth();
      RenderHelper.enableStandardItemLighting();
      GlStateManager.scale(1.0F, 1.0F, 0.01F);
   }

   private void itemrender(ItemStack var1, int var2, int var3) {
      preitemrender();
      InvPreview.mc.getRenderItem().renderItemAndEffectIntoGUI(var1, var2, var3);
      InvPreview.mc.getRenderItem().renderItemOverlays(InvPreview.mc.fontRenderer, var1, var2, var3);
      postitemrender();
   }

   public void onEnable() {
      this.width = 36.0D;
      this.height = 36.0D;
      super.onEnable();
   }

   private static void postboxrender() {
      GlStateManager.disableBlend();
      GlStateManager.disableDepth();
      GlStateManager.disableLighting();
      GlStateManager.enableDepth();
      GlStateManager.enableAlpha();
      GlStateManager.popMatrix();
      GL11.glPopMatrix();
   }

   private void boxrender(int var1, int var2) {
      preboxrender();
      InvPreview.mc.renderEngine.bindTexture(box);
      InvPreview.mc.ingameGUI.drawTexturedModalRect(var1, var2, 7, 17, 36, 36);
      postboxrender();
   }

   public void onRender() {
      if (((String)this.background.getValue()).equalsIgnoreCase("Texture")) {
         this.boxrender((int)this.x, (int)this.y);
      } else if (((String)this.background.getValue()).equalsIgnoreCase("Transparent")) {
         Gui.drawRect((int)this.x, (int)this.y, (int)this.x + (int)this.width, (int)this.y + (int)this.height, ColorUtils.changeAlpha(-15592942, 100));
      }

      for(int var1 = 1; var1 < 5; ++var1) {
         ItemStack var2 = (ItemStack)mc.player.inventoryContainer.getInventory().get(var1);
         int var3 = (int)this.x + 1 + (var1 - 1) * 18 - (var1 > 2 ? 36 : 0);
         int var4 = (int)this.y + 1 + (var1 > 2 ? 18 : 0);
         this.itemrender(var2, var3, var4);
      }

   }

   public CraftingPreview() {
      super("CraftingPreview");
   }

   private static void preboxrender() {
      GL11.glPushMatrix();
      GlStateManager.pushMatrix();
      GlStateManager.disableAlpha();
      GlStateManager.clear(256);
      GlStateManager.enableBlend();
   }
}
