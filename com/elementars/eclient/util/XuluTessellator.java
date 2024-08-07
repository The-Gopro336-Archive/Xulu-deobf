package com.elementars.eclient.util;

import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import org.lwjgl.opengl.GL11;

public class XuluTessellator extends Tessellator {
   // $FF: synthetic field
   public static XuluTessellator INSTANCE = new XuluTessellator();

   public static void drawFaceOutline(BlockPos var0, int var1, int var2, int var3, int var4, int var5) {
      drawFaceOutline(INSTANCE.getBuffer(), (float)var0.x, (float)var0.y, (float)var0.z, 1.0F, 1.0F, 1.0F, var1, var2, var3, var4, var5);
   }

   public XuluTessellator() {
      super(2097152);
   }

   public static void drawBox(float var0, float var1, float var2, int var3, int var4) {
      int var5 = var3 >>> 24 & 255;
      int var6 = var3 >>> 16 & 255;
      int var7 = var3 >>> 8 & 255;
      int var8 = var3 & 255;
      drawBox(INSTANCE.getBuffer(), var0, var1, var2, 1.0F, 1.0F, 1.0F, var6, var7, var8, var5, var4);
   }

   public static void drawRectOutline(double var0, double var2, double var4, double var6, double var8, int var10) {
      drawRectOutline(var0 - var8, var2 - var8, var4 + var8, var6 + var8, var0, var2, var4, var6, var10);
   }

   public static void drawForgehaxLines(BufferBuilder var0, double var1, double var3, double var5, double var7, double var9, double var11, int var13, int var14, int var15, int var16, int var17) {
      if ((var13 & 17) != 0) {
         var0.pos(var1, var3, var5).color(var15, var16, var17, var14).endVertex();
         var0.pos(var1, var3, var11).color(var15, var16, var17, var14).endVertex();
      }

      if ((var13 & 18) != 0) {
         var0.pos(var1, var9, var5).color(var15, var16, var17, var14).endVertex();
         var0.pos(var1, var9, var11).color(var15, var16, var17, var14).endVertex();
      }

      if ((var13 & 33) != 0) {
         var0.pos(var7, var3, var5).color(var15, var16, var17, var14).endVertex();
         var0.pos(var7, var3, var11).color(var15, var16, var17, var14).endVertex();
      }

      if ((var13 & 34) != 0) {
         var0.pos(var7, var9, var5).color(var15, var16, var17, var14).endVertex();
         var0.pos(var7, var9, var11).color(var15, var16, var17, var14).endVertex();
      }

      if ((var13 & 5) != 0) {
         var0.pos(var1, var3, var5).color(var15, var16, var17, var14).endVertex();
         var0.pos(var7, var3, var5).color(var15, var16, var17, var14).endVertex();
      }

      if ((var13 & 6) != 0) {
         var0.pos(var1, var9, var5).color(var15, var16, var17, var14).endVertex();
         var0.pos(var7, var9, var5).color(var15, var16, var17, var14).endVertex();
      }

      if ((var13 & 9) != 0) {
         var0.pos(var1, var3, var11).color(var15, var16, var17, var14).endVertex();
         var0.pos(var7, var3, var11).color(var15, var16, var17, var14).endVertex();
      }

      if ((var13 & 10) != 0) {
         var0.pos(var1, var9, var11).color(var15, var16, var17, var14).endVertex();
         var0.pos(var7, var9, var11).color(var15, var16, var17, var14).endVertex();
      }

      if ((var13 & 20) != 0) {
         var0.pos(var1, var3, var5).color(var15, var16, var17, var14).endVertex();
         var0.pos(var1, var9, var5).color(var15, var16, var17, var14).endVertex();
      }

      if ((var13 & 36) != 0) {
         var0.pos(var7, var3, var5).color(var15, var16, var17, var14).endVertex();
         var0.pos(var7, var9, var5).color(var15, var16, var17, var14).endVertex();
      }

      if ((var13 & 24) != 0) {
         var0.pos(var1, var3, var11).color(var15, var16, var17, var14).endVertex();
         var0.pos(var1, var9, var11).color(var15, var16, var17, var14).endVertex();
      }

      if ((var13 & 40) != 0) {
         var0.pos(var7, var3, var11).color(var15, var16, var17, var14).endVertex();
         var0.pos(var7, var9, var11).color(var15, var16, var17, var14).endVertex();
      }

   }

   public static void drawBoxOutline(BlockPos var0, int var1, int var2) {
      int var3 = var1 >>> 24 & 255;
      int var4 = var1 >>> 16 & 255;
      int var5 = var1 >>> 8 & 255;
      int var6 = var1 & 255;
      drawBoxOutline(var0, var4, var5, var6, var3, var2);
   }

   public static void drawBoxOutline(BlockPos var0, int var1, int var2, int var3, int var4, int var5) {
      drawBoxOutline(INSTANCE.getBuffer(), (float)var0.x, (float)var0.y, (float)var0.z, 1.0F, 1.0F, 1.0F, var1, var2, var3, var4, var5);
   }

