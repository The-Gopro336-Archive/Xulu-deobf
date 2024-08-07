package com.elementars.eclient.event.events;

import com.elementars.eclient.event.Event;
import net.minecraft.network.Packet;

public class EventReceivePacket extends Event {
   // $FF: synthetic field
   private Packet packet;

   public void setPacket(Packet var1) {
      this.packet = var1;
   }

   public Packet getPacket() {
      return this.packet;
   }

   public EventReceivePacket(Packet var1) {
      this.packet = var1;
   }
}
