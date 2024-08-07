package com.elementars.eclient.guirewrite;

import com.elementars.eclient.module.Category;
import com.elementars.eclient.module.Module;

public class Element extends Module {
   // $FF: synthetic field
   Frame frame;
   // $FF: synthetic field
   protected double width;
   // $FF: synthetic field
   protected double height;
   // $FF: synthetic field
   protected double y;
   // $FF: synthetic field
   protected double x;

   public Frame getFrame() {
      return this.frame;
   }

   public void registerFrame() {
      this.frame = HUD.getframeByName(this.getName());
      this.width = this.frame.width;
      this.height = this.frame.height;
   }

   public void setY(double var1) {
      this.y = var1;
   }

   public void setX(double var1) {
      this.x = var1;
   }

   public void onMiddleClick() {
   }

   public Element(String var1) {
      super(var1, "NONE", 0, Category.HUD, false);
   }

   public void onUpdate() {
      if (this.frame != null) {
         if (this.frame.width != this.width) {
            this.frame.width = this.width;
         }

         if (this.frame.height != this.height) {
            this.frame.height = this.height;
         }
      }

      super.onUpdate();
   }

   public void onDisable() {
      if (this.frame != null && this.frame.pinned) {
         this.frame.pinned = false;
      }

      super.onDisable();
   }

   public void onEnable() {
      if (this.frame != null && !this.frame.pinned) {
         this.frame.pinned = true;
      }

      super.onEnable();
   }
}
