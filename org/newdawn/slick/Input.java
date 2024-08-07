package org.newdawn.slick;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Controller;
import org.lwjgl.input.Controllers;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.util.Log;

public class Input {
   // $FF: synthetic field
   private float scaleX = 1.0F;
   // $FF: synthetic field
   public static final int KEY_LWIN = 219;
   // $FF: synthetic field
   public static final int KEY_3 = 4;
   // $FF: synthetic field
   protected ArrayList mouseListeners = new ArrayList();
   // $FF: synthetic field
   public static final int KEY_LBRACKET = 26;
   // $FF: synthetic field
   public static final int KEY_J = 36;
   // $FF: synthetic field
   public static final int KEY_V = 47;
   // $FF: synthetic field
   public static final int KEY_0 = 11;
   // $FF: synthetic field
   public static final int KEY_F11 = 87;
   // $FF: synthetic field
   public static final int KEY_Q = 16;
   // $FF: synthetic field
   public static final int KEY_UNLABELED = 151;
   // $FF: synthetic field
   public static final int KEY_P = 25;
   // $FF: synthetic field
   public static final int KEY_C = 46;
   // $FF: synthetic field
   public static final int KEY_B = 48;
   // $FF: synthetic field
   public static final int ANY_CONTROLLER = -1;
   // $FF: synthetic field
   public static final int KEY_N = 49;
   // $FF: synthetic field
   public static final int KEY_F14 = 101;
   // $FF: synthetic field
   public static final int KEY_F2 = 60;
   // $FF: synthetic field
   public static final int KEY_F = 33;
   // $FF: synthetic field
   public static final int KEY_YEN = 125;
   // $FF: synthetic field
   public static final int KEY_8 = 9;
   // $FF: synthetic field
   private int pressedX = -1;
   // $FF: synthetic field
   public static final int KEY_ENTER = 28;
   // $FF: synthetic field
   public static final int KEY_PAUSE = 197;
   // $FF: synthetic field
   protected boolean[] pressed = new boolean[1024];
   // $FF: synthetic field
   private float yoffset = 0.0F;
   // $FF: synthetic field
   public static final int KEY_SEMICOLON = 39;
   // $FF: synthetic field
   public static final int KEY_DIVIDE = 181;
   // $FF: synthetic field
   public static final int KEY_NUMLOCK = 69;
   // $FF: synthetic field
   public static final int KEY_F6 = 64;
   // $FF: synthetic field
   public static final int KEY_RSHIFT = 54;
   // $FF: synthetic field
   public static final int KEY_5 = 6;
   // $FF: synthetic field
   public static final int KEY_NUMPAD3 = 81;
   // $FF: synthetic field
   private int lastMouseX;
   // $FF: synthetic field
   protected ArrayList keyListenersToAdd = new ArrayList();
   // $FF: synthetic field
   private float xoffset = 0.0F;
   // $FF: synthetic field
   public static final int KEY_RWIN = 220;
   // $FF: synthetic field
   public static final int MOUSE_LEFT_BUTTON = 0;
   // $FF: synthetic field
   public static final int KEY_NUMPAD8 = 72;
   // $FF: synthetic field
   private static final int BUTTON9 = 12;
   // $FF: synthetic field
   public static final int KEY_NUMPAD5 = 76;
   // $FF: synthetic field
   public static final int KEY_ESCAPE = 1;
   // $FF: synthetic field
   private boolean paused;
   // $FF: synthetic field
   private static final int BUTTON5 = 8;
   // $FF: synthetic field
   protected ArrayList keyListeners = new ArrayList();
   // $FF: synthetic field
   public static final int KEY_2 = 3;
   // $FF: synthetic field
   public static final int KEY_RMENU = 184;
   // $FF: synthetic field
   public static final int KEY_SUBTRACT = 74;
   // $FF: synthetic field
   private int clickY;
   // $FF: synthetic field
   public static final int KEY_END = 207;
   // $FF: synthetic field
   public static final int KEY_SLASH = 53;
   // $FF: synthetic field
   public static final int KEY_NUMPAD2 = 80;
   // $FF: synthetic field
   public static final int KEY_F7 = 65;
   // $FF: synthetic field
   private static boolean controllersInited = false;
   // $FF: synthetic field
   public static final int KEY_MULTIPLY = 55;
   // $FF: synthetic field
   private static ArrayList controllers = new ArrayList();
   // $FF: synthetic field
   public static final int KEY_AT = 145;
   // $FF: synthetic field
   private int keyRepeatInterval;
   // $FF: synthetic field
   public static final int KEY_COLON = 146;
   // $FF: synthetic field
   private static final int BUTTON2 = 5;
   // $FF: synthetic field
   public static final int KEY_I = 23;
   // $FF: synthetic field
   public static final int KEY_6 = 7;
   // $FF: synthetic field
   private static final int MAX_BUTTONS = 100;
   // $FF: synthetic field
   public static final int KEY_TAB = 15;
   // $FF: synthetic field
   public static final int KEY_F9 = 67;
   // $FF: synthetic field
   private long doubleClickTimeout = 0L;
   // $FF: synthetic field
   protected HashSet allListeners = new HashSet();
   // $FF: synthetic field
   private int wheel;
   // $FF: synthetic field
   public static final int KEY_INSERT = 210;
   // $FF: synthetic field
   private int pressedY = -1;
   // $FF: synthetic field
   private static final int RIGHT = 1;
   // $FF: synthetic field
   private static final int LEFT = 0;
   // $FF: synthetic field
   protected char[] keys = new char[1024];
   // $FF: synthetic field
   public static final int KEY_RBRACKET = 27;
   // $FF: synthetic field
   public static final int KEY_R = 19;
   // $FF: synthetic field
   public static final int KEY_F1 = 59;
   // $FF: synthetic field
   public static final int KEY_K = 37;
   // $FF: synthetic field
   public static final int KEY_H = 35;
   // $FF: synthetic field
   public static final int KEY_EQUALS = 13;
   // $FF: synthetic field
   public static final int KEY_F5 = 63;
   // $FF: synthetic field
   public static final int KEY_KANA = 112;
   // $FF: synthetic field
   public static final int KEY_RETURN = 28;
   // $FF: synthetic field
   public static final int KEY_F10 = 68;
   // $FF: synthetic field
   public static final int KEY_M = 50;
   // $FF: synthetic field
   public static final int KEY_CIRCUMFLEX = 144;
   // $FF: synthetic field
   public static final int KEY_NOCONVERT = 123;
   // $FF: synthetic field
   private static final int BUTTON1 = 4;
   // $FF: synthetic field
   public static final int KEY_NUMPAD1 = 79;
   // $FF: synthetic field
   public static final int KEY_COMMA = 51;
   // $FF: synthetic field
   public static final int KEY_PRIOR = 201;
   // $FF: synthetic field
   public static final int KEY_O = 24;
   // $FF: synthetic field
   public static final int KEY_PERIOD = 52;
   // $FF: synthetic field
   public static final int KEY_F13 = 100;
   // $FF: synthetic field
   private static final int UP = 2;
   // $FF: synthetic field
   private float scaleY = 1.0F;
   // $FF: synthetic field
   public static final int KEY_UP = 200;
   // $FF: synthetic field
   public static final int KEY_F12 = 88;
   // $FF: synthetic field
   public static final int KEY_F15 = 102;
   // $FF: synthetic field
   public static final int KEY_F8 = 66;
   // $FF: synthetic field
   public static final int KEY_1 = 2;
   // $FF: synthetic field
   protected long[] nextRepeat = new long[1024];
   // $FF: synthetic field
   private int mouseClickTolerance = 5;
   // $FF: synthetic field
   public static final int KEY_UNDERLINE = 147;
   // $FF: synthetic field
   protected ArrayList controllerListeners = new ArrayList();
   // $FF: synthetic field
   public static final int MOUSE_MIDDLE_BUTTON = 2;
   // $FF: synthetic field
   public static final int KEY_LALT = 56;
   // $FF: synthetic field
   private static final int BUTTON4 = 7;
   // $FF: synthetic field
   private int clickX;
   // $FF: synthetic field
   private static final int BUTTON10 = 13;
   // $FF: synthetic field
   public static final int KEY_CAPITAL = 58;
   // $FF: synthetic field
   public static final int KEY_X = 45;
   // $FF: synthetic field
   public static final int KEY_DELETE = 211;
   // $FF: synthetic field
   public static final int KEY_L = 38;
   // $FF: synthetic field
   public static final int KEY_NUMPAD0 = 82;
   // $FF: synthetic field
   public static final int KEY_BACKSLASH = 43;
   // $FF: synthetic field
   public static final int KEY_LEFT = 203;
   // $FF: synthetic field
   public static final int KEY_F4 = 62;
   // $FF: synthetic field
   public static final int KEY_S = 31;
   // $FF: synthetic field
   public static final int KEY_RALT = 184;
   // $FF: synthetic field
   public static final int KEY_HOME = 199;
   // $FF: synthetic field
   private boolean[][] controllerPressed = new boolean[100][100];
   // $FF: synthetic field
   public static final int KEY_STOP = 149;
   // $FF: synthetic field
   public static final int KEY_BACK = 14;
   // $FF: synthetic field
   public static final int KEY_DOWN = 208;
   // $FF: synthetic field
   private boolean keyRepeat;
   // $FF: synthetic field
   public static final int KEY_SLEEP = 223;
   // $FF: synthetic field
   public static final int KEY_D = 32;
   // $FF: synthetic field
   private static final int DOWN = 3;
   // $FF: synthetic field
   public static final int KEY_SYSRQ = 183;
   // $FF: synthetic field
   public static final int KEY_NUMPADCOMMA = 179;
   // $FF: synthetic field
   private static final int BUTTON6 = 9;
   // $FF: synthetic field
   private boolean displayActive = true;
   // $FF: synthetic field
   private int keyRepeatInitial;
   // $FF: synthetic field
   public static final int KEY_4 = 5;
   // $FF: synthetic field
   private int doubleClickDelay = 250;
   // $FF: synthetic field
   public static final int KEY_W = 17;
   // $FF: synthetic field
   public static final int MOUSE_RIGHT_BUTTON = 1;
   // $FF: synthetic field
   private int lastMouseY;
   // $FF: synthetic field
   public static final int KEY_Y = 21;
   // $FF: synthetic field
   public static final int KEY_SCROLL = 70;
   // $FF: synthetic field
   public static final int KEY_NUMPAD7 = 71;
   // $FF: synthetic field
   public static final int KEY_NUMPADEQUALS = 141;
   // $FF: synthetic field
   public static final int KEY_F3 = 61;
   // $FF: synthetic field
   public static final int KEY_7 = 8;
   // $FF: synthetic field
   private boolean[][] controls = new boolean[10][110];
   // $FF: synthetic field
   public static final int KEY_A = 30;
   // $FF: synthetic field
   public static final int KEY_RIGHT = 205;
   // $FF: synthetic field
   private int clickButton;
   // $FF: synthetic field
   protected boolean[] mousePressed = new boolean[10];
   // $FF: synthetic field
   public static final int KEY_T = 20;
   // $FF: synthetic field
   private static final int BUTTON3 = 6;
   // $FF: synthetic field
   public static final int KEY_G = 34;
   // $FF: synthetic field
   public static final int KEY_KANJI = 148;
   // $FF: synthetic field
   public static final int KEY_NUMPADENTER = 156;
   // $FF: synthetic field
   public static final int KEY_MINUS = 12;
   // $FF: synthetic field
   public static final int KEY_APOSTROPHE = 40;
   // $FF: synthetic field
   protected boolean consumed = false;
   // $FF: synthetic field
   public static final int KEY_DECIMAL = 83;
   // $FF: synthetic field
   public static final int KEY_RCONTROL = 157;
   // $FF: synthetic field
   public static final int KEY_NUMPAD4 = 75;
   // $FF: synthetic field
   protected ArrayList mouseListenersToAdd = new ArrayList();
   // $FF: synthetic field
   public static final int KEY_E = 18;
   // $FF: synthetic field
   private static final int BUTTON8 = 11;
   // $FF: synthetic field
   public static final int KEY_Z = 44;
   // $FF: synthetic field
   public static final int KEY_LMENU = 56;
   // $FF: synthetic field
   public static final int KEY_NUMPAD6 = 77;
   // $FF: synthetic field
   public static final int KEY_CONVERT = 121;
   // $FF: synthetic field
   public static final int KEY_NEXT = 209;
   // $FF: synthetic field
   public static final int KEY_ADD = 78;
   // $FF: synthetic field
   public static final int KEY_NUMPAD9 = 73;
   // $FF: synthetic field
   public static final int KEY_APPS = 221;
   // $FF: synthetic field
   public static final int KEY_POWER = 222;
   // $FF: synthetic field
   public static final int KEY_LCONTROL = 29;
   // $FF: synthetic field
   public static final int KEY_GRAVE = 41;
   // $FF: synthetic field
   public static final int KEY_9 = 10;
   // $FF: synthetic field
   private static final int BUTTON7 = 10;
   // $FF: synthetic field
   private int height;
   // $FF: synthetic field
   public static final int KEY_LSHIFT = 42;
   // $FF: synthetic field
   public static final int KEY_AX = 150;
   // $FF: synthetic field
   public static final int KEY_SPACE = 57;
   // $FF: synthetic field
   public static final int KEY_U = 22;