   public static void drawLines(BufferBuilder var0, float var1, float var2, float var3, float var4, float var5, float var6, int var7, int var8, int var9, int var10, int var11) {
      if ((var11 & 17) != 0) {
         var0.pos((double)var1, (double)var2, (double)var3).color(var7, var8, var9, var10).endVertex();
         var0.pos((double)var1, (double)var2, (double)(var3 + var6)).color(var7, var8, var9, var10).endVertex();
      }

      if ((var11 & 18) != 0) {
         var0.pos((double)var1, (double)(var2 + var5), (double)var3).color(var7, var8, var9, var10).endVertex();
         var0.pos((double)var1, (double)(var2 + var5), (double)(var3 + var6)).color(var7, var8, var9, var10).endVertex();
      }

      if ((var11 & 33) != 0) {
         var0.pos((double)(var1 + var4), (double)var2, (double)var3).color(var7, var8, var9, var10).endVertex();
         var0.pos((double)(var1 + var4), (double)var2, (double)(var3 + var6)).color(var7, var8, var9, var10).endVertex();
      }

      if ((var11 & 34) != 0) {
         var0.pos((double)(var1 + var4), (double)(var2 + var5), (double)var3).color(var7, var8, var9, var10).endVertex();
         var0.pos((double)(var1 + var4), (double)(var2 + var5), (double)(var3 + var6)).color(var7, var8, var9, var10).endVertex();
      }

      if ((var11 & 5) != 0) {
         var0.pos((double)var1, (double)var2, (double)var3).color(var7, var8, var9, var10).endVertex();
         var0.pos((double)(var1 + var4), (double)var2, (double)var3).color(var7, var8, var9, var10).endVertex();
      }

      if ((var11 & 6) != 0) {
         var0.pos((double)var1, (double)(var2 + var5), (double)var3).color(var7, var8, var9, var10).endVertex();
         var0.pos((double)(var1 + var4), (double)(var2 + var5), (double)var3).color(var7, var8, var9, var10).endVertex();
      }

      if ((var11 & 9) != 0) {
         var0.pos((double)var1, (double)var2, (double)(var3 + var6)).color(var7, var8, var9, var10).endVertex();
         var0.pos((double)(var1 + var4), (double)var2, (double)(var3 + var6)).color(var7, var8, var9, var10).endVertex();
      }

      if ((var11 & 10) != 0) {
         var0.pos((double)var1, (double)(var2 + var5), (double)(var3 + var6)).color(var7, var8, var9, var10).endVertex();
         var0.pos((double)(var1 + var4), (double)(var2 + var5), (double)(var3 + var6)).color(var7, var8, var9, var10).endVertex();
      }

      if ((var11 & 20) != 0) {
         var0.pos((double)var1, (double)var2, (double)var3).color(var7, var8, var9, var10).endVertex();
         var0.pos((double)var1, (double)(var2 + var5), (double)var3).color(var7, var8, var9, var10).endVertex();
      }

      if ((var11 & 36) != 0) {
         var0.pos((double)(var1 + var4), (double)var2, (double)var3).color(var7, var8, var9, var10).endVertex();
         var0.pos((double)(var1 + var4), (double)(var2 + var5), (double)var3).color(var7, var8, var9, var10).endVertex();
      }

      if ((var11 & 24) != 0) {
         var0.pos((double)var1, (double)var2, (double)(var3 + var6)).color(var7, var8, var9, var10).endVertex();
         var0.pos((double)var1, (double)(var2 + var5), (double)(var3 + var6)).color(var7, var8, var9, var10).endVertex();
      }

      if ((var11 & 40) != 0) {
         var0.pos((double)(var1 + var4), (double)var2, (double)(var3 + var6)).color(var7, var8, var9, var10).endVertex();
         var0.pos((double)(var1 + var4), (double)(var2 + var5), (double)(var3 + var6)).color(var7, var8, var9, var10).endVertex();
      }

   }

   public static void drawFullBox2(AxisAlignedBB var0, BlockPos var1, float var2, int var3, int var4, int var5, int var6, int var7) {
      prepare(7);
      drawBox2(var0, var3, var4, var5, var6, 63);
      release();
      drawBoundingBox(var0, var2, var3, var4, var5, var7);
   }

   public static void prepareGL() {
      GL11.glBlendFunc(770, 771);
      GlStateManager.tryBlendFuncSeparate(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA, SourceFactor.ONE, DestFactor.ZERO);
      GlStateManager.glLineWidth(1.5F);
      GlStateManager.disableTexture2D();
      GlStateManager.depthMask(false);
      GlStateManager.enableBlend();
      GlStateManager.disableDepth();
      GlStateManager.disableLighting();
      GlStateManager.disableCull();
      GlStateManager.enableAlpha();
      GlStateManager.color(1.0F, 1.0F, 1.0F);
   }

   public static void drawRectOutline(double var0, double var2, double var4, double var6, double var8, double var10, double var12, double var14, int var16) {
      drawRectDouble(var0, var2, var4, var10, var16);
      drawRectDouble(var12, var10, var4, var6, var16);
      drawRectDouble(var0, var14, var12, var6, var16);
      drawRectDouble(var0, var10, var8, var14, var16);
   }

   public static void drawFullBox(AxisAlignedBB var0, BlockPos var1, float var2, int var3, int var4) {
      int var5 = var3 >>> 24 & 255;
      int var6 = var3 >>> 16 & 255;
      int var7 = var3 >>> 8 & 255;
      int var8 = var3 & 255;
      drawFullBox(var0, var1, var2, var6, var7, var8, var5, var4);
   }

   public static void drawBox(BlockPos var0, int var1, int var2) {
      int var3 = var1 >>> 24 & 255;
      int var4 = var1 >>> 16 & 255;
      int var5 = var1 >>> 8 & 255;
      int var6 = var1 & 255;
      drawBox(var0, var4, var5, var6, var3, var2);
   }

