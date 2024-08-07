package com.elementars.eclient.event.events;

import com.elementars.eclient.event.Event;
import net.minecraft.world.chunk.Chunk;

public class UnloadChunkEvent extends Event {
   // $FF: synthetic field
   private Chunk chunk;

   public Chunk getChunk() {
      return this.chunk;
   }

   public UnloadChunkEvent(Chunk var1) {
      this.chunk = var1;
   }
}
