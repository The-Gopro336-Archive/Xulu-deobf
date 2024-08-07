package dev.xulu.clickgui;

import java.awt.Color;
import java.awt.Rectangle;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.math.AxisAlignedBB;
import org.lwjgl.opengl.GL11;

public final class RenderMethods {
   // $FF: synthetic field
   public static FloatBuffer matModelView = GLAllocation.createDirectFloatBuffer(16);
   // $FF: synthetic field
   public static FloatBuffer matProjection = GLAllocation.createDirectFloatBuffer(16);

   public static void drawVLine(float var0, float var1, float var2, int var3) {
      if (var2 < var1) {
         float var4 = var1;
         var1 = var2;
         var2 = var4;
      }

      drawRect(var0, var1 + 1.0F, var0 + 1.0F, var2, var3);
   }

   public static void drawBorderedRectReliant(float var0, float var1, float var2, float var3, float var4, int var5, int var6) {
      enableGL2D();
      drawRect(var0, var1, var2, var3, var5);
      glColor(var6);
      GL11.glEnable(3042);
      GL11.glDisable(3553);
      GL11.glBlendFunc(770, 771);
      GL11.glLineWidth(var4);
      GL11.glBegin(3);
      GL11.glVertex2f(var0, var1);
      GL11.glVertex2f(var0, var3);
      GL11.glVertex2f(var2, var3);
      GL11.glVertex2f(var2, var1);
      GL11.glVertex2f(var0, var1);
      GL11.glEnd();
      GL11.glEnable(3553);
      GL11.glDisable(3042);
      disableGL2D();
   }

   public static void drawOutlinedBox(AxisAlignedBB var0) {
      if (var0 != null) {
         GL11.glBegin(3);
         GL11.glVertex3d(var0.minX, var0.minY, var0.minZ);
         GL11.glVertex3d(var0.maxX, var0.minY, var0.minZ);
         GL11.glVertex3d(var0.maxX, var0.minY, var0.maxZ);
         GL11.glVertex3d(var0.minX, var0.minY, var0.maxZ);
         GL11.glVertex3d(var0.minX, var0.minY, var0.minZ);
         GL11.glEnd();
         GL11.glBegin(3);
         GL11.glVertex3d(var0.minX, var0.maxY, var0.minZ);
         GL11.glVertex3d(var0.maxX, var0.maxY, var0.minZ);
         GL11.glVertex3d(var0.maxX, var0.maxY, var0.maxZ);
         GL11.glVertex3d(var0.minX, var0.maxY, var0.maxZ);
         GL11.glVertex3d(var0.minX, var0.maxY, var0.minZ);
         GL11.glEnd();
         GL11.glBegin(1);
         GL11.glVertex3d(var0.minX, var0.minY, var0.minZ);
         GL11.glVertex3d(var0.minX, var0.maxY, var0.minZ);
         GL11.glVertex3d(var0.maxX, var0.minY, var0.minZ);
         GL11.glVertex3d(var0.maxX, var0.maxY, var0.minZ);
         GL11.glVertex3d(var0.maxX, var0.minY, var0.maxZ);
         GL11.glVertex3d(var0.maxX, var0.maxY, var0.maxZ);
         GL11.glVertex3d(var0.minX, var0.minY, var0.maxZ);
         GL11.glVertex3d(var0.minX, var0.maxY, var0.maxZ);
         GL11.glEnd();
      }
   }

   public static void glColor(Color var0) {
      GL11.glColor4f((float)var0.getRed() / 255.0F, (float)var0.getGreen() / 255.0F, (float)var0.getBlue() / 255.0F, (float)var0.getAlpha() / 255.0F);
   }

