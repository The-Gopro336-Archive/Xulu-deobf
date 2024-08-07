package com.elementars.eclient.module.misc;

import com.elementars.eclient.module.Category;
import com.elementars.eclient.module.Module;
import java.util.ArrayList;
import java.util.Iterator;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;

public class HopperNuker extends Module {
   // $FF: synthetic field
   public ArrayList hoppers = new ArrayList();
   // $FF: synthetic field
   int pickaxeSlot;

   public HopperNuker() {
      super("HopperNuker", "Nuker for hoppers", 0, Category.MISC, true);
   }

   public void onUpdate() {
      if (this.isToggled()) {
         Iterable var1 = BlockPos.getAllInBox(mc.player.getPosition().add(-5, -5, -5), mc.player.getPosition().add(5, 5, 5));
         Iterator var2 = var1.iterator();

         while(true) {
            BlockPos var3;
            do {
               if (!var2.hasNext()) {
                  return;
               }

               var3 = (BlockPos)var2.next();
            } while(mc.world.getBlockState(var3).getBlock() != Blocks.HOPPER);

            this.pickaxeSlot = -1;

            for(int var4 = 0; var4 < 9 && this.pickaxeSlot == -1; ++var4) {
               ItemStack var5 = mc.player.inventory.getStackInSlot(var4);
               if (var5 != ItemStack.EMPTY && var5.getItem() instanceof ItemPickaxe) {
                  ItemPickaxe var6 = (ItemPickaxe)var5.getItem();
                  this.pickaxeSlot = var4;
               }
            }

            if (this.pickaxeSlot != -1) {
               mc.player.inventory.currentItem = this.pickaxeSlot;
            }

            mc.playerController.onPlayerDamageBlock(var3, mc.player.getHorizontalFacing());
            mc.player.swingArm(EnumHand.MAIN_HAND);
         }
      }
   }
}
