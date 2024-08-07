package org.newdawn.slick.opengl;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import org.lwjgl.BufferUtils;
import org.newdawn.slick.opengl.renderer.Renderer;
import org.newdawn.slick.opengl.renderer.SGL;
import org.newdawn.slick.util.Log;

public class TextureImpl implements Texture {
   // $FF: synthetic field
   private int height;
   // $FF: synthetic field
   private boolean alpha;
   // $FF: synthetic field
   private int texHeight;
   // $FF: synthetic field
   private int textureID;
   // $FF: synthetic field
   static Texture lastBind;
   // $FF: synthetic field
   protected static SGL GL = Renderer.get();
   // $FF: synthetic field
   private float heightRatio;
   // $FF: synthetic field
   private int texWidth;
   // $FF: synthetic field
   private float widthRatio;
   // $FF: synthetic field
   private int target;
   // $FF: synthetic field
   private String ref;
   // $FF: synthetic field
   private String cacheName;
   // $FF: synthetic field
   private int width;
   // $FF: synthetic field
   private TextureImpl.ReloadData reloadData;

   public TextureImpl(String var1, int var2, int var3) {
      this.target = var2;
      this.ref = var1;
      this.textureID = var3;
      lastBind = this;
   }

   public void setWidth(int var1) {
      this.width = var1;
      this.setWidth();
   }

   public void setHeight(int var1) {
      this.height = var1;
      this.setHeight();
   }

   public static void bindNone() {
      lastBind = null;
      GL.glDisable(3553);
   }

   public boolean hasAlpha() {
      return this.alpha;
   }

   public void setAlpha(boolean var1) {
      this.alpha = var1;
   }

   public static Texture getLastBind() {
      return lastBind;
   }

   public void bind() {
      if (lastBind != this) {
         lastBind = this;
         GL.glEnable(3553);
         GL.glBindTexture(this.target, this.textureID);
      }

   }

   public int getTextureWidth() {
      return this.texWidth;
   }

   public String getTextureRef() {
      return this.ref;
   }

   public void setCacheName(String var1) {
      this.cacheName = var1;
   }

   public void setTextureFilter(int var1) {
      this.bind();
      GL.glTexParameteri(this.target, 10241, var1);
      GL.glTexParameteri(this.target, 10240, var1);
   }

   public void setTextureData(int var1, int var2, int var3, int var4, ByteBuffer var5) {
      this.reloadData = new TextureImpl.ReloadData();
      this.reloadData.srcPixelFormat = var1;
      this.reloadData.componentCount = var2;
      this.reloadData.minFilter = var3;
      this.reloadData.magFilter = var4;
      this.reloadData.textureBuffer = var5;
   }

   public int getImageWidth() {
      return this.width;
   }

   private void setWidth() {
      if (this.texWidth != 0) {
         this.widthRatio = (float)this.width / (float)this.texWidth;
      }

   }

   protected TextureImpl() {
   }

   public void setTextureID(int var1) {
      this.textureID = var1;
   }

   public float getHeight() {
      return this.heightRatio;
   }

   public void reload() {
      if (this.reloadData != null) {
         this.textureID = this.reloadData.reload();
      }

   }

   public void release() {
      IntBuffer var1 = this.createIntBuffer(1);
      var1.put(this.textureID);
      var1.flip();
      GL.glDeleteTextures(var1);
      if (lastBind == this) {
         bindNone();
      }

      if (this.cacheName != null) {
         InternalTextureLoader.get().clear(this.cacheName);
      } else {
         InternalTextureLoader.get().clear(this.ref);
      }

   }

   public byte[] getTextureData() {
      ByteBuffer var1 = BufferUtils.createByteBuffer((this.hasAlpha() ? 4 : 3) * this.texWidth * this.texHeight);
      this.bind();
      GL.glGetTexImage(3553, 0, this.hasAlpha() ? 6408 : 6407, 5121, var1);
      byte[] var2 = new byte[var1.limit()];
      var1.get(var2);
      var1.clear();
      return var2;
   }

   private void setHeight() {
      if (this.texHeight != 0) {
         this.heightRatio = (float)this.height / (float)this.texHeight;
      }

   }

   public int getImageHeight() {
      return this.height;
   }

   public int getTextureHeight() {
      return this.texHeight;
   }

   protected IntBuffer createIntBuffer(int var1) {
      ByteBuffer var2 = ByteBuffer.allocateDirect(4 * var1);
      var2.order(ByteOrder.nativeOrder());
      return var2.asIntBuffer();
   }

   public float getWidth() {
      return this.widthRatio;
   }

   public int getTextureID() {
      return this.textureID;
   }

   public void setTextureWidth(int var1) {
      this.texWidth = var1;
      this.setWidth();
   }

   public static void unbind() {
      lastBind = null;
   }

   public void setTextureHeight(int var1) {
      this.texHeight = var1;
      this.setHeight();
   }

   private class ReloadData {
      // $FF: synthetic field
      private int magFilter;
      // $FF: synthetic field
      private int minFilter;
      // $FF: synthetic field
      private ByteBuffer textureBuffer;
      // $FF: synthetic field
      private int componentCount;
      // $FF: synthetic field
      private int srcPixelFormat;

      // $FF: synthetic method
      ReloadData(Object var2) {
         this();
      }

      private ReloadData() {
      }

      public int reload() {
         Log.error(String.valueOf((new StringBuilder()).append("Reloading texture: ").append(TextureImpl.this.ref)));
         return InternalTextureLoader.get().reload(TextureImpl.this, this.srcPixelFormat, this.componentCount, this.minFilter, this.magFilter, this.textureBuffer);
      }
   }
}
