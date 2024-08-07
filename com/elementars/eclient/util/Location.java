package com.elementars.eclient.util;

import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;

public class Location {
   // $FF: synthetic field
   private double y;
   // $FF: synthetic field
   private double x;
   // $FF: synthetic field
   private boolean ground;
   // $FF: synthetic field
   private double z;

   public Location(double var1, double var3, double var5) {
      this.x = var1;
      this.y = var3;
      this.z = var5;
      this.ground = true;
   }

   public BlockPos toBlockPos() {
      return new BlockPos(this.getX(), this.getY(), this.getZ());
   }

   public Block getBlock() {
      return Wrapper.getMinecraft().world.getBlockState(this.toBlockPos()).getBlock();
   }

   public Location setOnGround(boolean var1) {
      this.ground = var1;
      return this;
   }

   public double getY() {
      return this.y;
   }

   public Location add(double var1, double var3, double var5) {
      this.x += var1;
      this.y += var3;
      this.z += var5;
      return this;
   }

   public Location setY(double var1) {
      this.y = var1;
      return this;
   }

   public double getZ() {
      return this.z;
   }

   public static Location fromBlockPos(BlockPos var0) {
      return new Location(var0.getX(), var0.getY(), var0.getZ());
   }

   public Location(int var1, int var2, int var3) {
      this.x = (double)var1;
      this.y = (double)var2;
      this.z = (double)var3;
      this.ground = true;
   }

   public Location add(int var1, int var2, int var3) {
      this.x += (double)var1;
      this.y += (double)var2;
      this.z += (double)var3;
      return this;
   }

   public Location subtract(int var1, int var2, int var3) {
      this.x -= (double)var1;
      this.y -= (double)var2;
      this.z -= (double)var3;
      return this;
   }

   public Location setZ(double var1) {
      this.z = var1;
      return this;
   }

   public Location subtract(double var1, double var3, double var5) {
      this.x -= var1;
      this.y -= var3;
      this.z -= var5;
      return this;
   }

   public Location(double var1, double var3, double var5, boolean var7) {
      this.x = var1;
      this.y = var3;
      this.z = var5;
      this.ground = var7;
   }

   public boolean isOnGround() {
      return this.ground;
   }

   public Location setX(double var1) {
      this.x = var1;
      return this;
   }

   public double getX() {
      return this.x;
   }
}
