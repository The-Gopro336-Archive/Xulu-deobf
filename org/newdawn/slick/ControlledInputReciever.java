package org.newdawn.slick;

public interface ControlledInputReciever {
   void inputStarted();

   void inputEnded();

   boolean isAcceptingInput();

   void setInput(Input var1);
}