   public boolean isControllerUp(int var1) {
      if (var1 >= this.getControllerCount()) {
         return false;
      } else if (var1 == -1) {
         for(int var2 = 0; var2 < controllers.size(); ++var2) {
            if (this.isControllerUp(var2)) {
               return true;
            }
         }

         return false;
      } else {
         return ((Controller)controllers.get(var1)).getYAxisValue() < -0.5F || ((Controller)controllers.get(var1)).getPovY() < -0.5F;
      }
   }

   private void fireControlRelease(int var1, int var2) {
      this.consumed = false;

      for(int var3 = 0; var3 < this.controllerListeners.size(); ++var3) {
         ControllerListener var4 = (ControllerListener)this.controllerListeners.get(var3);
         if (var4.isAcceptingInput()) {
            switch(var1) {
            case 0:
               var4.controllerLeftReleased(var2);
               break;
            case 1:
               var4.controllerRightReleased(var2);
               break;
            case 2:
               var4.controllerUpReleased(var2);
               break;
            case 3:
               var4.controllerDownReleased(var2);
               break;
            default:
               var4.controllerButtonReleased(var2, var1 - 4 + 1);
            }

            if (this.consumed) {
               break;
            }
         }
      }

   }

   public boolean isControllerLeft(int var1) {
      if (var1 >= this.getControllerCount()) {
         return false;
      } else if (var1 == -1) {
         for(int var2 = 0; var2 < controllers.size(); ++var2) {
            if (this.isControllerLeft(var2)) {
               return true;
            }
         }

         return false;
      } else {
         return ((Controller)controllers.get(var1)).getXAxisValue() < -0.5F || ((Controller)controllers.get(var1)).getPovX() < -0.5F;
      }
   }