   public static void drawFullCircle(int var0, int var1, double var2, int var4) {
      var2 *= 2.0D;
      var0 *= 2;
      var1 *= 2;
      float var5 = (float)(var4 >> 24 & 255) / 255.0F;
      float var6 = (float)(var4 >> 16 & 255) / 255.0F;
      float var7 = (float)(var4 >> 8 & 255) / 255.0F;
      float var8 = (float)(var4 & 255) / 255.0F;
      enableGL2D();
      GL11.glScalef(0.5F, 0.5F, 0.5F);
      GL11.glColor4f(var6, var7, var8, var5);
      GL11.glBegin(6);

      for(int var9 = 0; var9 <= 360; ++var9) {
         double var10 = Math.sin((double)var9 * 3.141592653589793D / 180.0D) * var2;
         double var12 = Math.cos((double)var9 * 3.141592653589793D / 180.0D) * var2;
         GL11.glVertex2d((double)var0 + var10, (double)var1 + var12);
      }

      GL11.glEnd();
      GL11.glScalef(2.0F, 2.0F, 2.0F);
      disableGL2D();
   }

   public static void drawGradientRect(double var0, double var2, double var4, double var6, int var8, int var9) {
      GL11.glEnable(3042);
      GL11.glDisable(3553);
      GL11.glBlendFunc(770, 771);
      GL11.glEnable(2848);
      GL11.glShadeModel(7425);
      GL11.glPushMatrix();
      GL11.glBegin(7);
      glColor(var8);
      GL11.glVertex2d(var4, var2);
      GL11.glVertex2d(var0, var2);
      glColor(var9);
      GL11.glVertex2d(var0, var6);
      GL11.glVertex2d(var4, var6);
      GL11.glEnd();
      GL11.glPopMatrix();
      GL11.glEnable(3553);
      GL11.glDisable(3042);
      GL11.glDisable(2848);
      GL11.glShadeModel(7424);
   }

   public static void drawRoundedRect(float var0, float var1, float var2, float var3, int var4, int var5) {
      enableGL2D();
      var0 *= 2.0F;
      var1 *= 2.0F;
      var2 *= 2.0F;
      var3 *= 2.0F;
      GL11.glScalef(0.5F, 0.5F, 0.5F);
      drawVLine(var0, var1 + 1.0F, var3 - 2.0F, var4);
      drawVLine(var2 - 1.0F, var1 + 1.0F, var3 - 2.0F, var4);
      drawHLine(var0 + 2.0F, var2 - 3.0F, var1, var4);
      drawHLine(var0 + 2.0F, var2 - 3.0F, var3 - 1.0F, var4);
      drawHLine(var0 + 1.0F, var0 + 1.0F, var1 + 1.0F, var4);
      drawHLine(var2 - 2.0F, var2 - 2.0F, var1 + 1.0F, var4);
      drawHLine(var2 - 2.0F, var2 - 2.0F, var3 - 2.0F, var4);
      drawHLine(var0 + 1.0F, var0 + 1.0F, var3 - 2.0F, var4);
      drawRect(var0 + 1.0F, var1 + 1.0F, var2 - 1.0F, var3 - 1.0F, var5);
      GL11.glScalef(2.0F, 2.0F, 2.0F);
      disableGL2D();
   }

   public static void disableGL2D() {
      GL11.glEnable(3553);
      GL11.glDisable(3042);
      GL11.glEnable(2929);
      GL11.glDisable(2848);
      GL11.glHint(3154, 4352);
      GL11.glHint(3155, 4352);
   }

