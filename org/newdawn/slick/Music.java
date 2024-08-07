package org.newdawn.slick;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.openal.AudioImpl;
import org.newdawn.slick.openal.SoundStore;
import org.newdawn.slick.util.Log;

public class Music {
   // $FF: synthetic field
   private float requiredPosition;
   // $FF: synthetic field
   private int fadeDuration;
   // $FF: synthetic field
   private int fadeTime;
   // $FF: synthetic field
   private static Music currentMusic;
   // $FF: synthetic field
   private boolean stopAfterFade;
   // $FF: synthetic field
   private ArrayList listeners;
   // $FF: synthetic field
   private float fadeStartGain;
   // $FF: synthetic field
   private Audio sound;
   // $FF: synthetic field
   private boolean playing;
   // $FF: synthetic field
   private float fadeEndGain;
   // $FF: synthetic field
   private boolean positioning;
   // $FF: synthetic field
   private float volume;

   public void stop() {
      this.sound.stop();
   }

   public void fade(int var1, float var2, boolean var3) {
      this.stopAfterFade = var3;
      this.fadeStartGain = this.volume;
      this.fadeEndGain = var2;
      this.fadeDuration = var1;
      this.fadeTime = var1;
   }

   public void play() {
      this.play(1.0F, 1.0F);
   }

   public float getVolume() {
      return this.volume;
   }

   public void loop(float var1, float var2) {
      this.startMusic(var1, var2, true);
   }

   public Music(String var1) throws SlickException {
      this(var1, false);
   }

   public void setVolume(float var1) {
      if (var1 > 1.0F) {
         var1 = 1.0F;
      } else if (var1 < 0.0F) {
         var1 = 0.0F;
      }

      this.volume = var1;
      if (currentMusic == this) {
         SoundStore.get().setCurrentMusicVolume(var1);
      }

   }

   public float getPosition() {
      return this.sound.getPosition();
   }

   public Music(URL var1, boolean var2) throws SlickException {
      this.listeners = new ArrayList();
      this.volume = 1.0F;
      this.requiredPosition = -1.0F;
      SoundStore.get().init();
      String var3 = var1.getFile();

      try {
         if (var3.toLowerCase().endsWith(".ogg")) {
            if (var2) {
               this.sound = SoundStore.get().getOggStream(var1);
            } else {
               this.sound = SoundStore.get().getOgg(var1.openStream());
            }
         } else if (var3.toLowerCase().endsWith(".wav")) {
            this.sound = SoundStore.get().getWAV(var1.openStream());
         } else if (!var3.toLowerCase().endsWith(".xm") && !var3.toLowerCase().endsWith(".mod")) {
            if (!var3.toLowerCase().endsWith(".aif") && !var3.toLowerCase().endsWith(".aiff")) {
               throw new SlickException("Only .xm, .mod, .ogg, and .aif/f are currently supported.");
            }

            this.sound = SoundStore.get().getAIF(var1.openStream());
         } else {
            this.sound = SoundStore.get().getMOD(var1.openStream());
         }

      } catch (Exception var5) {
         Log.error((Throwable)var5);
         throw new SlickException(String.valueOf((new StringBuilder()).append("Failed to load sound: ").append(var1)));
      }
   }

   public boolean playing() {
      return currentMusic == this && this.playing;
   }

   public boolean setPosition(float var1) {
      if (this.playing) {
         this.requiredPosition = -1.0F;
         this.positioning = true;
         this.playing = false;
         boolean var2 = this.sound.setPosition(var1);
         this.playing = true;
         this.positioning = false;
         return var2;
      } else {
         this.requiredPosition = var1;
         return false;
      }
   }

   void update(int var1) {
      if (this.playing) {
         if (this.fadeTime > 0) {
            this.fadeTime -= var1;
            if (this.fadeTime < 0) {
               this.fadeTime = 0;
               if (this.stopAfterFade) {
                  this.stop();
                  return;
               }
            }

            float var2 = (this.fadeEndGain - this.fadeStartGain) * (1.0F - (float)this.fadeTime / (float)this.fadeDuration);
            this.setVolume(this.fadeStartGain + var2);
         }

      }
   }

   public void resume() {
      this.playing = true;
      AudioImpl.restartMusic();
   }

