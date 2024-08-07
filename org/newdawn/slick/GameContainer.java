package org.newdawn.slick;

import java.io.IOException;
import java.util.Properties;
import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Cursor;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.Drawable;
import org.lwjgl.opengl.Pbuffer;
import org.lwjgl.opengl.PixelFormat;
import org.newdawn.slick.gui.GUIContext;
import org.newdawn.slick.openal.SoundStore;
import org.newdawn.slick.opengl.CursorLoader;
import org.newdawn.slick.opengl.ImageData;
import org.newdawn.slick.opengl.renderer.Renderer;
import org.newdawn.slick.opengl.renderer.SGL;
import org.newdawn.slick.util.Log;
import org.newdawn.slick.util.ResourceLoader;

public abstract class GameContainer implements GUIContext {
   // $FF: synthetic field
   protected boolean clearEachFrame = true;
   // $FF: synthetic field
   protected boolean vsync;
   // $FF: synthetic field
   protected int height;
   // $FF: synthetic field
   protected static SGL GL = Renderer.get();
   // $FF: synthetic field
   private Font defaultFont;
   // $FF: synthetic field
   protected static boolean stencil;
   // $FF: synthetic field
   protected boolean alwaysRender;
   // $FF: synthetic field
   protected boolean paused;
   // $FF: synthetic field
   protected int recordedFPS;
   // $FF: synthetic field
   protected Input input;
   // $FF: synthetic field
   protected int samples;
   // $FF: synthetic field
   protected static Drawable SHARED_DRAWABLE;
   // $FF: synthetic field
   protected int fps;
   // $FF: synthetic field
   protected int targetFPS = -1;
   // $FF: synthetic field
   protected long minimumLogicInterval = 1L;
   // $FF: synthetic field
   protected boolean smoothDeltas;
   // $FF: synthetic field
   protected boolean forceExit = true;
   // $FF: synthetic field
   protected long storedDelta;
   // $FF: synthetic field
   protected long maximumLogicInterval = 0L;
   // $FF: synthetic field
   protected boolean supportsMultiSample;
   // $FF: synthetic field
   protected long lastFPS;
   // $FF: synthetic field
   protected long lastFrame;
   // $FF: synthetic field
   private Graphics graphics;
   // $FF: synthetic field
   protected Game lastGame;
   // $FF: synthetic field
   protected Game game;
   // $FF: synthetic field
   private boolean showFPS = true;
   // $FF: synthetic field
   protected int width;
   // $FF: synthetic field
   protected boolean running = true;

   protected void updateAndRender(int var1) throws SlickException {
      if (this.smoothDeltas && this.getFPS() != 0) {
         var1 = 1000 / this.getFPS();
      }

      this.input.poll(this.width, this.height);
      Music.poll(var1);
      if (!this.paused) {
         this.storedDelta += (long)var1;
         if (this.storedDelta >= this.minimumLogicInterval) {
            try {
               if (this.maximumLogicInterval != 0L) {
                  long var2 = this.storedDelta / this.maximumLogicInterval;

                  int var4;
                  for(var4 = 0; (long)var4 < var2; ++var4) {
                     this.game.update(this, (int)this.maximumLogicInterval);
                  }

                  var4 = (int)(this.storedDelta % this.maximumLogicInterval);
                  if ((long)var4 > this.minimumLogicInterval) {
                     this.game.update(this, (int)((long)var4 % this.maximumLogicInterval));
                     this.storedDelta = 0L;
                  } else {
                     this.storedDelta = (long)var4;
                  }
               } else {
                  this.game.update(this, (int)this.storedDelta);
                  this.storedDelta = 0L;
               }
            } catch (Throwable var6) {
               Log.error(var6);
               throw new SlickException("Game.update() failure - check the game code.");
            }
         }
      } else {
         this.game.update(this, 0);
      }

      if (this.hasFocus() || this.getAlwaysRender()) {
         if (this.clearEachFrame) {
            GL.glClear(16640);
         }

         GL.glLoadIdentity();
         this.graphics.resetTransform();
         this.graphics.resetFont();
         this.graphics.resetLineWidth();
         this.graphics.setAntiAlias(false);

         try {
            this.game.render(this, this.graphics);
         } catch (Throwable var5) {
            Log.error(var5);
            throw new SlickException("Game.render() failure - check the game code.");
         }

         this.graphics.resetTransform();
         if (this.showFPS) {
            this.defaultFont.drawString(10.0F, 10.0F, String.valueOf((new StringBuilder()).append("FPS: ").append(this.recordedFPS)));
         }

         GL.flush();
      }

      if (this.targetFPS != -1) {
         Display.sync(this.targetFPS);
      }

   }

