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

public class ModeButton extends Button {
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
         Xulu.cFontRenderer.drawStringWithShadow(String.format("%s§7 %s", this.getLabel(), (String)this.property.getValue()), (double)(this.x + 2.3F), (double)(this.y + 3.0F), this.getState() ? -1 : -5592406);
      } else {
         fontRenderer.drawStringWithShadow(String.format("%s§7 %s", this.getLabel(), (String)this.property.getValue()), this.x + 2.3F, this.y + 4.0F, this.getState() ? -1 : -5592406);
      }

   }

   public void mouseClicked(int var1, int var2, int var3) {
      super.mouseClicked(var1, var2, var3);
      if (this.isHovering(var1, var2)) {
         if (ExeterGui.getSound()) {
            mc.getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 1.0F));
         }

         String var4 = this.property.getValue() instanceof String ? (String)this.property.getValue() : this.property.getValue().toString();
         if (var3 == 0) {
            try {
               if (!this.property.getCorrectString(var4).equalsIgnoreCase(this.property.getOptions().get(this.property.getOptions().size() - 1).toString())) {
                  this.property.setValue(this.property.getOptions().get(this.property.getOptions().indexOf(this.property.getCorrectString(var4)) + 1));
               } else {
                  this.property.setValue(this.property.getOptions().get(0));
               }
            } catch (Exception var7) {
               System.err.println("Error with invalid combo");
               var7.printStackTrace();
               this.property.setValue(this.property.getOptions().get(0));
            }
         } else if (var3 == 1) {
            try {
               if (this.property.getOptions().listIterator(this.property.getOptions().indexOf(this.property.getCorrectString(var4))).hasPrevious()) {
                  this.property.setValue(this.property.getOptions().listIterator(this.property.getOptions().indexOf(this.property.getCorrectString(var4))).previous());
               } else {
                  this.property.setValue(this.property.getOptions().get(this.property.getOptions().size() - 1));
               }
            } catch (Exception var6) {
               System.err.println("Error with invalid combo");
               var6.printStackTrace();
               this.property.setValue(this.property.getOptions().get(0));
            }
         }
      }

   }

   public boolean getState() {
      return true;
   }

   public ModeButton(Value var1) {
      super(var1.getName(), (Panel)null);
      this.setValue(var1);
   }

   public void toggle() {
   }

   public int getHeight() {
      return 14;
   }
}
