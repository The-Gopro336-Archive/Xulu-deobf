package com.elementars.eclient.module.misc;

import com.elementars.eclient.module.Category;
import com.elementars.eclient.module.Module;
import com.elementars.eclient.util.BlockInteractionHelper;
import com.elementars.eclient.util.EnumUtil;
import com.mojang.realmsclient.gui.ChatFormatting;
import dev.xulu.settings.Value;
import java.util.Iterator;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockDeadBush;
import net.minecraft.block.BlockSoulSand;
import net.minecraft.block.BlockTallGrass;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketEntityAction.Action;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class AutoWither extends Module {
   // $FF: synthetic field
   private int bodySlot;
   // $FF: synthetic field
   private final Value useMode = this.register(new Value("UseMode", this, "Spam", EnumUtil.enumConverter(AutoWither.UseMode.class)));
   // $FF: synthetic field
   private final Value debug = this.register(new Value("Debug", this, false));
   // $FF: synthetic field
   private static boolean isSneaking;
   // $FF: synthetic field
   private boolean rotationPlaceableZ;
   // $FF: synthetic field
   private final Value placeRange = this.register(new Value("PlaceRange", this, 3.5F, 2.0F, 10.0F));
   // $FF: synthetic field
   private BlockPos placeTarget;
   // $FF: synthetic field
   private int buildStage;
   // $FF: synthetic field
   private int headSlot;
   // $FF: synthetic field
   private final Value delay = this.register(new Value("Delay", this, 20, 12, 100));
   // $FF: synthetic field
   private final Value rotate = this.register(new Value("Rotate", this, true));
   // $FF: synthetic field
   private boolean rotationPlaceableX;
   // $FF: synthetic field
   private int delayStep;

   private boolean placingIsBlocked(BlockPos var1) {
      Block var2 = mc.world.getBlockState(var1).getBlock();
      if (!(var2 instanceof BlockAir)) {
         return true;
      } else {
         Iterator var3 = mc.world.getEntitiesWithinAABBExcludingEntity((Entity)null, new AxisAlignedBB(var1)).iterator();

         Entity var4;
         do {
            if (!var3.hasNext()) {
               return false;
            }

            var4 = (Entity)var3.next();
         } while(var4 instanceof EntityItem || var4 instanceof EntityXPOrb);

         return true;
      }
   }

   private boolean checkBlocksInHotbar() {
      this.headSlot = -1;
      this.bodySlot = -1;

      for(int var1 = 0; var1 < 9; ++var1) {
         ItemStack var3 = mc.player.inventory.getStackInSlot(var1);
         if (var3 != ItemStack.EMPTY) {
            if (var3.getItem() == Items.SKULL && var3.getItemDamage() == 1) {
               if (mc.player.inventory.getStackInSlot(var1).stackSize >= 3) {
                  this.headSlot = var1;
               }
            } else if (var3.getItem() instanceof ItemBlock) {
               Block var2 = ((ItemBlock)var3.getItem()).getBlock();
               if (var2 instanceof BlockSoulSand && mc.player.inventory.getStackInSlot(var1).stackSize >= 4) {
                  this.bodySlot = var1;
               }

               if (var2 == Blocks.SNOW && mc.player.inventory.getStackInSlot(var1).stackSize >= 2) {
                  this.bodySlot = var1;
               }
            }
         }
      }

      return this.bodySlot != -1 && this.headSlot != -1;
   }

   public AutoWither() {
      super("AutoWither", "Automatically places withers", 0, Category.MISC, true);
   }

   private static EnumFacing getPlaceableSide(BlockPos var0) {
      EnumFacing[] var1 = EnumFacing.values();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         EnumFacing var4 = var1[var3];
         BlockPos var6 = var0.offset(var4);
         IBlockState var5;
         if (mc.world.getBlockState(var6).getBlock().canCollideCheck(mc.world.getBlockState(var6), false) && !(var5 = mc.world.getBlockState(var6)).getMaterial().isReplaceable() && !(var5.getBlock() instanceof BlockTallGrass) && !(var5.getBlock() instanceof BlockDeadBush)) {
            return var4;
         }
      }

      return null;
   }

   private boolean testStructure() {
      return this.testWitherStructure();
   }

   private static void placeBlock(BlockPos var0, boolean var1) {
      EnumFacing var2 = getPlaceableSide(var0);
      if (var2 != null) {
         BlockPos var3 = var0.offset(var2);
         EnumFacing var4 = var2.getOpposite();
         Vec3d var5 = (new Vec3d(var3)).add(0.5D, 0.5D, 0.5D).add((new Vec3d(var4.getDirectionVec())).scale(0.5D));
         Block var6 = mc.world.getBlockState(var3).getBlock();
         if (!isSneaking && (BlockInteractionHelper.blackList.contains(var6) || BlockInteractionHelper.shulkerList.contains(var6))) {
            mc.player.connection.sendPacket(new CPacketEntityAction(mc.player, Action.START_SNEAKING));
            isSneaking = true;
         }

         if (var1) {
            BlockInteractionHelper.faceVectorPacketInstant(var5);
         }

         mc.playerController.processRightClickBlock(mc.player, mc.world, var3, var4, var5, EnumHand.MAIN_HAND);
         mc.player.swingArm(EnumHand.MAIN_HAND);
         mc.rightClickDelayTimer = 4;
      }
   }

   public void onUpdate() {
      if (mc.player != null) {
         BlockPos var4;
         if (this.buildStage == 1) {
            isSneaking = false;
            this.rotationPlaceableX = false;
            this.rotationPlaceableZ = false;
            if (!this.checkBlocksInHotbar()) {
               this.sendDebugMessage("no blocks in hotbar");
               return;
            }

            List var1 = BlockInteractionHelper.getSphere(mc.player.getPosition().down(), (Float)this.placeRange.getValue(), ((Float)this.placeRange.getValue()).intValue(), false, true, 0);
            boolean var2 = true;
            Iterator var3 = var1.iterator();

            while(var3.hasNext()) {
               var4 = (BlockPos)var3.next();
               this.placeTarget = var4.down();
               if (this.testStructure()) {
                  var2 = false;
                  break;
               }
            }

            if (var2) {
               if (((String)this.useMode.getValue()).equalsIgnoreCase("Single")) {
                  if ((Boolean)this.debug.getValue()) {
                     this.sendDebugMessage(String.valueOf((new StringBuilder()).append(ChatFormatting.RED.toString()).append("Position not valid, disabling.")));
                  }

                  this.disable();
               }

               return;
            }

            mc.player.inventory.currentItem = this.bodySlot;
            BlockPos[] var9 = AutoWither.BodyParts.bodyBase;
            int var11 = var9.length;

            int var5;
            BlockPos var6;
            for(var5 = 0; var5 < var11; ++var5) {
               var6 = var9[var5];
               placeBlock(this.placeTarget.add(var6), (Boolean)this.rotate.getValue());
            }

            if (this.rotationPlaceableX) {
               var9 = AutoWither.BodyParts.ArmsX;
               var11 = var9.length;

               for(var5 = 0; var5 < var11; ++var5) {
                  var6 = var9[var5];
                  placeBlock(this.placeTarget.add(var6), (Boolean)this.rotate.getValue());
               }
            } else if (this.rotationPlaceableZ) {
               var9 = AutoWither.BodyParts.ArmsZ;
               var11 = var9.length;

               for(var5 = 0; var5 < var11; ++var5) {
                  var6 = var9[var5];
                  placeBlock(this.placeTarget.add(var6), (Boolean)this.rotate.getValue());
               }
            }

            this.buildStage = 2;
         } else if (this.buildStage == 2) {
            mc.player.inventory.currentItem = this.headSlot;
            BlockPos[] var7;
            int var8;
            int var10;
            if (this.rotationPlaceableX) {
               var7 = AutoWither.BodyParts.headsX;
               var8 = var7.length;

               for(var10 = 0; var10 < var8; ++var10) {
                  var4 = var7[var10];
                  placeBlock(this.placeTarget.add(var4), (Boolean)this.rotate.getValue());
               }
            } else if (this.rotationPlaceableZ) {
               var7 = AutoWither.BodyParts.headsZ;
               var8 = var7.length;

               for(var10 = 0; var10 < var8; ++var10) {
                  var4 = var7[var10];
                  placeBlock(this.placeTarget.add(var4), (Boolean)this.rotate.getValue());
               }
            }

            if (isSneaking) {
               mc.player.connection.sendPacket(new CPacketEntityAction(mc.player, Action.STOP_SNEAKING));
               isSneaking = false;
            }

            if (((String)this.useMode.getValue()).equalsIgnoreCase("Single")) {
               this.disable();
            }

            this.buildStage = 3;
         } else if (this.buildStage == 3) {
            if (this.delayStep < (Integer)this.delay.getValue()) {
               ++this.delayStep;
            } else {
               this.delayStep = 1;
               this.buildStage = 1;
            }
         }

      }
   }

   public void onEnable() {
      if (mc.player == null) {
         this.disable();
      } else {
         this.buildStage = 1;
         this.delayStep = 1;
      }
   }

   private boolean testWitherStructure() {
      boolean var1 = true;
      this.rotationPlaceableX = true;
      this.rotationPlaceableZ = true;
      boolean var2 = false;
      if (mc.world.getBlockState(this.placeTarget) == null) {
         return false;
      } else {
         Block var3 = mc.world.getBlockState(this.placeTarget).getBlock();
         if (var3 instanceof BlockTallGrass || var3 instanceof BlockDeadBush) {
            var2 = true;
         }

         if (getPlaceableSide(this.placeTarget.up()) == null) {
            return false;
         } else {
            BlockPos[] var4 = AutoWither.BodyParts.bodyBase;
            int var5 = var4.length;

            int var6;
            BlockPos var7;
            for(var6 = 0; var6 < var5; ++var6) {
               var7 = var4[var6];
               if (this.placingIsBlocked(this.placeTarget.add(var7))) {
                  var1 = false;
               }
            }

            var4 = AutoWither.BodyParts.ArmsX;
            var5 = var4.length;

            for(var6 = 0; var6 < var5; ++var6) {
               var7 = var4[var6];
               if (this.placingIsBlocked(this.placeTarget.add(var7)) || this.placingIsBlocked(this.placeTarget.add(var7.down()))) {
                  this.rotationPlaceableX = false;
               }
            }

            var4 = AutoWither.BodyParts.ArmsZ;
            var5 = var4.length;

            for(var6 = 0; var6 < var5; ++var6) {
               var7 = var4[var6];
               if (this.placingIsBlocked(this.placeTarget.add(var7)) || this.placingIsBlocked(this.placeTarget.add(var7.down()))) {
                  this.rotationPlaceableZ = false;
               }
            }

            var4 = AutoWither.BodyParts.headsX;
            var5 = var4.length;

            for(var6 = 0; var6 < var5; ++var6) {
               var7 = var4[var6];
               if (this.placingIsBlocked(this.placeTarget.add(var7))) {
                  this.rotationPlaceableX = false;
               }
            }

            var4 = AutoWither.BodyParts.headsZ;
            var5 = var4.length;

            for(var6 = 0; var6 < var5; ++var6) {
               var7 = var4[var6];
               if (this.placingIsBlocked(this.placeTarget.add(var7))) {
                  this.rotationPlaceableZ = false;
               }
            }

            return !var2 && var1 && (this.rotationPlaceableX || this.rotationPlaceableZ);
         }
      }
   }

   private static class BodyParts {
      // $FF: synthetic field
      private static final BlockPos[] head = new BlockPos[]{new BlockPos(0, 3, 0)};
      // $FF: synthetic field
      private static final BlockPos[] headsZ = new BlockPos[]{new BlockPos(0, 3, 0), new BlockPos(0, 3, -1), new BlockPos(0, 3, 1)};
      // $FF: synthetic field
      private static final BlockPos[] bodyBase = new BlockPos[]{new BlockPos(0, 1, 0), new BlockPos(0, 2, 0)};
      // $FF: synthetic field
      private static final BlockPos[] ArmsX = new BlockPos[]{new BlockPos(-1, 2, 0), new BlockPos(1, 2, 0)};
      // $FF: synthetic field
      private static final BlockPos[] ArmsZ = new BlockPos[]{new BlockPos(0, 2, -1), new BlockPos(0, 2, 1)};
      // $FF: synthetic field
      private static final BlockPos[] headsX = new BlockPos[]{new BlockPos(0, 3, 0), new BlockPos(-1, 3, 0), new BlockPos(1, 3, 0)};
   }

   private static enum UseMode {
      // $FF: synthetic field
      SINGLE,
      // $FF: synthetic field
      SPAM;
   }
}
