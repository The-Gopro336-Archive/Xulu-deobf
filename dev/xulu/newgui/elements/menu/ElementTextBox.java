package dev.xulu.newgui.elements.menu;

import com.elementars.eclient.Xulu;
import com.elementars.eclient.module.render.NewGui;
import com.elementars.eclient.util.ColorUtils;
import com.elementars.eclient.util.Timer;
import dev.xulu.newgui.elements.Element;
import dev.xulu.newgui.elements.ModuleButton;
import dev.xulu.newgui.util.ColorUtil;
import dev.xulu.newgui.util.FontUtil;
import dev.xulu.settings.TextBox;
import dev.xulu.settings.Value;
import java.awt.Color;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.ChatAllowedCharacters;

public class ElementTextBox extends Element {
   // $FF: synthetic field
   private Timer timer = new Timer();
   // $FF: synthetic field
   private boolean listening;
   // $FF: synthetic field
   private boolean showCursor;
   // $FF: synthetic field
   private ElementTextBox.CurrentString currentString = new ElementTextBox.CurrentString("");

   public boolean mouseClicked(int var1, int var2, int var3) {
      if (this.isButtonHovered(var1, var2)) {
         if (var3 == 0) {
            this.currentString = new ElementTextBox.CurrentString(((TextBox)this.set.getValue()).getText());
            this.listening = true;
         }

         return true;
      } else {
         return false;
      }
   }

   public void setString(String var1) {
      this.currentString = new ElementTextBox.CurrentString(var1);
   }

   public boolean keyTyped(char var1, int var2) {
      if (this.listening) {
         switch(var2) {
         case 1:
            return true;
         case 14:
            this.setString(removeLastChar(this.currentString.getString()));
            return true;
         case 28:
            this.enterString();
            return true;
         default:
            if (ChatAllowedCharacters.isAllowedCharacter(var1)) {
               this.setString(String.valueOf((new StringBuilder()).append(this.currentString.getString()).append(var1)));
               return true;
            } else {
               return false;
            }
         }
      } else {
         return false;
      }
   }

   public boolean isButtonHovered(int var1, int var2) {
      return (double)var1 >= this.x && (double)var1 <= this.x + this.width && (double)var2 >= this.y && (double)var2 <= this.y + this.height;
   }

   public void drawScreen(int var1, int var2, float var3) {
      Color var4 = ColorUtil.getClickGUIColor();
      int var5 = (new Color(var4.getRed(), var4.getGreen(), var4.getBlue(), 150)).getRGB();
      Gui.drawRect((int)this.x, (int)this.y, (int)(this.x + this.width), (int)(this.y + this.height), ColorUtils.changeAlpha(ColorUtils.Colors.BLACK, 60));
      Gui.drawRect((int)this.x, (int)(this.y + this.height - 1.0D), (int)(this.x + this.width), (int)(this.y + this.height), ColorUtils.changeAlpha(1996488704, 30));
      String var6 = this.currentString.getString();
      if (this.listening) {
         if (this.timer.hasReached(500L)) {
            this.showCursor = !this.showCursor;
            this.timer.reset();
         }

         if (this.showCursor) {
            Gui.drawRect((int)this.x + ((Boolean)NewGui.customfont.getValue() ? Xulu.cFontRenderer.getStringWidth(var6) : FontUtil.getStringWidth(var6)) + 2, (int)this.y, (int)this.x + ((Boolean)NewGui.customfont.getValue() ? Xulu.cFontRenderer.getStringWidth(var6) : FontUtil.getStringWidth(var6)) + 3, (int)this.y + FontUtil.getFontHeight() + 2, -1);
         }
      } else {
         if (!var6.equals(((TextBox)this.set.getValue()).getText())) {
            this.currentString = new ElementTextBox.CurrentString(((TextBox)this.set.getValue()).getText());
         }

         this.showCursor = false;
      }

      if ((Boolean)NewGui.customfont.getValue()) {
         Xulu.cFontRenderer.drawStringWithShadow(var6, (double)((float)(this.x + 2.0D)), (double)((float)this.y + 2.0F), -1);
      } else {
         FontUtil.drawStringWithShadow(var6, this.x + 2.0D, this.y + 2.0D, -1);
      }

   }

   private void enterString() {
      this.set.setValue(new TextBox(this.currentString.getString()));
      this.setString(((TextBox)this.set.getValue()).getText());
      this.listening = false;
   }

   public ElementTextBox(ModuleButton var1, Value var2) {
      this.parent = var1;
      this.set = var2;
      this.currentString = new ElementTextBox.CurrentString(((TextBox)var2.getValue()).getText());
      super.setup();
   }

   public static String removeLastChar(String var0) {
      String var1 = "";
      if (var0 != null && var0.length() > 0) {
         var1 = var0.substring(0, var0.length() - 1);
      }

      return var1;
   }

   public static class CurrentString {
      // $FF: synthetic field
      private String string;

      public String getString() {
         return this.string;
      }

      public CurrentString(String var1) {
         this.string = var1;
      }
   }
}
