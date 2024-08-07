package dev.xulu.newgui;

import com.elementars.eclient.Xulu;
import com.elementars.eclient.module.core.Global;
import com.elementars.eclient.module.render.NewGui;
import com.elementars.eclient.util.ColorUtils;
import com.elementars.eclient.util.XuluTessellator;
import dev.xulu.newgui.elements.Element;
import dev.xulu.newgui.elements.ModuleButton;
import dev.xulu.newgui.util.ColorUtil;
import dev.xulu.newgui.util.FontUtil;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import net.minecraft.client.gui.Gui;

public class Panel {
   // $FF: synthetic field
   public int rgb;
   // $FF: synthetic field
   public NewGUI clickgui;
   // $FF: synthetic field
   public double x;
   // $FF: synthetic field
   public boolean dragging;
   // $FF: synthetic field
   public boolean visible;
   // $FF: synthetic field
   public double y;
   // $FF: synthetic field
   private double y2;
   // $FF: synthetic field
   private double x2;
   // $FF: synthetic field
   public boolean extended;
   // $FF: synthetic field
   public double width;
   // $FF: synthetic field
   public String title;
   // $FF: synthetic field
   public double height;
   // $FF: synthetic field
   public ArrayList Elements = new ArrayList();

   public boolean isHovered(int var1, int var2) {
      return (double)var1 >= this.x && (double)var1 <= this.x + this.width && (double)var2 >= this.y && (double)var2 <= this.y + this.height;
   }

   public void mouseReleased(int var1, int var2, int var3) {
      if (this.visible) {
         if (var3 == 0) {
            this.dragging = false;
         }

      }
   }

   public void setup() {
   }

   public boolean mouseClicked(int var1, int var2, int var3) {
      if (!this.visible) {
         return false;
      } else if (var3 == 0 && this.isHovered(var1, var2)) {
         this.x2 = this.x - (double)var1;
         this.y2 = this.y - (double)var2;
         this.dragging = true;
         return true;
      } else if (var3 == 1 && this.isHovered(var1, var2)) {
         this.extended = !this.extended;
         return true;
      } else {
         if (this.extended) {
            Iterator var4 = this.Elements.iterator();

            while(var4.hasNext()) {
               ModuleButton var5 = (ModuleButton)var4.next();
               if (var5.mouseClicked(var1, var2, var3)) {
                  return true;
               }
            }
         }

         return false;
      }
   }

