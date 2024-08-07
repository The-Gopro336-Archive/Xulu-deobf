package com.elementars.eclient.module.render;

import com.elementars.eclient.Xulu;
import com.elementars.eclient.event.Event;
import com.elementars.eclient.event.EventTarget;
import com.elementars.eclient.event.events.EventModelRender;
import com.elementars.eclient.event.events.EventPostRenderLayers;
import com.elementars.eclient.friend.Friends;
import com.elementars.eclient.module.Category;
import com.elementars.eclient.module.Module;
import com.elementars.eclient.util.EntityUtil;
import com.elementars.eclient.util.MathUtil;
import com.elementars.eclient.util.OutlineUtils;
import com.elementars.eclient.util.OutlineUtils2;
import com.elementars.eclient.util.TargetPlayers;
import com.elementars.eclient.util.Wrapper;
import dev.xulu.newgui.util.ColorUtil;
import dev.xulu.settings.Value;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.culling.ICamera;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.opengl.GL11;

public class OutlineESP extends Module {
   // $FF: synthetic field
   public final Value mode = this.register(new Value("Mode", this, "Outline", new ArrayList(Arrays.asList("Outline", "Wireframe", "Solid", "Shader"))));
   // $FF: synthetic field
   public final Value chams = this.register(new Value("Chams", this, false));
   // $FF: synthetic field
   public final Value width = this.register(new Value("Line Width", this, 1.0F, 0.0F, 10.0F));
   // $FF: synthetic field
   private final Value blue = this.register(new Value("TargetBlue", this, 0, 0, 255));
   // $FF: synthetic field
   public final Value color = this.register(new Value("Color Mode", this, "Tracers", new String[]{"ClickGui", "Tracers", "Target", "Rainbow"}));
   // $FF: synthetic field
   private final Value green = this.register(new Value("Target Green", this, 0, 0, 255));
   // $FF: synthetic field
   public static boolean krOE;
   // $FF: synthetic field
   public final Value crystals = this.register(new Value("Crystals", this, true));
   // $FF: synthetic field
   public final Value renderEntities = this.register(new Value("Render Entities", this, true));
   // $FF: synthetic field
   public static boolean feQE;
   // $FF: synthetic field
   float gamma;
   // $FF: synthetic field
   ICamera camera = new Frustum();
   // $FF: synthetic field
   public static Value future;
   // $FF: synthetic field
   public final Value mobs = this.register(new Value("Mobs", this, true));
   // $FF: synthetic field
   public final Value renderCrystals = this.register(new Value("Render Crystals", this, true));
   // $FF: synthetic field
   boolean fancyGraphics;
   // $FF: synthetic field
   private final Value red = this.register(new Value("Target Red", this, 255, 0, 255));
   // $FF: synthetic field
   public final Value animals = this.register(new Value("Animals", this, true));
   // $FF: synthetic field
   public final Value players = this.register(new Value("Players", this, true));
   // $FF: synthetic field
   public final Value friends = this.register(new Value("Friends", this, true));
   // $FF: synthetic field
   public final Value onTop = this.register(new Value("OnTop", this, true));

   public static void renderNormal(float var0) {
      Iterator var1 = Wrapper.getMinecraft().world.loadedEntityList.iterator();

      while(var1.hasNext()) {
         Entity var2 = (Entity)var1.next();
         if (var2 != Wrapper.getMinecraft().getRenderViewEntity() && var2 instanceof AbstractClientPlayer) {
            AbstractClientPlayer var3 = (AbstractClientPlayer)var2;
            double var4 = var2.lastTickPosX + (var2.posX - var2.lastTickPosX) * (double)var0;
            double var6 = var2.lastTickPosY + (var2.posY - var2.lastTickPosY) * (double)var0;
            double var8 = var2.lastTickPosZ + (var2.posZ - var2.lastTickPosZ) * (double)var0;
            mc.renderGlobal.renderManager.playerRenderer.doRender(var3, var4 - mc.renderManager.renderPosX, var6 - mc.renderManager.renderPosY, var8 - mc.renderManager.renderPosZ, var2.rotationYaw, var0);
         }
      }

   }

