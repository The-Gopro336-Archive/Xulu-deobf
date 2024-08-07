package com.elementars.eclient.event.events;

import com.elementars.eclient.event.Event;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class AddCollisionBoxToListEvent extends Event {
   // $FF: synthetic field
   private final IBlockState state;
   // $FF: synthetic field
   private final List collidingBoxes;
   // $FF: synthetic field
   private final boolean bool;
   // $FF: synthetic field
   private final Block block;
   // $FF: synthetic field
   private final AxisAlignedBB entityBox;
   // $FF: synthetic field
   private final World world;
   // $FF: synthetic field
   private final BlockPos pos;
   // $FF: synthetic field
   private final Entity entity;

   public Block getBlock() {
      return this.block;
   }

   public List getCollidingBoxes() {
      return this.collidingBoxes;
   }

   public AxisAlignedBB getEntityBox() {
      return this.entityBox;
   }

   public Entity getEntity() {
      return this.entity;
   }

   public AddCollisionBoxToListEvent(Block var1, IBlockState var2, World var3, BlockPos var4, AxisAlignedBB var5, List var6, Entity var7, boolean var8) {
      this.block = var1;
      this.state = var2;
      this.world = var3;
      this.pos = var4;
      this.entityBox = var5;
      this.collidingBoxes = var6;
      this.entity = var7;
      this.bool = var8;
   }

   public IBlockState getState() {
      return this.state;
   }

   public World getWorld() {
      return this.world;
   }

   public boolean isBool() {
      return this.bool;
   }

   public BlockPos getPos() {
      return this.pos;
   }
}
