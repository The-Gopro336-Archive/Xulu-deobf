package com.elementars.eclient.module.render;

import com.elementars.eclient.Xulu;
import com.elementars.eclient.event.events.RenderEvent;
import com.elementars.eclient.friend.Friends;
import com.elementars.eclient.module.Category;
import com.elementars.eclient.module.Module;
import com.elementars.eclient.util.TargetPlayers;
import dev.xulu.newgui.util.ColorUtil;
import dev.xulu.settings.Value;
import java.awt.Color;
import java.util.HashMap;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.culling.ICamera;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.opengl.GL11;

public class Skeleton extends Module {
   // $FF: synthetic field
   private ICamera camera = new Frustum();
   // $FF: synthetic field
   private final Value red = this.register(new Value("Target Red", this, 255, 0, 255));
   // $FF: synthetic field
   private final Value friends = this.register(new Value("Friends", this, true));
   // $FF: synthetic field
   private final Value blue = this.register(new Value("TargetBlue", this, 0, 0, 255));
   // $FF: synthetic field
   private static final HashMap entities = new HashMap();
   // $FF: synthetic field
   private final Value color = this.register(new Value("Color Mode", this, "White", new String[]{"White", "ClickGui", "Tracers", "Target", "Rainbow"}));
   // $FF: synthetic field
   private final Value green = this.register(new Value("Target Green", this, 0, 0, 255));
   // $FF: synthetic field
   private final Value width = this.register(new Value("Width", this, 1.5F, 0.0F, 10.0F));

   private boolean doesntContain(EntityPlayer var1) {
      return !mc.world.playerEntities.contains(var1);
   }

   public static void addEntity(EntityPlayer var0, ModelPlayer var1) {
      entities.put(var0, new float[][]{{var1.bipedHead.rotateAngleX, var1.bipedHead.rotateAngleY, var1.bipedHead.rotateAngleZ}, {var1.bipedRightArm.rotateAngleX, var1.bipedRightArm.rotateAngleY, var1.bipedRightArm.rotateAngleZ}, {var1.bipedLeftLeg.rotateAngleX, var1.bipedLeftLeg.rotateAngleY, var1.bipedLeftLeg.rotateAngleZ}, {var1.bipedRightLeg.rotateAngleX, var1.bipedRightLeg.rotateAngleY, var1.bipedRightLeg.rotateAngleZ}, {var1.bipedLeftLeg.rotateAngleX, var1.bipedLeftLeg.rotateAngleY, var1.bipedLeftLeg.rotateAngleZ}});
   }

   public void onWorldRender(RenderEvent var1) {
      if (mc.getRenderManager() != null && mc.getRenderManager().options != null) {
         this.startEnd(true);
         GL11.glEnable(2903);
         GL11.glDisable(2848);
         entities.keySet().removeIf(this::doesntContain);
         mc.world.playerEntities.forEach((var2) -> {
            this.drawSkeleton(var1, var2);
         });
         Gui.drawRect(0, 0, 0, 0, 0);
         this.startEnd(false);
      }
   }