   public static void renderColor(float var0) {
      Iterator var1 = Wrapper.getMinecraft().world.loadedEntityList.iterator();

      while(true) {
         Entity var2;
         do {
            do {
               if (!var1.hasNext()) {
                  return;
               }

               var2 = (Entity)var1.next();
            } while(var2 == Wrapper.getMinecraft().getRenderViewEntity());
         } while(!(var2 instanceof AbstractClientPlayer));

         AbstractClientPlayer var3 = (AbstractClientPlayer)var2;
         double var4 = var2.lastTickPosX + (var2.posX - var2.lastTickPosX) * (double)var0;
         double var6 = var2.lastTickPosY + (var2.posY - var2.lastTickPosY) * (double)var0;
         double var8 = var2.lastTickPosZ + (var2.posZ - var2.lastTickPosZ) * (double)var0;
         if (var2 instanceof EntityPlayer) {
            GL11.glColor3f(5.0F, 255.0F, 240.0F);
         } else if (var2 instanceof EntityEnderCrystal) {
            OutlineUtils2.setColor(ColorUtil.getClickGUIColor());
         } else if (EntityUtil.isPassive(var2)) {
            if ((Boolean)future.getValue()) {
               OutlineUtils2.setColor(new Color(0, 196, 0));
            } else {
               OutlineUtils2.setColor(new Color(5, 255, 240));
            }
         } else if (EntityUtil.isPassive(var2) && !(var2 instanceof EntitySpider)) {
            OutlineUtils2.setColor(new Color(1.0F, 1.0F, 0.0F));
         } else if ((Boolean)future.getValue()) {
            OutlineUtils2.setColor(new Color(191, 57, 59));
         } else {
            OutlineUtils2.setColor(new Color(255, 0, 102));
         }

         mc.renderGlobal.renderManager.playerRenderer.doRender(var3, var4 - mc.renderManager.renderPosX, var6 - mc.renderManager.renderPosY, var8 - mc.renderManager.renderPosZ, var2.rotationYaw, var0);
      }
   }

   public OutlineESP() {
      super("OutlineESP", "Outlines entities", 0, Category.RENDER, true);
      future = this.register(new Value("Future Colors", this, true));
   }