   public static void drawForgehaxLines(BufferBuilder var0, double var1, double var3, double var5, double var7, double var9, double var11, int var13, int var14) {
      int var15 = var14 >>> 24 & 255;
      int var16 = var14 >>> 16 & 255;
      int var17 = var14 >>> 8 & 255;
      int var18 = var14 & 255;
      drawForgehaxLines(var0, var1, var3, var5, var7, var9, var11, var13, var15, var16, var17, var18);
   }

   public static void render() {
      INSTANCE.draw();
   }

   public static void drawLines(BlockPos var0, int var1, int var2, int var3, int var4, int var5) {
      drawLines(INSTANCE.getBuffer(), (float)var0.x, (float)var0.y, (float)var0.z, 1.0F, 1.0F, 1.0F, var1, var2, var3, var4, var5);
   }

   public static void drawOutlineLine(double var0, double var2, double var4, double var6, float var8, int var9) {
      GlStateManager.pushMatrix();
      GlStateManager.enableBlend();
      GlStateManager.disableDepth();
      GlStateManager.tryBlendFuncSeparate(770, 771, 0, 1);
      GlStateManager.disableTexture2D();
      GlStateManager.depthMask(false);
      GL11.glEnable(2848);
      GL11.glHint(3154, 4354);
      GL11.glLineWidth(var8);
      double var10;
      if (var0 < var4) {
         var10 = var0;
         var0 = var4;
         var4 = var10;
      }

      if (var2 < var6) {
         var10 = var2;
         var2 = var6;
         var6 = var10;
      }

      float var16 = (float)(var9 >> 24 & 255) / 255.0F;
      float var11 = (float)(var9 >> 16 & 255) / 255.0F;
      float var12 = (float)(var9 >> 8 & 255) / 255.0F;
      float var13 = (float)(var9 & 255) / 255.0F;
      Tessellator var14 = Tessellator.getInstance();
      BufferBuilder var15 = var14.getBuffer();
      var15.begin(3, DefaultVertexFormats.POSITION_COLOR);
      var15.pos(var0, var6, 0.0D).color(var11, var12, var13, var16).endVertex();
      var15.pos(var4, var6, 0.0D).color(var11, var12, var13, var16).endVertex();
      var15.pos(var4, var2, 0.0D).color(var11, var12, var13, var16).endVertex();
      var15.pos(var0, var2, 0.0D).color(var11, var12, var13, var16).endVertex();
      var15.pos(var0, var6, 0.0D).color(var11, var12, var13, var16).endVertex();
      var14.draw();
      GL11.glDisable(2848);
      GlStateManager.depthMask(true);
      GlStateManager.enableTexture2D();
      GlStateManager.disableBlend();
      GlStateManager.popMatrix();
   }

   public static void drawBoxOutline(float var0, float var1, float var2, int var3, int var4) {
      int var5 = var3 >>> 24 & 255;
      int var6 = var3 >>> 16 & 255;
      int var7 = var3 >>> 8 & 255;
      int var8 = var3 & 255;
      drawBoxOutline(INSTANCE.getBuffer(), var0, var1, var2, 1.0F, 1.0F, 1.0F, var6, var7, var8, var5, var4);
   }

   public static void drawRectDouble(double var0, double var2, double var4, double var6, int var8) {
      double var9;
      if (var0 < var4) {
         var9 = var0;
         var0 = var4;
         var4 = var9;
      }

      if (var2 < var6) {
         var9 = var2;
         var2 = var6;
         var6 = var9;
      }

      float var16 = (float)(var8 >> 24 & 255) / 255.0F;
      float var10 = (float)(var8 >> 16 & 255) / 255.0F;
      float var11 = (float)(var8 >> 8 & 255) / 255.0F;
      float var12 = (float)(var8 & 255) / 255.0F;
      Tessellator var13 = Tessellator.getInstance();
      BufferBuilder var14 = var13.getBuffer();
      GlStateManager.enableBlend();
      GlStateManager.disableTexture2D();
      GlStateManager.tryBlendFuncSeparate(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA, SourceFactor.ONE, DestFactor.ZERO);
      GlStateManager.color(var10, var11, var12, var16);
      var14.begin(7, DefaultVertexFormats.POSITION);
      var14.pos(var0, var6, 0.0D).endVertex();
      var14.pos(var4, var6, 0.0D).endVertex();
      var14.pos(var4, var2, 0.0D).endVertex();
      var14.pos(var0, var2, 0.0D).endVertex();
      var13.draw();
      GlStateManager.enableTexture2D();
      GlStateManager.disableBlend();
   }

