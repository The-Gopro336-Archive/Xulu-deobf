package org.newdawn.slick.state;

import java.util.HashMap;
import java.util.Iterator;
import org.newdawn.slick.Game;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.InputListener;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.transition.EmptyTransition;
import org.newdawn.slick.state.transition.Transition;

public abstract class StateBasedGame implements Game, InputListener {
   // $FF: synthetic field
   private HashMap states = new HashMap();
   // $FF: synthetic field
   private GameState currentState;
   // $FF: synthetic field
   private Transition enterTransition;
   // $FF: synthetic field
   private GameState nextState;
   // $FF: synthetic field
   private GameContainer container;
   // $FF: synthetic field
   private Transition leaveTransition;
   // $FF: synthetic field
   private String title;

   public final void init(GameContainer var1) throws SlickException {
      this.container = var1;
      this.initStatesList(var1);
      Iterator var2 = this.states.values().iterator();

      while(var2.hasNext()) {
         GameState var3 = (GameState)var2.next();
         var3.init(var1, this);
      }

      if (this.currentState != null) {
         this.currentState.enter(var1, this);
      }

   }

   public void controllerLeftPressed(int var1) {
      if (!this.transitioning()) {
         this.currentState.controllerLeftPressed(var1);
      }
   }

   public String getTitle() {
      return this.title;
   }

   public void mouseWheelMoved(int var1) {
      if (!this.transitioning()) {
         this.currentState.mouseWheelMoved(var1);
      }
   }

   protected void postUpdateState(GameContainer var1, int var2) throws SlickException {
   }

   public void keyPressed(int var1, char var2) {
      if (!this.transitioning()) {
         this.currentState.keyPressed(var1, var2);
      }
   }

   protected void postRenderState(GameContainer var1, Graphics var2) throws SlickException {
   }

   protected void preUpdateState(GameContainer var1, int var2) throws SlickException {
   }

   public GameContainer getContainer() {
      return this.container;
   }

   public void mouseReleased(int var1, int var2, int var3) {
      if (!this.transitioning()) {
         this.currentState.mouseReleased(var1, var2, var3);
      }
   }

   public boolean isAcceptingInput() {
      return this.transitioning() ? false : this.currentState.isAcceptingInput();
   }

   public void controllerRightReleased(int var1) {
      if (!this.transitioning()) {
         this.currentState.controllerRightReleased(var1);
      }
   }

   public void setInput(Input var1) {
   }

   public boolean closeRequested() {
      return true;
   }

   public abstract void initStatesList(GameContainer var1) throws SlickException;

   public void mouseMoved(int var1, int var2, int var3, int var4) {
      if (!this.transitioning()) {
         this.currentState.mouseMoved(var1, var2, var3, var4);
      }
   }

   public void enterState(int var1, Transition var2, Transition var3) {
      if (var2 == null) {
         var2 = new EmptyTransition();
      }

      if (var3 == null) {
         var3 = new EmptyTransition();
      }

      this.leaveTransition = (Transition)var2;
      this.enterTransition = (Transition)var3;
      this.nextState = this.getState(var1);
      if (this.nextState == null) {
         throw new RuntimeException(String.valueOf((new StringBuilder()).append("No game state registered with the ID: ").append(var1)));
      } else {
         this.leaveTransition.init(this.currentState, this.nextState);
      }
   }

   public void mouseClicked(int var1, int var2, int var3, int var4) {
      if (!this.transitioning()) {
         this.currentState.mouseClicked(var1, var2, var3, var4);
      }
   }

   public GameState getCurrentState() {
      return this.currentState;
   }

   public void keyReleased(int var1, char var2) {
      if (!this.transitioning()) {
         this.currentState.keyReleased(var1, var2);
      }
   }

   protected void preRenderState(GameContainer var1, Graphics var2) throws SlickException {
   }

   public void addState(GameState var1) {
      this.states.put(new Integer(var1.getID()), var1);
      if (this.currentState.getID() == -1) {
         this.currentState = var1;
      }

   }

