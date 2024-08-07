package org.newdawn.slick.gui;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.Sound;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

public class MouseOverArea extends AbstractComponent {
   // $FF: synthetic field
   private Color mouseDownColor;
   // $FF: synthetic field
   private Image normalImage;
   // $FF: synthetic field
   private Sound mouseDownSound;
   // $FF: synthetic field
   private boolean mouseUp;
   // $FF: synthetic field
   private Color currentColor;
   // $FF: synthetic field
   private static final int MOUSE_DOWN = 2;
   // $FF: synthetic field
   private boolean mouseDown;
   // $FF: synthetic field
   private static final int NORMAL = 1;
   // $FF: synthetic field
   private boolean over;
   // $FF: synthetic field
   private Color normalColor;
   // $FF: synthetic field
   private int state;
   // $FF: synthetic field
   private Image mouseDownImage;
   // $FF: synthetic field
   private Image currentImage;
   // $FF: synthetic field
   private Image mouseOverImage;
   // $FF: synthetic field
   private Sound mouseOverSound;
   // $FF: synthetic field
   private Shape area;
   // $FF: synthetic field
   private Color mouseOverColor;
   // $FF: synthetic field
   private static final int MOUSE_OVER = 3;

   public void setMouseDownImage(Image var1) {
      this.mouseDownImage = var1;
   }

   public void setLocation(int var1, int var2) {
      this.setLocation((float)var1, (float)var2);
   }

   public void setMouseOverImage(Image var1) {
      this.mouseOverImage = var1;
   }

   public void setMouseOverSound(Sound var1) {
      this.mouseOverSound = var1;
   }

   public void mouseMoved(int var1, int var2, int var3, int var4) {
      this.over = this.area.contains((float)var3, (float)var4);
   }

   public int getX() {
      return (int)this.area.getX();
   }

   public int getWidth() {
      return (int)(this.area.getMaxX() - this.area.getX());
   }

   public void mouseDragged(int var1, int var2, int var3, int var4) {
      this.mouseMoved(var1, var2, var3, var4);
   }

   public int getHeight() {
      return (int)(this.area.getMaxY() - this.area.getY());
   }

   public MouseOverArea(GUIContext var1, Image var2, int var3, int var4, int var5, int var6) {
      this(var1, var2, new Rectangle((float)var3, (float)var4, (float)var5, (float)var6));
   }

   public MouseOverArea(GUIContext var1, Image var2, int var3, int var4, ComponentListener var5) {
      this(var1, var2, var3, var4, var2.getWidth(), var2.getHeight());
      this.addListener(var5);
   }

   public void setMouseDownSound(Sound var1) {
      this.mouseDownSound = var1;
   }

   public void setNormalImage(Image var1) {
      this.normalImage = var1;
   }

   public void setMouseOverColor(Color var1) {
      this.mouseOverColor = var1;
   }

   public int getY() {
      return (int)this.area.getY();
   }

   private void updateImage() {
      if (!this.over) {
         this.currentImage = this.normalImage;
         this.currentColor = this.normalColor;
         this.state = 1;
         this.mouseUp = false;
      } else {
         if (this.mouseDown) {
            if (this.state != 2 && this.mouseUp) {
               if (this.mouseDownSound != null) {
                  this.mouseDownSound.play();
               }

               this.currentImage = this.mouseDownImage;
               this.currentColor = this.mouseDownColor;
               this.state = 2;
               this.notifyListeners();
               this.mouseUp = false;
            }

            return;
         }

         this.mouseUp = true;
         if (this.state != 3) {
            if (this.mouseOverSound != null) {
               this.mouseOverSound.play();
            }

            this.currentImage = this.mouseOverImage;
            this.currentColor = this.mouseOverColor;
            this.state = 3;
         }
      }

      this.mouseDown = false;
      this.state = 1;
   }

   public void mousePressed(int var1, int var2, int var3) {
      this.over = this.area.contains((float)var2, (float)var3);
      if (var1 == 0) {
         this.mouseDown = true;
      }

   }

   public void render(GUIContext var1, Graphics var2) {
      if (this.currentImage != null) {
         int var3 = (int)(this.area.getX() + (float)((this.getWidth() - this.currentImage.getWidth()) / 2));
         int var4 = (int)(this.area.getY() + (float)((this.getHeight() - this.currentImage.getHeight()) / 2));
         this.currentImage.draw((float)var3, (float)var4, this.currentColor);
      } else {
         var2.setColor(this.currentColor);
         var2.fill(this.area);
      }

      this.updateImage();
   }

   public void setNormalColor(Color var1) {
      this.normalColor = var1;
   }

   public boolean isMouseOver() {
      return this.over;
   }

   public void setX(float var1) {
      this.area.setX(var1);
   }

   public void setY(float var1) {
      this.area.setY(var1);
   }

   public MouseOverArea(GUIContext var1, Image var2, int var3, int var4, int var5, int var6, ComponentListener var7) {
      this(var1, var2, var3, var4, var5, var6);
      this.addListener(var7);
   }

   public void setMouseDownColor(Color var1) {
      this.mouseDownColor = var1;
   }

   public MouseOverArea(GUIContext var1, Image var2, int var3, int var4) {
      this(var1, var2, var3, var4, var2.getWidth(), var2.getHeight());
   }

   public MouseOverArea(GUIContext var1, Image var2, Shape var3) {
      super(var1);
      this.normalColor = Color.white;
      this.mouseOverColor = Color.white;
      this.mouseDownColor = Color.white;
      this.state = 1;
      this.area = var3;
      this.normalImage = var2;
      this.currentImage = var2;
      this.mouseOverImage = var2;
      this.mouseDownImage = var2;
      this.currentColor = this.normalColor;
      this.state = 1;
      Input var4 = var1.getInput();
      this.over = this.area.contains((float)var4.getMouseX(), (float)var4.getMouseY());
      this.mouseDown = var4.isMouseButtonDown(0);
      this.updateImage();
   }

   public void mouseReleased(int var1, int var2, int var3) {
      this.over = this.area.contains((float)var2, (float)var3);
      if (var1 == 0) {
         this.mouseDown = false;
      }

   }

   public void setLocation(float var1, float var2) {
      if (this.area != null) {
         this.area.setX(var1);
         this.area.setY(var2);
      }

   }
}