   public static void drawBoxOutline(BufferBuilder var0, float var1, float var2, float var3, float var4, float var5, float var6, int var7, int var8, int var9, int var10, int var11) {
      if ((var11 & 1) != 0) {
         var0.pos((double)(var1 + var4), (double)var2, (double)var3).color(var7, var8, var9, var10).endVertex();
         var0.pos((double)(var1 + var4), (double)var2, (double)var3 + 0.02D).color(var7, var8, var9, var10).endVertex();
         var0.pos((double)var1, (double)var2, (double)var3 + 0.02D).color(var7, var8, var9, var10).endVertex();
         var0.pos((double)var1, (double)var2, (double)var3).color(var7, var8, var9, var10).endVertex();
         var0.pos((double)var1 + 0.02D, (double)var2, (double)var3).color(var7, var8, var9, var10).endVertex();
         var0.pos((double)var1 + 0.02D, (double)var2, (double)(var3 + var6)).color(var7, var8, var9, var10).endVertex();
         var0.pos((double)var1, (double)var2, (double)(var3 + var6)).color(var7, var8, var9, var10).endVertex();
         var0.pos((double)var1, (double)var2, (double)var3).color(var7, var8, var9, var10).endVertex();
         var0.pos((double)(var1 + var4), (double)var2, (double)(var3 + var6) - 0.02D).color(var7, var8, var9, var10).endVertex();
         var0.pos((double)(var1 + var4), (double)var2, (double)(var3 + var6)).color(var7, var8, var9, var10).endVertex();
         var0.pos((double)var1, (double)var2, (double)(var3 + var6)).color(var7, var8, var9, var10).endVertex();
         var0.pos((double)var1, (double)var2, (double)(var3 + var6) - 0.02D).color(var7, var8, var9, var10).endVertex();
         var0.pos((double)(var1 + var4), (double)var2, (double)var3).color(var7, var8, var9, var10).endVertex();
         var0.pos((double)(var1 + var4), (double)var2, (double)(var3 + var6)).color(var7, var8, var9, var10).endVertex();
         var0.pos((double)(var1 + var4) - 0.02D, (double)var2, (double)(var3 + var6)).color(var7, var8, var9, var10).endVertex();
         var0.pos((double)(var1 + var4) - 0.02D, (double)var2, (double)var3).color(var7, var8, var9, var10).endVertex();
      }

   }

   public static void drawLines(BlockPos var0, int var1, int var2) {
      int var3 = var1 >>> 24 & 255;
      int var4 = var1 >>> 16 & 255;
      int var5 = var1 >>> 8 & 255;
      int var6 = var1 & 255;
      drawLines(var0, var4, var5, var6, var3, var2);
   }

   public static void drawIndicator(AxisAlignedBB var0, int var1, int var2, int var3, int var4, int var5) {
      GlStateManager.pushMatrix();
      GlStateManager.enableBlend();
      GlStateManager.disableDepth();
      GlStateManager.tryBlendFuncSeparate(770, 771, 0, 1);
      GlStateManager.disableTexture2D();
      GlStateManager.depthMask(false);
      Tessellator var6 = Tessellator.getInstance();
      BufferBuilder var7 = var6.getBuffer();
      var7.begin(7, DefaultVertexFormats.POSITION_COLOR);
      if ((var5 & 1) != 0) {
         var7.pos(var0.maxX, var0.minY, var0.minZ).color(var1, var2, var3, var4).endVertex();
         var7.pos(var0.maxX, var0.minY, var0.maxZ).color(var1, var2, var3, var4).endVertex();
         var7.pos(var0.minX, var0.minY, var0.maxZ).color(var1, var2, var3, var4).endVertex();
         var7.pos(var0.minX, var0.minY, var0.minZ).color(var1, var2, var3, var4).endVertex();
      }

      if ((var5 & 4) != 0) {
         var7.pos(var0.maxX, var0.minY, var0.minZ).color(var1, var2, var3, var4).endVertex();
         var7.pos(var0.minX, var0.minY, var0.minZ).color(var1, var2, var3, var4).endVertex();
         var7.pos(var0.minX, var0.maxY, var0.minZ).color(var1, var2, var3, 0).endVertex();
         var7.pos(var0.maxX, var0.maxY, var0.minZ).color(var1, var2, var3, 0).endVertex();
      }

      if ((var5 & 8) != 0) {
         var7.pos(var0.minX, var0.minY, var0.maxZ).color(var1, var2, var3, var4).endVertex();
         var7.pos(var0.maxX, var0.minY, var0.maxZ).color(var1, var2, var3, var4).endVertex();
         var7.pos(var0.maxX, var0.maxY, var0.maxZ).color(var1, var2, var3, 0).endVertex();
         var7.pos(var0.minX, var0.maxY, var0.maxZ).color(var1, var2, var3, 0).endVertex();
      }

      if ((var5 & 16) != 0) {
         var7.pos(var0.minX, var0.minY, var0.minZ).color(var1, var2, var3, var4).endVertex();
         var7.pos(var0.minX, var0.minY, var0.maxZ).color(var1, var2, var3, var4).endVertex();
         var7.pos(var0.minX, var0.maxY, var0.maxZ).color(var1, var2, var3, 0).endVertex();
         var7.pos(var0.minX, var0.maxY, var0.minZ).color(var1, var2, var3, 0).endVertex();
      }

      if ((var5 & 32) != 0) {
         var7.pos(var0.maxX, var0.minY, var0.maxZ).color(var1, var2, var3, var4).endVertex();
         var7.pos(var0.maxX, var0.minY, var0.minZ).color(var1, var2, var3, var4).endVertex();
         var7.pos(var0.maxX, var0.maxY, var0.minZ).color(var1, var2, var3, 0).endVertex();
         var7.pos(var0.maxX, var0.maxY, var0.maxZ).color(var1, var2, var3, 0).endVertex();
      }

      var6.draw();
      GlStateManager.depthMask(true);
      GlStateManager.enableDepth();
      GlStateManager.enableTexture2D();
      GlStateManager.disableBlend();
      GlStateManager.popMatrix();
   }

