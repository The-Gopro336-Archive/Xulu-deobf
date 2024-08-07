package com.elementars.eclient.module.movement;

import com.elementars.eclient.event.EventTarget;
import com.elementars.eclient.event.events.LocalPlayerUpdateEvent;
import com.elementars.eclient.module.Category;
import com.elementars.eclient.module.Module;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;

public class HoleTP extends Module {
   // $FF: synthetic field
   private boolean wasOnGround = false;
   // $FF: synthetic field
   private final BlockPos[] xd = new BlockPos[]{new BlockPos(1, 0, 0), new BlockPos(-1, 0, 0), new BlockPos(0, 0, 1), new BlockPos(0, 0, -1), new BlockPos(0, -1, 0)};
   // $FF: synthetic field
   private static final float DEFAULT_STEP_HEIGHT = 0.6F;
   // $FF: synthetic field
   float lastStep;

   private void unstep(EntityPlayer var1) {
      if (this.shouldUnstep(new BlockPos(var1.getPositionVector()))) {
         AxisAlignedBB var2 = var1.getEntityBoundingBox().expand(0.0D, -1.2000000476837158D, 0.0D).contract(0.0D, (double)var1.height, 0.0D);
         if (var1.world.collidesWithAnyBlock(var2)) {
            List var3 = var1.world.getCollisionBoxes(var1, var2);
            AtomicReference var4 = new AtomicReference(0.0D);
            var3.forEach((var1x) -> {
               var4.set(Math.max((Double)var4.get(), var1x.maxY));
            });
            var1.setPositionAndUpdate(var1.posX, (Double)var4.get(), var1.posZ);
         }
      }
   }

   private boolean shouldUnstep(BlockPos var1) {
      boolean var2 = true;
      BlockPos[] var3 = this.xd;
      int var4 = var3.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         BlockPos var6 = var3[var5];
         if (mc.world.getBlockState(var1.add(var6)).getBlock() != Blocks.BEDROCK && mc.world.getBlockState(var1.add(var6)).getBlock() != Blocks.OBSIDIAN) {
            var2 = false;
         }
      }

      return var2;
   }

   private void updateStepHeight(EntityPlayer var1) {
      var1.stepHeight = var1.onGround ? 1.2F : 0.6F;
   }

   private void updateUnstep(EntityPlayer var1) {
      try {
         if (this.wasOnGround && !var1.onGround && var1.motionY <= 0.0D) {
            this.unstep(var1);
         }
      } finally {
         this.wasOnGround = var1.onGround;
      }

   }

   public HoleTP() {
      super("HoleTP", "Reverse step for holes", 0, Category.MOVEMENT, true);
   }

   @EventTarget
   public void onLocalPlayerUpdate(LocalPlayerUpdateEvent var1) {
      EntityPlayer var2 = (EntityPlayer)var1.getEntityLivingBase();
      if (var2 != null) {
         this.updateUnstep(var2);
      }
   }

   public void onDisable() {
      if (mc.player != null) {
         mc.player.stepHeight = 0.6F;
      }

      if (mc.player != null && mc.player.getRidingEntity() != null) {
         mc.player.getRidingEntity().stepHeight = 1.0F;
      }

   }

   public void onEnable() {
      if (mc.player != null) {
         this.wasOnGround = mc.player.onGround;
      }

   }
}
