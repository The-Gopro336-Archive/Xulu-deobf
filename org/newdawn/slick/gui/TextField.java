package org.newdawn.slick.gui;

import org.lwjgl.Sys;
import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;

public class TextField extends AbstractComponent {
   // $FF: synthetic field
   private boolean visibleCursor;
   // $FF: synthetic field
   protected int y;
   // $FF: synthetic field
   private int lastKey;
   // $FF: synthetic field
   private String value;
   // $FF: synthetic field
   protected int x;
   // $FF: synthetic field
   private int maxCharacter;
   // $FF: synthetic field
   private Color border;
   // $FF: synthetic field
   private Color text;
   // $FF: synthetic field
   private char lastChar;
   // $FF: synthetic field
   private static final int KEY_REPEAT_INTERVAL = 50;
   // $FF: synthetic field
   private long repeatTimer;
   // $FF: synthetic field
   private Color background;
   // $FF: synthetic field
   private int height;
   // $FF: synthetic field
   private String oldText;
   // $FF: synthetic field
   private int width;
   // $FF: synthetic field
   private Font font;
   // $FF: synthetic field
   private int cursorPos;
   // $FF: synthetic field
   private boolean consume;
   // $FF: synthetic field
   private static final int INITIAL_KEY_REPEAT_INTERVAL = 400;
   // $FF: synthetic field
   private int oldCursorPos;

   public int getWidth() {
      return this.width;
   }

   public int getX() {
      return this.x;
   }

   public String getText() {
      return this.value;
   }

   public void setBorderColor(Color var1) {
      this.border = var1;
   }

   public int getHeight() {
      return this.height;
   }

   public void setFocus(boolean var1) {
      this.lastKey = -1;
      super.setFocus(var1);
   }

   public int getY() {
      return this.y;
   }

   public TextField(GUIContext var1, Font var2, int var3, int var4, int var5, int var6, ComponentListener var7) {
      this(var1, var2, var3, var4, var5, var6);
      this.addListener(var7);
   }

   public void setLocation(int var1, int var2) {
      this.x = var1;
      this.y = var2;
   }

   public void setBackgroundColor(Color var1) {
      this.background = var1;
   }

   public void setCursorPos(int var1) {
      this.cursorPos = var1;
      if (this.cursorPos > this.value.length()) {
         this.cursorPos = this.value.length();
      }

   }

   protected void doUndo(int var1, String var2) {
      if (var2 != null) {
         this.setText(var2);
         this.setCursorPos(var1);
      }

   }

   public void setConsumeEvents(boolean var1) {
      this.consume = var1;
   }

   public void setCursorVisible(boolean var1) {
      this.visibleCursor = var1;
   }

   public void setText(String var1) {
      this.value = var1;
      if (this.cursorPos > var1.length()) {
         this.cursorPos = var1.length();
      }

   }

   public void render(GUIContext var1, Graphics var2) {
      if (this.lastKey != -1) {
         if (this.input.isKeyDown(this.lastKey)) {
            if (this.repeatTimer < System.currentTimeMillis()) {
               this.repeatTimer = System.currentTimeMillis() + 50L;
               this.keyPressed(this.lastKey, this.lastChar);
            }
         } else {
            this.lastKey = -1;
         }
      }

      Rectangle var3 = var2.getClip();
      var2.setWorldClip((float)this.x, (float)this.y, (float)this.width, (float)this.height);
      Color var4 = var2.getColor();
      if (this.background != null) {
         var2.setColor(this.background.multiply(var4));
         var2.fillRect((float)this.x, (float)this.y, (float)this.width, (float)this.height);
      }

      var2.setColor(this.text.multiply(var4));
      Font var5 = var2.getFont();
      int var6 = this.font.getWidth(this.value.substring(0, this.cursorPos));
      int var7 = 0;
      if (var6 > this.width) {
         var7 = this.width - var6 - this.font.getWidth("_");
      }

      var2.translate((float)(var7 + 2), 0.0F);
      var2.setFont(this.font);
      var2.drawString(this.value, (float)(this.x + 1), (float)(this.y + 1));
      if (this.hasFocus() && this.visibleCursor) {
         var2.drawString("_", (float)(this.x + 1 + var6 + 2), (float)(this.y + 1));
      }

      var2.translate((float)(-var7 - 2), 0.0F);
      if (this.border != null) {
         var2.setColor(this.border.multiply(var4));
         var2.drawRect((float)this.x, (float)this.y, (float)this.width, (float)this.height);
      }

      var2.setColor(var4);
      var2.setFont(var5);
      var2.clearWorldClip();
      var2.setClip(var3);
   }

