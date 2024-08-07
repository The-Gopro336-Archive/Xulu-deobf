package com.elementars.eclient.module.combat;

import com.elementars.eclient.module.Category;
import com.elementars.eclient.module.Module;
import com.elementars.eclient.util.BlockInteractionHelper;
import com.elementars.eclient.util.Timer;
import dev.xulu.settings.Value;
import java.util.Iterator;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.BlockObsidian;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.network.play.client.CPacketEntityAction.Action;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.GameType;

public class AutoFeetBlock extends Module {
   // $FF: synthetic field
   private final Value rotate = this.register(new Value("Rotate", this, true));
   // $FF: synthetic field
   private final Value noGlitchBlocks = this.register(new Value("NoGlitchBlocks", this, true));
   // $FF: synthetic field
   int lastHotbarSlot;
   // $FF: synthetic field
   private final Value delay = this.register(new Value("MS Delay", this, 160L, 0L, 1000L));
   // $FF: synthetic field
   int playerHotbarSlot;
   // $FF: synthetic field
   boolean isSneaking;
   // $FF: synthetic field
   Timer timer = new Timer();

   public void onEnable() {
      if (mc.player == null) {
         this.disable();
      } else {
         this.playerHotbarSlot = mc.player.inventory.currentItem;
         this.lastHotbarSlot = -1;
         mc.player.jump();
         this.timer.reset();
      }
   }

   public void onDisable() {
      if (mc.player != null) {
         if (this.lastHotbarSlot != this.playerHotbarSlot && this.playerHotbarSlot != -1) {
            mc.player.inventory.currentItem = this.playerHotbarSlot;
         }

         if (this.isSneaking) {
            mc.player.connection.sendPacket(new CPacketEntityAction(mc.player, Action.STOP_SNEAKING));
            this.isSneaking = false;
         }

         this.playerHotbarSlot = -1;
         this.lastHotbarSlot = -1;
      }
   }

   public AutoFeetBlock() {
      super("AutoFeetBlock", "Automatically phases yourself into a block", 0, Category.COMBAT, true);
   }

   private boolean placeBlock(BlockPos var1) {
      Block var2 = mc.world.getBlockState(var1).getBlock();
      if (!(var2 instanceof BlockAir) && !(var2 instanceof BlockLiquid)) {
         return false;
      } else {
         Iterator var3 = mc.world.getEntitiesWithinAABBExcludingEntity((Entity)null, new AxisAlignedBB(var1)).iterator();

         while(var3.hasNext()) {
            Entity var4 = (Entity)var3.next();
            if (!(var4 instanceof EntityItem) && !(var4 instanceof EntityXPOrb)) {
               return false;
            }
         }

         EnumFacing var9 = BlockInteractionHelper.getPlaceableSide(var1);
         if (var9 == null) {
            return false;
         } else {
            BlockPos var10 = var1.offset(var9);
            EnumFacing var5 = var9.getOpposite();
            if (!BlockInteractionHelper.canBeClicked(var10)) {
               return false;
            } else {
               Vec3d var6 = (new Vec3d(var10)).add(0.5D, 0.5D, 0.5D).add((new Vec3d(var5.getDirectionVec())).scale(0.5D));
               Block var7 = mc.world.getBlockState(var10).getBlock();
               int var8 = this.findObiInHotbar();
               if (var8 == -1) {
                  this.disable();
               }

               if (this.lastHotbarSlot != var8) {
                  mc.player.inventory.currentItem = var8;
                  this.lastHotbarSlot = var8;
               }

               if (!this.isSneaking && BlockInteractionHelper.blackList.contains(var7) || BlockInteractionHelper.shulkerList.contains(var7)) {
                  mc.player.connection.sendPacket(new CPacketEntityAction(mc.player, Action.START_SNEAKING));
                  this.isSneaking = true;
               }

               if ((Boolean)this.rotate.getValue()) {
                  BlockInteractionHelper.faceVectorPacketInstant(var6);
               }

               mc.playerController.processRightClickBlock(mc.player, mc.world, var10, var5, var6, EnumHand.MAIN_HAND);
               mc.player.swingArm(EnumHand.MAIN_HAND);
               mc.rightClickDelayTimer = 4;
               if ((Boolean)this.noGlitchBlocks.getValue() && !mc.playerController.getCurrentGameType().equals(GameType.CREATIVE)) {
                  mc.player.connection.sendPacket(new CPacketPlayerDigging(net.minecraft.network.play.client.CPacketPlayerDigging.Action.START_DESTROY_BLOCK, var10, var5));
               }

               return true;
            }
         }
      }
   }

   public int findObiInHotbar() {
      int var1 = -1;

      for(int var2 = 0; var2 < 9; ++var2) {
         ItemStack var3 = mc.player.inventory.getStackInSlot(var2);
         if (var3 != ItemStack.EMPTY && var3.getItem() instanceof ItemBlock) {
            Block var4 = ((ItemBlock)var3.getItem()).getBlock();
            if (var4 instanceof BlockObsidian) {
               var1 = var2;
               break;
            }
         }
      }

      return var1;
   }

   public void onUpdate() {
      if (this.timer.hasReached((Long)this.delay.getValue())) {
         BlockPos var1 = new BlockPos(0, -1, 0);
         BlockPos var2 = (new BlockPos(mc.player.getPositionVector())).add(var1.x, var1.y, var1.z);
         if (this.placeBlock(var2)) {
            if (this.lastHotbarSlot != this.playerHotbarSlot && this.playerHotbarSlot != -1) {
               mc.player.inventory.currentItem = this.playerHotbarSlot;
               this.lastHotbarSlot = this.playerHotbarSlot;
            }

            if (this.isSneaking) {
               mc.player.connection.sendPacket(new CPacketEntityAction(mc.player, Action.STOP_SNEAKING));
               this.isSneaking = false;
            }

            mc.player.onGround = false;
            mc.player.motionY = 20.0D;
         }

         this.disable();
      }

   }
}
