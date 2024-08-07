package dev.xulu.clickgui;

import com.elementars.eclient.Xulu;
import com.elementars.eclient.module.core.Global;
import com.elementars.eclient.module.render.ExeterGui;
import com.elementars.eclient.util.ColorUtils;
import com.elementars.eclient.util.Helper;
import com.elementars.eclient.util.XuluTessellator;
import dev.xulu.clickgui.item.Button;
import dev.xulu.clickgui.item.Item;
import dev.xulu.newgui.util.ColorUtil;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.init.SoundEvents;

public abstract class Panel implements Helper, Labeled {
   // $FF: synthetic field
   private final ArrayList items = new ArrayList();
   // $FF: synthetic field
   public int rgb;
   // $FF: synthetic field
   private int height;
   // $FF: synthetic field
   private int x2;
   // $FF: synthetic field
   private int y2;
   // $FF: synthetic field
   public boolean drag;
   // $FF: synthetic field
   private boolean open;
   // $FF: synthetic field
   private int y;
   // $FF: synthetic field
   private int width;
   // $FF: synthetic field
   private int x;
   // $FF: synthetic field
   private final String label;

   public Panel(String var1, int var2, int var3, boolean var4) {
      this.label = var1;
      this.x = var2;
      this.y = var3;
      this.width = 88;
      this.height = 18;
      this.open = var4;
      this.setupItems();
   }

   public void mouseClicked(int var1, int var2, int var3) {
      if (var3 == 0 && this.isHovering(var1, var2)) {
         this.x2 = this.x - var1;
         this.y2 = this.y - var2;
         ClickGui.getClickGui().getPanels().forEach((var0) -> {
            if (var0.drag) {
               var0.drag = false;
            }

         });
         this.drag = true;
      } else if (var3 == 1 && this.isHovering(var1, var2)) {
         this.open = !this.open;
         if (ExeterGui.getSound()) {
            mc.getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 1.0F));
         }

      } else if (this.open) {
         this.getItems().forEach((var3x) -> {
            var3x.mouseClicked(var1, var2, var3);
         });
      }
   }

   public void setX(int var1) {
      this.x = var1;
   }

   private boolean isHovering(int var1, int var2) {
      return var1 >= this.getX() && var1 <= this.getX() + this.getWidth() && var2 >= this.getY() && var2 <= this.getY() + this.getHeight() - (this.open ? 2 : 0);
   }

   public int updateRainbow(int var1) {
      float var2 = Color.RGBtoHSB((new Color(var1)).getRed(), (new Color(var1)).getGreen(), (new Color(var1)).getBlue(), (float[])null)[0];
      var2 += (float)ExeterGui.getSpeed() / 1000.0F;
      if (var2 > 1.0F) {
         --var2;
      }

      return Color.HSBtoRGB(var2, (float)(Integer)Global.rainbowSaturation.getValue() / 255.0F, (float)(Integer)Global.rainbowLightness.getValue() / 255.0F);
   }

   private float getTotalItemHeight() {
      float var1 = 0.0F;

      Item var3;
      for(Iterator var2 = this.getItems().iterator(); var2.hasNext(); var1 += (float)var3.getHeight() + 1.5F) {
         var3 = (Item)var2.next();
      }

      return var1;
   }

   public final ArrayList getItems() {
      return this.items;
   }

   public abstract void setupItems();

   public void addButton(Button var1) {
      this.items.add(var1);
   }

   public void setY(int var1) {
      this.y = var1;
   }

   public int getWidth() {
      return this.width;
   }

   public void setOpen(boolean var1) {
      this.open = var1;
   }

   private void drag(int var1, int var2) {
      if (this.drag) {
         this.x = this.x2 + var1;
         this.y = this.y2 + var2;
      }
   }

   public int getY() {
      return this.y;
   }

   public int getHeight() {
      return this.height;
   }

   public int getX() {
      return this.x;
   }

   public void mouseReleased(int var1, int var2, int var3) {
      if (var3 == 0) {
         this.drag = false;
      }

      if (this.open) {
         this.getItems().forEach((var3x) -> {
            var3x.mouseReleased(var1, var2, var3);
         });
      }
   }

   public boolean getOpen() {
      return this.open;
   }

   public void drawScreen(int var1, int var2, float var3) {
      this.rgb = Xulu.rgb;
      this.drag(var1, var2);
      float var4 = this.open ? this.getTotalItemHeight() - 2.0F : 0.0F;
      XuluTessellator.drawRectGradient((double)((float)this.x), (double)((float)this.y - 1.5F), (double)((float)(this.x + this.width)), (double)((float)(this.y + this.height - 6)), ColorUtils.changeAlpha(ExeterGui.getRainbow() ? this.rgb : ColorUtil.getClickGUIColor().getRGB(), 225), -6710887);
      if (this.open) {
         RenderMethods.drawRect((float)this.x, (float)this.y + 12.5F, (float)(this.x + this.width), this.open ? (float)(this.y + this.height) + var4 : (float)(this.y + this.height - 1), 1996488704);
      }

      if (ExeterGui.getCF()) {
         Xulu.cFontRenderer.drawStringWithShadow(this.getLabel(), (double)((float)this.x + 3.0F), (double)((float)this.y + 1.0F), -1);
      } else {
         fontRenderer.drawStringWithShadow(this.getLabel(), (float)this.x + 3.0F, (float)this.y + 2.0F, -1);
      }

      if (this.open) {
         float var5 = (float)(this.getY() + this.getHeight()) - 3.0F;

         Item var7;
         for(Iterator var6 = this.getItems().iterator(); var6.hasNext(); var5 += (float)var7.getHeight() + 1.5F) {
            var7 = (Item)var6.next();
            this.rgb = this.updateRainbow(this.rgb);
            var7.setLocation((float)this.x + 2.0F, var5);
            var7.setWidth(this.getWidth() - 4);
            var7.drawScreen(var1, var2, var3);
         }
      }

   }

   public final String getLabel() {
      return this.label;
   }
}
