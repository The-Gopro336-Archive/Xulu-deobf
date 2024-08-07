package org.newdawn.slick.state.transition;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.GameState;
import org.newdawn.slick.state.StateBasedGame;

public class FadeInTransition implements Transition {
   // $FF: synthetic field
   private Color color;
   // $FF: synthetic field
   private int fadeTime;

   public void postRender(StateBasedGame var1, GameContainer var2, Graphics var3) {
      Color var4 = var3.getColor();
      var3.setColor(this.color);
      var3.fillRect(0.0F, 0.0F, (float)(var2.getWidth() * 2), (float)(var2.getHeight() * 2));
      var3.setColor(var4);
   }

   public void init(GameState var1, GameState var2) {
   }

   public void preRender(StateBasedGame var1, GameContainer var2, Graphics var3) {
   }

   public boolean isComplete() {
      return this.color.a <= 0.0F;
   }

   public FadeInTransition(Color var1) {
      this(var1, 500);
   }

   public FadeInTransition() {
      this(Color.black, 500);
   }

   public FadeInTransition(Color var1, int var2) {
      this.fadeTime = 500;
      this.color = new Color(var1);
      this.color.a = 1.0F;
      this.fadeTime = var2;
   }

   public void update(StateBasedGame var1, GameContainer var2, int var3) {
      Color var10000 = this.color;
      var10000.a -= (float)var3 * (1.0F / (float)this.fadeTime);
      if (this.color.a < 0.0F) {
         this.color.a = 0.0F;
      }

   }
}
