package org.newdawn.slick.state.transition;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.GameState;
import org.newdawn.slick.state.StateBasedGame;

public abstract class CrossStateTransition implements Transition {
   // $FF: synthetic field
   private GameState secondState;

   public CrossStateTransition(GameState var1) {
      this.secondState = var1;
   }

   public void preRenderSecondState(StateBasedGame var1, GameContainer var2, Graphics var3) throws SlickException {
   }

   public abstract boolean isComplete();

   public void update(StateBasedGame var1, GameContainer var2, int var3) throws SlickException {
   }

   public void postRender(StateBasedGame var1, GameContainer var2, Graphics var3) throws SlickException {
      this.preRenderSecondState(var1, var2, var3);
      this.secondState.render(var2, var1, var3);
      this.postRenderSecondState(var1, var2, var3);
   }

   public void postRenderSecondState(StateBasedGame var1, GameContainer var2, Graphics var3) throws SlickException {
   }

   public void preRender(StateBasedGame var1, GameContainer var2, Graphics var3) throws SlickException {
      this.preRenderFirstState(var1, var2, var3);
   }

   public void preRenderFirstState(StateBasedGame var1, GameContainer var2, Graphics var3) throws SlickException {
   }
}
