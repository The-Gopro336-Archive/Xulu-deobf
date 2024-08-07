package com.elementars.eclient.module.combat;

import com.elementars.eclient.module.Category;
import com.elementars.eclient.module.Module;
import com.elementars.eclient.util.EntityUtil;
import com.elementars.eclient.util.TargetPlayers;
import dev.xulu.settings.Value;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import javax.annotation.Nullable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class AutoEz extends Module {
   // $FF: synthetic field
   private final Value acmessage;
   // $FF: synthetic field
   private final Value acmode;
   // $FF: synthetic field
   private final Value autocityboss;
   // $FF: synthetic field
   private ArrayList targets;
   // $FF: synthetic field
   private ConcurrentHashMap targettedplayers;
   // $FF: synthetic field
   private ArrayList dummy = new ArrayList(Arrays.asList("Change this in the settings"));
   // $FF: synthetic field
   public static AutoEz INSTANCE;
   // $FF: synthetic field
   private EntityEnderCrystal crystal;
   // $FF: synthetic field
   private ConcurrentHashMap totemplayers;
   // $FF: synthetic field
   private int hasBeenCombat;
   // $FF: synthetic field
   private int acdelay;
   // $FF: synthetic field
   private int ahdelay;
   // $FF: synthetic field
   private final Value message;
   // $FF: synthetic field
   private final Value ahmessage;
   // $FF: synthetic field
   private ConcurrentHashMap helmetplayers;
   // $FF: synthetic field
   private final Value autoezhelmet;
   // $FF: synthetic field
   private final Value ahmode;
   // $FF: synthetic field
   private final Value mode;
   // $FF: synthetic field
   private EntityPlayer target;

   public void onUpdate() {
      if (mc.player.isDead) {
         this.hasBeenCombat = 0;
      }

      Iterator var1 = mc.world.getLoadedEntityList().iterator();

      Entity var2;
      EntityPlayer var3;
      String var4;
      while(var1.hasNext()) {
         var2 = (Entity)var1.next();
         if (EntityUtil.isPlayer(var2)) {
            var3 = (EntityPlayer)var2;
            if (!(var3.getHealth() > 0.0F)) {
               var4 = var3.getName();
               if (TargetPlayers.targettedplayers.containsKey(var4)) {
                  if ((Boolean)this.mode.getValue()) {
                     this.sendChatMessage((String)this.message.getValue(), var4);
                  } else {
                     this.sendChatMessage((String)this.message.getValue(), (String)null);
                  }

                  TargetPlayers.targettedplayers.remove(var4);
               }
            }
         }
      }

      if ((Boolean)this.autocityboss.getValue() && this.acdelay == 0) {
         var1 = mc.world.getLoadedEntityList().iterator();

         while(var1.hasNext()) {
            var2 = (Entity)var1.next();
            if (EntityUtil.isPlayer(var2)) {
               var3 = (EntityPlayer)var2;
               if (var3.getHeldItemOffhand().getItem() == Items.TOTEM_OF_UNDYING) {
                  this.totemplayers.put(var3.getName(), 20);
               } else {
                  var4 = var3.getName();
                  if (TargetPlayers.targettedplayers.containsKey(var4) && this.totemplayers.containsKey(var4) && mc.world.playerEntities.contains(var3)) {
                     if ((Boolean)this.acmode.getValue()) {
                        this.sendChatMessage((String)this.acmessage.getValue(), var4);
                     } else {
                        this.sendChatMessage((String)this.acmessage.getValue(), (String)null);
                     }

                     TargetPlayers.targettedplayers.remove(var4);
                     this.totemplayers.remove(var4);
                     this.acdelay = 1500;
                     break;
                  }
               }
            }
         }
      }

      if ((Boolean)this.autoezhelmet.getValue() && this.ahdelay == 0) {
         var1 = mc.world.getLoadedEntityList().iterator();

         label95:
         while(true) {
            do {
               if (!var1.hasNext()) {
                  break label95;
               }

               var2 = (Entity)var1.next();
            } while(!EntityUtil.isPlayer(var2));

            var3 = (EntityPlayer)var2;
            boolean var7 = false;
            Iterator var5 = var3.getArmorInventoryList().iterator();

            while(var5.hasNext()) {
               ItemStack var6 = (ItemStack)var5.next();
               if (var6 != null && var6.getItem() == Items.DIAMOND_HELMET) {
                  var7 = true;
               }
            }

            if (var7) {
               this.helmetplayers.put(var3.getName(), 20);
            } else {
               String var8 = var3.getName();
               if (TargetPlayers.targettedplayers.containsKey(var8) && this.helmetplayers.containsKey(var8) && mc.world.playerEntities.contains(var3)) {
                  if ((Boolean)this.acmode.getValue()) {
                     this.sendChatMessage((String)this.ahmessage.getValue(), var8);
                  } else {
                     this.sendChatMessage((String)this.ahmessage.getValue(), (String)null);
                  }

                  TargetPlayers.targettedplayers.remove(var8);
                  this.helmetplayers.remove(var8);
                  this.ahdelay = 1500;
                  break;
               }
            }
         }
      }

      this.totemplayers.forEach((var1x, var2x) -> {
         if (var2x <= 0) {
            this.totemplayers.remove(var1x);
         } else {
            this.totemplayers.put(var1x, var2x - 1);
         }

      });
      this.helmetplayers.forEach((var1x, var2x) -> {
         if (var2x <= 0) {
            this.helmetplayers.remove(var1x);
         } else {
            this.helmetplayers.put(var1x, var2x - 1);
         }

      });
      if (this.acdelay > 0) {
         --this.acdelay;
      }

      if (this.ahdelay > 0) {
         --this.ahdelay;
      }

   }

   public void onDisable() {
      MinecraftForge.EVENT_BUS.unregister(this);
   }

   @SubscribeEvent
   public void onDeath(LivingDeathEvent var1) {
      if (mc.player != null) {
         EntityLivingBase var2 = var1.getEntityLiving();
         if (var2 != null && EntityUtil.isPlayer(var2)) {
            EntityPlayer var3 = (EntityPlayer)var2;
            if (var3.getHealth() <= 0.0F) {
               String var4 = var3.getName();
               if (TargetPlayers.targettedplayers.containsKey(var4)) {
                  if ((Boolean)this.mode.getValue()) {
                     this.sendChatMessage((String)this.message.getValue(), var4);
                  } else {
                     this.sendChatMessage((String)this.message.getValue(), (String)null);
                  }

                  TargetPlayers.targettedplayers.remove(var4);
               }
            }
         }
      }

   }

   private void sendChatMessage(String var1, @Nullable String var2) {
      String var3 = var2 == null ? var1 : var1.replaceAll("NAME", var2);
      mc.player.sendChatMessage(var3);
   }

   public AutoEz() {
      super("AutoEZ", "Sends toxic messages for you (use NAME like in Welcome)", 0, Category.COMBAT, true);
      this.message = this.register(new Value("Message", this, "NAME has been put in the montage", this.dummy));
      this.mode = this.register(new Value("Names", this, true));
      this.autocityboss = this.register(new Value("AutoCityboss", this, false));
      this.acmessage = this.register(new Value("AC message", this, "NAME ez pop", this.dummy));
      this.acmode = this.register(new Value("AC Names", this, true));
      this.autoezhelmet = this.register(new Value("AutoEZHelmet", this, false));
      this.ahmessage = this.register(new Value("AH message", this, "NAME ez helmet", this.dummy));
      this.ahmode = this.register(new Value("AH Names", this, true));
      this.targettedplayers = new ConcurrentHashMap();
      this.totemplayers = new ConcurrentHashMap();
      this.helmetplayers = new ConcurrentHashMap();
      INSTANCE = this;
   }

   public void onEnable() {
      MinecraftForge.EVENT_BUS.register(this);
   }

   @SubscribeEvent
   public void onAttack(AttackEntityEvent var1) {
      if (this.isToggled() && var1.getTarget() instanceof EntityEnderCrystal) {
         this.crystal = (EntityEnderCrystal)var1.getTarget();
      }

      if (this.isToggled() && var1.getTarget() instanceof EntityPlayer) {
         EntityPlayer var2 = (EntityPlayer)var1.getTarget();
         if (var2.getHealth() <= 0.0F || var2.isDead) {
            if ((Boolean)this.mode.getValue()) {
               this.sendChatMessage((String)this.message.getValue(), var2.getName());
            } else {
               this.sendChatMessage((String)this.message.getValue(), (String)null);
            }
         }
      }

   }
}
