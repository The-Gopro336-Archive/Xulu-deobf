package com.elementars.eclient.event.events;

import com.elementars.eclient.event.Event;
import net.minecraft.network.play.server.SPacketChunkData;
import net.minecraft.world.chunk.Chunk;

public class ChunkEvent extends Event {
   // $FF: synthetic field
   private SPacketChunkData packet;
   // $FF: synthetic field
   private Chunk chunk;

   public SPacketChunkData getPacket() {
      return this.packet;
   }

   public ChunkEvent(Chunk var1, SPacketChunkData var2) {
      this.chunk = var1;
      this.packet = var2;
   }

   public Chunk getChunk() {
      return this.chunk;
   }
}
