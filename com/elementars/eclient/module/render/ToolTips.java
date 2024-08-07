package com.elementars.eclient.module.render;

import com.elementars.eclient.Xulu;
import com.elementars.eclient.module.Category;
import com.elementars.eclient.module.Module;
import com.elementars.eclient.util.XuluTessellator;
import dev.xulu.settings.Value;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraftforge.client.event.RenderTooltipEvent.Pre;
import net.minecraftforge.fml.client.config.GuiUtils;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ToolTips extends Module {
   // $FF: synthetic field
   public static Value Og;
   // $FF: synthetic field
   public static Value cf;
   // $FF: synthetic field
   public static Value g;
   // $FF: synthetic field
   public static Value outline;
   // $FF: synthetic field
   public static Value Ob;
   // $FF: synthetic field
   public static Value cb;
   // $FF: synthetic field
   public static Value b;
   // $FF: synthetic field
   public static Value r;
   // $FF: synthetic field
   public static Value Or;

   public void onDisable() {
      EVENT_BUS.unregister(this);
   }

   public void onEnable() {
      EVENT_BUS.register(this);
   }

   @SubscribeEvent
   public void onToolText(Pre var1) {
      int var2 = var1.getX();
      int var3 = var1.getY();
      int var4 = var1.getScreenWidth();
      int var5 = var1.getScreenHeight();
      int var6 = var1.getMaxWidth();
      Object var7 = var1.getLines();
      FontRenderer var8 = var1.getFontRenderer();
      GlStateManager.disableRescaleNormal();
      RenderHelper.disableStandardItemLighting();
      GlStateManager.disableLighting();
      GlStateManager.disableDepth();
      int var9 = 0;
      Iterator var10 = ((List)var7).iterator();

      int var12;
      while(var10.hasNext()) {
         String var11 = (String)var10.next();
         var12 = var8.getStringWidth(var11);
         if (var12 > var9) {
            var9 = var12;
         }
      }

      boolean var21 = false;
      int var22 = 1;
      var12 = var2 + 12;
      if (var12 + var9 + 4 > var4) {
         var12 = var2 - 16 - var9;
         if (var12 < 4) {
            if (var2 > var4 / 2) {
               var9 = var2 - 12 - 8;
            } else {
               var9 = var4 - 16 - var2;
            }

            var21 = true;
         }
      }

      if (var6 > 0 && var9 > var6) {
         var9 = var6;
         var21 = true;
      }

      int var13;
      int var15;
      String var16;
      if (var21) {
         var13 = 0;
         ArrayList var14 = new ArrayList();

         for(var15 = 0; var15 < ((List)var7).size(); ++var15) {
            var16 = (String)((List)var7).get(var15);
            List var17 = var8.listFormattedStringToWidth(var16, var9);
            if (var15 == 0) {
               var22 = var17.size();
            }

            String var19;
            for(Iterator var18 = var17.iterator(); var18.hasNext(); var14.add(var19)) {
               var19 = (String)var18.next();
               int var20 = var8.getStringWidth(var19);
               if (var20 > var13) {
                  var13 = var20;
               }
            }
         }

         var9 = var13;
         var7 = var14;
         if (var2 > var4 / 2) {
            var12 = var2 - 16 - var13;
         } else {
            var12 = var2 + 12;
         }
      }

      var13 = var3 - 12;
      int var23 = 8;
      if (((List)var7).size() > 1) {
         var23 += (((List)var7).size() - 1) * 10;
         if (((List)var7).size() > var22) {
            var23 += 2;
         }
      }

      if (var13 < 4) {
         var13 = 4;
      } else if (var13 + var23 + 4 > var5) {
         var13 = var5 - var23 - 4;
      }

      if (!(Boolean)cb.getValue()) {
         boolean var24 = true;
         int var25 = -267386864;
         int var26 = 1347420415;
         int var27 = (var26 & 16711422) >> 1 | var26 & -16777216;
         GuiUtils.drawGradientRect(300, var12 - 3, var13 - 4, var12 + var9 + 3, var13 - 3, var25, var25);
         GuiUtils.drawGradientRect(300, var12 - 3, var13 + var23 + 3, var12 + var9 + 3, var13 + var23 + 4, var25, var25);
         GuiUtils.drawGradientRect(300, var12 - 3, var13 - 3, var12 + var9 + 3, var13 + var23 + 3, var25, var25);
         GuiUtils.drawGradientRect(300, var12 - 4, var13 - 3, var12 - 3, var13 + var23 + 3, var25, var25);
         GuiUtils.drawGradientRect(300, var12 + var9 + 3, var13 - 3, var12 + var9 + 4, var13 + var23 + 3, var25, var25);
         GuiUtils.drawGradientRect(300, var12 - 3, var13 - 3 + 1, var12 - 3 + 1, var13 + var23 + 3 - 1, var26, var27);
         GuiUtils.drawGradientRect(300, var12 + var9 + 2, var13 - 3 + 1, var12 + var9 + 3, var13 + var23 + 3 - 1, var26, var27);
         GuiUtils.drawGradientRect(300, var12 - 3, var13 - 3, var12 + var9 + 3, var13 - 3 + 1, var26, var26);
         GuiUtils.drawGradientRect(300, var12 - 3, var13 + var23 + 2, var12 + var9 + 3, var13 + var23 + 3, var27, var27);
      } else {
         Gui.drawRect(var12 - 3, var13 - 4, var12 + var9 + 3, var13 + var23 + 4, (new Color((Integer)r.getValue(), (Integer)g.getValue(), (Integer)b.getValue())).getRGB());
         if ((Boolean)outline.getValue()) {
            XuluTessellator.drawRectOutline((double)(var12 - 3), (double)(var13 - 4), (double)(var12 + var9 + 3), (double)(var13 + var23 + 4), 1.0D, (new Color((Integer)Or.getValue(), (Integer)Og.getValue(), (Integer)Ob.getValue())).getRGB());
         }
      }

      for(var15 = 0; var15 < ((List)var7).size(); ++var15) {
         var16 = (String)((List)var7).get(var15);
         if ((Boolean)cf.getValue()) {
            Xulu.cFontRenderer.drawStringWithShadow(var16, (double)((float)var12), (double)((float)var13), -1);
         } else {
            var8.drawStringWithShadow(var16, (float)var12, (float)var13, -1);
         }

         if (var15 + 1 == var22) {
            var13 += 2;
         }

         var13 += 10;
      }

      GlStateManager.enableLighting();
      GlStateManager.enableDepth();
      RenderHelper.enableStandardItemLighting();
      GlStateManager.enableRescaleNormal();
      var1.setCanceled(true);
   }

   public ToolTips() {
      super("ToolTips", "Custom ToolTips", 0, Category.RENDER, true);
      cf = this.register(new Value("Custom Font", this, true));
      cb = this.register(new Value("Custom Background", this, true));
      outline = this.register(new Value("Outline", this, true));
      r = this.register(new Value("Red", this, 7, 0, 255));
      g = this.register(new Value("Green", this, 12, 0, 255));
      b = this.register(new Value("Blue", this, 17, 0, 255));
      Or = this.register(new Value("Outline Red", this, 10, 0, 255));
      Og = this.register(new Value("Outline Green", this, 12, 0, 255));
      Ob = this.register(new Value("Outline Blue", this, 43, 0, 255));
   }
}