   public static void poll(int var0) {
      if (currentMusic != null) {
         SoundStore.get().poll(var0);
         if (!SoundStore.get().isMusicPlaying()) {
            if (!currentMusic.positioning) {
               Music var1 = currentMusic;
               currentMusic = null;
               var1.fireMusicEnded();
            }
         } else {
            currentMusic.update(var0);
         }
      }

   }

   private void fireMusicEnded() {
      this.playing = false;

      for(int var1 = 0; var1 < this.listeners.size(); ++var1) {
         ((MusicListener)this.listeners.get(var1)).musicEnded(this);
      }

   }

   public void play(float var1, float var2) {
      this.startMusic(var1, var2, false);
   }

   public void addListener(MusicListener var1) {
      this.listeners.add(var1);
   }

   public void removeListener(MusicListener var1) {
      this.listeners.remove(var1);
   }

   public Music(InputStream var1, String var2) throws SlickException {
      this.listeners = new ArrayList();
      this.volume = 1.0F;
      this.requiredPosition = -1.0F;
      SoundStore.get().init();

      try {
         if (var2.toLowerCase().endsWith(".ogg")) {
            this.sound = SoundStore.get().getOgg(var1);
         } else if (var2.toLowerCase().endsWith(".wav")) {
            this.sound = SoundStore.get().getWAV(var1);
         } else if (!var2.toLowerCase().endsWith(".xm") && !var2.toLowerCase().endsWith(".mod")) {
            if (!var2.toLowerCase().endsWith(".aif") && !var2.toLowerCase().endsWith(".aiff")) {
               throw new SlickException("Only .xm, .mod, .ogg, and .aif/f are currently supported.");
            }

            this.sound = SoundStore.get().getAIF(var1);
         } else {
            this.sound = SoundStore.get().getMOD(var1);
         }

      } catch (Exception var4) {
         Log.error((Throwable)var4);
         throw new SlickException(String.valueOf((new StringBuilder()).append("Failed to load music: ").append(var2)));
      }
   }

   private void startMusic(float var1, float var2, boolean var3) {
      if (currentMusic != null) {
         currentMusic.stop();
         currentMusic.fireMusicSwapped(this);
      }

      currentMusic = this;
      if (var2 < 0.0F) {
         var2 = 0.0F;
      }

      if (var2 > 1.0F) {
         var2 = 1.0F;
      }

      this.sound.playAsMusic(var1, var2, var3);
      this.playing = true;
      this.setVolume(var2);
      if (this.requiredPosition != -1.0F) {
         this.setPosition(this.requiredPosition);
      }

   }

   public Music(URL var1) throws SlickException {
      this(var1, false);
   }

   public void pause() {
      this.playing = false;
      AudioImpl.pauseMusic();
   }

   public Music(String var1, boolean var2) throws SlickException {
      this.listeners = new ArrayList();
      this.volume = 1.0F;
      this.requiredPosition = -1.0F;
      SoundStore.get().init();

      try {
         if (var1.toLowerCase().endsWith(".ogg")) {
            if (var2) {
               this.sound = SoundStore.get().getOggStream(var1);
            } else {
               this.sound = SoundStore.get().getOgg(var1);
            }
         } else if (var1.toLowerCase().endsWith(".wav")) {
            this.sound = SoundStore.get().getWAV(var1);
         } else if (!var1.toLowerCase().endsWith(".xm") && !var1.toLowerCase().endsWith(".mod")) {
            if (!var1.toLowerCase().endsWith(".aif") && !var1.toLowerCase().endsWith(".aiff")) {
               throw new SlickException("Only .xm, .mod, .ogg, and .aif/f are currently supported.");
            }

            this.sound = SoundStore.get().getAIF(var1);
         } else {
            this.sound = SoundStore.get().getMOD(var1);
         }

      } catch (Exception var4) {
         Log.error((Throwable)var4);
         throw new SlickException(String.valueOf((new StringBuilder()).append("Failed to load sound: ").append(var1)));
      }
   }

   private void fireMusicSwapped(Music var1) {
      this.playing = false;

      for(int var2 = 0; var2 < this.listeners.size(); ++var2) {
         ((MusicListener)this.listeners.get(var2)).musicSwapped(this, var1);
      }

   }

   public void loop() {
      this.loop(1.0F, 1.0F);
   }
}