   public static void drawBox(AxisAlignedBB var0) {
      if (var0 != null) {
         GL11.glBegin(7);
         GL11.glVertex3d(var0.minX, var0.minY, var0.maxZ);
         GL11.glVertex3d(var0.maxX, var0.minY, var0.maxZ);
         GL11.glVertex3d(var0.maxX, var0.maxY, var0.maxZ);
         GL11.glVertex3d(var0.minX, var0.maxY, var0.maxZ);
         GL11.glEnd();
         GL11.glBegin(7);
         GL11.glVertex3d(var0.maxX, var0.minY, var0.maxZ);
         GL11.glVertex3d(var0.minX, var0.minY, var0.maxZ);
         GL11.glVertex3d(var0.minX, var0.maxY, var0.maxZ);
         GL11.glVertex3d(var0.maxX, var0.maxY, var0.maxZ);
         GL11.glEnd();
         GL11.glBegin(7);
         GL11.glVertex3d(var0.minX, var0.minY, var0.minZ);
         GL11.glVertex3d(var0.minX, var0.minY, var0.maxZ);
         GL11.glVertex3d(var0.minX, var0.maxY, var0.maxZ);
         GL11.glVertex3d(var0.minX, var0.maxY, var0.minZ);
         GL11.glEnd();
         GL11.glBegin(7);
         GL11.glVertex3d(var0.minX, var0.minY, var0.maxZ);
         GL11.glVertex3d(var0.minX, var0.minY, var0.minZ);
         GL11.glVertex3d(var0.minX, var0.maxY, var0.minZ);
         GL11.glVertex3d(var0.minX, var0.maxY, var0.maxZ);
         GL11.glEnd();
         GL11.glBegin(7);
         GL11.glVertex3d(var0.maxX, var0.minY, var0.maxZ);
         GL11.glVertex3d(var0.maxX, var0.minY, var0.minZ);
         GL11.glVertex3d(var0.maxX, var0.maxY, var0.minZ);
         GL11.glVertex3d(var0.maxX, var0.maxY, var0.maxZ);
         GL11.glEnd();
         GL11.glBegin(7);
         GL11.glVertex3d(var0.maxX, var0.minY, var0.minZ);
         GL11.glVertex3d(var0.maxX, var0.minY, var0.maxZ);
         GL11.glVertex3d(var0.maxX, var0.maxY, var0.maxZ);
         GL11.glVertex3d(var0.maxX, var0.maxY, var0.minZ);
         GL11.glEnd();
         GL11.glBegin(7);
         GL11.glVertex3d(var0.minX, var0.minY, var0.minZ);
         GL11.glVertex3d(var0.maxX, var0.minY, var0.minZ);
         GL11.glVertex3d(var0.maxX, var0.maxY, var0.minZ);
         GL11.glVertex3d(var0.minX, var0.maxY, var0.minZ);
         GL11.glEnd();
         GL11.glBegin(7);
         GL11.glVertex3d(var0.maxX, var0.minY, var0.minZ);
         GL11.glVertex3d(var0.minX, var0.minY, var0.minZ);
         GL11.glVertex3d(var0.minX, var0.maxY, var0.minZ);
         GL11.glVertex3d(var0.maxX, var0.maxY, var0.minZ);
         GL11.glEnd();
         GL11.glBegin(7);
         GL11.glVertex3d(var0.minX, var0.maxY, var0.minZ);
         GL11.glVertex3d(var0.maxX, var0.maxY, var0.minZ);
         GL11.glVertex3d(var0.maxX, var0.maxY, var0.maxZ);
         GL11.glVertex3d(var0.minX, var0.maxY, var0.maxZ);
         GL11.glEnd();
         GL11.glBegin(7);
         GL11.glVertex3d(var0.maxX, var0.maxY, var0.minZ);
         GL11.glVertex3d(var0.minX, var0.maxY, var0.minZ);
         GL11.glVertex3d(var0.minX, var0.maxY, var0.maxZ);
         GL11.glVertex3d(var0.maxX, var0.maxY, var0.maxZ);
         GL11.glEnd();
         GL11.glBegin(7);
         GL11.glVertex3d(var0.minX, var0.minY, var0.minZ);
         GL11.glVertex3d(var0.maxX, var0.minY, var0.minZ);
         GL11.glVertex3d(var0.maxX, var0.minY, var0.maxZ);
         GL11.glVertex3d(var0.minX, var0.minY, var0.maxZ);
         GL11.glEnd();
         GL11.glBegin(7);
         GL11.glVertex3d(var0.maxX, var0.minY, var0.minZ);
         GL11.glVertex3d(var0.minX, var0.minY, var0.minZ);
         GL11.glVertex3d(var0.minX, var0.minY, var0.maxZ);
         GL11.glVertex3d(var0.maxX, var0.minY, var0.maxZ);
         GL11.glEnd();
      }
   }

