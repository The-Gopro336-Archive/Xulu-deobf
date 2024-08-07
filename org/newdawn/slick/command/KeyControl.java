package org.newdawn.slick.command;

public class KeyControl implements Control {
   // $FF: synthetic field
   private int keycode;

   public boolean equals(Object var1) {
      if (var1 instanceof KeyControl) {
         return ((KeyControl)var1).keycode == this.keycode;
      } else {
         return false;
      }
   }

   public KeyControl(int var1) {
      this.keycode = var1;
   }

   public int hashCode() {
      return this.keycode;
   }
}
