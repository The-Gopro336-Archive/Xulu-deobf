package com.elementars.eclient.module.combat;

import com.elementars.eclient.command.Command;
import com.elementars.eclient.event.EventTarget;
import com.elementars.eclient.event.events.EventReceivePacket;
import com.elementars.eclient.module.Category;
import com.elementars.eclient.module.Module;
import com.elementars.eclient.util.ColorTextUtils;
import dev.xulu.settings.Value;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemPotion;
import net.minecraft.network.play.server.SPacketEntityStatus;
import net.minecraft.network.play.server.SPacketSoundEffect;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.math.AxisAlignedBB;

public class StrengthDetect extends Module {
   // $FF: synthetic field
   public static List strPlayers = new ArrayList();
   // $FF: synthetic field
   private final Value watermark = this.register(new Value("Watermark", this, true));
   // $FF: synthetic field
   public static List drinkSet = new ArrayList();
   // $FF: synthetic field
   private final Value color;

   public void onUpdate() {
      if (mc.player != null) {
         Iterator var1 = mc.world.playerEntities.iterator();

         while(var1.hasNext()) {
            EntityPlayer var2 = (EntityPlayer)var1.next();
            Iterator var3 = var2.getActivePotionEffects().iterator();

            while(var3.hasNext()) {
               PotionEffect var4 = (PotionEffect)var3.next();
               boolean var5 = true;
               if (var4.getPotion() == MobEffects.STRENGTH) {
                  strPlayers.add(var2);
                  var5 = false;
               }

               if (var5) {
                  strPlayers.remove(var2);
               }
            }

            if (var2.getHealth() == 0.0F && strPlayers.contains(var2)) {
               strPlayers.remove(var2);
            }
         }

      }
   }

   @EventTarget
   public void onPacket(EventReceivePacket var1) {
      if (var1.getPacket() instanceof SPacketEntityStatus) {
         SPacketEntityStatus var2 = (SPacketEntityStatus)var1.getPacket();
         if (var2.getOpCode() == 35 && var2.getEntity(mc.world) instanceof EntityPlayer) {
            EntityPlayer var3 = (EntityPlayer)var2.getEntity(mc.world);
            strPlayers.remove(var3);
            Command.sendRawChatMessage(String.valueOf((new StringBuilder()).append(ColorTextUtils.getColor((String)this.color.getValue())).append(var3.getName()).append(" no longer has strength!")));
         }
      }

      if (var1.getPacket() instanceof SPacketSoundEffect) {
         SPacketSoundEffect var8 = (SPacketSoundEffect)var1.getPacket();
         EntityPlayer var4;
         Iterator var5;
         EntityPlayer var6;
         List var9;
         if (var8.sound.getSoundName().toString().equalsIgnoreCase("minecraft:entity.generic.drink")) {
            var9 = mc.world.getEntitiesWithinAABB(EntityPlayer.class, new AxisAlignedBB(var8.getX() - 1.0D, var8.getY() - 1.0D, var8.getZ() - 1.0D, var8.getX() + 1.0D, var8.getY() + 1.0D, var8.getZ() + 1.0D));
            var4 = null;
            if (var9.size() <= 1) {
               var4 = (EntityPlayer)var9.get(0);
            } else {
               var5 = var9.iterator();

               label78:
               while(true) {
                  do {
                     if (!var5.hasNext()) {
                        break label78;
                     }

                     var6 = (EntityPlayer)var5.next();
                  } while(var4 != null && !(var6.getDistance(var8.getX(), var8.getY(), var8.getZ()) < var4.getDistance(var8.getX(), var8.getY(), var8.getZ())));

                  var4 = var6;
               }
            }

            if (var4.getHeldItemMainhand().getItem() instanceof ItemPotion) {
               List var10 = PotionUtils.getEffectsFromStack(var4.getHeldItemMainhand());
               Iterator var11 = var10.iterator();

               while(var11.hasNext()) {
                  PotionEffect var7 = (PotionEffect)var11.next();
                  if (var7.getPotion() == MobEffects.STRENGTH) {
                     drinkSet.add(var4);
                  }
               }
            }
         } else if (var8.sound.getSoundName().toString().equalsIgnoreCase("minecraft:item.armor.equip_generic")) {
            var9 = mc.world.getEntitiesWithinAABB(EntityPlayer.class, new AxisAlignedBB(var8.getX() - 1.0D, var8.getY() - 1.0D, var8.getZ() - 1.0D, var8.getX() + 1.0D, var8.getY() + 1.0D, var8.getZ() + 1.0D));
            var4 = null;
            if (var9.size() <= 1) {
               var4 = (EntityPlayer)var9.get(0);
            } else {
               var5 = var9.iterator();

               label57:
               while(true) {
                  do {
                     if (!var5.hasNext()) {
                        break label57;
                     }

                     var6 = (EntityPlayer)var5.next();
                  } while(var4 != null && !(var6.getDistance(var8.getX(), var8.getY(), var8.getZ()) < var4.getDistance(var8.getX(), var8.getY(), var8.getZ())));

                  var4 = var6;
               }
            }

            if (drinkSet.contains(var4) && var4.getHeldItemMainhand().getItem() == Items.GLASS_BOTTLE) {
               strPlayers.add(var4);
               drinkSet.remove(var4);
               Command.sendRawChatMessage(String.valueOf((new StringBuilder()).append(ColorTextUtils.getColor((String)this.color.getValue())).append(var4.getName()).append(" has drank a strength pot!")));
            }
         }
      }

   }

   public StrengthDetect() {
      super("StrengthDetect", "Detects when someone has strength (BUGGY)", 0, Category.COMBAT, true);
      this.color = this.register(new Value("Color", this, "White", ColorTextUtils.colors));
   }
}
