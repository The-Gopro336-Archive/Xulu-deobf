package org.newdawn.slick.opengl.renderer;

public interface LineStripRenderer {
   void end();

   void setAntiAlias(boolean var1);

   void start();

   void vertex(float var1, float var2);

   void setWidth(float var1);

   void color(float var1, float var2, float var3, float var4);

   boolean applyGLLineFixes();

   void setLineCaps(boolean var1);
}
