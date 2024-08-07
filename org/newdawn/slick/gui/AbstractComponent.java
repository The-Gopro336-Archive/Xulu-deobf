package org.newdawn.slick.gui;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.util.InputAdapter;

public abstract class AbstractComponent extends InputAdapter {
   // $FF: synthetic field
   protected Set listeners;
   // $FF: synthetic field
   protected GUIContext container;
   // $FF: synthetic field
   private static AbstractComponent currentFocus = null;
   // $FF: synthetic field
   protected Input input;
   // $FF: synthetic field
   private boolean focus = false;

   public abstract int getY();

   public boolean hasFocus() {
      return this.focus;
   }

   protected void consumeEvent() {
      this.input.consumeEvent();
   }

   public void setFocus(boolean var1) {
      if (var1) {
         if (currentFocus != null) {
            currentFocus.setFocus(false);
         }

         currentFocus = this;
      } else if (currentFocus == this) {
         currentFocus = null;
      }

      this.focus = var1;
   }

   public abstract int getHeight();

   public void removeListener(ComponentListener var1) {
      this.listeners.remove(var1);
   }

   protected void notifyListeners() {
      Iterator var1 = this.listeners.iterator();

      while(var1.hasNext()) {
         ((ComponentListener)var1.next()).componentActivated(this);
      }

   }

   public abstract int getX();

   public abstract void render(GUIContext var1, Graphics var2) throws SlickException;

   public abstract void setLocation(int var1, int var2);

   public void mouseReleased(int var1, int var2, int var3) {
      this.setFocus(Rectangle.contains((float)var2, (float)var3, (float)this.getX(), (float)this.getY(), (float)this.getWidth(), (float)this.getHeight()));
   }

   public AbstractComponent(GUIContext var1) {
      this.container = var1;
      this.listeners = new HashSet();
      this.input = var1.getInput();
      this.input.addPrimaryListener(this);
      this.setLocation(0, 0);
   }

   public abstract int getWidth();

   public void addListener(ComponentListener var1) {
      this.listeners.add(var1);
   }
}
