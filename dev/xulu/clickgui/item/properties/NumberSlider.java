package dev.xulu.clickgui.item.properties;

import com.elementars.eclient.Xulu;
import com.elementars.eclient.module.render.ExeterGui;
import com.elementars.eclient.util.ColorUtils;
import com.elementars.eclient.util.Helper;
import com.elementars.eclient.util.XuluTessellator;
import dev.xulu.clickgui.ClickGui;
import dev.xulu.clickgui.Panel;
import dev.xulu.clickgui.item.Item;
import dev.xulu.newgui.util.ColorUtil;
import dev.xulu.settings.Value;
import java.util.Iterator;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.math.MathHelper;

public class NumberSlider extends Item implements Helper {
   // $FF: synthetic field
   private boolean dragging;

   public void mouseClicked(int var1, int var2, int var3) {
      if (this.isHovering(var1, var2) && var3 == 0) {
         if (ExeterGui.getSound()) {
            mc.getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 1.0F));
         }

         this.dragging = true;
      } else {
         super.mouseClicked(var1, var2, var3);
      }
   }

   public void drawScreen(int var1, int var2, float var3) {
      String var4;
      if (this.property.getValue() instanceof Integer) {
         var4 = String.valueOf((new StringBuilder()).append("").append((double)Math.round((double)(Integer)this.property.getValue() * 100.0D) / 100.0D));
      } else if (this.property.getValue() instanceof Short) {
         var4 = String.valueOf((new StringBuilder()).append("").append((double)Math.round((double)(Short)this.property.getValue() * 100.0D) / 100.0D));
      } else if (this.property.getValue() instanceof Long) {
         var4 = String.valueOf((new StringBuilder()).append("").append((double)Math.round((double)(Long)this.property.getValue() * 100.0D) / 100.0D));
      } else if (this.property.getValue() instanceof Float) {
         var4 = String.valueOf((new StringBuilder()).append("").append((double)Math.round((double)(Float)this.property.getValue() * 100.0D) / 100.0D));
      } else if (this.property.getValue() instanceof Double) {
         var4 = String.valueOf((new StringBuilder()).append("").append((double)Math.round((Double)this.property.getValue() * 100.0D) / 100.0D));
      } else {
         var4 = "";
      }

      double var5;
      double var7;
      if (this.property.getValue() instanceof Integer) {
         var7 = (double)(Integer)this.property.getValue();
         var5 = (var7 - (double)(Integer)this.property.getMin()) / (double)((Integer)this.property.getMax() - (Integer)this.property.getMin());
      } else if (this.property.getValue() instanceof Short) {
         var7 = (double)(Short)this.property.getValue();
         var5 = (var7 - (double)(Short)this.property.getMin()) / (double)((Short)this.property.getMax() - (Short)this.property.getMin());
      } else if (this.property.getValue() instanceof Long) {
         var7 = (double)(Long)this.property.getValue();
         var5 = (var7 - (double)(Long)this.property.getMin()) / (double)((Long)this.property.getMax() - (Long)this.property.getMin());
      } else if (this.property.getValue() instanceof Float) {
         var5 = (double)(((Float)this.property.getValue() - (Float)this.property.getMin()) / ((Float)this.property.getMax() - (Float)this.property.getMin()));
      } else if (this.property.getValue() instanceof Double) {
         var5 = ((Double)this.property.getValue() - (Double)this.property.getMin()) / ((Double)this.property.getMax() - (Double)this.property.getMin());
      } else {
         var5 = 0.0D;
      }

      XuluTessellator.drawRectGradient((double)this.x, (double)this.y, (double)this.x + var5 * (double)((float)this.width + 7.4F), (double)(this.y + (float)this.height), ColorUtils.changeAlpha(ColorUtil.getClickGUIColor().getRGB(), 200), -1);
      if (this.isHovering(var1, var2)) {
         XuluTessellator.drawRectGradient((double)this.x, (double)this.y, (double)(this.x + (float)this.width), (double)(this.y + (float)this.height), ColorUtils.changeAlpha(ColorUtils.Colors.BLACK, 25), -1);
      }

      if (ExeterGui.getCF()) {
         Xulu.cFontRenderer.drawStringWithShadow(String.format("%s§7 %s", this.getLabel(), var4), (double)(this.x + 2.3F), (double)(this.y + 3.0F), -1);
      } else {
         fontRenderer.drawStringWithShadow(String.format("%s§7 %s", this.getLabel(), var4), this.x + 2.3F, this.y + 4.0F, -1);
      }

      if (this.dragging) {
         Object var10;
         if (this.property.getValue() instanceof Integer) {
            int var8 = (Integer)this.property.getMax() - (Integer)this.property.getMin();
            var10 = (float)(Integer)this.property.getMin() + MathHelper.clamp(((float)var1 - this.x) / ((float)this.width + 7.4F), 0.0F, 1.0F) * (float)var8;
         } else if (this.property.getValue() instanceof Short) {
            short var11 = (short)((Short)this.property.getMax() - (Short)this.property.getMin());
            var10 = (float)(Short)this.property.getMin() + MathHelper.clamp(((float)var1 - this.x) / ((float)this.width + 7.4F), 0.0F, 1.0F) * (float)var11;
         } else if (this.property.getValue() instanceof Long) {
            long var12 = (Long)this.property.getMax() - (Long)this.property.getMin();
            var10 = (float)(Long)this.property.getMin() + MathHelper.clamp(((float)var1 - this.x) / ((float)this.width + 7.4F), 0.0F, 1.0F) * (float)var12;
         } else if (this.property.getValue() instanceof Float) {
            float var13 = (Float)this.property.getMax() - (Float)this.property.getMin();
            var10 = (Float)this.property.getMin() + MathHelper.clamp(((float)var1 - this.x) / ((float)this.width + 7.4F), 0.0F, 1.0F) * var13;
         } else if (this.property.getValue() instanceof Double) {
            double var14 = (Double)this.property.getMax() - (Double)this.property.getMin();
            var10 = (Double)this.property.getMin() + (double)MathHelper.clamp(((float)var1 - this.x) / ((float)this.width + 7.4F), 0.0F, 1.0F) * var14;
         } else {
            var10 = 0.0D;
         }

         Object var15;
         if (this.property.getValue() instanceof Integer) {
            var15 = ((Number)var10).intValue();
         } else if (this.property.getValue() instanceof Short) {
            var15 = ((Number)var10).shortValue();
         } else if (this.property.getValue() instanceof Long) {
            var15 = ((Number)var10).longValue();
         } else if (this.property.getValue() instanceof Float) {
            var15 = ((Number)var10).floatValue();
         } else if (this.property.getValue() instanceof Double) {
            var15 = ((Number)var10).doubleValue();
         } else {
            var15 = 0;
         }

         this.property.setValue(var15);
      }

   }

   private boolean isHovering(int var1, int var2) {
      Iterator var3 = ClickGui.getClickGui().getPanels().iterator();

      while(var3.hasNext()) {
         Panel var4 = (Panel)var3.next();
         if (var4.drag) {
            return false;
         }
      }

      return (float)var1 >= this.getX() && (float)var1 <= this.getX() + (float)this.getWidth() && (float)var2 >= this.getY() && (float)var2 <= this.getY() + (float)this.height;
   }

   public int getHeight() {
      return 14;
   }

   private float getValueWidth() {
      return ((Number)this.property.getMax()).floatValue() - ((Number)this.property.getMin()).floatValue() + ((Number)this.property.getValue()).floatValue();
   }

   public NumberSlider(Value var1) {
      super(var1.getName());
      this.setValue(var1);
   }

   public void mouseReleased(int var1, int var2, int var3) {
      this.dragging = false;
   }
}