   public static void drawBox2(AxisAlignedBB var0, int var1, int var2, int var3, int var4, int var5) {
      Tessellator var6 = Tessellator.getInstance();
      BufferBuilder var7 = var6.getBuffer();
      var7.begin(7, DefaultVertexFormats.POSITION_COLOR);
      if ((var5 & 1) != 0) {
         var7.pos(var0.maxX, var0.minY, var0.minZ).color(var1, var2, var3, var4).endVertex();
         var7.pos(var0.maxX, var0.minY, var0.maxZ).color(var1, var2, var3, var4).endVertex();
         var7.pos(var0.minX, var0.minY, var0.maxZ).color(var1, var2, var3, var4).endVertex();
         var7.pos(var0.minX, var0.minY, var0.minZ).color(var1, var2, var3, var4).endVertex();
      }

      if ((var5 & 2) != 0) {
         var7.pos(var0.maxX, var0.maxY, var0.minZ).color(var1, var2, var3, var4).endVertex();
         var7.pos(var0.minX, var0.maxY, var0.minZ).color(var1, var2, var3, var4).endVertex();
         var7.pos(var0.minX, var0.maxY, var0.maxZ).color(var1, var2, var3, var4).endVertex();
         var7.pos(var0.maxX, var0.maxY, var0.maxZ).color(var1, var2, var3, var4).endVertex();
      }

      if ((var5 & 4) != 0) {
         var7.pos(var0.maxX, var0.minY, var0.minZ).color(var1, var2, var3, var4).endVertex();
         var7.pos(var0.minX, var0.minY, var0.minZ).color(var1, var2, var3, var4).endVertex();
         var7.pos(var0.minX, var0.maxY, var0.minZ).color(var1, var2, var3, var4).endVertex();
         var7.pos(var0.maxX, var0.maxY, var0.minZ).color(var1, var2, var3, var4).endVertex();
      }

      if ((var5 & 8) != 0) {
         var7.pos(var0.minX, var0.minY, var0.maxZ).color(var1, var2, var3, var4).endVertex();
         var7.pos(var0.maxX, var0.minY, var0.maxZ).color(var1, var2, var3, var4).endVertex();
         var7.pos(var0.maxX, var0.maxY, var0.maxZ).color(var1, var2, var3, var4).endVertex();
         var7.pos(var0.minX, var0.maxY, var0.maxZ).color(var1, var2, var3, var4).endVertex();
      }

      if ((var5 & 16) != 0) {
         var7.pos(var0.minX, var0.minY, var0.minZ).color(var1, var2, var3, var4).endVertex();
         var7.pos(var0.minX, var0.minY, var0.maxZ).color(var1, var2, var3, var4).endVertex();
         var7.pos(var0.minX, var0.maxY, var0.maxZ).color(var1, var2, var3, var4).endVertex();
         var7.pos(var0.minX, var0.maxY, var0.minZ).color(var1, var2, var3, var4).endVertex();
      }

      if ((var5 & 32) != 0) {
         var7.pos(var0.maxX, var0.minY, var0.maxZ).color(var1, var2, var3, var4).endVertex();
         var7.pos(var0.maxX, var0.minY, var0.minZ).color(var1, var2, var3, var4).endVertex();
         var7.pos(var0.maxX, var0.maxY, var0.minZ).color(var1, var2, var3, var4).endVertex();
         var7.pos(var0.maxX, var0.maxY, var0.maxZ).color(var1, var2, var3, var4).endVertex();
      }

      var6.draw();
   }

   public static void drawFullBox2(AxisAlignedBB var0, BlockPos var1, float var2, int var3, int var4) {
      int var5 = var3 >>> 24 & 255;
      int var6 = var3 >>> 16 & 255;
      int var7 = var3 >>> 8 & 255;
      int var8 = var3 & 255;
      drawFullBox2(var0, var1, var2, var6, var7, var8, var5, var4);
   }

   public static BufferBuilder getBufferBuilder() {
      return INSTANCE.getBuffer();
   }

   public static void release() {
      render();
      releaseGL();
   }

   public static void drawFace(BlockPos var0, int var1, int var2, int var3, int var4, int var5) {
      drawFace(INSTANCE.getBuffer(), (float)var0.x, (float)var0.y, (float)var0.z, 1.0F, 1.0F, 1.0F, var1, var2, var3, var4, var5);
   }

   public static void drawFullBoxAA(AxisAlignedBB var0, float var1, int var2, int var3, int var4, int var5, int var6) {
      drawBox2(var0, var2, var3, var4, var5, 63);
      drawBoundingBox(var0, var1, var2, var3, var4, var6);
   }

   public static void drawFullFace(AxisAlignedBB var0, BlockPos var1, float var2, int var3, int var4, int var5, int var6, int var7) {
      prepare(7);
      drawFace(var1, var3, var4, var5, var6, 63);
      release();
      drawBoundingBoxFace(var0, var2, var3, var4, var5, var7);
   }

   public static void drawFullBox(AxisAlignedBB var0, BlockPos var1, float var2, int var3, int var4, int var5, int var6, int var7) {
      prepare(7);
      drawBox(var1, var3, var4, var5, var6, 63);
      release();
      drawBoundingBox(var0, var2, var3, var4, var5, var7);
   }

   public static void drawFullBox2(AxisAlignedBB var0, BlockPos var1, float var2, int var3) {
      int var4 = var3 >>> 24 & 255;
      int var5 = var3 >>> 16 & 255;
      int var6 = var3 >>> 8 & 255;
      int var7 = var3 & 255;
      drawFullBox2(var0, var1, var2, var5, var6, var7, var4, 255);
   }

