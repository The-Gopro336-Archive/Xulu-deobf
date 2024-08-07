package org.newdawn.slick.tests;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.openal.AudioLoader;
import org.newdawn.slick.openal.SoundStore;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.Log;

public class TestUtils {
   // $FF: synthetic field
   private Audio oggStream;
   // $FF: synthetic field
   private Audio aifEffect;
   // $FF: synthetic field
   private Texture texture;
   // $FF: synthetic field
   private Audio modStream;
   // $FF: synthetic field
   private Audio oggEffect;
   // $FF: synthetic field
   private Audio wavEffect;
   // $FF: synthetic field
   private Font font;

   public void init() {
      Log.setVerbose(false);
      java.awt.Font var1 = new java.awt.Font("Times New Roman", 1, 16);
      this.font = new TrueTypeFont(var1, false);

      try {
         this.texture = TextureLoader.getTexture("PNG", new FileInputStream("testdata/rocks.png"));
         System.out.println(String.valueOf((new StringBuilder()).append("Texture loaded: ").append(this.texture)));
         System.out.println(String.valueOf((new StringBuilder()).append(">> Image width: ").append(this.texture.getImageWidth())));
         System.out.println(String.valueOf((new StringBuilder()).append(">> Image height: ").append(this.texture.getImageWidth())));
         System.out.println(String.valueOf((new StringBuilder()).append(">> Texture width: ").append(this.texture.getTextureWidth())));
         System.out.println(String.valueOf((new StringBuilder()).append(">> Texture height: ").append(this.texture.getTextureHeight())));
         System.out.println(String.valueOf((new StringBuilder()).append(">> Texture ID: ").append(this.texture.getTextureID())));
      } catch (IOException var5) {
         var5.printStackTrace();
      }

      try {
         this.oggEffect = AudioLoader.getAudio("OGG", new FileInputStream("testdata/restart.ogg"));
         this.oggStream = AudioLoader.getStreamingAudio("OGG", (new File("testdata/bongos.ogg")).toURL());
         this.modStream = AudioLoader.getStreamingAudio("MOD", (new File("testdata/SMB-X.XM")).toURL());
         this.modStream.playAsMusic(1.0F, 1.0F, true);
         this.aifEffect = AudioLoader.getAudio("AIF", new FileInputStream("testdata/burp.aif"));
         this.wavEffect = AudioLoader.getAudio("WAV", new FileInputStream("testdata/cbrown01.wav"));
      } catch (IOException var4) {
         var4.printStackTrace();
      }

   }

   public void render() {
      Color.white.bind();
      this.texture.bind();
      GL11.glBegin(7);
      GL11.glTexCoord2f(0.0F, 0.0F);
      GL11.glVertex2f(100.0F, 100.0F);
      GL11.glTexCoord2f(1.0F, 0.0F);
      GL11.glVertex2f((float)(100 + this.texture.getTextureWidth()), 100.0F);
      GL11.glTexCoord2f(1.0F, 1.0F);
      GL11.glVertex2f((float)(100 + this.texture.getTextureWidth()), (float)(100 + this.texture.getTextureHeight()));
      GL11.glTexCoord2f(0.0F, 1.0F);
      GL11.glVertex2f(100.0F, (float)(100 + this.texture.getTextureHeight()));
      GL11.glEnd();
      this.font.drawString(150.0F, 300.0F, "HELLO LWJGL WORLD", Color.yellow);
   }

   public static void main(String[] var0) {
      TestUtils var1 = new TestUtils();
      var1.start();
   }

   public void update() {
      while(Keyboard.next()) {
         if (Keyboard.getEventKeyState()) {
            if (Keyboard.getEventKey() == 16) {
               this.oggEffect.playAsSoundEffect(1.0F, 1.0F, false);
            }

            if (Keyboard.getEventKey() == 17) {
               this.oggStream.playAsMusic(1.0F, 1.0F, true);
            }

            if (Keyboard.getEventKey() == 18) {
               this.modStream.playAsMusic(1.0F, 1.0F, true);
            }

            if (Keyboard.getEventKey() == 19) {
               this.aifEffect.playAsSoundEffect(1.0F, 1.0F, false);
            }

            if (Keyboard.getEventKey() == 20) {
               this.wavEffect.playAsSoundEffect(1.0F, 1.0F, false);
            }
         }
      }

      SoundStore.get().poll(0);
   }

   public void start() {
      this.initGL(800, 600);
      this.init();

      while(true) {
         do {
            this.update();
            GL11.glClear(16384);
            this.render();
            Display.update();
            Display.sync(100);
         } while(!Display.isCloseRequested());

         System.exit(0);
      }
   }

   private void initGL(int var1, int var2) {
      try {
         Display.setDisplayMode(new DisplayMode(var1, var2));
         Display.create();
         Display.setVSyncEnabled(true);
      } catch (LWJGLException var4) {
         var4.printStackTrace();
         System.exit(0);
      }

      GL11.glEnable(3553);
      GL11.glShadeModel(7425);
      GL11.glDisable(2929);
      GL11.glDisable(2896);
      GL11.glClearColor(0.0F, 0.0F, 0.0F, 0.0F);
      GL11.glClearDepth(1.0D);
      GL11.glEnable(3042);
      GL11.glBlendFunc(770, 771);
      GL11.glViewport(0, 0, var1, var2);
      GL11.glMatrixMode(5888);
      GL11.glMatrixMode(5889);
      GL11.glLoadIdentity();
      GL11.glOrtho(0.0D, (double)var1, (double)var2, 0.0D, 1.0D, -1.0D);
      GL11.glMatrixMode(5888);
   }
}
