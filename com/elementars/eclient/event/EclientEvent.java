package com.elementars.eclient.event;

import com.elementars.eclient.util.Wrapper;

public class EclientEvent {
   // $FF: synthetic field
   private final float partialTicks;
   // $FF: synthetic field
   private EclientEvent.Era era;

   public float getPartialTicks() {
      return this.partialTicks;
   }

   public EclientEvent() {
      this.era = EclientEvent.Era.PRE;
      this.partialTicks = Wrapper.getMinecraft().getRenderPartialTicks();
   }

   public EclientEvent.Era getEra() {
      return this.era;
   }

   public static enum Era {
      // $FF: synthetic field
      POST,
      // $FF: synthetic field
      PERI,
      // $FF: synthetic field
      PRE;
   }
}