   public StateBasedGame(String var1) {
      this.title = var1;
      this.currentState = new BasicGameState() {
         public void update(GameContainer var1, StateBasedGame var2, int var3) throws SlickException {
         }

         public int getID() {
            return -1;
         }

         public void render(StateBasedGame var1, Graphics var2) throws SlickException {
         }

         public void render(GameContainer var1, StateBasedGame var2, Graphics var3) throws SlickException {
         }

         public void init(GameContainer var1, StateBasedGame var2) throws SlickException {
         }
      };
   }

   public void controllerDownReleased(int var1) {
      if (!this.transitioning()) {
         this.currentState.controllerDownReleased(var1);
      }
   }

   public void controllerButtonPressed(int var1, int var2) {
      if (!this.transitioning()) {
         this.currentState.controllerButtonPressed(var1, var2);
      }
   }

   public final void update(GameContainer var1, int var2) throws SlickException {
      this.preUpdateState(var1, var2);
      if (this.leaveTransition != null) {
         this.leaveTransition.update(this, var1, var2);
         if (!this.leaveTransition.isComplete()) {
            return;
         }

         this.currentState.leave(var1, this);
         GameState var3 = this.currentState;
         this.currentState = this.nextState;
         this.nextState = null;
         this.leaveTransition = null;
         this.currentState.enter(var1, this);
         if (this.enterTransition != null) {
            this.enterTransition.init(this.currentState, var3);
         }
      }

      if (this.enterTransition != null) {
         this.enterTransition.update(this, var1, var2);
         if (!this.enterTransition.isComplete()) {
            return;
         }

         this.enterTransition = null;
      }

      this.currentState.update(var1, this, var2);
      this.postUpdateState(var1, var2);
   }

   public void controllerDownPressed(int var1) {
      if (!this.transitioning()) {
         this.currentState.controllerDownPressed(var1);
      }
   }

   public void inputEnded() {
   }

   public void enterState(int var1) {
      this.enterState(var1, new EmptyTransition(), new EmptyTransition());
   }

   private boolean transitioning() {
      return this.leaveTransition != null || this.enterTransition != null;
   }

   public void mousePressed(int var1, int var2, int var3) {
      if (!this.transitioning()) {
         this.currentState.mousePressed(var1, var2, var3);
      }
   }

   public int getStateCount() {
      return this.states.keySet().size();
   }

   public void controllerRightPressed(int var1) {
      if (!this.transitioning()) {
         this.currentState.controllerRightPressed(var1);
      }
   }

   public void controllerUpPressed(int var1) {
      if (!this.transitioning()) {
         this.currentState.controllerUpPressed(var1);
      }
   }

   public GameState getState(int var1) {
      return (GameState)this.states.get(new Integer(var1));
   }

   public void controllerButtonReleased(int var1, int var2) {
      if (!this.transitioning()) {
         this.currentState.controllerButtonReleased(var1, var2);
      }
   }

   public int getCurrentStateID() {
      return this.currentState.getID();
   }

   public final void render(GameContainer var1, Graphics var2) throws SlickException {
      this.preRenderState(var1, var2);
      if (this.leaveTransition != null) {
         this.leaveTransition.preRender(this, var1, var2);
      } else if (this.enterTransition != null) {
         this.enterTransition.preRender(this, var1, var2);
      }

      this.currentState.render(var1, this, var2);
      if (this.leaveTransition != null) {
         this.leaveTransition.postRender(this, var1, var2);
      } else if (this.enterTransition != null) {
         this.enterTransition.postRender(this, var1, var2);
      }

      this.postRenderState(var1, var2);
   }

   public void controllerLeftReleased(int var1) {
      if (!this.transitioning()) {
         this.currentState.controllerLeftReleased(var1);
      }
   }

   public void controllerUpReleased(int var1) {
      if (!this.transitioning()) {
         this.currentState.controllerUpReleased(var1);
      }
   }

   public void inputStarted() {
   }

   public void mouseDragged(int var1, int var2, int var3, int var4) {
      if (!this.transitioning()) {
         this.currentState.mouseDragged(var1, var2, var3, var4);
      }
   }
}
