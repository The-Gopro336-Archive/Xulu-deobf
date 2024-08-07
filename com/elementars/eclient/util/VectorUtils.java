package com.elementars.eclient.util;

import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector4f;

public class VectorUtils implements Helper {
   // $FF: synthetic field
   static Matrix4f modelMatrix = new Matrix4f();
   // $FF: synthetic field
   static Matrix4f projectionMatrix = new Matrix4f();

   /** @deprecated */
   @Deprecated
   public static VectorUtils.ScreenPos _toScreen(double var0, double var2, double var4) {
      Plane var6 = toScreen(var0, var2, var4);
      return new VectorUtils.ScreenPos(var6.getX(), var6.getY(), var6.isVisible());
   }

   public static double getCrosshairDistance(Vec3d var0, Vec3d var1, Vec3d var2) {
      return var2.subtract(var0).normalize().subtract(var1).lengthSquared();
   }

   public static Plane toScreen(double var0, double var2, double var4) {
      Entity var6 = mc.getRenderViewEntity();
      if (var6 == null) {
         return new Plane(0.0D, 0.0D, false);
      } else {
         Vec3d var7 = ActiveRenderInfo.position;
         Vec3d var8 = ActiveRenderInfo.projectViewFromEntity(var6, (double)mc.getRenderPartialTicks());
         float var9 = (float)(var7.x + var8.x - (double)((float)var0));
         float var10 = (float)(var7.y + var8.y - (double)((float)var2));
         float var11 = (float)(var7.z + var8.z - (double)((float)var4));
         Vector4f var12 = new Vector4f(var9, var10, var11, 1.0F);
         modelMatrix.load(ActiveRenderInfo.MODELVIEW.asReadOnlyBuffer());
         projectionMatrix.load(ActiveRenderInfo.PROJECTION.asReadOnlyBuffer());
         VecTransformCoordinate(var12, modelMatrix);
         VecTransformCoordinate(var12, projectionMatrix);
         if (var12.w > 0.0F) {
            var12.x *= -100000.0F;
            var12.y *= -100000.0F;
         } else {
            float var13 = 1.0F / var12.w;
            var12.x *= var13;
            var12.y *= var13;
         }

         ScaledResolution var18 = new ScaledResolution(mc);
         float var14 = (float)var18.getScaledWidth() / 2.0F;
         float var15 = (float)var18.getScaledHeight() / 2.0F;
         var12.x = var14 + 0.5F * var12.x * (float)var18.getScaledWidth() + 0.5F;
         var12.y = var15 - (0.5F * var12.y * (float)var18.getScaledHeight() + 0.5F);
         boolean var16 = true;
         if (var12.x < 0.0F || var12.y < 0.0F || var12.x > (float)var18.getScaledWidth() || var12.y > (float)var18.getScaledHeight()) {
            var16 = false;
         }

         return new Plane((double)var12.x, (double)var12.y, var16);
      }
   }

   public static Vec3d copy(Vec3d var0) {
      return new Vec3d(var0.x, var0.y, var0.z);
   }

   public static Vec3d multiplyBy(Vec3d var0, Vec3d var1) {
      return new Vec3d(var0.x * var1.x, var0.y * var1.y, var0.z * var1.z);
   }

   /** @deprecated */
   @Deprecated
   public static VectorUtils.ScreenPos _toScreen(Vec3d var0) {
      return _toScreen(var0.x, var0.y, var0.z);
   }

   private static void VecTransformCoordinate(Vector4f var0, Matrix4f var1) {
      float var2 = var0.x;
      float var3 = var0.y;
      float var4 = var0.z;
      var0.x = var2 * var1.m00 + var3 * var1.m10 + var4 * var1.m20 + var1.m30;
      var0.y = var2 * var1.m01 + var3 * var1.m11 + var4 * var1.m21 + var1.m31;
      var0.z = var2 * var1.m02 + var3 * var1.m12 + var4 * var1.m22 + var1.m32;
      var0.w = var2 * var1.m03 + var3 * var1.m13 + var4 * var1.m23 + var1.m33;
   }

   public static Plane toScreen(Vec3d var0) {
      return toScreen(var0.x, var0.y, var0.z);
   }

   /** @deprecated */
   @Deprecated
   public static Object vectorAngle(Vec3d var0) {
      return null;
   }

   /** @deprecated */
   @Deprecated
   public static class ScreenPos {
      // $FF: synthetic field
      public final boolean isVisible;
      // $FF: synthetic field
      public final int x;
      // $FF: synthetic field
      public final double xD;
      // $FF: synthetic field
      public final double yD;
      // $FF: synthetic field
      public final int y;

      public ScreenPos(double var1, double var3, boolean var5) {
         this.x = (int)var1;
         this.y = (int)var3;
         this.xD = var1;
         this.yD = var3;
         this.isVisible = var5;
      }
   }
}
