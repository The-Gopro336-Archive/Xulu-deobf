package org.newdawn.slick;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.security.AccessController;
import java.security.PrivilegedAction;
import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Cursor;
import org.lwjgl.input.Mouse;
import org.lwjgl.openal.AL;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.PixelFormat;
import org.newdawn.slick.openal.SoundStore;
import org.newdawn.slick.opengl.CursorLoader;
import org.newdawn.slick.opengl.ImageData;
import org.newdawn.slick.opengl.ImageIOImageData;
import org.newdawn.slick.opengl.InternalTextureLoader;
import org.newdawn.slick.opengl.LoadableImageData;
import org.newdawn.slick.opengl.TGAImageData;
import org.newdawn.slick.util.Log;
import org.newdawn.slick.util.ResourceLoader;

public class AppGameContainer extends GameContainer {
   // $FF: synthetic field
   protected boolean updateOnlyOnVisible;
   // $FF: synthetic field
   protected boolean alphaSupport;
   // $FF: synthetic field
   protected DisplayMode targetDisplayMode;
   // $FF: synthetic field
   protected DisplayMode originalDisplayMode;

   public void setIcons(String[] var1) throws SlickException {
      ByteBuffer[] var2 = new ByteBuffer[var1.length];

      for(int var3 = 0; var3 < var1.length; ++var3) {
         boolean var5 = true;
         Object var4;
         if (var1[var3].endsWith(".tga")) {
            var4 = new TGAImageData();
         } else {
            var5 = false;
            var4 = new ImageIOImageData();
         }

         try {
            var2[var3] = ((LoadableImageData)var4).loadImage(ResourceLoader.getResourceAsStream(var1[var3]), var5, false, (int[])null);
         } catch (Exception var8) {
            Log.error((Throwable)var8);
            throw new SlickException("Failed to set the icon");
         }
      }

      Display.setIcon(var2);
   }

   public void setMouseCursor(String var1, int var2, int var3) throws SlickException {
      try {
         Cursor var4 = CursorLoader.get().getCursor(var1, var2, var3);
         Mouse.setNativeCursor(var4);
      } catch (Throwable var6) {
         Log.error("Failed to load and apply cursor.", var6);
         throw new SlickException("Failed to set mouse cursor", var6);
      }
   }

   static {
      AccessController.doPrivileged(new PrivilegedAction() {
         public Object run() {
            try {
               Display.getDisplayMode();
            } catch (Exception var2) {
               Log.error((Throwable)var2);
            }

            return null;
         }
      });
   }

   public void setTitle(String var1) {
      Display.setTitle(var1);
   }

   public AppGameContainer(Game var1) throws SlickException {
      this(var1, 640, 480, false);
   }

   public boolean isUpdatingOnlyWhenVisible() {
      return this.updateOnlyOnVisible;
   }

   public boolean hasFocus() {
      return Display.isActive();
   }

   public boolean supportsAlphaInBackBuffer() {
      return this.alphaSupport;
   }

   public AppGameContainer(Game var1, int var2, int var3, boolean var4) throws SlickException {
      super(var1);
      this.updateOnlyOnVisible = true;
      this.alphaSupport = false;
      this.originalDisplayMode = Display.getDisplayMode();
      this.setDisplayMode(var2, var3, var4);
   }

   public void setMouseCursor(Cursor var1, int var2, int var3) throws SlickException {
      try {
         Mouse.setNativeCursor(var1);
      } catch (Throwable var5) {
         Log.error("Failed to load and apply cursor.", var5);
         throw new SlickException("Failed to set mouse cursor", var5);
      }
   }

   public void setUpdateOnlyWhenVisible(boolean var1) {
      this.updateOnlyOnVisible = var1;
   }

   private void tryCreateDisplay(PixelFormat var1) throws LWJGLException {
      if (SHARED_DRAWABLE == null) {
         Display.create(var1);
      } else {
         Display.create(var1, SHARED_DRAWABLE);
      }

   }

   public int getScreenHeight() {
      return this.originalDisplayMode.getHeight();
   }