   public static void drawTriangle(int var0, int var1, int var2, int var3, int var4) {
      GL11.glEnable(3042);
      GL11.glDisable(3553);
      GL11.glBlendFunc(770, 771);
      float var5 = (float)(var4 >> 24 & 255) / 255.0F;
      float var6 = (float)(var4 >> 16 & 255) / 255.0F;
      float var7 = (float)(var4 >> 8 & 255) / 255.0F;
      float var8 = (float)(var4 & 255) / 255.0F;
      GL11.glColor4f(var6, var7, var8, var5);
      GL11.glEnable(2848);
      GL11.glHint(3154, 4354);
      GL11.glLineWidth(1.0F);
      GL11.glShadeModel(7425);
      switch(var2) {
      case 0:
         GL11.glBegin(2);
         GL11.glVertex2d((double)var0, (double)(var1 + var3));
         GL11.glVertex2d((double)(var0 + var3), (double)(var1 - var3));
         GL11.glVertex2d((double)(var0 - var3), (double)(var1 - var3));
         GL11.glEnd();
         GL11.glBegin(4);
         GL11.glVertex2d((double)var0, (double)(var1 + var3));
         GL11.glVertex2d((double)(var0 + var3), (double)(var1 - var3));
         GL11.glVertex2d((double)(var0 - var3), (double)(var1 - var3));
         GL11.glEnd();
         break;
      case 1:
         GL11.glBegin(2);
         GL11.glVertex2d((double)var0, (double)var1);
         GL11.glVertex2d((double)var0, (double)(var1 + var3 / 2));
         GL11.glVertex2d((double)(var0 + var3 + var3 / 2), (double)var1);
         GL11.glEnd();
         GL11.glBegin(4);
         GL11.glVertex2d((double)var0, (double)var1);
         GL11.glVertex2d((double)var0, (double)(var1 + var3 / 2));
         GL11.glVertex2d((double)(var0 + var3 + var3 / 2), (double)var1);
         GL11.glEnd();
      case 3:
         GL11.glBegin(2);
         GL11.glVertex2d((double)var0, (double)var1);
         GL11.glVertex2d((double)var0 + (double)var3 * 1.25D, (double)(var1 - var3 / 2));
         GL11.glVertex2d((double)var0 + (double)var3 * 1.25D, (double)(var1 + var3 / 2));
         GL11.glEnd();
         GL11.glBegin(4);
         GL11.glVertex2d((double)var0 + (double)var3 * 1.25D, (double)(var1 - var3 / 2));
         GL11.glVertex2d((double)var0, (double)var1);
         GL11.glVertex2d((double)var0 + (double)var3 * 1.25D, (double)(var1 + var3 / 2));
         GL11.glEnd();
      case 2:
      }

      GL11.glDisable(2848);
      GL11.glEnable(3553);
      GL11.glDisable(3042);
   }

   public static void drawGradientBorderedRectReliant(float var0, float var1, float var2, float var3, float var4, int var5, int var6, int var7) {
      enableGL2D();
      drawGradientRect(var0, var1, var2, var3, var7, var6);
      glColor(var5);
      GL11.glEnable(3042);
      GL11.glDisable(3553);
      GL11.glBlendFunc(770, 771);
      GL11.glLineWidth(var4);
      GL11.glBegin(3);
      GL11.glVertex2f(var0, var1);
      GL11.glVertex2f(var0, var3);
      GL11.glVertex2f(var2, var3);
      GL11.glVertex2f(var2, var1);
      GL11.glVertex2f(var0, var1);
      GL11.glEnd();
      GL11.glEnable(3553);
      GL11.glDisable(3042);
      disableGL2D();
   }