   public static void drawFullFace(AxisAlignedBB var0, BlockPos var1, float var2, int var3, int var4) {
      int var5 = var3 >>> 24 & 255;
      int var6 = var3 >>> 16 & 255;
      int var7 = var3 >>> 8 & 255;
      int var8 = var3 & 255;
      drawFullFace(var0, var1, var2, var6, var7, var8, var5, var4);
   }

   public static void drawBox2(AxisAlignedBB var0, int var1, int var2) {
      int var3 = var1 >>> 24 & 255;
      int var4 = var1 >>> 16 & 255;
      int var5 = var1 >>> 8 & 255;
      int var6 = var1 & 255;
      drawBox2(var0, var4, var5, var6, var3, var2);
   }

   public static void drawIndicator(AxisAlignedBB var0, int var1, int var2) {
      int var3 = var1 >>> 24 & 255;
      int var4 = var1 >>> 16 & 255;
      int var5 = var1 >>> 8 & 255;
      int var6 = var1 & 255;
      drawIndicator(var0, var4, var5, var6, var3, var2);
   }

   public static void drawFace(BufferBuilder var0, float var1, float var2, float var3, float var4, float var5, float var6, int var7, int var8, int var9, int var10, int var11) {
      if ((var11 & 1) != 0) {
         var0.pos((double)(var1 + var4), (double)var2, (double)var3).color(var7, var8, var9, var10).endVertex();
         var0.pos((double)(var1 + var4), (double)var2, (double)(var3 + var6)).color(var7, var8, var9, var10).endVertex();
         var0.pos((double)var1, (double)var2, (double)(var3 + var6)).color(var7, var8, var9, var10).endVertex();
         var0.pos((double)var1, (double)var2, (double)var3).color(var7, var8, var9, var10).endVertex();
      }

   }

   public static void drawBoundingBox(AxisAlignedBB var0, float var1, int var2) {
      int var3 = var2 >>> 24 & 255;
      int var4 = var2 >>> 16 & 255;
      int var5 = var2 >>> 8 & 255;
      int var6 = var2 & 255;
      drawBoundingBox(var0, var1, var4, var5, var6, var3);
   }

   public static void drawFace(BlockPos var0, int var1, int var2) {
      int var3 = var1 >>> 24 & 255;
      int var4 = var1 >>> 16 & 255;
      int var5 = var1 >>> 8 & 255;
      int var6 = var1 & 255;
      drawFace(var0, var4, var5, var6, var3, var2);
   }

   public static void prepare(int var0) {
      prepareGL();
      begin(var0);
   }

   public static void drawBoundingBoxFace(AxisAlignedBB var0, float var1, int var2, int var3, int var4, int var5) {
      GlStateManager.pushMatrix();
      GlStateManager.enableBlend();
      GlStateManager.disableDepth();
      GlStateManager.tryBlendFuncSeparate(770, 771, 0, 1);
      GlStateManager.disableTexture2D();
      GlStateManager.depthMask(false);
      GL11.glEnable(2848);
      GL11.glHint(3154, 4354);
      GL11.glLineWidth(var1);
      Tessellator var6 = Tessellator.getInstance();
      BufferBuilder var7 = var6.getBuffer();
      var7.begin(3, DefaultVertexFormats.POSITION_COLOR);
      var7.pos(var0.minX, var0.minY, var0.minZ).color(var2, var3, var4, var5).endVertex();
      var7.pos(var0.minX, var0.minY, var0.maxZ).color(var2, var3, var4, var5).endVertex();
      var7.pos(var0.maxX, var0.minY, var0.maxZ).color(var2, var3, var4, var5).endVertex();
      var7.pos(var0.maxX, var0.minY, var0.minZ).color(var2, var3, var4, var5).endVertex();
      var7.pos(var0.minX, var0.minY, var0.minZ).color(var2, var3, var4, var5).endVertex();
      var6.draw();
      GL11.glDisable(2848);
      GlStateManager.depthMask(true);
      GlStateManager.enableDepth();
      GlStateManager.enableTexture2D();
      GlStateManager.disableBlend();
      GlStateManager.popMatrix();
   }

   public static void releaseGL() {
      GlStateManager.enableCull();
      GlStateManager.depthMask(true);
      GlStateManager.enableTexture2D();
      GlStateManager.enableBlend();
      GlStateManager.enableDepth();
   }

   public static void drawBox(BlockPos var0, int var1, int var2, int var3, int var4, int var5) {
      drawBox(INSTANCE.getBuffer(), (float)var0.x, (float)var0.y, (float)var0.z, 1.0F, 1.0F, 1.0F, var1, var2, var3, var4, var5);
   }

   public static void drawFullBoxAA(AxisAlignedBB var0, float var1, int var2, int var3) {
      int var4 = var2 >>> 24 & 255;
      int var5 = var2 >>> 16 & 255;
      int var6 = var2 >>> 8 & 255;
      int var7 = var2 & 255;
      drawFullBoxAA(var0, var1, var5, var6, var7, var4, var3);
   }

