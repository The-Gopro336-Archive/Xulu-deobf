package com.elementars.eclient.module.combat;

import com.elementars.eclient.Xulu;
import com.elementars.eclient.module.Category;
import com.elementars.eclient.module.Module;
import dev.xulu.settings.Value;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.network.play.server.SPacketDisconnect;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class AutoLog extends Module {
   // $FF: synthetic field
   private boolean shouldLog = false;
   // $FF: synthetic field
   long lastLog = System.currentTimeMillis();
   // $FF: synthetic field
   private Value health = this.register(new Value("Health", this, 6, 0, 36));

   public void onEnable() {
      super.onEnable();
   }

   public void onDisable() {
      super.onDisable();
   }

   public void onUpdate() {
      if (this.shouldLog) {
         this.shouldLog = false;
         if (System.currentTimeMillis() - this.lastLog < 2000L) {
            return;
         }

         Minecraft.getMinecraft().getConnection().handleDisconnect(new SPacketDisconnect(new TextComponentString("AutoLogged")));
      }

   }

   public AutoLog() {
      super("AutoLog", "Automatically Logs", 0, Category.COMBAT, true);
   }

   @SubscribeEvent
   private void onEntity(EntityJoinWorldEvent var1) {
      if (mc.player != null) {
         if (var1.getEntity() instanceof EntityEnderCrystal && mc.player.getHealth() - AutoCrystal.calculateDamage((EntityEnderCrystal)var1.getEntity(), mc.player) < (float)(Integer)this.health.getValue()) {
            this.log();
         }

      }
   }

   private void log() {
      Xulu.MODULE_MANAGER.getModuleByName("AutoReconnect").disable();
      this.shouldLog = true;
      this.lastLog = System.currentTimeMillis();
   }
}