   public static void drawRect(Rectangle var0, int var1) {
      drawRect((float)var0.x, (float)var0.y, (float)(var0.x + var0.width), (float)(var0.y + var0.height), var1);
   }

   public static Color rainbow(long var0, float var2) {
      float var3 = (float)(System.nanoTime() + var0) / 1.0E10F % 1.0F;
      long var4 = Long.parseLong(Integer.toHexString(Color.HSBtoRGB(var3, 1.0F, 1.0F)), 16);
      Color var6 = new Color((int)var4);
      return new Color((float)var6.getRed() / 255.0F * var2, (float)var6.getGreen() / 255.0F * var2, (float)var6.getBlue() / 255.0F * var2, (float)var6.getAlpha() / 255.0F);
   }

   public static int applyTexture(int var0, int var1, int var2, ByteBuffer var3, boolean var4, boolean var5) {
      GL11.glBindTexture(3553, var0);
      GL11.glTexParameteri(3553, 10241, var4 ? 9729 : 9728);
      GL11.glTexParameteri(3553, 10240, var4 ? 9729 : 9728);
      GL11.glTexParameteri(3553, 10242, var5 ? 10497 : 10496);
      GL11.glTexParameteri(3553, 10243, var5 ? 10497 : 10496);
      GL11.glPixelStorei(3317, 1);
      GL11.glTexImage2D(3553, 0, 32856, var1, var2, 0, 6408, 5121, var3);
      return var0;
   }

   public static double getDiff(double var0, double var2, float var4, double var5) {
      return var0 + (var2 - var0) * (double)var4 - var5;
   }

   public static void enableGL2D() {
      GL11.glDisable(2929);
      GL11.glEnable(3042);
      GL11.glDisable(3553);
      GL11.glBlendFunc(770, 771);
      GL11.glDepthMask(true);
      GL11.glEnable(2848);
      GL11.glHint(3154, 4354);
      GL11.glHint(3155, 4354);
   }

   public static void rectangle(double var0, double var2, double var4, double var6, int var8) {
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

      float var15 = (float)(var8 >> 24 & 255) / 255.0F;
      float var10 = (float)(var8 >> 16 & 255) / 255.0F;
      float var11 = (float)(var8 >> 8 & 255) / 255.0F;
      float var12 = (float)(var8 & 255) / 255.0F;
      Tessellator var13 = Tessellator.getInstance();
      BufferBuilder var14 = var13.getBuffer();
      GlStateManager.enableBlend();
      GlStateManager.disableLighting();
      GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
      GlStateManager.color(var10, var11, var12, var15);
      var14.begin(7, DefaultVertexFormats.POSITION);
      var14.pos(var0, var6, 0.0D).endVertex();
      var14.pos(var4, var6, 0.0D).endVertex();
      var14.pos(var4, var2, 0.0D).endVertex();
      var14.pos(var0, var2, 0.0D).endVertex();
      var13.draw();
      GlStateManager.enableLighting();
      GlStateManager.disableBlend();
   }

   public static void drawBorderedRect(float var0, float var1, float var2, float var3, float var4, int var5, int var6) {
      enableGL2D();
      glColor(var5);
      drawRect(var0 + var4, var1 + var4, var2 - var4, var3 - var4);
      glColor(var6);
      drawRect(var0 + var4, var1, var2 - var4, var1 + var4);
      drawRect(var0, var1, var0 + var4, var3);
      drawRect(var2 - var4, var1, var2, var3);
      drawRect(var0 + var4, var3 - var4, var2 - var4, var3);
      disableGL2D();
   }

   public static void drawHLine(float var0, float var1, float var2, int var3) {
      if (var1 < var0) {
         float var4 = var0;
         var0 = var1;
         var1 = var4;
      }

      drawRect(var0, var2, var1 + 1.0F, var2 + 1.0F, var3);
   }

