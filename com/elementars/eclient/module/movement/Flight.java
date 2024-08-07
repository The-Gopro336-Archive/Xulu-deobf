package com.elementars.eclient.module.movement;

import com.elementars.eclient.event.EventTarget;
import com.elementars.eclient.event.events.MotionEvent;
import com.elementars.eclient.module.Category;
import com.elementars.eclient.module.Module;
import com.elementars.eclient.util.MathUtil;
import dev.xulu.settings.Value;

public class Flight extends Module {
   // $FF: synthetic field
   private Value vSpeed = this.register(new Value("V Speed", this, 3.0F, 0.0F, 20.0F));
   // $FF: synthetic field
   private Value glide = this.register(new Value("Glide", this, true));
   // $FF: synthetic field
   private Value speed = this.register(new Value("Speed", this, 10.0F, 0.0F, 20.0F));
   // $FF: synthetic field
   private Value glideSpeed = this.register(new Value("GlideSpeed", this, 0.25F, 0.0F, 5.0F));
   // $FF: synthetic field
   private Double flyHeight;

   @EventTarget
   public void onWalkingUpdate(MotionEvent var1) {
      double[] var2 = MathUtil.directionSpeed((double)(Float)this.speed.getValue());
      if (ElytraFly.mc.player.isElytraFlying()) {
         if (this.flyHeight == null) {
            this.flyHeight = ElytraFly.mc.player.posY;
         }

         if ((Boolean)this.glide.getValue()) {
            this.flyHeight = this.flyHeight - (double)(Float)this.glideSpeed.getValue();
         }

         double var3 = 0.0D;
         double var5 = 0.0D;
         if (ElytraFly.mc.player.movementInput.moveStrafe != 0.0F || ElytraFly.mc.player.movementInput.moveForward != 0.0F) {
            var3 = var2[0];
            var5 = var2[1];
         }

         if (ElytraFly.mc.gameSettings.keyBindJump.isKeyDown()) {
            this.flyHeight = ElytraFly.mc.player.posY + (double)(Float)this.vSpeed.getValue();
         }

         if (ElytraFly.mc.gameSettings.keyBindSneak.isKeyDown()) {
            this.flyHeight = ElytraFly.mc.player.posY - (double)(Float)this.vSpeed.getValue();
         }

         ElytraFly.mc.player.setPosition(ElytraFly.mc.player.posX + var3, this.flyHeight, ElytraFly.mc.player.posZ + var5);
         ElytraFly.mc.player.setVelocity(0.0D, 0.0D, 0.0D);
      }

      this.flyHeight = null;
   }

   public Flight() {
      super("Flight", "Get off the ground!", 0, Category.MOVEMENT, true);
   }
}
