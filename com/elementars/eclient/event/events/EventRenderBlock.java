package com.elementars.eclient.event.events;

import com.elementars.eclient.event.Event;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class EventRenderBlock extends Event {
   // $FF: synthetic field
   private BlockPos blockPos;
   // $FF: synthetic field
   private boolean renderable;
   // $FF: synthetic field
   private boolean checkSides;
   // $FF: synthetic field
   private IBlockState blockState;
   // $FF: synthetic field
   private BufferBuilder bufferBuilder;
   // $FF: synthetic field
   private long rand;
   // $FF: synthetic field
   private IBakedModel bakedModel;
   // $FF: synthetic field
   private IBlockAccess blockAccess;

   public void setBufferBuilder(BufferBuilder var1) {
      this.bufferBuilder = var1;
   }

   public boolean isCheckSides() {
      return this.checkSides;
   }

   public EventRenderBlock(IBlockAccess var1, IBakedModel var2, IBlockState var3, BlockPos var4, BufferBuilder var5, boolean var6, long var7) {
      this.blockAccess = var1;
      this.bakedModel = var2;
      this.blockState = var3;
      this.blockPos = var4;
      this.bufferBuilder = var5;
      this.checkSides = var6;
      this.rand = var7;
   }

   public void setCheckSides(boolean var1) {
      this.checkSides = var1;
   }

   public void setBlockState(IBlockState var1) {
      this.blockState = var1;
   }

   public void setRenderable(boolean var1) {
      this.renderable = var1;
   }

   public void setBlockPos(BlockPos var1) {
      this.blockPos = var1;
   }

   public void setRand(long var1) {
      this.rand = var1;
   }

   public BlockPos getBlockPos() {
      return this.blockPos;
   }

   public IBlockState getBlockState() {
      return this.blockState;
   }

   public void setBakedModel(IBakedModel var1) {
      this.bakedModel = var1;
   }

   public IBlockAccess getBlockAccess() {
      return this.blockAccess;
   }

   public long getRand() {
      return this.rand;
   }

   public boolean isRenderable() {
      return this.renderable;
   }

   public BufferBuilder getBufferBuilder() {
      return this.bufferBuilder;
   }

   public void setBlockAccess(IBlockAccess var1) {
      this.blockAccess = var1;
   }

   public IBakedModel getBakedModel() {
      return this.bakedModel;
   }
}