   public static void drawGradientHRect(float var0, float var1, float var2, float var3, int var4, int var5) {
      enableGL2D();
      GL11.glShadeModel(7425);
      GL11.glBegin(7);
      glColor(var4);
      GL11.glVertex2f(var0, var1);
      GL11.glVertex2f(var0, var3);
      glColor(var5);
      GL11.glVertex2f(var2, var3);
      GL11.glVertex2f(var2, var1);
      GL11.glEnd();
      GL11.glShadeModel(7424);
      disableGL2D();
   }

   public static void drawBorderedRect(Rectangle var0, float var1, int var2, int var3) {
      float var4 = (float)var0.x;
      float var5 = (float)var0.y;
      float var6 = (float)(var0.x + var0.width);
      float var7 = (float)(var0.y + var0.height);
      enableGL2D();
      glColor(var2);
      drawRect(var4 + var1, var5 + var1, var6 - var1, var7 - var1);
      glColor(var3);
      drawRect(var4 + 1.0F, var5, var6 - 1.0F, var5 + var1);
      drawRect(var4, var5, var4 + var1, var7);
      drawRect(var6 - var1, var5, var6, var7);
      drawRect(var4 + 1.0F, var7 - var1, var6 - 1.0F, var7);
      disableGL2D();
   }

   public static void disableGL3D() {
      GL11.glEnable(2896);
      GL11.glDisable(2848);
      GL11.glEnable(3553);
      GL11.glEnable(2929);
      GL11.glDisable(3042);
      GL11.glEnable(3008);
      GL11.glDepthMask(true);
      GL11.glCullFace(1029);
   }

   public static void renderCrosses(AxisAlignedBB var0) {
      GL11.glBegin(1);
      GL11.glVertex3d(var0.maxX, var0.maxY, var0.maxZ);
      GL11.glVertex3d(var0.maxX, var0.minY, var0.minZ);
      GL11.glVertex3d(var0.maxX, var0.maxY, var0.minZ);
      GL11.glVertex3d(var0.minX, var0.maxY, var0.maxZ);
      GL11.glVertex3d(var0.minX, var0.maxY, var0.minZ);
      GL11.glVertex3d(var0.maxX, var0.minY, var0.minZ);
      GL11.glVertex3d(var0.minX, var0.minY, var0.maxZ);
      GL11.glVertex3d(var0.maxX, var0.maxY, var0.maxZ);
      GL11.glVertex3d(var0.minX, var0.minY, var0.maxZ);
      GL11.glVertex3d(var0.minX, var0.maxY, var0.minZ);
      GL11.glVertex3d(var0.minX, var0.minY, var0.minZ);
      GL11.glVertex3d(var0.maxX, var0.minY, var0.maxZ);
      GL11.glEnd();
   }

   public static void drawRect(float var0, float var1, float var2, float var3, int var4) {
      enableGL2D();
      glColor(var4);
      drawRect(var0, var1, var2, var3);
      disableGL2D();
   }

   public static void drawLine(float var0, float var1, float var2, float var3, float var4) {
      GL11.glDisable(3553);
      GL11.glLineWidth(var4);
      GL11.glBegin(1);
      GL11.glVertex2f(var0, var1);
      GL11.glVertex2f(var2, var3);
      GL11.glEnd();
      GL11.glEnable(3553);
   }

   public static void drawBorderedRect(float var0, float var1, float var2, float var3, int var4, int var5) {
      enableGL2D();
      var0 *= 2.0F;
      var2 *= 2.0F;
      var1 *= 2.0F;
      var3 *= 2.0F;
      GL11.glScalef(0.5F, 0.5F, 0.5F);
      drawVLine(var0, var1, var3 - 1.0F, var5);
      drawVLine(var2 - 1.0F, var1, var3, var5);
      drawHLine(var0, var2 - 1.0F, var1, var5);
      drawHLine(var0, var2 - 2.0F, var3 - 1.0F, var5);
      drawRect(var0 + 1.0F, var1 + 1.0F, var2 - 1.0F, var3 - 1.0F, var4);
      GL11.glScalef(2.0F, 2.0F, 2.0F);
      disableGL2D();
   }

