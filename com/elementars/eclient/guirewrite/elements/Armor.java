package com.elementars.eclient.guirewrite.elements;

import com.elementars.eclient.Xulu;
import com.elementars.eclient.guirewrite.Element;
import com.elementars.eclient.util.ColorUtils;
import com.elementars.eclient.util.ColourHolder;
import dev.xulu.settings.Value;
import java.util.Iterator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.item.ItemStack;

public class Armor extends Element {
   // $FF: synthetic field
   private final Value h = this.register(new Value("Height", this, 0, 0, 100));
   // $FF: synthetic field
   private final Value w = this.register(new Value("Width", this, 0, 0, 100));
   // $FF: synthetic field
   private final Value progress = this.register(new Value("Durab. Bar", this, false));
   // $FF: synthetic field
   private final Value cf = this.register(new Value("Custom Font", this, false));
   // $FF: synthetic field
   private static RenderItem itemRender = Minecraft.getMinecraft().getRenderItem();
   // $FF: synthetic field
   private final Value aligned = this.register(new Value("Aligned", this, "X-axis", new String[]{"X-axis", "Y-axis"})).onChanged((var1) -> {
      String var2 = (String)var1.getNew();
      byte var3 = -1;
      switch(var2.hashCode()) {
      case -1731035146:
         if (var2.equals("X-axis")) {
            var3 = 0;
         }
         break;
      case -1702405995:
         if (var2.equals("Y-axis")) {
            var3 = 1;
         }
      }

      switch(var3) {
      case 0:
         this.height = 16.0D;
         this.width = 79.0D;
         break;
      case 1:
         this.height = 72.0D;
         this.width = 16.0D;
      }

   });
   // $FF: synthetic field
   private final Value fixed = this.register(new Value("Fixed", this, false));
   // $FF: synthetic field
   private final Value damage = this.register(new Value("Damage", this, false));