   public void setMouseGrabbed(boolean var1) {
      Mouse.setGrabbed(var1);
   }

   public void reinit() throws SlickException {
      InternalTextureLoader.get().clear();
      SoundStore.get().clear();
      this.initSystem();
      this.enterOrtho();

      try {
         this.game.init(this);
      } catch (SlickException var2) {
         Log.error((Throwable)var2);
         this.running = false;
      }

   }

   public void setFullscreen(boolean var1) throws SlickException {
      if (this.isFullscreen() != var1) {
         if (!var1) {
            try {
               Display.setFullscreen(var1);
            } catch (LWJGLException var3) {
               throw new SlickException(String.valueOf((new StringBuilder()).append("Unable to set fullscreen=").append(var1)), var3);
            }
         } else {
            this.setDisplayMode(this.width, this.height, var1);
         }

         this.getDelta();
      }
   }

   public void setDefaultMouseCursor() {
      try {
         Mouse.setNativeCursor((Cursor)null);
      } catch (LWJGLException var2) {
         Log.error("Failed to reset mouse cursor", var2);
      }

   }

   public void setMouseCursor(Image var1, int var2, int var3) throws SlickException {
      try {
         Image var4 = new Image(this.get2Fold(var1.getWidth()), this.get2Fold(var1.getHeight()));
         Graphics var5 = var4.getGraphics();
         ByteBuffer var6 = BufferUtils.createByteBuffer(var4.getWidth() * var4.getHeight() * 4);
         var5.drawImage(var1.getFlippedCopy(false, true), 0.0F, 0.0F);
         var5.flush();
         var5.getArea(0, 0, var4.getWidth(), var4.getHeight(), var6);
         Cursor var7 = CursorLoader.get().getCursor(var6, var2, var3, var4.getWidth(), var1.getHeight());
         Mouse.setNativeCursor(var7);
      } catch (Throwable var8) {
         Log.error("Failed to load and apply cursor.", var8);
         throw new SlickException("Failed to set mouse cursor", var8);
      }
   }

   public void start() throws SlickException {
      try {
         this.setup();
         this.getDelta();

         while(this.running()) {
            this.gameLoop();
         }
      } finally {
         this.destroy();
      }

      if (this.forceExit) {
         System.exit(0);
      }

   }

   public void setMouseCursor(ImageData var1, int var2, int var3) throws SlickException {
      try {
         Cursor var4 = CursorLoader.get().getCursor(var1, var2, var3);
         Mouse.setNativeCursor(var4);
      } catch (Throwable var5) {
         Log.error("Failed to load and apply cursor.", var5);
         throw new SlickException("Failed to set mouse cursor", var5);
      }
   }

   public void setDisplayMode(int var1, int var2, boolean var3) throws SlickException {
      if (this.width != var1 || this.height != var2 || this.isFullscreen() != var3) {
         try {
            this.targetDisplayMode = null;
            if (!var3) {
               this.targetDisplayMode = new DisplayMode(var1, var2);
            } else {
               DisplayMode[] var4 = Display.getAvailableDisplayModes();
               int var5 = 0;

               for(int var6 = 0; var6 < var4.length; ++var6) {
                  DisplayMode var7 = var4[var6];
                  if (var7.getWidth() == var1 && var7.getHeight() == var2) {
                     if ((this.targetDisplayMode == null || var7.getFrequency() >= var5) && (this.targetDisplayMode == null || var7.getBitsPerPixel() > this.targetDisplayMode.getBitsPerPixel())) {
                        this.targetDisplayMode = var7;
                        var5 = this.targetDisplayMode.getFrequency();
                     }

                     if (var7.getBitsPerPixel() == this.originalDisplayMode.getBitsPerPixel() && var7.getFrequency() == this.originalDisplayMode.getFrequency()) {
                        this.targetDisplayMode = var7;
                        break;
                     }
                  }
               }
            }

            if (this.targetDisplayMode == null) {
               throw new SlickException(String.valueOf((new StringBuilder()).append("Failed to find value mode: ").append(var1).append("x").append(var2).append(" fs=").append(var3)));
            }

            this.width = var1;
            this.height = var2;
            Display.setDisplayMode(this.targetDisplayMode);
            Display.setFullscreen(var3);
            if (Display.isCreated()) {
               this.initGL();
               this.enterOrtho();
            }

            if (this.targetDisplayMode.getBitsPerPixel() == 16) {
               InternalTextureLoader.get().set16BitMode();
            }
         } catch (LWJGLException var8) {
            throw new SlickException(String.valueOf((new StringBuilder()).append("Unable to setup mode ").append(var1).append("x").append(var2).append(" fullscreen=").append(var3)), var8);
         }

         this.getDelta();
      }
   }

