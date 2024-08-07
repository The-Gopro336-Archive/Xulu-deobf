package dev.xulu.clickgui.item.properties;

import com.elementars.eclient.Xulu;
import com.elementars.eclient.module.render.ExeterGui;
import com.elementars.eclient.util.ColorUtils;
import com.elementars.eclient.util.XuluTessellator;
import dev.xulu.clickgui.Panel;
import dev.xulu.clickgui.item.Button;
import dev.xulu.newgui.util.ColorUtil;
import dev.xulu.settings.Bind;
import dev.xulu.settings.Value;
import java.io.IOException;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.init.SoundEvents;
import org.lwjgl.input.Keyboard;

public class BindButton extends Button {
   // $FF: synthetic field
   private boolean listening;

   public int getHeight() {
      return 14;
   }

   public boolean keyTyped(char var1, int var2) throws IOException {
      if (this.listening) {
         if (var2 != 1) {
            this.property.getParentMod().setKey(var2);
         } else {
            this.property.getParentMod().setKey(0);
         }

         this.listening = false;
         return true;
      } else {
         return super.keyTyped(var1, var2);
      }
   }

   public void toggle() {
   }

   public boolean getState() {
      return false;
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

      String var4 = this.listening ? "..." : Keyboard.getKeyName(((Bind)this.property.getValue()).getNum());
      if (ExeterGui.getCF()) {
         Xulu.cFontRenderer.drawStringWithShadow(String.format("%s§7 %s", this.getLabel(), var4), (double)(this.x + 2.3F), (double)(this.y + 3.0F), this.getState() ? -1 : -1);
      } else {
         fontRenderer.drawStringWithShadow(String.format("%s§7 %s", this.getLabel(), var4), this.x + 2.3F, this.y + 4.0F, this.getState() ? -1 : -1);
      }

   }

   public void mouseClicked(int var1, int var2, int var3) {
      super.mouseClicked(var1, var2, var3);
      if (this.isHovering(var1, var2)) {
         if (ExeterGui.getSound()) {
            mc.getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 1.0F));
         }

         if (var3 == 0) {
            this.listening = true;
         }
      }

   }

   public BindButton(Value var1) {
      super(var1.getName(), (Panel)null);
      this.setValue(var1);
   }
}
