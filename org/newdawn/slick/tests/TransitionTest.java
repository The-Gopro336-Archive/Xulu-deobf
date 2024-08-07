package org.newdawn.slick.tests;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.BlobbyTransition;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;
import org.newdawn.slick.state.transition.HorizontalSplitTransition;
import org.newdawn.slick.state.transition.RotateTransition;
import org.newdawn.slick.state.transition.SelectTransition;
import org.newdawn.slick.state.transition.Transition;
import org.newdawn.slick.state.transition.VerticalSplitTransition;
import org.newdawn.slick.util.Log;

public class TransitionTest extends StateBasedGame {
   // $FF: synthetic field
   private int index;
   // $FF: synthetic field
   private Class[][] transitions = new Class[][]{{null, VerticalSplitTransition.class}, {FadeOutTransition.class, FadeInTransition.class}, {null, RotateTransition.class}, {null, HorizontalSplitTransition.class}, {null, BlobbyTransition.class}, {null, SelectTransition.class}};

   public Transition[] getNextTransitionPair() {
      Transition[] var1 = new Transition[2];

      try {
         if (this.transitions[this.index][0] != null) {
            var1[0] = (Transition)this.transitions[this.index][0].newInstance();
         }

         if (this.transitions[this.index][1] != null) {
            var1[1] = (Transition)this.transitions[this.index][1].newInstance();
         }
      } catch (Throwable var3) {
         Log.error(var3);
      }

      ++this.index;
      if (this.index >= this.transitions.length) {
         this.index = 0;
      }

      return var1;
   }

   public void initStatesList(GameContainer var1) throws SlickException {
      this.addState(new TransitionTest.ImageState(0, "testdata/wallpaper/paper1.png", 1));
      this.addState(new TransitionTest.ImageState(1, "testdata/wallpaper/paper2.png", 2));
      this.addState(new TransitionTest.ImageState(2, "testdata/bigimage.tga", 0));
   }

   public static void main(String[] var0) {
      try {
         AppGameContainer var1 = new AppGameContainer(new TransitionTest());
         var1.setDisplayMode(800, 600, false);
         var1.start();
      } catch (SlickException var2) {
         var2.printStackTrace();
      }

   }

   public TransitionTest() {
      super("Transition Test - Hit Space To Transition");
   }

   private class ImageState extends BasicGameState {
      // $FF: synthetic field
      private int next;
      // $FF: synthetic field
      private String ref;
      // $FF: synthetic field
      private int id;
      // $FF: synthetic field
      private Image image;

      public ImageState(int var2, String var3, int var4) {
         this.ref = var3;
         this.id = var2;
         this.next = var4;
      }

      public int getID() {
         return this.id;
      }

      public void update(GameContainer var1, StateBasedGame var2, int var3) throws SlickException {
         if (var1.getInput().isKeyPressed(57)) {
            Transition[] var4 = TransitionTest.this.getNextTransitionPair();
            var2.enterState(this.next, var4[0], var4[1]);
         }

      }

      public void init(GameContainer var1, StateBasedGame var2) throws SlickException {
         this.image = new Image(this.ref);
      }

      public void render(GameContainer var1, StateBasedGame var2, Graphics var3) throws SlickException {
         this.image.draw(0.0F, 0.0F, 800.0F, 600.0F);
         var3.setColor(Color.red);
         var3.fillRect(-50.0F, 200.0F, 50.0F, 50.0F);
      }
   }
}
