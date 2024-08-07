package org.newdawn.slick.opengl;

import java.nio.ByteBuffer;
import org.lwjgl.BufferUtils;

public class EmptyImageData implements ImageData {
   // $FF: synthetic field
   private int height;
   // $FF: synthetic field
   private int width;

   public ByteBuffer getImageBufferData() {
      return BufferUtils.createByteBuffer(this.getTexWidth() * this.getTexHeight() * 4);
   }

   public int getDepth() {
      return 32;
   }

   public int getWidth() {
      return this.width;
   }

   public int getHeight() {
      return this.height;
   }

   public int getTexHeight() {
      return InternalTextureLoader.get2Fold(this.height);
   }

   public int getTexWidth() {
      return InternalTextureLoader.get2Fold(this.width);
   }

   public EmptyImageData(int var1, int var2) {
      this.width = var1;
      this.height = var2;
   }
}