   @EventTarget
   public void renderPost(EventPostRenderLayers var1) {
      if (var1.renderer.bindEntityTexture(var1.entity)) {
         Vec3d var2 = MathUtil.interpolateEntity(mc.player, var1.getPartialTicks());
         this.camera.setPosition(var2.x, var2.y, var2.z);
         if (this.camera.isBoundingBoxInFrustum(var1.entity.getEntityBoundingBox())) {
            if (var1.getEventState() == Event.State.PRE) {
               this.fancyGraphics = mc.gameSettings.fancyGraphics;
               mc.gameSettings.fancyGraphics = false;
               this.gamma = mc.gameSettings.gammaSetting;
               mc.gameSettings.gammaSetting = 10000.0F;
               if ((Boolean)this.onTop.getValue() && (Boolean)this.renderEntities.getValue()) {
                  var1.modelBase.render(var1.entity, var1.limbSwing, var1.limbSwingAmount, var1.ageInTicks, var1.netHeadYaw, var1.headPitch, var1.scaleIn);
               }

               if (((String)this.mode.getValue()).equalsIgnoreCase("Shader")) {
                  if (var1.entity instanceof EntityOtherPlayerMP && (Boolean)this.players.getValue()) {
                     var1.entity.setGlowing(true);
                  } else if ((Boolean)this.animals.getValue() && EntityUtil.isPassive(var1.entity)) {
                     var1.entity.setGlowing(true);
                  } else if ((Boolean)this.mobs.getValue() && !EntityUtil.isPassive(var1.entity)) {
                     var1.entity.setGlowing(true);
                  } else {
                     var1.entity.setGlowing(false);
                  }
               } else {
                  var1.entity.setGlowing(false);
                  if (!((String)this.mode.getValue()).equalsIgnoreCase("Outline")) {
                     if (var1.entity instanceof EntityOtherPlayerMP && (Boolean)this.players.getValue()) {
                        GL11.glPushMatrix();
                        GL11.glEnable(32823);
                        GL11.glPolygonOffset(1.0F, -100000.0F);
                        GL11.glPushAttrib(1048575);
                        if (((String)this.mode.getValue()).equalsIgnoreCase("Solid")) {
                           GL11.glPolygonMode(1028, 6913);
                        } else {
                           GL11.glPolygonMode(1032, 6913);
                        }

                        GL11.glDisable(3553);
                        GL11.glDisable(2896);
                        GL11.glDisable(2929);
                        GL11.glEnable(2848);
                        GL11.glEnable(3042);
                        GL11.glBlendFunc(770, 771);
                        OutlineUtils.setColor(this.getColor((EntityOtherPlayerMP)var1.entity));
                        GL11.glLineWidth((Float)this.width.getValue());
                        var1.modelBase.render(var1.entity, var1.limbSwing, var1.limbSwingAmount, var1.ageInTicks, var1.netHeadYaw, var1.headPitch, var1.scaleIn);
                        GL11.glPopAttrib();
                        GL11.glPolygonOffset(1.0F, 100000.0F);
                        GL11.glDisable(32823);
                        GL11.glPopMatrix();
                     } else if ((Boolean)this.animals.getValue() && EntityUtil.isPassive(var1.entity)) {
                        GL11.glPushMatrix();
                        GL11.glPushAttrib(1048575);
                        GL11.glPolygonMode(1032, 6913);
                        GL11.glDisable(3553);
                        GL11.glDisable(2896);
                        GL11.glDisable(2929);
                        GL11.glEnable(2848);
                        GL11.glEnable(3042);
                        GL11.glBlendFunc(770, 771);
                        OutlineUtils.setColor(getEntityColor(var1.entity));
                        GL11.glLineWidth((Float)this.width.getValue());
                        var1.modelBase.render(var1.entity, var1.limbSwing, var1.limbSwingAmount, var1.ageInTicks, var1.netHeadYaw, var1.headPitch, var1.scaleIn);
                        GL11.glPopAttrib();
                        GL11.glPopMatrix();
                     } else if ((Boolean)this.mobs.getValue() && !EntityUtil.isPassive(var1.entity)) {
                        GL11.glPushMatrix();
                        GL11.glPushAttrib(1048575);
                        GL11.glPolygonMode(1032, 6913);
                        GL11.glDisable(3553);
                        GL11.glDisable(2896);
                        GL11.glDisable(2929);
                        GL11.glEnable(2848);
                        GL11.glEnable(3042);
                        GL11.glBlendFunc(770, 771);
                        OutlineUtils.setColor(getEntityColor(var1.entity));
                        GL11.glLineWidth((Float)this.width.getValue());
                        var1.modelBase.render(var1.entity, var1.limbSwing, var1.limbSwingAmount, var1.ageInTicks, var1.netHeadYaw, var1.headPitch, var1.scaleIn);
                        GL11.glPopAttrib();
                        GL11.glPopMatrix();
                     }
                  } else {
                     Color var3;
                     if (var1.entity instanceof EntityOtherPlayerMP && (Boolean)this.players.getValue()) {
                        var3 = this.getColor((EntityOtherPlayerMP)var1.entity);
                        var1.modelBase.render(var1.entity, var1.limbSwing, var1.limbSwingAmount, var1.ageInTicks, var1.netHeadYaw, var1.headPitch, var1.scaleIn);
                        OutlineUtils.renderOne((Float)this.width.getValue());
                        var1.modelBase.render(var1.entity, var1.limbSwing, var1.limbSwingAmount, var1.ageInTicks, var1.netHeadYaw, var1.headPitch, var1.scaleIn);
                        OutlineUtils.renderTwo();
                        var1.modelBase.render(var1.entity, var1.limbSwing, var1.limbSwingAmount, var1.ageInTicks, var1.netHeadYaw, var1.headPitch, var1.scaleIn);
                        OutlineUtils.renderThree();
                        OutlineUtils.renderFour(var3);
                        OutlineUtils.setColor(var3);
                        var1.modelBase.render(var1.entity, var1.limbSwing, var1.limbSwingAmount, var1.ageInTicks, var1.netHeadYaw, var1.headPitch, var1.scaleIn);
                        OutlineUtils.renderFive();
                        OutlineUtils.setColor(Color.WHITE);
                     } else if ((Boolean)this.animals.getValue() && EntityUtil.isPassive(var1.entity)) {
                        if ((Boolean)future.getValue()) {
                           var3 = new Color(0, 196, 0);
                        } else {
                           var3 = new Color(5, 255, 240);
                        }

                        GL11.glLineWidth(5.0F);
                        var1.modelBase.render(var1.entity, var1.limbSwing, var1.limbSwingAmount, var1.ageInTicks, var1.netHeadYaw, var1.headPitch, var1.scaleIn);
                        OutlineUtils.renderOne((Float)this.width.getValue());
                        var1.modelBase.render(var1.entity, var1.limbSwing, var1.limbSwingAmount, var1.ageInTicks, var1.netHeadYaw, var1.headPitch, var1.scaleIn);
                        OutlineUtils.renderTwo();
                        var1.modelBase.render(var1.entity, var1.limbSwing, var1.limbSwingAmount, var1.ageInTicks, var1.netHeadYaw, var1.headPitch, var1.scaleIn);
                        OutlineUtils.renderThree();
                        OutlineUtils.renderFour(var3);
                        var1.modelBase.render(var1.entity, var1.limbSwing, var1.limbSwingAmount, var1.ageInTicks, var1.netHeadYaw, var1.headPitch, var1.scaleIn);
                        OutlineUtils.renderFive();
                     } else if ((Boolean)this.mobs.getValue() && !EntityUtil.isPassive(var1.entity) && !(var1.entity instanceof EntityPlayer)) {
                        if ((Boolean)future.getValue()) {
                           var3 = new Color(191, 57, 59);
                        } else {
                           var3 = new Color(255, 0, 102);
                        }

                        GL11.glLineWidth(5.0F);
                        var1.modelBase.render(var1.entity, var1.limbSwing, var1.limbSwingAmount, var1.ageInTicks, var1.netHeadYaw, var1.headPitch, var1.scaleIn);
                        OutlineUtils.renderOne((Float)this.width.getValue());
                        var1.modelBase.render(var1.entity, var1.limbSwing, var1.limbSwingAmount, var1.ageInTicks, var1.netHeadYaw, var1.headPitch, var1.scaleIn);
                        OutlineUtils.renderTwo();
                        var1.modelBase.render(var1.entity, var1.limbSwing, var1.limbSwingAmount, var1.ageInTicks, var1.netHeadYaw, var1.headPitch, var1.scaleIn);
                        OutlineUtils.renderThree();
                        OutlineUtils.renderFour(var3);
                        var1.modelBase.render(var1.entity, var1.limbSwing, var1.limbSwingAmount, var1.ageInTicks, var1.netHeadYaw, var1.headPitch, var1.scaleIn);
                        OutlineUtils.renderFive();
                     }
                  }
               }

               if (!(Boolean)this.onTop.getValue() && (Boolean)this.renderEntities.getValue()) {
                  var1.modelBase.render(var1.entity, var1.limbSwing, var1.limbSwingAmount, var1.ageInTicks, var1.netHeadYaw, var1.headPitch, var1.scaleIn);
               }

               OutlineUtils.setColor(Color.WHITE);
               mc.gameSettings.fancyGraphics = this.fancyGraphics;
               mc.gameSettings.gammaSetting = this.gamma;
               var1.setCancelled(true);
            }

         }
      }
   }

