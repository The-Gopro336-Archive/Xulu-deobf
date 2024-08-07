package org.newdawn.slick.tests;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class ImageMemTest extends BasicGame {
   public void init(GameContainer var1) throws SlickException {
      try {
         Image var2 = new Image(2400, 2400);
         var2.getGraphics();
         var2.destroy();
         var2 = new Image(2400, 2400);
         var2.getGraphics();
      } catch (Exception var4) {
         var4.printStackTrace();
      }

   }

   public ImageMemTest() {
      super("Image Memory Test");
   }

   public void update(GameContainer var1, int var2) {
   }

   public static void main(String[] var0) {
      try {
         AppGameContainer var1 = new AppGameContainer(new ImageMemTest());
         var1.setDisplayMode(800, 600, false);
         var1.start();
      } catch (SlickException var3) {
         var3.printStackTrace();
      }

   }

   public void render(GameContainer var1, Graphics var2) {
   }
}
