package dev.xulu.newgui.elements;

import com.elementars.eclient.Xulu;
import com.elementars.eclient.module.Category;
import com.elementars.eclient.module.Module;
import com.elementars.eclient.module.render.NewGui;
import com.elementars.eclient.util.ColorUtils;
import com.mojang.realmsclient.gui.ChatFormatting;
import dev.xulu.newgui.Panel;
import dev.xulu.newgui.elements.menu.ElementCheckBox;
import dev.xulu.newgui.elements.menu.ElementComboBox;
import dev.xulu.newgui.elements.menu.ElementComboBoxEnum;
import dev.xulu.newgui.elements.menu.ElementKeyBind;
import dev.xulu.newgui.elements.menu.ElementSlider;
import dev.xulu.newgui.elements.menu.ElementTextBox;
import dev.xulu.newgui.util.ColorUtil;
import dev.xulu.newgui.util.FontUtil;
import dev.xulu.settings.Value;
import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;

public class ModuleButton {
   // $FF: synthetic field
   public double y;
   // $FF: synthetic field
   public ArrayList menuelements;
   // $FF: synthetic field
   public Module mod;
   // $FF: synthetic field
   public double x;
   // $FF: synthetic field
   public boolean extended = false;
   // $FF: synthetic field
   public double height2;
   // $FF: synthetic field
   public Panel parent;
   // $FF: synthetic field
   public double width;
   // $FF: synthetic field
   public double height;
   // $FF: synthetic field
   public boolean listening = false;

   public boolean keyTyped(char var1, int var2) throws IOException {
      Iterator var3 = this.menuelements.iterator();

      Element var4;
      do {
         if (!var3.hasNext()) {
            return false;
         }

         var4 = (Element)var3.next();
      } while(!var4.set.isVisible() || !var4.keyTyped(var1, var2));

      return true;
   }

   public void drawScreen(int var1, int var2, float var3) {
      Color var4 = ColorUtil.getClickGUIColor();
      int var5 = (new Color(var4.getRed(), var4.getGreen(), var4.getBlue(), 200)).getRGB();
      if ((Boolean)NewGui.rainbowgui.getValue()) {
         var5 = ColorUtils.changeAlpha(this.parent.rgb, 200);
      }

      int var6 = -5263441;
      if (this.mod.isToggled() && !((String)NewGui.moduleSetting.getValue()).equalsIgnoreCase("Text")) {
         if (((String)NewGui.moduleSetting.getValue()).equalsIgnoreCase("MiniButton")) {
            Gui.drawRect((int)this.x, (int)this.y + 1, (int)(this.x + this.width), (int)(this.y + this.height), ColorUtils.changeAlpha(var5, 100));
            var6 = -1052689;
         } else {
            Gui.drawRect((int)this.x - 2, (int)this.y, (int)(this.x + this.width + 2.0D), (int)(this.y + this.height + 1.0D), var5);
            var6 = -1052689;
         }
      }

      if (this.isHovered(var1, var2) && !((String)NewGui.moduleSetting.getValue()).equalsIgnoreCase("Text")) {
         if (((String)NewGui.moduleSetting.getValue()).equalsIgnoreCase("MiniButton")) {
            Gui.drawRect((int)this.x, (int)this.y + 1, (int)(this.x + this.width), (int)(this.y + this.height), this.mod.isToggled() && !this.mod.getCategory().equals(Category.HUD) ? ColorUtils.changeAlpha(1427181841, 30) : ColorUtils.changeAlpha(ColorUtils.Colors.GRAY, 30));
         } else {
            Gui.drawRect((int)(this.x - 2.0D), (int)this.y, (int)(this.x + this.width + 2.0D), (int)(this.y + this.height + 1.0D), this.mod.isToggled() && !this.mod.getCategory().equals(Category.HUD) ? ColorUtils.changeAlpha(1427181841, 30) : ColorUtils.changeAlpha(ColorUtils.Colors.GRAY, 30));
         }
      }

      if (((String)NewGui.moduleSetting.getValue()).equalsIgnoreCase("MiniButton")) {
         if ((Boolean)NewGui.customfont.getValue()) {
            Xulu.cFontRenderer.drawStringWithShadow(this.mod.getName(), (double)((float)(this.x + 2.0D)), (double)((float)(this.y + 1.0D + this.height2 / 2.0D - 4.0D)), var6);
            if (Xulu.VALUE_MANAGER.getValuesByMod(this.mod) != null) {
               Xulu.cFontRenderer.drawStringWithShadow(this.extended ? "." : "...", (double)((float)(this.x + this.width - 10.0D)), (double)((float)(this.y + 1.0D + this.height2 / 2.0D - 4.0D)), var6);
            }
         } else {
            FontUtil.drawStringWithShadow(this.mod.getName(), this.x + 2.0D, this.y + 1.0D + this.height / 2.0D - 4.0D, var6);
            if (Xulu.VALUE_MANAGER.getValuesByMod(this.mod) != null) {
               FontUtil.drawStringWithShadow(this.extended ? "." : "...", this.x + this.width - 7.0D, this.y + 1.0D + this.height / 2.0D - 4.0D, var6);
            }
         }
      } else if (((String)NewGui.moduleSetting.getValue()).equalsIgnoreCase("Text")) {
         if ((Boolean)NewGui.customfont.getValue()) {
            Xulu.cFontRenderer.drawStringWithShadow(String.valueOf((new StringBuilder()).append(this.isHovered(var1, var2) ? ChatFormatting.BOLD : "").append(this.mod.getName())), (double)((float)(this.x + 2.0D)), (double)((float)(this.y + 1.0D + this.height2 / 2.0D - 4.0D)), this.mod.isToggled() && !this.mod.getCategory().equals(Category.HUD) ? ColorUtil.getClickGUIColor().getRGB() : var6);
            if (Xulu.VALUE_MANAGER.getValuesByMod(this.mod) != null) {
               Xulu.cFontRenderer.drawStringWithShadow(this.extended ? ">" : "V", (double)((float)(this.x + this.width - 6.0D)), (double)((float)(this.y + 1.0D + this.height2 / 2.0D - 4.0D)), var6);
            }
         } else {
            FontUtil.drawStringWithShadow(String.valueOf((new StringBuilder()).append(this.isHovered(var1, var2) ? ChatFormatting.BOLD : "").append(this.mod.getName())), this.x + 2.0D, this.y + 1.0D + this.height / 2.0D - 4.0D, this.mod.isToggled() && !this.mod.getCategory().equals(Category.HUD) ? ColorUtil.getClickGUIColor().getRGB() : var6);
            if (Xulu.VALUE_MANAGER.getValuesByMod(this.mod) != null) {
               FontUtil.drawStringWithShadow(this.extended ? ">" : "V", this.x + this.width - 5.0D, this.y + 1.0D + this.height / 2.0D - 4.0D, var6);
            }
         }
      } else if ((Boolean)NewGui.customfont.getValue()) {
         Xulu.cFontRenderer.drawStringWithShadow(this.mod.getName(), (double)((float)(this.x + 2.0D)), (double)((float)(this.y + 1.0D + this.height2 / 2.0D - 4.0D)), var6);
         if (Xulu.VALUE_MANAGER.getValuesByMod(this.mod) != null) {
            Xulu.cFontRenderer.drawStringWithShadow(this.extended ? ">" : "V", (double)((float)(this.x + this.width - 6.0D)), (double)((float)(this.y + 1.0D + this.height2 / 2.0D - 4.0D)), var6);
         }
      } else {
         FontUtil.drawStringWithShadow(this.mod.getName(), this.x + 2.0D, this.y + 1.0D + this.height / 2.0D - 4.0D, var6);
         if (Xulu.VALUE_MANAGER.getValuesByMod(this.mod) != null) {
            FontUtil.drawStringWithShadow(this.extended ? ">" : "V", this.x + this.width - 5.0D, this.y + 1.0D + this.height / 2.0D - 4.0D, var6);
         }
      }

   }

