package com.elementars.eclient.module.render;

import com.elementars.eclient.Xulu;
import com.elementars.eclient.event.events.RenderEvent;
import com.elementars.eclient.friend.Friends;
import com.elementars.eclient.module.Category;
import com.elementars.eclient.module.Module;
import com.elementars.eclient.util.ColorUtils;
import com.elementars.eclient.util.EntityUtil;
import com.elementars.eclient.util.HueCycler;
import dev.xulu.settings.Value;
import java.awt.Color;
import java.util.Objects;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.opengl.GL11;

public class Tracers extends Module {
   // $FF: synthetic field
   private final Value opacity = this.register(new Value("Opacity", this, 1.0F, 0.0F, 1.0F));
   // $FF: synthetic field
   private final Value spine = this.register(new Value("Spine", this, false));
   // $FF: synthetic field
   private final Value friends = this.register(new Value("Friends", this, true));
   // $FF: synthetic field
   private final Value mobs = this.register(new Value("Mobs", this, false));
   // $FF: synthetic field
   private final Value range = this.register(new Value("Range", this, 200.0F, 1.0F, 1000.0F));
   // $FF: synthetic field
   private final Value players = this.register(new Value("Players", this, true));
   // $FF: synthetic field
   private final Value animals = this.register(new Value("Animals", this, false));
   // $FF: synthetic field
   HueCycler cycler = new HueCycler(3600);

   public void onDisable() {
      EVENT_BUS.unregister(this);
   }

   public static void drawLineFromPosToPos(double var0, double var2, double var4, double var6, double var8, double var10, double var12, float var14, float var15, float var16, float var17) {
      GL11.glBlendFunc(770, 771);
      GL11.glEnable(3042);
      GL11.glLineWidth(1.5F);
      GL11.glDisable(3553);
      GL11.glDisable(2929);
      GL11.glDepthMask(false);
      GL11.glColor4f(var14, var15, var16, var17);
      GL11.glEnable(2848);
      GL11.glHint(3154, 4354);
      GL11.glLoadIdentity();
      boolean var18 = mc.gameSettings.viewBobbing;
      mc.gameSettings.viewBobbing = false;
      mc.entityRenderer.orientCamera(mc.getRenderPartialTicks());
      GL11.glBegin(1);
      GL11.glVertex3d(var0, var2, var4);
      GL11.glVertex3d(var6, var8, var10);
      if ((Boolean)((Tracers)Xulu.MODULE_MANAGER.getModuleT(Tracers.class)).spine.getValue()) {
         GL11.glVertex3d(var6, var8, var10);
         GL11.glVertex3d(var6, var8 + var12, var10);
      }

      GL11.glEnd();
      GL11.glEnable(3553);
      GL11.glEnable(2929);
      GL11.glDepthMask(true);
      GL11.glDisable(3042);
      GL11.glDisable(2848);
      GL11.glColor3d(1.0D, 1.0D, 1.0D);
      mc.gameSettings.viewBobbing = var18;
   }

   public void onUpdate() {
      this.cycler.next();
   }

   public static void drawLine(double var0, double var2, double var4, double var6, float var8, float var9, float var10, float var11) {
      Vec3d var12 = (new Vec3d(0.0D, 0.0D, 1.0D)).rotatePitch(-((float)Math.toRadians((double)Minecraft.getMinecraft().player.rotationPitch))).rotateYaw(-((float)Math.toRadians((double)Minecraft.getMinecraft().player.rotationYaw)));
      drawLineFromPosToPos(var12.x, var12.y + (double)mc.player.getEyeHeight(), var12.z, var0, var2, var4, var6, var8, var9, var10, var11);
   }

   public static void drawLineToEntity(Entity var0, float var1, float var2, float var3, float var4) {
      double[] var5 = interpolate(var0);
      drawLine(var5[0], var5[1], var5[2], (double)var0.height, var1, var2, var3, var4);
   }

   private int getColour(Entity var1) {
      if (var1 instanceof EntityPlayer) {
         if (Friends.isFriend(var1.getName())) {
            return (new Color(0.27F, 0.7F, 0.92F)).getRGB();
         } else {
            float var2 = mc.player.getDistance(var1);
            return var2 <= 32.0F ? (new Color(1.0F - var2 / 32.0F / 2.0F, var2 / 32.0F, 0.0F)).getRGB() : (new Color(0.0F, 0.9F, 0.0F)).getRGB();
         }
      } else {
         return EntityUtil.isPassive(var1) ? ColorUtils.Colors.GREEN : ColorUtils.Colors.RED;
      }
   }

   public static double interpolate(double var0, double var2) {
      return var2 + (var0 - var2) * (double)mc.getRenderPartialTicks();
   }

