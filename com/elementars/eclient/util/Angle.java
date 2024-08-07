package com.elementars.eclient.util;

import java.util.Objects;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public abstract class Angle {
   // $FF: synthetic field
   private final float yaw;
   // $FF: synthetic field
   public static final Angle ZERO = degrees(0.0F, 0.0F, 0.0F);
   // $FF: synthetic field
   private final float roll;
   // $FF: synthetic field
   private final float pitch;

   public Angle setYaw(float var1) {
      return this.newInstance(this.getPitch(), var1, this.getRoll());
   }

   public Angle sub(Angle var1) {
      return this.add(var1.scale(-1.0F));
   }

   public float getYaw() {
      return this.yaw;
   }

   public static Angle radians(double var0, double var2) {
      return radians(var0, var2, 0.0D);
   }

   public abstract Angle inRadians();

   public float[] toArray() {
      return new float[]{this.getPitch(), this.getYaw(), this.getRoll()};
   }

   public Angle sub(float var1, float var2) {
      return this.sub(var1, var2, 0.0F);
   }

   protected Angle same(Angle var1) {
      return var1.isInDegrees() ? this.inDegrees() : this.inRadians();
   }

   public Angle scale(float var1) {
      return this.newInstance(this.getPitch() * var1, this.getYaw() * var1, this.getRoll() * var1);
   }

   public static Angle radians(float var0, float var1, float var2) {
      return new Angle.Radians(var0, var1, var2);
   }

   public boolean isInRadians() {
      return !this.isInDegrees();
   }

   public abstract boolean isInDegrees();

   public int hashCode() {
      Angle var1 = this.normalize().inDegrees();
      return Objects.hash(new Object[]{var1.getPitch(), var1.getYaw(), var1.getRoll()});
   }

   public Angle add(Angle var1) {
      return this.newInstance(this.getPitch() + var1.same(this).getPitch(), this.getYaw() + var1.same(this).getYaw(), this.getRoll() + var1.same(this).getRoll());
   }

   public static Angle radians(float var0, float var1) {
      return radians(var0, var1, 0.0F);
   }

   public String toString() {
      return String.format("(%.15f, %.15f, %.15f)[%s]", this.getPitch(), this.getYaw(), this.getRoll(), this.isInRadians() ? "rad" : "deg");
   }

   public Angle setPitch(float var1) {
      return this.newInstance(var1, this.getYaw(), this.getRoll());
   }

   public static Angle degrees(float var0, float var1, float var2) {
      return new Angle.Degrees(var0, var1, var2);
   }

   public Angle setRoll(float var1) {
      return this.newInstance(this.getPitch(), this.getYaw(), var1);
   }

   public boolean equals(Object var1) {
      return this == var1 || var1 instanceof Angle && AngleHelper.isEqual(this, (Angle)var1);
   }

   public float getPitch() {
      return this.pitch;
   }

   public float getRoll() {
      return this.roll;
   }

   public static Angle copy(Angle var0) {
      return var0.newInstance(var0.getPitch(), var0.getYaw(), var0.getRoll());
   }

   public Vec3d getDirectionVector() {
      float var1 = MathHelper.cos(-this.inDegrees().getYaw() * 0.017453292F - 3.1415927F);
      float var2 = MathHelper.sin(-this.inDegrees().getYaw() * 0.017453292F - 3.1415927F);
      float var3 = -MathHelper.cos(-this.inDegrees().getPitch() * 0.017453292F);
      float var4 = MathHelper.sin(-this.inDegrees().getPitch() * 0.017453292F);
      return new Vec3d((double)(var2 * var3), (double)var4, (double)(var1 * var3));
   }

   // $FF: synthetic method
   Angle(float var1, float var2, float var3, Object var4) {
      this(var1, var2, var3);
   }

   private Angle(float var1, float var2, float var3) {
      this.pitch = var1;
      this.yaw = var2;
      this.roll = var3;
   }

   public abstract Angle normalize();

   public Angle add(float var1, float var2) {
      return this.add(var1, var2, 0.0F);
   }

   public double[] getForwardVector() {
      double var1 = Math.sin((double)this.inRadians().getPitch());
      double var3 = Math.cos((double)this.inRadians().getPitch());
      double var5 = Math.sin((double)this.inRadians().getYaw());
      double var7 = Math.cos((double)this.inRadians().getYaw());
      return new double[]{var3 * var7, var1, var3 * var5};
   }

   public static Angle degrees(double var0, double var2, double var4) {
      return degrees((float)AngleHelper.roundAngle(var0), (float)AngleHelper.roundAngle(var2), (float)AngleHelper.roundAngle(var4));
   }

   public static Angle degrees(float var0, float var1) {
      return degrees(var0, var1, 0.0F);
   }

   public Angle add(float var1, float var2, float var3) {
      return this.add(this.newInstance(var1, var2, var3));
   }

   public static Angle degrees(double var0, double var2) {
      return degrees(var0, var2, 0.0D);
   }

   public static Angle radians(double var0, double var2, double var4) {
      return radians((float)AngleHelper.roundAngle(var0), (float)AngleHelper.roundAngle(var2), (float)AngleHelper.roundAngle(var4));
   }

   public abstract Angle inDegrees();

   public Angle sub(float var1, float var2, float var3) {
      return this.add(-var1, -var2, -var3);
   }

   protected abstract Angle newInstance(float var1, float var2, float var3);

   static class Radians extends Angle {
      // $FF: synthetic field
      private Angle.Degrees degrees;

      public boolean isInDegrees() {
         return false;
      }

      protected Angle newInstance(float var1, float var2, float var3) {
         return new Angle.Radians(var1, var2, var3);
      }

      private Radians(float var1, float var2, float var3) {
         super(var1, var2, var3, null);
         this.degrees = null;
      }

      public Angle inRadians() {
         return this;
      }

      // $FF: synthetic method
      Radians(float var1, float var2, float var3, Object var4) {
         this(var1, var2, var3);
      }

      public Angle inDegrees() {
         return this.degrees == null ? (this.degrees = (Angle.Degrees)degrees(Math.toDegrees((double)this.getPitch()), Math.toDegrees((double)this.getYaw()), Math.toDegrees((double)this.getRoll()))) : this.degrees;
      }

      public Angle normalize() {
         return this.newInstance(AngleHelper.normalizeInRadians(this.getPitch()), AngleHelper.normalizeInRadians(this.getYaw()), AngleHelper.normalizeInRadians(this.getRoll()));
      }
   }

   static class Degrees extends Angle {
      // $FF: synthetic field
      private Angle.Radians radians;

      public Angle inDegrees() {
         return this;
      }

      public Angle inRadians() {
         return this.radians == null ? (this.radians = (Angle.Radians)radians(Math.toRadians((double)this.getPitch()), Math.toRadians((double)this.getYaw()), Math.toRadians((double)this.getRoll()))) : this.radians;
      }

      protected Angle newInstance(float var1, float var2, float var3) {
         return new Angle.Degrees(var1, var2, var3);
      }

      private Degrees(float var1, float var2, float var3) {
         super(var1, var2, var3, null);
         this.radians = null;
      }

      // $FF: synthetic method
      Degrees(float var1, float var2, float var3, Object var4) {
         this(var1, var2, var3);
      }

      public Angle normalize() {
         return this.newInstance(AngleHelper.normalizeInDegrees(this.getPitch()), AngleHelper.normalizeInDegrees(this.getYaw()), AngleHelper.normalizeInDegrees(this.getRoll()));
      }

      public boolean isInDegrees() {
         return true;
      }
   }
}
