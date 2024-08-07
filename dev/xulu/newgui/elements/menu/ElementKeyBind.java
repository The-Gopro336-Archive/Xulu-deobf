package dev.xulu.newgui.elements.menu;

import com.elementars.eclient.Xulu;
import com.elementars.eclient.module.render.NewGui;
import com.elementars.eclient.util.ColorUtils;
import dev.xulu.newgui.elements.Element;
import dev.xulu.newgui.elements.ModuleButton;
import dev.xulu.newgui.util.ColorUtil;
import dev.xulu.newgui.util.FontUtil;
import dev.xulu.settings.Bind;
import dev.xulu.settings.Value;
import java.awt.Color;
import java.io.IOException;
import net.minecraft.client.gui.Gui;
import org.lwjgl.input.Keyboard;

public class ElementKeyBind extends Element {
   // $FF: synthetic field
   private boolean listening;

   public ElementKeyBind(ModuleButton var1, Value var2) {
      this.parent = var1;
      this.set = var2;
      super.setup();
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
      String var8 = this.listening ? "..." : Keyboard.getKeyName(((Bind)this.set.getValue()).getNum());
      if ((Boolean)NewGui.customfont.getValue()) {
         Xulu.cFontRenderer.drawStringWithShadow(var8, (double)((float)(this.x + 8.0D + (double)Xulu.cFontRenderer.getStringWidth(this.setstrg))), (double)((float)this.y + 2.0F), (new Color(-1)).darker().darker().getRGB());
      } else {
         FontUtil.drawStringWithShadow(var8, this.x + 8.0D + (double)FontUtil.getStringWidth(this.setstrg), this.y + 2.0D, (new Color(-1)).darker().darker().getRGB());
      }

   }

   public boolean isButtonHovered(int var1, int var2) {
      return (double)var1 >= this.x && (double)var1 <= this.x + this.width && (double)var2 >= this.y && (double)var2 <= this.y + this.height;
   }

   public boolean keyTyped(char var1, int var2) throws IOException {
      if (this.listening) {
         if (var2 != 1) {
            this.parent.mod.setKey(var2);
         } else {
            this.parent.mod.setKey(0);
         }

         this.listening = false;
         return true;
      } else {
         return super.keyTyped(var1, var2);
      }
   }

   public boolean mouseClicked(int var1, int var2, int var3) {
      if (this.isButtonHovered(var1, var2)) {
         if (var3 == 0) {
            this.listening = true;
         }

         return true;
      } else {
         return false;
      }
   }
}
