package com.elementars.eclient.event.events;

import com.elementars.eclient.event.Event;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;

public class CollisionBoxEvent extends Event {
   // $FF: synthetic field
   private AxisAlignedBB aabb;
   // $FF: synthetic field
   private final Block block;
   // $FF: synthetic field
   private final BlockPos pos;
   // $FF: synthetic field
   private final List collidingBoxes;
   // $FF: synthetic field
   private final Entity entity;

   public void setAABB(AxisAlignedBB var1) {
      this.aabb = var1;
   }

   public final Block getBlock() {
      return this.block;
   }

   public final BlockPos getPos() {
      return this.pos;
   }

   public final List getCollidingBoxes() {
      return this.collidingBoxes;
   }

   public CollisionBoxEvent(Block var1, BlockPos var2, AxisAlignedBB var3, List var4, @Nullable Entity var5) {
      this.block = var1;
      this.pos = var2;
      this.aabb = var3;
      this.collidingBoxes = var4;
      this.entity = var5;
   }

   public final AxisAlignedBB getBoundingBox() {
      return this.aabb;
   }

   public final Entity getEntity() {
      return this.entity;
   }
}