   public void disableKeyRepeat() {
      Keyboard.enableRepeatEvents(false);
   }

   public void removeAllListeners() {
      this.removeAllKeyListeners();
      this.removeAllMouseListeners();
      this.removeAllControllerListeners();
   }

   public void removeListener(InputListener var1) {
      this.removeKeyListener(var1);
      this.removeMouseListener(var1);
      this.removeControllerListener(var1);
   }

   public void initControllers() throws SlickException {
      if (!controllersInited) {
         controllersInited = true;

         try {
            Controllers.create();
            int var1 = Controllers.getControllerCount();

            int var2;
            for(var2 = 0; var2 < var1; ++var2) {
               Controller var3 = Controllers.getController(var2);
               if (var3.getButtonCount() >= 3 && var3.getButtonCount() < 100) {
                  controllers.add(var3);
               }
            }

            Log.info(String.valueOf((new StringBuilder()).append("Found ").append(controllers.size()).append(" controllers")));

            for(var2 = 0; var2 < controllers.size(); ++var2) {
               Log.info(String.valueOf((new StringBuilder()).append(var2).append(" : ").append(((Controller)controllers.get(var2)).getName())));
            }
         } catch (LWJGLException var4) {
            if (var4.getCause() instanceof ClassNotFoundException) {
               throw new SlickException("Unable to create controller - no jinput found - add jinput.jar to your classpath");
            }

            throw new SlickException("Unable to create controllers");
         } catch (NoClassDefFoundError var5) {
         }

      }
   }

