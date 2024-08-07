package com.elementars.eclient.util;

import net.minecraft.client.Minecraft;
import net.minecraft.potion.Potion;

public final class MovementUtils implements Helper {
   public static void setSpeedStrafe(double var0) {
      Minecraft.getMinecraft().player.motionX = -Math.sin(getDirection()) * var0;
      Minecraft.getMinecraft().player.motionZ = Math.cos(getDirection()) * var0;
   }

   public static double getSpeedStrafe() {
      return Math.sqrt(Math.pow(Minecraft.getMinecraft().player.motionX, 2.0D) + Math.pow(Minecraft.getMinecraft().player.motionX, 2.0D));
   }

   public static double[] forward2(double var0) {
      float var2 = mc.player.movementInput.moveForward;
      float var3 = mc.player.movementInput.moveStrafe;
      float var4 = mc.player.prevRotationYaw + (mc.player.rotationYaw - mc.player.prevRotationYaw) * mc.getRenderPartialTicks();
      if (var2 != 0.0F) {
         if (var3 > 0.0F) {
            var4 += (float)(var2 > 0.0F ? -45 : 45);
         } else if (var3 < 0.0F) {
            var4 += (float)(var2 > 0.0F ? 45 : -45);
         }

         var3 = 0.0F;
         if (var2 > 0.0F) {
            var2 = 1.0F;
         } else if (var2 < 0.0F) {
            var2 = -1.0F;
         }
      }

      double var5 = Math.sin(Math.toRadians((double)(var4 + 90.0F)));
      double var7 = Math.cos(Math.toRadians((double)(var4 + 90.0F)));
      double var9 = (double)var2 * var0 * var7 + (double)var3 * var0 * var5;
      double var11 = (double)var2 * var0 * var5 - (double)var3 * var0 * var7;
      return new double[]{var9, var11};
   }

   public static boolean hasMotion() {
      return Wrapper.getMinecraft().player.motionX != 0.0D && Wrapper.getMinecraft().player.motionZ != 0.0D && Wrapper.getMinecraft().player.motionY != 0.0D;
   }

   public static boolean isMoving() {
      return Wrapper.getMinecraft().player != null && (Wrapper.getMinecraft().player.movementInput.moveForward != 0.0F || Wrapper.getMinecraft().player.movementInput.moveStrafe != 0.0F);
   }

   public static double getBaseMoveSpeed() {
      double var0 = 0.2873D;
      if (mc.player != null && mc.player.isPotionActive(Potion.getPotionById(1))) {
         int var2 = mc.player.getActivePotionEffect(Potion.getPotionById(1)).getAmplifier();
         var0 *= 1.0D + 0.2D * (double)(var2 + 1);
      }

      return var0;
   }

   public static void forward(double var0) {
      double var2 = Math.toRadians((double)Wrapper.getMinecraft().player.rotationYaw);
      Wrapper.getMinecraft().player.setPosition(Wrapper.getMinecraft().player.posX + -Math.sin(var2) * var0, Wrapper.getMinecraft().player.posY, Wrapper.getMinecraft().player.posZ + Math.cos(var2) * var0);
   }

   public static float getSpeed() {
      return (float)Math.sqrt(Wrapper.getMinecraft().player.motionX * Wrapper.getMinecraft().player.motionX + Wrapper.getMinecraft().player.motionZ * Wrapper.getMinecraft().player.motionZ);
   }

   public static void strafe() {
      strafe(getSpeed());
   }

   public static double getDirectionStrafe() {
      return Math.toRadians((double)Minecraft.getMinecraft().player.rotationYaw);
   }

   public static double getDirection() {
      float var0 = Wrapper.getMinecraft().player.rotationYaw;
      if (Wrapper.getMinecraft().player.moveForward < 0.0F) {
         var0 += 180.0F;
      }

      float var1 = 1.0F;
      if (Wrapper.getMinecraft().player.moveForward < 0.0F) {
         var1 = -0.5F;
      } else if (Wrapper.getMinecraft().player.moveForward > 0.0F) {
         var1 = 0.5F;
      }

      if (Wrapper.getMinecraft().player.moveStrafing > 0.0F) {
         var0 -= 90.0F * var1;
      }

      if (Wrapper.getMinecraft().player.moveStrafing < 0.0F) {
         var0 += 90.0F * var1;
      }

      return Math.toRadians((double)var0);
   }

   public static void strafe(float var0) {
      if (isMoving()) {
         double var1 = getDirection();
         Wrapper.getMinecraft().player.motionX = -Math.sin(var1) * (double)var0;
         Wrapper.getMinecraft().player.motionZ = Math.cos(var1) * (double)var0;
      }
   }
}
