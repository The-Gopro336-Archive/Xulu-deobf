package org.newdawn.slick.opengl;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import java.util.HashMap;
import java.util.Iterator;
import org.lwjgl.BufferUtils;
import org.newdawn.slick.opengl.renderer.Renderer;
import org.newdawn.slick.opengl.renderer.SGL;
import org.newdawn.slick.util.ResourceLoader;

public class InternalTextureLoader {
   // $FF: synthetic field
   private boolean deferred;
   // $FF: synthetic field
   private boolean holdTextureData;
   // $FF: synthetic field
   private HashMap texturesNearest = new HashMap();
   // $FF: synthetic field
   private HashMap texturesLinear = new HashMap();
   // $FF: synthetic field
   private static final InternalTextureLoader loader = new InternalTextureLoader();
   // $FF: synthetic field
   private int dstPixelFormat = 6408;
   // $FF: synthetic field
   protected static SGL GL = Renderer.get();

   public static int createTextureID() {
      IntBuffer var0 = createIntBuffer(1);
      GL.glGenTextures(var0);
      return var0.get(0);
   }

   public void setHoldTextureData(boolean var1) {
      this.holdTextureData = var1;
   }

   public void set16BitMode() {
      this.dstPixelFormat = 32859;
   }

   public static IntBuffer createIntBuffer(int var0) {
      ByteBuffer var1 = ByteBuffer.allocateDirect(4 * var0);
      var1.order(ByteOrder.nativeOrder());
      return var1.asIntBuffer();
   }

   public Texture getTexture(String var1, boolean var2, int var3, int[] var4) throws IOException {
      InputStream var5 = ResourceLoader.getResourceAsStream(var1);
      return this.getTexture(var5, var1, var2, var3, var4);
   }

   public static int get2Fold(int var0) {
      int var1;
      for(var1 = 2; var1 < var0; var1 *= 2) {
      }

      return var1;
   }

   public static InternalTextureLoader get() {
      return loader;
   }

   public void clear(String var1) {
      this.texturesLinear.remove(var1);
      this.texturesNearest.remove(var1);
   }

   public Texture getTexture(ImageData var1, int var2) throws IOException {
      short var3 = 3553;
      ByteBuffer var4 = var1.getImageBufferData();
      int var5 = createTextureID();
      TextureImpl var6 = new TextureImpl(String.valueOf((new StringBuilder()).append("generated:").append(var1)), var3, var5);
      boolean var9 = false;
      GL.glBindTexture(var3, var5);
      int var10 = var1.getWidth();
      int var11 = var1.getHeight();
      boolean var14 = var1.getDepth() == 32;
      var6.setTextureWidth(var1.getTexWidth());
      var6.setTextureHeight(var1.getTexHeight());
      int var12 = var6.getTextureWidth();
      int var13 = var6.getTextureHeight();
      int var15 = var14 ? 6408 : 6407;
      int var16 = var14 ? 4 : 3;
      var6.setWidth(var10);
      var6.setHeight(var11);
      var6.setAlpha(var14);
      IntBuffer var17 = BufferUtils.createIntBuffer(16);
      GL.glGetInteger(3379, var17);
      int var18 = var17.get(0);
      if (var12 <= var18 && var13 <= var18) {
         if (this.holdTextureData) {
            var6.setTextureData(var15, var16, var2, var2, var4);
         }

         GL.glTexParameteri(var3, 10241, var2);
         GL.glTexParameteri(var3, 10240, var2);
         GL.glTexImage2D(var3, 0, this.dstPixelFormat, get2Fold(var10), get2Fold(var11), 0, var15, 5121, var4);
         return var6;
      } else {
         throw new IOException("Attempt to allocate a texture to big for the current hardware");
      }
   }

   public Texture getTexture(InputStream var1, String var2, boolean var3, int var4) throws IOException {
      return this.getTexture(var1, var2, var3, var4, (int[])null);
   }

   public Texture createTexture(int var1, int var2) throws IOException {
      return this.createTexture(var1, var2, 9728);
   }

   public Texture getTexture(File var1, boolean var2, int var3) throws IOException {
      String var4 = var1.getAbsolutePath();
      FileInputStream var5 = new FileInputStream(var1);
      return this.getTexture(var5, var4, var2, var3, (int[])null);
   }

   private InternalTextureLoader() {
   }

   public void reload() {
      Iterator var1 = this.texturesLinear.values().iterator();

      while(var1.hasNext()) {
         ((TextureImpl)var1.next()).reload();
      }

      var1 = this.texturesNearest.values().iterator();

      while(var1.hasNext()) {
         ((TextureImpl)var1.next()).reload();
      }

   }