   public void considerDoubleClick(int var1, int var2, int var3) {
      if (this.doubleClickTimeout == 0L) {
         this.clickX = var2;
         this.clickY = var3;
         this.clickButton = var1;
         this.doubleClickTimeout = System.currentTimeMillis() + (long)this.doubleClickDelay;
         this.fireMouseClicked(var1, var2, var3, 1);
      } else if (this.clickButton == var1 && System.currentTimeMillis() < this.doubleClickTimeout) {
         this.fireMouseClicked(var1, var2, var3, 2);
         this.doubleClickTimeout = 0L;
      }

   }

   public Input(int var1) {
      this.init(var1);
   }

   public int getControllerCount() {
      try {
         this.initControllers();
      } catch (SlickException var2) {
         throw new RuntimeException("Failed to initialise controllers");
      }

      return controllers.size();
   }

   public void consumeEvent() {
      this.consumed = true;
   }

   public static void disableControllers() {
      controllersInited = true;
   }

   public void removeKeyListener(KeyListener var1) {
      this.keyListeners.remove(var1);
      if (!this.mouseListeners.contains(var1) && !this.controllerListeners.contains(var1)) {
         this.allListeners.remove(var1);
      }

   }

   public void addMouseListener(MouseListener var1) {
      this.mouseListenersToAdd.add(var1);
   }

