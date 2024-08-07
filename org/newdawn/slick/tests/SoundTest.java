package org.newdawn.slick.tests;

import java.io.IOException;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.openal.AudioLoader;
import org.newdawn.slick.openal.SoundStore;
import org.newdawn.slick.util.ResourceLoader;

public class SoundTest extends BasicGame {
   // $FF: synthetic field
   private Sound charlie;
   // $FF: synthetic field
   private GameContainer myContainer;
   // $FF: synthetic field
   private Sound burp;
   // $FF: synthetic field
   private int[] engines = new int[3];
   // $FF: synthetic field
   private int volume = 10;
   // $FF: synthetic field
   private Sound sound;
   // $FF: synthetic field
   private Audio engine;
   // $FF: synthetic field
   private Music music;
   // $FF: synthetic field
   private Music musica;
   // $FF: synthetic field
   private Music musicb;

   public void keyPressed(int var1, char var2) {
      if (var1 == 1) {
         System.exit(0);
      }

      if (var1 == 57) {
         this.sound.play();
      }

      if (var1 == 48) {
         this.burp.play();
      }

      if (var1 == 30) {
         this.sound.playAt(-1.0F, 0.0F, 0.0F);
      }

      if (var1 == 38) {
         this.sound.playAt(1.0F, 0.0F, 0.0F);
      }

      if (var1 == 28) {
         this.charlie.play(1.0F, 1.0F);
      }

      if (var1 == 25) {
         if (this.music.playing()) {
            this.music.pause();
         } else {
            this.music.resume();
         }
      }

      if (var1 == 46) {
         this.music.stop();
         if (this.music == this.musica) {
            this.music = this.musicb;
         } else {
            this.music = this.musica;
         }

         this.music.loop();
      }

      int var3;
      for(var3 = 0; var3 < 3; ++var3) {
         if (var1 == 2 + var3) {
            if (this.engines[var3] != 0) {
               System.out.println(String.valueOf((new StringBuilder()).append("Stop ").append(var3)));
               SoundStore.get().stopSoundEffect(this.engines[var3]);
               this.engines[var3] = 0;
            } else {
               System.out.println(String.valueOf((new StringBuilder()).append("Start ").append(var3)));
               this.engines[var3] = this.engine.playAsSoundEffect(1.0F, 1.0F, true);
            }
         }
      }

      if (var2 == '+') {
         ++this.volume;
         this.setVolume();
      }

      if (var2 == '-') {
         --this.volume;
         this.setVolume();
      }

      if (var1 == 21) {
         var3 = (int)(this.music.getVolume() * 10.0F);
         --var3;
         if (var3 < 0) {
            var3 = 0;
         }

         this.music.setVolume((float)var3 / 10.0F);
      }

      if (var1 == 45) {
         var3 = (int)(this.music.getVolume() * 10.0F);
         ++var3;
         if (var3 > 10) {
            var3 = 10;
         }

         this.music.setVolume((float)var3 / 10.0F);
      }

      if (var1 == 49) {
         var3 = (int)(this.myContainer.getSoundVolume() * 10.0F);
         --var3;
         if (var3 < 0) {
            var3 = 0;
         }

         this.myContainer.setSoundVolume((float)var3 / 10.0F);
      }

      if (var1 == 50) {
         var3 = (int)(this.myContainer.getSoundVolume() * 10.0F);
         ++var3;
         if (var3 > 10) {
            var3 = 10;
         }

         this.myContainer.setSoundVolume((float)var3 / 10.0F);
      }

   }

   public static void main(String[] var0) {
      try {
         AppGameContainer var1 = new AppGameContainer(new SoundTest());
         var1.setDisplayMode(800, 600, false);
         var1.start();
      } catch (SlickException var2) {
         var2.printStackTrace();
      }

   }

   public void update(GameContainer var1, int var2) {
   }

   public SoundTest() {
      super("Sound And Music Test");
   }

   private void setVolume() {
      if (this.volume > 10) {
         this.volume = 10;
      } else if (this.volume < 0) {
         this.volume = 0;
      }

      this.myContainer.setMusicVolume((float)this.volume / 10.0F);
   }

   public void render(GameContainer var1, Graphics var2) {
      var2.setColor(Color.white);
      var2.drawString("The OGG loop is now streaming from the file, woot.", 100.0F, 60.0F);
      var2.drawString("Press space for sound effect (OGG)", 100.0F, 100.0F);
      var2.drawString("Press P to pause/resume music (XM)", 100.0F, 130.0F);
      var2.drawString("Press E to pause/resume engine sound (WAV)", 100.0F, 190.0F);
      var2.drawString("Press enter for charlie (WAV)", 100.0F, 160.0F);
      var2.drawString("Press C to change music", 100.0F, 210.0F);
      var2.drawString("Press B to burp (AIF)", 100.0F, 240.0F);
      var2.drawString("Press + or - to change global volume of music", 100.0F, 270.0F);
      var2.drawString("Press Y or X to change individual volume of music", 100.0F, 300.0F);
      var2.drawString("Press N or M to change global volume of sound fx", 100.0F, 330.0F);
      var2.setColor(Color.blue);
      var2.drawString(String.valueOf((new StringBuilder()).append("Global Sound Volume Level: ").append(var1.getSoundVolume())), 150.0F, 390.0F);
      var2.drawString(String.valueOf((new StringBuilder()).append("Global Music Volume Level: ").append(var1.getMusicVolume())), 150.0F, 420.0F);
      var2.drawString(String.valueOf((new StringBuilder()).append("Current Music Volume Level: ").append(this.music.getVolume())), 150.0F, 450.0F);
   }

   public void init(GameContainer var1) throws SlickException {
      SoundStore.get().setMaxSources(32);
      this.myContainer = var1;
      this.sound = new Sound("testdata/restart.ogg");
      this.charlie = new Sound("testdata/cbrown01.wav");

      try {
         this.engine = AudioLoader.getAudio("WAV", ResourceLoader.getResourceAsStream("testdata/engine.wav"));
      } catch (IOException var3) {
         throw new SlickException("Failed to load engine", var3);
      }

      this.music = this.musica = new Music("testdata/SMB-X.XM");
      this.musicb = new Music("testdata/kirby.ogg", true);
      this.burp = new Sound("testdata/burp.aif");
      this.music.play();
   }
}
