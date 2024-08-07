package org.newdawn.slick.gui;

import org.lwjgl.input.Cursor;
import org.newdawn.slick.Font;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.opengl.ImageData;

public interface GUIContext {
   void setMouseCursor(ImageData var1, int var2, int var3) throws SlickException;

   int getScreenHeight();

   int getWidth();

   int getScreenWidth();

   void setDefaultMouseCursor();

   void setMouseCursor(Cursor var1, int var2, int var3) throws SlickException;

   Input getInput();

   Font getDefaultFont();

   void setMouseCursor(String var1, int var2, int var3) throws SlickException;

   long getTime();

   int getHeight();
}