   public int getMouseY() {
      return (int)((float)(this.height - Mouse.getY()) * this.scaleY + this.yoffset);
   }

   public void setOffset(float var1, float var2) {
      this.xoffset = var1;
      this.yoffset = var2;
   }

   public void pause() {
      this.paused = true;
      this.clearKeyPressedRecord();
      this.clearMousePressedRecord();
      this.clearControlPressedRecord();
   }

   public void resetInputTransform() {
      this.setOffset(0.0F, 0.0F);
      this.setScale(1.0F, 1.0F);
   }

   public void addKeyListener(KeyListener var1) {
      this.keyListenersToAdd.add(var1);
   }

   public void removeMouseListener(MouseListener var1) {
      this.mouseListeners.remove(var1);
      if (!this.controllerListeners.contains(var1) && !this.keyListeners.contains(var1)) {
         this.allListeners.remove(var1);
      }

   }

   public void poll(int var1, int var2) {
      if (this.paused) {
         this.clearControlPressedRecord();
         this.clearKeyPressedRecord();
         this.clearMousePressedRecord();

         while(Keyboard.next()) {
         }

         while(Mouse.next()) {
         }

      } else {
         if (!Display.isActive()) {
            this.clearControlPressedRecord();
            this.clearKeyPressedRecord();
            this.clearMousePressedRecord();
         }

         int var3;
         for(var3 = 0; var3 < this.keyListenersToAdd.size(); ++var3) {
            this.addKeyListenerImpl((KeyListener)this.keyListenersToAdd.get(var3));
         }

         this.keyListenersToAdd.clear();

         for(var3 = 0; var3 < this.mouseListenersToAdd.size(); ++var3) {
            this.addMouseListenerImpl((MouseListener)this.mouseListenersToAdd.get(var3));
         }

         this.mouseListenersToAdd.clear();
         if (this.doubleClickTimeout != 0L && System.currentTimeMillis() > this.doubleClickTimeout) {
            this.doubleClickTimeout = 0L;
         }

         this.height = var2;
         Iterator var9 = this.allListeners.iterator();

         while(var9.hasNext()) {
            ControlledInputReciever var4 = (ControlledInputReciever)var9.next();
            var4.inputStarted();
         }

         while(true) {
            int var5;
            KeyListener var6;
            int var10;
            while(Keyboard.next()) {
               if (Keyboard.getEventKeyState()) {
                  var10 = this.resolveEventKey(Keyboard.getEventKey(), Keyboard.getEventCharacter());
                  this.keys[var10] = Keyboard.getEventCharacter();
                  this.pressed[var10] = true;
                  this.nextRepeat[var10] = System.currentTimeMillis() + (long)this.keyRepeatInitial;
                  this.consumed = false;

                  for(var5 = 0; var5 < this.keyListeners.size(); ++var5) {
                     var6 = (KeyListener)this.keyListeners.get(var5);
                     if (var6.isAcceptingInput()) {
                        var6.keyPressed(var10, Keyboard.getEventCharacter());
                        if (this.consumed) {
                           break;
                        }
                     }
                  }
               } else {
                  var10 = this.resolveEventKey(Keyboard.getEventKey(), Keyboard.getEventCharacter());
                  this.nextRepeat[var10] = 0L;
                  this.consumed = false;

                  for(var5 = 0; var5 < this.keyListeners.size(); ++var5) {
                     var6 = (KeyListener)this.keyListeners.get(var5);
                     if (var6.isAcceptingInput()) {
                        var6.keyReleased(var10, this.keys[var10]);
                        if (this.consumed) {
                           break;
                        }
                     }
                  }
               }
            }

            while(true) {
               while(true) {
                  MouseListener var11;
                  int var13;
                  while(Mouse.next()) {
                     if (Mouse.getEventButton() >= 0) {
                        if (Mouse.getEventButtonState()) {
                           this.consumed = false;
                           this.mousePressed[Mouse.getEventButton()] = true;
                           this.pressedX = (int)(this.xoffset + (float)Mouse.getEventX() * this.scaleX);
                           this.pressedY = (int)(this.yoffset + (float)(var2 - Mouse.getEventY()) * this.scaleY);

                           for(var10 = 0; var10 < this.mouseListeners.size(); ++var10) {
                              var11 = (MouseListener)this.mouseListeners.get(var10);
                              if (var11.isAcceptingInput()) {
                                 var11.mousePressed(Mouse.getEventButton(), this.pressedX, this.pressedY);
                                 if (this.consumed) {
                                    break;
                                 }
                              }
                           }
                        } else {
                           this.consumed = false;
                           this.mousePressed[Mouse.getEventButton()] = false;
                           var10 = (int)(this.xoffset + (float)Mouse.getEventX() * this.scaleX);
                           var5 = (int)(this.yoffset + (float)(var2 - Mouse.getEventY()) * this.scaleY);
                           if (this.pressedX != -1 && this.pressedY != -1 && Math.abs(this.pressedX - var10) < this.mouseClickTolerance && Math.abs(this.pressedY - var5) < this.mouseClickTolerance) {
                              this.considerDoubleClick(Mouse.getEventButton(), var10, var5);
                              this.pressedX = this.pressedY = -1;
                           }

                           for(var13 = 0; var13 < this.mouseListeners.size(); ++var13) {
                              MouseListener var7 = (MouseListener)this.mouseListeners.get(var13);
                              if (var7.isAcceptingInput()) {
                                 var7.mouseReleased(Mouse.getEventButton(), var10, var5);
                                 if (this.consumed) {
                                    break;
                                 }
                              }
                           }
                        }
                     } else {
                        if (Mouse.isGrabbed() && this.displayActive && (Mouse.getEventDX() != 0 || Mouse.getEventDY() != 0)) {
                           this.consumed = false;

                           for(var10 = 0; var10 < this.mouseListeners.size(); ++var10) {
                              var11 = (MouseListener)this.mouseListeners.get(var10);
                              if (var11.isAcceptingInput()) {
                                 if (this.anyMouseDown()) {
                                    var11.mouseDragged(0, 0, Mouse.getEventDX(), -Mouse.getEventDY());
                                 } else {
                                    var11.mouseMoved(0, 0, Mouse.getEventDX(), -Mouse.getEventDY());
                                 }

                                 if (this.consumed) {
                                    break;
                                 }
                              }
                           }
                        }

                        var10 = Mouse.getEventDWheel();
                        this.wheel += var10;
                        if (var10 != 0) {
                           this.consumed = false;

                           for(var5 = 0; var5 < this.mouseListeners.size(); ++var5) {
                              MouseListener var12 = (MouseListener)this.mouseListeners.get(var5);
                              if (var12.isAcceptingInput()) {
                                 var12.mouseWheelMoved(var10);
                                 if (this.consumed) {
                                    break;
                                 }
                              }
                           }
                        }
                     }
                  }

                  if (this.displayActive && !Mouse.isGrabbed()) {
                     if (this.lastMouseX != this.getMouseX() || this.lastMouseY != this.getMouseY()) {
                        this.consumed = false;

                        for(var10 = 0; var10 < this.mouseListeners.size(); ++var10) {
                           var11 = (MouseListener)this.mouseListeners.get(var10);
                           if (var11.isAcceptingInput()) {
                              if (this.anyMouseDown()) {
                                 var11.mouseDragged(this.lastMouseX, this.lastMouseY, this.getMouseX(), this.getMouseY());
                              } else {
                                 var11.mouseMoved(this.lastMouseX, this.lastMouseY, this.getMouseX(), this.getMouseY());
                              }

                              if (this.consumed) {
                                 break;
                              }
                           }
                        }

                        this.lastMouseX = this.getMouseX();
                        this.lastMouseY = this.getMouseY();
                     }
                  } else {
                     this.lastMouseX = this.getMouseX();
                     this.lastMouseY = this.getMouseY();
                  }

                  if (controllersInited) {
                     for(var10 = 0; var10 < this.getControllerCount(); ++var10) {
                        var5 = ((Controller)controllers.get(var10)).getButtonCount() + 3;
                        var5 = Math.min(var5, 24);

                        for(var13 = 0; var13 <= var5; ++var13) {
                           if (this.controls[var10][var13] && !this.isControlDwn(var13, var10)) {
                              this.controls[var10][var13] = false;
                              this.fireControlRelease(var13, var10);
                           } else if (!this.controls[var10][var13] && this.isControlDwn(var13, var10)) {
                              this.controllerPressed[var10][var13] = true;
                              this.controls[var10][var13] = true;
                              this.fireControlPress(var13, var10);
                           }
                        }
                     }
                  }

                  if (this.keyRepeat) {
                     for(var10 = 0; var10 < 1024; ++var10) {
                        if (this.pressed[var10] && this.nextRepeat[var10] != 0L && System.currentTimeMillis() > this.nextRepeat[var10]) {
                           this.nextRepeat[var10] = System.currentTimeMillis() + (long)this.keyRepeatInterval;
                           this.consumed = false;

                           for(var5 = 0; var5 < this.keyListeners.size(); ++var5) {
                              var6 = (KeyListener)this.keyListeners.get(var5);
                              if (var6.isAcceptingInput()) {
                                 var6.keyPressed(var10, this.keys[var10]);
                                 if (this.consumed) {
                                    break;
                                 }
                              }
                           }
                        }
                     }
                  }

                  Iterator var14 = this.allListeners.iterator();

                  while(var14.hasNext()) {
                     ControlledInputReciever var15 = (ControlledInputReciever)var14.next();
                     var15.inputEnded();
                  }

                  if (Display.isCreated()) {
                     this.displayActive = Display.isActive();
                  }

                  return;
               }
            }
         }
      }
   }

