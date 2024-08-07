package org.newdawn.slick.geom;

import org.newdawn.slick.Image;
import org.newdawn.slick.ShapeFill;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureImpl;
import org.newdawn.slick.opengl.renderer.LineStripRenderer;
import org.newdawn.slick.opengl.renderer.Renderer;
import org.newdawn.slick.opengl.renderer.SGL;

public final class ShapeRenderer {
   // $FF: synthetic field
   private static SGL GL = Renderer.get();
   // $FF: synthetic field
   private static LineStripRenderer LSR = Renderer.getLineStripRenderer();

   public static final void fill(Shape var0, final ShapeFill var1) {
      if (validFill(var0)) {
         Texture var2 = TextureImpl.getLastBind();
         TextureImpl.bindNone();
         float[] var3 = var0.getCenter();
         fill(var0, new ShapeRenderer.PointCallback() {
            public float[] preRenderPoint(Shape var1x, float var2, float var3) {
               var1.colorAt(var1x, var2, var3).bind();
               Vector2f var4 = var1.getOffsetAt(var1x, var2, var3);
               return new float[]{var4.x + var2, var4.y + var3};
            }
         });
         if (var2 == null) {
            TextureImpl.bindNone();
         } else {
            var2.bind();
         }

      }
   }

   private static final void fill(Shape var0, ShapeRenderer.PointCallback var1) {
      Triangulator var2 = var0.getTriangles();
      GL.glBegin(4);

      for(int var3 = 0; var3 < var2.getTriangleCount(); ++var3) {
         for(int var4 = 0; var4 < 3; ++var4) {
            float[] var5 = var2.getTrianglePoint(var3, var4);
            float[] var6 = var1.preRenderPoint(var0, var5[0], var5[1]);
            if (var6 == null) {
               GL.glVertex2f(var5[0], var5[1]);
            } else {
               GL.glVertex2f(var6[0], var6[1]);
            }
         }
      }

      GL.glEnd();
   }

   public static final void textureFit(Shape var0, Image var1) {
      textureFit(var0, var1, 1.0F, 1.0F);
   }

   public static final void texture(Shape var0, Image var1, final TexCoordGenerator var2) {
      Texture var3 = TextureImpl.getLastBind();
      var1.getTexture().bind();
      float[] var4 = var0.getCenter();
      fill(var0, new ShapeRenderer.PointCallback() {
         public float[] preRenderPoint(Shape var1, float var2x, float var3) {
            Vector2f var4 = var2.getCoordFor(var2x, var3);
            ShapeRenderer.GL.glTexCoord2f(var4.x, var4.y);
            return new float[]{var2x, var3};
         }
      });
      if (var3 == null) {
         TextureImpl.bindNone();
      } else {
         var3.bind();
      }

   }

   public static final void texture(Shape var0, final Image var1, final float var2, final float var3, final ShapeFill var4) {
      if (validFill(var0)) {
         Texture var5 = TextureImpl.getLastBind();
         var1.getTexture().bind();
         final float[] var6 = var0.getCenter();
         fill(var0, new ShapeRenderer.PointCallback() {
            public float[] preRenderPoint(Shape var1x, float var2x, float var3x) {
               var4.colorAt(var1x, var2x - var6[0], var3x - var6[1]).bind();
               Vector2f var4x = var4.getOffsetAt(var1x, var2x, var3x);
               var2x += var4x.x;
               var3x += var4x.y;
               float var5 = var2x * var2;
               float var6x = var3x * var3;
               var5 = var1.getTextureOffsetX() + var1.getTextureWidth() * var5;
               var6x = var1.getTextureOffsetY() + var1.getTextureHeight() * var6x;
               ShapeRenderer.GL.glTexCoord2f(var5, var6x);
               return new float[]{var4x.x + var2x, var4x.y + var3x};
            }
         });
         if (var5 == null) {
            TextureImpl.bindNone();
         } else {
            var5.bind();
         }

      }
   }

