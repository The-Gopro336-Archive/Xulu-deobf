package com.elementars.eclient.module.combat;

import com.elementars.eclient.Xulu;
import com.elementars.eclient.event.events.RenderEvent;
import com.elementars.eclient.friend.Friends;
import com.elementars.eclient.module.Category;
import com.elementars.eclient.module.Module;
import com.elementars.eclient.module.ModuleManager;
import com.elementars.eclient.util.BlockInteractionHelper;
import com.elementars.eclient.util.EntityUtil;
import com.elementars.eclient.util.MathUtil;
import com.elementars.eclient.util.XuluTessellator;
import com.mojang.realmsclient.gui.ChatFormatting;
import dev.xulu.settings.Value;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.BlockObsidian;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
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

public class AutoTrap extends Module {
   // $FF: synthetic field
   private boolean firstRun;
   // $FF: synthetic field
   private String lastTickTargetName;
   // $FF: synthetic field
   String caura;
   // $FF: synthetic field
   private int test;
   // $FF: synthetic field
   private int lastHotbarSlot = -1;
   // $FF: synthetic field
   private final Value noGlitchBlocks = this.register(new Value("NoGlitchBlocks", this, true));
   // $FF: synthetic field
   private final Value blue = this.register(new Value("Blue", this, 255, 0, 255));
   // $FF: synthetic field
   private final Value activeInFreecam = this.register(new Value("ActiveInFreecam", this, true));
   // $FF: synthetic field
   private int delayStep = 0;
   // $FF: synthetic field
   private final Value red = this.register(new Value("Red", this, 0, 0, 255));
   // $FF: synthetic field
   private int offsetStep = 0;
   // $FF: synthetic field
   private int playerHotbarSlot = -1;
   // $FF: synthetic field
   boolean isDisabling;
   // $FF: synthetic field
   private final Value tickDelay = this.register(new Value("TickDelay", this, 2, 0, 10));
   // $FF: synthetic field
   private final Value oalpha = this.register(new Value("Outline Alpha", this, 70, 0, 255));
   // $FF: synthetic field
   private final Value range = this.register(new Value("Range", this, 4.5F, 3.5F, 32.0F));
   // $FF: synthetic field
   private Set placeList = new HashSet();
   // $FF: synthetic field
   private ArrayList options;
   // $FF: synthetic field
   boolean hasDisabled;
   // $FF: synthetic field
   private final Value esp = this.register(new Value("Show esp", this, false));
   // $FF: synthetic field
   int cDelay = 0;
   // $FF: synthetic field
   private final Value toggleoff = this.register(new Value("Toggle Off", this, false));
   // $FF: synthetic field
   private boolean isSneaking = false;
   // $FF: synthetic field
   private final Value mode = this.register(new Value("Esp Mode", this, "Solid", new String[]{"Solid", "Outline", "Full"}));
   // $FF: synthetic field
   private final Value turnOffCauras = this.register(new Value("Toggle Other Cauras", this, false));
   // $FF: synthetic field
   private final Value cage = this.register(new Value("Cage", this, "Trap", new ArrayList(Arrays.asList("Trap", "TrapTop", "TrapFullRoof", "TrapFullRoofTop", "Crystalexa", "Crystal", "CrystalFullRoof"))));
   // $FF: synthetic field
   private final Value blocksPerTick = this.register(new Value("BlocksPerTick", this, 2, 1, 23));
   // $FF: synthetic field
   private final Value announceUsage = this.register(new Value("AnnounceUsage", this, true));
   // $FF: synthetic field
   private final Value rotate = this.register(new Value("Rotate", this, false));
   // $FF: synthetic field
   private final Value alpha = this.register(new Value("Alpha", this, 70, 0, 255));
   // $FF: synthetic field
   private final Value green = this.register(new Value("Green", this, 255, 0, 255));
   // $FF: synthetic field
   private EntityPlayer closestTarget;