   protected void initGL() {
      Log.info(String.valueOf((new StringBuilder()).append("Starting display ").append(this.width).append("x").append(this.height)));
      GL.initDisplay(this.width, this.height);
      if (this.input == null) {
         this.input = new Input(this.height);
      }

      this.input.init(this.height);
      if (this.game instanceof InputListener) {
         this.input.removeListener((InputListener)this.game);
         this.input.addListener((InputListener)this.game);
      }

      if (this.graphics != null) {
         this.graphics.setDimensions(this.getWidth(), this.getHeight());
      }

      this.lastGame = this.game;
   }

   public boolean getAlwaysRender() {
      return this.alwaysRender;
   }

   public Graphics getGraphics() {
      return this.graphics;
   }

   public boolean isVSyncRequested() {
      return this.vsync;
   }

   public abstract void setMouseCursor(String var1, int var2, int var3) throws SlickException;

   public int getHeight() {
      return this.height;
   }

   protected void enterOrtho() {
      this.enterOrtho(this.width, this.height);
   }

   public float getMusicVolume() {
      return SoundStore.get().getMusicVolume();
   }

   public void setMultiSample(int var1) {
      this.samples = var1;
   }

   public boolean isMusicOn() {
      return SoundStore.get().musicOn();
   }

   public void setVerbose(boolean var1) {
      Log.setVerbose(var1);
   }

   public void setForceExit(boolean var1) {
      this.forceExit = var1;
   }

   public int getWidth() {
      return this.width;
   }

   public abstract void setMouseCursor(Cursor var1, int var2, int var3) throws SlickException;

   public static void enableSharedContext() throws SlickException {
      try {
         SHARED_DRAWABLE = new Pbuffer(64, 64, new PixelFormat(8, 0, 0), (Drawable)null);
      } catch (LWJGLException var2) {
         throw new SlickException("Unable to create the pbuffer used for shard context, buffers not supported", var2);
      }
   }

   public abstract int getScreenWidth();

   public void setUpdateOnlyWhenVisible(boolean var1) {
   }

   public void setTargetFrameRate(int var1) {
      this.targetFPS = var1;
   }

   public boolean isShowingFPS() {
      return this.showFPS;
   }

   public void reinit() throws SlickException {
   }

   public Font getDefaultFont() {
      return this.defaultFont;
   }

   public abstract void setMouseCursor(ImageData var1, int var2, int var3) throws SlickException;

   public int getSamples() {
      return this.samples;
   }

   public boolean isPaused() {
      return this.paused;
   }

   public void setSmoothDeltas(boolean var1) {
      this.smoothDeltas = var1;
   }

   public static void enableStencil() {
      stencil = true;
   }

   public boolean supportsMultiSample() {
      return this.supportsMultiSample;
   }

   public boolean isFullscreen() {
      return false;
   }

   public boolean isUpdatingOnlyWhenVisible() {
      return true;
   }

   protected GameContainer(Game var1) {
      this.game = var1;
      this.lastFrame = this.getTime();
      getBuildVersion();
      Log.checkVerboseLogSetting();
   }

   public abstract boolean hasFocus();

   protected int getDelta() {
      long var1 = this.getTime();
      int var3 = (int)(var1 - this.lastFrame);
      this.lastFrame = var1;
      return var3;
   }

   public abstract int getScreenHeight();

   protected void initSystem() throws SlickException {
      this.initGL();
      this.setMusicVolume(1.0F);
      this.setSoundVolume(1.0F);
      this.graphics = new Graphics(this.width, this.height);
      this.defaultFont = this.graphics.getFont();
   }

