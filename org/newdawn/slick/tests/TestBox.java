package org.newdawn.slick.tests;

import java.util.ArrayList;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.opengl.SlickCallable;
import org.newdawn.slick.util.Log;

public class TestBox extends BasicGame {
   // $FF: synthetic field
   private int index;
   // $FF: synthetic field
   private ArrayList games = new ArrayList();
   // $FF: synthetic field
   private AppGameContainer container;
   // $FF: synthetic field
   private BasicGame currentGame;

   private void startGame() {
      try {
         this.currentGame = (BasicGame)((Class)this.games.get(this.index)).newInstance();
         this.container.getGraphics().setBackground(Color.black);
         this.currentGame.init(this.container);
         this.currentGame.render(this.container, this.container.getGraphics());
      } catch (Exception var2) {
         Log.error((Throwable)var2);
      }

      this.container.setTitle(this.currentGame.getTitle());
   }

   public void render(GameContainer var1, Graphics var2) throws SlickException {
      SlickCallable.enterSafeBlock();
      this.currentGame.render(var1, var2);
      SlickCallable.leaveSafeBlock();
   }

   public void controllerUpPressed(int var1) {
      this.currentGame.controllerUpPressed(var1);
   }

   public void init(GameContainer var1) throws SlickException {
      if (this.games.size() == 0) {
         this.currentGame = new BasicGame("NULL") {
            public void update(GameContainer var1, int var2) throws SlickException {
            }

            public void render(GameContainer var1, Graphics var2) throws SlickException {
            }

            public void init(GameContainer var1) throws SlickException {
            }
         };
         this.currentGame.init(var1);
         this.index = -1;
      } else {
         this.index = 0;
         this.container = (AppGameContainer)var1;
         this.startGame();
      }

   }

   public void update(GameContainer var1, int var2) throws SlickException {
      this.currentGame.update(var1, var2);
   }

   public void controllerRightPressed(int var1) {
      this.currentGame.controllerRightPressed(var1);
   }

   public void keyPressed(int var1, char var2) {
      this.currentGame.keyPressed(var1, var2);
      if (var1 == 28) {
         this.nextGame();
      }

   }

   public static void main(String[] var0) {
      try {
         TestBox var1 = new TestBox();
         var1.addGame(AnimationTest.class);
         var1.addGame(AntiAliasTest.class);
         var1.addGame(BigImageTest.class);
         var1.addGame(ClipTest.class);
         var1.addGame(DuplicateEmitterTest.class);
         var1.addGame(FlashTest.class);
         var1.addGame(FontPerformanceTest.class);
         var1.addGame(FontTest.class);
         var1.addGame(GeomTest.class);
         var1.addGame(GradientTest.class);
         var1.addGame(GraphicsTest.class);
         var1.addGame(ImageBufferTest.class);
         var1.addGame(ImageReadTest.class);
         var1.addGame(ImageTest.class);
         var1.addGame(KeyRepeatTest.class);
         var1.addGame(MusicListenerTest.class);
         var1.addGame(PackedSheetTest.class);
         var1.addGame(PedigreeTest.class);
         var1.addGame(PureFontTest.class);
         var1.addGame(ShapeTest.class);
         var1.addGame(SoundTest.class);
         var1.addGame(SpriteSheetFontTest.class);
         var1.addGame(TransparentColorTest.class);
         AppGameContainer var2 = new AppGameContainer(var1);
         var2.setDisplayMode(800, 600, false);
         var2.start();
      } catch (SlickException var3) {
         var3.printStackTrace();
      }

   }

   public void controllerDownPressed(int var1) {
      this.currentGame.controllerDownPressed(var1);
   }

   public void mouseReleased(int var1, int var2, int var3) {
      this.currentGame.mouseReleased(var1, var2, var3);
   }

   public void controllerDownReleased(int var1) {
      this.currentGame.controllerDownReleased(var1);
   }

   public void mouseWheelMoved(int var1) {
      this.currentGame.mouseWheelMoved(var1);
   }

   public void controllerLeftReleased(int var1) {
      this.currentGame.controllerLeftReleased(var1);
   }

   public void controllerButtonReleased(int var1, int var2) {
      this.currentGame.controllerButtonReleased(var1, var2);
   }

   public void controllerLeftPressed(int var1) {
      this.currentGame.controllerLeftPressed(var1);
   }

   public void mouseMoved(int var1, int var2, int var3, int var4) {
      this.currentGame.mouseMoved(var1, var2, var3, var4);
   }

   private void nextGame() {
      if (this.index != -1) {
         ++this.index;
         if (this.index >= this.games.size()) {
            this.index = 0;
         }

         this.startGame();
      }
   }

   public void keyReleased(int var1, char var2) {
      this.currentGame.keyReleased(var1, var2);
   }

   public void mousePressed(int var1, int var2, int var3) {
      this.currentGame.mousePressed(var1, var2, var3);
   }

   public void controllerUpReleased(int var1) {
      this.currentGame.controllerUpReleased(var1);
   }

   public void controllerRightReleased(int var1) {
      this.currentGame.controllerRightReleased(var1);
   }

   public void addGame(Class var1) {
      this.games.add(var1);
   }

   public void controllerButtonPressed(int var1, int var2) {
      this.currentGame.controllerButtonPressed(var1, var2);
   }

   public TestBox() {
      super("Test Box");
   }
}
