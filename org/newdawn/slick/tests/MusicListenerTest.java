package org.newdawn.slick.tests;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Music;
import org.newdawn.slick.MusicListener;
import org.newdawn.slick.SlickException;

public class MusicListenerTest extends BasicGame implements MusicListener {
   // $FF: synthetic field
   private Music music;
   // $FF: synthetic field
   private boolean musicSwapped = false;
   // $FF: synthetic field
   private Music stream;
   // $FF: synthetic field
   private boolean musicEnded = false;

   public MusicListenerTest() {
      super("Music Listener Test");
   }

   public void init(GameContainer var1) throws SlickException {
      this.music = new Music("testdata/restart.ogg", false);
      this.stream = new Music("testdata/restart.ogg", false);
      this.music.addListener(this);
      this.stream.addListener(this);
   }

   public void update(GameContainer var1, int var2) throws SlickException {
   }

   public void render(GameContainer var1, Graphics var2) throws SlickException {
      var2.drawString("Press M to play music", 100.0F, 100.0F);
      var2.drawString("Press S to stream music", 100.0F, 150.0F);
      if (this.musicEnded) {
         var2.drawString("Music Ended", 100.0F, 200.0F);
      }

      if (this.musicSwapped) {
         var2.drawString("Music Swapped", 100.0F, 250.0F);
      }

   }

   public void musicEnded(Music var1) {
      this.musicEnded = true;
   }

   public void musicSwapped(Music var1, Music var2) {
      this.musicSwapped = true;
   }

   public void keyPressed(int var1, char var2) {
      if (var1 == 50) {
         this.musicEnded = false;
         this.musicSwapped = false;
         this.music.play();
      }

      if (var1 == 31) {
         this.musicEnded = false;
         this.musicSwapped = false;
         this.stream.play();
      }

   }

   public static void main(String[] var0) {
      try {
         AppGameContainer var1 = new AppGameContainer(new MusicListenerTest());
         var1.setDisplayMode(800, 600, false);
         var1.start();
      } catch (SlickException var2) {
         var2.printStackTrace();
      }

   }
}
