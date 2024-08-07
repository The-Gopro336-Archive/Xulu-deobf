package com.elementars.eclient.module.render;

import com.elementars.eclient.Xulu;
import com.elementars.eclient.event.events.RenderEvent;
import com.elementars.eclient.module.Category;
import com.elementars.eclient.module.Module;
import com.google.common.base.Predicate;
import dev.xulu.settings.Value;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemEgg;
import net.minecraft.item.ItemEnderPearl;
import net.minecraft.item.ItemExpBottle;
import net.minecraft.item.ItemFishingRod;
import net.minecraft.item.ItemSnowball;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.RayTraceResult.Type;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.Cylinder;

public class Trajectories extends Module {
   // $FF: synthetic field
   private Value blueC = this.register(new Value("Charge Blue", this, 0, 0, 255));
   // $FF: synthetic field
   private Value scale = this.register(new Value("Scale", this, 1.0F, 1.0F, 2.0F));
   // $FF: synthetic field
   private Value green = this.register(new Value("Green", this, 255, 0, 255));
   // $FF: synthetic field
   private Value red = this.register(new Value("Red", this, 0, 0, 255));
   // $FF: synthetic field
   private Value greenC = this.register(new Value("Charge Green", this, 127, 0, 255));
   // $FF: synthetic field
   private Value blue = this.register(new Value("Blue", this, 0, 0, 255));
   // $FF: synthetic field
   private Value rainbow = this.register(new Value("Rainbow", this, false));
   // $FF: synthetic field
   private Value redC = this.register(new Value("Charge Red", this, 204, 0, 255));

   public void drawLine3D(double var1, double var3, double var5) {
      GL11.glVertex3d(var1, var3, var5);
   }

   public void disableGL3D() {
      GL11.glEnable(3553);
      GL11.glEnable(2929);
      GL11.glDisable(3042);
      GL11.glEnable(3008);
      GL11.glDepthMask(true);
      GL11.glCullFace(1029);
      GL11.glDisable(2848);
      GL11.glHint(3154, 4352);
      GL11.glHint(3155, 4352);
   }

   private List getEntitiesWithinAABB(AxisAlignedBB var1) {
      ArrayList var2 = new ArrayList();
      int var3 = MathHelper.floor((var1.minX - 2.0D) / 16.0D);
      int var4 = MathHelper.floor((var1.maxX + 2.0D) / 16.0D);
      int var5 = MathHelper.floor((var1.minZ - 2.0D) / 16.0D);
      int var6 = MathHelper.floor((var1.maxZ + 2.0D) / 16.0D);

      for(int var7 = var3; var7 <= var4; ++var7) {
         for(int var8 = var5; var8 <= var6; ++var8) {
            if (mc.world.getChunkProvider().getLoadedChunk(var7, var8) != null) {
               mc.world.getChunk(var7, var8).getEntitiesWithinAABBForEntity(mc.player, var1, var2, (Predicate)null);
            }
         }
      }

      return var2;
   }

   public Trajectories() {
      super("Trajectories", "Ingrosware trajectories", 0, Category.RENDER, true);
   }

