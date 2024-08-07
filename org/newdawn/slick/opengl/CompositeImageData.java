package org.newdawn.slick.opengl;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import org.newdawn.slick.util.Log;

public class CompositeImageData implements LoadableImageData {
   // $FF: synthetic field
   private ArrayList sources = new ArrayList();
   // $FF: synthetic field
   private LoadableImageData picked;

   public ByteBuffer getImageBufferData() {
      this.checkPicked();
      return this.picked.getImageBufferData();
   }

   public ByteBuffer loadImage(InputStream var1, boolean var2, boolean var3, int[] var4) throws IOException {
      CompositeIOException var5 = new CompositeIOException();
      ByteBuffer var6 = null;
      BufferedInputStream var7 = new BufferedInputStream(var1, var1.available());
      var7.mark(var1.available());
      int var8 = 0;

      while(var8 < this.sources.size()) {
         var7.reset();

         try {
            LoadableImageData var9 = (LoadableImageData)this.sources.get(var8);
            var6 = var9.loadImage(var7, var2, var3, var4);
            this.picked = var9;
            break;
         } catch (Exception var10) {
            Log.warn(String.valueOf((new StringBuilder()).append(this.sources.get(var8).getClass()).append(" failed to read the data")), var10);
            var5.addException(var10);
            ++var8;
         }
      }

      if (this.picked == null) {
         throw var5;
      } else {
         return var6;
      }
   }

   private void checkPicked() {
      if (this.picked == null) {
         throw new RuntimeException("Attempt to make use of uninitialised or invalid composite image data");
      }
   }

   public void add(LoadableImageData var1) {
      this.sources.add(var1);
   }

   public int getDepth() {
      this.checkPicked();
      return this.picked.getDepth();
   }

   public void configureEdging(boolean var1) {
      for(int var2 = 0; var2 < this.sources.size(); ++var2) {
         ((LoadableImageData)this.sources.get(var2)).configureEdging(var1);
      }

   }

   public int getTexWidth() {
      this.checkPicked();
      return this.picked.getTexWidth();
   }

   public ByteBuffer loadImage(InputStream var1, boolean var2, int[] var3) throws IOException {
      return this.loadImage(var1, var2, false, var3);
   }

   public int getTexHeight() {
      this.checkPicked();
      return this.picked.getTexHeight();
   }

   public int getWidth() {
      this.checkPicked();
      return this.picked.getWidth();
   }

   public ByteBuffer loadImage(InputStream var1) throws IOException {
      return this.loadImage(var1, false, (int[])null);
   }

   public int getHeight() {
      this.checkPicked();
      return this.picked.getHeight();
   }
}