   public void onWorldRender(RenderEvent var1) {
      if ((Boolean)this.esp.getValue()) {
         int var2 = (Integer)this.red.getValue();
         int var3 = (Integer)this.green.getValue();
         int var4 = (Integer)this.blue.getValue();
         Iterator var5 = this.placeList.iterator();

         while(var5.hasNext()) {
            BlockPos var6 = (BlockPos)var5.next();
            if (((String)this.mode.getValue()).equalsIgnoreCase("Solid")) {
               XuluTessellator.prepare(7);
               XuluTessellator.drawBox(var6, var2, var3, var4, (Integer)this.alpha.getValue(), 63);
               XuluTessellator.release();
            } else {
               IBlockState var7;
               Vec3d var8;
               if (((String)this.mode.getValue()).equalsIgnoreCase("Outline")) {
                  var7 = mc.world.getBlockState(var6);
                  var8 = MathUtil.interpolateEntity(mc.player, mc.getRenderPartialTicks());
                  XuluTessellator.drawBoundingBox(var7.getSelectedBoundingBox(mc.world, var6).grow(0.0020000000949949026D).offset(-var8.x, -var8.y, -var8.z), 1.5F, var2, var3, var4, (Integer)this.alpha.getValue());
               } else if (((String)this.mode.getValue()).equalsIgnoreCase("Full")) {
                  var7 = mc.world.getBlockState(var6);
                  var8 = MathUtil.interpolateEntity(mc.player, mc.getRenderPartialTicks());
                  XuluTessellator.drawFullBox(var7.getSelectedBoundingBox(mc.world, var6).grow(0.0020000000949949026D).offset(-var8.x, -var8.y, -var8.z), var6, 1.5F, var2, var3, var4, (Integer)this.alpha.getValue(), (Integer)this.oalpha.getValue());
               }
            }
         }
      }

   }

   public AutoTrap() {
      super("AutoTrap", "Automatically traps people", 0, Category.COMBAT, true);
   }

   private void findClosestTarget() {
      List var1 = mc.world.playerEntities;
      this.closestTarget = null;
      Iterator var2 = var1.iterator();

      while(var2.hasNext()) {
         EntityPlayer var3 = (EntityPlayer)var2.next();
         if (var3 != mc.player && !Friends.isFriend(var3.getName()) && EntityUtil.isLiving(var3) && !(var3.getHealth() <= 0.0F)) {
            if (this.closestTarget == null) {
               this.closestTarget = var3;
            } else if (!(mc.player.getDistance(var3) >= mc.player.getDistance(this.closestTarget))) {
               this.closestTarget = var3;
            }
         }
      }

   }

   public void onEnable() {
      this.test = 0;
      if (mc.player == null) {
         this.disable();
      } else {
         this.hasDisabled = false;
         this.firstRun = true;
         this.playerHotbarSlot = mc.player.inventory.currentItem;
         this.lastHotbarSlot = -1;
      }
   }

   private boolean placeBlockInRange(BlockPos var1, double var2) {
      Block var4 = mc.world.getBlockState(var1).getBlock();
      if (!(var4 instanceof BlockAir) && !(var4 instanceof BlockLiquid)) {
         this.placeList.remove(var1);
         return false;
      } else {
         Iterator var5 = mc.world.getEntitiesWithinAABBExcludingEntity((Entity)null, new AxisAlignedBB(var1)).iterator();

         while(var5.hasNext()) {
            Entity var6 = (Entity)var5.next();
            if (!(var6 instanceof EntityItem) && !(var6 instanceof EntityXPOrb)) {
               return false;
            }
         }

         EnumFacing var11 = BlockInteractionHelper.getPlaceableSide(var1);
         if (var11 == null) {
            return false;
         } else {
            BlockPos var12 = var1.offset(var11);
            EnumFacing var7 = var11.getOpposite();
            if (!BlockInteractionHelper.canBeClicked(var12)) {
               return false;
            } else {
               Vec3d var8 = (new Vec3d(var12)).add(0.5D, 0.5D, 0.5D).add((new Vec3d(var7.getDirectionVec())).scale(0.5D));
               Block var9 = mc.world.getBlockState(var12).getBlock();
               if (mc.player.getPositionVector().distanceTo(var8) > var2) {
                  return false;
               } else {
                  int var10 = this.findObiInHotbar();
                  if (var10 == -1) {
                     this.disable();
                  }

                  if (this.lastHotbarSlot != var10) {
                     mc.player.inventory.currentItem = var10;
                     this.lastHotbarSlot = var10;
                  }

                  if (!this.isSneaking && BlockInteractionHelper.blackList.contains(var9) || BlockInteractionHelper.shulkerList.contains(var9)) {
                     mc.player.connection.sendPacket(new CPacketEntityAction(mc.player, Action.START_SNEAKING));
                     this.isSneaking = true;
                  }

                  if ((Boolean)this.rotate.getValue()) {
                     BlockInteractionHelper.faceVectorPacketInstant(var8);
                  }

                  mc.playerController.processRightClickBlock(mc.player, mc.world, var12, var7, var8, EnumHand.MAIN_HAND);
                  mc.player.swingArm(EnumHand.MAIN_HAND);
                  mc.rightClickDelayTimer = 4;
                  if ((Boolean)this.noGlitchBlocks.getValue() && !mc.playerController.getCurrentGameType().equals(GameType.CREATIVE)) {
                     mc.player.connection.sendPacket(new CPacketPlayerDigging(net.minecraft.network.play.client.CPacketPlayerDigging.Action.START_DESTROY_BLOCK, var12, var7));
                  }

                  return true;
               }
            }
         }
      }
   }