   public boolean isButtonPressed(int var1, int var2) {
      if (var2 >= this.getControllerCount()) {
         return false;
      } else if (var2 == -1) {
         for(int var3 = 0; var3 < controllers.size(); ++var3) {
            if (this.isButtonPressed(var1, var3)) {
               return true;
            }
         }

         return false;
      } else {
         return ((Controller)controllers.get(var2)).isButtonPressed(var1);
      }
   }

   public boolean isControlPressed(int var1, int var2) {
      if (this.controllerPressed[var2][var1]) {
         this.controllerPressed[var2][var1] = false;
         return true;
      } else {
         return false;
      }
   }

   public boolean isControllerRight(int var1) {
      if (var1 >= this.getControllerCount()) {
         return false;
      } else if (var1 == -1) {
         for(int var2 = 0; var2 < controllers.size(); ++var2) {
            if (this.isControllerRight(var2)) {
               return true;
            }
         }

         return false;
      } else {
         return ((Controller)controllers.get(var1)).getXAxisValue() > 0.5F || ((Controller)controllers.get(var1)).getPovX() > 0.5F;
      }
   }

   private void fireControlPress(int var1, int var2) {
      this.consumed = false;

      for(int var3 = 0; var3 < this.controllerListeners.size(); ++var3) {
         ControllerListener var4 = (ControllerListener)this.controllerListeners.get(var3);
         if (var4.isAcceptingInput()) {
            switch(var1) {
            case 0:
               var4.controllerLeftPressed(var2);
               break;
            case 1:
               var4.controllerRightPressed(var2);
               break;
            case 2:
               var4.controllerUpPressed(var2);
               break;
            case 3:
               var4.controllerDownPressed(var2);
               break;
            default:
               var4.controllerButtonPressed(var2, var1 - 4 + 1);
            }

            if (this.consumed) {
               break;
            }
         }
      }

   }