   public void drawScreen(int var1, int var2, float var3) {
      if (this.visible) {
         if (this.dragging) {
            this.x = this.x2 + (double)var1;
            this.y = this.y2 + (double)var2;
         }

         this.rgb = Xulu.rgb;
         Color var4 = ColorUtil.getClickGUIColor();
         int var5 = (new Color(var4.getRed(), var4.getGreen(), var4.getBlue(), 200)).getRGB();
         if ((Boolean)NewGui.rainbowgui.getValue()) {
            var5 = ColorUtils.changeAlpha(this.rgb, 200);
         }

         int var6 = ColorUtils.changeAlpha(var5, 225);
         Gui.drawRect((int)this.x, (int)this.y, (int)(this.x + this.width), (int)(this.y + this.height), ColorUtils.changeAlpha(ColorUtils.Colors.BLACK, 225));
         Gui.drawRect((int)this.x, (int)this.y, (int)(this.x + this.width), (int)(this.y + this.height), var5);
         Gui.drawRect((int)(this.x + 4.0D), (int)(this.y + 2.0D), (int)(this.x + 4.3D), (int)(this.y + this.height - 2.0D), -5592406);
         if (this.extended) {
            Gui.drawRect((int)this.x, (int)(this.y + this.height - 1.0D), (int)(this.x + this.width), (int)(this.y + this.height), ColorUtils.changeAlpha(ColorUtils.Colors.BLACK, 30));
         }

         if ((Boolean)NewGui.outline.getValue()) {
            if (this.extended) {
               XuluTessellator.drawRectOutline((double)((int)this.x - 1), (double)((int)this.y - 1), (double)((int)(this.x + this.width) + 1), (double)((int)(this.y + this.height)), (double)((int)this.x), (double)((int)this.y), (double)((int)(this.x + this.width)), (double)((int)(this.y + this.height)), var6);
            } else {
               XuluTessellator.drawRectOutline((double)((int)this.x), (double)((int)this.y), (double)((int)(this.x + this.width)), (double)((int)(this.y + this.height)), 1.0D, var6);
            }
         }

         if ((Boolean)NewGui.customfont.getValue()) {
            Xulu.cFontRenderer.drawStringWithShadow(this.title, (double)((float)(this.x + 4.0D)), (double)((float)(this.y + this.height / 2.0D - 4.0D)), -1052689);
         } else {
            FontUtil.drawStringWithShadow(this.title, this.x + 4.0D, this.y + this.height / 2.0D - 4.0D, -1052689);
         }

         if (this.extended && !this.Elements.isEmpty()) {
            double var7 = this.y + this.height;
            int var9 = ColorUtils.changeAlpha(ColorUtils.Colors.BLACK, (Integer)NewGui.bgAlpha.getValue());
            int var10 = ColorUtils.changeAlpha(ColorUtil.getClickGUIColor().getRGB(), 30);
            Iterator var11 = this.Elements.iterator();

            label84:
            while(true) {
               ModuleButton var12;
               do {
                  if (!var11.hasNext()) {
                     Gui.drawRect((int)this.x, (int)(var7 + 1.0D), (int)(this.x + this.width), (int)(var7 + 1.0D), var9);
                     return;
                  }

                  var12 = (ModuleButton)var11.next();
                  if ((Boolean)NewGui.rainbowgui.getValue()) {
                     var6 = ColorUtils.changeAlpha(this.rgb = this.updateRainbow(this.rgb), 225);
                  }

                  Gui.drawRect((int)this.x, (int)var7, (int)(this.x + this.width), (int)(var7 + var12.height + 1.0D), var9);
                  if ((Boolean)NewGui.outline.getValue()) {
                     if (this.Elements.indexOf(var12) == this.Elements.size() - 1 && !var12.extended) {
                        XuluTessellator.drawRectOutline((double)((int)this.x - 1), (double)((int)var7), (double)((int)(this.x + this.width) + 1), (double)((int)(var7 + var12.height + 1.0D) + 1), (double)((int)this.x), (double)((int)var7), (double)((int)(this.x + this.width)), (double)((int)(var7 + var12.height + 1.0D)), var6);
                     } else {
                        XuluTessellator.drawRectOutline((double)((int)this.x - 1), (double)((int)var7), (double)((int)(this.x + this.width) + 1), (double)((int)(var7 + var12.height + 1.0D)), (double)((int)this.x), (double)((int)var7), (double)((int)(this.x + this.width)), (double)((int)(var7 + var12.height + 1.0D)), var6);
                     }
                  }

                  if (((String)NewGui.moduleSetting.getValue()).equalsIgnoreCase("MiniButton")) {
                     Gui.drawRect((int)this.x + 2, (int)var7 + 1, (int)(this.x + this.width) - 2, (int)(var7 + var12.height), var10);
                  }

                  var12.x = this.x + 2.0D;
                  var12.y = var7;
                  var12.width = this.width - 4.0D;
                  var12.drawScreen(var1, var2, var3);
                  var7 += var12.height + 1.0D;
               } while(!var12.extended);

               Iterator var13 = var12.menuelements.iterator();

               while(true) {
                  Element var14;
                  do {
                     if (!var13.hasNext()) {
                        continue label84;
                     }

                     var14 = (Element)var13.next();
                  } while(!var14.set.isVisible());

                  Gui.drawRect((int)this.x, (int)var7, (int)(this.x + this.width), (int)(var7 + var12.height + 1.0D), var9);
                  if ((Boolean)NewGui.outline.getValue()) {
                     if (this.Elements.indexOf(var12) == this.Elements.size() - 1 && var12.menuelements.indexOf(var14) == var12.menuelements.size() - 1) {
                        XuluTessellator.drawRectOutline((double)((int)this.x - 1), (double)((int)var7), (double)((int)(this.x + this.width) + 1), (double)((int)(var7 + var12.height + 1.0D) + 1), (double)((int)this.x), (double)((int)var7), (double)((int)(this.x + this.width)), (double)((int)(var7 + var12.height + 1.0D)), var6);
                     } else {
                        XuluTessellator.drawRectOutline((double)((int)this.x - 1), (double)((int)var7), (double)((int)(this.x + this.width) + 1), (double)((int)(var7 + var12.height + 1.0D)), (double)((int)this.x), (double)((int)var7), (double)((int)(this.x + this.width)), (double)((int)(var7 + var12.height + 1.0D)), var6);
                     }
                  }

                  var7 += var12.height + 1.0D;
               }
            }
         }
      }
   }

   public Panel(String var1, double var2, double var4, double var6, double var8, boolean var10, NewGUI var11) {
      this.title = var1;
      this.x = var2;
      this.y = var4;
      this.width = var6;
      this.height = var8;
      this.extended = var10;
      this.dragging = false;
      this.visible = true;
      this.clickgui = var11;
      this.setup();
   }

   public int updateRainbow(int var1) {
      float var2 = Color.RGBtoHSB((new Color(var1)).getRed(), (new Color(var1)).getGreen(), (new Color(var1)).getBlue(), (float[])null)[0];
      var2 += (float)(Integer)NewGui.rainbowspeed.getValue() / 1000.0F;
      if (var2 > 1.0F) {
         --var2;
      }

      return Color.HSBtoRGB(var2, (float)(Integer)Global.rainbowSaturation.getValue() / 255.0F, (float)(Integer)Global.rainbowLightness.getValue() / 255.0F);
   }
}
