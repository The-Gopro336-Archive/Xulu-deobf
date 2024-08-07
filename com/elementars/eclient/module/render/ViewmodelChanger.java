package com.elementars.eclient.module.render;

import com.elementars.eclient.module.Category;
import com.elementars.eclient.module.Module;
import dev.xulu.settings.Value;

public class ViewmodelChanger extends Module {
   // $FF: synthetic field
   public final Value mode;
   // $FF: synthetic field
   public final Value y;
   // $FF: synthetic field
   public final Value alpha;
   // $FF: synthetic field
   public final Value x;
   // $FF: synthetic field
   public final Value sizey;
   // $FF: synthetic field
   public final Value posY;
   // $FF: synthetic field
   public final Value pause;
   // $FF: synthetic field
   public final Value hand;
   // $FF: synthetic field
   public final Value posZ;
   // $FF: synthetic field
   public final Value sizez;
   // $FF: synthetic field
   public final Value z;
   // $FF: synthetic field
   public final Value posX;
   // $FF: synthetic field
   public final Value item;
   // $FF: synthetic field
   public final Value sizex;

   public ViewmodelChanger() {
      super("ViewmodelChanger", "Tweaks the players hand", 0, Category.RENDER, true);
      this.mode = this.register(new Value("Mode", this, ViewmodelChanger.HandMode.MAINHAND, ViewmodelChanger.HandMode.values()));
      this.pause = this.register(new Value("Pause On Eat", this, false));
      this.hand = this.register(new Value("Hand", this, true));
      this.item = this.register(new Value("Item", this, true));
      this.sizex = this.register(new Value("Size X", this, 1.0F, 0.0F, 2.0F));
      this.sizey = this.register(new Value("Size Y", this, 1.0F, 0.0F, 2.0F));
      this.sizez = this.register(new Value("Size Z", this, 1.0F, 0.0F, 2.0F));
      this.x = this.register(new Value("x", this, 1.0F, 0.0F, 1.0F));
      this.y = this.register(new Value("y", this, 1.0F, 0.0F, 1.0F));
      this.z = this.register(new Value("z", this, 1.0F, 0.0F, 1.0F));
      this.posX = this.register(new Value("X offset", this, 0.0F, -2.0F, 2.0F));
      this.posY = this.register(new Value("Y offset", this, 0.0F, -2.0F, 2.0F));
      this.posZ = this.register(new Value("Z offset", this, 0.0F, -2.0F, 2.0F));
      this.alpha = this.register(new Value("Alpha", this, 1.0F, 0.0F, 1.0F));
   }

   public void onEnable() {
      EVENT_BUS.register(this);
   }

   public void onDisable() {
      EVENT_BUS.unregister(this);
   }

   public static enum HandMode {
      // $FF: synthetic field
      MAINHAND,
      // $FF: synthetic field
      BOTH,
      // $FF: synthetic field
      OFFHAND;

      public boolean isOK(boolean var1) {
         switch(this) {
         case MAINHAND:
            return !var1;
         case OFFHAND:
            return var1;
         case BOTH:
            return true;
         default:
            return false;
         }
      }
   }
}
