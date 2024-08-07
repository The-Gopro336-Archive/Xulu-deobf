package org.newdawn.slick.command;

abstract class ControllerControl implements Control {
   // $FF: synthetic field
   private int button;
   // $FF: synthetic field
   protected static final int DOWN_EVENT = 4;
   // $FF: synthetic field
   protected static final int RIGHT_EVENT = 2;
   // $FF: synthetic field
   private int controllerNumber;
   // $FF: synthetic field
   protected static final int UP_EVENT = 3;
   // $FF: synthetic field
   protected static final int LEFT_EVENT = 1;
   // $FF: synthetic field
   protected static final int BUTTON_EVENT = 0;
   // $FF: synthetic field
   private int event;

   protected ControllerControl(int var1, int var2, int var3) {
      this.event = var2;
      this.button = var3;
      this.controllerNumber = var1;
   }

   public int hashCode() {
      return this.event + this.button + this.controllerNumber;
   }

   public boolean equals(Object var1) {
      if (var1 == null) {
         return false;
      } else if (!(var1 instanceof ControllerControl)) {
         return false;
      } else {
         ControllerControl var2 = (ControllerControl)var1;
         return var2.controllerNumber == this.controllerNumber && var2.event == this.event && var2.button == this.button;
      }
   }
}
