package com.elementars.eclient.module.player;

import com.elementars.eclient.module.Category;
import com.elementars.eclient.module.Module;
import com.elementars.eclient.util.Pair;
import dev.xulu.settings.Value;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.block.Block;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.init.Items;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class AutoReplenish extends Module {
   // $FF: synthetic field
   Value tickdelay = this.register(new Value("Delay", this, 2, 1, 10));
   // $FF: synthetic field
   private int delay_step = 0;
   // $FF: synthetic field
   Value threshold = this.register(new Value("Threshold", this, 32, 1, 63));
   // $FF: synthetic field
   Value mode = this.register(new Value("Mode", this, "All", new String[]{"All", "Crystals", "Xp", "Both"}));

   private Pair findReplenishableHotbarSlot() {
      Pair var1 = null;
      Iterator var2 = this.get_hotbar().entrySet().iterator();

      while(true) {
         Entry var3;
         ItemStack var4;
         label68:
         while(true) {
            do {
               do {
                  do {
                     if (!var2.hasNext()) {
                        return var1;
                     }

                     var3 = (Entry)var2.next();
                     var4 = (ItemStack)var3.getValue();
                  } while(var4.isEmpty);
               } while(var4.getItem() == Items.AIR);
            } while(!var4.isStackable());

            String var5 = (String)this.mode.getValue();
            byte var6 = -1;
            switch(var5.hashCode()) {
            case -1820702691:
               if (var5.equals("Crystals")) {
                  var6 = 0;
               }
               break;
            case 2840:
               if (var5.equals("Xp")) {
                  var6 = 1;
               }
               break;
            case 2076577:
               if (var5.equals("Both")) {
                  var6 = 2;
               }
            }

            switch(var6) {
            case 0:
               if (var4.getItem() != Items.END_CRYSTAL) {
                  break;
               }
               break label68;
            case 1:
               if (var4.getItem() != Items.EXPERIENCE_BOTTLE) {
                  break;
               }
               break label68;
            case 2:
               if (var4.getItem() != Items.END_CRYSTAL || var4.getItem() != Items.EXPERIENCE_BOTTLE) {
                  break;
               }
            default:
               break label68;
            }
         }

         if (var4.stackSize < var4.getMaxStackSize() && var4.stackSize <= (Integer)this.threshold.getValue()) {
            int var7 = this.findCompatibleInventorySlot(var4);
            if (var7 != -1) {
               var1 = new Pair(var7, var3.getKey());
            }
         }
      }
   }

   private Map get_inventory() {
      return this.get_inv_slots(9, 35);
   }

   private int findCompatibleInventorySlot(ItemStack var1) {
      int var2 = -1;
      int var3 = 999;
      Iterator var4 = this.get_inventory().entrySet().iterator();

      while(var4.hasNext()) {
         Entry var5 = (Entry)var4.next();
         ItemStack var6 = (ItemStack)var5.getValue();
         if (!var6.isEmpty && var6.getItem() != Items.AIR && this.isCompatibleStacks(var1, var6)) {
            int var7 = ((ItemStack)mc.player.inventoryContainer.getInventory().get((Integer)var5.getKey())).stackSize;
            if (var3 > var7) {
               var3 = var7;
               var2 = (Integer)var5.getKey();
            }
         }
      }

      return var2;
   }

   public void onUpdate() {
      if (!(mc.currentScreen instanceof GuiContainer)) {
         if (this.delay_step < (Integer)this.tickdelay.getValue()) {
            ++this.delay_step;
         } else {
            this.delay_step = 0;
            Pair var1 = this.findReplenishableHotbarSlot();
            if (var1 != null) {
               int var2 = (Integer)var1.getKey();
               int var3 = (Integer)var1.getValue();
               mc.playerController.windowClick(0, var2, 0, ClickType.PICKUP, mc.player);
               mc.playerController.windowClick(0, var3, 0, ClickType.PICKUP, mc.player);
               mc.playerController.windowClick(0, var2, 0, ClickType.PICKUP, mc.player);
               mc.playerController.updateController();
            }
         }
      }
   }

   public AutoReplenish() {
      super("AutoReplenish", "Automatically replenishes stacks in the hotbar", 0, Category.PLAYER, true);
   }

   private Map get_hotbar() {
      return this.get_inv_slots(36, 44);
   }

   private Map get_inv_slots(int var1, int var2) {
      HashMap var3;
      for(var3 = new HashMap(); var1 <= var2; ++var1) {
         var3.put(var1, (ItemStack)mc.player.inventoryContainer.getInventory().get(var1));
      }

      return var3;
   }

   private boolean isCompatibleStacks(ItemStack var1, ItemStack var2) {
      if (!var1.getItem().equals(var2.getItem())) {
         return false;
      } else {
         if (var1.getItem() instanceof ItemBlock && var2.getItem() instanceof ItemBlock) {
            Block var3 = ((ItemBlock)var1.getItem()).getBlock();
            Block var4 = ((ItemBlock)var2.getItem()).getBlock();
            if (!var3.material.equals(var4.material)) {
               return false;
            }
         }

         return var1.getDisplayName().equals(var2.getDisplayName()) && var1.getItemDamage() == var2.getItemDamage();
      }
   }
}