   public static void oisD(Color var0) {
      GL11.glColor4f((float)var0.getRed() / 255.0F, (float)var0.getGreen() / 255.0F, (float)var0.getBlue() / 255.0F, (float)var0.getAlpha() / 255.0F);
   }

   public static void renderNormal2(float var0) {
      Iterator var1 = Wrapper.getMinecraft().world.loadedEntityList.iterator();

      while(var1.hasNext()) {
         Entity var2 = (Entity)var1.next();
         if (var2 != Wrapper.getMinecraft().getRenderViewEntity() && var2 instanceof EntityPlayer) {
            Render var3 = Wrapper.getMinecraft().getRenderManager().getEntityRenderObject(var2);
            RenderLivingBase var4 = var3 instanceof RenderLivingBase ? (RenderLivingBase)var3 : null;
            double var5 = var2.lastTickPosX + (var2.posX - var2.lastTickPosX) * (double)var0;
            double var7 = var2.lastTickPosY + (var2.posY - var2.lastTickPosY) * (double)var0;
            double var9 = var2.lastTickPosZ + (var2.posZ - var2.lastTickPosZ) * (double)var0;
            if (var4 != null) {
               var4.doRender((EntityPlayer)var2, var5 - mc.renderManager.renderPosX, var7 - mc.renderManager.renderPosY, var9 - mc.renderManager.renderPosZ, var2.rotationYaw, mc.getRenderPartialTicks());
            }
         }
      }

   }

