package com.elementars.eclient.util;

import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class AngleHelper {
   // $FF: synthetic field
   public static final double DEFAULT_EPSILON = 1.0E-9D;
   // $FF: synthetic field
   public static final double TWO_PI = 6.283185307179586D;
   // $FF: synthetic field
   public static final double HALF_PI = 1.5707963267948966D;
   // $FF: synthetic field
   public static final double QUARTER_PI = 0.7853981633974483D;
   // $FF: synthetic field
   public static final long DEFAULT_N = 1000000000L;

   public static Angle getAngleFacingInRadians(Vec3d var0) {
      double var1;
      double var3;
      if (var0.x == 0.0D && var0.z == 0.0D) {
         var3 = 0.0D;
         var1 = 1.5707963267948966D;
      } else {
         var3 = Math.atan2(var0.z, var0.x) - 1.5707963267948966D;
         double var5 = Math.sqrt(var0.x * var0.x + var0.z * var0.z);
         var1 = -Math.atan2(var0.y, var5);
      }

      return Angle.radians((float)var1, (float)var3);
   }

   public static double roundAngle(double var0) {
      return roundAngle(var0, 1000000000L);
   }

   public static float normalizeInDegrees(float var0) {
      return MathHelper.wrapDegrees(var0);
   }

   public static double normalizeInRadians(double var0) {
      while(var0 > 3.141592653589793D) {
         var0 -= 6.283185307179586D;
      }

      while(var0 < -3.141592653589793D) {
         var0 += 6.283185307179586D;
      }

      return var0;
   }

   public static boolean isAngleEqual(double var0, double var2) {
      return isAngleEqual(var0, var2, 1.0E-4D);
   }

   public static float normalizeInRadians(float var0) {
      while((double)var0 > 3.141592653589793D) {
         var0 = (float)((double)var0 - 6.283185307179586D);
      }

      while((double)var0 < -3.141592653589793D) {
         var0 = (float)((double)var0 + 6.283185307179586D);
      }

      return var0;
   }

   public static Angle getAngleFacingInDegrees(Vec3d var0) {
      return getAngleFacingInRadians(var0).inDegrees();
   }

   public static boolean isEqual(Angle var0, Angle var1) {
      Angle var2 = var0.normalize();
      Angle var3 = var1.same(var2).normalize();
      return isAngleEqual((double)var2.getPitch(), (double)var3.getPitch()) && isAngleEqual((double)var2.getYaw(), (double)var3.getYaw()) && isAngleEqual((double)var2.getRoll(), (double)var3.getRoll());
   }

   public static boolean isAngleEqual(double var0, double var2, double var4) {
      return Double.compare(var0, var2) == 0 || Math.abs(var0 - var2) < var4;
   }

   public static double roundAngle(double var0, long var2) {
      return (double)Math.round(var0 * (double)var2) / (double)var2;
   }

   public static double normalizeInDegrees(double var0) {
      return MathHelper.wrapDegrees(var0);
   }
}
