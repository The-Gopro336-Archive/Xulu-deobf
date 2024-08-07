package com.elementars.eclient.guirewrite.elements;

import com.elementars.eclient.guirewrite.Element;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;

public class Exp extends Element {
   public void onEnable() {
      this.width = 16.0D;
      this.height = 16.0D;
      super.onEnable();
   }

   private void itemrender(ItemStack var1) {
      preitemrender();
      mc.getRenderItem().renderItemAndEffectIntoGUI(var1, (int)this.x, (int)this.y);
      mc.getRenderItem().renderItemOverlays(mc.fontRenderer, var1, (int)this.x, (int)this.y);
      postitemrender();
   }

   public void onRender() {
      int var1 = mc.player.inventory.mainInventory.stream().filter((var0) -> {
         return var0.getItem() == Items.EXPERIENCE_BOTTLE;
      }).mapToInt(ItemStack::func_190916_E).sum();
      if (mc.player.getHeldItemOffhand().getItem() == Items.EXPERIENCE_BOTTLE) {
         ++var1;
      }

      ItemStack var2 = new ItemStack(Items.EXPERIENCE_BOTTLE, var1);
      this.itemrender(var2);
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

   public Exp() {
      super("Exp");
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
