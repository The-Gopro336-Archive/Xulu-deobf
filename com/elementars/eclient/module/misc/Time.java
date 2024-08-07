package com.elementars.eclient.module.misc;

import com.elementars.eclient.event.EventTarget;
import com.elementars.eclient.event.events.EventReceivePacket;
import com.elementars.eclient.module.Category;
import com.elementars.eclient.module.Module;
import dev.xulu.settings.Value;
import net.minecraft.network.play.server.SPacketTimeUpdate;

public class Time extends Module {
   // $FF: synthetic field
   long oldTime;
   // $FF: synthetic field
   private final Value time = this.register(new Value("Time", this, 0L, 0L, 24000L));

   @EventTarget
   public void onTime(EventReceivePacket var1) {
      if (var1.getPacket() instanceof SPacketTimeUpdate) {
         this.oldTime = ((SPacketTimeUpdate)var1.getPacket()).getWorldTime();
         var1.setCancelled(true);
      }

   }

   public void onEnable() {
      this.oldTime = mc.world.getWorldTime();
   }

   public void onDisable() {
      mc.world.setWorldTime(this.oldTime);
   }

   public void onUpdate() {
      if (mc.world != null) {
         mc.world.setWorldTime((Long)this.time.getValue());
      }
   }

   public Time() {
      super("Time", "Clientside sets the time", 0, Category.MISC, true);
   }
}
