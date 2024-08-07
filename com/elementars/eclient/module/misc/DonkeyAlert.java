package com.elementars.eclient.module.misc;

import com.elementars.eclient.command.Command;
import com.elementars.eclient.module.Category;
import com.elementars.eclient.module.Module;
import net.minecraft.entity.passive.EntityDonkey;

public class DonkeyAlert extends Module {
   // $FF: synthetic field
   int delay;

   public void onUpdate() {
      if (this.delay > 0) {
         --this.delay;
      }

      mc.world.loadedEntityList.forEach((var1) -> {
         if (var1 instanceof EntityDonkey && this.delay == 0) {
            Command.sendChatMessage("Donkey spotted!");
            this.delay = 200;
         }

      });
   }

   public DonkeyAlert() {
      super("DonkeyAlert", "Alerts you when a donkey is in your render distance", 0, Category.MISC, true);
   }

   public void onEnable() {
      this.delay = 0;
   }
}