   public void removeAllControllerListeners() {
      this.allListeners.removeAll(this.controllerListeners);
      this.controllerListeners.clear();
   }

   public void clearMousePressedRecord() {
      Arrays.fill(this.mousePressed, false);
   }

   private void addKeyListenerImpl(KeyListener var1) {
      if (!this.keyListeners.contains(var1)) {
         this.keyListeners.add(var1);
         this.allListeners.add(var1);
      }
   }

   public boolean isMousePressed(int var1) {
      if (this.mousePressed[var1]) {
         this.mousePressed[var1] = false;
         return true;
      } else {
         return false;
      }
   }

   public boolean isKeyRepeatEnabled() {
      return Keyboard.areRepeatEventsEnabled();
   }

   public boolean isMouseButtonDown(int var1) {
      return Mouse.isButtonDown(var1);
   }

   public void clearKeyPressedRecord() {
      Arrays.fill(this.pressed, false);
   }

   private boolean isControlDwn(int var1, int var2) {
      switch(var1) {
      case 0:
         return this.isControllerLeft(var2);
      case 1:
         return this.isControllerRight(var2);
      case 2:
         return this.isControllerUp(var2);
      case 3:
         return this.isControllerDown(var2);
      default:
         if (var1 >= 4) {
            return this.isButtonPressed(var1 - 4, var2);
         } else {
            throw new RuntimeException("Unknown control index");
         }
      }
   }

   public boolean isButton3Pressed(int var1) {
      return this.isButtonPressed(2, var1);
   }

   void init(int var1) {
      this.height = var1;
      this.lastMouseX = this.getMouseX();
      this.lastMouseY = this.getMouseY();
   }

   public void clearControlPressedRecord() {
      for(int var1 = 0; var1 < controllers.size(); ++var1) {
         Arrays.fill(this.controllerPressed[var1], false);
      }

   }

   public void removeAllKeyListeners() {
      this.allListeners.removeAll(this.keyListeners);
      this.keyListeners.clear();
   }

