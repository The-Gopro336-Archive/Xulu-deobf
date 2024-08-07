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

public class ElementCheckBox extends Element {
   public ElementCheckBox(ModuleButton var1, Value var2) {
      this.parent = var1;
      this.set = var2;
      super.setup();
   }

   public boolean isCheckHovered(int var1, int var2) {
      return (double)var1 >= this.x + 1.0D && (double)var1 <= this.x + 11.0D && (double)var2 >= this.y + 1.0D && (double)var2 <= this.y + 11.0D;
   }

   public void drawScreen(int var1, int var2, float var3) {
      Color var4;
      int var5;
      if (((String)NewGui.toggleSetting.getValue()).equalsIgnoreCase("Checkbox")) {
         var4 = ColorUtil.getClickGUIColor();
         if ((Boolean)NewGui.rainbowgui.getValue()) {
            var4 = (new Color(Xulu.rgb)).darker();
         }

         var5 = (new Color(var4.getRed(), var4.getGreen(), var4.getBlue(), 225)).getRGB();
         Gui.drawRect((int)this.x, (int)this.y, (int)(this.x + this.width), (int)(this.y + this.height), ColorUtils.changeAlpha(ColorUtils.Colors.BLACK, 60));
         if ((Boolean)NewGui.customfont.getValue()) {
            Xulu.cFontRenderer.drawStringWithShadow(this.setstrg, (double)((float)(this.x + this.width - (double)Xulu.cFontRenderer.getStringWidth(this.setstrg))), (double)((float)(this.y + this.height / 2.0D - 1.5D) - 3.0F), -1);
         } else {
            FontUtil.drawString(this.setstrg, this.x + this.width - (double)FontUtil.getStringWidth(this.setstrg), this.y + (double)(FontUtil.getFontHeight() / 2) - 1.5D, -1);
         }

         Gui.drawRect((int)(this.x + 1.0D), (int)(this.y + 1.0D), (int)(this.x + 11.0D), (int)(this.y + 11.0D), (Boolean)this.set.getValue() ? var5 : ColorUtils.changeAlpha(-16777216, 150));
         if (this.isCheckHovered(var1, var2)) {
            Gui.drawRect((int)(this.x + 1.0D), (int)(this.y + 1.0D), (int)(this.x + 11.0D), (int)(this.y + 11.0D), ColorUtils.changeAlpha(1427181841, 30));
         }
      } else if (((String)NewGui.toggleSetting.getValue()).equalsIgnoreCase("Full-box")) {
         var4 = ColorUtil.getClickGUIColor().darker();
         if ((Boolean)NewGui.rainbowgui.getValue()) {
            var4 = (new Color(Xulu.rgb)).darker();
         }

         var5 = (new Color(var4.getRed(), var4.getGreen(), var4.getBlue(), 200)).getRGB();
         Gui.drawRect((int)this.x, (int)this.y, (int)(this.x + this.width), (int)(this.y + this.height), (Boolean)this.set.getValue() ? ColorUtils.changeAlpha(var5, 225) : ColorUtils.changeAlpha(ColorUtils.Colors.BLACK, 60));
         if ((Boolean)NewGui.customfont.getValue()) {
            Xulu.cFontRenderer.drawStringWithShadow(this.setstrg, (double)((float)(this.x + this.width / 2.0D - (double)(Xulu.cFontRenderer.getStringWidth(this.setstrg) / 2))), (double)((float)(this.y + (double)(Xulu.cFontRenderer.getHeight() / 2.0F) - 1.5D)), -1);
         } else {
            FontUtil.drawStringWithShadow(this.setstrg, this.x + this.width / 2.0D - (double)(FontUtil.getStringWidth(this.setstrg) / 2), this.y + (double)(FontUtil.getFontHeight() / 2) - 1.5D, -1);
         }

         Gui.drawRect((int)this.x, (int)(this.y + this.height - 1.0D), (int)(this.x + this.width), (int)(this.y + this.height), ColorUtils.changeAlpha(ColorUtils.Colors.BLACK, 30));
      }

   }

   public boolean mouseClicked(int var1, int var2, int var3) {
      if (var3 == 0) {
         if (((String)NewGui.toggleSetting.getValue()).equalsIgnoreCase("Checkbox")) {
            if (!this.isCheckHovered(var1, var2)) {
               return super.mouseClicked(var1, var2, var3);
            }
         } else if (!this.isHovered(var1, var2)) {
            return super.mouseClicked(var1, var2, var3);
         }

         this.set.setValue(!(Boolean)this.set.getValue());
         return true;
      } else {
         return super.mouseClicked(var1, var2, var3);
      }
   }
}