   private TextureImpl getTexture(InputStream var1, String var2, int var3, int var4, int var5, boolean var6, int[] var7) throws IOException {
      LoadableImageData var9 = ImageDataFactory.getImageDataFor(var2);
      ByteBuffer var8 = var9.loadImage(new BufferedInputStream(var1), var6, var7);
      int var10 = createTextureID();
      TextureImpl var11 = new TextureImpl(var2, var3, var10);
      GL.glBindTexture(var3, var10);
      int var12 = var9.getWidth();
      int var13 = var9.getHeight();
      boolean var16 = var9.getDepth() == 32;
      var11.setTextureWidth(var9.getTexWidth());
      var11.setTextureHeight(var9.getTexHeight());
      int var14 = var11.getTextureWidth();
      int var15 = var11.getTextureHeight();
      IntBuffer var17 = BufferUtils.createIntBuffer(16);
      GL.glGetInteger(3379, var17);
      int var18 = var17.get(0);
      if (var14 <= var18 && var15 <= var18) {
         int var19 = var16 ? 6408 : 6407;
         int var20 = var16 ? 4 : 3;
         var11.setWidth(var12);
         var11.setHeight(var13);
         var11.setAlpha(var16);
         if (this.holdTextureData) {
            var11.setTextureData(var19, var20, var5, var4, var8);
         }

         SGL var10002 = GL;
         GL.glTexParameteri(var3, 10241, var5);
         var10002 = GL;
         GL.glTexParameteri(var3, 10240, var4);
         GL.glTexImage2D(var3, 0, this.dstPixelFormat, get2Fold(var12), get2Fold(var13), 0, var19, 5121, var8);
         return var11;
      } else {
         throw new IOException("Attempt to allocate a texture to big for the current hardware");
      }
   }

   public void clear() {
      this.texturesLinear.clear();
      this.texturesNearest.clear();
   }

   public Texture getTexture(File var1, boolean var2, int var3, int[] var4) throws IOException {
      String var5 = var1.getAbsolutePath();
      FileInputStream var6 = new FileInputStream(var1);
      return this.getTexture(var6, var5, var2, var3, var4);
   }

   public Texture getTexture(String var1, boolean var2, int var3) throws IOException {
      InputStream var4 = ResourceLoader.getResourceAsStream(var1);
      return this.getTexture(var4, var1, var2, var3, (int[])null);
   }

   public Texture createTexture(int var1, int var2, int var3) throws IOException {
      EmptyImageData var4 = new EmptyImageData(var1, var2);
      return this.getTexture(var4, var3);
   }

   public int reload(TextureImpl var1, int var2, int var3, int var4, int var5, ByteBuffer var6) {
      short var7 = 3553;
      int var8 = createTextureID();
      GL.glBindTexture(var7, var8);
      GL.glTexParameteri(var7, 10241, var4);
      GL.glTexParameteri(var7, 10240, var5);
      GL.glTexImage2D(var7, 0, this.dstPixelFormat, var1.getTextureWidth(), var1.getTextureHeight(), 0, var2, 5121, var6);
      return var8;
   }

   public boolean isDeferredLoading() {
      return this.deferred;
   }

   public void setDeferredLoading(boolean var1) {
      this.deferred = var1;
   }

   public TextureImpl getTexture(InputStream var1, String var2, boolean var3, int var4, int[] var5) throws IOException {
      if (this.deferred) {
         return new DeferredTexture(var1, var2, var3, var4, var5);
      } else {
         HashMap var6 = this.texturesLinear;
         if (var4 == 9728) {
            var6 = this.texturesNearest;
         }

         String var7 = var2;
         if (var5 != null) {
            var7 = String.valueOf((new StringBuilder()).append(var2).append(":").append(var5[0]).append(":").append(var5[1]).append(":").append(var5[2]));
         }

         var7 = String.valueOf((new StringBuilder()).append(var7).append(":").append(var3));
         TextureImpl var8;
         if (this.holdTextureData) {
            var8 = (TextureImpl)var6.get(var7);
            if (var8 != null) {
               return var8;
            }
         } else {
            SoftReference var11 = (SoftReference)var6.get(var7);
            if (var11 != null) {
               TextureImpl var9 = (TextureImpl)var11.get();
               if (var9 != null) {
                  return var9;
               }

               var6.remove(var7);
            }
         }

         try {
            GL.glGetError();
         } catch (NullPointerException var10) {
            throw new RuntimeException("Image based resources must be loaded as part of init() or the game loop. They cannot be loaded before initialisation.");
         }

         var8 = this.getTexture(var1, var2, 3553, var4, var4, var3, var5);
         var8.setCacheName(var7);
         if (this.holdTextureData) {
            var6.put(var7, var8);
         } else {
            var6.put(var7, new SoftReference(var8));
         }

         return var8;
      }
   }
}
