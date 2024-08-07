package com.elementars.eclient.event.events;

import com.elementars.eclient.event.Event;
import net.minecraft.network.Packet;

public class EventSendPacket extends Event {
   // $FF: synthetic field
   private Packet packet;

   public Packet getPacket() {
      return this.packet;
   }

   public EventSendPacket(Packet var1) {
      this.packet = var1;
   }

   public void setPacket(Packet var1) {
      this.packet = var1;
   }
}
