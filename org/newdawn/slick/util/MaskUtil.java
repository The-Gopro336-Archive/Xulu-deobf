package org.newdawn.slick.util;

import org.newdawn.slick.opengl.renderer.Renderer;
import org.newdawn.slick.opengl.renderer.SGL;

public class MaskUtil {
   // $FF: synthetic field
   protected static SGL GL = Renderer.get();

   public static void drawOnMask() {
      GL.glDepthFunc(514);
   }

   public static void resetMask() {
      GL.glDepthMask(true);
      GL.glClearDepth(0.0F);
      GL.glClear(256);
      GL.glDepthMask(false);
      GL.glDisable(2929);
   }

   public static void defineMask() {
      GL.glDepthMask(true);
      GL.glClearDepth(1.0F);
      GL.glClear(256);
      GL.glDepthFunc(519);
      GL.glEnable(2929);
      GL.glDepthMask(true);
      GL.glColorMask(false, false, false, false);
   }

   public static void drawOffMask() {
      GL.glDepthFunc(517);
   }

   public static void finishDefineMask() {
      GL.glDepthMask(false);
      GL.glColorMask(true, true, true, true);
   }
}
