package com.elementars.eclient.util;

import java.util.Iterator;
import java.util.List;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemEgg;
import net.minecraft.item.ItemEnderPearl;
import net.minecraft.item.ItemExpBottle;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemSnowball;
import net.minecraft.item.ItemSplashPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;

public class TrajectoryCalculator {
   public static Vec3d mult(Vec3d var0, float var1) {
      return new Vec3d(var0.x * (double)var1, var0.y * (double)var1, var0.z * (double)var1);
   }

   public static double interpolate(double var0, double var2) {
      return var2 + (var0 - var2) * (double)Wrapper.getMinecraft().getRenderPartialTicks();
   }

   public static double[] interpolate(Entity var0) {
      double var1 = interpolate(var0.posX, var0.lastTickPosX) - Wrapper.getMinecraft().renderManager.renderPosX;
      double var3 = interpolate(var0.posY, var0.lastTickPosY) - Wrapper.getMinecraft().renderManager.renderPosY;
      double var5 = interpolate(var0.posZ, var0.lastTickPosZ) - Wrapper.getMinecraft().renderManager.renderPosZ;
      return new double[]{var1, var3, var5};
   }

   public static Vec3d div(Vec3d var0, float var1) {
      return new Vec3d(var0.x / (double)var1, var0.y / (double)var1, var0.z / (double)var1);
   }

   public static TrajectoryCalculator.ThrowingType getThrowType(EntityLivingBase var0) {
      if (var0.getHeldItem(EnumHand.MAIN_HAND).isEmpty()) {
         return TrajectoryCalculator.ThrowingType.NONE;
      } else {
         ItemStack var1 = var0.getHeldItem(EnumHand.MAIN_HAND);
         Item var2 = var1.getItem();
         if (var2 instanceof ItemPotion) {
            if (var1.getItem() instanceof ItemSplashPotion) {
               return TrajectoryCalculator.ThrowingType.POTION;
            }
         } else {
            if (var2 instanceof ItemBow && var0.isHandActive()) {
               return TrajectoryCalculator.ThrowingType.BOW;
            }

            if (var2 instanceof ItemExpBottle) {
               return TrajectoryCalculator.ThrowingType.EXPERIENCE;
            }

            if (var2 instanceof ItemSnowball || var2 instanceof ItemEgg || var2 instanceof ItemEnderPearl) {
               return TrajectoryCalculator.ThrowingType.NORMAL;
            }
         }

         return TrajectoryCalculator.ThrowingType.NONE;
      }
   }

   public static final class FlightPath {
      // $FF: synthetic field
      private Vec3d motion;
      // $FF: synthetic field
      private TrajectoryCalculator.ThrowingType throwingType;
      // $FF: synthetic field
      private EntityLivingBase shooter;
      // $FF: synthetic field
      private float pitch;
      // $FF: synthetic field
      public Vec3d position;
      // $FF: synthetic field
      private float yaw;
      // $FF: synthetic field
      private boolean collided;
      // $FF: synthetic field
      private RayTraceResult target;
      // $FF: synthetic field
      private AxisAlignedBB boundingBox;

      public boolean isCollided() {
         return this.collided;
      }

      private void setThrowableHeading(Vec3d var1, float var2) {
         this.motion = TrajectoryCalculator.div(var1, (float)var1.length());
         this.motion = TrajectoryCalculator.mult(this.motion, var2);
      }

      private void setLocationAndAngles(double var1, double var3, double var5, float var7, float var8) {
         this.position = new Vec3d(var1, var3, var5);
         this.yaw = var7;
         this.pitch = var8;
      }

      private float getGravityVelocity() {
         switch(this.throwingType) {
         case BOW:
         case POTION:
            return 0.05F;
         case EXPERIENCE:
            return 0.07F;
         case NORMAL:
            return 0.03F;
         default:
            return 0.03F;
         }
      }

      public FlightPath(EntityLivingBase var1, TrajectoryCalculator.ThrowingType var2) {
         this.shooter = var1;
         this.throwingType = var2;
         double[] var3 = TrajectoryCalculator.interpolate(this.shooter);
         this.setLocationAndAngles(var3[0] + Wrapper.getMinecraft().getRenderManager().renderPosX, var3[1] + (double)this.shooter.getEyeHeight() + Wrapper.getMinecraft().getRenderManager().renderPosY, var3[2] + Wrapper.getMinecraft().getRenderManager().renderPosZ, this.shooter.rotationYaw, this.shooter.rotationPitch);
         Vec3d var4 = new Vec3d((double)(MathHelper.cos(this.yaw / 180.0F * 3.1415927F) * 0.16F), 0.1D, (double)(MathHelper.sin(this.yaw / 180.0F * 3.1415927F) * 0.16F));
         this.position = this.position.subtract(var4);
         this.setPosition(this.position);
         this.motion = new Vec3d((double)(-MathHelper.sin(this.yaw / 180.0F * 3.1415927F) * MathHelper.cos(this.pitch / 180.0F * 3.1415927F)), (double)(-MathHelper.sin(this.pitch / 180.0F * 3.1415927F)), (double)(MathHelper.cos(this.yaw / 180.0F * 3.1415927F) * MathHelper.cos(this.pitch / 180.0F * 3.1415927F)));
         this.setThrowableHeading(this.motion, this.getInitialVelocity());
      }

