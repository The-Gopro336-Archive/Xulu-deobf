package com.elementars.eclient.mixin.mixins;

import com.elementars.eclient.Xulu;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemShulkerBox;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({GuiScreen.class})
public class MixinGuiScreen {
   RenderItem itemRender = Minecraft.getMinecraft().getRenderItem();
   FontRenderer fontRenderer;

   public MixinGuiScreen() {
      this.fontRenderer = Minecraft.getMinecraft().fontRenderer;
   }

   @Inject(
      method = {"renderToolTip"},
      at = {@At("HEAD")},
      cancellable = true
   )
   public void renderToolTip(ItemStack var1, int var2, int var3, CallbackInfo var4) {
      if (Xulu.MODULE_MANAGER.getModuleByName("ShulkerPreview").isToggled() && var1.getItem() instanceof ItemShulkerBox) {
         NBTTagCompound var5 = var1.getTagCompound();
         if (var5 != null && var5.hasKey("BlockEntityTag", 10)) {
            NBTTagCompound var6 = var5.getCompoundTag("BlockEntityTag");
            if (var6.hasKey("Items", 9)) {
               var4.cancel();
               NonNullList var7 = NonNullList.withSize(27, ItemStack.EMPTY);
               ItemStackHelper.loadAllItems(var6, var7);
               GlStateManager.enableBlend();
               GlStateManager.disableRescaleNormal();
               RenderHelper.disableStandardItemLighting();
               GlStateManager.disableLighting();
               GlStateManager.disableDepth();
               int var8 = Math.max(144, this.fontRenderer.getStringWidth(var1.getDisplayName()) + 3);
               int var9 = var2 + 12;
               int var10 = var3 - 12;
               byte var11 = 57;
               this.itemRender.zLevel = 300.0F;
               this.drawGradientRectP(var9 - 3, var10 - 4, var9 + var8 + 3, var10 - 3, -267386864, -267386864);
               this.drawGradientRectP(var9 - 3, var10 + var11 + 3, var9 + var8 + 3, var10 + var11 + 4, -267386864, -267386864);
               this.drawGradientRectP(var9 - 3, var10 - 3, var9 + var8 + 3, var10 + var11 + 3, -267386864, -267386864);
               this.drawGradientRectP(var9 - 4, var10 - 3, var9 - 3, var10 + var11 + 3, -267386864, -267386864);
               this.drawGradientRectP(var9 + var8 + 3, var10 - 3, var9 + var8 + 4, var10 + var11 + 3, -267386864, -267386864);
               this.drawGradientRectP(var9 - 3, var10 - 3 + 1, var9 - 3 + 1, var10 + var11 + 3 - 1, 1347420415, 1344798847);
               this.drawGradientRectP(var9 + var8 + 2, var10 - 3 + 1, var9 + var8 + 3, var10 + var11 + 3 - 1, 1347420415, 1344798847);
               this.drawGradientRectP(var9 - 3, var10 - 3, var9 + var8 + 3, var10 - 3 + 1, 1347420415, 1347420415);
               this.drawGradientRectP(var9 - 3, var10 + var11 + 2, var9 + var8 + 3, var10 + var11 + 3, 1344798847, 1344798847);
               this.fontRenderer.drawString(var1.getDisplayName(), var2 + 12, var3 - 12, 16777215);
               GlStateManager.enableBlend();
               GlStateManager.enableAlpha();
               GlStateManager.enableTexture2D();
               GlStateManager.enableLighting();
               GlStateManager.enableDepth();
               RenderHelper.enableGUIStandardItemLighting();

               for(int var12 = 0; var12 < var7.size(); ++var12) {
                  int var13 = var2 + var12 % 9 * 16 + 11;
                  int var14 = var3 + var12 / 9 * 16 - 11 + 8;
                  ItemStack var15 = (ItemStack)var7.get(var12);
                  this.itemRender.renderItemAndEffectIntoGUI(var15, var13, var14);
                  this.itemRender.renderItemOverlayIntoGUI(this.fontRenderer, var15, var13, var14, (String)null);
               }

               RenderHelper.disableStandardItemLighting();
               this.itemRender.zLevel = 0.0F;
               GlStateManager.enableLighting();
               GlStateManager.enableDepth();
               RenderHelper.enableStandardItemLighting();
               GlStateManager.enableRescaleNormal();
            }
         }
      }

   }

   private void drawGradientRectP(int var1, int var2, int var3, int var4, int var5, int var6) {
      float var7 = (float)(var5 >> 24 & 255) / 255.0F;
      float var8 = (float)(var5 >> 16 & 255) / 255.0F;
      float var9 = (float)(var5 >> 8 & 255) / 255.0F;
      float var10 = (float)(var5 & 255) / 255.0F;
      float var11 = (float)(var6 >> 24 & 255) / 255.0F;
      float var12 = (float)(var6 >> 16 & 255) / 255.0F;
      float var13 = (float)(var6 >> 8 & 255) / 255.0F;
      float var14 = (float)(var6 & 255) / 255.0F;
      GlStateManager.disableTexture2D();
      GlStateManager.enableBlend();
      GlStateManager.disableAlpha();
      GlStateManager.tryBlendFuncSeparate(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA, SourceFactor.ONE, DestFactor.ZERO);
      GlStateManager.shadeModel(7425);
      Tessellator var15 = Tessellator.getInstance();
      BufferBuilder var16 = var15.getBuffer();
      var16.begin(7, DefaultVertexFormats.POSITION_COLOR);
      var16.pos((double)var3, (double)var2, 300.0D).color(var8, var9, var10, var7).endVertex();
      var16.pos((double)var1, (double)var2, 300.0D).color(var8, var9, var10, var7).endVertex();
      var16.pos((double)var1, (double)var4, 300.0D).color(var12, var13, var14, var11).endVertex();
      var16.pos((double)var3, (double)var4, 300.0D).color(var12, var13, var14, var11).endVertex();
      var15.draw();
      GlStateManager.shadeModel(7424);
      GlStateManager.disableBlend();
      GlStateManager.enableAlpha();
      GlStateManager.enableTexture2D();
   }
}
