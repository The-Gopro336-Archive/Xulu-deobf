package com.elementars.eclient.module.combat;

import com.elementars.eclient.friend.Friends;
import com.elementars.eclient.module.Category;
import com.elementars.eclient.module.Module;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CPacketPlayer.Rotation;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class AutoWeb extends Module {
   // $FF: synthetic field
   int delay;
   // $FF: synthetic field
   public static float pitch;
   // $FF: synthetic field
   public static List targets;
   // $FF: synthetic field
   public static EntityPlayer target;
   // $FF: synthetic field
   public static float yaw;
   // $FF: synthetic field
   BlockPos feet;
   // $FF: synthetic field
   BlockPos head;

   public void loadTargets() {
      Iterator var1 = mc.world.playerEntities.iterator();

      while(var1.hasNext()) {
         EntityPlayer var2 = (EntityPlayer)var1.next();
         if (!(var2 instanceof EntityPlayerSP)) {
            if (this.isValid(var2)) {
               targets.add(var2);
            } else if (targets.contains(var2)) {
               targets.remove(var2);
            }
         }
      }

   }

   private boolean isStackObby(ItemStack var1) {
      return var1 != null && var1.getItem() == Item.getItemById(30);
   }

   public static IBlockState getState(BlockPos var0) {
      return mc.world.getBlockState(var0);
   }

   public BlockPos getBlockPos(double var1, double var3, double var5) {
      return new BlockPos(var1, var3, var5);
   }

   private void trap(EntityPlayer var1) {
      if ((double)var1.moveForward == 0.0D && (double)var1.moveStrafing == 0.0D && (double)var1.moveVertical == 0.0D) {
         ++this.delay;
      }

      if ((double)var1.moveForward != 0.0D || (double)var1.moveStrafing != 0.0D || (double)var1.moveVertical != 0.0D) {
         this.delay = 0;
      }

      if (!this.doesHotbarHaveObby()) {
         this.delay = 0;
      }

      if (this.delay == 2 && this.doesHotbarHaveObby()) {
         this.head = new BlockPos(var1.posX, var1.posY + 1.0D, var1.posZ);
         this.feet = new BlockPos(var1.posX, var1.posY, var1.posZ);

         for(int var2 = 36; var2 < 45; ++var2) {
            ItemStack var3 = mc.player.inventoryContainer.getSlot(var2).getStack();
            if (var3 != null && this.isStackObby(var3)) {
               int var4 = mc.player.inventory.currentItem;
               if (mc.world.getBlockState(this.head).getMaterial().isReplaceable() || mc.world.getBlockState(this.feet).getMaterial().isReplaceable()) {
                  mc.player.inventory.currentItem = var2 - 36;
                  if (mc.world.getBlockState(this.head).getMaterial().isReplaceable()) {
                     placeBlockLegit(this.head);
                  }

                  if (mc.world.getBlockState(this.feet).getMaterial().isReplaceable()) {
                     placeBlockLegit(this.feet);
                  }

                  mc.player.inventory.currentItem = var4;
                  this.delay = 0;
                  break;
               }

               this.delay = 0;
            }

            this.delay = 0;
         }
      }

   }

   public void onUpdate() {
      if (!mc.player.isHandActive()) {
         if (!this.isValid(target) || target == null) {
            this.updateTarget();
         }

         Iterator var1 = mc.world.playerEntities.iterator();

         while(var1.hasNext()) {
            EntityPlayer var2 = (EntityPlayer)var1.next();
            if (!(var2 instanceof EntityPlayerSP) && this.isValid(var2) && var2.getDistance(mc.player) < target.getDistance(mc.player)) {
               target = var2;
               return;
            }
         }

         if (this.isValid(target) && mc.player.getDistance(target) < 4.0F) {
            this.trap(target);
         } else {
            this.delay = 0;
         }

      }
   }

   public static Block getBlock(BlockPos var0) {
      return getState(var0).getBlock();
   }

   public void onDisable() {
      this.delay = 0;
      yaw = mc.player.rotationYaw;
      pitch = mc.player.rotationPitch;
      target = null;
   }

   public void updateTarget() {
      Iterator var1 = mc.world.playerEntities.iterator();

      while(var1.hasNext()) {
         EntityPlayer var2 = (EntityPlayer)var1.next();
         if (!(var2 instanceof EntityPlayerSP) && !(var2 instanceof EntityPlayerSP) && this.isValid(var2)) {
            target = var2;
         }
      }

   }

   public boolean isInBlockRange(Entity var1) {
      return var1.getDistance(mc.player) <= 4.0F;
   }

   public static boolean placeBlockLegit(BlockPos var0) {
      Vec3d var1 = new Vec3d(mc.player.posX, mc.player.posY + (double)mc.player.getEyeHeight(), mc.player.posZ);
      Vec3d var2 = (new Vec3d(var0)).add(0.5D, 0.5D, 0.5D);
      EnumFacing[] var3 = EnumFacing.values();
      int var4 = var3.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         EnumFacing var6 = var3[var5];
         BlockPos var7 = var0.offset(var6);
         if (canBeClicked(var7)) {
            Vec3d var8 = var2.add((new Vec3d(var6.getDirectionVec())).scale(0.5D));
            if (var1.squareDistanceTo(var8) <= 36.0D) {
               mc.playerController.processRightClickBlock(mc.player, mc.world, var7, var6.getOpposite(), var8, EnumHand.MAIN_HAND);
               mc.player.swingArm(EnumHand.MAIN_HAND);

               try {
                  TimeUnit.MILLISECONDS.sleep(10L);
               } catch (InterruptedException var10) {
                  var10.printStackTrace();
               }

               return true;
            }
         }
      }

      return false;
   }

   public EnumFacing getEnumFacing(float var1, float var2, float var3) {
      return EnumFacing.getFacingFromVector(var1, var2, var3);
   }

   public void onEnable() {
      this.delay = 0;
   }

   public static double roundToHalf(double var0) {
      return (double)Math.round(var0 * 2.0D) / 2.0D;
   }

   public boolean isValid(EntityPlayer var1) {
      return var1 instanceof EntityPlayer && this.isInBlockRange(var1) && var1.getHealth() > 0.0F && !var1.isDead && !var1.getName().startsWith("Body #") && !Friends.isFriend(var1.getName());
   }

   private boolean doesHotbarHaveObby() {
      for(int var1 = 36; var1 < 45; ++var1) {
         ItemStack var2 = mc.player.inventoryContainer.getSlot(var1).getStack();
         if (var2 != null && this.isStackObby(var2)) {
            return true;
         }
      }

      return false;
   }

   public AutoWeb() {
      super("AutoWeb", "AutoTrap with webs", 0, Category.COMBAT, true);
   }

   public static boolean canBeClicked(BlockPos var0) {
      return mc.world.getBlockState(var0).getBlock().canCollideCheck(mc.world.getBlockState(var0), false);
   }

   private static void faceVectorPacket(Vec3d var0) {
      double var1 = var0.x - mc.player.posX;
      double var3 = var0.y - mc.player.posY + (double)mc.player.getEyeHeight();
      double var5 = var0.z - mc.player.posZ;
      double var7 = (double)MathHelper.sqrt(var1 * var1 + var5 * var5);
      float var9 = (float)Math.toDegrees(Math.atan2(var5, var1)) - 90.0F;
      float var10 = (float)(-Math.toDegrees(Math.atan2(var3, var7)));
      mc.getConnection().sendPacket(new Rotation(mc.player.rotationYaw + MathHelper.wrapDegrees(var9 - mc.player.rotationYaw), mc.player.rotationPitch + MathHelper.wrapDegrees(var10 - mc.player.rotationPitch), mc.player.onGround));
   }
}
