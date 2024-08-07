package com.elementars.eclient.module.combat;

import com.elementars.eclient.event.EventTarget;
import com.elementars.eclient.event.events.EventMiddleClick;
import com.elementars.eclient.event.events.LocalPlayerUpdateEvent;
import com.elementars.eclient.module.Category;
import com.elementars.eclient.module.Module;
import com.elementars.eclient.module.misc.MCF;
import dev.xulu.settings.Value;
import net.minecraft.block.Block;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.init.Items;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemEnderPearl;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CPacketHeldItemChange;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.RayTraceResult.Type;

public class MiddleClickPearl extends Module {
   // $FF: synthetic field
   private int slot2 = -1;
   // $FF: synthetic field
   int switchDelay2 = -1;
   // $FF: synthetic field
   int time;
   // $FF: synthetic field
   int switchDelay1 = -1;
   // $FF: synthetic field
   public static boolean switchInProgress = false;
   // $FF: synthetic field
   boolean didFirst = false;
   // $FF: synthetic field
   private int slot = -1;
   // $FF: synthetic field
   private final Value delayA = this.register(new Value("Click Delay", this, 1, 0, 5)).visibleWhen((var1) -> {
      return ((String)this.mode.getValue()).equalsIgnoreCase("Switch");
   });
   // $FF: synthetic field
   boolean hasClicked = false;
   // $FF: synthetic field
   private final Value delay = this.register(new Value("Switch Delay", this, 10, 0, 80)).visibleWhen((var1) -> {
      return ((String)this.mode.getValue()).equalsIgnoreCase("Switch");
   });
   // $FF: synthetic field
   boolean isFinishingTask = false;
   // $FF: synthetic field
   boolean isThrowingPearl = false;
   // $FF: synthetic field
   private final Value mode = this.register(new Value("Mode", this, "Switch", new String[]{"Switch", "Instant"}));

   @EventTarget
   public void onMiddleClick(EventMiddleClick var1) {
      if (!(mc.currentScreen instanceof GuiContainer)) {
         RayTraceResult var2 = MCF.mc.objectMouseOver;
         if (var2.typeOfHit != Type.ENTITY) {
            if (var2.typeOfHit != Type.BLOCK) {
               if (((String)this.mode.getValue()).equalsIgnoreCase("Instant")) {
                  this.throwPearl();
               } else {
                  this.slot = this.getSlot() < 9 ? this.getSlot() + 36 : this.getSlot();
                  this.slot2 = this.getSlot2() < 9 ? this.getSlot2() + 36 : this.getSlot2();
                  if (this.getSlot() == -1) {
                     this.sendDebugMessage("No pearl found!");
                  } else {
                     if (this.getSlot() != -1 && this.getSlot2() != -1 && mc.player.getHeldItemMainhand().getItem() != Items.ENDER_PEARL) {
                        mc.addScheduledTask(() -> {
                           switchInProgress = true;
                           this.isThrowingPearl = true;
                        });
                        this.hasClicked = true;
                     }

                  }
               }
            }
         }
      }
   }

   public void onUpdate() {
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
            mc.playerController.windowClick(0, this.slot2, 0, ClickType.PICKUP, mc.player);
            this.switchDelay1 = -1;
            this.switchDelay2 = (Integer)this.delayA.getValue();
            return;
         }

         if (this.switchDelay2 == 0) {
            mc.playerController.windowClick(0, this.slot, 0, ClickType.PICKUP, mc.player);
            this.switchDelay1 = -1;
            this.switchDelay2 = -1;
            switchInProgress = false;
            this.didFirst = false;
            if (this.isThrowingPearl) {
               mc.playerController.processRightClick(mc.player, mc.world, EnumHand.MAIN_HAND);
               mc.player.swingArm(EnumHand.MAIN_HAND);
               this.isThrowingPearl = false;
            }

            if (this.isFinishingTask) {
               this.slot = -1;
               this.slot2 = -1;
               this.time = 0;
               this.hasClicked = false;
               this.isFinishingTask = false;
               mc.playerController.updateController();
            }
         }
      }

   }

   public static int findHotbarBlock(Class var0) {
      for(int var1 = 0; var1 < 9; ++var1) {
         ItemStack var2 = mc.player.inventory.getStackInSlot(var1);
         if (var2 != ItemStack.EMPTY) {
            if (var0.isInstance(var2.getItem())) {
               return var1;
            }

            if (var2.getItem() instanceof ItemBlock) {
               Block var3 = ((ItemBlock)var2.getItem()).getBlock();
               if (var0.isInstance(var3)) {
                  return var1;
               }
            }
         }
      }

      return -1;
   }

   private void throwPearl() {
      int var1 = findHotbarBlock(ItemEnderPearl.class);
      boolean var2 = mc.player.getHeldItemOffhand().getItem() == Items.ENDER_PEARL;
      if (var1 != -1 || var2) {
         int var3 = mc.player.inventory.currentItem;
         if (!var2) {
            switchToHotbarSlot(var1, false);
         }

         mc.playerController.processRightClick(mc.player, mc.world, var2 ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND);
         if (!var2) {
            switchToHotbarSlot(var3, false);
         }
      }

   }

   public static void switchToHotbarSlot(int var0, boolean var1) {
      if (mc.player.inventory.currentItem != var0 && var0 >= 0) {
         if (var1) {
            mc.player.connection.sendPacket(new CPacketHeldItemChange(var0));
            mc.playerController.updateController();
         } else {
            mc.player.connection.sendPacket(new CPacketHeldItemChange(var0));
            mc.player.inventory.currentItem = var0;
            mc.playerController.updateController();
         }

      }
   }

   public MiddleClickPearl() {
      super("MiddleClickPearl", "Throws a pearl without it being in your hotbar", 0, Category.COMBAT, true);
   }

   int getSlot2() {
      int var1 = -1;

      for(int var2 = 45; var2 > -1; --var2) {
         if (mc.player.inventory.getStackInSlot(var2) == mc.player.getHeldItemMainhand()) {
            var1 = var2;
            break;
         }
      }

      return var1;
   }

   public static void switchToHotbarSlot(Class var0, boolean var1) {
      int var2 = findHotbarBlock(var0);
      if (var2 > -1) {
         switchToHotbarSlot(var2, var1);
      }

   }

   int getSlot() {
      int var1 = -1;

      for(int var2 = 45; var2 > -1; --var2) {
         if (mc.player.inventory.getStackInSlot(var2).getItem() == Items.ENDER_PEARL) {
            var1 = var2;
            break;
         }
      }

      return var1;
   }

   @EventTarget
   public void onPlayerUpdate(LocalPlayerUpdateEvent var1) {
      if (mc.player.getHeldItemMainhand().getItem() == Items.ENDER_PEARL && this.hasClicked) {
         ++this.time;
         if (this.time < (Integer)this.delay.getValue()) {
            return;
         }

         mc.addScheduledTask(() -> {
            switchInProgress = true;
            this.isFinishingTask = true;
         });
      } else if (this.getSlot() == -1 && this.hasClicked) {
         mc.addScheduledTask(() -> {
            switchInProgress = true;
            this.isFinishingTask = true;
         });
      }

   }
}