   public static void drawBoundingBox(AxisAlignedBB var0, float var1, int var2, int var3, int var4, int var5) {
      GlStateManager.pushMatrix();
      GlStateManager.enableBlend();
      GlStateManager.disableDepth();
      GlStateManager.tryBlendFuncSeparate(770, 771, 0, 1);
      GlStateManager.disableTexture2D();
      GlStateManager.depthMask(false);
      GL11.glEnable(2848);
      GL11.glHint(3154, 4354);
      GL11.glLineWidth(var1);
      Tessellator var6 = Tessellator.getInstance();
      BufferBuilder var7 = var6.getBuffer();
      var7.begin(3, DefaultVertexFormats.POSITION_COLOR);
      var7.pos(var0.minX, var0.minY, var0.minZ).color(var2, var3, var4, var5).endVertex();
      var7.pos(var0.maxX, var0.minY, var0.minZ).color(var2, var3, var4, var5).endVertex();
      var7.pos(var0.maxX, var0.minY, var0.maxZ).color(var2, var3, var4, var5).endVertex();
      var7.pos(var0.minX, var0.minY, var0.maxZ).color(var2, var3, var4, var5).endVertex();
      var7.pos(var0.minX, var0.minY, var0.minZ).color(var2, var3, var4, var5).endVertex();
      var7.pos(var0.minX, var0.maxY, var0.minZ).color(var2, var3, var4, var5).endVertex();
      var7.pos(var0.maxX, var0.maxY, var0.minZ).color(var2, var3, var4, var5).endVertex();
      var7.pos(var0.maxX, var0.minY, var0.minZ).color(var2, var3, var4, var5).endVertex();
      var6.draw();
      var7.begin(3, DefaultVertexFormats.POSITION_COLOR);
      var7.pos(var0.minX, var0.maxY, var0.minZ).color(var2, var3, var4, var5).endVertex();
      var7.pos(var0.minX, var0.maxY, var0.maxZ).color(var2, var3, var4, var5).endVertex();
      var7.pos(var0.minX, var0.minY, var0.maxZ).color(var2, var3, var4, var5).endVertex();
      var6.draw();
      var7.begin(3, DefaultVertexFormats.POSITION_COLOR);
      var7.pos(var0.minX, var0.maxY, var0.maxZ).color(var2, var3, var4, var5).endVertex();
      var7.pos(var0.maxX, var0.maxY, var0.maxZ).color(var2, var3, var4, var5).endVertex();
      var7.pos(var0.maxX, var0.minY, var0.maxZ).color(var2, var3, var4, var5).endVertex();
      var6.draw();
      var7.begin(3, DefaultVertexFormats.POSITION_COLOR);
      var7.pos(var0.maxX, var0.maxY, var0.maxZ).color(var2, var3, var4, var5).endVertex();
      var7.pos(var0.maxX, var0.maxY, var0.minZ).color(var2, var3, var4, var5).endVertex();
      var6.draw();
      GL11.glDisable(2848);
      GlStateManager.depthMask(true);
      GlStateManager.enableDepth();
      GlStateManager.enableTexture2D();
      GlStateManager.disableBlend();
      GlStateManager.popMatrix();
   }

   public static void drawRectGradient(double var0, double var2, double var4, double var6, int var8, int var9) {
      double var10;
      if (var0 < var4) {
         var10 = var0;
         var0 = var4;
         var4 = var10;
      }

      if (var2 < var6) {
         var10 = var2;
         var2 = var6;
         var6 = var10;
      }

      float var20 = (float)(var8 >> 24 & 255) / 255.0F;
      float var11 = (float)(var8 >> 16 & 255) / 255.0F;
      float var12 = (float)(var8 >> 8 & 255) / 255.0F;
      float var13 = (float)(var8 & 255) / 255.0F;
      float var14 = (float)(var9 >> 24 & 255) / 255.0F;
      float var15 = (float)(var9 >> 16 & 255) / 255.0F;
      float var16 = (float)(var9 >> 8 & 255) / 255.0F;
      float var17 = (float)(var9 & 255) / 255.0F;
      Tessellator var18 = Tessellator.getInstance();
      BufferBuilder var19 = var18.getBuffer();
      GlStateManager.enableBlend();
      GlStateManager.disableTexture2D();
      GlStateManager.tryBlendFuncSeparate(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA, SourceFactor.ONE, DestFactor.ZERO);
      GlStateManager.pushMatrix();
      GlStateManager.color(var11, var12, var13, var20);
      var19.begin(7, DefaultVertexFormats.POSITION);
      var19.pos(var0, var6, 0.0D).endVertex();
      var19.pos(var4, var6, 0.0D).endVertex();
      var19.pos(var4, var2, 0.0D).endVertex();
      var19.pos(var0, var2, 0.0D).endVertex();
      var18.draw();
      GlStateManager.enableTexture2D();
      GlStateManager.disableBlend();
      GlStateManager.popMatrix();
      GL11.glShadeModel(7424);
   }

