package org.newdawn.slick.tests;

import org.newdawn.slick.AngelCodeFont;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.gui.AbstractComponent;
import org.newdawn.slick.gui.ComponentListener;
import org.newdawn.slick.gui.MouseOverArea;
import org.newdawn.slick.gui.TextField;
import org.newdawn.slick.util.Log;

public class GUITest extends BasicGame implements ComponentListener {
   // $FF: synthetic field
   private String message = "Demo Menu System with stock images";
   // $FF: synthetic field
   private MouseOverArea[] areas = new MouseOverArea[4];
   // $FF: synthetic field
   private AppGameContainer app;
   // $FF: synthetic field
   private TextField field;
   // $FF: synthetic field
   private Image image;
   // $FF: synthetic field
   private TextField field2;
   // $FF: synthetic field
   private GameContainer container;
   // $FF: synthetic field
   private Font font;
   // $FF: synthetic field
   private Image background;

   public GUITest() {
      super("GUI Test");
   }

   public void init(GameContainer var1) throws SlickException {
      if (var1 instanceof AppGameContainer) {
         this.app = (AppGameContainer)var1;
         this.app.setIcon("testdata/icon.tga");
      }

      this.font = new AngelCodeFont("testdata/demo2.fnt", "testdata/demo2_00.tga");
      this.field = new TextField(var1, this.font, 150, 20, 500, 35, new ComponentListener() {
         public void componentActivated(AbstractComponent var1) {
            GUITest.this.message = String.valueOf((new StringBuilder()).append("Entered1: ").append(GUITest.this.field.getText()));
            GUITest.this.field2.setFocus(true);
         }
      });
      this.field2 = new TextField(var1, this.font, 150, 70, 500, 35, new ComponentListener() {
         public void componentActivated(AbstractComponent var1) {
            GUITest.this.message = String.valueOf((new StringBuilder()).append("Entered2: ").append(GUITest.this.field2.getText()));
            GUITest.this.field.setFocus(true);
         }
      });
      this.field2.setBorderColor(Color.red);
      this.container = var1;
      this.image = new Image("testdata/logo.tga");
      this.background = new Image("testdata/dungeontiles.gif");
      var1.setMouseCursor((String)"testdata/cursor.tga", 0, 0);

      for(int var2 = 0; var2 < 4; ++var2) {
         this.areas[var2] = new MouseOverArea(var1, this.image, 300, 100 + var2 * 100, 200, 90, this);
         this.areas[var2].setNormalColor(new Color(1.0F, 1.0F, 1.0F, 0.8F));
         this.areas[var2].setMouseOverColor(new Color(1.0F, 1.0F, 1.0F, 0.9F));
      }

   }

   public void update(GameContainer var1, int var2) {
   }

   public static void main(String[] var0) {
      try {
         AppGameContainer var1 = new AppGameContainer(new GUITest());
         var1.setDisplayMode(800, 600, false);
         var1.start();
      } catch (SlickException var2) {
         var2.printStackTrace();
      }

   }

   public void componentActivated(AbstractComponent var1) {
      System.out.println(String.valueOf((new StringBuilder()).append("ACTIVL : ").append(var1)));

      for(int var2 = 0; var2 < 4; ++var2) {
         if (var1 == this.areas[var2]) {
            this.message = String.valueOf((new StringBuilder()).append("Option ").append(var2 + 1).append(" pressed!"));
         }
      }

      if (var1 == this.field2) {
      }

   }

   public void keyPressed(int var1, char var2) {
      if (var1 == 1) {
         System.exit(0);
      }

      if (var1 == 60) {
         this.app.setDefaultMouseCursor();
      }

      if (var1 == 59 && this.app != null) {
         try {
            this.app.setDisplayMode(640, 480, false);
         } catch (SlickException var4) {
            Log.error((Throwable)var4);
         }
      }

   }

   public void render(GameContainer var1, Graphics var2) {
      this.background.draw(0.0F, 0.0F, 800.0F, 500.0F);

      for(int var3 = 0; var3 < 4; ++var3) {
         this.areas[var3].render(var1, var2);
      }

      this.field.render(var1, var2);
      this.field2.render(var1, var2);
      var2.setFont(this.font);
      var2.drawString(this.message, 200.0F, 550.0F);
   }
}
