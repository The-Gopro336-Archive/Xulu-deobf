package com.elementars.eclient.module.combat;

import com.elementars.eclient.Xulu;
import com.elementars.eclient.module.Category;
import com.elementars.eclient.module.Module;
import com.elementars.eclient.module.ModuleManager;
import com.elementars.eclient.util.BlockInteractionHelper;
import com.mojang.realmsclient.gui.ChatFormatting;
import dev.xulu.settings.Value;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.BlockObsidian;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.init.Blocks;
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

public class Surround extends Module {
   // $FF: synthetic field
   private boolean firstRun;
   // $FF: synthetic field
   private ArrayList options;
   // $FF: synthetic field
   private final Value blocksPerTick = this.register(new Value("BlocksPerTick", this, 4, 1, 9));
   // $FF: synthetic field
   private final Value announceUsage = this.register(new Value("AnnounceUsage", this, true));
   // $FF: synthetic field
   private final Value rotate = this.register(new Value("Rotate", this, true));
   // $FF: synthetic field
   boolean isDisabling;
   // $FF: synthetic field
   private int totalTicksRunning = 0;
   // $FF: synthetic field
   private int delayStep = 0;
   // $FF: synthetic field
   int cDelay = 0;
   // $FF: synthetic field
   private final Value tickDelay = this.register(new Value("TickDelay", this, 0, 0, 10));
   // $FF: synthetic field
   private int offsetStep = 0;
   // $FF: synthetic field
   private final Value turnOffCauras = this.register(new Value("Toggle Other Cauras", this, false));
   // $FF: synthetic field
   String caura;
   // $FF: synthetic field
   double oldY;
   // $FF: synthetic field
   private final Value triggerable = this.register(new Value("Triggerable", this, true));
   // $FF: synthetic field
   private boolean isSneaking = false;
   // $FF: synthetic field
   private final Value noGlitchBlocks = this.register(new Value("NoGlitchBlocks", this, true));
   // $FF: synthetic field
   private int lastHotbarSlot = -1;
   // $FF: synthetic field
   private int playerHotbarSlot = -1;
   // $FF: synthetic field
   private final Value mode = this.register(new Value("Mode", this, "Full", new ArrayList(Arrays.asList("Full", "Surround", "Double", "FullCity", "SurroundCity"))));
   // $FF: synthetic field
   private final Value timeoutTicks = this.register(new Value("TimeoutTicks", this, 40, 1, 100));
   // $FF: synthetic field
   boolean hasDisabled;

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

   public void onEnable() {
      if (mc.player == null) {
         this.disable();
      } else {
         this.hasDisabled = false;
         this.oldY = mc.player.posY;
         this.firstRun = true;
         this.playerHotbarSlot = mc.player.inventory.currentItem;
         this.lastHotbarSlot = -1;
         if ((Boolean)this.announceUsage.getValue()) {
            this.sendDebugMessage(String.valueOf((new StringBuilder()).append(ChatFormatting.GREEN.toString()).append("Enabled!")));
         }

      }
   }

   public static boolean isExposed() {
      if (mc.world == null) {
         return false;
      } else {
         BlockPos var0 = new BlockPos(mc.player.getPositionVector());
         if (mc.world.getBlockState(var0.add(1, 0, 0)).getBlock() == Blocks.AIR) {
            return true;
         } else if (mc.world.getBlockState(var0.add(-1, 0, 0)).getBlock() == Blocks.AIR) {
            return true;
         } else if (mc.world.getBlockState(var0.add(0, 0, 1)).getBlock() == Blocks.AIR) {
            return true;
         } else if (mc.world.getBlockState(var0.add(0, 0, -1)).getBlock() == Blocks.AIR) {
            return true;
         } else {
            return mc.world.getBlockState(var0.add(0, -1, 0)).getBlock() == Blocks.AIR;
         }
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
         if ((Boolean)this.announceUsage.getValue()) {
            this.sendDebugMessage(String.valueOf((new StringBuilder()).append(ChatFormatting.RED.toString()).append("Disabled!")));
         }

      }
   }