   public static void drawBox(BufferBuilder var0, float var1, float var2, float var3, float var4, float var5, float var6, int var7, int var8, int var9, int var10, int var11) {
      if ((var11 & 1) != 0) {
         var0.pos((double)(var1 + var4), (double)var2, (double)var3).color(var7, var8, var9, var10).endVertex();
         var0.pos((double)(var1 + var4), (double)var2, (double)(var3 + var6)).color(var7, var8, var9, var10).endVertex();
         var0.pos((double)var1, (double)var2, (double)(var3 + var6)).color(var7, var8, var9, var10).endVertex();
         var0.pos((double)var1, (double)var2, (double)var3).color(var7, var8, var9, var10).endVertex();
      }

      if ((var11 & 2) != 0) {
         var0.pos((double)(var1 + var4), (double)(var2 + var5), (double)var3).color(var7, var8, var9, var10).endVertex();
         var0.pos((double)var1, (double)(var2 + var5), (double)var3).color(var7, var8, var9, var10).endVertex();
         var0.pos((double)var1, (double)(var2 + var5), (double)(var3 + var6)).color(var7, var8, var9, var10).endVertex();
         var0.pos((double)(var1 + var4), (double)(var2 + var5), (double)(var3 + var6)).color(var7, var8, var9, var10).endVertex();
      }

      if ((var11 & 4) != 0) {
         var0.pos((double)(var1 + var4), (double)var2, (double)var3).color(var7, var8, var9, var10).endVertex();
         var0.pos((double)var1, (double)var2, (double)var3).color(var7, var8, var9, var10).endVertex();
         var0.pos((double)var1, (double)(var2 + var5), (double)var3).color(var7, var8, var9, var10).endVertex();
         var0.pos((double)(var1 + var4), (double)(var2 + var5), (double)var3).color(var7, var8, var9, var10).endVertex();
      }

      if ((var11 & 8) != 0) {
         var0.pos((double)var1, (double)var2, (double)(var3 + var6)).color(var7, var8, var9, var10).endVertex();
         var0.pos((double)(var1 + var4), (double)var2, (double)(var3 + var6)).color(var7, var8, var9, var10).endVertex();
         var0.pos((double)(var1 + var4), (double)(var2 + var5), (double)(var3 + var6)).color(var7, var8, var9, var10).endVertex();
         var0.pos((double)var1, (double)(var2 + var5), (double)(var3 + var6)).color(var7, var8, var9, var10).endVertex();
      }

      if ((var11 & 16) != 0) {
         var0.pos((double)var1, (double)var2, (double)var3).color(var7, var8, var9, var10).endVertex();
         var0.pos((double)var1, (double)var2, (double)(var3 + var6)).color(var7, var8, var9, var10).endVertex();
         var0.pos((double)var1, (double)(var2 + var5), (double)(var3 + var6)).color(var7, var8, var9, var10).endVertex();
         var0.pos((double)var1, (double)(var2 + var5), (double)var3).color(var7, var8, var9, var10).endVertex();
      }

      if ((var11 & 32) != 0) {
         var0.pos((double)(var1 + var4), (double)var2, (double)(var3 + var6)).color(var7, var8, var9, var10).endVertex();
         var0.pos((double)(var1 + var4), (double)var2, (double)var3).color(var7, var8, var9, var10).endVertex();
         var0.pos((double)(var1 + var4), (double)(var2 + var5), (double)var3).color(var7, var8, var9, var10).endVertex();
         var0.pos((double)(var1 + var4), (double)(var2 + var5), (double)(var3 + var6)).color(var7, var8, var9, var10).endVertex();
      }

   }

   public static void drawFaceOutline(BufferBuilder var0, float var1, float var2, float var3, float var4, float var5, float var6, int var7, int var8, int var9, int var10, int var11) {
      if ((var11 & 1) != 0) {
         var0.pos((double)(var1 + var4), (double)var2, (double)var3).color(var7, var8, var9, var10).endVertex();
         var0.pos((double)(var1 + var4), (double)var2, (double)var3 + 0.02D).color(var7, var8, var9, var10).endVertex();
         var0.pos((double)var1, (double)var2, (double)var3 + 0.02D).color(var7, var8, var9, var10).endVertex();
         var0.pos((double)var1, (double)var2, (double)var3).color(var7, var8, var9, var10).endVertex();
         var0.pos((double)var1 + 0.02D, (double)var2, (double)var3).color(var7, var8, var9, var10).endVertex();
         var0.pos((double)var1 + 0.02D, (double)var2, (double)(var3 + var6)).color(var7, var8, var9, var10).endVertex();
         var0.pos((double)var1, (double)var2, (double)(var3 + var6)).color(var7, var8, var9, var10).endVertex();
         var0.pos((double)var1, (double)var2, (double)var3).color(var7, var8, var9, var10).endVertex();
         var0.pos((double)(var1 + var4), (double)var2, (double)(var3 + var6) - 0.02D).color(var7, var8, var9, var10).endVertex();
         var0.pos((double)(var1 + var4), (double)var2, (double)(var3 + var6)).color(var7, var8, var9, var10).endVertex();
         var0.pos((double)var1, (double)var2, (double)(var3 + var6)).color(var7, var8, var9, var10).endVertex();
         var0.pos((double)var1, (double)var2, (double)(var3 + var6) - 0.02D).color(var7, var8, var9, var10).endVertex();
         var0.pos((double)(var1 + var4), (double)var2, (double)var3).color(var7, var8, var9, var10).endVertex();
         var0.pos((double)(var1 + var4), (double)var2, (double)(var3 + var6)).color(var7, var8, var9, var10).endVertex();
         var0.pos((double)(var1 + var4) - 0.02D, (double)var2, (double)(var3 + var6)).color(var7, var8, var9, var10).endVertex();
         var0.pos((double)(var1 + var4) - 0.02D, (double)var2, (double)var3).color(var7, var8, var9, var10).endVertex();
      }

   }

   public static void begin(int var0) {
      INSTANCE.getBuffer().begin(var0, DefaultVertexFormats.POSITION_COLOR);
   }
}
