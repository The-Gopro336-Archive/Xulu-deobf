package dev.xulu.newgui.elements.menu;

import com.elementars.eclient.Xulu;
import com.elementars.eclient.module.render.NewGui;
import com.elementars.eclient.util.ColorUtils;
import dev.xulu.newgui.elements.Element;
import dev.xulu.newgui.elements.ModuleButton;
import dev.xulu.newgui.util.ColorUtil;
import dev.xulu.newgui.util.FontUtil;
import dev.xulu.settings.Value;
import java.awt.Color;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.math.MathHelper;

public class ElementSlider extends Element {
   // $FF: synthetic field
   public boolean dragging;

   public boolean mouseClicked(int var1, int var2, int var3) {
      label16: {
         if (var3 == 0) {
            if (((String)NewGui.sliderSetting.getValue()).equalsIgnoreCase("Line")) {
               if (this.isSliderHovered(var1, var2)) {
                  break label16;
               }
            } else if (this.isHovered(var1, var2)) {
               break label16;
            }
         }

         return super.mouseClicked(var1, var2, var3);
      }

      this.dragging = true;
      return true;
   }

   public void mouseReleased(int var1, int var2, int var3) {
      this.dragging = false;
   }

   public ElementSlider(ModuleButton var1, Value var2) {
      this.parent = var1;
      this.set = var2;
      this.dragging = false;
      super.setup();
   }

