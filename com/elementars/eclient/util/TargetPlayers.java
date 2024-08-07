package com.elementars.eclient.util;

import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.player.AttackEntityEvent;

public class TargetPlayers implements Helper {
   // $FF: synthetic field
   public static ConcurrentHashMap targettedplayers = new ConcurrentHashMap();

   public static void onUpdate() {
      targettedplayers.forEach((var0, var1) -> {
         if (var1 <= 0) {
            targettedplayers.remove(var0);
         } else {
            targettedplayers.put(var0, var1 - 1);
         }

      });
   }

   public static void onAttack(AttackEntityEvent var0) {
      if (var0.getTarget() instanceof EntityPlayer) {
         if (((EntityPlayer)var0.getTarget()).getHealth() == 0.0F) {
            return;
         }

         targettedplayers.put(var0.getTarget().getName(), 20);
      }

   }

   public static void addTargetedPlayer(String var0) {
      if (!var0.equalsIgnoreCase(mc.player.getName())) {
         if (targettedplayers == null) {
            targettedplayers = new ConcurrentHashMap();
         }

         targettedplayers.put(var0, 20);
      }
   }
}