   public static void enableGL3D(float var0) {
      GL11.glDisable(3008);
      GL11.glEnable(3042);
      GL11.glBlendFunc(770, 771);
      GL11.glDisable(3553);
      GL11.glDisable(2929);
      GL11.glDepthMask(false);
      GL11.glEnable(2884);
      Minecraft.getMinecraft().entityRenderer.enableLightmap();
      GL11.glEnable(2848);
      GL11.glHint(3154, 4354);
      GL11.glHint(3155, 4354);
      GL11.glLineWidth(var0);
   }

   public static void drawRect(float var0, float var1, float var2, float var3, float var4, float var5, float var6, float var7) {
      enableGL2D();
      GL11.glColor4f(var4, var5, var6, var7);
      drawRect(var0, var1, var2, var3);
      disableGL2D();
   }

   public static void drawCircle(float var0, float var1, float var2, int var3, int var4) {
      var2 *= 2.0F;
      var0 *= 2.0F;
      var1 *= 2.0F;
      float var5 = (float)(var4 >> 24 & 255) / 255.0F;
      float var6 = (float)(var4 >> 16 & 255) / 255.0F;
      float var7 = (float)(var4 >> 8 & 255) / 255.0F;
      float var8 = (float)(var4 & 255) / 255.0F;
      float var9 = (float)(6.2831852D / (double)var3);
      float var10 = (float)Math.cos((double)var9);
      float var11 = (float)Math.sin((double)var9);
      float var12 = var2;
      float var13 = 0.0F;
      enableGL2D();
      GL11.glScalef(0.5F, 0.5F, 0.5F);
      GL11.glColor4f(var6, var7, var8, var5);
      GL11.glBegin(2);

      for(int var14 = 0; var14 < var3; ++var14) {
         GL11.glVertex2f(var12 + var0, var13 + var1);
         float var15 = var12;
         var12 = var10 * var12 - var11 * var13;
         var13 = var11 * var15 + var10 * var13;
      }

      GL11.glEnd();
      GL11.glScalef(2.0F, 2.0F, 2.0F);
      disableGL2D();
   }

   public static void drawGradientRect(float var0, float var1, float var2, float var3, int var4, int var5) {
      GL11.glEnable(3042);
      GL11.glDisable(3553);
      GL11.glBlendFunc(770, 771);
      GL11.glEnable(2848);
      GL11.glShadeModel(7425);
      GL11.glPushMatrix();
      GL11.glBegin(7);
      glColor(var4);
      GL11.glVertex2d((double)var0, (double)var3);
      GL11.glVertex2d((double)var2, (double)var3);
      glColor(var5);
      GL11.glVertex2d((double)var2, (double)var1);
      GL11.glVertex2d((double)var0, (double)var1);
      GL11.glEnd();
      GL11.glPopMatrix();
      GL11.glEnable(3553);
      GL11.glDisable(3042);
      GL11.glDisable(2848);
      GL11.glShadeModel(7424);
   }

   public static void glColor(float var0, int var1, int var2, int var3) {
      float var4 = 0.003921569F * (float)var1;
      float var5 = 0.003921569F * (float)var2;
      float var6 = 0.003921569F * (float)var3;
      GL11.glColor4f(var4, var5, var6, var0);
   }

   public static void drawRect(float var0, float var1, float var2, float var3) {
      GL11.glBegin(7);
      GL11.glVertex2f(var0, var3);
      GL11.glVertex2f(var2, var3);
      GL11.glVertex2f(var2, var1);
      GL11.glVertex2f(var0, var1);
      GL11.glEnd();
   }

