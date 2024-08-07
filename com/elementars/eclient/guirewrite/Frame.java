package com.elementars.eclient.guirewrite;

import com.elementars.eclient.Xulu;
import com.elementars.eclient.module.ModuleManager;
import com.elementars.eclient.util.Wrapper;
import java.awt.Color;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;

public class Frame {
   // $FF: synthetic field
   public double y;
   // $FF: synthetic field
   public String title;
   // $FF: synthetic field
   private double y2;
   // $FF: synthetic field
   public boolean dragging;
   // $FF: synthetic field
   public double height;
   // $FF: synthetic field
   private double x2;
   // $FF: synthetic field
   public double x;
   // $FF: synthetic field
   public boolean pinned;
   // $FF: synthetic field
   public HUD hud;
   // $FF: synthetic field
   public boolean visible;
   // $FF: synthetic field
   public double width;

   public void setup() {
   }

   public void drawScreen(int var1, int var2, float var3) {
      this.visible = ModuleManager.isModuleEnabled(this.title);
      if (this.visible) {
         if (this.dragging) {
            this.x = this.x2 + (double)var1;
            this.y = this.y2 + (double)var2;
            ScaledResolution var4 = new ScaledResolution(Wrapper.getMinecraft());
            if (this.x < 0.0D) {
               this.x = 0.0D;
            }

            if (this.y < 0.0D) {
               this.y = 0.0D;
            }

            if (this.x > (double)var4.getScaledWidth() - this.width) {
               this.x = (double)var4.getScaledWidth() - this.width;
            }

            if (this.y > (double)var4.getScaledHeight() - this.height) {
               this.y = (double)var4.getScaledHeight() - this.height;
            }
         }

         if (Xulu.MODULE_MANAGER.getModuleByName(this.title) != null) {
            ((Element)Xulu.MODULE_MANAGER.getModuleByName(this.title)).x = this.x;
            ((Element)Xulu.MODULE_MANAGER.getModuleByName(this.title)).y = this.y;
         }

         if (this.dragging) {
            Gui.drawRect((int)this.x, (int)this.y, (int)(this.x + this.width), (int)(this.y + this.height), this.changeAlpha(Color.lightGray.getRGB(), 100));
            Gui.drawRect((int)(this.x + 4.0D), (int)(this.y + 2.0D), (int)(this.x + 4.3D), (int)(this.y + this.height - 2.0D), this.changeAlpha(Color.lightGray.getRGB(), 100));
         } else {
            Gui.drawRect((int)this.x, (int)this.y, (int)(this.x + this.width), (int)(this.y + this.height), this.changeAlpha(-15592942, 100));
            Gui.drawRect((int)(this.x + 4.0D), (int)(this.y + 2.0D), (int)(this.x + 4.3D), (int)(this.y + this.height - 2.0D), this.changeAlpha(-5592406, 100));
         }

         if (Xulu.MODULE_MANAGER.getModuleByName(this.title) != null) {
            Xulu.MODULE_MANAGER.getModuleByName(this.title).onRender();
         }

      }
   }

   public boolean mouseClicked(int var1, int var2, int var3) {
      if (!this.visible) {
         return false;
      } else if (var3 == 0 && this.isHovered(var1, var2)) {
         this.x2 = this.x - (double)var1;
         this.y2 = this.y - (double)var2;
         this.dragging = true;
         return true;
      } else {
         if (var3 == 2 && this.isHovered(var1, var2)) {
            ((Element)Xulu.MODULE_MANAGER.getModuleByName(this.title)).onMiddleClick();
         }

         return false;
      }
   }

   public boolean isHovered(int var1, int var2) {
      return (double)var1 >= this.x && (double)var1 <= this.x + this.width && (double)var2 >= this.y && (double)var2 <= this.y + this.height;
   }

   int changeAlpha(int var1, int var2) {
      var1 &= 16777215;
      return var2 << 24 | var1;
   }

   public Frame(String var1, double var2, double var4, double var6, double var8, boolean var10, HUD var11) {
      this.title = var1;
      this.x = var2;
      this.y = var4;
      this.width = var6;
      this.height = var8;
      this.pinned = var10;
      this.dragging = false;
      this.visible = true;
      this.hud = var11;
      this.setup();
   }

   public void mouseReleased(int var1, int var2, int var3) {
      if (this.visible) {
         if (var3 == 0) {
            this.dragging = false;
         }

      }
   }
}