   public static final void draw(Shape var0, ShapeFill var1) {
      float[] var2 = var0.getPoints();
      Texture var3 = TextureImpl.getLastBind();
      TextureImpl.bindNone();
      float[] var4 = var0.getCenter();
      GL.glBegin(3);

      for(int var5 = 0; var5 < var2.length; var5 += 2) {
         var1.colorAt(var0, var2[var5], var2[var5 + 1]).bind();
         Vector2f var6 = var1.getOffsetAt(var0, var2[var5], var2[var5 + 1]);
         GL.glVertex2f(var2[var5] + var6.x, var2[var5 + 1] + var6.y);
      }

      if (var0.closed()) {
         var1.colorAt(var0, var2[0], var2[1]).bind();
         Vector2f var7 = var1.getOffsetAt(var0, var2[0], var2[1]);
         GL.glVertex2f(var2[0] + var7.x, var2[1] + var7.y);
      }

      GL.glEnd();
      if (var3 == null) {
         TextureImpl.bindNone();
      } else {
         var3.bind();
      }

   }

   public static boolean validFill(Shape var0) {
      if (var0.getTriangles() == null) {
         return false;
      } else {
         return var0.getTriangles().getTriangleCount() != 0;
      }
   }

   public static final void texture(Shape var0, final Image var1, final float var2, final float var3) {
      if (validFill(var0)) {
         Texture var4 = TextureImpl.getLastBind();
         var1.getTexture().bind();
         fill(var0, new ShapeRenderer.PointCallback() {
            public float[] preRenderPoint(Shape var1x, float var2x, float var3x) {
               float var4 = var2x * var2;
               float var5 = var3x * var3;
               var4 = var1.getTextureOffsetX() + var1.getTextureWidth() * var4;
               var5 = var1.getTextureOffsetY() + var1.getTextureHeight() * var5;
               ShapeRenderer.GL.glTexCoord2f(var4, var5);
               return null;
            }
         });
         float[] var5 = var0.getPoints();
         if (var4 == null) {
            TextureImpl.bindNone();
         } else {
            var4.bind();
         }

      }
   }

   public static final void draw(Shape var0) {
      Texture var1 = TextureImpl.getLastBind();
      TextureImpl.bindNone();
      float[] var2 = var0.getPoints();
      LSR.start();

      for(int var3 = 0; var3 < var2.length; var3 += 2) {
         LSR.vertex(var2[var3], var2[var3 + 1]);
      }

      if (var0.closed()) {
         LSR.vertex(var2[0], var2[1]);
      }

      LSR.end();
      if (var1 == null) {
         TextureImpl.bindNone();
      } else {
         var1.bind();
      }

   }

   public static final void texture(Shape var0, Image var1) {
      texture(var0, var1, 0.01F, 0.01F);
   }

   public static final void fill(Shape var0) {
      if (validFill(var0)) {
         Texture var1 = TextureImpl.getLastBind();
         TextureImpl.bindNone();
         fill(var0, new ShapeRenderer.PointCallback() {
            public float[] preRenderPoint(Shape var1, float var2, float var3) {
               return null;
            }
         });
         if (var1 == null) {
            TextureImpl.bindNone();
         } else {
            var1.bind();
         }

      }
   }

   public static final void textureFit(Shape var0, final Image var1, final float var2, final float var3) {
      if (validFill(var0)) {
         float[] var4 = var0.getPoints();
         Texture var5 = TextureImpl.getLastBind();
         var1.getTexture().bind();
         float var6 = var0.getX();
         float var7 = var0.getY();
         float var8 = var0.getMaxX() - var6;
         float var9 = var0.getMaxY() - var7;
         fill(var0, new ShapeRenderer.PointCallback() {
            public float[] preRenderPoint(Shape var1x, float var2x, float var3x) {
               var2x -= var1x.getMinX();
               var3x -= var1x.getMinY();
               var2x /= var1x.getMaxX() - var1x.getMinX();
               var3x /= var1x.getMaxY() - var1x.getMinY();
               float var4 = var2x * var2;
               float var5 = var3x * var3;
               var4 = var1.getTextureOffsetX() + var1.getTextureWidth() * var4;
               var5 = var1.getTextureOffsetY() + var1.getTextureHeight() * var5;
               ShapeRenderer.GL.glTexCoord2f(var4, var5);
               return null;
            }
         });
         if (var5 == null) {
            TextureImpl.bindNone();
         } else {
            var5.bind();
         }

      }
   }

   private interface PointCallback {
      float[] preRenderPoint(Shape var1, float var2, float var3);
   }
}
