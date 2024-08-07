package com.elementars.eclient.module.movement;

import com.elementars.eclient.event.Event;
import com.elementars.eclient.event.EventTarget;
import com.elementars.eclient.event.events.EventSendPacket;
import com.elementars.eclient.event.events.PlayerMoveEvent;
import com.elementars.eclient.module.Category;
import com.elementars.eclient.module.Module;
import com.elementars.eclient.util.BlockInteractionHelper;
import dev.xulu.settings.Value;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class Scaffold extends Module {
   // $FF: synthetic field
   private final Value delay = this.register(new Value("Delay", this, 0, 0, 20));
   // $FF: synthetic field
   int delayT;
   // $FF: synthetic field
   private double yaw;
   // $FF: synthetic field
   private BlockPos block;
   // $FF: synthetic field
   private boolean isSpoofingAngles;
   // $FF: synthetic field
   private double pitch;
   // $FF: synthetic field
   private EnumFacing side;
   // $FF: synthetic field
   private boolean rotated;

   @EventTarget
   public void onSend(EventSendPacket var1) {
      if (var1.getPacket() instanceof CPacketPlayer && this.isSpoofingAngles) {
         ((CPacketPlayer)var1.getPacket()).yaw = (float)this.yaw;
         ((CPacketPlayer)var1.getPacket()).pitch = (float)this.pitch;
      }

   }

   public void onUpdate() {
      if (this.delayT > 0) {
         --this.delayT;
      }

   }

   @EventTarget
   public void onMove(PlayerMoveEvent var1) {
      if (var1.getEventState() == Event.State.PRE) {
         this.rotated = false;
         this.block = null;
         this.side = null;
         BlockPos var2 = (new BlockPos(mc.player.getPositionVector())).add(0, -1, 0);
         if (mc.world.getBlockState(var2).getBlock() == Blocks.AIR) {
            this.setBlockAndFacing(var2);
            if (this.block != null) {
               float[] var3 = BlockInteractionHelper.getDirectionToBlock(this.block.getX(), this.block.getY(), this.block.getZ(), this.side);
               float var4 = var3[0];
               float var5 = Math.min(90.0F, var3[1] + 9.0F);
               this.rotated = true;
               this.yaw = (double)var4;
               this.pitch = (double)var5;
               this.isSpoofingAngles = true;
            }
         }
      }

      if (var1.getEventState() == Event.State.POST && this.block != null && this.delayT == 0 && mc.player.getHeldItemMainhand() != null && mc.player.getHeldItemMainhand().getItem() instanceof ItemBlock && mc.playerController.processRightClickBlock(mc.player, mc.world, this.block, this.side, new Vec3d((double)this.block.getX(), (double)this.block.getY(), (double)this.block.getZ()), EnumHand.MAIN_HAND) == EnumActionResult.SUCCESS) {
         this.delayT = (Integer)this.delay.getValue();
         mc.player.swingArm(EnumHand.MAIN_HAND);
         mc.player.motionX = 0.0D;
         mc.player.motionZ = 0.0D;
      }

   }

   private void setBlockAndFacing(BlockPos var1) {
      if (mc.world.getBlockState(var1.add(0, -1, 0)).getBlock() != Blocks.AIR) {
         this.block = var1.add(0, -1, 0);
         this.side = EnumFacing.UP;
      } else if (mc.world.getBlockState(var1.add(-1, 0, 0)).getBlock() != Blocks.AIR) {
         this.block = var1.add(-1, 0, 0);
         this.side = EnumFacing.EAST;
      } else if (mc.world.getBlockState(var1.add(1, 0, 0)).getBlock() != Blocks.AIR) {
         this.block = var1.add(1, 0, 0);
         this.side = EnumFacing.WEST;
      } else if (mc.world.getBlockState(var1.add(0, 0, -1)).getBlock() != Blocks.AIR) {
         this.block = var1.add(0, 0, -1);
         this.side = EnumFacing.SOUTH;
      } else if (mc.world.getBlockState(var1.add(0, 0, 1)).getBlock() != Blocks.AIR) {
         this.block = var1.add(0, 0, 1);
         this.side = EnumFacing.NORTH;
      } else {
         this.block = null;
         this.side = null;
      }

   }

   public Scaffold() {
      super("Scaffold", "Automatically places blocks below you", 0, Category.MOVEMENT, true);
   }
}
