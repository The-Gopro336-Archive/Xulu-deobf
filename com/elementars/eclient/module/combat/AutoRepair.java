package com.elementars.eclient.module.combat;

import com.elementars.eclient.Xulu;
import com.elementars.eclient.command.Command;
import com.elementars.eclient.module.Category;
import com.elementars.eclient.module.Module;
import com.elementars.eclient.util.BlockInteractionHelper;
import dev.xulu.settings.Value;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.init.Items;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.ItemExpBottle;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;

public class AutoRepair extends Module {
   // $FF: synthetic field
   private boolean shouldArmor;
   // $FF: synthetic field
   private int mostDamagedSlot;
   // $FF: synthetic field
   private int wait;
   // $FF: synthetic field
   private final Value damage = this.register(new Value("Heal Damage %", this, 60, 10, 90));
   // $FF: synthetic field
   private final Value delay = this.register(new Value("Delay", this, 16, 12, 24));
   // $FF: synthetic field
   private int mostDamage;
   // $FF: synthetic field
   private boolean falg;
   // $FF: synthetic field
   private int[] slots;
   // $FF: synthetic field
   private int armorCount;
   // $FF: synthetic field
   private int counter;
   // $FF: synthetic field
   private boolean shouldThrow;
   // $FF: synthetic field
   private int lastSlot;

   private static Map getInventorySlots(int var0, int var1) {
      HashMap var2;
      for(var2 = new HashMap(); var0 <= var1; ++var0) {
         var2.put(var0, (ItemStack)mc.player.inventoryContainer.getInventory().get(var0));
      }

      return var2;
   }

   public void onDisable() {
      if (this.falg) {
         Xulu.MODULE_MANAGER.getModuleByName("EXPFast").toggle();
      }

   }

   public boolean isSpace() {
      int var1 = 0;
      Iterator var2 = getInventory().entrySet().iterator();

      while(var2.hasNext()) {
         Entry var3 = (Entry)var2.next();
         ItemStack var4 = (ItemStack)var3.getValue();
         if (var4.getItem() == Items.AIR) {
            this.slots[var1] = (Integer)var3.getKey();
            ++var1;
            if (var1 > 2) {
               return true;
            }
         }
      }

      return false;
   }

   private static Map getInventory() {
      return getInventorySlots(9, 44);
   }

   public int findXP() {
      this.lastSlot = mc.player.inventory.currentItem;
      int var1 = -1;

      for(int var2 = 0; var2 < 9; ++var2) {
         ItemStack var3 = mc.player.inventory.getStackInSlot(var2);
         if (var3 != ItemStack.EMPTY && var3.getItem() instanceof ItemExpBottle) {
            var1 = var2;
            break;
         }
      }

      if (var1 == -1) {
         Command.sendChatMessage("No EXP in hotbar!");
         this.disable();
         return 1;
      } else {
         return var1;
      }
   }

   public AutoRepair() {
      super("AutoRepair", "Automatically repairs your armor", 0, Category.COMBAT, true);
   }

   public void onUpdate() {
      if (mc.player != null && this.isToggled() && !(mc.currentScreen instanceof GuiContainer)) {
         if (this.shouldThrow) {
            BlockInteractionHelper.lookAtBlock(new BlockPos(mc.player.getPosition().add(0, -1, 0)));
            mc.player.inventory.currentItem = this.findXP();
            mc.playerController.processRightClick(mc.player, mc.world, EnumHand.MAIN_HAND);
            if (this.isRepaired() || this.counter > 40) {
               this.shouldThrow = false;
               this.shouldArmor = true;
               mc.player.inventory.currentItem = this.lastSlot;
               Command.sendChatMessage("Finished Repairing");
            }

            ++this.counter;
         }

         if (this.shouldArmor) {
            if (this.wait >= (Integer)this.delay.getValue()) {
               this.wait = 0;
               mc.playerController.windowClick(0, this.slots[this.armorCount], 0, ClickType.QUICK_MOVE, mc.player);
               mc.playerController.updateController();
               ++this.armorCount;
               if (this.armorCount > 2) {
                  this.armorCount = 0;
                  this.shouldArmor = false;
                  this.disable();
                  return;
               }
            }

            ++this.wait;
         }

      }
   }

   public int getMostDamagedSlot() {
      Iterator var1 = getArmor().entrySet().iterator();

      while(var1.hasNext()) {
         Entry var2 = (Entry)var1.next();
         ItemStack var3 = (ItemStack)var2.getValue();
         if (var3.getItemDamage() > this.mostDamage) {
            this.mostDamage = var3.getItemDamage();
            this.mostDamagedSlot = (Integer)var2.getKey();
         }
      }

      return this.mostDamagedSlot;
   }

   public void onEnable() {
      this.falg = false;
      this.mostDamage = -1;
      this.mostDamagedSlot = -1;
      this.shouldArmor = false;
      this.armorCount = 0;
      this.slots = new int[3];
      this.wait = 0;
      this.takeOffArmor();
      if (Xulu.MODULE_MANAGER.getModuleByName("EXPFast").isToggled()) {
         this.falg = true;
         Xulu.MODULE_MANAGER.getModuleByName("EXPFast").disable();
      }

   }

   public boolean isRepaired() {
      Iterator var1 = getArmor().entrySet().iterator();

      Entry var2;
      ItemStack var3;
      do {
         if (!var1.hasNext()) {
            return false;
         }

         var2 = (Entry)var1.next();
         var3 = (ItemStack)var2.getValue();
      } while((Integer)var2.getKey() != this.mostDamagedSlot);

      float var4 = (float)(Integer)this.damage.getValue() / 100.0F;
      int var5 = Math.round((float)var3.getMaxDamage() * var4);
      int var6 = var3.getMaxDamage() - var3.getItemDamage();
      if (var5 <= var6) {
         return true;
      } else {
         return false;
      }
   }

   public void takeOffArmor() {
      if (this.isSpace()) {
         this.getMostDamagedSlot();
         if (this.mostDamagedSlot != -1) {
            Iterator var1 = getArmor().entrySet().iterator();

            while(var1.hasNext()) {
               Entry var2 = (Entry)var1.next();
               if ((Integer)var2.getKey() != this.mostDamagedSlot) {
                  mc.playerController.windowClick(0, (Integer)var2.getKey(), 0, ClickType.QUICK_MOVE, mc.player);
               }
            }

            this.counter = 0;
            this.shouldThrow = true;
            return;
         }
      }

      Command.sendChatMessage("Please ensure there is atleast 3 inv slots open!");
      this.disable();
   }

   private static Map getArmor() {
      return getInventorySlots(5, 8);
   }
}