   public void onWorldRender(RenderEvent var1) {
      GlStateManager.pushMatrix();
      Minecraft.getMinecraft().world.loadedEntityList.stream().filter(EntityUtil::isLiving).filter((var0) -> {
         return !EntityUtil.isFakeLocalPlayer(var0);
      }).filter((var1x) -> {
         return var1x instanceof EntityPlayer ? (Boolean)this.players.getValue() && mc.player != var1x : (EntityUtil.isPassive(var1x) ? (Boolean)this.animals.getValue() : (Boolean)this.mobs.getValue());
      }).filter((var1x) -> {
         return mc.player.getDistance(var1x) < (Float)this.range.getValue();
      }).forEach((var1x) -> {
         int var2 = this.getColour(var1x);
         if (var2 == Integer.MIN_VALUE) {
            if (!(Boolean)this.friends.getValue()) {
               return;
            }

            var2 = this.cycler.current();
         }

         float var3 = (float)(var2 >>> 16 & 255) / 255.0F;
         float var4 = (float)(var2 >>> 8 & 255) / 255.0F;
         float var5 = (float)(var2 & 255) / 255.0F;
         drawLineToEntity(var1x, var3, var4, var5, (Float)this.opacity.getValue());
      });
      GlStateManager.popMatrix();
   }

   private Tracers.EntityType getType(Entity var1) {
      if (!EntityUtil.isDrivenByPlayer(var1) && !EntityUtil.isFakeLocalPlayer(var1)) {
         if (EntityUtil.isPlayer(var1)) {
            return Tracers.EntityType.PLAYER;
         } else if (EntityUtil.isPassive(var1)) {
            return Tracers.EntityType.ANIMAL;
         } else {
            return EntityUtil.isPassive(var1) && !(var1 instanceof EntitySpider) ? Tracers.EntityType.HOSTILE : Tracers.EntityType.HOSTILE;
         }
      } else {
         return Tracers.EntityType.INVALID;
      }
   }

   public void onEnable() {
      EVENT_BUS.register(this);
   }

   public Tracers() {
      super("Tracers", "Draws a line to entities in render distance", 0, Category.RENDER, true);
   }

   private void drawRainbowToEntity(Entity var1, float var2) {
      Vec3d var3 = (new Vec3d(0.0D, 0.0D, 1.0D)).rotatePitch(-((float)Math.toRadians((double)Minecraft.getMinecraft().player.rotationPitch))).rotateYaw(-((float)Math.toRadians((double)Minecraft.getMinecraft().player.rotationYaw)));
      double[] var4 = interpolate(var1);
      double var5 = var4[0];
      double var7 = var4[1];
      double var9 = var4[2];
      double var11 = var3.x;
      double var13 = var3.y + (double)mc.player.getEyeHeight();
      double var15 = var3.z;
      GL11.glBlendFunc(770, 771);
      GL11.glEnable(3042);
      GL11.glLineWidth(1.5F);
      GL11.glDisable(3553);
      GL11.glDisable(2929);
      GL11.glDepthMask(false);
      this.cycler.reset();
      this.cycler.setNext(var2);
      GlStateManager.disableLighting();
      GL11.glLoadIdentity();
      mc.entityRenderer.orientCamera(mc.getRenderPartialTicks());
      GL11.glBegin(1);
      GL11.glVertex3d(var5, var7, var9);
      GL11.glVertex3d(var11, var13, var15);
      this.cycler.setNext(var2);
      GL11.glVertex3d(var11, var13, var15);
      GL11.glVertex3d(var11, var13, var15);
      GL11.glEnd();
      GL11.glEnable(3553);
      GL11.glEnable(2929);
      GL11.glDepthMask(true);
      GL11.glDisable(3042);
      GL11.glColor3d(1.0D, 1.0D, 1.0D);
      GlStateManager.enableLighting();
   }

   public static double[] interpolate(Entity var0) {
      double var1 = interpolate(var0.posX, var0.lastTickPosX) - mc.getRenderManager().renderPosX;
      double var3 = interpolate(var0.posY, var0.lastTickPosY) - mc.getRenderManager().renderPosY;
      double var5 = interpolate(var0.posZ, var0.lastTickPosZ) - mc.getRenderManager().renderPosZ;
      return new double[]{var1, var3, var5};
   }

   private static enum EntityType {
      // $FF: synthetic field
      ANIMAL,
      // $FF: synthetic field
      INVALID,
      // $FF: synthetic field
      HOSTILE,
      // $FF: synthetic field
      PLAYER;
   }

   private class EntityRelations implements Comparable {
      // $FF: synthetic field
      private final Entity entity;
      // $FF: synthetic field
      private final Tracers.EntityType entityType;

      public boolean isOptionEnabled() {
         switch(this.entityType) {
         case PLAYER:
            return (Boolean)Tracers.this.players.getValue();
         case HOSTILE:
            return (Boolean)Tracers.this.mobs.getValue();
         default:
            return (Boolean)Tracers.this.animals.getValue();
         }
      }

      public Entity getEntity() {
         return this.entity;
      }

      public EntityRelations(Entity var2) {
         Objects.requireNonNull(var2);
         this.entity = var2;
         this.entityType = Tracers.this.getType(var2);
      }

      public Tracers.EntityType getEntityType() {
         return this.entityType;
      }

      public float getDepth() {
         switch(this.entityType) {
         case PLAYER:
            return 15.0F;
         case HOSTILE:
            return 10.0F;
         case ANIMAL:
            return 5.0F;
         default:
            return 0.0F;
         }
      }

      public Color getColor() {
         switch(this.entityType) {
         case PLAYER:
            return Color.YELLOW;
         case HOSTILE:
            return Color.RED;
         default:
            return Color.GREEN;
         }
      }

      public int compareTo(Tracers.EntityRelations var1) {
         return this.getEntityType().compareTo(var1.getEntityType());
      }
   }
}
