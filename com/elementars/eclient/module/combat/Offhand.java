package com.elementars.eclient.module.combat;

import com.elementars.eclient.event.EventTarget;
import com.elementars.eclient.event.events.LocalPlayerUpdateEvent;
import com.elementars.eclient.module.Category;
import com.elementars.eclient.module.Module;
import com.google.common.collect.Maps;
import dev.xulu.settings.Value;
import java.util.Map;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.init.Items;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.EntityEquipmentSlot;

public class Offhand extends Module {
   // $FF: synthetic field
   int switchDelay1 = -1;
   // $FF: synthetic field
   public static boolean switchInProgress = false;
   // $FF: synthetic field
   public final Value conserveGapples = this.register(new Value("Conserve Gap", this, true));
   // $FF: synthetic field
   int slot = -1;
   // $FF: synthetic field
   private final Value item = this.register(new Value("Item", this, "Crystals", new String[]{"Crystals", "Gapples"}));
   // $FF: synthetic field
   private Map itemMap = Maps.newHashMap();
   // $FF: synthetic field
   private final Value delay = this.register(new Value("Delay", this, 1, 0, 5));
   // $FF: synthetic field
   int switchDelay2 = -1;
   // $FF: synthetic field
   boolean didFirst = false;
   // $FF: synthetic field
   private final Value gapOnSword = this.register(new Value("Gap On Sword", this, false));

   private boolean isOk() {
      return mc.player.getHealth() + mc.player.getAbsorptionAmount() > (float)(Integer)((AutoTotem)getModuleT(AutoTotem.class)).health.getValue() && mc.player.getItemStackFromSlot(EntityEquipmentSlot.CHEST).getItem() != Items.ELYTRA;
   }

   public String getHudInfo() {
      int var1 = 0;

      for(int var2 = 45; var2 > -1; --var2) {
         if (mc.player.inventory.getStackInSlot(var2).getItem() == this.itemMap.get(this.item.getValue())) {
            var1 += mc.player.inventory.getStackInSlot(var2).getCount();
         }
      }

      return String.valueOf((new StringBuilder()).append(var1).append(""));
   }

   public Offhand() {
      super("Offhand", "Automatically places items in your offhand", 0, Category.COMBAT, true);
      this.itemMap.put("Crystals", Items.END_CRYSTAL);
      this.itemMap.put("Gapples", Items.GOLDEN_APPLE);
   }

   int getSlot() {
      int var1 = -1;

      for(int var2 = 45; var2 > -1; --var2) {
         if (mc.player.inventory.getStackInSlot(var2).getItem() == this.itemMap.get(this.getItem())) {
            var1 = var2;
            break;
         }
      }

      return var1;
   }

   public void onUpdate() {
      if (!switchInProgress) {
         if (!(mc.currentScreen instanceof GuiContainer)) {
            if (AutoTotem.offhand_delay == 0) {
               if (this.isOk() && mc.player.getHeldItemOffhand().getItem() != this.itemMap.get(this.getItem())) {
                  int var1 = this.getSlot() < 9 ? this.getSlot() + 36 : this.getSlot();
                  if (this.getSlot() != -1) {
                     this.slot = var1;
                     switchInProgress = true;
                  }
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
            this.switchDelay1 = (Integer)this.delay.getValue();
            this.didFirst = true;
            return;
         }

         if (this.switchDelay1 == 0) {
            mc.playerController.windowClick(0, 45, 0, ClickType.PICKUP, mc.player);
            this.switchDelay1 = -1;
            this.switchDelay2 = (Integer)this.delay.getValue();
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

   String getItem() {
      return (Boolean)this.gapOnSword.getValue() && mc.player.getHeldItemMainhand().getItem() == Items.DIAMOND_SWORD ? "Gapples" : (String)this.item.getValue();
   }
}
