package org.newdawn.slick.opengl.pbuffer;

import java.util.HashMap;
import org.lwjgl.opengl.GLContext;
import org.lwjgl.opengl.Pbuffer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.util.Log;

public class GraphicsFactory {
   // $FF: synthetic field
   private static boolean pbuffer = true;
   // $FF: synthetic field
   private static boolean fbo = true;
   // $FF: synthetic field
   private static boolean pbufferRT = true;
   // $FF: synthetic field
   private static HashMap graphics = new HashMap();
   // $FF: synthetic field
   private static boolean init = false;

   public static boolean usingPBuffer() {
      return !fbo && pbuffer;
   }

   public static Graphics getGraphicsForImage(Image var0) throws SlickException {
      Graphics var1 = (Graphics)graphics.get(var0.getTexture());
      if (var1 == null) {
         var1 = createGraphics(var0);
         graphics.put(var0.getTexture(), var1);
      }

      return var1;
   }

   public static void setUseFBO(boolean var0) {
      fbo = var0;
   }

   public static boolean usingFBO() {
      return fbo;
   }

   private static void init() throws SlickException {
      init = true;
      if (fbo) {
         fbo = GLContext.getCapabilities().GL_EXT_framebuffer_object;
      }

      pbuffer = (Pbuffer.getCapabilities() & 1) != 0;
      pbufferRT = (Pbuffer.getCapabilities() & 2) != 0;
      if (!fbo && !pbuffer && !pbufferRT) {
         throw new SlickException("Your OpenGL card does not support offscreen buffers and hence can't handle the dynamic images required for this application.");
      } else {
         Log.info(String.valueOf((new StringBuilder()).append("Offscreen Buffers FBO=").append(fbo).append(" PBUFFER=").append(pbuffer).append(" PBUFFERRT=").append(pbufferRT)));
      }
   }

   private static Graphics createGraphics(Image var0) throws SlickException {
      init();
      if (fbo) {
         try {
            return new FBOGraphics(var0);
         } catch (Exception var2) {
            fbo = false;
            Log.warn("FBO failed in use, falling back to PBuffer");
         }
      }

      if (pbuffer) {
         return (Graphics)(pbufferRT ? new PBufferGraphics(var0) : new PBufferUniqueGraphics(var0));
      } else {
         throw new SlickException("Failed to create offscreen buffer even though the card reports it's possible");
      }
   }

   public static void releaseGraphicsForImage(Image var0) throws SlickException {
      Graphics var1 = (Graphics)graphics.remove(var0.getTexture());
      if (var1 != null) {
         var1.destroy();
      }

   }
}
