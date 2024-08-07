package org.newdawn.slick.opengl;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import org.lwjgl.BufferUtils;

public class PNGImageData implements LoadableImageData {
   // $FF: synthetic field
   private int height;
   // $FF: synthetic field
   private ByteBuffer scratch;
   // $FF: synthetic field
   private int texHeight;
   // $FF: synthetic field
   private PNGDecoder decoder;
   // $FF: synthetic field
   private int width;
   // $FF: synthetic field
   private int texWidth;
   // $FF: synthetic field
   private int bitDepth;

   public int getTexWidth() {
      return this.texWidth;
   }

   public ByteBuffer loadImage(InputStream var1, boolean var2, int[] var3) throws IOException {
      return this.loadImage(var1, var2, false, var3);
   }

   private int toInt(byte var1) {
      return var1 < 0 ? 256 + var1 : var1;
   }

   public int getWidth() {
      return this.width;
   }

   public void configureEdging(boolean var1) {
   }

   public ByteBuffer getImageBufferData() {
      return this.scratch;
   }

   public ByteBuffer loadImage(InputStream var1) throws IOException {
      return this.loadImage(var1, false, (int[])null);
   }

   public ByteBuffer loadImage(InputStream var1, boolean var2, boolean var3, int[] var4) throws IOException {
      if (var4 != null) {
         var3 = true;
         throw new IOException("Transparent color not support in custom PNG Decoder");
      } else {
         PNGDecoder var5 = new PNGDecoder(var1);
         if (!var5.isRGB()) {
            throw new IOException("Only RGB formatted images are supported by the PNGLoader");
         } else {
            this.width = var5.getWidth();
            this.height = var5.getHeight();
            this.texWidth = this.get2Fold(this.width);
            this.texHeight = this.get2Fold(this.height);
            int var6 = var5.hasAlpha() ? 4 : 3;
            this.bitDepth = var5.hasAlpha() ? 32 : 24;
            this.scratch = BufferUtils.createByteBuffer(this.texWidth * this.texHeight * var6);
            var5.decode(this.scratch, this.texWidth * var6, var6 == 4 ? PNGDecoder.RGBA : PNGDecoder.RGB);
            int var7;
            int var8;
            int var9;
            int var10;
            if (this.height < this.texHeight - 1) {
               var7 = (this.texHeight - 1) * this.texWidth * var6;
               var8 = (this.height - 1) * this.texWidth * var6;

               for(var9 = 0; var9 < this.texWidth; ++var9) {
                  for(var10 = 0; var10 < var6; ++var10) {
                     this.scratch.put(var7 + var9 + var10, this.scratch.get(var9 + var10));
                     this.scratch.put(var8 + this.texWidth * var6 + var9 + var10, this.scratch.get(var8 + var9 + var10));
                  }
               }
            }

            if (this.width < this.texWidth - 1) {
               for(var7 = 0; var7 < this.texHeight; ++var7) {
                  for(var8 = 0; var8 < var6; ++var8) {
                     this.scratch.put((var7 + 1) * this.texWidth * var6 - var6 + var8, this.scratch.get(var7 * this.texWidth * var6 + var8));
                     this.scratch.put(var7 * this.texWidth * var6 + this.width * var6 + var8, this.scratch.get(var7 * this.texWidth * var6 + (this.width - 1) * var6 + var8));
                  }
               }
            }

            if (!var5.hasAlpha() && var3) {
               ByteBuffer var12 = BufferUtils.createByteBuffer(this.texWidth * this.texHeight * 4);

               for(var8 = 0; var8 < this.texWidth; ++var8) {
                  for(var9 = 0; var9 < this.texHeight; ++var9) {
                     var10 = var9 * 3 + var8 * this.texHeight * 3;
                     int var11 = var9 * 4 + var8 * this.texHeight * 4;
                     var12.put(var11, this.scratch.get(var10));
                     var12.put(var11 + 1, this.scratch.get(var10 + 1));
                     var12.put(var11 + 2, this.scratch.get(var10 + 2));
                     if (var8 < this.getHeight() && var9 < this.getWidth()) {
                        var12.put(var11 + 3, (byte)-1);
                     } else {
                        var12.put(var11 + 3, (byte)0);
                     }
                  }
               }

               this.bitDepth = 32;
               this.scratch = var12;
            }

            if (var4 != null) {
               for(var7 = 0; var7 < this.texWidth * this.texHeight * 4; var7 += 4) {
                  boolean var13 = true;

                  for(var9 = 0; var9 < 3; ++var9) {
                     if (this.toInt(this.scratch.get(var7 + var9)) != var4[var9]) {
                        var13 = false;
                     }
                  }

                  if (var13) {
                     this.scratch.put(var7 + 3, (byte)0);
                  }
               }
            }

            this.scratch.position(0);
            return this.scratch;
         }
      }
   }

   public int getHeight() {
      return this.height;
   }

   public int getTexHeight() {
      return this.texHeight;
   }

   public int getDepth() {
      return this.bitDepth;
   }

   private int get2Fold(int var1) {
      int var2;
      for(var2 = 2; var2 < var1; var2 *= 2) {
      }

      return var2;
   }
}