   public String getHudInfo() {
      return this.closestTarget != null ? this.closestTarget.getName().toUpperCase() : null;
   }

   public void onUpdate() {
      if (this.cDelay > 0) {
         --this.cDelay;
      }

      if (this.cDelay == 0 && this.isDisabling && Xulu.MODULE_MANAGER.getModuleByName(this.caura) != null) {
         Xulu.MODULE_MANAGER.getModuleByName(this.caura).toggle();
         this.isDisabling = false;
         this.hasDisabled = true;
      }

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

      if ((Boolean)this.toggleoff.getValue()) {
         ++this.test;
         if (this.test == 20) {
            super.toggle();
         }
      }

      if (mc.player != null) {
         if ((Boolean)this.activeInFreecam.getValue() || !ModuleManager.isModuleEnabled("Freecam")) {
            if (!this.firstRun) {
               if (this.delayStep < (Integer)this.tickDelay.getValue()) {
                  ++this.delayStep;
                  return;
               }

               this.delayStep = 0;
            }

            this.findClosestTarget();
            if (this.closestTarget == null) {
               if (this.firstRun) {
                  this.firstRun = false;
                  if ((Boolean)this.announceUsage.getValue()) {
                     this.sendDebugMessage(String.valueOf((new StringBuilder()).append(ChatFormatting.GREEN).append("enabled").append(ChatFormatting.RESET).append(", waiting for target.")));
                  }
               }

            } else {
               if (this.firstRun) {
                  this.firstRun = false;
                  this.lastTickTargetName = this.closestTarget.getName();
                  if ((Boolean)this.announceUsage.getValue()) {
                     this.sendDebugMessage(String.valueOf((new StringBuilder()).append(ChatFormatting.GREEN).append("enabled").append(ChatFormatting.RESET).append(", target: ").append(this.lastTickTargetName)));
                  }
               } else if (!this.lastTickTargetName.equals(this.closestTarget.getName())) {
                  this.lastTickTargetName = this.closestTarget.getName();
                  this.offsetStep = 0;
                  if ((Boolean)this.announceUsage.getValue()) {
                     this.sendDebugMessage(String.valueOf((new StringBuilder()).append("New target: ").append(this.lastTickTargetName)));
                  }
               }

               ArrayList var1 = new ArrayList();
               if (((String)this.cage.getValue()).equalsIgnoreCase("Trap")) {
                  Collections.addAll(var1, AutoTrap.Offsets.TRAP);
               }

               if (((String)this.cage.getValue()).equalsIgnoreCase("TrapTop")) {
                  Collections.addAll(var1, AutoTrap.Offsets.TRAPTOP);
               }

               if (((String)this.cage.getValue()).equalsIgnoreCase("TrapFullRoof")) {
                  Collections.addAll(var1, AutoTrap.Offsets.TRAPFULLROOF);
               }

               if (((String)this.cage.getValue()).equalsIgnoreCase("TrapFullRoofTop")) {
                  Collections.addAll(var1, AutoTrap.Offsets.TRAPFULLROOFTOP);
               }

               if (((String)this.cage.getValue()).equalsIgnoreCase("Crystalexa")) {
                  Collections.addAll(var1, AutoTrap.Offsets.CRYSTALEXA);
               }

               if (((String)this.cage.getValue()).equalsIgnoreCase("Crystal")) {
                  Collections.addAll(var1, AutoTrap.Offsets.CRYSTAL);
               }

               if (((String)this.cage.getValue()).equalsIgnoreCase("CrystalFullRoof")) {
                  Collections.addAll(var1, AutoTrap.Offsets.CRYSTALFULLROOF);
               }

               int var2;
               for(var2 = 0; var2 < (Integer)this.blocksPerTick.getValue(); ++this.offsetStep) {
                  if (this.offsetStep >= var1.size()) {
                     this.offsetStep = 0;
                     break;
                  }

                  BlockPos var3 = new BlockPos((Vec3d)var1.get(this.offsetStep));
                  BlockPos var4 = (new BlockPos(this.closestTarget.getPositionVector())).down().add(var3.x, var3.y, var3.z);
                  this.placeList.add(var4);
                  if (this.placeBlockInRange(var4, (double)(Float)this.range.getValue())) {
                     ++var2;
                  }
               }

               if (var2 > 0) {
                  if (this.lastHotbarSlot != this.playerHotbarSlot && this.playerHotbarSlot != -1) {
                     mc.player.inventory.currentItem = this.playerHotbarSlot;
                     this.lastHotbarSlot = this.playerHotbarSlot;
                  }

                  if (this.isSneaking) {
                     mc.player.connection.sendPacket(new CPacketEntityAction(mc.player, Action.STOP_SNEAKING));
                     this.isSneaking = false;
                  }
               }

            }
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
         this.placeList.clear();
         if ((Boolean)this.announceUsage.getValue()) {
            this.sendDebugMessage(String.valueOf((new StringBuilder()).append(ChatFormatting.RED.toString()).append("Disabled!")));
         }

      }
   }

   private int findObiInHotbar() {
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

   private static class Offsets {
      // $FF: synthetic field
      private static final Vec3d[] TRAP = new Vec3d[]{new Vec3d(0.0D, 0.0D, -1.0D), new Vec3d(1.0D, 0.0D, 0.0D), new Vec3d(0.0D, 0.0D, 1.0D), new Vec3d(-1.0D, 0.0D, 0.0D), new Vec3d(0.0D, 1.0D, -1.0D), new Vec3d(1.0D, 1.0D, 0.0D), new Vec3d(0.0D, 1.0D, 1.0D), new Vec3d(-1.0D, 1.0D, 0.0D), new Vec3d(0.0D, 2.0D, -1.0D), new Vec3d(1.0D, 2.0D, 0.0D), new Vec3d(0.0D, 2.0D, 1.0D), new Vec3d(-1.0D, 2.0D, 0.0D), new Vec3d(0.0D, 3.0D, -1.0D), new Vec3d(0.0D, 3.0D, 0.0D)};
      // $FF: synthetic field
      private static final Vec3d[] CRYSTAL = new Vec3d[]{new Vec3d(0.0D, 0.0D, -1.0D), new Vec3d(1.0D, 0.0D, 0.0D), new Vec3d(0.0D, 0.0D, 1.0D), new Vec3d(-1.0D, 0.0D, 0.0D), new Vec3d(-1.0D, 0.0D, 1.0D), new Vec3d(1.0D, 0.0D, -1.0D), new Vec3d(-1.0D, 0.0D, -1.0D), new Vec3d(1.0D, 0.0D, 1.0D), new Vec3d(-1.0D, 1.0D, -1.0D), new Vec3d(1.0D, 1.0D, 1.0D), new Vec3d(-1.0D, 1.0D, 1.0D), new Vec3d(1.0D, 1.0D, -1.0D), new Vec3d(0.0D, 2.0D, -1.0D), new Vec3d(1.0D, 2.0D, 0.0D), new Vec3d(0.0D, 2.0D, 1.0D), new Vec3d(-1.0D, 2.0D, 0.0D), new Vec3d(-1.0D, 2.0D, 1.0D), new Vec3d(1.0D, 2.0D, -1.0D), new Vec3d(0.0D, 3.0D, -1.0D), new Vec3d(0.0D, 3.0D, 0.0D)};
      // $FF: synthetic field
      private static final Vec3d[] TRAPFULLROOFTOP = new Vec3d[]{new Vec3d(0.0D, 0.0D, -1.0D), new Vec3d(1.0D, 0.0D, 0.0D), new Vec3d(0.0D, 0.0D, 1.0D), new Vec3d(-1.0D, 0.0D, 0.0D), new Vec3d(0.0D, 1.0D, -1.0D), new Vec3d(1.0D, 1.0D, 0.0D), new Vec3d(0.0D, 1.0D, 1.0D), new Vec3d(-1.0D, 1.0D, 0.0D), new Vec3d(0.0D, 2.0D, -1.0D), new Vec3d(1.0D, 2.0D, 0.0D), new Vec3d(0.0D, 2.0D, 1.0D), new Vec3d(-1.0D, 2.0D, 0.0D), new Vec3d(0.0D, 3.0D, -1.0D), new Vec3d(1.0D, 3.0D, 0.0D), new Vec3d(0.0D, 3.0D, 1.0D), new Vec3d(-1.0D, 3.0D, 0.0D), new Vec3d(0.0D, 3.0D, 0.0D), new Vec3d(0.0D, 4.0D, 0.0D)};
      // $FF: synthetic field
      private static final Vec3d[] CRYSTALEXA = new Vec3d[]{new Vec3d(0.0D, 0.0D, -1.0D), new Vec3d(0.0D, 1.0D, -1.0D), new Vec3d(0.0D, 2.0D, -1.0D), new Vec3d(1.0D, 2.0D, 0.0D), new Vec3d(0.0D, 2.0D, 1.0D), new Vec3d(-1.0D, 2.0D, 0.0D), new Vec3d(-1.0D, 2.0D, -1.0D), new Vec3d(1.0D, 2.0D, 1.0D), new Vec3d(1.0D, 2.0D, -1.0D), new Vec3d(-1.0D, 2.0D, 1.0D), new Vec3d(0.0D, 3.0D, -1.0D), new Vec3d(0.0D, 3.0D, 0.0D)};
      // $FF: synthetic field
      private static final Vec3d[] TRAPFULLROOF = new Vec3d[]{new Vec3d(0.0D, 0.0D, -1.0D), new Vec3d(1.0D, 0.0D, 0.0D), new Vec3d(0.0D, 0.0D, 1.0D), new Vec3d(-1.0D, 0.0D, 0.0D), new Vec3d(0.0D, 1.0D, -1.0D), new Vec3d(1.0D, 1.0D, 0.0D), new Vec3d(0.0D, 1.0D, 1.0D), new Vec3d(-1.0D, 1.0D, 0.0D), new Vec3d(0.0D, 2.0D, -1.0D), new Vec3d(1.0D, 2.0D, 0.0D), new Vec3d(0.0D, 2.0D, 1.0D), new Vec3d(-1.0D, 2.0D, 0.0D), new Vec3d(0.0D, 3.0D, -1.0D), new Vec3d(1.0D, 3.0D, 0.0D), new Vec3d(0.0D, 3.0D, 1.0D), new Vec3d(-1.0D, 3.0D, 0.0D), new Vec3d(0.0D, 3.0D, 0.0D)};
      // $FF: synthetic field
      private static final Vec3d[] CRYSTALFULLROOF = new Vec3d[]{new Vec3d(0.0D, 0.0D, -1.0D), new Vec3d(1.0D, 0.0D, 0.0D), new Vec3d(0.0D, 0.0D, 1.0D), new Vec3d(-1.0D, 0.0D, 0.0D), new Vec3d(-1.0D, 0.0D, 1.0D), new Vec3d(1.0D, 0.0D, -1.0D), new Vec3d(-1.0D, 0.0D, -1.0D), new Vec3d(1.0D, 0.0D, 1.0D), new Vec3d(-1.0D, 1.0D, -1.0D), new Vec3d(1.0D, 1.0D, 1.0D), new Vec3d(-1.0D, 1.0D, 1.0D), new Vec3d(1.0D, 1.0D, -1.0D), new Vec3d(0.0D, 2.0D, -1.0D), new Vec3d(1.0D, 2.0D, 0.0D), new Vec3d(0.0D, 2.0D, 1.0D), new Vec3d(-1.0D, 2.0D, 0.0D), new Vec3d(-1.0D, 2.0D, 1.0D), new Vec3d(1.0D, 2.0D, -1.0D), new Vec3d(0.0D, 3.0D, -1.0D), new Vec3d(1.0D, 3.0D, 0.0D), new Vec3d(0.0D, 3.0D, 1.0D), new Vec3d(-1.0D, 3.0D, 0.0D), new Vec3d(0.0D, 3.0D, 0.0D)};
      // $FF: synthetic field
      private static final Vec3d[] TRAPTOP = new Vec3d[]{new Vec3d(0.0D, 0.0D, -1.0D), new Vec3d(1.0D, 0.0D, 0.0D), new Vec3d(0.0D, 0.0D, 1.0D), new Vec3d(-1.0D, 0.0D, 0.0D), new Vec3d(0.0D, 1.0D, -1.0D), new Vec3d(1.0D, 1.0D, 0.0D), new Vec3d(0.0D, 1.0D, 1.0D), new Vec3d(-1.0D, 1.0D, 0.0D), new Vec3d(0.0D, 2.0D, -1.0D), new Vec3d(1.0D, 2.0D, 0.0D), new Vec3d(0.0D, 2.0D, 1.0D), new Vec3d(-1.0D, 2.0D, 0.0D), new Vec3d(0.0D, 3.0D, -1.0D), new Vec3d(0.0D, 3.0D, 0.0D), new Vec3d(0.0D, 4.0D, 0.0D)};
   }

   private static enum Cage {
      // $FF: synthetic field
      TRAPFULLROOF,
      // $FF: synthetic field
      CRYSTALEXA,
      // $FF: synthetic field
      TRAP,
      // $FF: synthetic field
      TRAPTOP,
      // $FF: synthetic field
      CRYSTAL,
      // $FF: synthetic field
      CRYSTALFULLROOF,
      // $FF: synthetic field
      TRAPFULLROOFTOP;
   }
}
