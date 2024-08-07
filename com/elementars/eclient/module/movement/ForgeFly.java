package com.elementars.eclient.module.movement;

import com.elementars.eclient.module.Category;
import com.elementars.eclient.module.Module;
import dev.xulu.settings.Value;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketEntityAction.Action;

public class ForgeFly extends Module {
   // $FF: synthetic field
   private final Value fenable = this.register(new Value("Fly on Enable", this, false));
   // $FF: synthetic field
   private final Value speed = this.register(new Value("Speed", this, 0.05D, 0.0D, 10.0D));

   public void onUpdate() {
      if (mc.player.isElytraFlying()) {
         this.enableFly();
      }

      mc.player.capabilities.setFlySpeed(((Double)this.speed.getValue()).floatValue());
   }

   private void enableFly() {
      if (mc.player != null && mc.player.capabilities != null) {
         mc.player.capabilities.allowFlying = true;
         mc.player.capabilities.isFlying = true;
      }
   }

   public void onEnable() {
      if ((Boolean)this.fenable.getValue()) {
         mc.addScheduledTask(() -> {
            if (mc.player != null && !mc.player.isElytraFlying()) {
               mc.player.connection.sendPacket(new CPacketEntityAction(mc.player, Action.START_FALL_FLYING));
            }

         });
      }

   }

   private void disableFly() {
      if (mc.player != null && mc.player.capabilities != null) {
         PlayerCapabilities var1 = new PlayerCapabilities();
         mc.playerController.getCurrentGameType().configurePlayerCapabilities(var1);
         PlayerCapabilities var2 = mc.player.capabilities;
         var2.allowFlying = var1.allowFlying;
         var2.isFlying = var1.allowFlying && var2.isFlying;
         var2.setFlySpeed(var1.getFlySpeed());
      }
   }

   public ForgeFly() {
      super("ForgeFly", "ForgeHax ElytraFlight", 0, Category.MOVEMENT, true);
   }

   public void onDisable() {
      this.disableFly();
      if (mc.player != null) {
         mc.player.connection.sendPacket(new CPacketEntityAction(mc.player, Action.START_FALL_FLYING));
      }

   }
}
