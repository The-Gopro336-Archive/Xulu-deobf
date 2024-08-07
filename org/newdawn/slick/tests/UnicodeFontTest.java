package org.newdawn.slick.tests;

import java.io.IOException;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;

public class UnicodeFontTest extends BasicGame {
   // $FF: synthetic field
   private UnicodeFont unicodeFont;

   public UnicodeFontTest() {
      super("Font Test");
   }

   public static void main(String[] var0) throws SlickException, IOException {
      Input.disableControllers();
      AppGameContainer var1 = new AppGameContainer(new UnicodeFontTest());
      var1.setDisplayMode(512, 600, false);
      var1.setTargetFrameRate(20);
      var1.start();
   }

   public void render(GameContainer var1, Graphics var2) {
      var2.setColor(Color.white);
      String var3 = "This is UnicodeFont!\nIt rockz. Kerning: T,";
      this.unicodeFont.drawString(10.0F, 33.0F, var3);
      var2.setColor(Color.red);
      var2.drawRect(10.0F, 33.0F, (float)this.unicodeFont.getWidth(var3), (float)this.unicodeFont.getLineHeight());
      var2.setColor(Color.blue);
      int var4 = this.unicodeFont.getYOffset(var3);
      var2.drawRect(10.0F, (float)(33 + var4), (float)this.unicodeFont.getWidth(var3), (float)(this.unicodeFont.getHeight(var3) - var4));
      this.unicodeFont.addGlyphs("~!@!#!#$%___--");
   }

   public void update(GameContainer var1, int var2) throws SlickException {
      this.unicodeFont.loadGlyphs(1);
   }

   public void init(GameContainer var1) throws SlickException {
      var1.setShowFPS(false);
      this.unicodeFont = new UnicodeFont("c:/windows/fonts/arial.ttf", 48, false, false);
      this.unicodeFont.getEffects().add(new ColorEffect(java.awt.Color.white));
      var1.getGraphics().setBackground(Color.darkGray);
   }
}