   public void drawScreen(int var1, int var2, float var3) {
      String var4;
      boolean var5;
      Color var6;
      int var7;
      int var8;
      double var9;
      double var11;
      int var12;
      Double var14;
      short var15;
      long var16;
      float var17;
      double var18;
      Object var19;
      if (((String)NewGui.sliderSetting.getValue()).equalsIgnoreCase("Line")) {
         if (this.set.getValue() instanceof Integer) {
            var4 = String.valueOf((new StringBuilder()).append("").append((double)Math.round((double)(Integer)this.set.getValue() * 100.0D) / 100.0D));
         } else if (this.set.getValue() instanceof Short) {
            var4 = String.valueOf((new StringBuilder()).append("").append((double)Math.round((double)(Short)this.set.getValue() * 100.0D) / 100.0D));
         } else if (this.set.getValue() instanceof Long) {
            var4 = String.valueOf((new StringBuilder()).append("").append((double)Math.round((double)(Long)this.set.getValue() * 100.0D) / 100.0D));
         } else if (this.set.getValue() instanceof Float) {
            var4 = String.valueOf((new StringBuilder()).append("").append((double)Math.round((double)(Float)this.set.getValue() * 100.0D) / 100.0D));
         } else if (this.set.getValue() instanceof Double) {
            var4 = String.valueOf((new StringBuilder()).append("").append((double)Math.round((Double)this.set.getValue() * 100.0D) / 100.0D));
         } else {
            var4 = "";
         }

         var5 = this.isSliderHovered(var1, var2) || this.dragging;
         var6 = ColorUtil.getClickGUIColor();
         if ((Boolean)NewGui.rainbowgui.getValue()) {
            var6 = (new Color(Xulu.rgb)).darker();
         }

         var7 = (new Color(var6.getRed(), var6.getGreen(), var6.getBlue(), var5 ? 225 : 225)).getRGB();
         var8 = (new Color(var6.getRed(), var6.getGreen(), var6.getBlue(), var5 ? 225 : 225)).getRGB();
         if (this.set.getValue() instanceof Integer) {
            var11 = (double)(Integer)this.set.getValue();
            var9 = (var11 - (double)(Integer)this.set.getMin()) / (double)((Integer)this.set.getMax() - (Integer)this.set.getMin());
         } else if (this.set.getValue() instanceof Short) {
            var11 = (double)(Short)this.set.getValue();
            var9 = (var11 - (double)(Short)this.set.getMin()) / (double)((Short)this.set.getMax() - (Short)this.set.getMin());
         } else if (this.set.getValue() instanceof Long) {
            var11 = (double)(Long)this.set.getValue();
            var9 = (var11 - (double)(Long)this.set.getMin()) / (double)((Long)this.set.getMax() - (Long)this.set.getMin());
         } else if (this.set.getValue() instanceof Float) {
            var9 = (double)(((Float)this.set.getValue() - (Float)this.set.getMin()) / ((Float)this.set.getMax() - (Float)this.set.getMin()));
         } else if (this.set.getValue() instanceof Double) {
            var9 = ((Double)this.set.getValue() - (Double)this.set.getMin()) / ((Double)this.set.getMax() - (Double)this.set.getMin());
         } else {
            var9 = 0.0D;
         }

         Gui.drawRect((int)this.x, (int)this.y, (int)(this.x + this.width), (int)(this.y + this.height), ColorUtils.changeAlpha(ColorUtils.Colors.BLACK, 30));
         if ((Boolean)NewGui.customfont.getValue()) {
            Xulu.cFontRenderer.drawString(this.setstrg, (float)(this.x + 2.0D), (float)(this.y + 2.0D), -1);
            Xulu.cFontRenderer.drawString(var4, (float)(this.x + this.width - (double)Xulu.cFontRenderer.getStringWidth(var4)), (float)(this.y + 2.0D), -1);
         } else {
            FontUtil.drawString(this.setstrg, this.x + 2.0D, this.y + 2.0D, -1);
            FontUtil.drawString(var4, this.x + this.width - (double)FontUtil.getStringWidth(var4), this.y + 2.0D, -1);
         }

         Gui.drawRect((int)this.x, (int)(this.y + 12.0D), (int)(this.x + this.width), (int)(this.y + 13.5D), ColorUtils.changeAlpha(-15724528, 30));
         Gui.drawRect((int)this.x, (int)(this.y + 12.0D), (int)(this.x + var9 * this.width), (int)(this.y + 13.5D), var7);
         if (var9 > 0.0D && var9 < 1.0D) {
            Gui.drawRect((int)(this.x + var9 * this.width - 1.0D), (int)(this.y + 12.0D), (int)(this.x + Math.min(var9 * this.width, this.width)), (int)(this.y + 13.5D), var8);
         }

         if (this.dragging) {
            if (this.set.getValue() instanceof Integer) {
               var12 = (Integer)this.set.getMax() - (Integer)this.set.getMin();
               var14 = (double)(Integer)this.set.getMin() + MathHelper.clamp(((double)var1 - this.x) / this.width, 0.0D, 1.0D) * (double)var12;
            } else if (this.set.getValue() instanceof Short) {
               var15 = (short)((Short)this.set.getMax() - (Short)this.set.getMin());
               var14 = (double)(Short)this.set.getMin() + MathHelper.clamp(((double)var1 - this.x) / this.width, 0.0D, 1.0D) * (double)var15;
            } else if (this.set.getValue() instanceof Long) {
               var16 = (Long)this.set.getMax() - (Long)this.set.getMin();
               var14 = (double)(Long)this.set.getMin() + MathHelper.clamp(((double)var1 - this.x) / this.width, 0.0D, 1.0D) * (double)var16;
            } else if (this.set.getValue() instanceof Float) {
               var17 = (Float)this.set.getMax() - (Float)this.set.getMin();
               var14 = (double)(Float)this.set.getMin() + MathHelper.clamp(((double)var1 - this.x) / this.width, 0.0D, 1.0D) * (double)var17;
            } else if (this.set.getValue() instanceof Double) {
               var18 = (Double)this.set.getMax() - (Double)this.set.getMin();
               var14 = (Double)this.set.getMin() + MathHelper.clamp(((double)var1 - this.x) / this.width, 0.0D, 1.0D) * var18;
            } else {
               var14 = 0.0D;
            }

            if (this.set.getValue() instanceof Integer) {
               var19 = var14.intValue();
            } else if (this.set.getValue() instanceof Short) {
               var19 = var14.shortValue();
            } else if (this.set.getValue() instanceof Long) {
               var19 = var14.longValue();
            } else if (this.set.getValue() instanceof Float) {
               var19 = var14.floatValue();
            } else if (this.set.getValue() instanceof Double) {
               var19 = var14;
            } else {
               var19 = 0;
            }

            this.set.setValue(var19);
         }
      } else if (((String)NewGui.sliderSetting.getValue()).equalsIgnoreCase("Box")) {
         if (this.set.getValue() instanceof Integer) {
            var4 = String.valueOf((new StringBuilder()).append("").append((double)Math.round((double)(Integer)this.set.getValue() * 100.0D) / 100.0D));
         } else if (this.set.getValue() instanceof Short) {
            var4 = String.valueOf((new StringBuilder()).append("").append((double)Math.round((double)(Short)this.set.getValue() * 100.0D) / 100.0D));
         } else if (this.set.getValue() instanceof Long) {
            var4 = String.valueOf((new StringBuilder()).append("").append((double)Math.round((double)(Long)this.set.getValue() * 100.0D) / 100.0D));
         } else if (this.set.getValue() instanceof Float) {
            var4 = String.valueOf((new StringBuilder()).append("").append((double)Math.round((double)(Float)this.set.getValue() * 100.0D) / 100.0D));
         } else if (this.set.getValue() instanceof Double) {
            var4 = String.valueOf((new StringBuilder()).append("").append((double)Math.round((Double)this.set.getValue() * 100.0D) / 100.0D));
         } else {
            var4 = "";
         }

         boolean var10000;
         label216: {
            label215: {
               if (((String)NewGui.sliderSetting.getValue()).equalsIgnoreCase("Line")) {
                  if (this.isSliderHovered(var1, var2)) {
                     break label215;
                  }
               } else if (this.isHovered(var1, var2)) {
                  break label215;
               }

               if (!this.dragging) {
                  var10000 = false;
                  break label216;
               }
            }

            var10000 = true;
         }

         var5 = var10000;
         var6 = ColorUtil.getClickGUIColor().darker();
         if ((Boolean)NewGui.rainbowgui.getValue()) {
            var6 = (new Color(Xulu.rgb)).darker();
         }

         var7 = (new Color(var6.getRed(), var6.getGreen(), var6.getBlue(), var5 ? 50 : 30)).getRGB();
         var8 = (new Color(var6.getRed(), var6.getGreen(), var6.getBlue(), var5 ? 255 : 230)).getRGB();
         if (this.set.getValue() instanceof Integer) {
            var11 = (double)(Integer)this.set.getValue();
            var9 = (var11 - (double)(Integer)this.set.getMin()) / (double)((Integer)this.set.getMax() - (Integer)this.set.getMin());
         } else if (this.set.getValue() instanceof Short) {
            var11 = (double)(Short)this.set.getValue();
            var9 = (var11 - (double)(Short)this.set.getMin()) / (double)((Short)this.set.getMax() - (Short)this.set.getMin());
         } else if (this.set.getValue() instanceof Long) {
            var11 = (double)(Long)this.set.getValue();
            var9 = (var11 - (double)(Long)this.set.getMin()) / (double)((Long)this.set.getMax() - (Long)this.set.getMin());
         } else if (this.set.getValue() instanceof Float) {
            var9 = (double)(((Float)this.set.getValue() - (Float)this.set.getMin()) / ((Float)this.set.getMax() - (Float)this.set.getMin()));
         } else if (this.set.getValue() instanceof Double) {
            var9 = ((Double)this.set.getValue() - (Double)this.set.getMin()) / ((Double)this.set.getMax() - (Double)this.set.getMin());
         } else {
            var9 = 0.0D;
         }

         Gui.drawRect((int)(this.x + var9 * this.width), (int)this.y, (int)(this.x + this.width), (int)(this.y + this.height), ColorUtils.changeAlpha(ColorUtils.Colors.BLACK, 60));
         Gui.drawRect((int)this.x, (int)this.y, (int)(this.x + var9 * this.width), (int)(this.y + this.height), ColorUtils.changeAlpha(var7, 225));
         if ((Boolean)NewGui.customfont.getValue()) {
            Xulu.cFontRenderer.drawStringWithShadow(this.setstrg, (double)((float)(this.x + 2.0D)), (double)((float)(this.y + 2.0D)), -1);
            Xulu.cFontRenderer.drawStringWithShadow(var4, (double)((float)(this.x + this.width - (double)Xulu.cFontRenderer.getStringWidth(var4))), (double)((float)(this.y + 2.0D)), -1);
         } else {
            FontUtil.drawStringWithShadow(this.setstrg, this.x + 2.0D, this.y + 2.0D, -1);
            FontUtil.drawStringWithShadow(var4, this.x + this.width - (double)FontUtil.getStringWidth(var4), this.y + 2.0D, -1);
         }

         if (this.dragging) {
            if (this.set.getValue() instanceof Integer) {
               var12 = (Integer)this.set.getMax() - (Integer)this.set.getMin();
               var14 = (double)(Integer)this.set.getMin() + MathHelper.clamp(((double)var1 - this.x) / this.width, 0.0D, 1.0D) * (double)var12;
            } else if (this.set.getValue() instanceof Short) {
               var15 = (short)((Short)this.set.getMax() - (Short)this.set.getMin());
               var14 = (double)(Short)this.set.getMin() + MathHelper.clamp(((double)var1 - this.x) / this.width, 0.0D, 1.0D) * (double)var15;
            } else if (this.set.getValue() instanceof Long) {
               var16 = (Long)this.set.getMax() - (Long)this.set.getMin();
               var14 = (double)(Long)this.set.getMin() + MathHelper.clamp(((double)var1 - this.x) / this.width, 0.0D, 1.0D) * (double)var16;
            } else if (this.set.getValue() instanceof Float) {
               var17 = (Float)this.set.getMax() - (Float)this.set.getMin();
               var14 = (double)(Float)this.set.getMin() + MathHelper.clamp(((double)var1 - this.x) / this.width, 0.0D, 1.0D) * (double)var17;
            } else if (this.set.getValue() instanceof Double) {
               var18 = (Double)this.set.getMax() - (Double)this.set.getMin();
               var14 = (Double)this.set.getMin() + MathHelper.clamp(((double)var1 - this.x) / this.width, 0.0D, 1.0D) * var18;
            } else {
               var14 = 0.0D;
            }

            if (this.set.getValue() instanceof Integer) {
               var19 = var14.intValue();
            } else if (this.set.getValue() instanceof Short) {
               var19 = var14.shortValue();
            } else if (this.set.getValue() instanceof Long) {
               var19 = var14.longValue();
            } else if (this.set.getValue() instanceof Float) {
               var19 = var14.floatValue();
            } else if (this.set.getValue() instanceof Double) {
               var19 = var14;
            } else {
               var19 = 0;
            }

            this.set.setValue(var19);
         }
      }

   }

   public boolean isSliderHovered(int var1, int var2) {
      return (double)var1 >= this.x && (double)var1 <= this.x + this.width && (double)var2 >= this.y + 11.0D && (double)var2 <= this.y + 14.0D;
   }
}
