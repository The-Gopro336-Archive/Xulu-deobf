package dev.xulu.newgui.elements;

import dev.xulu.newgui.NewGUI;
import dev.xulu.newgui.util.FontUtil;
import dev.xulu.settings.Value;
import java.io.IOException;

public class Element {
   // $FF: synthetic field
   public double y;
   // $FF: synthetic field
   public double x;
   // $FF: synthetic field
   public Value set;
   // $FF: synthetic field
   public boolean comboextended;
   // $FF: synthetic field
   public NewGUI clickgui;
   // $FF: synthetic field
   public double offset;
   // $FF: synthetic field
   public String setstrg;
   // $FF: synthetic field
   public double height;
   // $FF: synthetic field
   public ModuleButton parent;
   // $FF: synthetic field
   public double width;

   public void mouseReleased(int var1, int var2, int var3) {
   }

   public boolean mouseClicked(int var1, int var2, int var3) {
      return this.isHovered(var1, var2);
   }

   public void update() {
      this.x = this.parent.x - 2.0D;
      this.y = this.parent.y + this.offset;
      this.width = this.parent.width + 4.0D;
      this.height = this.parent.height + 1.0D;
      String var1 = this.set.getName();
      if (this.set.isToggle()) {
         this.setstrg = String.valueOf((new StringBuilder()).append(var1.substring(0, 1).toUpperCase()).append(var1.substring(1, var1.length())));
         double var2 = this.x + this.width - (double)FontUtil.getStringWidth(this.setstrg);
         if (var2 < this.x + 13.0D) {
            this.width += this.x + 13.0D - var2 + 1.0D;
         }
      } else if (this.set.isMode()) {
         this.setstrg = String.valueOf((new StringBuilder()).append(var1.substring(0, 1).toUpperCase()).append(var1.substring(1, var1.length())));
      } else if (this.set.isNumber()) {
         this.setstrg = String.valueOf((new StringBuilder()).append(var1.substring(0, 1).toUpperCase()).append(var1.substring(1, var1.length())));
         String var5;
         if (this.set.getValue() instanceof Integer) {
            var5 = String.valueOf((new StringBuilder()).append("").append((double)Math.round((double)(Integer)this.set.getMax() * 100.0D) / 100.0D));
         } else if (this.set.getValue() instanceof Short) {
            var5 = String.valueOf((new StringBuilder()).append("").append((double)Math.round((double)(Short)this.set.getMax() * 100.0D) / 100.0D));
         } else if (this.set.getValue() instanceof Long) {
            var5 = String.valueOf((new StringBuilder()).append("").append((double)Math.round((double)(Long)this.set.getMax() * 100.0D) / 100.0D));
         } else if (this.set.getValue() instanceof Float) {
            var5 = String.valueOf((new StringBuilder()).append("").append((double)Math.round((double)(Float)this.set.getMax() * 100.0D) / 100.0D));
         } else if (this.set.getValue() instanceof Double) {
            var5 = String.valueOf((new StringBuilder()).append("").append((double)Math.round((Double)this.set.getMax() * 100.0D) / 100.0D));
         } else {
            var5 = "";
         }

         double var3 = this.x + this.width - (double)FontUtil.getStringWidth(this.setstrg) - (double)FontUtil.getStringWidth(var5) - 4.0D;
         if (var3 < this.x) {
            this.width += this.x - var3 + 1.0D;
         }
      } else if (this.set.isBind()) {
         this.setstrg = String.valueOf((new StringBuilder()).append(var1.substring(0, 1).toUpperCase()).append(var1.substring(1, var1.length())));
      }

   }

   public boolean keyTyped(char var1, int var2) throws IOException {
      return false;
   }

   public void drawScreen(int var1, int var2, float var3) {
   }

   public boolean isHovered(int var1, int var2) {
      return (double)var1 >= this.x && (double)var1 <= this.x + this.width && (double)var2 >= this.y && (double)var2 <= this.y + this.height;
   }

   public void setup() {
      this.clickgui = this.parent.parent.clickgui;
   }
}
