package dev.xulu.clickgui;

import com.elementars.eclient.Xulu;
import com.elementars.eclient.module.Category;
import com.elementars.eclient.module.Module;
import dev.xulu.clickgui.item.Item;
import dev.xulu.clickgui.item.ModuleButton;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import net.minecraft.client.gui.GuiScreen;
import org.lwjgl.input.Mouse;

public final class ClickGui extends GuiScreen {
   // $FF: synthetic field
   private static ClickGui clickGui;
   // $FF: synthetic field
   private final ArrayList panels = new ArrayList();

   public void mouseClicked(int var1, int var2, int var3) {
      this.panels.forEach((var3x) -> {
         var3x.mouseClicked(var1, var2, var3);
      });
   }

   private void load() {
      int var1 = -84;
      Category[] var2 = Category.values();
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         final Category var5 = var2[var4];
         if (var5 != Category.HIDDEN && var5 != Category.HUD) {
            ArrayList var6 = this.panels;
            String var7 = String.valueOf((new StringBuilder()).append(Character.toUpperCase(var5.name().toLowerCase().charAt(0))).append(var5.name().toLowerCase().substring(1)));
            var1 += 90;
            var6.add(new Panel(var7, var1, 4, true) {
               public void setupItems() {
                  Iterator var1 = Xulu.MODULE_MANAGER.getModules().iterator();

                  while(var1.hasNext()) {
                     Module var2 = (Module)var1.next();
                     if (var2.getCategory().equals(var5)) {
                        this.addButton(new ModuleButton(var2, this));
                     }
                  }

               }
            });
         }
      }

      this.panels.forEach((var0) -> {
         var0.getItems().sort((var0x, var1) -> {
            return var0x.getLabel().compareTo(var1.getLabel());
         });
      });
   }

   public void handleMouseInput() throws IOException {
      byte var1 = 5;
      Iterator var2;
      Panel var3;
      if (Mouse.getEventDWheel() > 0) {
         var2 = this.panels.iterator();

         while(var2.hasNext()) {
            var3 = (Panel)var2.next();
            var3.setY(var3.getY() + var1);
         }
      }

      if (Mouse.getEventDWheel() < 0) {
         var2 = this.panels.iterator();

         while(var2.hasNext()) {
            var3 = (Panel)var2.next();
            var3.setY(var3.getY() - var1);
         }
      }

      super.handleMouseInput();
   }

   public boolean doesGuiPauseGame() {
      return false;
   }

   public ClickGui() {
      if (this.getPanels().isEmpty()) {
         this.load();
      }

   }

   public void mouseReleased(int var1, int var2, int var3) {
      this.panels.forEach((var3x) -> {
         var3x.mouseReleased(var1, var2, var3);
      });
   }

   public static ClickGui getClickGui() {
      return clickGui == null ? (clickGui = new ClickGui()) : clickGui;
   }

   public void drawScreen(int var1, int var2, float var3) {
      this.drawDefaultBackground();
      this.panels.forEach((var3x) -> {
         var3x.drawScreen(var1, var2, var3);
      });
   }

   public final ArrayList getPanels() {
      return this.panels;
   }

   protected void keyTyped(char var1, int var2) {
      Iterator var3 = this.panels.iterator();

      while(true) {
         Panel var4;
         do {
            do {
               if (!var3.hasNext()) {
                  try {
                     super.keyTyped(var1, var2);
                  } catch (IOException var8) {
                     var8.printStackTrace();
                  }

                  return;
               }

               var4 = (Panel)var3.next();
            } while(var4 == null);
         } while(!var4.getOpen());

         Iterator var5 = var4.getItems().iterator();

         while(var5.hasNext()) {
            Item var6 = (Item)var5.next();

            try {
               if (var6.keyTyped(var1, var2)) {
                  return;
               }
            } catch (IOException var9) {
               var9.printStackTrace();
            }
         }
      }
   }
}