   public static void drawGradientBorderedRect(double var0, double var2, double var4, double var6, float var8, int var9, int var10, int var11) {
      enableGL2D();
      GL11.glPushMatrix();
      glColor(var9);
      GL11.glLineWidth(1.0F);
      GL11.glBegin(1);
      GL11.glVertex2d(var0, var2);
      GL11.glVertex2d(var0, var6);
      GL11.glVertex2d(var4, var6);
      GL11.glVertex2d(var4, var2);
      GL11.glVertex2d(var0, var2);
      GL11.glVertex2d(var4, var2);
      GL11.glVertex2d(var0, var6);
      GL11.glVertex2d(var4, var6);
      GL11.glEnd();
      GL11.glPopMatrix();
      drawGradientRect(var0, var2, var4, var6, var10, var11);
      disableGL2D();
   }

   public static void drawStrip(int var0, int var1, float var2, double var3, float var5, float var6, int var7) {
      float var8 = (float)(var7 >> 24 & 255) / 255.0F;
      float var9 = (float)(var7 >> 16 & 255) / 255.0F;
      float var10 = (float)(var7 >> 8 & 255) / 255.0F;
      float var11 = (float)(var7 & 255) / 255.0F;
      GL11.glPushMatrix();
      GL11.glTranslated((double)var0, (double)var1, 0.0D);
      GL11.glColor4f(var9, var10, var11, var8);
      GL11.glLineWidth(var2);
      int var12;
      float var13;
      float var14;
      float var15;
      if (var3 > 0.0D) {
         GL11.glBegin(3);

         for(var12 = 0; (double)var12 < var3; ++var12) {
            var13 = (float)((double)var12 * (var3 * 3.141592653589793D / (double)var5));
            var14 = (float)(Math.cos((double)var13) * (double)var6);
            var15 = (float)(Math.sin((double)var13) * (double)var6);
            GL11.glVertex2f(var14, var15);
         }

         GL11.glEnd();
      }

      if (var3 < 0.0D) {
         GL11.glBegin(3);

         for(var12 = 0; (double)var12 > var3; --var12) {
            var13 = (float)((double)var12 * (var3 * 3.141592653589793D / (double)var5));
            var14 = (float)(Math.cos((double)var13) * (double)(-var6));
            var15 = (float)(Math.sin((double)var13) * (double)(-var6));
            GL11.glVertex2f(var14, var15);
         }

         GL11.glEnd();
      }

      disableGL2D();
      GL11.glDisable(3479);
      GL11.glPopMatrix();
   }

   public static void drawHLine(float var0, float var1, float var2, int var3, int var4) {
      if (var1 < var0) {
         float var5 = var0;
         var0 = var1;
         var1 = var5;
      }

      drawGradientRect(var0, var2, var1 + 1.0F, var2 + 1.0F, var3, var4);
   }

   public static void enableGL3D() {
      GL11.glDisable(3008);
      GL11.glEnable(3042);
      GL11.glBlendFunc(770, 771);
      GL11.glDisable(3553);
      GL11.glDisable(2929);
      GL11.glDepthMask(false);
      GL11.glEnable(2884);
      GL11.glEnable(2848);
      GL11.glHint(3154, 4353);
      GL11.glDisable(2896);
   }

   public static Color blend(Color var0, Color var1, float var2) {
      float var3 = 1.0F - var2;
      float[] var4 = new float[3];
      float[] var5 = new float[3];
      var0.getColorComponents(var4);
      var1.getColorComponents(var5);
      Color var6 = new Color(var4[0] * var2 + var5[0] * var3, var4[1] * var2 + var5[1] * var3, var4[2] * var2 + var5[2] * var3);
      return var6;
   }

   public static void glColor(int var0) {
      float var1 = (float)(var0 >> 24 & 255) / 255.0F;
      float var2 = (float)(var0 >> 16 & 255) / 255.0F;
      float var3 = (float)(var0 >> 8 & 255) / 255.0F;
      float var4 = (float)(var0 & 255) / 255.0F;
      GL11.glColor4f(var2, var3, var4, var1);
   }
}
