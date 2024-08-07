package org.newdawn.slick.gui;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

/** @deprecated */
public abstract class BasicComponent extends AbstractComponent {
   // $FF: synthetic field
   protected int width;
   // $FF: synthetic field
   protected int height;
   // $FF: synthetic field
   protected int x;
   // $FF: synthetic field
   protected int y;

   public void render(GUIContext var1, Graphics var2) throws SlickException {
      this.renderImpl(var1, var2);
   }

   public int getY() {
      return this.y;
   }

   public BasicComponent(GUIContext var1) {
      super(var1);
   }

   public int getWidth() {
      return this.width;
   }

   public int getHeight() {
      return this.height;
   }

   public void setLocation(int var1, int var2) {
      this.x = var1;
      this.y = var2;
   }

   public abstract void renderImpl(GUIContext var1, Graphics var2);

   public int getX() {
      return this.x;
   }
}
