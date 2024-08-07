package com.elementars.eclient.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public final class MathUtil {
   public static Vec3d direction(float var0) {
      return new Vec3d(Math.cos(degToRad((double)(var0 + 90.0F))), 0.0D, Math.sin(degToRad((double)(var0 + 90.0F))));
   }

   public static double radToDeg(double var0) {
      return var0 * 57.295780181884766D;
   }

   public static double degToRad(double var0) {
      return var0 * 0.01745329238474369D;
   }

   public static Vec3d div(Vec3d var0, Vec3d var1) {
      return new Vec3d(var0.x / var1.x, var0.y / var1.y, var0.z / var1.z);
   }

   public static double[] directionSpeed(double var0) {
      Minecraft var2 = Minecraft.getMinecraft();
      float var3 = var2.player.movementInput.moveForward;
      float var4 = var2.player.movementInput.moveStrafe;
      float var5 = var2.player.prevRotationYaw + (var2.player.rotationYaw - var2.player.prevRotationYaw) * var2.getRenderPartialTicks();
      if (var3 != 0.0F) {
         if (var4 > 0.0F) {
            var5 += (float)(var3 > 0.0F ? -45 : 45);
         } else if (var4 < 0.0F) {
            var5 += (float)(var3 > 0.0F ? 45 : -45);
         }

         var4 = 0.0F;
         if (var3 > 0.0F) {
            var3 = 1.0F;
         } else if (var3 < 0.0F) {
            var3 = -1.0F;
         }
      }

      double var6 = Math.sin(Math.toRadians((double)(var5 + 90.0F)));
      double var8 = Math.cos(Math.toRadians((double)(var5 + 90.0F)));
      double var10 = (double)var3 * var0 * var8 + (double)var4 * var0 * var6;
      double var12 = (double)var3 * var0 * var6 - (double)var4 * var0 * var8;
      return new double[]{var10, var12};
   }

   public static float wrap(float var0) {
      var0 %= 360.0F;
      if (var0 >= 180.0F) {
         var0 -= 360.0F;
      }

      if (var0 < -180.0F) {
         var0 += 360.0F;
      }

      return var0;
   }

   public static double getDistance(Vec3d var0, double var1, double var3, double var5) {
      double var7 = var0.x - var1;
      double var9 = var0.y - var3;
      double var11 = var0.z - var5;
      return (double)MathHelper.sqrt(var7 * var7 + var9 * var9 + var11 * var11);
   }

   public static Vec3d mult(Vec3d var0, float var1) {
      return new Vec3d(var0.x * (double)var1, var0.y * (double)var1, var0.z * (double)var1);
   }

   public static Vec3d mult(Vec3d var0, Vec3d var1) {
      return new Vec3d(var0.x * var1.x, var0.y * var1.y, var0.z * var1.z);
   }

   public static double map(double var0, double var2, double var4, double var6, double var8) {
      var0 = (var0 - var2) / (var4 - var2);
      return var6 + var0 * (var8 - var6);
   }

   public static int ensureRange(int var0, int var1, int var2) {
      int var3 = Math.min(Math.max(var0, var1), var2);
      return var3;
   }

   public static Vec3d div(Vec3d var0, float var1) {
      return new Vec3d(var0.x / (double)var1, var0.y / (double)var1, var0.z / (double)var1);
   }

   public static double round(double var0, int var2) {
      return var2 < 0 ? var0 : (new BigDecimal(var0)).setScale(var2, RoundingMode.HALF_UP).doubleValue();
   }

   public static Vec3d interpolateEntity(Entity var0, float var1) {
      return new Vec3d(var0.lastTickPosX + (var0.posX - var0.lastTickPosX) * (double)var1, var0.lastTickPosY + (var0.posY - var0.lastTickPosY) * (double)var1, var0.lastTickPosZ + (var0.posZ - var0.lastTickPosZ) * (double)var1);
   }

   public static double linear(double var0, double var2, double var4) {
      return var0 < var2 - var4 ? var0 + var4 : (var0 > var2 + var4 ? var0 - var4 : var2);
   }

   public static float clamp(float var0, float var1, float var2) {
      if (var0 <= var1) {
         var0 = var1;
      }

      if (var0 >= var2) {
         var0 = var2;
      }

      return var0;
   }

   public static float[] calcAngle(Vec3d var0, Vec3d var1) {
      double var2 = var1.x - var0.x;
      double var4 = (var1.y - var0.y) * -1.0D;
      double var6 = var1.z - var0.z;
      double var8 = (double)MathHelper.sqrt(var2 * var2 + var6 * var6);
      return new float[]{(float)MathHelper.wrapDegrees(Math.toDegrees(Math.atan2(var6, var2)) - 90.0D), (float)MathHelper.wrapDegrees(Math.toDegrees(Math.atan2(var4, var8)))};
   }

   public static double parabolic(double var0, double var2, double var4) {
      return var0 + (var2 - var0) / var4;
   }
}
