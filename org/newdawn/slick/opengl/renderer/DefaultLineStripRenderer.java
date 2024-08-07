package org.newdawn.slick.opengl.renderer;

public class DefaultLineStripRenderer implements LineStripRenderer {
   // $FF: synthetic field
   private SGL GL = Renderer.get();

   public boolean applyGLLineFixes() {
      return true;
   }

   public void start() {
      this.GL.glBegin(3);
   }

   public void setLineCaps(boolean var1) {
   }

   public void color(float var1, float var2, float var3, float var4) {
      this.GL.glColor4f(var1, var2, var3, var4);
   }

   public void vertex(float var1, float var2) {
      this.GL.glVertex2f(var1, var2);
   }

   public void setAntiAlias(boolean var1) {
      if (var1) {
         this.GL.glEnable(2848);
      } else {
         this.GL.glDisable(2848);
      }

   }

   public void setWidth(float var1) {
      this.GL.glLineWidth(var1);
   }

   public void end() {
      this.GL.glEnd();
   }
}
