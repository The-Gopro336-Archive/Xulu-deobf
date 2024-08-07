package com.elementars.eclient.module.movement;

import com.elementars.eclient.module.Category;
import com.elementars.eclient.module.Module;
import net.minecraft.util.MovementInput;
import net.minecraftforge.client.event.InputUpdateEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class NoSlowDown extends Module {
   public void onEnable() {
      MinecraftForge.EVENT_BUS.register(this);
   }

   public void onDisable() {
      MinecraftForge.EVENT_BUS.unregister(this);
   }

   public NoSlowDown() {
      super("NoSlowDown", "Prevents slowdown when using items", 0, Category.MOVEMENT, true);
   }

   @SubscribeEvent
   public void onInput(InputUpdateEvent var1) {
      if (mc.player.isHandActive() && !mc.player.isRiding()) {
         MovementInput var10000 = var1.getMovementInput();
         var10000.moveStrafe *= 5.0F;
         var10000 = var1.getMovementInput();
         var10000.moveForward *= 5.0F;
      }

   }
}
