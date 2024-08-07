package dev.xulu.clickgui.item.properties;

import com.elementars.eclient.Xulu;
import com.elementars.eclient.module.render.ExeterGui;
import com.elementars.eclient.util.ColorUtils;
import com.elementars.eclient.util.XuluTessellator;
import dev.xulu.clickgui.Panel;
import dev.xulu.clickgui.item.Button;
import dev.xulu.newgui.util.ColorUtil;
import dev.xulu.settings.Value;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.init.SoundEvents;

public class BooleanButton extends Button {
   public void toggle() {
      this.property.setValue(!(Boolean)this.property.getValue());
   }

   public int getHeight() {
      return 14;
   }

   public BooleanButton(Value var1) {
      super(var1.getName(), (Panel)null);
      this.setValue(var1);
      this.width = 15;
   }

   public void mouseClicked(int var1, int var2, int var3) {
      super.mouseClicked(var1, var2, var3);
      if (this.isHovering(var1, var2) && ExeterGui.getSound()) {
         mc.getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 1.0F));
      }

   }

   public boolean getState() {
      return (Boolean)this.property.getValue();
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

      if (ExeterGui.getCF()) {
         Xulu.cFontRenderer.drawStringWithShadow(this.getLabel(), (double)(this.x + 2.3F), (double)(this.y + 3.0F), this.getState() ? -1 : -5592406);
      } else {
         fontRenderer.drawStringWithShadow(this.getLabel(), this.x + 2.3F, this.y + 4.0F, this.getState() ? -1 : -5592406);
      }

   }
}