   public static Color getEntityColor(Entity var0) {
      if (var0 instanceof EntityPlayer) {
         if (Friends.isFriend(var0.getName())) {
            return new Color(0.27F, 0.7F, 0.92F);
         } else {
            float var1 = mc.player.getDistance(var0);
            return var1 <= 32.0F ? new Color(1.0F - var1 / 32.0F / 2.0F, var1 / 32.0F, 0.0F) : new Color(0.0F, 0.9F, 0.0F);
         }
      } else if (var0 instanceof EntityEnderCrystal) {
         return ColorUtil.getClickGUIColor();
      } else if (EntityUtil.isPassive(var0)) {
         return (Boolean)future.getValue() ? new Color(0, 196, 0) : new Color(5, 255, 240);
      } else if (EntityUtil.isPassive(var0) && !(var0 instanceof EntitySpider)) {
         return new Color(1.0F, 1.0F, 0.0F);
      } else {
         return (Boolean)future.getValue() ? new Color(191, 57, 59) : new Color(255, 0, 102);
      }
   }

   public static void renderColor2(float var0) {
      Iterator var1 = Wrapper.getMinecraft().world.loadedEntityList.iterator();

      while(true) {
         Entity var2;
         do {
            do {
               if (!var1.hasNext()) {
                  return;
               }

               var2 = (Entity)var1.next();
            } while(var2 == Wrapper.getMinecraft().getRenderViewEntity());
         } while(!(var2 instanceof EntityPlayer));

         Render var3 = Wrapper.getMinecraft().getRenderManager().getEntityRenderObject(var2);
         RenderLivingBase var4 = var3 instanceof RenderLivingBase ? (RenderLivingBase)var3 : null;
         if (var2 instanceof EntityPlayer) {
            GL11.glColor3f(5.0F, 255.0F, 240.0F);
         } else if (var2 instanceof EntityEnderCrystal) {
            OutlineUtils2.setColor(ColorUtil.getClickGUIColor());
         } else if (EntityUtil.isPassive(var2)) {
            if ((Boolean)future.getValue()) {
               OutlineUtils2.setColor(new Color(0, 196, 0));
            } else {
               OutlineUtils2.setColor(new Color(5, 255, 240));
            }
         } else if (EntityUtil.isPassive(var2) && !(var2 instanceof EntitySpider)) {
            OutlineUtils2.setColor(new Color(1.0F, 1.0F, 0.0F));
         } else if ((Boolean)future.getValue()) {
            OutlineUtils2.setColor(new Color(191, 57, 59));
         } else {
            OutlineUtils2.setColor(new Color(255, 0, 102));
         }

         double var5 = var2.lastTickPosX + (var2.posX - var2.lastTickPosX) * (double)var0;
         double var7 = var2.lastTickPosY + (var2.posY - var2.lastTickPosY) * (double)var0;
         double var9 = var2.lastTickPosZ + (var2.posZ - var2.lastTickPosZ) * (double)var0;
         if (var4 != null) {
            var4.doRender((EntityPlayer)var2, var5, var7, var9, var2.rotationYaw, mc.getRenderPartialTicks());
         }
      }
   }

