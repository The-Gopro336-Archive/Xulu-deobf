package org.newdawn.slick.tests;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

public class KeyRepeatTest extends BasicGame {
   // $FF: synthetic field
   private Input input;
   // $FF: synthetic field
   private int count;

   public KeyRepeatTest() {
      super("KeyRepeatTest");
   }

   public void render(GameContainer var1, Graphics var2) {
      var2.drawString(String.valueOf((new StringBuilder()).append("Key Press Count: ").append(this.count)), 100.0F, 100.0F);
      var2.drawString("Press Space to Toggle Key Repeat", 100.0F, 150.0F);
      var2.drawString(String.valueOf((new StringBuilder()).append("Key Repeat Enabled: ").append(this.input.isKeyRepeatEnabled())), 100.0F, 200.0F);
   }

   public void init(GameContainer var1) throws SlickException {
      this.input = var1.getInput();
      this.input.enableKeyRepeat(300, 100);
   }

   public void keyPressed(int var1, char var2) {
      ++this.count;
      if (var1 == 57) {
         if (this.input.isKeyRepeatEnabled()) {
            this.input.disableKeyRepeat();
         } else {
            this.input.enableKeyRepeat(300, 100);
         }
      }

   }

   public static void main(String[] var0) {
      try {
         AppGameContainer var1 = new AppGameContainer(new KeyRepeatTest());
         var1.setDisplayMode(800, 600, false);
         var1.start();
      } catch (SlickException var2) {
         var2.printStackTrace();
      }

   }

   public void update(GameContainer var1, int var2) {
   }
}
