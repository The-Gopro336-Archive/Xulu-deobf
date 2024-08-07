package org.newdawn.slick;

import java.awt.Canvas;
import javax.swing.SwingUtilities;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.util.Log;

public class CanvasGameContainer extends Canvas {
   // $FF: synthetic field
   protected CanvasGameContainer.Container container;
   // $FF: synthetic field
   protected Game game;

   public GameContainer getContainer() {
      return this.container;
   }

   public CanvasGameContainer(Game var1) throws SlickException {
      this(var1, false);
   }

   public void dispose() {
   }

   private void scheduleUpdate() {
      if (this.isVisible()) {
         SwingUtilities.invokeLater(new Runnable() {
            public void run() {
               try {
                  CanvasGameContainer.this.container.gameLoop();
               } catch (SlickException var3) {
                  var3.printStackTrace();
               }

               CanvasGameContainer.this.container.checkDimensions();
               CanvasGameContainer.this.scheduleUpdate();
            }
         });
      }
   }

   public void start() throws SlickException {
      SwingUtilities.invokeLater(new Runnable() {
         public void run() {
            try {
               Input.disableControllers();

               try {
                  Display.setParent(CanvasGameContainer.this);
               } catch (LWJGLException var2) {
                  throw new SlickException("Failed to setParent of canvas", var2);
               }

               CanvasGameContainer.this.container.setup();
               CanvasGameContainer.this.scheduleUpdate();
            } catch (SlickException var3) {
               var3.printStackTrace();
               System.exit(0);
            }

         }
      });
   }

   public CanvasGameContainer(Game var1, boolean var2) throws SlickException {
      this.game = var1;
      this.setIgnoreRepaint(true);
      this.requestFocus();
      this.setSize(500, 500);
      this.container = new CanvasGameContainer.Container(var1, var2);
      this.container.setForceExit(false);
   }

   private class Container extends AppGameContainer {
      public int getWidth() {
         return CanvasGameContainer.this.getWidth();
      }

      protected void updateFPS() {
         super.updateFPS();
      }

      protected boolean running() {
         return super.running() && CanvasGameContainer.this.isDisplayable();
      }

      public int getHeight() {
         return CanvasGameContainer.this.getHeight();
      }

      public Container(Game var2, boolean var3) throws SlickException {
         super(var2, CanvasGameContainer.this.getWidth(), CanvasGameContainer.this.getHeight(), false);
         this.width = CanvasGameContainer.this.getWidth();
         this.height = CanvasGameContainer.this.getHeight();
         if (var3) {
            enableSharedContext();
         }

      }

      public void checkDimensions() {
         if (this.width != CanvasGameContainer.this.getWidth() || this.height != CanvasGameContainer.this.getHeight()) {
            try {
               this.setDisplayMode(CanvasGameContainer.this.getWidth(), CanvasGameContainer.this.getHeight(), false);
            } catch (SlickException var2) {
               Log.error((Throwable)var2);
            }
         }

      }
   }
}
