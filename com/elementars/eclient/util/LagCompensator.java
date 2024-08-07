package com.elementars.eclient.util;

import com.elementars.eclient.Xulu;
import com.elementars.eclient.event.EventTarget;
import com.elementars.eclient.event.events.EventReceivePacket;
import java.util.Arrays;
import java.util.EventListener;
import net.minecraft.network.play.server.SPacketTimeUpdate;
import net.minecraft.util.math.MathHelper;

public class LagCompensator implements EventListener {
   // $FF: synthetic field
   private int nextIndex = 0;
   // $FF: synthetic field
   public static LagCompensator INSTANCE;
   // $FF: synthetic field
   private final float[] tickRates = new float[20];
   // $FF: synthetic field
   private long timeLastTimeUpdate;

   public LagCompensator() {
      Xulu.EVENT_MANAGER.register(this);
      this.reset();
   }

   @EventTarget
   public void onPacket(EventReceivePacket var1) {
      if (var1.getPacket() instanceof SPacketTimeUpdate) {
         INSTANCE.onTimeUpdate();
      }

   }

   public void onTimeUpdate() {
      if (this.timeLastTimeUpdate != -1L) {
         float var1 = (float)(System.currentTimeMillis() - this.timeLastTimeUpdate) / 1000.0F;
         this.tickRates[this.nextIndex % this.tickRates.length] = MathHelper.clamp(20.0F / var1, 0.0F, 20.0F);
         ++this.nextIndex;
      }

      this.timeLastTimeUpdate = System.currentTimeMillis();
   }

   public void reset() {
      this.nextIndex = 0;
      this.timeLastTimeUpdate = -1L;
      Arrays.fill(this.tickRates, 0.0F);
   }

   public float getTickRate() {
      float var1 = 0.0F;
      float var2 = 0.0F;
      float[] var3 = this.tickRates;
      int var4 = var3.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         float var6 = var3[var5];
         if (var6 > 0.0F) {
            var2 += var6;
            ++var1;
         }
      }

      return MathHelper.clamp(var2 / var1, 0.0F, 20.0F);
   }
}