   public boolean mouseClicked(int var1, int var2, int var3) {
      if (!this.isHovered(var1, var2)) {
         return false;
      } else {
         if (var3 == 0) {
            this.mod.toggle();
         } else if (var3 == 1 && this.menuelements != null && this.menuelements.size() > 0) {
            boolean var4 = !this.extended;
            Xulu.newGUI.closeAllSettings();
            this.extended = var4;
         }

         return true;
      }
   }

   public ModuleButton(Module var1, Panel var2) {
      this.mod = var1;
      this.height = (double)(Minecraft.getMinecraft().fontRenderer.FONT_HEIGHT + 2);
      this.height2 = (double)(Xulu.cFontRenderer.getHeight() + 2.0F);
      this.parent = var2;
      this.menuelements = new ArrayList();
      if (Xulu.VALUE_MANAGER.getSettingsByMod(var1) != null) {
         Iterator var3 = Xulu.VALUE_MANAGER.getSettingsByMod(var1).iterator();

         while(true) {
            while(var3.hasNext()) {
               Value var4 = (Value)var3.next();
               if (var4.isToggle()) {
                  this.menuelements.add(new ElementCheckBox(this, var4));
               } else if (var4.isNumber()) {
                  this.menuelements.add(new ElementSlider(this, var4));
               } else if (var4.isMode()) {
                  this.menuelements.add(new ElementComboBox(this, var4));
               } else if (var4.isEnum()) {
                  this.menuelements.add(new ElementComboBoxEnum(this, var4));
               } else if (var4.isBind() && !(this.mod instanceof com.elementars.eclient.guirewrite.Element)) {
                  this.menuelements.add(new ElementKeyBind(this, var4));
               } else if (var4.isText()) {
                  this.menuelements.add(new ElementTextBox(this, var4));
               }
            }

            return;
         }
      }
   }

   public boolean isHovered(int var1, int var2) {
      return (double)var1 >= this.x && (double)var1 <= this.x + this.width && (double)var2 >= this.y && (double)var2 <= this.y + this.height;
   }
}
