package org.newdawn.slick.command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import org.newdawn.slick.Input;
import org.newdawn.slick.util.InputAdapter;

public class InputProvider {
   // $FF: synthetic field
   private ArrayList listeners = new ArrayList();
   // $FF: synthetic field
   private boolean active = true;
   // $FF: synthetic field
   private Input input;
   // $FF: synthetic field
   private HashMap commandState = new HashMap();
   // $FF: synthetic field
   private HashMap commands;

   public void addListener(InputProviderListener var1) {
      this.listeners.add(var1);
   }

   private InputProvider.CommandState getState(Command var1) {
      return (InputProvider.CommandState)this.commandState.get(var1);
   }

   public void removeListener(InputProviderListener var1) {
      this.listeners.remove(var1);
   }

   public boolean isCommandControlPressed(Command var1) {
      return this.getState(var1).isPressed();
   }

   protected void fireReleased(Command var1) {
      this.getState(var1).down = false;
      if (this.isActive()) {
         for(int var2 = 0; var2 < this.listeners.size(); ++var2) {
            ((InputProviderListener)this.listeners.get(var2)).controlReleased(var1);
         }

      }
   }

   public boolean isActive() {
      return this.active;
   }

   public boolean isCommandControlDown(Command var1) {
      return this.getState(var1).isDown();
   }

   public List getUniqueCommands() {
      ArrayList var1 = new ArrayList();
      Iterator var2 = this.commands.values().iterator();

      while(var2.hasNext()) {
         Command var3 = (Command)var2.next();
         if (!var1.contains(var3)) {
            var1.add(var3);
         }
      }

      return var1;
   }

   public InputProvider(Input var1) {
      this.input = var1;
      var1.addListener(new InputProvider.InputListenerImpl());
      this.commands = new HashMap();
   }

   public void bindCommand(Control var1, Command var2) {
      this.commands.put(var1, var2);
      if (this.commandState.get(var2) == null) {
         this.commandState.put(var2, new InputProvider.CommandState());
      }

   }

   protected void firePressed(Command var1) {
      this.getState(var1).down = true;
      this.getState(var1).pressed = true;
      if (this.isActive()) {
         for(int var2 = 0; var2 < this.listeners.size(); ++var2) {
            ((InputProviderListener)this.listeners.get(var2)).controlPressed(var1);
         }

      }
   }

   public void unbindCommand(Control var1) {
      Command var2 = (Command)this.commands.remove(var1);
      if (var2 != null && !this.commands.keySet().contains(var2)) {
         this.commandState.remove(var2);
      }

   }

   public List getControlsFor(Command var1) {
      ArrayList var2 = new ArrayList();
      Iterator var3 = this.commands.entrySet().iterator();

      while(var3.hasNext()) {
         Entry var4 = (Entry)var3.next();
         Control var5 = (Control)var4.getKey();
         Command var6 = (Command)var4.getValue();
         if (var6 == var1) {
            var2.add(var5);
         }
      }

      return var2;
   }

   public void clearCommand(Command var1) {
      List var2 = this.getControlsFor(var1);

      for(int var3 = 0; var3 < var2.size(); ++var3) {
         this.unbindCommand((Control)var2.get(var3));
      }

   }

   public void setActive(boolean var1) {
      this.active = var1;
   }

   private class InputListenerImpl extends InputAdapter {
      public void controllerLeftReleased(int var1) {
         Command var2 = (Command)InputProvider.this.commands.get(new ControllerDirectionControl(var1, ControllerDirectionControl.LEFT));
         if (var2 != null) {
            InputProvider.this.fireReleased(var2);
         }

      }

      public void mouseReleased(int var1, int var2, int var3) {
         Command var4 = (Command)InputProvider.this.commands.get(new MouseButtonControl(var1));
         if (var4 != null) {
            InputProvider.this.fireReleased(var4);
         }

      }

      private InputListenerImpl() {
      }

      public void controllerDownPressed(int var1) {
         Command var2 = (Command)InputProvider.this.commands.get(new ControllerDirectionControl(var1, ControllerDirectionControl.DOWN));
         if (var2 != null) {
            InputProvider.this.firePressed(var2);
         }

      }

      public void controllerButtonReleased(int var1, int var2) {
         Command var3 = (Command)InputProvider.this.commands.get(new ControllerButtonControl(var1, var2));
         if (var3 != null) {
            InputProvider.this.fireReleased(var3);
         }

      }

      public void controllerButtonPressed(int var1, int var2) {
         Command var3 = (Command)InputProvider.this.commands.get(new ControllerButtonControl(var1, var2));
         if (var3 != null) {
            InputProvider.this.firePressed(var3);
         }

      }

      public void keyPressed(int var1, char var2) {
         Command var3 = (Command)InputProvider.this.commands.get(new KeyControl(var1));
         if (var3 != null) {
            InputProvider.this.firePressed(var3);
         }

      }

      public void keyReleased(int var1, char var2) {
         Command var3 = (Command)InputProvider.this.commands.get(new KeyControl(var1));
         if (var3 != null) {
            InputProvider.this.fireReleased(var3);
         }

      }

      public void controllerRightReleased(int var1) {
         Command var2 = (Command)InputProvider.this.commands.get(new ControllerDirectionControl(var1, ControllerDirectionControl.RIGHT));
         if (var2 != null) {
            InputProvider.this.fireReleased(var2);
         }

      }

      // $FF: synthetic method
      InputListenerImpl(Object var2) {
         this();
      }

      public void controllerUpPressed(int var1) {
         Command var2 = (Command)InputProvider.this.commands.get(new ControllerDirectionControl(var1, ControllerDirectionControl.UP));
         if (var2 != null) {
            InputProvider.this.firePressed(var2);
         }

      }

      public void controllerUpReleased(int var1) {
         Command var2 = (Command)InputProvider.this.commands.get(new ControllerDirectionControl(var1, ControllerDirectionControl.UP));
         if (var2 != null) {
            InputProvider.this.fireReleased(var2);
         }

      }

      public void controllerDownReleased(int var1) {
         Command var2 = (Command)InputProvider.this.commands.get(new ControllerDirectionControl(var1, ControllerDirectionControl.DOWN));
         if (var2 != null) {
            InputProvider.this.fireReleased(var2);
         }

      }

      public void controllerRightPressed(int var1) {
         Command var2 = (Command)InputProvider.this.commands.get(new ControllerDirectionControl(var1, ControllerDirectionControl.RIGHT));
         if (var2 != null) {
            InputProvider.this.firePressed(var2);
         }

      }

      public void controllerLeftPressed(int var1) {
         Command var2 = (Command)InputProvider.this.commands.get(new ControllerDirectionControl(var1, ControllerDirectionControl.LEFT));
         if (var2 != null) {
            InputProvider.this.firePressed(var2);
         }

      }

      public boolean isAcceptingInput() {
         return true;
      }

      public void mousePressed(int var1, int var2, int var3) {
         Command var4 = (Command)InputProvider.this.commands.get(new MouseButtonControl(var1));
         if (var4 != null) {
            InputProvider.this.firePressed(var4);
         }

      }
   }

   private class CommandState {
      // $FF: synthetic field
      private boolean down;
      // $FF: synthetic field
      private boolean pressed;

      public boolean isPressed() {
         if (this.pressed) {
            this.pressed = false;
            return true;
         } else {
            return false;
         }
      }

      public boolean isDown() {
         return this.down;
      }

      // $FF: synthetic method
      CommandState(Object var2) {
         this();
      }

      private CommandState() {
      }
   }
}