   public void setIcon(String var1) throws SlickException {
      this.setIcons(new String[]{var1});
   }

   public void destroy() {
      Display.destroy();
      AL.destroy();
   }

   protected void setup() throws SlickException {
      if (this.targetDisplayMode == null) {
         this.setDisplayMode(640, 480, false);
      }

      Display.setTitle(this.game.getTitle());
      Log.info(String.valueOf((new StringBuilder()).append("LWJGL Version: ").append(Sys.getVersion())));
      Log.info(String.valueOf((new StringBuilder()).append("OriginalDisplayMode: ").append(this.originalDisplayMode)));
      Log.info(String.valueOf((new StringBuilder()).append("TargetDisplayMode: ").append(this.targetDisplayMode)));
      AccessController.doPrivileged(new PrivilegedAction() {
         public Object run() {
            try {
               PixelFormat var1 = new PixelFormat(8, 8, GameContainer.stencil ? 8 : 0, AppGameContainer.this.samples);
               AppGameContainer.this.tryCreateDisplay(var1);
               AppGameContainer.this.supportsMultiSample = true;
            } catch (Exception var7) {
               Display.destroy();

               try {
                  PixelFormat var2 = new PixelFormat(8, 8, GameContainer.stencil ? 8 : 0);
                  AppGameContainer.this.tryCreateDisplay(var2);
                  AppGameContainer.this.alphaSupport = false;
               } catch (Exception var6) {
                  Display.destroy();

                  try {
                     AppGameContainer.this.tryCreateDisplay(new PixelFormat());
                  } catch (Exception var5) {
                     Log.error((Throwable)var5);
                  }
               }
            }

            return null;
         }
      });
      if (!Display.isCreated()) {
         throw new SlickException("Failed to initialise the LWJGL display");
      } else {
         this.initSystem();
         this.enterOrtho();

         try {
            this.getInput().initControllers();
         } catch (SlickException var4) {
            Log.info("Controllers not available");
         } catch (Throwable var5) {
            Log.info("Controllers not available");
         }

         try {
            this.game.init(this);
         } catch (SlickException var3) {
            Log.error((Throwable)var3);
            this.running = false;
         }

      }
   }

   public boolean isFullscreen() {
      return Display.isFullscreen();
   }

   public boolean isMouseGrabbed() {
      return Mouse.isGrabbed();
   }

   private int get2Fold(int var1) {
      int var2;
      for(var2 = 2; var2 < var1; var2 *= 2) {
      }

      return var2;
   }

   public int getScreenWidth() {
      return this.originalDisplayMode.getWidth();
   }

   protected void gameLoop() throws SlickException {
      int var1 = this.getDelta();
      if (!Display.isVisible() && this.updateOnlyOnVisible) {
         try {
            Thread.sleep(100L);
         } catch (Exception var4) {
         }
      } else {
         try {
            this.updateAndRender(var1);
         } catch (SlickException var3) {
            Log.error((Throwable)var3);
            this.running = false;
            return;
         }
      }

      this.updateFPS();
      Display.update();
      if (Display.isCloseRequested() && this.game.closeRequested()) {
         this.running = false;
      }

   }

   private class NullOutputStream extends OutputStream {
      public void write(int var1) throws IOException {
      }
   }
}