   private Color getColor(EntityPlayer var1) {
      if (Friends.isFriend(var1.getName()) && (Boolean)this.friends.getValue()) {
         return new Color(0.27F, 0.7F, 0.92F);
      } else if (((String)this.color.getValue()).equalsIgnoreCase("ClickGui")) {
         return ColorUtil.getClickGUIColor();
      } else if (((String)this.color.getValue()).equalsIgnoreCase("Tracers")) {
         float var2 = mc.player.getDistance(var1);
         return var2 <= 32.0F ? new Color(1.0F - var2 / 32.0F / 2.0F, var2 / 32.0F, 0.0F) : new Color(0.0F, 0.9F, 0.0F);
      } else if (((String)this.color.getValue()).equalsIgnoreCase("Target")) {
         return TargetPlayers.targettedplayers.containsKey(var1.getName()) ? new Color((float)(Integer)this.red.getValue() / 255.0F, (float)(Integer)this.green.getValue() / 255.0F, (float)(Integer)this.blue.getValue() / 255.0F) : ColorUtil.getClickGUIColor();
      } else {
         return ((String)this.color.getValue()).equalsIgnoreCase("Rainbow") ? new Color(Xulu.rgb) : new Color(1.0F, 1.0F, 1.0F);
      }
   }

   @EventTarget
   public void onModelRender(EventModelRender var1) {
      Vec3d var2 = MathUtil.interpolateEntity(mc.player, var1.getPartialTicks());
      this.camera.setPosition(var2.x, var2.y, var2.z);
      if (this.camera.isBoundingBoxInFrustum(var1.entity.getEntityBoundingBox())) {
         if (var1.getEventState() == Event.State.PRE) {
            this.fancyGraphics = mc.gameSettings.fancyGraphics;
            mc.gameSettings.fancyGraphics = false;
            this.gamma = mc.gameSettings.gammaSetting;
            mc.gameSettings.gammaSetting = 10000.0F;
            if ((Boolean)this.onTop.getValue() && (Boolean)this.renderEntities.getValue()) {
               var1.modelBase.render(var1.entity, var1.limbSwing, var1.limbSwingAmount, var1.ageInTicks, var1.netHeadYaw, var1.headPitch, var1.scaleFactor);
            }

            if (((String)this.mode.getValue()).equalsIgnoreCase("Shader")) {
               if (var1.entity instanceof EntityOtherPlayerMP && (Boolean)this.players.getValue()) {
                  var1.entity.setGlowing(true);
               } else if ((Boolean)this.animals.getValue() && EntityUtil.isPassive(var1.entity)) {
                  var1.entity.setGlowing(true);
               } else if ((Boolean)this.mobs.getValue() && !EntityUtil.isPassive(var1.entity)) {
                  var1.entity.setGlowing(true);
               } else {
                  var1.entity.setGlowing(false);
               }
            } else {
               var1.entity.setGlowing(false);
               if (!((String)this.mode.getValue()).equalsIgnoreCase("Outline")) {
                  if (var1.entity instanceof EntityOtherPlayerMP && (Boolean)this.players.getValue()) {
                     GL11.glPushMatrix();
                     GL11.glEnable(32823);
                     GL11.glPolygonOffset(1.0F, -100000.0F);
                     GL11.glPushAttrib(1048575);
                     if (((String)this.mode.getValue()).equalsIgnoreCase("Solid")) {
                        GL11.glPolygonMode(1028, 6913);
                     } else {
                        GL11.glPolygonMode(1032, 6913);
                     }

                     GL11.glDisable(3553);
                     GL11.glDisable(2896);
                     GL11.glDisable(2929);
                     GL11.glEnable(2848);
                     GL11.glEnable(3042);
                     GL11.glBlendFunc(770, 771);
                     OutlineUtils.setColor(this.getColor((EntityOtherPlayerMP)var1.entity));
                     GL11.glLineWidth((Float)this.width.getValue());
                     var1.modelBase.render(var1.entity, var1.limbSwing, var1.limbSwingAmount, var1.ageInTicks, var1.netHeadYaw, var1.headPitch, var1.scaleFactor);
                     GL11.glPopAttrib();
                     GL11.glPolygonOffset(1.0F, 100000.0F);
                     GL11.glDisable(32823);
                     GL11.glPopMatrix();
                  } else if ((Boolean)this.animals.getValue() && EntityUtil.isPassive(var1.entity)) {
                     GL11.glPushMatrix();
                     GL11.glPushAttrib(1048575);
                     GL11.glPolygonMode(1032, 6913);
                     GL11.glDisable(3553);
                     GL11.glDisable(2896);
                     GL11.glDisable(2929);
                     GL11.glEnable(2848);
                     GL11.glEnable(3042);
                     GL11.glBlendFunc(770, 771);
                     OutlineUtils.setColor(getEntityColor(var1.entity));
                     GL11.glLineWidth((Float)this.width.getValue());
                     var1.modelBase.render(var1.entity, var1.limbSwing, var1.limbSwingAmount, var1.ageInTicks, var1.netHeadYaw, var1.headPitch, var1.scaleFactor);
                     GL11.glPopAttrib();
                     GL11.glPopMatrix();
                  } else if ((Boolean)this.mobs.getValue() && !EntityUtil.isPassive(var1.entity)) {
                     GL11.glPushMatrix();
                     GL11.glPushAttrib(1048575);
                     GL11.glPolygonMode(1032, 6913);
                     GL11.glDisable(3553);
                     GL11.glDisable(2896);
                     GL11.glDisable(2929);
                     GL11.glEnable(2848);
                     GL11.glEnable(3042);
                     GL11.glBlendFunc(770, 771);
                     OutlineUtils.setColor(getEntityColor(var1.entity));
                     GL11.glLineWidth((Float)this.width.getValue());
                     var1.modelBase.render(var1.entity, var1.limbSwing, var1.limbSwingAmount, var1.ageInTicks, var1.netHeadYaw, var1.headPitch, var1.scaleFactor);
                     GL11.glPopAttrib();
                     GL11.glPopMatrix();
                  }
               } else {
                  Color var3;
                  if (var1.entity instanceof EntityOtherPlayerMP && (Boolean)this.players.getValue()) {
                     var3 = this.getColor((EntityOtherPlayerMP)var1.entity);
                     var1.modelBase.render(var1.entity, var1.limbSwing, var1.limbSwingAmount, var1.ageInTicks, var1.netHeadYaw, var1.headPitch, var1.scaleFactor);
                     OutlineUtils.renderOne((Float)this.width.getValue());
                     var1.modelBase.render(var1.entity, var1.limbSwing, var1.limbSwingAmount, var1.ageInTicks, var1.netHeadYaw, var1.headPitch, var1.scaleFactor);
                     OutlineUtils.renderTwo();
                     var1.modelBase.render(var1.entity, var1.limbSwing, var1.limbSwingAmount, var1.ageInTicks, var1.netHeadYaw, var1.headPitch, var1.scaleFactor);
                     OutlineUtils.renderThree();
                     OutlineUtils.renderFour(var3);
                     OutlineUtils.setColor(var3);
                     var1.modelBase.render(var1.entity, var1.limbSwing, var1.limbSwingAmount, var1.ageInTicks, var1.netHeadYaw, var1.headPitch, var1.scaleFactor);
                     OutlineUtils.renderFive();
                     OutlineUtils.setColor(Color.WHITE);
                  } else if ((Boolean)this.animals.getValue() && EntityUtil.isPassive(var1.entity)) {
                     if ((Boolean)future.getValue()) {
                        var3 = new Color(0, 196, 0);
                     } else {
                        var3 = new Color(5, 255, 240);
                     }

                     GL11.glLineWidth(5.0F);
                     var1.modelBase.render(var1.entity, var1.limbSwing, var1.limbSwingAmount, var1.ageInTicks, var1.netHeadYaw, var1.headPitch, var1.scaleFactor);
                     OutlineUtils.renderOne((Float)this.width.getValue());
                     var1.modelBase.render(var1.entity, var1.limbSwing, var1.limbSwingAmount, var1.ageInTicks, var1.netHeadYaw, var1.headPitch, var1.scaleFactor);
                     OutlineUtils.renderTwo();
                     var1.modelBase.render(var1.entity, var1.limbSwing, var1.limbSwingAmount, var1.ageInTicks, var1.netHeadYaw, var1.headPitch, var1.scaleFactor);
                     OutlineUtils.renderThree();
                     OutlineUtils.renderFour(var3);
                     var1.modelBase.render(var1.entity, var1.limbSwing, var1.limbSwingAmount, var1.ageInTicks, var1.netHeadYaw, var1.headPitch, var1.scaleFactor);
                     OutlineUtils.renderFive();
                  } else if ((Boolean)this.mobs.getValue() && !EntityUtil.isPassive(var1.entity) && !(var1.entity instanceof EntityPlayer)) {
                     if ((Boolean)future.getValue()) {
                        var3 = new Color(191, 57, 59);
                     } else {
                        var3 = new Color(255, 0, 102);
                     }

                     GL11.glLineWidth(5.0F);
                     var1.modelBase.render(var1.entity, var1.limbSwing, var1.limbSwingAmount, var1.ageInTicks, var1.netHeadYaw, var1.headPitch, var1.scaleFactor);
                     OutlineUtils.renderOne((Float)this.width.getValue());
                     var1.modelBase.render(var1.entity, var1.limbSwing, var1.limbSwingAmount, var1.ageInTicks, var1.netHeadYaw, var1.headPitch, var1.scaleFactor);
                     OutlineUtils.renderTwo();
                     var1.modelBase.render(var1.entity, var1.limbSwing, var1.limbSwingAmount, var1.ageInTicks, var1.netHeadYaw, var1.headPitch, var1.scaleFactor);
                     OutlineUtils.renderThree();
                     OutlineUtils.renderFour(var3);
                     var1.modelBase.render(var1.entity, var1.limbSwing, var1.limbSwingAmount, var1.ageInTicks, var1.netHeadYaw, var1.headPitch, var1.scaleFactor);
                     OutlineUtils.renderFive();
                  }
               }
            }

            if (!(Boolean)this.onTop.getValue() && (Boolean)this.renderEntities.getValue()) {
               var1.modelBase.render(var1.entity, var1.limbSwing, var1.limbSwingAmount, var1.ageInTicks, var1.netHeadYaw, var1.headPitch, var1.scaleFactor);
            }

            OutlineUtils.setColor(Color.WHITE);
            mc.gameSettings.fancyGraphics = this.fancyGraphics;
            mc.gameSettings.gammaSetting = this.gamma;
            var1.setCancelled(true);
         }

      }
   }
}
