package com.elementars.eclient.module.movement;

import com.elementars.eclient.event.Event;
import com.elementars.eclient.event.EventTarget;
import com.elementars.eclient.event.events.PlayerMoveEvent;
import com.elementars.eclient.module.Category;
import com.elementars.eclient.module.Module;
import com.elementars.eclient.util.MovementUtils;
import dev.xulu.settings.Value;

public class Sprint extends Module {
   // $FF: synthetic field
   private final Value mode = this.register(new Value("Mode", this, "Legit", new String[]{"Legit", "Rage"}));

   @EventTarget
   public void onMove(PlayerMoveEvent var1) {
      if (var1.getEventState() == Event.State.PRE) {
         if ((mc.gameSettings.keyBindForward.isKeyDown() || mc.gameSettings.keyBindBack.isKeyDown() || mc.gameSettings.keyBindLeft.isKeyDown() || mc.gameSettings.keyBindRight.isKeyDown()) && !mc.player.isSneaking() && !mc.player.collidedHorizontally && !((float)mc.player.getFoodStats().getFoodLevel() <= 6.0F)) {
            mc.player.setSprinting(true);
            double[] var2 = MovementUtils.forward2(0.01745329238474369D);
            var1.setX(var2[0] * 0.20000000298023224D);
            var1.setZ(var2[1] * 0.20000000298023224D);
         }

      }
   }

   public Sprint() {
      super("Sprint", "Sprints", 0, Category.MOVEMENT, true);
   }

   public void onUpdate() {
      try {
         if (((String)this.mode.getValue()).equalsIgnoreCase("Legit") && mc.gameSettings.keyBindForward.isKeyDown() && !mc.player.isSneaking() && !mc.player.isHandActive() && !mc.player.collidedHorizontally && mc.currentScreen == null && !((float)mc.player.getFoodStats().getFoodLevel() <= 6.0F)) {
            mc.player.setSprinting(true);
         }
      } catch (Exception var2) {
      }

   }
}
