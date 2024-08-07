package com.elementars.eclient.event.events;

import com.elementars.eclient.event.Event;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

public class EventClickBlock extends Event {
   // $FF: synthetic field
   BlockPos pos;
   // $FF: synthetic field
   EnumFacing facing;

   public EventClickBlock(BlockPos var1, EnumFacing var2) {
      this.pos = var1;
      this.facing = var2;
   }

   public BlockPos getPos() {
      return this.pos;
   }

   public EnumFacing getFacing() {
      return this.facing;
   }
}