   public void onRender() {
      GlStateManager.enableTexture2D();
      ScaledResolution var1 = new ScaledResolution(mc);
      int var2 = var1.getScaledWidth() / 2;
      int var3 = 0;
      int var4 = var1.getScaledHeight() - 55 - (mc.player.isInWater() ? 10 : 0);
      Iterator var5 = mc.player.inventory.armorInventory.iterator();

      while(true) {
         ItemStack var6;
         int var7;
         int var8;
         String var9;
         float var18;
         label114:
         while(true) {
            if (!var5.hasNext()) {
               GlStateManager.enableDepth();
               GlStateManager.disableLighting();
               return;
            }

            var6 = (ItemStack)var5.next();
            ++var3;
            var7 = 0;
            var8 = 0;
            var9 = (String)this.aligned.getValue();
            byte var10 = -1;
            switch(var9.hashCode()) {
            case -1731035146:
               if (var9.equals("X-axis")) {
                  var10 = 0;
               }
               break;
            case -1702405995:
               if (var9.equals("Y-axis")) {
                  var10 = 1;
               }
            }

            switch(var10) {
            case 0:
               var7 = (int)this.x - 90 + (9 - var3) * 20 + 2 - 12;
               var8 = (int)this.y;
               if ((Boolean)this.fixed.getValue()) {
                  var7 = var2 - 90 + (9 - var3) * 20 + 2;
                  var8 = var4;
               }
               break;
            case 1:
               var7 = (int)this.x;
               var8 = (int)this.y - (var3 - 1) * 18 + 54;
               if ((Boolean)this.fixed.getValue()) {
                  var7 = var2;
                  var8 = var4 - (var3 - 1) * 18 + 54;
               }
            }

            if ((Boolean)this.progress.getValue()) {
               var9 = (String)this.aligned.getValue();
               var10 = -1;
               switch(var9.hashCode()) {
               case -1731035146:
                  if (var9.equals("X-axis")) {
                     var10 = 0;
                  }
                  break;
               case -1702405995:
                  if (var9.equals("Y-axis")) {
                     var10 = 1;
                  }
               }

               switch(var10) {
               case 0:
                  Gui.drawRect(var7 - 1, var8 + 16, var7 + 19, var8 - 51 + 16, ColorUtils.changeAlpha(ColorUtils.Colors.BLACK, 60));
                  break;
               case 1:
                  Gui.drawRect(var7, var8, var7 + 69, var8 + 18, ColorUtils.changeAlpha(ColorUtils.Colors.BLACK, 60));
               }

               if (!var6.isEmpty()) {
                  float var16 = ((float)var6.getMaxDamage() - (float)var6.getItemDamage()) / (float)var6.getMaxDamage();
                  var18 = 1.0F - var16;
                  String var11 = (String)this.aligned.getValue();
                  byte var12 = -1;
                  switch(var11.hashCode()) {
                  case -1731035146:
                     if (var11.equals("X-axis")) {
                        var12 = 0;
                     }
                     break;
                  case -1702405995:
                     if (var11.equals("Y-axis")) {
                        var12 = 1;
                     }
                  }

                  switch(var12) {
                  case 0:
                     Gui.drawRect(var7 - 1, var8 + 16, var7 + 19, (int)((float)var8 - var16 * 51.0F) + 16, ColorUtils.changeAlpha(ColourHolder.toHex((int)(var18 * 255.0F), (int)(var16 * 255.0F), 0), 150));
                     break label114;
                  case 1:
                     Gui.drawRect(var7, var8, (int)((float)var7 + var16 * 69.0F), var8 + 18, ColorUtils.changeAlpha(ColourHolder.toHex((int)(var18 * 255.0F), (int)(var16 * 255.0F), 0), 150));
                  default:
                     break label114;
                  }
               }
            } else {
               if (var6.isEmpty()) {
                  continue;
               }
               break;
            }
         }

         GlStateManager.enableDepth();
         itemRender.zLevel = 200.0F;
         itemRender.renderItemAndEffectIntoGUI(var6, var7, var8);
         itemRender.renderItemOverlayIntoGUI(mc.fontRenderer, var6, var7, var8, "");
         itemRender.zLevel = 0.0F;
         GlStateManager.enableTexture2D();
         GlStateManager.disableLighting();
         GlStateManager.disableDepth();
         var9 = var6.getCount() > 1 ? String.valueOf((new StringBuilder()).append(var6.getCount()).append("")) : "";
         mc.fontRenderer.drawStringWithShadow(var9, (float)(var7 + 19 - 2 - mc.fontRenderer.getStringWidth(var9)), (float)((int)this.y + 9), 16777215);
         if ((Boolean)this.damage.getValue()) {
            var18 = ((float)var6.getMaxDamage() - (float)var6.getItemDamage()) / (float)var6.getMaxDamage();
            float var17 = 1.0F - var18;
            int var19 = 100 - (int)(var17 * 100.0F);
            String var13 = (String)this.aligned.getValue();
            byte var14 = -1;
            switch(var13.hashCode()) {
            case -1731035146:
               if (var13.equals("X-axis")) {
                  var14 = 0;
               }
               break;
            case -1702405995:
               if (var13.equals("Y-axis")) {
                  var14 = 1;
               }
            }

            switch(var14) {
            case 0:
               if ((Boolean)this.cf.getValue()) {
                  Xulu.cFontRenderer.drawStringWithShadow(String.valueOf((new StringBuilder()).append(var19).append("")), (double)(var7 + 8 - Xulu.cFontRenderer.getStringWidth(String.valueOf((new StringBuilder()).append(var19).append(""))) / 2), (double)(var8 - 11), ColourHolder.toHex((int)(var17 * 255.0F), (int)(var18 * 255.0F), 0));
               } else {
                  fontRenderer.drawStringWithShadow(String.valueOf((new StringBuilder()).append(var19).append("")), (float)(var7 + 9 - fontRenderer.getStringWidth(String.valueOf((new StringBuilder()).append(var19).append(""))) / 2), (float)(var8 - 11), ColourHolder.toHex((int)(var17 * 255.0F), (int)(var18 * 255.0F), 0));
               }
               break;
            case 1:
               if ((Boolean)this.cf.getValue()) {
                  Xulu.cFontRenderer.drawStringWithShadow(String.valueOf((new StringBuilder()).append(var19).append("")), (double)(var7 + 18), (double)(var8 + 5), ColourHolder.toHex((int)(var17 * 255.0F), (int)(var18 * 255.0F), 0));
               } else {
                  fontRenderer.drawStringWithShadow(String.valueOf((new StringBuilder()).append(var19).append("")), (float)(var7 + 18), (float)(var8 + 5), ColourHolder.toHex((int)(var17 * 255.0F), (int)(var18 * 255.0F), 0));
               }
            }
         }
      }
   }

   public void onEnable() {
      String var1 = (String)this.aligned.getValue();
      byte var2 = -1;
      switch(var1.hashCode()) {
      case -1731035146:
         if (var1.equals("X-axis")) {
            var2 = 0;
         }
         break;
      case -1702405995:
         if (var1.equals("Y-axis")) {
            var2 = 1;
         }
      }

      switch(var2) {
      case 0:
         this.height = 16.0D;
         this.width = 79.0D;
         break;
      case 1:
         this.height = 72.0D;
         this.width = 16.0D;
      }

   }

   public Armor() {
      super("Armor");
   }
}
