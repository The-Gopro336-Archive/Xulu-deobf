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

public class ElementComboBox extends Element {
   public ElementComboBox(ModuleButton var1, Value var2) {
      this.parent = var1;
      this.set = var2;
      super.setup();
   }

   public boolean mouseClicked(int var1, int var2, int var3) {
      String var4 = this.set.getValue() instanceof String ? (String)this.set.getValue() : this.set.getValue().toString();
      if (var3 == 0 && this.isButtonHovered(var1, var2)) {
         try {
            if (!this.set.getCorrectString(var4).equalsIgnoreCase(this.set.getOptions().get(this.set.getOptions().size() - 1).toString())) {
               this.set.setValue(this.set.getOptions().get(this.set.getOptions().indexOf(this.set.getCorrectString(var4)) + 1));
            } else {
               this.set.setValue(this.set.getOptions().get(0));
            }
         } catch (Exception var6) {
            System.err.println("Error with invalid combo");
            var6.printStackTrace();
            this.set.setValue(this.set.getOptions().get(0));
         }

         return true;
      } else if (var3 == 1 && this.isButtonHovered(var1, var2)) {
         try {
            if (this.set.getOptions().listIterator(this.set.getOptions().indexOf(this.set.getCorrectString(var4))).hasPrevious()) {
               this.set.setValue(this.set.getOptions().listIterator(this.set.getOptions().indexOf(this.set.getCorrectString(var4))).previous());
            } else {
               this.set.setValue(this.set.getOptions().get(this.set.getOptions().size() - 1));
            }
         } catch (Exception var7) {
            System.err.println("Error with invalid combo");
            var7.printStackTrace();
            this.set.setValue(this.set.getOptions().get(0));
         }

         return true;
      } else {
         return super.mouseClicked(var1, var2, var3);
      }
   }

   public void drawScreen(int var1, int var2, float var3) {
      Color var4 = ColorUtil.getClickGUIColor();
      int var5 = (new Color(var4.getRed(), var4.getGreen(), var4.getBlue(), 150)).getRGB();
      Gui.drawRect((int)this.x, (int)this.y, (int)(this.x + this.width), (int)(this.y + this.height), ColorUtils.changeAlpha(ColorUtils.Colors.BLACK, 60));
      if ((Boolean)NewGui.customfont.getValue()) {
         Xulu.cFontRenderer.drawStringWithShadow(this.setstrg, (double)((float)(this.x + 2.0D)), (double)((float)this.y + 2.0F), -1);
      } else {
         FontUtil.drawStringWithShadow(this.setstrg, this.x + 2.0D, this.y + 2.0D, -1);
      }

      int var7 = var4.getRGB();
      Gui.drawRect((int)this.x, (int)(this.y + this.height - 1.0D), (int)(this.x + this.width), (int)(this.y + this.height), ColorUtils.changeAlpha(1996488704, 30));
      String var8 = this.set.getValue() instanceof String ? (String)this.set.getValue() : this.set.getValue().toString();
      if ((Boolean)NewGui.customfont.getValue()) {
         Xulu.cFontRenderer.drawStringWithShadow(var8, (double)((float)(this.x + 8.0D + (double)Xulu.cFontRenderer.getStringWidth(this.setstrg))), (double)((float)this.y + 2.0F), (new Color(-1)).darker().darker().getRGB());
      } else {
         FontUtil.drawStringWithShadow(var8, this.x + 8.0D + (double)FontUtil.getStringWidth(this.setstrg), this.y + 2.0D, (new Color(-1)).darker().darker().getRGB());
      }

   }

   public boolean isButtonHovered(int var1, int var2) {
      return (double)var1 >= this.x && (double)var1 <= this.x + this.width && (double)var2 >= this.y && (double)var2 <= this.y + this.height;
   }
}
