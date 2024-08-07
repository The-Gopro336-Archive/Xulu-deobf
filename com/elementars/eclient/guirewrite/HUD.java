package com.elementars.eclient.guirewrite;

import com.elementars.eclient.Xulu;
import com.elementars.eclient.module.Category;
import com.elementars.eclient.module.Module;
import com.elementars.eclient.module.render.NewGui;
import dev.xulu.newgui.Panel;
import dev.xulu.newgui.elements.ModuleButton;
import dev.xulu.newgui.util.ColorUtil;
import dev.xulu.settings.ValueManager;
import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.input.Mouse;

public class HUD extends GuiScreen {
   // $FF: synthetic field
   private ModuleButton mb = null;
   // $FF: synthetic field
   public static ArrayList frames;
   // $FF: synthetic field
   public static ArrayList rframes;
   // $FF: synthetic field
   public ValueManager setmgr;
   // $FF: synthetic field
   public Panel hudPanel;

   public void mouseClicked(int var1, int var2, int var3) {
      Iterator var4;
      if (this.hudPanel.extended && this.hudPanel.visible && this.hudPanel.Elements != null) {
         var4 = this.hudPanel.Elements.iterator();

         label51:
         while(true) {
            ModuleButton var5;
            do {
               if (!var4.hasNext()) {
                  break label51;
               }

               var5 = (ModuleButton)var4.next();
            } while(!var5.extended);

            Iterator var6 = var5.menuelements.iterator();

            while(var6.hasNext()) {
               dev.xulu.newgui.elements.Element var7 = (dev.xulu.newgui.elements.Element)var6.next();
               if (var7.mouseClicked(var1, var2, var3)) {
                  return;
               }
            }
         }
      }

      if (!this.hudPanel.mouseClicked(var1, var2, var3)) {
         var4 = frames.iterator();

         Frame var10;
         do {
            if (!var4.hasNext()) {
               try {
                  super.mouseClicked(var1, var2, var3);
               } catch (IOException var9) {
                  var9.printStackTrace();
               }

               return;
            }

            var10 = (Frame)var4.next();
         } while(!var10.mouseClicked(var1, var2, var3));

      }
   }

   public HUD() {
      this.setmgr = Xulu.VALUE_MANAGER;
      frames = new ArrayList();
      double var1 = 80.0D;
      double var3 = 15.0D;
      double var5 = 10.0D;
      double var7 = 10.0D;
      double var9 = var3 + 10.0D;
      frames.add(new Frame("PvPInfo", var5, var7, var1, var3, false, this));
      frames.add(new Frame("Totems", var5, var7, var1, var3, false, this));
      frames.add(new Frame("Obsidian", var5, var7, var1, var3, false, this));
      frames.add(new Frame("Crystals", var5, var7, var1, var3, false, this));
      frames.add(new Frame("Gapples", var5, var7, var1, var3, false, this));
      frames.add(new Frame("InvPreview", var5, var7, var1, var3, false, this));
      frames.add(new Frame("TextRadar", var5, var7, var1, var3, false, this));
      frames.add(new Frame("FeatureList", var5, var7, var1, var3, false, this));
      frames.add(new Frame("Player", var5, var7, var1, var3, false, this));
      frames.add(new Frame("Welcome", var5, var7, var1, var3, false, this));
      frames.add(new Frame("OldName", var5, var7, var1, var3, false, this));
      frames.add(new Frame("TheGoons", var5, var7, var1, var3, false, this));
      frames.add(new Frame("Potions", var5, var7, var1, var3, false, this));
      frames.add(new Frame("StickyNotes", var5, var7, var1, var3, false, this));
      frames.add(new Frame("Exp", var5, var7, var1, var3, false, this));
      frames.add(new Frame("HoleHud", var5, var7, var1, var3, false, this));
      frames.add(new Frame("Info", var5, var7, var1, var3, false, this));
      frames.add(new Frame("Armor", var5, var7, var1, var3, false, this));
      frames.add(new Frame("CraftingPreview", var5, var7, var1, var3, false, this));
      frames.add(new Frame("GodInfo", var5, var7, var1, var3, false, this));
      frames.add(new Frame("Watermark", 2.0D, 2.0D, var1, var3, false, this));
      frames.add(new Frame("Logo", var5, var7, var1, var3, false, this));
      frames.add(new Frame("Target", var5, var7, var1, var3, false, this));
      rframes = new ArrayList();
      Iterator var11 = frames.iterator();

      while(var11.hasNext()) {
         Frame var12 = (Frame)var11.next();
         rframes.add(var12);
      }

      Collections.reverse(rframes);
      this.hudPanel = new Panel("Elements", var5, var7, 100.0D, 13.0D, true, Xulu.newGUI) {
         public void setup() {
            Iterator var1 = Xulu.MODULE_MANAGER.getModules().iterator();

            while(true) {
               Module var2;
               do {
                  if (!var1.hasNext()) {
                     return;
                  }

                  var2 = (Module)var1.next();
               } while(!var2.getCategory().equals(Category.HUD) && !(var2 instanceof Element));

               System.out.println("[HUD] We adding a modulebutton");
               this.Elements.add(new ModuleButton(var2, this));
            }
         }
      };
   }