   public void deactivate() {
      this.setFocus(false);
   }

   public void setTextColor(Color var1) {
      this.text = var1;
   }

   protected void recordOldPosition() {
      this.oldText = this.getText();
      this.oldCursorPos = this.cursorPos;
   }

   public TextField(GUIContext var1, Font var2, int var3, int var4, int var5, int var6) {
      super(var1);
      this.maxCharacter = 10000;
      this.value = "";
      this.border = Color.white;
      this.text = Color.white;
      this.background = new Color(0.0F, 0.0F, 0.0F, 0.5F);
      this.visibleCursor = true;
      this.lastKey = -1;
      this.lastChar = 0;
      this.consume = true;
      this.font = var2;
      this.setLocation(var3, var4);
      this.width = var5;
      this.height = var6;
   }

   public void keyPressed(int var1, char var2) {
      if (this.hasFocus()) {
         if (var1 != -1) {
            label129: {
               if (var1 == 47 && (this.input.isKeyDown(29) || this.input.isKeyDown(157))) {
                  String var3 = Sys.getClipboard();
                  if (var3 != null) {
                     this.doPaste(var3);
                  }

                  return;
               }

               if (var1 != 44 || !this.input.isKeyDown(29) && !this.input.isKeyDown(157)) {
                  if (!this.input.isKeyDown(29) && !this.input.isKeyDown(157)) {
                     if (!this.input.isKeyDown(56) && !this.input.isKeyDown(184)) {
                        break label129;
                     }

                     return;
                  }

                  return;
               }

               if (this.oldText != null) {
                  this.doUndo(this.oldCursorPos, this.oldText);
               }

               return;
            }
         }

         if (this.lastKey != var1) {
            this.lastKey = var1;
            this.repeatTimer = System.currentTimeMillis() + 400L;
         } else {
            this.repeatTimer = System.currentTimeMillis() + 50L;
         }

         this.lastChar = var2;
         if (var1 == 203) {
            if (this.cursorPos > 0) {
               --this.cursorPos;
            }

            if (this.consume) {
               this.container.getInput().consumeEvent();
            }
         } else if (var1 == 205) {
            if (this.cursorPos < this.value.length()) {
               ++this.cursorPos;
            }

            if (this.consume) {
               this.container.getInput().consumeEvent();
            }
         } else if (var1 == 14) {
            if (this.cursorPos > 0 && this.value.length() > 0) {
               if (this.cursorPos < this.value.length()) {
                  this.value = String.valueOf((new StringBuilder()).append(this.value.substring(0, this.cursorPos - 1)).append(this.value.substring(this.cursorPos)));
               } else {
                  this.value = this.value.substring(0, this.cursorPos - 1);
               }

               --this.cursorPos;
            }

            if (this.consume) {
               this.container.getInput().consumeEvent();
            }
         } else if (var1 == 211) {
            if (this.value.length() > this.cursorPos) {
               this.value = String.valueOf((new StringBuilder()).append(this.value.substring(0, this.cursorPos)).append(this.value.substring(this.cursorPos + 1)));
            }

            if (this.consume) {
               this.container.getInput().consumeEvent();
            }
         } else if (var2 < 127 && var2 > 31 && this.value.length() < this.maxCharacter) {
            if (this.cursorPos < this.value.length()) {
               this.value = String.valueOf((new StringBuilder()).append(this.value.substring(0, this.cursorPos)).append(var2).append(this.value.substring(this.cursorPos)));
            } else {
               this.value = String.valueOf((new StringBuilder()).append(this.value.substring(0, this.cursorPos)).append(var2));
            }

            ++this.cursorPos;
            if (this.consume) {
               this.container.getInput().consumeEvent();
            }
         } else if (var1 == 28) {
            this.notifyListeners();
            if (this.consume) {
               this.container.getInput().consumeEvent();
            }
         }
      }

   }

   protected void doPaste(String var1) {
      this.recordOldPosition();

      for(int var2 = 0; var2 < var1.length(); ++var2) {
         this.keyPressed(-1, var1.charAt(var2));
      }

   }

   public void setMaxLength(int var1) {
      this.maxCharacter = var1;
      if (this.value.length() > this.maxCharacter) {
         this.value = this.value.substring(0, this.maxCharacter);
      }

   }
}
