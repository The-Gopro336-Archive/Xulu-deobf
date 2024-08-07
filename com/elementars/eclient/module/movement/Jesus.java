package com.elementars.eclient.module.movement;

import com.elementars.eclient.event.EventTarget;
import com.elementars.eclient.event.events.AddCollisionBoxToListEvent;
import com.elementars.eclient.event.events.EventSendPacket;
import com.elementars.eclient.module.Category;
import com.elementars.eclient.module.Module;
import com.elementars.eclient.module.ModuleManager;
import com.elementars.eclient.util.EntityUtil;
import com.elementars.eclient.util.Wrapper;
import net.minecraft.block.BlockLiquid;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;

public class Jesus extends Module {
   // $FF: synthetic field
   private static final AxisAlignedBB WATER_WALK_AA = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.99D, 1.0D);

   private static boolean isAboveBlock(Entity var0, BlockPos var1) {
      return var0.posY >= (double)var1.getY();
   }

   private static boolean isAboveLand(Entity var0) {
      if (var0 == null) {
         return false;
      } else {
         double var1 = var0.posY - 0.01D;

         for(int var3 = MathHelper.floor(var0.posX); var3 < MathHelper.ceil(var0.posX); ++var3) {
            for(int var4 = MathHelper.floor(var0.posZ); var4 < MathHelper.ceil(var0.posZ); ++var4) {
               BlockPos var5 = new BlockPos(var3, MathHelper.floor(var1), var4);
               if (Wrapper.getWorld().getBlockState(var5).getBlock().isFullBlock(Wrapper.getWorld().getBlockState(var5))) {
                  return true;
               }
            }
         }

         return false;
      }
   }

   public Jesus() {
      super("Jesus", "Walk on water", 0, Category.MOVEMENT, true);
   }

   @EventTarget
   public void onPacket(EventSendPacket var1) {
      if (var1.getPacket() instanceof CPacketPlayer && EntityUtil.isAboveWater(mc.player, true) && !EntityUtil.isInWater(mc.player) && !isAboveLand(mc.player)) {
         int var2 = mc.player.ticksExisted % 2;
         if (var2 == 0) {
            CPacketPlayer var10000 = (CPacketPlayer)var1.getPacket();
            var10000.y += 0.02D;
         }
      }

   }

   @EventTarget
   public void onCollision(AddCollisionBoxToListEvent var1) {
      if (mc.player != null && var1.getBlock() instanceof BlockLiquid && (EntityUtil.isDrivenByPlayer(var1.getEntity()) || var1.getEntity() == mc.player) && !(var1.getEntity() instanceof EntityBoat) && !mc.player.isSneaking() && mc.player.fallDistance < 3.0F && !EntityUtil.isInWater(mc.player) && (EntityUtil.isAboveWater(mc.player, false) || EntityUtil.isAboveWater(mc.player.getRidingEntity(), false)) && isAboveBlock(mc.player, var1.getPos())) {
         AxisAlignedBB var2 = WATER_WALK_AA.offset(var1.getPos());
         if (var1.getEntityBox().intersects(var2)) {
            var1.getCollidingBoxes().add(var2);
         }

         var1.setCancelled(true);
      }

   }

   public void onUpdate() {
      if (!ModuleManager.isModuleEnabled("Freecam") && EntityUtil.isInWater(mc.player) && !mc.player.isSneaking()) {
         mc.player.motionY = 0.1D;
         if (mc.player.getRidingEntity() != null && !(mc.player.getRidingEntity() instanceof EntityBoat)) {
            mc.player.getRidingEntity().motionY = 0.3D;
         }
      }

   }
}