   public void refreshPanel() {
      this.hudPanel = new Panel("Elements", 10.0D, 10.0D, 100.0D, 13.0D, true, Xulu.newGUI) {
         public void setup() {
            Iterator var1 = Xulu.MODULE_MANAGER.getModules().iterator();

            while(true) {
               Module var2;
               do {
                  if (!var1.hasNext()) {
                     return;
                  }

                  var2 = (Module)var1.next();
               } while(!var2.getCategory().equals(Category.HUD) && !(var2 instanceof Element));

               this.Elements.add(new ModuleButton(var2, this));
            }
         }
      };
   }

   public static void registerElements() {
      Iterator var0 = Xulu.MODULE_MANAGER.getModules().iterator();

      while(var0.hasNext()) {
         Module var1 = (Module)var0.next();
         if (var1 instanceof Element) {
            ((Element)var1).registerFrame();
         }
      }

   }

   public void handleMouseInput() throws IOException {
      byte var1 = 5;
      Panel var10000;
      Iterator var2;
      Frame var3;
      if (Mouse.getEventDWheel() > 0) {
         for(var2 = rframes.iterator(); var2.hasNext(); var3.y += (double)var1) {
            var3 = (Frame)var2.next();
         }

         var10000 = this.hudPanel;
         var10000.y += (double)var1;
      }

      if (Mouse.getEventDWheel() < 0) {
         for(var2 = rframes.iterator(); var2.hasNext(); var3.y -= (double)var1) {
            var3 = (Frame)var2.next();
         }

         var10000 = this.hudPanel;
         var10000.y -= (double)var1;
      }

      super.handleMouseInput();
   }

   protected void keyTyped(char var1, int var2) {
      try {
         super.keyTyped(var1, var2);
      } catch (IOException var4) {
         var4.printStackTrace();
      }

   }

   public static Frame getframeByName(String var0) {
      Iterator var1 = getFrames().iterator();

      Frame var2;
      do {
         if (!var1.hasNext()) {
            return null;
         }

         var2 = (Frame)var1.next();
      } while(!var2.title.equalsIgnoreCase(var0));

      return var2;
   }

   public void drawScreen(int var1, int var2, float var3) {
      this.hudPanel.drawScreen(var1, var2, var3);
      Iterator var4;
      ModuleButton var5;
      if (this.hudPanel.extended && this.hudPanel.visible && this.hudPanel.Elements != null) {
         var4 = this.hudPanel.Elements.iterator();

         label72:
         while(true) {
            do {
               do {
                  do {
                     if (!var4.hasNext()) {
                        break label72;
                     }

                     var5 = (ModuleButton)var4.next();
                  } while(!var5.extended);
               } while(var5.menuelements == null);
            } while(var5.menuelements.isEmpty());

            double var6 = var5.height + 1.0D;
            Color var8 = ColorUtil.getClickGUIColor().darker();
            if ((Boolean)NewGui.rainbowgui.getValue()) {
               var8 = (new Color(Xulu.rgb)).darker();
            }

            int var9 = (new Color(var8.getRed(), var8.getGreen(), var8.getBlue(), 170)).getRGB();

            dev.xulu.newgui.elements.Element var11;
            for(Iterator var10 = var5.menuelements.iterator(); var10.hasNext(); var6 += var11.height) {
               var11 = (dev.xulu.newgui.elements.Element)var10.next();
               var11.offset = var6;
               var11.update();
               var11.drawScreen(var1, var2, var3);
            }
         }
      }

      this.mb = null;
      if (this.hudPanel != null && this.hudPanel.visible && this.hudPanel.extended && this.hudPanel.Elements != null && this.hudPanel.Elements.size() > 0) {
         var4 = this.hudPanel.Elements.iterator();

         while(var4.hasNext()) {
            var5 = (ModuleButton)var4.next();
            if (var5.listening) {
               this.mb = var5;
               break;
            }
         }
      }

      var4 = frames.iterator();

      while(var4.hasNext()) {
         Frame var12 = (Frame)var4.next();
         var12.drawScreen(var1, var2, var3);
      }

      new ScaledResolution(this.mc);
      super.drawScreen(var1, var2, var3);
   }

   public static ArrayList getFrames() {
      return frames;
   }

   public void mouseReleased(int var1, int var2, int var3) {
      Iterator var4;
      if (this.hudPanel.extended && this.hudPanel.visible && this.hudPanel.Elements != null) {
         var4 = this.hudPanel.Elements.iterator();

         label36:
         while(true) {
            ModuleButton var5;
            do {
               if (!var4.hasNext()) {
                  break label36;
               }

               var5 = (ModuleButton)var4.next();
            } while(!var5.extended);

            Iterator var6 = var5.menuelements.iterator();

            while(var6.hasNext()) {
               dev.xulu.newgui.elements.Element var7 = (dev.xulu.newgui.elements.Element)var6.next();
               var7.mouseReleased(var1, var2, var3);
            }
         }
      }

      this.hudPanel.mouseReleased(var1, var2, var3);
      var4 = rframes.iterator();

      while(var4.hasNext()) {
         Frame var8 = (Frame)var4.next();
         var8.mouseReleased(var1, var2, var3);
      }

      super.mouseReleased(var1, var2, var3);
   }
}
