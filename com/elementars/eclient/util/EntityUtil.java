package com.elementars.eclient.util;

import net.minecraft.block.BlockLiquid;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.passive.EntityAmbientCreature;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class EntityUtil {
   public static boolean isHostileMob(Entity var0) {
      return var0.isCreatureType(EnumCreatureType.MONSTER, false) && !isNeutralMob(var0);
   }

   public static Vec3d getInterpolatedEyePos(Entity var0, float var1) {
      return getInterpolatedPos(var0, var1).add(0.0D, (double)var0.getEyeHeight(), 0.0D);
   }

   public static boolean isLiving(Entity var0) {
      return var0 instanceof EntityLivingBase;
   }

   public static Vec3d getInterpolatedPos(Entity var0, float var1) {
      return (new Vec3d(var0.lastTickPosX, var0.lastTickPosY, var0.lastTickPosZ)).add(getInterpolatedAmount(var0, (double)var1));
   }

   public static Vec3d getInterpolatedAmount(Entity var0, Vec3d var1) {
      return getInterpolatedAmount(var0, var1.x, var1.y, var1.z);
   }

   public static double[] calculateLookAt(double var0, double var2, double var4, EntityPlayer var6) {
      double var7 = var6.posX - var0;
      double var9 = var6.posY - var2;
      double var11 = var6.posZ - var4;
      double var13 = Math.sqrt(var7 * var7 + var9 * var9 + var11 * var11);
      var7 /= var13;
      var9 /= var13;
      var11 /= var13;
      double var15 = Math.asin(var9);
      double var17 = Math.atan2(var11, var7);
      var15 = var15 * 180.0D / 3.141592653589793D;
      var17 = var17 * 180.0D / 3.141592653589793D;
      var17 += 90.0D;
      return new double[]{var17, var15};
   }

   public static Vec3d getInterpolatedAmount(Entity var0, double var1) {
      return getInterpolatedAmount(var0, var1, var1, var1);
   }

   public static double getRelativeZ(float var0) {
      return (double)MathHelper.cos(var0 * 0.017453292F);
   }

   public static Vec3d getInterpolatedAmount(Entity var0, double var1, double var3, double var5) {
      return new Vec3d((var0.posX - var0.lastTickPosX) * var1, (var0.posY - var0.lastTickPosY) * var3, (var0.posZ - var0.lastTickPosZ) * var5);
   }

   public static boolean isMobAggressive(Entity var0) {
      if (var0 instanceof EntityPigZombie) {
         if (((EntityPigZombie)var0).isArmsRaised() || ((EntityPigZombie)var0).isAngry()) {
            return true;
         }
      } else {
         if (var0 instanceof EntityWolf) {
            return ((EntityWolf)var0).isAngry() && !Wrapper.getPlayer().equals(((EntityWolf)var0).getOwner());
         }

         if (var0 instanceof EntityEnderman) {
            return ((EntityEnderman)var0).isScreaming();
         }
      }

      return isHostileMob(var0);
   }

   public static boolean isAboveWater(Entity var0) {
      return isAboveWater(var0, false);
   }

   public static boolean isPassive(Entity var0) {
      if (var0 instanceof EntityWolf && ((EntityWolf)var0).isAngry()) {
         return false;
      } else if (!(var0 instanceof EntityAnimal) && !(var0 instanceof EntityAgeable) && !(var0 instanceof EntityTameable) && !(var0 instanceof EntityAmbientCreature) && !(var0 instanceof EntitySquid)) {
         return var0 instanceof EntityIronGolem && ((EntityIronGolem)var0).getRevengeTarget() == null;
      } else {
         return true;
      }
   }

   public static boolean isFriendlyMob(Entity var0) {
      return var0.isCreatureType(EnumCreatureType.CREATURE, false) && !isNeutralMob(var0) || var0.isCreatureType(EnumCreatureType.AMBIENT, false) || var0 instanceof EntityVillager || var0 instanceof EntityIronGolem || isNeutralMob(var0) && !isMobAggressive(var0);
   }

   public static Vec3d getInterpolatedRenderPos(Entity var0, float var1) {
      return getInterpolatedPos(var0, var1).subtract(Wrapper.getMinecraft().getRenderManager().renderPosX, Wrapper.getMinecraft().getRenderManager().renderPosY, Wrapper.getMinecraft().getRenderManager().renderPosZ);
   }

   public static double getRelativeX(float var0) {
      return (double)MathHelper.sin(-var0 * 0.017453292F);
   }

   public static boolean isDrivenByPlayer(Entity var0) {
      return Wrapper.getPlayer() != null && var0 != null && var0.equals(Wrapper.getPlayer().getRidingEntity());
   }

   public static boolean isPlayer(Entity var0) {
      return var0 instanceof EntityPlayer;
   }

   public static boolean isNeutralMob(Entity var0) {
      return var0 instanceof EntityPigZombie || var0 instanceof EntityWolf || var0 instanceof EntityEnderman;
   }

   public static boolean isFakeLocalPlayer(Entity var0) {
      return var0 != null && var0.getEntityId() == -100 && Wrapper.getPlayer() != var0;
   }

   public static boolean isInWater(Entity var0) {
      if (var0 == null) {
         return false;
      } else {
         double var1 = var0.posY + 0.01D;

         for(int var3 = MathHelper.floor(var0.posX); var3 < MathHelper.ceil(var0.posX); ++var3) {
            for(int var4 = MathHelper.floor(var0.posZ); var4 < MathHelper.ceil(var0.posZ); ++var4) {
               BlockPos var5 = new BlockPos(var3, (int)var1, var4);
               if (Wrapper.getWorld().getBlockState(var5).getBlock() instanceof BlockLiquid) {
                  return true;
               }
            }
         }

         return false;
      }
   }

   public static boolean isAboveWater(Entity var0, boolean var1) {
      if (var0 == null) {
         return false;
      } else {
         double var2 = var0.posY - (var1 ? 0.03D : (isPlayer(var0) ? 0.2D : 0.5D));

         for(int var4 = MathHelper.floor(var0.posX); var4 < MathHelper.ceil(var0.posX); ++var4) {
            for(int var5 = MathHelper.floor(var0.posZ); var5 < MathHelper.ceil(var0.posZ); ++var5) {
               BlockPos var6 = new BlockPos(var4, MathHelper.floor(var2), var5);
               if (Wrapper.getWorld().getBlockState(var6).getBlock() instanceof BlockLiquid) {
                  return true;
               }
            }
         }

         return false;
      }
   }
}
