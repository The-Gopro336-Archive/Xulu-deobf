package com.elementars.eclient.module.combat;

import com.elementars.eclient.Xulu;
import com.elementars.eclient.event.EventTarget;
import com.elementars.eclient.event.events.LocalPlayerUpdateEvent;
import com.elementars.eclient.module.Category;
import com.elementars.eclient.module.Module;
import dev.xulu.settings.Value;
import java.util.ArrayList;
import java.util.Iterator;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagList;

public class AutoTotem extends Module {
   // $FF: synthetic field
   int switchDelay1 = -1;
   // $FF: synthetic field
   int slot = -1;
   // $FF: synthetic field
   public final Value delay = this.register(new Value("Offhand Delay", this, 5, 0, 20));
   // $FF: synthetic field
   public static int offhand_delay = 0;
   // $FF: synthetic field
   public final Value health = this.register(new Value("Health", this, 20, 1, 22));
   // $FF: synthetic field
   int switchDelay2 = -1;
   // $FF: synthetic field
   boolean didFirst = false;
   // $FF: synthetic field
   public final Value delayA = this.register(new Value("Delay", this, 1, 0, 5));
   // $FF: synthetic field
   public static boolean switchInProgress = false;

   public static boolean isFullArmor(EntityPlayer var0) {
      boolean var1 = true;
      int var2 = 0;
      boolean var3 = false;
      Iterator var4 = var0.getArmorInventoryList().iterator();

      while(var4.hasNext()) {
         ItemStack var5 = (ItemStack)var4.next();
         if (var5.isEmpty()) {
            var1 = false;
            break;
         }

         if (var5.getItem() == Items.DIAMOND_HELMET) {
            ++var2;
         }

         if (var5.getItem() == Items.DIAMOND_CHESTPLATE) {
            ++var2;
         }

         if (var5.getItem() == Items.DIAMOND_LEGGINGS) {
            ++var2;
         }

         if (var5.getItem() == Items.DIAMOND_BOOTS) {
            ++var2;
         }

         NBTTagList var6 = var5.getEnchantmentTagList();
         ArrayList var7 = new ArrayList();
         if (var6 != null) {
            for(int var8 = 0; var8 < var6.tagCount(); ++var8) {
               short var9 = var6.getCompoundTagAt(var8).getShort("id");
               short var10 = var6.getCompoundTagAt(var8).getShort("lvl");
               Enchantment var11 = Enchantment.getEnchantmentByID(var9);
               if (var11 != null) {
                  var7.add(var11.getTranslatedName(var10));
               }
            }
         }

         if (var7.contains("Blast Protection IV")) {
            var3 = true;
         }
      }

      return var1 && var2 == 4 && var3;
   }

   public String getHudInfo() {
      int var1 = 0;

      for(int var2 = 45; var2 > -1; --var2) {
         if (mc.player.inventory.getStackInSlot(var2).getItem() == Items.TOTEM_OF_UNDYING) {
            var1 += mc.player.inventory.getStackInSlot(var2).getCount();
         }
      }

      return String.valueOf((new StringBuilder()).append(var1).append(""));
   }

   public void onUpdate() {
      if (!switchInProgress) {
         if (!(mc.currentScreen instanceof GuiContainer)) {
            if (offhand_delay > 0) {
               --offhand_delay;
            }

            if (this.shouldTotem() && (mc.player.getHeldItemOffhand() == ItemStack.EMPTY || mc.player.getHeldItemOffhand().getItem() != Items.TOTEM_OF_UNDYING)) {
               int var1 = this.getTotemSlot() < 9 ? this.getTotemSlot() + 36 : this.getTotemSlot();
               if (this.getTotemSlot() != -1) {
                  this.slot = var1;
                  switchInProgress = true;
                  offhand_delay = (Integer)this.delay.getValue();
               }
            }

         }
      }
   }

   @EventTarget
   public void onPlayerUpdate(LocalPlayerUpdateEvent var1) {
      if (switchInProgress) {
         if (this.switchDelay1 > 0) {
            --this.switchDelay1;
         }

         if (this.switchDelay2 > 0) {
            --this.switchDelay2;
         }

         if (!this.didFirst) {
            mc.playerController.windowClick(0, this.slot, 0, ClickType.PICKUP, mc.player);
            this.switchDelay1 = (Integer)this.delayA.getValue();
            this.didFirst = true;
            return;
         }

         if (this.switchDelay1 == 0) {
            mc.playerController.windowClick(0, 45, 0, ClickType.PICKUP, mc.player);
            this.switchDelay1 = -1;
            this.switchDelay2 = (Integer)this.delayA.getValue();
            return;
         }

         if (this.switchDelay2 == 0) {
            mc.playerController.windowClick(0, this.slot, 0, ClickType.PICKUP, mc.player);
            this.switchDelay1 = -1;
            this.switchDelay2 = -1;
            this.slot = -1;
            switchInProgress = false;
            this.didFirst = false;
         }
      }

   }

   public AutoTotem() {
      super("AutoTotem", "Automatically places totems in your offhand", 0, Category.COMBAT, true);
   }

   int getTotemSlot() {
      int var1 = -1;

      for(int var2 = 45; var2 > -1; --var2) {
         if (mc.player.inventory.getStackInSlot(var2).getItem() == Items.TOTEM_OF_UNDYING) {
            var1 = var2;
            break;
         }
      }

      return var1;
   }

   private boolean shouldTotem() {
      return mc.player.getHealth() + mc.player.getAbsorptionAmount() <= (float)((Boolean)((Offhand)Xulu.MODULE_MANAGER.getModuleT(Offhand.class)).conserveGapples.getValue() ? (!Surround.isExposed() && isFullArmor(mc.player) ? 6 : (Integer)this.health.getValue()) : (Integer)this.health.getValue()) || mc.player.getItemStackFromSlot(EntityEquipmentSlot.CHEST).getItem() == Items.ELYTRA;
   }
}
