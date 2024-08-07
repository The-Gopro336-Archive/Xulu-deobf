package dev.xulu.clickgui.item;

import com.elementars.eclient.Xulu;
import com.elementars.eclient.guirewrite.Element;
import com.elementars.eclient.module.Category;
import com.elementars.eclient.module.Module;
import com.elementars.eclient.module.render.ExeterGui;
import com.elementars.eclient.util.Helper;
import dev.xulu.clickgui.Panel;
import dev.xulu.clickgui.item.properties.BindButton;
import dev.xulu.clickgui.item.properties.BooleanButton;
import dev.xulu.clickgui.item.properties.EnumButton;
import dev.xulu.clickgui.item.properties.ModeButton;
import dev.xulu.clickgui.item.properties.NumberSlider;
import dev.xulu.clickgui.item.properties.TextButton;
import dev.xulu.settings.Value;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.init.SoundEvents;

public class ModuleButton extends Button implements Helper {
   // $FF: synthetic field
   private final Module module;
   // $FF: synthetic field
   private List items = new ArrayList();
   // $FF: synthetic field
   private boolean subOpen;

   public void mouseClicked(int var1, int var2, int var3) {
      super.mouseClicked(var1, var2, var3);
      if (!this.items.isEmpty()) {
         if (var3 == 1 && this.isHovering(var1, var2)) {
            this.subOpen = !this.subOpen;
            if (ExeterGui.getSound()) {
               mc.getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 1.0F));
            }
         }

         if (this.subOpen) {
            Iterator var4 = this.items.iterator();

            while(var4.hasNext()) {
               Item var5 = (Item)var4.next();
               if (var5.property.isVisible()) {
                  var5.mouseClicked(var1, var2, var3);
               }
            }
         }
      }

   }

   public void mouseReleased(int var1, int var2, int var3) {
      this.items.forEach((var3x) -> {
         if (var3x.property.isVisible()) {
            var3x.mouseReleased(var1, var2, var3);
         }

      });
      super.mouseReleased(var1, var2, var3);
   }

   public int getHeight() {
      if (this.subOpen) {
         int var1 = 14;
         Iterator var2 = this.items.iterator();

         while(var2.hasNext()) {
            Item var3 = (Item)var2.next();
            if (var3.property.isVisible()) {
               var1 += var3.getHeight() + 1;
            }
         }

         return var1 + 2;
      } else {
         return 14;
      }
   }

   public ModuleButton(Module var1, Panel var2) {
      super(var1.getName(), var2);
      this.module = var1;
      if (Xulu.VALUE_MANAGER.getSettingsByMod(var1) != null) {
         Iterator var3 = Xulu.VALUE_MANAGER.getSettingsByMod(var1).iterator();

         while(true) {
            while(var3.hasNext()) {
               Value var4 = (Value)var3.next();
               if (var4.isToggle()) {
                  this.items.add(new BooleanButton(var4));
               } else if (var4.isNumber()) {
                  this.items.add(new NumberSlider(var4));
               } else if (var4.isMode()) {
                  this.items.add(new ModeButton(var4));
               } else if (var4.isEnum()) {
                  this.items.add(new EnumButton(var4));
               } else if (var4.isBind() && !(var4.getParentMod() instanceof Element)) {
                  this.items.add(new BindButton(var4));
               } else if (var4.isText()) {
                  this.items.add(new TextButton(var4));
               }
            }

            return;
         }
      }
   }

   public boolean getState() {
      return this.module.isToggled();
   }

   public void drawScreen(int var1, int var2, float var3) {
      super.drawScreen(var1, var2, var3);
      if (!this.items.isEmpty()) {
         if (Xulu.VALUE_MANAGER.getValuesByMod(this.module) != null) {
            if (ExeterGui.getCF()) {
               Xulu.cFontRenderer.drawStringWithShadow("...", (double)(this.x + (float)this.width - (float)Xulu.cFontRenderer.getStringWidth("...") - 3.0F), (double)(this.y + 3.0F), -1);
            } else {
               fontRenderer.drawStringWithShadow("...", this.x + (float)this.width - (float)fontRenderer.getStringWidth("...") - 2.0F, this.y + 4.0F, -1);
            }
         }

         if (this.subOpen) {
            float var4 = 1.0F;
            Iterator var5 = this.items.iterator();

            while(var5.hasNext()) {
               Item var6 = (Item)var5.next();
               if (var6.property.isVisible()) {
                  var4 += 15.0F;
                  var6.setLocation(this.x + 1.0F, this.y + var4);
                  var6.setHeight(15);
                  var6.setWidth(this.width - 9);
                  var6.drawScreen(var1, var2, var3);
               }
            }
         }
      }

   }

   public void toggle() {
      if (this.module.getName().equalsIgnoreCase("HudEditor") || !this.module.getCategory().equals(Category.HUD)) {
         this.module.toggle();
      }

   }

   public boolean keyTyped(char var1, int var2) throws IOException {
      Iterator var3 = this.items.iterator();

      while(var3.hasNext()) {
         Item var4 = (Item)var3.next();
         if (var4.property.isVisible()) {
            try {
               if (var4.keyTyped(var1, var2)) {
                  return true;
               }
            } catch (IOException var6) {
               var6.printStackTrace();
            }
         }
      }

      return super.keyTyped(var1, var2);
   }
}