   private void drawSkeleton(RenderEvent var1, EntityPlayer var2) {
      double var3 = mc.player.lastTickPosX + (mc.player.posX - mc.player.lastTickPosX) * (double)var1.getPartialTicks();
      double var5 = mc.player.lastTickPosY + (mc.player.posY - mc.player.lastTickPosY) * (double)var1.getPartialTicks();
      double var7 = mc.player.lastTickPosZ + (mc.player.posZ - mc.player.lastTickPosZ) * (double)var1.getPartialTicks();
      this.camera.setPosition(var3, var5, var7);
      float[][] var9 = (float[][])entities.get(var2);
      if (var9 != null && var2.isEntityAlive() && this.camera.isBoundingBoxInFrustum(var2.getEntityBoundingBox()) && !var2.isDead && var2 != mc.player && !var2.isPlayerSleeping()) {
         GL11.glPushMatrix();
         GL11.glEnable(2848);
         GL11.glLineWidth((Float)this.width.getValue());
         this.glColor(var2);
         Vec3d var10 = this.getVec3(var1, var2);
         double var11 = var10.x - mc.getRenderManager().renderPosX;
         double var13 = var10.y - mc.getRenderManager().renderPosY;
         double var15 = var10.z - mc.getRenderManager().renderPosZ;
         GL11.glTranslated(var11, var13, var15);
         float var17 = var2.prevRenderYawOffset + (var2.renderYawOffset - var2.prevRenderYawOffset) * var1.getPartialTicks();
         GL11.glRotatef(-var17, 0.0F, 1.0F, 0.0F);
         GL11.glTranslated(0.0D, 0.0D, var2.isSneaking() ? -0.235D : 0.0D);
         float var18 = var2.isSneaking() ? 0.6F : 0.75F;
         GL11.glPushMatrix();
         this.glColor(var2);
         GL11.glTranslated(-0.125D, (double)var18, 0.0D);
         if (var9[3][0] != 0.0F) {
            GL11.glRotatef(var9[3][0] * 57.295776F, 1.0F, 0.0F, 0.0F);
         }

         if (var9[3][1] != 0.0F) {
            GL11.glRotatef(var9[3][1] * 57.295776F, 0.0F, 1.0F, 0.0F);
         }

         if (var9[3][2] != 0.0F) {
            GL11.glRotatef(var9[3][2] * 57.295776F, 0.0F, 0.0F, 1.0F);
         }

         GL11.glBegin(3);
         GL11.glVertex3d(0.0D, 0.0D, 0.0D);
         GL11.glVertex3d(0.0D, (double)(-var18), 0.0D);
         GL11.glEnd();
         GL11.glPopMatrix();
         GL11.glPushMatrix();
         this.glColor(var2);
         GL11.glTranslated(0.125D, (double)var18, 0.0D);
         if (var9[4][0] != 0.0F) {
            GL11.glRotatef(var9[4][0] * 57.295776F, 1.0F, 0.0F, 0.0F);
         }

         if (var9[4][1] != 0.0F) {
            GL11.glRotatef(var9[4][1] * 57.295776F, 0.0F, 1.0F, 0.0F);
         }

         if (var9[4][2] != 0.0F) {
            GL11.glRotatef(var9[4][2] * 57.295776F, 0.0F, 0.0F, 1.0F);
         }

         GL11.glBegin(3);
         GL11.glVertex3d(0.0D, 0.0D, 0.0D);
         GL11.glVertex3d(0.0D, (double)(-var18), 0.0D);
         GL11.glEnd();
         GL11.glPopMatrix();
         GL11.glTranslated(0.0D, 0.0D, var2.isSneaking() ? 0.25D : 0.0D);
         GL11.glPushMatrix();
         this.glColor(var2);
         GL11.glTranslated(0.0D, var2.isSneaking() ? -0.05D : 0.0D, var2.isSneaking() ? -0.01725D : 0.0D);
         GL11.glPushMatrix();
         this.glColor(var2);
         GL11.glTranslated(-0.375D, (double)var18 + 0.55D, 0.0D);
         if (var9[1][0] != 0.0F) {
            GL11.glRotatef(var9[1][0] * 57.295776F, 1.0F, 0.0F, 0.0F);
         }

         if (var9[1][1] != 0.0F) {
            GL11.glRotatef(var9[1][1] * 57.295776F, 0.0F, 1.0F, 0.0F);
         }

         if (var9[1][2] != 0.0F) {
            GL11.glRotatef(-var9[1][2] * 57.295776F, 0.0F, 0.0F, 1.0F);
         }

         GL11.glBegin(3);
         GL11.glVertex3d(0.0D, 0.0D, 0.0D);
         GL11.glVertex3d(0.0D, -0.5D, 0.0D);
         GL11.glEnd();
         GL11.glPopMatrix();
         GL11.glPushMatrix();
         GL11.glTranslated(0.375D, (double)var18 + 0.55D, 0.0D);
         if (var9[2][0] != 0.0F) {
            GL11.glRotatef(var9[2][0] * 57.295776F, 1.0F, 0.0F, 0.0F);
         }

         if (var9[2][1] != 0.0F) {
            GL11.glRotatef(var9[2][1] * 57.295776F, 0.0F, 1.0F, 0.0F);
         }

         if (var9[2][2] != 0.0F) {
            GL11.glRotatef(-var9[2][2] * 57.295776F, 0.0F, 0.0F, 1.0F);
         }

         GL11.glBegin(3);
         GL11.glVertex3d(0.0D, 0.0D, 0.0D);
         GL11.glVertex3d(0.0D, -0.5D, 0.0D);
         GL11.glEnd();
         GL11.glPopMatrix();
         GL11.glRotatef(var17 - var2.rotationYawHead, 0.0F, 1.0F, 0.0F);
         GL11.glPushMatrix();
         this.glColor(var2);
         GL11.glTranslated(0.0D, (double)var18 + 0.55D, 0.0D);
         if (var9[0][0] != 0.0F) {
            GL11.glRotatef(var9[0][0] * 57.295776F, 1.0F, 0.0F, 0.0F);
         }

         GL11.glBegin(3);
         GL11.glVertex3d(0.0D, 0.0D, 0.0D);
         GL11.glVertex3d(0.0D, 0.3D, 0.0D);
         GL11.glEnd();
         GL11.glPopMatrix();
         GL11.glPopMatrix();
         GL11.glRotatef(var2.isSneaking() ? 25.0F : 0.0F, 1.0F, 0.0F, 0.0F);
         GL11.glTranslated(0.0D, var2.isSneaking() ? -0.16175D : 0.0D, var2.isSneaking() ? -0.48025D : 0.0D);
         GL11.glPushMatrix();
         GL11.glTranslated(0.0D, (double)var18, 0.0D);
         GL11.glBegin(3);
         GL11.glVertex3d(-0.125D, 0.0D, 0.0D);
         GL11.glVertex3d(0.125D, 0.0D, 0.0D);
         GL11.glEnd();
         GL11.glPopMatrix();
         GL11.glPushMatrix();
         this.glColor(var2);
         GL11.glTranslated(0.0D, (double)var18, 0.0D);
         GL11.glBegin(3);
         GL11.glVertex3d(0.0D, 0.0D, 0.0D);
         GL11.glVertex3d(0.0D, 0.55D, 0.0D);
         GL11.glEnd();
         GL11.glPopMatrix();
         GL11.glPushMatrix();
         GL11.glTranslated(0.0D, (double)var18 + 0.55D, 0.0D);
         GL11.glBegin(3);
         GL11.glVertex3d(-0.375D, 0.0D, 0.0D);
         GL11.glVertex3d(0.375D, 0.0D, 0.0D);
         GL11.glEnd();
         GL11.glPopMatrix();
         GL11.glPopMatrix();
      }

   }

