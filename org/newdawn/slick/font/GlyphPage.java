package org.newdawn.slick.font;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.font.FontRenderContext;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.Effect;
import org.newdawn.slick.opengl.TextureImpl;
import org.newdawn.slick.opengl.renderer.Renderer;
import org.newdawn.slick.opengl.renderer.SGL;

public class GlyphPage {
   // $FF: synthetic field
   private static ByteBuffer scratchByteBuffer = ByteBuffer.allocateDirect(262144);
   // $FF: synthetic field
   private final UnicodeFont unicodeFont;
   // $FF: synthetic field
   public static final int MAX_GLYPH_SIZE = 256;
   // $FF: synthetic field
   private int pageX;
   // $FF: synthetic field
   public static FontRenderContext renderContext;
   // $FF: synthetic field
   private final List pageGlyphs = new ArrayList(32);
   // $FF: synthetic field
   private int pageY;
   // $FF: synthetic field
   private static IntBuffer scratchIntBuffer;
   // $FF: synthetic field
   private final int pageWidth;
   // $FF: synthetic field
   private int rowHeight;
   // $FF: synthetic field
   private static final SGL GL = Renderer.get();
   // $FF: synthetic field
   private final Image pageImage;
   // $FF: synthetic field
   private static Graphics2D scratchGraphics;
   // $FF: synthetic field
   private static BufferedImage scratchImage;
   // $FF: synthetic field
   private final int pageHeight;
   // $FF: synthetic field
   private boolean orderAscending;

   public GlyphPage(UnicodeFont var1, int var2, int var3) throws SlickException {
      this.unicodeFont = var1;
      this.pageWidth = var2;
      this.pageHeight = var3;
      this.pageImage = new Image(var2, var3);
   }

   public int loadGlyphs(List var1, int var2) throws SlickException {
      int var3;
      if (this.rowHeight != 0 && var2 == -1) {
         var3 = this.pageX;
         int var4 = this.pageY;
         int var5 = this.rowHeight;

         int var8;
         for(Iterator var6 = this.getIterator(var1); var6.hasNext(); var3 += var8) {
            Glyph var7 = (Glyph)var6.next();
            var8 = var7.getWidth();
            int var9 = var7.getHeight();
            if (var3 + var8 >= this.pageWidth) {
               var3 = 0;
               var4 += var5;
               var5 = var9;
            } else if (var9 > var5) {
               var5 = var9;
            }

            if (var4 + var5 >= this.pageWidth) {
               return 0;
            }
         }
      }

      Color.white.bind();
      this.pageImage.bind();
      var3 = 0;
      Iterator var10 = this.getIterator(var1);

      while(var10.hasNext()) {
         Glyph var11 = (Glyph)var10.next();
         int var12 = Math.min(256, var11.getWidth());
         int var13 = Math.min(256, var11.getHeight());
         if (this.rowHeight == 0) {
            this.rowHeight = var13;
         } else if (this.pageX + var12 >= this.pageWidth) {
            if (this.pageY + this.rowHeight + var13 >= this.pageHeight) {
               break;
            }

            this.pageX = 0;
            this.pageY += this.rowHeight;
            this.rowHeight = var13;
         } else if (var13 > this.rowHeight) {
            if (this.pageY + var13 >= this.pageHeight) {
               break;
            }

            this.rowHeight = var13;
         }

         this.renderGlyph(var11, var12, var13);
         this.pageGlyphs.add(var11);
         this.pageX += var12;
         var10.remove();
         ++var3;
         if (var3 == var2) {
            this.orderAscending = !this.orderAscending;
            break;
         }
      }

      TextureImpl.bindNone();
      this.orderAscending = !this.orderAscending;
      return var3;
   }

   static {
      scratchByteBuffer.order(ByteOrder.LITTLE_ENDIAN);
      scratchIntBuffer = scratchByteBuffer.asIntBuffer();
      scratchImage = new BufferedImage(256, 256, 2);
      scratchGraphics = (Graphics2D)scratchImage.getGraphics();
      scratchGraphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
      scratchGraphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
      scratchGraphics.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
      renderContext = scratchGraphics.getFontRenderContext();
   }

   private Iterator getIterator(List var1) {
      if (this.orderAscending) {
         return var1.iterator();
      } else {
         final ListIterator var2 = var1.listIterator(var1.size());
         return new Iterator() {
            public void remove() {
               var2.remove();
            }

            public Object next() {
               return var2.previous();
            }

            public boolean hasNext() {
               return var2.hasPrevious();
            }
         };
      }
   }

   public List getGlyphs() {
      return this.pageGlyphs;
   }

   public Image getImage() {
      return this.pageImage;
   }

   public static Graphics2D getScratchGraphics() {
      return scratchGraphics;
   }

   private void renderGlyph(Glyph var1, int var2, int var3) throws SlickException {
      scratchGraphics.setComposite(AlphaComposite.Clear);
      scratchGraphics.fillRect(0, 0, 256, 256);
      scratchGraphics.setComposite(AlphaComposite.SrcOver);
      scratchGraphics.setColor(java.awt.Color.white);
      Iterator var4 = this.unicodeFont.getEffects().iterator();

      while(var4.hasNext()) {
         ((Effect)var4.next()).draw(scratchImage, scratchGraphics, this.unicodeFont, var1);
      }

      var1.setShape((Shape)null);
      WritableRaster var7 = scratchImage.getRaster();
      int[] var5 = new int[var2];

      for(int var6 = 0; var6 < var3; ++var6) {
         var7.getDataElements(0, var6, var2, 1, var5);
         scratchIntBuffer.put(var5);
      }

      GL.glTexSubImage2D(3553, 0, this.pageX, this.pageY, var2, var3, 32993, 5121, scratchByteBuffer);
      scratchIntBuffer.clear();
      var1.setImage(this.pageImage.getSubImage(this.pageX, this.pageY, var2, var3));
   }
}
