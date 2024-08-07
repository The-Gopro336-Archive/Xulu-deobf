package dev.xulu.clickgui.item;

import com.elementars.eclient.Xulu;
import com.elementars.eclient.module.render.ExeterGui;
import com.elementars.eclient.util.ColorUtils;
import com.elementars.eclient.util.Helper;
import com.elementars.eclient.util.XuluTessellator;
import dev.xulu.clickgui.ClickGui;
import dev.xulu.clickgui.Labeled;
import dev.xulu.clickgui.Panel;
import dev.xulu.newgui.util.ColorUtil;
import java.util.Iterator;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.init.SoundEvents;

public class Button extends Item implements Helper, Labeled {
   // $FF: synthetic field
   private Panel parent;
   // $FF: synthetic field
   private boolean state;

   public Button(String var1, Panel var2) {
      super(var1);
      this.parent = var2;
      this.height = 15;
   }

   public void mouseClicked(int var1, int var2, int var3) {
      if (var3 == 0 && this.isHovering(var1, var2)) {
         this.state = !this.state;
         this.toggle();
         if (ExeterGui.getSound()) {
            mc.getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 1.0F));
         }
      }

   }

   public boolean getState() {
      return this.state;
   }

   protected boolean isHovering(int var1, int var2) {
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

   public void toggle() {
   }

   public void drawScreen(int var1, int var2, float var3) {
      XuluTessellator.drawRectGradient((double)this.x, (double)this.y, (double)(this.x + (float)this.width), (double)(this.y + (float)this.height), this.getState() ? ColorUtils.changeAlpha(ExeterGui.getRainbow() ? this.parent.rgb : ColorUtil.getClickGUIColor().getRGB(), 225) : 861230421, -1);
      if (this.isHovering(var1, var2)) {
         if (this.getState()) {
            XuluTessellator.drawRectGradient((double)this.x, (double)this.y, (double)(this.x + (float)this.width), (double)(this.y + (float)this.height), ColorUtils.changeAlpha(ColorUtils.Colors.BLACK, 30), -1);
         } else {
            XuluTessellator.drawRectGradient((double)this.x, (double)this.y, (double)(this.x + (float)this.width), (double)(this.y + (float)this.height), ColorUtils.changeAlpha(ColorUtils.Colors.WHITE, 30), -1);
         }
      }

      if (ExeterGui.getCF()) {
         Xulu.cFontRenderer.drawStringWithShadow(this.getLabel(), (double)(this.x + 2.3F), (double)(this.y + 3.0F), this.getState() ? -1 : -5592406);
      } else {
         fontRenderer.drawStringWithShadow(this.getLabel(), this.x + 2.3F, this.y + 4.0F, this.getState() ? -1 : -5592406);
      }

   }
}