      private void setPosition(Vec3d var1) {
         this.position = new Vec3d(var1.x, var1.y, var1.z);
         double var2 = (this.throwingType == TrajectoryCalculator.ThrowingType.BOW ? 0.5D : 0.25D) / 2.0D;
         this.boundingBox = new AxisAlignedBB(var1.x - var2, var1.y - var2, var1.z - var2, var1.x + var2, var1.y + var2, var1.z + var2);
      }

      public void onUpdate() {
         Vec3d var1 = this.position.add(this.motion);
         RayTraceResult var2 = this.shooter.getEntityWorld().rayTraceBlocks(this.position, var1, false, true, false);
         if (var2 != null) {
            var1 = var2.hitVec;
         }

         this.onCollideWithEntity(var1, var2);
         if (this.target != null) {
            this.collided = true;
            this.setPosition(this.target.hitVec);
         } else if (this.position.y <= 0.0D) {
            this.collided = true;
         } else {
            this.position = this.position.add(this.motion);
            float var3 = 0.99F;
            if (this.shooter.getEntityWorld().isMaterialInBB(this.boundingBox, Material.WATER)) {
               var3 = this.throwingType == TrajectoryCalculator.ThrowingType.BOW ? 0.6F : 0.8F;
            }

            this.motion = TrajectoryCalculator.mult(this.motion, var3);
            this.motion = this.motion.subtract(0.0D, (double)this.getGravityVelocity(), 0.0D);
            this.setPosition(this.position);
         }
      }

      public RayTraceResult getCollidingTarget() {
         return this.target;
      }

      private void onCollideWithEntity(Vec3d var1, RayTraceResult var2) {
         Entity var3 = null;
         double var4 = 0.0D;
         List var6 = this.shooter.world.getEntitiesWithinAABBExcludingEntity(this.shooter, this.boundingBox.expand(this.motion.x, this.motion.y, this.motion.z).expand(1.0D, 1.0D, 1.0D));
         Iterator var7 = var6.iterator();

         while(true) {
            Entity var8;
            double var12;
            do {
               RayTraceResult var11;
               do {
                  do {
                     if (!var7.hasNext()) {
                        if (var3 != null) {
                           this.target = new RayTraceResult(var3);
                        } else {
                           this.target = var2;
                        }

                        return;
                     }

                     var8 = (Entity)var7.next();
                  } while(!var8.canBeCollidedWith() && var8 != this.shooter);

                  float var9 = var8.getCollisionBorderSize();
                  AxisAlignedBB var10 = var8.getEntityBoundingBox().expand((double)var9, (double)var9, (double)var9);
                  var11 = var10.calculateIntercept(this.position, var1);
               } while(var11 == null);

               var12 = this.position.distanceTo(var11.hitVec);
            } while(!(var12 < var4) && var4 != 0.0D);

            var3 = var8;
            var4 = var12;
         }
      }

      private float getInitialVelocity() {
         Item var1 = this.shooter.getHeldItem(EnumHand.MAIN_HAND).getItem();
         switch(this.throwingType) {
         case BOW:
            ItemBow var2 = (ItemBow)var1;
            int var3 = var2.getMaxItemUseDuration(this.shooter.getHeldItem(EnumHand.MAIN_HAND)) - this.shooter.getItemInUseCount();
            float var4 = (float)var3 / 20.0F;
            var4 = (var4 * var4 + var4 * 2.0F) / 3.0F;
            if (var4 > 1.0F) {
               var4 = 1.0F;
            }

            return var4 * 2.0F * 1.5F;
         case POTION:
            return 0.5F;
         case EXPERIENCE:
            return 0.7F;
         case NORMAL:
            return 1.5F;
         default:
            return 1.5F;
         }
      }
   }

   public static enum ThrowingType {
      // $FF: synthetic field
      NORMAL,
      // $FF: synthetic field
      EXPERIENCE,
      // $FF: synthetic field
      POTION,
      // $FF: synthetic field
      BOW,
      // $FF: synthetic field
      NONE;
   }
}
