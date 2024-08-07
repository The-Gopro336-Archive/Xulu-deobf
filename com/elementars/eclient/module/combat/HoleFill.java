package com.elementars.eclient.module.combat;

import com.elementars.eclient.Xulu;
import com.elementars.eclient.enemy.Enemies;
import com.elementars.eclient.friend.Friends;
import com.elementars.eclient.module.Category;
import com.elementars.eclient.module.Module;
import com.elementars.eclient.util.BlockInteractionHelper;
import com.elementars.eclient.util.BoolSwitch;
import com.elementars.eclient.util.TargetPlayers;
import com.mojang.realmsclient.gui.ChatFormatting;
import dev.xulu.settings.Value;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
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

public class HoleFill extends Module {
   // $FF: synthetic field
   BlockPos pos;
   // $FF: synthetic field
   public final Value noGlitchBlocks = this.register(new Value("NoGlitchBlocks", this, true));
   // $FF: synthetic field
   private final Value triggerable = this.register(new Value("Triggerable", this, true));
   // $FF: synthetic field
   private int waitCounter;
   // $FF: synthetic field
   private final Value useEC = this.register(new Value("UseEnderchests", this, false));
   // $FF: synthetic field
   int delay;
   // $FF: synthetic field
   private final Value waitTick = this.register(new Value("TickDelay", this, 1, 0, 10));
   // $FF: synthetic field
   private final Value pre = this.register(new Value("Prioritize Enemies", this, false));
   // $FF: synthetic field
   private List whiteList;
   // $FF: synthetic field
   private ArrayList holes = new ArrayList();
   // $FF: synthetic field
   private final Value rotate = this.register(new Value("Rotate", this, true));
   // $FF: synthetic field
   private final Value chat = this.register(new Value("Chat", this, false));
   // $FF: synthetic field
   private final Value yRange = this.register(new Value("YRange", this, 2, 1, 10));
   // $FF: synthetic field
   private final Value range = this.register(new Value("Range", this, 5, 1, 10));

   public void onEnable() {
      this.delay = 20;
      if (mc.player != null && (Boolean)this.chat.getValue()) {
         this.sendDebugMessage(String.valueOf((new StringBuilder()).append(ChatFormatting.GREEN).append("Enabled!")));
      }

   }

   public void onDisable() {
      if (mc.player != null && (Boolean)this.chat.getValue()) {
         this.sendDebugMessage(String.valueOf((new StringBuilder()).append(ChatFormatting.RED).append("Disabled!")));
      }

   }

   private static PlayerControllerMP getPlayerController() {
      return mc.playerController;
   }

   public HoleFill() {
      super("HoleFill", "Fills holes", 0, Category.COMBAT, true);
      this.whiteList = Arrays.asList(Blocks.OBSIDIAN);
   }

   private void place(BlockPos var1) {
      Iterator var2 = mc.world.getEntitiesWithinAABBExcludingEntity((Entity)null, new AxisAlignedBB(var1)).iterator();

      Entity var3;
      do {
         if (!var2.hasNext()) {
            placeBlockScaffold(var1, (Boolean)this.rotate.getValue());
            ++this.waitCounter;
            return;
         }

         var3 = (Entity)var2.next();
      } while(!(var3 instanceof EntityLivingBase));

   }

