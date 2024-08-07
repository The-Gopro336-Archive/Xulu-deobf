package org.newdawn.slick.command;

public class ControllerDirectionControl extends ControllerControl {
   // $FF: synthetic field
   public static final ControllerDirectionControl.Direction RIGHT = new ControllerDirectionControl.Direction(2);
   // $FF: synthetic field
   public static final ControllerDirectionControl.Direction LEFT = new ControllerDirectionControl.Direction(1);
   // $FF: synthetic field
   public static final ControllerDirectionControl.Direction DOWN = new ControllerDirectionControl.Direction(4);
   // $FF: synthetic field
   public static final ControllerDirectionControl.Direction UP = new ControllerDirectionControl.Direction(3);

   public ControllerDirectionControl(int var1, ControllerDirectionControl.Direction var2) {
      super(var1, var2.event, 0);
   }

   private static class Direction {
      // $FF: synthetic field
      private int event;

      public Direction(int var1) {
         this.event = var1;
      }
   }
}
