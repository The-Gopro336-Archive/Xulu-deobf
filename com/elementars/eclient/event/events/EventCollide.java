package com.elementars.eclient.event.events;

import com.elementars.eclient.event.Event;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.AxisAlignedBB;

public class EventCollide extends Event {
   // $FF: synthetic field
   private AxisAlignedBB boundingBox;
   // $FF: synthetic field
   private double posY;
   // $FF: synthetic field
   private Block block;
   // $FF: synthetic field
   private double posZ;
   // $FF: synthetic field
   private double posX;
   // $FF: synthetic field
   private Entity entity;

   public EventCollide(Entity var1, double var2, double var4, double var6, AxisAlignedBB var8, Block var9) {
      this.entity = var1;
      this.posX = var2;
      this.posY = var4;
      this.posZ = var6;
      this.boundingBox = var8;
      this.block = var9;
   }

   public Block getBlock() {
      return this.block;
   }

   public Entity getEntity() {
      return this.entity;
   }

   public double getPosX() {
      return this.posX;
   }

   public double getPosY() {
      return this.posY;
   }

   public double getPosZ() {
      return this.posZ;
   }

   public void setBoundingBox(AxisAlignedBB var1) {
      this.boundingBox = var1;
   }

   public AxisAlignedBB getBoundingBox() {
      return this.boundingBox;
   }
}
