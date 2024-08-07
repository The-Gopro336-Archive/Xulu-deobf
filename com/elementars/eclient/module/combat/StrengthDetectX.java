package com.elementars.eclient.module.combat;

import com.elementars.eclient.module.Category;
import com.elementars.eclient.module.Module;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.event.entity.living.PotionColorCalculationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class StrengthDetectX extends Module {
   // $FF: synthetic field
   public static final Set strengthPlayers = new HashSet();
   // $FF: synthetic field
   public static final Map strMap = new HashMap();

   public StrengthDetectX() {
      super("StrengthDetectX", "Hope this works", 0, Category.COMBAT, true);
   }

   @SubscribeEvent
   public void onPotionColor(PotionColorCalculationEvent var1) {
      if (var1.getEntityLiving() instanceof EntityPlayer) {
         this.sendDebugMessage("Yo this event is being fired");
         boolean var2 = false;
         Iterator var3 = var1.getEffects().iterator();

         while(var3.hasNext()) {
            PotionEffect var4 = (PotionEffect)var3.next();
            if (var4.getPotion() == MobEffects.STRENGTH) {
               strMap.put((EntityPlayer)var1.getEntityLiving(), var4.getAmplifier());
               this.sendRawDebugMessage(String.valueOf((new StringBuilder()).append(var1.getEntityLiving().getName()).append(" has strength")));
               var2 = true;
               break;
            }
         }

         if (strMap.containsKey((EntityPlayer)var1.getEntityLiving()) && !var2) {
            strMap.remove((EntityPlayer)var1.getEntityLiving());
            this.sendRawDebugMessage(String.valueOf((new StringBuilder()).append(var1.getEntityLiving().getName()).append(" no longer has strength")));
         }
      }

   }

   public void onEnable() {
      EVENT_BUS.register(this);
   }

   public void onDisable() {
      EVENT_BUS.unregister(this);
   }
}