   public void onWorldRender(RenderEvent var1) {
      Color var2 = new Color(Xulu.rgb);
      if (mc.world != null && mc.player != null) {
         double var3 = mc.player.lastTickPosX + (mc.player.posX - mc.player.lastTickPosX) * (double)var1.getPartialTicks();
         double var5 = mc.player.lastTickPosY + (mc.player.posY - mc.player.lastTickPosY) * (double)var1.getPartialTicks();
         double var7 = mc.player.lastTickPosZ + (mc.player.posZ - mc.player.lastTickPosZ) * (double)var1.getPartialTicks();
         mc.player.getHeldItem(EnumHand.MAIN_HAND);
         if (mc.gameSettings.thirdPersonView == 0 && (mc.player.getHeldItem(EnumHand.MAIN_HAND).getItem() instanceof ItemBow || mc.player.getHeldItem(EnumHand.MAIN_HAND).getItem() instanceof ItemFishingRod || mc.player.getHeldItem(EnumHand.MAIN_HAND).getItem() instanceof ItemEnderPearl || mc.player.getHeldItem(EnumHand.MAIN_HAND).getItem() instanceof ItemEgg || mc.player.getHeldItem(EnumHand.MAIN_HAND).getItem() instanceof ItemSnowball || mc.player.getHeldItem(EnumHand.MAIN_HAND).getItem() instanceof ItemExpBottle)) {
            GL11.glPushMatrix();
            Item var9 = mc.player.getHeldItem(EnumHand.MAIN_HAND).getItem();
            double var10 = var3 - (double)(MathHelper.cos(mc.player.rotationYaw / 180.0F * 3.1415927F) * 0.16F);
            double var12 = var5 + (double)mc.player.getEyeHeight() - 0.1000000014901161D;
            double var14 = var7 - (double)(MathHelper.sin(mc.player.rotationYaw / 180.0F * 3.1415927F) * 0.16F);
            double var16 = (double)(-MathHelper.sin(mc.player.rotationYaw / 180.0F * 3.1415927F) * MathHelper.cos(mc.player.rotationPitch / 180.0F * 3.1415927F)) * (var9 instanceof ItemBow ? 1.0D : 0.4D);
            double var18 = (double)(-MathHelper.sin(mc.player.rotationPitch / 180.0F * 3.1415927F)) * (var9 instanceof ItemBow ? 1.0D : 0.4D);
            double var20 = (double)(MathHelper.cos(mc.player.rotationYaw / 180.0F * 3.1415927F) * MathHelper.cos(mc.player.rotationPitch / 180.0F * 3.1415927F)) * (var9 instanceof ItemBow ? 1.0D : 0.4D);
            int var22 = 72000 - mc.player.getItemInUseCount();
            float var23 = (float)var22 / 20.0F;
            var23 = (var23 * var23 + var23 * 2.0F) / 3.0F;
            if (var23 > 1.0F) {
               var23 = 1.0F;
            }

            float var24 = MathHelper.sqrt(var16 * var16 + var18 * var18 + var20 * var20);
            var16 /= (double)var24;
            var18 /= (double)var24;
            var20 /= (double)var24;
            float var25 = var9 instanceof ItemBow ? var23 * 2.0F : (var9 instanceof ItemFishingRod ? 1.25F : (mc.player.getHeldItem(EnumHand.MAIN_HAND).getItem() == Items.EXPERIENCE_BOTTLE ? 0.9F : 1.0F));
            var16 *= (double)(var25 * (var9 instanceof ItemFishingRod ? 0.75F : (mc.player.getHeldItem(EnumHand.MAIN_HAND).getItem() == Items.EXPERIENCE_BOTTLE ? 0.75F : 1.5F)));
            var18 *= (double)(var25 * (var9 instanceof ItemFishingRod ? 0.75F : (mc.player.getHeldItem(EnumHand.MAIN_HAND).getItem() == Items.EXPERIENCE_BOTTLE ? 0.75F : 1.5F)));
            var20 *= (double)(var25 * (var9 instanceof ItemFishingRod ? 0.75F : (mc.player.getHeldItem(EnumHand.MAIN_HAND).getItem() == Items.EXPERIENCE_BOTTLE ? 0.75F : 1.5F)));
            this.enableGL3D(2.0F);
            if ((Boolean)this.rainbow.getValue()) {
               GlStateManager.color((float)var2.getRed() / 255.0F, (float)var2.getGreen() / 255.0F, (float)var2.getBlue() / 255.0F, 1.0F);
            } else if (var23 > 0.6F) {
               GlStateManager.color((float)(Integer)this.red.getValue() / 255.0F, (float)(Integer)this.green.getValue() / 255.0F, (float)(Integer)this.blue.getValue() / 255.0F, 1.0F);
            } else {
               GlStateManager.color((float)(Integer)this.redC.getValue() / 255.0F, (float)(Integer)this.greenC.getValue() / 255.0F, (float)(Integer)this.blueC.getValue() / 255.0F, 1.0F);
            }

            GL11.glEnable(2848);
            float var26 = (float)(var9 instanceof ItemBow ? 0.3D : 0.25D);
            boolean var27 = false;
            Entity var28 = null;
            RayTraceResult var29 = null;

            while(!var27 && var12 > 0.0D) {
               Vec3d var30 = new Vec3d(var10, var12, var14);
               Vec3d var31 = new Vec3d(var10 + var16, var12 + var18, var14 + var20);
               RayTraceResult var32 = mc.world.rayTraceBlocks(var30, var31, false, true, false);
               if (var32 != null && var32.typeOfHit != Type.MISS) {
                  var29 = var32;
                  var27 = true;
               }

               AxisAlignedBB var33 = new AxisAlignedBB(var10 - (double)var26, var12 - (double)var26, var14 - (double)var26, var10 + (double)var26, var12 + (double)var26, var14 + (double)var26);
               List var34 = this.getEntitiesWithinAABB(var33.offset(var16, var18, var20).expand(1.0D, 1.0D, 1.0D));
               Iterator var35 = var34.iterator();

               while(var35.hasNext()) {
                  Object var36 = var35.next();
                  Entity var37 = (Entity)var36;
                  if (var37.canBeCollidedWith() && var37 != mc.player) {
                     float var38 = 0.3F;
                     AxisAlignedBB var39 = var37.getEntityBoundingBox().expand((double)var38, (double)var38, (double)var38);
                     RayTraceResult var40 = var39.calculateIntercept(var30, var31);
                     if (var40 != null) {
                        var27 = true;
                        var28 = var37;
                        var29 = var40;
                     }
                  }
               }

               if (var28 != null) {
                  GlStateManager.color(1.0F, 0.0F, 0.0F, 1.0F);
               }

               var10 += var16;
               var12 += var18;
               var14 += var20;
               float var43 = 0.99F;
               var16 *= (double)var43;
               var18 *= (double)var43;
               var20 *= (double)var43;
               var18 -= var9 instanceof ItemBow ? 0.05D : 0.03D;
               this.drawLine3D(var10 - var3, var12 - var5, var14 - var7);
            }

            if (var29 != null && var29.typeOfHit == Type.BLOCK) {
               GlStateManager.translate(var10 - var3, var12 - var5, var14 - var7);
               int var41 = var29.sideHit.getIndex();
               if (var41 == 2) {
                  GlStateManager.rotate(90.0F, 1.0F, 0.0F, 0.0F);
               } else if (var41 == 3) {
                  GlStateManager.rotate(90.0F, 1.0F, 0.0F, 0.0F);
               } else if (var41 == 4) {
                  GlStateManager.rotate(90.0F, 0.0F, 0.0F, 1.0F);
               } else if (var41 == 5) {
                  GlStateManager.rotate(90.0F, 0.0F, 0.0F, 1.0F);
               }

               Cylinder var42 = new Cylinder();
               GlStateManager.rotate(-90.0F, 1.0F, 0.0F, 0.0F);
               GlStateManager.scale((Float)this.scale.getValue(), (Float)this.scale.getValue(), (Float)this.scale.getValue());
               var42.setDrawStyle(100011);
               if (var28 != null) {
                  GlStateManager.color(0.0F, 0.0F, 0.0F, 1.0F);
                  GL11.glLineWidth(2.5F);
                  var42.draw(0.6F, 0.3F, 0.0F, 4, 1);
                  GL11.glLineWidth(0.1F);
                  if ((Boolean)this.rainbow.getValue()) {
                     GlStateManager.color((float)var2.getRed() / 255.0F, (float)var2.getGreen() / 255.0F, (float)var2.getBlue() / 255.0F, 1.0F);
                  } else {
                     GlStateManager.color((float)(Integer)this.red.getValue() / 255.0F, (float)(Integer)this.green.getValue() / 255.0F, (float)(Integer)this.blue.getValue() / 255.0F, 1.0F);
                  }
               }

               var42.draw(0.6F, 0.3F, 0.0F, 4, 1);
            }

            this.disableGL3D();
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            GL11.glPopMatrix();
         }
      }

   }

   public void enableGL3D(float var1) {
      GL11.glDisable(3008);
      GL11.glEnable(3042);
      GL11.glBlendFunc(770, 771);
      GL11.glDisable(3553);
      GL11.glDisable(2929);
      GL11.glDepthMask(false);
      GL11.glEnable(2884);
      mc.entityRenderer.disableLightmap();
      GL11.glEnable(2848);
      GL11.glHint(3154, 4354);
      GL11.glHint(3155, 4354);
      GL11.glLineWidth(var1);
   }
}
