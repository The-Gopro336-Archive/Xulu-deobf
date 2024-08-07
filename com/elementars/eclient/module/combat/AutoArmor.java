package com.elementars.eclient.module.combat;

import com.elementars.eclient.module.Category;
import com.elementars.eclient.module.Module;
import com.elementars.eclient.util.Timer;
import dev.xulu.settings.Value;
import java.util.Arrays;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.InventoryEffectRenderer;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;

public class AutoArmor extends Module {
   // $FF: synthetic field
   private Value pif = this.register(new Value("Pickup If Full", this, true));
   // $FF: synthetic field
   private Timer timer = new Timer();
   // $FF: synthetic field
   private Value preserve = this.register(new Value("Preserve Damaged", this, false));
   // $FF: synthetic field
   private int[] bestArmorDamage;
   // $FF: synthetic field
   private Value ms = this.register(new Value("MS delay", this, 500, 0, 1000));
   // $FF: synthetic field
   private int[] bestArmorSlots;
   // $FF: synthetic field
   private Value preserveDMG = this.register(new Value("Damage %", this, 5, 0, 100));
   // $FF: synthetic field
   private Value replace = this.register(new Value("Replace Empty", this, true));

   public void onUpdate() {
      if (!(mc.currentScreen instanceof GuiContainer) || mc.currentScreen instanceof InventoryEffectRenderer) {
         this.searchSlots();

         for(int var1 = 0; var1 < 4; ++var1) {
            if (this.bestArmorSlots[var1] != -1) {
               int var2 = this.bestArmorSlots[var1];
               if (var2 < 9) {
                  var2 += 36;
               }

               if (!mc.player.inventory.armorItemInSlot(var1).isEmpty()) {
                  if (mc.player.inventory.getFirstEmptyStack() == -1 && !AutoTotem.switchInProgress && !Offhand.switchInProgress && !MiddleClickPearl.switchInProgress && (Boolean)this.pif.getValue()) {
                     mc.playerController.windowClick(mc.player.inventoryContainer.windowId, 8 - var1, 0, ClickType.PICKUP, mc.player);
                     mc.playerController.windowClick(mc.player.inventoryContainer.windowId, var2, 0, ClickType.PICKUP, mc.player);
                     mc.playerController.windowClick(mc.player.inventoryContainer.windowId, 8 - var1, 0, ClickType.PICKUP, mc.player);
                     continue;
                  }

                  mc.playerController.windowClick(mc.player.inventoryContainer.windowId, 8 - var1, 0, ClickType.QUICK_MOVE, mc.player);
                  if (!this.timer.hasReached((long)(Integer)this.ms.getValue())) {
                     return;
                  }
               }

               mc.playerController.windowClick(mc.player.inventoryContainer.windowId, var2, 0, ClickType.QUICK_MOVE, mc.player);
               this.timer.reset();
            }
         }

      }
   }

   private void searchSlots() {
      this.bestArmorDamage = new int[4];
      this.bestArmorSlots = new int[4];
      Arrays.fill(this.bestArmorDamage, -1);
      Arrays.fill(this.bestArmorSlots, -1);

      int var1;
      ItemStack var2;
      ItemArmor var3;
      for(var1 = 0; var1 < this.bestArmorSlots.length; ++var1) {
         var2 = mc.player.inventory.armorItemInSlot(var1);
         if (var2.getItem() instanceof ItemArmor) {
            var3 = (ItemArmor)var2.getItem();
            if ((Boolean)this.preserve.getValue()) {
               float var4 = ((float)var2.getMaxDamage() - (float)var2.getItemDamage()) / (float)var2.getMaxDamage();
               int var5 = (int)(var4 * 100.0F);
               if (var5 > (Integer)this.preserveDMG.getValue()) {
                  this.bestArmorDamage[var1] = var3.damageReduceAmount;
               }
            } else {
               this.bestArmorDamage[var1] = var3.damageReduceAmount;
            }
         } else if (var2.isEmpty() && !(Boolean)this.replace.getValue()) {
            this.bestArmorDamage[var1] = Integer.MAX_VALUE;
         }
      }

      for(var1 = 0; var1 < 36; ++var1) {
         var2 = mc.player.inventory.getStackInSlot(var1);
         if (var2.getCount() <= 1 && var2.getItem() instanceof ItemArmor) {
            var3 = (ItemArmor)var2.getItem();
            int var7 = var3.armorType.ordinal() - 2;
            if (this.bestArmorDamage[var7] < var3.damageReduceAmount) {
               this.bestArmorDamage[var7] = var3.damageReduceAmount;
               this.bestArmorSlots[var7] = var1;
            }
         }
      }

   }

   public AutoArmor() {
      super("AutoArmor", "Automatically refills armor", 0, Category.COMBAT, true);
   }
}