   public void onUpdate() {
      if ((Boolean)this.triggerable.getValue()) {
         if (this.delay > 0) {
            --this.delay;
         } else {
            this.toggle();
         }
      }

      this.holes = new ArrayList();
      if ((Boolean)this.useEC.getValue()) {
         if (!this.whiteList.contains(Blocks.ENDER_CHEST)) {
            this.whiteList.add(Blocks.ENDER_CHEST);
         }
      } else {
         this.whiteList.remove(Blocks.ENDER_CHEST);
      }

      Iterable var1 = BlockPos.getAllInBox(mc.player.getPosition().add(-(Integer)this.range.getValue(), -(Integer)this.yRange.getValue(), -(Integer)this.range.getValue()), mc.player.getPosition().add((double)(Integer)this.range.getValue(), (double)(Integer)this.yRange.getValue(), (double)(Integer)this.range.getValue()));
      Iterator var2 = var1.iterator();

      while(true) {
         BlockPos var3;
         do {
            do {
               if (!var2.hasNext()) {
                  int var6 = -1;

                  int var7;
                  for(var7 = 0; var7 < 9; ++var7) {
                     ItemStack var8 = mc.player.inventory.getStackInSlot(var7);
                     if (var8 != ItemStack.EMPTY && var8.getItem() instanceof ItemBlock) {
                        Block var5 = ((ItemBlock)var8.getItem()).getBlock();
                        if (this.whiteList.contains(var5)) {
                           var6 = var7;
                           break;
                        }
                     }
                  }

                  if (var6 == -1) {
                     return;
                  }

                  var7 = mc.player.inventory.currentItem;
                  BoolSwitch var9 = new BoolSwitch(false);
                  TargetPlayers.targettedplayers.keySet().stream().map((var0) -> {
                     return mc.world.getPlayerEntityByName(var0);
                  }).filter(Objects::nonNull).filter((var1x) -> {
                     return mc.player.getDistance(var1x) <= (float)(Integer)this.range.getValue();
                  }).min(Comparator.comparing((var0) -> {
                     return mc.player.getDistance(var0);
                  })).ifPresent((var2x) -> {
                     var9.setValue(true);
                     ArrayList var10000 = this.holes;
                     var2x.getClass();
                     var10000.sort(Comparator.comparing(var2x::func_174818_b));
                  });
                  if (!var9.isValue()) {
                     mc.world.playerEntities.stream().filter((var0) -> {
                        return !Friends.isFriend(var0.getName());
                     }).filter((var1x) -> {
                        return mc.player.getDistance(var1x) <= (float)(Integer)this.range.getValue();
                     }).min(Comparator.comparing((var0) -> {
                        return mc.player.getDistance(var0);
                     })).ifPresent((var1x) -> {
                        ArrayList var10000 = this.holes;
                        var1x.getClass();
                        var10000.sort(Comparator.comparing(var1x::func_174818_b));
                     });
                     if ((Boolean)this.pre.getValue()) {
                        mc.world.playerEntities.stream().filter((var0) -> {
                           return !Friends.isFriend(var0.getName()) && Enemies.isEnemy(var0.getName());
                        }).filter((var1x) -> {
                           return mc.player.getDistance(var1x) <= (float)(Integer)this.range.getValue();
                        }).min(Comparator.comparing((var0) -> {
                           return mc.player.getDistance(var0);
                        })).ifPresent((var1x) -> {
                           ArrayList var10000 = this.holes;
                           var1x.getClass();
                           var10000.sort(Comparator.comparing(var1x::func_174818_b));
                        });
                     }
                  }

                  if ((double)(Integer)this.waitTick.getValue() > 0.0D) {
                     if (this.waitCounter < (Integer)this.waitTick.getValue()) {
                        mc.player.inventory.currentItem = var6;
                        this.holes.forEach(this::place);
                        mc.player.inventory.currentItem = var7;
                        return;
                     }

                     this.waitCounter = 0;
                  }

                  return;
               }

               var3 = (BlockPos)var2.next();
            } while(mc.world.getBlockState(var3).getMaterial().blocksMovement());
         } while(mc.world.getBlockState(var3.add(0, 1, 0)).getMaterial().blocksMovement());

         boolean var4 = mc.world.getBlockState(var3.add(1, 0, 0)).getBlock() == Blocks.BEDROCK | mc.world.getBlockState(var3.add(1, 0, 0)).getBlock() == Blocks.OBSIDIAN && mc.world.getBlockState(var3.add(0, 0, 1)).getBlock() == Blocks.BEDROCK | mc.world.getBlockState(var3.add(0, 0, 1)).getBlock() == Blocks.OBSIDIAN && mc.world.getBlockState(var3.add(-1, 0, 0)).getBlock() == Blocks.BEDROCK | mc.world.getBlockState(var3.add(-1, 0, 0)).getBlock() == Blocks.OBSIDIAN && mc.world.getBlockState(var3.add(0, 0, -1)).getBlock() == Blocks.BEDROCK | mc.world.getBlockState(var3.add(0, 0, -1)).getBlock() == Blocks.OBSIDIAN && mc.world.getBlockState(var3.add(0, 0, 0)).getMaterial() == Material.AIR && mc.world.getBlockState(var3.add(0, 1, 0)).getMaterial() == Material.AIR && mc.world.getBlockState(var3.add(0, 2, 0)).getMaterial() == Material.AIR;
         if (var4) {
            this.holes.add(var3);
         }
      }
   }

   public static boolean placeBlockScaffold(BlockPos var0, boolean var1) {
      new Vec3d(mc.player.posX, mc.player.posY + (double)mc.player.getEyeHeight(), mc.player.posZ);
      EnumFacing[] var3 = EnumFacing.values();
      int var4 = var3.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         EnumFacing var6 = var3[var5];
         BlockPos var7 = var0.offset(var6);
         EnumFacing var8 = var6.getOpposite();
         if (BlockInteractionHelper.canBeClicked(var7)) {
            Vec3d var9 = (new Vec3d(var7)).add(0.5D, 0.5D, 0.5D).add((new Vec3d(var8.getDirectionVec())).scale(0.5D));
            if (var1) {
               BlockInteractionHelper.faceVectorPacketInstant(var9);
            }

            mc.player.connection.sendPacket(new CPacketEntityAction(mc.player, Action.START_SNEAKING));
            processRightClickBlock(var7, var8, var9);
            mc.player.swingArm(EnumHand.MAIN_HAND);
            mc.rightClickDelayTimer = 0;
            mc.player.connection.sendPacket(new CPacketEntityAction(mc.player, Action.STOP_SNEAKING));
            if ((Boolean)((HoleFill)Xulu.MODULE_MANAGER.getModuleT(HoleFill.class)).noGlitchBlocks.getValue() && !Surround.mc.playerController.getCurrentGameType().equals(GameType.CREATIVE)) {
               Surround.mc.player.connection.sendPacket(new CPacketPlayerDigging(net.minecraft.network.play.client.CPacketPlayerDigging.Action.START_DESTROY_BLOCK, var7, var8));
            }

            return true;
         }
      }

      return false;
   }

   public static void processRightClickBlock(BlockPos var0, EnumFacing var1, Vec3d var2) {
      getPlayerController().processRightClickBlock(mc.player, mc.world, var0, var1, var2, EnumHand.MAIN_HAND);
   }
}