   protected boolean running() {
      return this.running;
   }

   public void setAnimatedMouseCursor(String var1, int var2, int var3, int var4, int var5, int[] var6) throws SlickException {
      try {
         Cursor var7 = CursorLoader.get().getAnimatedCursor(var1, var2, var3, var4, var5, var6);
         this.setMouseCursor(var7, var2, var3);
      } catch (IOException var8) {
         throw new SlickException("Failed to set mouse cursor", var8);
      } catch (LWJGLException var9) {
         throw new SlickException("Failed to set mouse cursor", var9);
      }
   }

   public float getAspectRatio() {
      return (float)(this.getWidth() / this.getHeight());
   }

   public void setMusicVolume(float var1) {
      SoundStore.get().setMusicVolume(var1);
   }

   public abstract void setDefaultMouseCursor();

   public void setMaximumLogicUpdateInterval(int var1) {
      this.maximumLogicInterval = (long)var1;
   }

   public void setVSync(boolean var1) {
      this.vsync = var1;
      Display.setVSyncEnabled(var1);
   }

   public void sleep(int var1) {
      long var2 = this.getTime() + (long)var1;

      while(this.getTime() < var2) {
         try {
            Thread.sleep(1L);
         } catch (Exception var5) {
         }
      }

   }

   public void setSoundVolume(float var1) {
      SoundStore.get().setSoundVolume(var1);
   }

   public void exit() {
      this.running = false;
   }

   public boolean isSoundOn() {
      return SoundStore.get().soundsOn();
   }

   public float getSoundVolume() {
      return SoundStore.get().getSoundVolume();
   }

   public static Drawable getSharedContext() {
      return SHARED_DRAWABLE;
   }

   public void setDefaultFont(Font var1) {
      if (var1 != null) {
         this.defaultFont = var1;
      } else {
         Log.warn("Please provide a non null font");
      }

   }

   public abstract void setMouseCursor(Image var1, int var2, int var3) throws SlickException;

   public void setMinimumLogicUpdateInterval(int var1) {
      this.minimumLogicInterval = (long)var1;
   }

   public void pause() {
      this.setPaused(true);
   }

   public void setAlwaysRender(boolean var1) {
      this.alwaysRender = var1;
   }

   public int getFPS() {
      return this.recordedFPS;
   }

   public Input getInput() {
      return this.input;
   }

   protected void enterOrtho(int var1, int var2) {
      GL.enterOrtho(var1, var2);
   }

   public abstract void setIcons(String[] var1) throws SlickException;

   public abstract void setIcon(String var1) throws SlickException;

   protected void updateFPS() {
      if (this.getTime() - this.lastFPS > 1000L) {
         this.lastFPS = this.getTime();
         this.recordedFPS = this.fps;
         this.fps = 0;
      }

      ++this.fps;
   }

   public void setFullscreen(boolean var1) throws SlickException {
   }

   public void setShowFPS(boolean var1) {
      this.showFPS = var1;
   }

   public void resume() {
      this.setPaused(false);
   }

   public abstract boolean isMouseGrabbed();

   public abstract void setMouseGrabbed(boolean var1);

   public static int getBuildVersion() {
      try {
         Properties var0 = new Properties();
         var0.load(ResourceLoader.getResourceAsStream("version"));
         int var1 = Integer.parseInt(var0.getProperty("build"));
         Log.info(String.valueOf((new StringBuilder()).append("Slick Build #").append(var1)));
         return var1;
      } catch (Exception var2) {
         Log.error("Unable to determine Slick build number");
         return -1;
      }
   }

   public void setMusicOn(boolean var1) {
      SoundStore.get().setMusicOn(var1);
   }

   public long getTime() {
      return Sys.getTime() * 1000L / Sys.getTimerResolution();
   }

   public void setClearEachFrame(boolean var1) {
      this.clearEachFrame = var1;
   }

   public void setSoundOn(boolean var1) {
      SoundStore.get().setSoundsOn(var1);
   }

   public void setPaused(boolean var1) {
      this.paused = var1;
   }
}
