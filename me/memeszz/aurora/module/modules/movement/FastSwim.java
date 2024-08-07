package me.memeszz.aurora.module.modules.movement;

import com.elementars.eclient.module.Category;
import com.elementars.eclient.module.Module;
import dev.xulu.settings.Value;

public class FastSwim extends Module {
   // $FF: synthetic field
   int divider = 5;
   // $FF: synthetic field
   private Value down;
   // $FF: synthetic field
   private Value forward;
   // $FF: synthetic field
   private Value up;
   // $FF: synthetic field
   private Value sprint;
   // $FF: synthetic field
   private Value only2b;

   public FastSwim() {
      super("FastSwim", "Allows The Player To Swim Faster Horizontally and Vertically", 0, Category.MOVEMENT, true);
   }

   public void setup() {
      this.up = this.register(new Value("FastSwimUp", this, true));
      this.down = this.register(new Value("FastSwimDown", this, true));
      this.forward = this.register(new Value("FastSwimForward", this, true));
      this.sprint = this.register(new Value("AutoSprintInLiquid", this, true));
      this.only2b = this.register(new Value("Only2b", this, true));
   }

   public void onUpdate() {
      int var1;
      if ((Boolean)this.only2b.getValue() && !mc.isSingleplayer() && mc.getCurrentServerData() != null && mc.getCurrentServerData().serverIP.equalsIgnoreCase("2b2t.org")) {
         if ((Boolean)this.sprint.getValue() && (mc.player.isInLava() || mc.player.isInWater())) {
            mc.player.setSprinting(true);
         }

         if ((mc.player.isInWater() || mc.player.isInLava()) && mc.gameSettings.keyBindJump.isKeyDown() && (Boolean)this.up.getValue()) {
            mc.player.motionY = 0.725D / (double)this.divider;
         }

         if (mc.player.isInWater() || mc.player.isInLava()) {
            if ((!(Boolean)this.forward.getValue() || !mc.gameSettings.keyBindForward.isKeyDown()) && !mc.gameSettings.keyBindLeft.isKeyDown() && !mc.gameSettings.keyBindRight.isKeyDown() && !mc.gameSettings.keyBindBack.isKeyDown()) {
               mc.player.jumpMovementFactor = 0.0F;
            } else {
               mc.player.jumpMovementFactor = 0.34F / (float)this.divider;
            }
         }

         if (mc.player.isInWater() && (Boolean)this.down.getValue() && mc.gameSettings.keyBindSneak.isKeyDown()) {
            var1 = this.divider * -1;
            mc.player.motionY = 2.2D / (double)var1;
         }

         if (mc.player.isInLava() && (Boolean)this.down.getValue() && mc.gameSettings.keyBindSneak.isKeyDown()) {
            var1 = this.divider * -1;
            mc.player.motionY = 0.91D / (double)var1;
         }
      }

      if (!(Boolean)this.only2b.getValue()) {
         if ((Boolean)this.sprint.getValue() && (mc.player.isInLava() || mc.player.isInWater())) {
            mc.player.setSprinting(true);
         }

         if ((mc.player.isInWater() || mc.player.isInLava()) && mc.gameSettings.keyBindJump.isKeyDown() && (Boolean)this.up.getValue()) {
            mc.player.motionY = 0.725D / (double)this.divider;
         }

         if (mc.player.isInWater() || mc.player.isInLava()) {
            if ((!(Boolean)this.forward.getValue() || !mc.gameSettings.keyBindForward.isKeyDown()) && !mc.gameSettings.keyBindLeft.isKeyDown() && !mc.gameSettings.keyBindRight.isKeyDown() && !mc.gameSettings.keyBindBack.isKeyDown()) {
               mc.player.jumpMovementFactor = 0.0F;
            } else {
               mc.player.jumpMovementFactor = 0.34F / (float)this.divider;
            }
         }

         if (mc.player.isInWater() && (Boolean)this.down.getValue() && mc.gameSettings.keyBindSneak.isKeyDown()) {
            var1 = this.divider * -1;
            mc.player.motionY = 2.2D / (double)var1;
         }

         if (mc.player.isInLava() && (Boolean)this.down.getValue() && mc.gameSettings.keyBindSneak.isKeyDown()) {
            var1 = this.divider * -1;
            mc.player.motionY = 0.91D / (double)var1;
         }
      }

   }
}
