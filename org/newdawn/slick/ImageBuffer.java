package org.newdawn.slick;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import org.lwjgl.BufferUtils;
import org.newdawn.slick.opengl.ImageData;

public class ImageBuffer implements ImageData {
   // $FF: synthetic field
   private int texWidth;
   // $FF: synthetic field
   private int width;
   // $FF: synthetic field
   private int height;
   // $FF: synthetic field
   private byte[] rawData;
   // $FF: synthetic field
   private int texHeight;

   public Image getImage(int var1) {
      return new Image(this, var1);
   }

   public int getWidth() {
      return this.width;
   }

   public byte[] getRGBA() {
      return this.rawData;
   }

   public ImageBuffer(int var1, int var2) {
      this.width = var1;
      this.height = var2;
      this.texWidth = this.get2Fold(var1);
      this.texHeight = this.get2Fold(var2);
      this.rawData = new byte[this.texWidth * this.texHeight * 4];
   }

   public int getTexWidth() {
      return this.texWidth;
   }

   public int getDepth() {
      return 32;
   }

   public ByteBuffer getImageBufferData() {
      ByteBuffer var1 = BufferUtils.createByteBuffer(this.rawData.length);
      var1.put(this.rawData);
      var1.flip();
      return var1;
   }

   public int getHeight() {
      return this.height;
   }

   public void setRGBA(int var1, int var2, int var3, int var4, int var5, int var6) {
      if (var1 >= 0 && var1 < this.width && var2 >= 0 && var2 < this.height) {
         int var7 = (var1 + var2 * this.texWidth) * 4;
         if (ByteOrder.nativeOrder() == ByteOrder.BIG_ENDIAN) {
            this.rawData[var7] = (byte)var5;
            this.rawData[var7 + 1] = (byte)var4;
            this.rawData[var7 + 2] = (byte)var3;
            this.rawData[var7 + 3] = (byte)var6;
         } else {
            this.rawData[var7] = (byte)var3;
            this.rawData[var7 + 1] = (byte)var4;
            this.rawData[var7 + 2] = (byte)var5;
            this.rawData[var7 + 3] = (byte)var6;
         }

      } else {
         throw new RuntimeException(String.valueOf((new StringBuilder()).append("Specified location: ").append(var1).append(",").append(var2).append(" outside of image")));
      }
   }

   public Image getImage() {
      return new Image(this);
   }

   public int getTexHeight() {
      return this.texHeight;
   }

   private int get2Fold(int var1) {
      int var2;
      for(var2 = 2; var2 < var1; var2 *= 2) {
      }

      return var2;
   }
}