   public void removeControllerListener(ControllerListener var1) {
      this.controllerListeners.remove(var1);
      if (!this.mouseListeners.contains(var1) && !this.keyListeners.contains(var1)) {
         this.allListeners.remove(var1);
      }

   }

   public int getAxisCount(int var1) {
      return ((Controller)controllers.get(var1)).getAxisCount();
   }

   public void enableKeyRepeat() {
      Keyboard.enableRepeatEvents(true);
   }

   public boolean isKeyDown(int var1) {
      return Keyboard.isKeyDown(var1);
   }

   public void setMouseClickTolerance(int var1) {
      this.mouseClickTolerance = var1;
   }

   public void addControllerListener(ControllerListener var1) {
      if (!this.controllerListeners.contains(var1)) {
         this.controllerListeners.add(var1);
         this.allListeners.add(var1);
      }
   }

   public float getAxisValue(int var1, int var2) {
      return ((Controller)controllers.get(var1)).getAxisValue(var2);
   }

   public boolean isControlPressed(int var1) {
      return this.isControlPressed(var1, 0);
   }

   private void fireMouseClicked(int var1, int var2, int var3, int var4) {
      this.consumed = false;

      for(int var5 = 0; var5 < this.mouseListeners.size(); ++var5) {
         MouseListener var6 = (MouseListener)this.mouseListeners.get(var5);
         if (var6.isAcceptingInput()) {
            var6.mouseClicked(var1, var2, var3, var4);
            if (this.consumed) {
               break;
            }
         }
      }

   }

   public void setScale(float var1, float var2) {
      this.scaleX = var1;
      this.scaleY = var2;
   }

   private boolean anyMouseDown() {
      for(int var1 = 0; var1 < 3; ++var1) {
         if (Mouse.isButtonDown(var1)) {
            return true;
         }
      }

      return false;
   }

   private int resolveEventKey(int var1, char var2) {
      return var2 != '=' && var1 != 0 ? var1 : 13;
   }

   public boolean isKeyPressed(int var1) {
      if (this.pressed[var1]) {
         this.pressed[var1] = false;
         return true;
      } else {
         return false;
      }
   }

   public void addListener(InputListener var1) {
      this.addKeyListener(var1);
      this.addMouseListener(var1);
      this.addControllerListener(var1);
   }

   public boolean isButton2Pressed(int var1) {
      return this.isButtonPressed(1, var1);
   }

   public void resume() {
      this.paused = false;
   }

   public void setDoubleClickInterval(int var1) {
      this.doubleClickDelay = var1;
   }

   public static String getKeyName(int var0) {
      return Keyboard.getKeyName(var0);
   }

   public boolean isButton1Pressed(int var1) {
      return this.isButtonPressed(0, var1);
   }

   public int getAbsoluteMouseX() {
      return Mouse.getX();
   }

   public void addPrimaryListener(InputListener var1) {
      this.removeListener(var1);
      this.keyListeners.add(0, var1);
      this.mouseListeners.add(0, var1);
      this.controllerListeners.add(0, var1);
      this.allListeners.add(var1);
   }

   public boolean isControllerDown(int var1) {
      if (var1 >= this.getControllerCount()) {
         return false;
      } else if (var1 == -1) {
         for(int var2 = 0; var2 < controllers.size(); ++var2) {
            if (this.isControllerDown(var2)) {
               return true;
            }
         }

         return false;
      } else {
         return ((Controller)controllers.get(var1)).getYAxisValue() > 0.5F || ((Controller)controllers.get(var1)).getPovY() > 0.5F;
      }
   }

   public void removeAllMouseListeners() {
      this.allListeners.removeAll(this.mouseListeners);
      this.mouseListeners.clear();
   }

   public int getAbsoluteMouseY() {
      return this.height - Mouse.getY();
   }

   private void addMouseListenerImpl(MouseListener var1) {
      if (!this.mouseListeners.contains(var1)) {
         this.mouseListeners.add(var1);
         this.allListeners.add(var1);
      }
   }

   public int getMouseX() {
      return (int)((float)Mouse.getX() * this.scaleX + this.xoffset);
   }

   /** @deprecated */
   public void enableKeyRepeat(int var1, int var2) {
      Keyboard.enableRepeatEvents(true);
   }

   public String getAxisName(int var1, int var2) {
      return ((Controller)controllers.get(var1)).getAxisName(var2);
   }

   private class NullOutputStream extends OutputStream {
      public void write(int var1) throws IOException {
      }
   }
}
