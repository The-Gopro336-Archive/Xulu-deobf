package com.elementars.eclient.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.Potion;

public class PotionUtil {
   // $FF: synthetic field
   public static ArrayList potionMap;

   static {
      potionMap = new ArrayList(Arrays.asList(new Pair(MobEffects.ABSORPTION, "Absorption"), new Pair(MobEffects.BLINDNESS, "Blindness"), new Pair(MobEffects.FIRE_RESISTANCE, "Fire Resistance"), new Pair(MobEffects.GLOWING, "Glowing"), new Pair(MobEffects.HASTE, "Haste"), new Pair(MobEffects.HEALTH_BOOST, "Health Boost"), new Pair(MobEffects.HUNGER, "Hunger"), new Pair(MobEffects.INSTANT_DAMAGE, "Instant Damage"), new Pair(MobEffects.INSTANT_HEALTH, "Instant Health"), new Pair(MobEffects.INVISIBILITY, "Invisibility"), new Pair(MobEffects.JUMP_BOOST, "Jump Boost"), new Pair(MobEffects.LEVITATION, "Levitation"), new Pair(MobEffects.LUCK, "Luck"), new Pair(MobEffects.MINING_FATIGUE, "Mining Fatigue"), new Pair(MobEffects.NAUSEA, "Nausea"), new Pair(MobEffects.NIGHT_VISION, "Night Vision"), new Pair(MobEffects.POISON, "Poison"), new Pair(MobEffects.REGENERATION, "Regeneration"), new Pair(MobEffects.RESISTANCE, "Resistance"), new Pair(MobEffects.SATURATION, "Saturation"), new Pair(MobEffects.SLOWNESS, "Slowness"), new Pair(MobEffects.SPEED, "Speed"), new Pair(MobEffects.STRENGTH, "Strength"), new Pair(MobEffects.UNLUCK, "Unluck"), new Pair(MobEffects.WATER_BREATHING, "Water Breathing"), new Pair(MobEffects.WEAKNESS, "Weakness"), new Pair(MobEffects.WITHER, "Wither")));
   }

   public static String getPotionName(Potion var0) {
      Iterator var1 = potionMap.iterator();

      Pair var2;
      do {
         if (!var1.hasNext()) {
            return null;
         }

         var2 = (Pair)var1.next();
      } while(var2.key != var0);

      return (String)var2.value;
   }
}