   public void onUpdate() {
      if (this.cDelay > 0) {
         --this.cDelay;
      }

      if (this.cDelay == 0 && this.isDisabling) {
         Xulu.MODULE_MANAGER.getModuleByName(this.caura).toggle();
         this.isDisabling = false;
         this.hasDisabled = true;
      }

      if (mc.player != null && !ModuleManager.isModuleEnabled("Freecam")) {
         if (Xulu.MODULE_MANAGER.getModuleByName("AutoCrystal") != null && Xulu.MODULE_MANAGER.getModuleByName("AutoCrystal").isToggled() && (Boolean)this.turnOffCauras.getValue() && !this.hasDisabled) {
            this.caura = "AutoCrystal";
            this.cDelay = 19;
            this.isDisabling = true;
            Xulu.MODULE_MANAGER.getModuleByName(this.caura).toggle();
         }

         if (Xulu.MODULE_MANAGER.getModuleByName("AutoCrystalO") != null && Xulu.MODULE_MANAGER.getModuleByName("AutoCrystalO").isToggled() && (Boolean)this.turnOffCauras.getValue() && !this.hasDisabled) {
            this.caura = "AutoCrystalO";
            this.cDelay = 19;
            this.isDisabling = true;
            Xulu.MODULE_MANAGER.getModuleByName(this.caura).toggle();
         }

         if (Xulu.MODULE_MANAGER.getModuleByName("AutoCrystalX") != null && Xulu.MODULE_MANAGER.getModuleByName("AutoCrystalX").isToggled() && (Boolean)this.turnOffCauras.getValue() && !this.hasDisabled) {
            this.caura = "AutoCrystalX";
            this.cDelay = 19;
            this.isDisabling = true;
            Xulu.MODULE_MANAGER.getModuleByName(this.caura).toggle();
         }

         if ((Boolean)this.triggerable.getValue() && this.totalTicksRunning >= (Integer)this.timeoutTicks.getValue()) {
            this.totalTicksRunning = 0;
            this.disable();
         } else if (mc.player.posY != this.oldY) {
            this.disable();
         } else {
            if (!this.firstRun) {
               if (this.delayStep < (Integer)this.tickDelay.getValue()) {
                  ++this.delayStep;
                  return;
               }

               this.delayStep = 0;
            }

            if (this.firstRun) {
               this.firstRun = false;
            }

            int var1;
            for(var1 = 0; var1 < (Integer)this.blocksPerTick.getValue(); ++this.offsetStep) {
               Vec3d[] var2 = new Vec3d[0];
               int var3 = 0;
               if (((String)this.mode.getValue()).equalsIgnoreCase("Full")) {
                  var2 = Surround.Offsets.FULL;
                  var3 = Surround.Offsets.FULL.length;
               }

               if (((String)this.mode.getValue()).equalsIgnoreCase("Surround")) {
                  var2 = Surround.Offsets.SURROUND;
                  var3 = Surround.Offsets.SURROUND.length;
               }

               if (((String)this.mode.getValue()).equalsIgnoreCase("Double")) {
                  var2 = Surround.Offsets.DOUBLE;
                  var3 = Surround.Offsets.DOUBLE.length;
               }

               if (((String)this.mode.getValue()).equalsIgnoreCase("FullCity")) {
                  var2 = Surround.Offsets.FULLCITY;
                  var3 = Surround.Offsets.FULLCITY.length;
               }

               if (((String)this.mode.getValue()).equalsIgnoreCase("SurroundCity")) {
                  var2 = Surround.Offsets.SURROUNDCITY;
                  var3 = Surround.Offsets.SURROUNDCITY.length;
               }

               if (this.offsetStep >= var3) {
                  this.offsetStep = 0;
                  break;
               }

               BlockPos var4 = new BlockPos(var2[this.offsetStep]);
               BlockPos var5 = (new BlockPos(mc.player.getPositionVector())).add(var4.x, var4.y, var4.z);
               if (this.placeBlock(var5)) {
                  ++var1;
               }
            }

            if (var1 > 0) {
               if (this.lastHotbarSlot != this.playerHotbarSlot && this.playerHotbarSlot != -1) {
                  mc.player.inventory.currentItem = this.playerHotbarSlot;
                  this.lastHotbarSlot = this.playerHotbarSlot;
               }

               if (this.isSneaking) {
                  mc.player.connection.sendPacket(new CPacketEntityAction(mc.player, Action.STOP_SNEAKING));
                  this.isSneaking = false;
               }
            }

            ++this.totalTicksRunning;
         }
      }
   }

