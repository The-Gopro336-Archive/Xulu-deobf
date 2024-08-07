package org.newdawn.slick.state;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.InputListener;
import org.newdawn.slick.SlickException;

public interface GameState extends InputListener {
   void enter(GameContainer var1, StateBasedGame var2) throws SlickException;

   void leave(GameContainer var1, StateBasedGame var2) throws SlickException;

   void init(GameContainer var1, StateBasedGame var2) throws SlickException;

   void update(GameContainer var1, StateBasedGame var2, int var3) throws SlickException;

   int getID();

   void render(GameContainer var1, StateBasedGame var2, Graphics var3) throws SlickException;
}