   private Vec3d getVec3(RenderEvent var1, EntityPlayer var2) {
      float var3 = var1.getPartialTicks();
      double var4 = var2.lastTickPosX + (var2.posX - var2.lastTickPosX) * (double)var3;
      double var6 = var2.lastTickPosY + (var2.posY - var2.lastTickPosY) * (double)var3;
      double var8 = var2.lastTickPosZ + (var2.posZ - var2.lastTickPosZ) * (double)var3;
      return new Vec3d(var4, var6, var8);
   }

   public Skeleton() {
      super("Skeleton", "Renders player entities skeletons", 0, Category.RENDER, true);
   }

   private void glColor(EntityPlayer var1) {
      if (Friends.isFriend(var1.getName()) && (Boolean)this.friends.getValue()) {
         GlStateManager.color(0.27F, 0.7F, 0.92F, 1.0F);
      } else {
         if (((String)this.color.getValue()).equalsIgnoreCase("ClickGui")) {
            GlStateManager.color((float)ColorUtil.getClickGUIColor().getRed() / 255.0F, (float)ColorUtil.getClickGUIColor().getGreen() / 255.0F, (float)ColorUtil.getClickGUIColor().getBlue() / 255.0F, 1.0F);
         } else if (((String)this.color.getValue()).equalsIgnoreCase("Tracers")) {
            float var2 = mc.player.getDistance(var1);
            if (var2 <= 32.0F) {
               GlStateManager.color(1.0F - var2 / 32.0F / 2.0F, var2 / 32.0F, 0.0F, 1.0F);
            } else {
               GlStateManager.color(0.0F, 0.9F, 0.0F, 1.0F);
            }
         } else if (((String)this.color.getValue()).equalsIgnoreCase("Target")) {
            if (TargetPlayers.targettedplayers.containsKey(var1.getName())) {
               GlStateManager.color((float)(Integer)this.red.getValue() / 255.0F, (float)(Integer)this.green.getValue() / 255.0F, (float)(Integer)this.blue.getValue() / 255.0F, 1.0F);
            } else {
               GlStateManager.color((float)ColorUtil.getClickGUIColor().getRed() / 255.0F, (float)ColorUtil.getClickGUIColor().getGreen() / 255.0F, (float)ColorUtil.getClickGUIColor().getBlue() / 255.0F, 1.0F);
            }
         } else if (((String)this.color.getValue()).equalsIgnoreCase("Rainbow")) {
            Color var3 = new Color(Xulu.rgb);
            GlStateManager.color((float)var3.getRed() / 255.0F, (float)var3.getGreen() / 255.0F, (float)var3.getBlue() / 255.0F, (float)var3.getAlpha() / 255.0F);
         } else {
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
         }

      }
   }

   private void startEnd(boolean var1) {
      if (var1) {
         GlStateManager.pushMatrix();
         GlStateManager.enableBlend();
         GL11.glEnable(2848);
         GlStateManager.disableDepth();
         GlStateManager.disableTexture2D();
         GL11.glHint(3154, 4354);
      } else {
         GlStateManager.disableBlend();
         GlStateManager.enableTexture2D();
         GL11.glDisable(2848);
         GlStateManager.enableDepth();
         GlStateManager.popMatrix();
      }

      GlStateManager.depthMask(!var1);
   }
}
