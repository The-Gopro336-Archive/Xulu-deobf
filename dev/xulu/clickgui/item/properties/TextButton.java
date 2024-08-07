package dev.xulu.clickgui.item.properties;

import com.elementars.eclient.Xulu;
import com.elementars.eclient.module.render.ExeterGui;
import com.elementars.eclient.module.render.NewGui;
import com.elementars.eclient.util.ColorUtils;
import com.elementars.eclient.util.Timer;
import com.elementars.eclient.util.XuluTessellator;
import dev.xulu.clickgui.Panel;
import dev.xulu.clickgui.item.Button;
import dev.xulu.newgui.elements.menu.ElementTextBox;
import dev.xulu.newgui.util.ColorUtil;
import dev.xulu.newgui.util.FontUtil;
import dev.xulu.settings.TextBox;
import dev.xulu.settings.Value;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.Gui;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.ChatAllowedCharacters;

public class TextButton extends Button {
   // $FF: synthetic field
   private boolean showCursor;
   // $FF: synthetic field
   private boolean listening;
   // $FF: synthetic field
   private ElementTextBox.CurrentString currentString = new ElementTextBox.CurrentString("");
   // $FF: synthetic field
   private Timer timer = new Timer();

   public void mouseClicked(int var1, int var2, int var3) {
      super.mouseClicked(var1, var2, var3);
      if (this.isHovering(var1, var2)) {
         if (ExeterGui.getSound()) {
            mc.getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 1.0F));
         }

         if (var3 == 0) {
            this.currentString = new ElementTextBox.CurrentString(((TextBox)this.property.getValue()).getText());
            this.listening = true;
         }
      }

   }

   public void setString(String var1) {
      this.currentString = new ElementTextBox.CurrentString(var1);
   }

   public void drawScreen(int var1, int var2, float var3) {
      XuluTessellator.drawRectGradient((double)this.x, (double)this.y, (double)(this.x + (float)this.width + 7.4F), (double)(this.y + (float)this.height), this.getState() ? ColorUtils.changeAlpha(ColorUtil.getClickGUIColor().getRGB(), 200) : 290805077, -1);
      if (this.isHovering(var1, var2)) {
         if (this.getState()) {
            XuluTessellator.drawRectGradient((double)this.x, (double)this.y, (double)(this.x + (float)this.width), (double)(this.y + (float)this.height), ColorUtils.changeAlpha(ColorUtils.Colors.BLACK, 25), -1);
         } else {
            XuluTessellator.drawRectGradient((double)this.x, (double)this.y, (double)(this.x + (float)this.width), (double)(this.y + (float)this.height), ColorUtils.changeAlpha(ColorUtils.Colors.WHITE, 25), -1);
         }
      }

      String var4 = this.currentString.getString();
      if (this.listening) {
         if (this.timer.hasReached(500L)) {
            this.showCursor = !this.showCursor;
            this.timer.reset();
         }

         if (this.showCursor) {
            Gui.drawRect((int)this.x + ((Boolean)NewGui.customfont.getValue() ? Xulu.cFontRenderer.getStringWidth(var4) : FontUtil.getStringWidth(var4)) + 3, (int)this.y + 1, (int)this.x + ((Boolean)NewGui.customfont.getValue() ? Xulu.cFontRenderer.getStringWidth(var4) : FontUtil.getStringWidth(var4)) + 4, (int)this.y + FontUtil.getFontHeight() + 5, -1);
         }
      } else {
         if (!var4.equals(((TextBox)this.property.getValue()).getText())) {
            this.currentString = new ElementTextBox.CurrentString(((TextBox)this.property.getValue()).getText());
         }

         this.showCursor = false;
      }

      if (ExeterGui.getCF()) {
         Xulu.cFontRenderer.drawStringWithShadow(var4, (double)(this.x + 2.3F), (double)(this.y + 3.0F), this.getState() ? -1 : -1);
      } else {
         fontRenderer.drawStringWithShadow(var4, this.x + 2.3F, this.y + 4.0F, this.getState() ? -1 : -1);
      }

   }

   private void enterString() {
      this.property.setValue(new TextBox(this.currentString.getString()));
      this.setString(((TextBox)this.property.getValue()).getText());
      this.listening = false;
   }

   public static String removeLastChar(String var0) {
      String var1 = "";
      if (var0 != null && var0.length() > 0) {
         var1 = var0.substring(0, var0.length() - 1);
      }

      return var1;
   }

   public TextButton(Value var1) {
      super(var1.getName(), (Panel)null);
      this.setValue(var1);
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

   public int getHeight() {
      return 14;
   }

   public boolean getState() {
      return false;
   }

   public void toggle() {
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
