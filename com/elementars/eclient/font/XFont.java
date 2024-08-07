package com.elementars.eclient.font;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.TextureUtil;
import org.lwjgl.opengl.GL11;

public class XFont {
   // $FF: synthetic field
   private int charOffset = 8;
   // $FF: synthetic field
   private final Font font;
   // $FF: synthetic field
   private int fontHeight = -1;
   // $FF: synthetic field
   private boolean antiAlias;
   // $FF: synthetic field
   public int IMAGE_HEIGHT = 1024;
   // $FF: synthetic field
   private final XFont.IntObject[] chars = new XFont.IntObject[2048];
   // $FF: synthetic field
   private int texID;
   // $FF: synthetic field
   public int IMAGE_WIDTH = 1024;

   public int getStringWidth(String var1) {
      int var2 = 0;
      char[] var3 = var1.toCharArray();
      int var4 = var3.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         char var6 = var3[var5];
         if (var6 < this.chars.length && var6 >= 0) {
            var2 += this.chars[var6].width - this.charOffset;
         }
      }

      return var2 / 2;
   }

   private void drawQuad(float var1, float var2, float var3, float var4, float var5, float var6, float var7, float var8) {
      float var9 = var5 / (float)this.IMAGE_WIDTH;
      float var10 = var6 / (float)this.IMAGE_HEIGHT;
      float var11 = var7 / (float)this.IMAGE_WIDTH;
      float var12 = var8 / (float)this.IMAGE_HEIGHT;
      GL11.glBegin(4);
      GL11.glTexCoord2f(var9 + var11, var10);
      GL11.glVertex2d((double)(var1 + var3), (double)var2);
      GL11.glTexCoord2f(var9, var10);
      GL11.glVertex2d((double)var1, (double)var2);
      GL11.glTexCoord2f(var9, var10 + var12);
      GL11.glVertex2d((double)var1, (double)(var2 + var4));
      GL11.glTexCoord2f(var9, var10 + var12);
      GL11.glVertex2d((double)var1, (double)(var2 + var4));
      GL11.glTexCoord2f(var9 + var11, var10 + var12);
      GL11.glVertex2d((double)(var1 + var3), (double)(var2 + var4));
      GL11.glTexCoord2f(var9 + var11, var10);
      GL11.glVertex2d((double)(var1 + var3), (double)var2);
      GL11.glEnd();
   }

   public Font getFont() {
      return this.font;
   }

   public void drawString(String var1, double var2, double var4, Color var6, boolean var7) {
      var2 *= 2.0D;
      var4 = var4 * 2.0D - 2.0D;
      GL11.glPushMatrix();
      GL11.glScaled(0.25D, 0.25D, 0.25D);
      GlStateManager.bindTexture(this.texID);
      this.glColor(var7 ? var6.darker().darker().darker() : var6);
      int var8 = var1.length();

      for(int var9 = 0; var9 < var8; ++var9) {
         char var10 = var1.charAt(var9);
         if (var10 < this.chars.length && var10 >= 0) {
            this.drawChar(var10, (float)var2, (float)var4);
            var2 += (double)(this.chars[var10].width - this.charOffset);
         }
      }

      GL11.glPopMatrix();
   }

   public void setAntiAlias(boolean var1) {
      if (this.antiAlias != var1) {
         this.antiAlias = var1;
         this.setupTexture(var1);
      }

   }

   public void drawChar(char var1, float var2, float var3) throws ArrayIndexOutOfBoundsException {
      try {
         this.drawQuad(var2, var3, (float)this.chars[var1].width, (float)this.chars[var1].height, (float)this.chars[var1].storedX, (float)this.chars[var1].storedY, (float)this.chars[var1].width, (float)this.chars[var1].height);
      } catch (Exception var5) {
         var5.printStackTrace();
      }

   }

   public void glColor(Color var1) {
      float var2 = (float)var1.getRed() / 255.0F;
      float var3 = (float)var1.getGreen() / 255.0F;
      float var4 = (float)var1.getBlue() / 255.0F;
      float var5 = (float)var1.getAlpha() / 255.0F;
      GL11.glColor4f(var2, var3, var4, var5);
   }

   public int getHeight() {
      return (this.fontHeight - this.charOffset) / 2;
   }

   public XFont(Font var1, boolean var2) {
      this.font = var1;
      this.antiAlias = var2;
      this.charOffset = 8;
      this.setupTexture(var2);
   }

   public int getStringHeight(String var1) {
      int var2 = 1;
      char[] var3 = var1.toCharArray();
      int var4 = var3.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         char var6 = var3[var5];
         if (var6 == '\n') {
            ++var2;
         }
      }

      return (this.fontHeight - this.charOffset) / 2 * var2;
   }

   private BufferedImage getFontImage(char var1, boolean var2) {
      BufferedImage var3 = new BufferedImage(1, 1, 2);
      Graphics2D var4 = (Graphics2D)var3.getGraphics();
      if (var2) {
         var4.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
      } else {
         var4.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
      }

      var4.setFont(this.font);
      FontMetrics var5 = var4.getFontMetrics();
      int var6 = var5.charWidth(var1) + 8;
      if (var6 <= 0) {
         var6 = 7;
      }

      int var7 = var5.getHeight() + 3;
      if (var7 <= 0) {
         var7 = this.font.getSize();
      }

      BufferedImage var8 = new BufferedImage(var6, var7, 2);
      Graphics2D var9 = (Graphics2D)var8.getGraphics();
      if (var2) {
         var9.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
      } else {
         var9.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
      }

      var9.setFont(this.font);
      var9.setColor(Color.WHITE);
      boolean var10 = true;
      boolean var11 = true;
      var9.drawString(String.valueOf(var1), 3, 1 + var5.getAscent());
      return var8;
   }

   public XFont(Font var1, boolean var2, int var3) {
      this.font = var1;
      this.antiAlias = var2;
      this.charOffset = var3;
      this.setupTexture(var2);
   }

   public boolean isAntiAlias() {
      return this.antiAlias;
   }

   private void setupTexture(boolean var1) {
      if (this.font.getSize() <= 15) {
         this.IMAGE_WIDTH = 256;
         this.IMAGE_HEIGHT = 256;
      }

      if (this.font.getSize() <= 43) {
         this.IMAGE_WIDTH = 512;
         this.IMAGE_HEIGHT = 512;
      } else if (this.font.getSize() <= 91) {
         this.IMAGE_WIDTH = 1024;
         this.IMAGE_HEIGHT = 1024;
      } else {
         this.IMAGE_WIDTH = 2048;
         this.IMAGE_HEIGHT = 2048;
      }

      BufferedImage var2 = new BufferedImage(this.IMAGE_WIDTH, this.IMAGE_HEIGHT, 2);
      Graphics2D var3 = (Graphics2D)var2.getGraphics();
      var3.setFont(this.font);
      var3.setColor(new Color(255, 255, 255, 0));
      var3.fillRect(0, 0, this.IMAGE_WIDTH, this.IMAGE_HEIGHT);
      var3.setColor(Color.white);
      int var4 = 0;
      int var5 = 0;
      int var6 = 0;

      for(int var7 = 0; var7 < 2048; ++var7) {
         char var8 = (char)var7;
         BufferedImage var9 = this.getFontImage(var8, var1);
         XFont.IntObject var10 = new XFont.IntObject();
         var10.width = var9.getWidth();
         var10.height = var9.getHeight();
         if (var5 + var10.width >= this.IMAGE_WIDTH) {
            var5 = 0;
            var6 += var4;
            var4 = 0;
         }

         var10.storedX = var5;
         var10.storedY = var6;
         if (var10.height > this.fontHeight) {
            this.fontHeight = var10.height;
         }

         if (var10.height > var4) {
            var4 = var10.height;
         }

         this.chars[var7] = var10;
         var3.drawImage(var9, var5, var6, (ImageObserver)null);
         var5 += var10.width;
      }

      try {
         this.texID = TextureUtil.uploadTextureImageAllocate(TextureUtil.glGenTextures(), var2, true, true);
      } catch (NullPointerException var11) {
         var11.printStackTrace();
      }

   }

   private class IntObject {
      // $FF: synthetic field
      public int storedX;
      // $FF: synthetic field
      public int storedY;
      // $FF: synthetic field
      public int width;
      // $FF: synthetic field
      public int height;

      private IntObject() {
      }

      // $FF: synthetic method
      IntObject(Object var2) {
         this();
      }
   }
}