   public Surround() {
      super("Surround", "Surrounds you with obby", 0, Category.COMBAT, true);
   }

   private static class Offsets {
      // $FF: synthetic field
      private static final Vec3d[] SURROUND = new Vec3d[]{new Vec3d(1.0D, 0.0D, 0.0D), new Vec3d(0.0D, 0.0D, 1.0D), new Vec3d(-1.0D, 0.0D, 0.0D), new Vec3d(0.0D, 0.0D, -1.0D), new Vec3d(1.0D, -1.0D, 0.0D), new Vec3d(0.0D, -1.0D, 1.0D), new Vec3d(-1.0D, -1.0D, 0.0D), new Vec3d(0.0D, -1.0D, -1.0D)};
      // $FF: synthetic field
      private static final Vec3d[] DOUBLE = new Vec3d[]{new Vec3d(1.0D, 0.0D, 0.0D), new Vec3d(0.0D, 0.0D, 1.0D), new Vec3d(-1.0D, 0.0D, 0.0D), new Vec3d(0.0D, 0.0D, -1.0D), new Vec3d(1.0D, -1.0D, 0.0D), new Vec3d(0.0D, -1.0D, 1.0D), new Vec3d(-1.0D, -1.0D, 0.0D), new Vec3d(0.0D, -1.0D, -1.0D), new Vec3d(1.0D, 1.0D, 0.0D), new Vec3d(0.0D, 1.0D, 1.0D), new Vec3d(-1.0D, 1.0D, 0.0D), new Vec3d(0.0D, 1.0D, -1.0D)};
      // $FF: synthetic field
      private static final Vec3d[] FULL = new Vec3d[]{new Vec3d(1.0D, 0.0D, 0.0D), new Vec3d(0.0D, 0.0D, 1.0D), new Vec3d(-1.0D, 0.0D, 0.0D), new Vec3d(0.0D, 0.0D, -1.0D), new Vec3d(1.0D, -1.0D, 0.0D), new Vec3d(0.0D, -1.0D, 1.0D), new Vec3d(-1.0D, -1.0D, 0.0D), new Vec3d(0.0D, -1.0D, -1.0D), new Vec3d(0.0D, -1.0D, 0.0D)};
      // $FF: synthetic field
      private static final Vec3d[] SURROUNDCITY = new Vec3d[]{new Vec3d(1.0D, 0.0D, 0.0D), new Vec3d(0.0D, 0.0D, 1.0D), new Vec3d(-1.0D, 0.0D, 0.0D), new Vec3d(0.0D, 0.0D, -1.0D), new Vec3d(1.0D, -1.0D, 0.0D), new Vec3d(0.0D, -1.0D, 1.0D), new Vec3d(-1.0D, -1.0D, 0.0D), new Vec3d(0.0D, -1.0D, -1.0D), new Vec3d(2.0D, 0.0D, 0.0D), new Vec3d(0.0D, 0.0D, 2.0D), new Vec3d(-2.0D, 0.0D, 0.0D), new Vec3d(0.0D, 0.0D, -2.0D)};
      // $FF: synthetic field
      private static final Vec3d[] FULLCITY = new Vec3d[]{new Vec3d(1.0D, 0.0D, 0.0D), new Vec3d(0.0D, 0.0D, 1.0D), new Vec3d(-1.0D, 0.0D, 0.0D), new Vec3d(0.0D, 0.0D, -1.0D), new Vec3d(1.0D, -1.0D, 0.0D), new Vec3d(0.0D, -1.0D, 1.0D), new Vec3d(-1.0D, -1.0D, 0.0D), new Vec3d(0.0D, -1.0D, -1.0D), new Vec3d(0.0D, -1.0D, 0.0D), new Vec3d(2.0D, 0.0D, 0.0D), new Vec3d(0.0D, 0.0D, 2.0D), new Vec3d(-2.0D, 0.0D, 0.0D), new Vec3d(0.0D, 0.0D, -2.0D)};
   }

   private static enum Mode {
      // $FF: synthetic field
      FULL,
      // $FF: synthetic field
      SURROUND;
   }
}
