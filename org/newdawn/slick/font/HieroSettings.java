package org.newdawn.slick.font;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.font.effects.ConfigurableEffect;
import org.newdawn.slick.util.ResourceLoader;

public class HieroSettings {
   // $FF: synthetic field
   private int paddingBottom;
   // $FF: synthetic field
   private int glyphPageWidth;
   // $FF: synthetic field
   private int paddingAdvanceX;
   // $FF: synthetic field
   private int paddingRight;
   // $FF: synthetic field
   private boolean bold;
   // $FF: synthetic field
   private final List effects;
   // $FF: synthetic field
   private int paddingAdvanceY;
   // $FF: synthetic field
   private int glyphPageHeight;
   // $FF: synthetic field
   private boolean italic;
   // $FF: synthetic field
   private int fontSize;
   // $FF: synthetic field
   private int paddingLeft;
   // $FF: synthetic field
   private int paddingTop;

   public void setPaddingTop(int var1) {
      this.paddingTop = var1;
   }

   public void setGlyphPageWidth(int var1) {
      this.glyphPageWidth = var1;
   }

   public void setPaddingLeft(int var1) {
      this.paddingLeft = var1;
   }

   public int getPaddingAdvanceY() {
      return this.paddingAdvanceY;
   }

   public boolean isBold() {
      return this.bold;
   }

   public void setPaddingAdvanceX(int var1) {
      this.paddingAdvanceX = var1;
   }

   public int getPaddingBottom() {
      return this.paddingBottom;
   }

   public void save(File var1) throws IOException {
      PrintStream var2 = new PrintStream(new FileOutputStream(var1));
      var2.println(String.valueOf((new StringBuilder()).append("font.size=").append(this.fontSize)));
      var2.println(String.valueOf((new StringBuilder()).append("font.bold=").append(this.bold)));
      var2.println(String.valueOf((new StringBuilder()).append("font.italic=").append(this.italic)));
      var2.println();
      var2.println(String.valueOf((new StringBuilder()).append("pad.top=").append(this.paddingTop)));
      var2.println(String.valueOf((new StringBuilder()).append("pad.right=").append(this.paddingRight)));
      var2.println(String.valueOf((new StringBuilder()).append("pad.bottom=").append(this.paddingBottom)));
      var2.println(String.valueOf((new StringBuilder()).append("pad.left=").append(this.paddingLeft)));
      var2.println(String.valueOf((new StringBuilder()).append("pad.advance.x=").append(this.paddingAdvanceX)));
      var2.println(String.valueOf((new StringBuilder()).append("pad.advance.y=").append(this.paddingAdvanceY)));
      var2.println();
      var2.println(String.valueOf((new StringBuilder()).append("glyph.page.width=").append(this.glyphPageWidth)));
      var2.println(String.valueOf((new StringBuilder()).append("glyph.page.height=").append(this.glyphPageHeight)));
      var2.println();
      Iterator var3 = this.effects.iterator();

      while(var3.hasNext()) {
         ConfigurableEffect var4 = (ConfigurableEffect)var3.next();
         var2.println(String.valueOf((new StringBuilder()).append("effect.class=").append(var4.getClass().getName())));
         Iterator var5 = var4.getValues().iterator();

         while(var5.hasNext()) {
            ConfigurableEffect.Value var6 = (ConfigurableEffect.Value)var5.next();
            var2.println(String.valueOf((new StringBuilder()).append("effect.").append(var6.getName()).append("=").append(var6.getString())));
         }

         var2.println();
      }

      var2.close();
   }

   public List getEffects() {
      return this.effects;
   }

   public int getPaddingLeft() {
      return this.paddingLeft;
   }

   public HieroSettings(InputStream var1) throws SlickException {
      this.fontSize = 12;
      this.bold = false;
      this.italic = false;
      this.glyphPageWidth = 512;
      this.glyphPageHeight = 512;
      this.effects = new ArrayList();

      try {
         BufferedReader var2 = new BufferedReader(new InputStreamReader(var1));

         while(true) {
            while(true) {
               String var3;
               do {
                  var3 = var2.readLine();
                  if (var3 == null) {
                     var2.close();
                     return;
                  }

                  var3 = var3.trim();
               } while(var3.length() == 0);

               String[] var4 = var3.split("=", 2);
               String var5 = var4[0].trim();
               String var6 = var4[1];
               if (var5.equals("font.size")) {
                  this.fontSize = Integer.parseInt(var6);
               } else if (var5.equals("font.bold")) {
                  this.bold = Boolean.valueOf(var6);
               } else if (var5.equals("font.italic")) {
                  this.italic = Boolean.valueOf(var6);
               } else if (var5.equals("pad.top")) {
                  this.paddingTop = Integer.parseInt(var6);
               } else if (var5.equals("pad.right")) {
                  this.paddingRight = Integer.parseInt(var6);
               } else if (var5.equals("pad.bottom")) {
                  this.paddingBottom = Integer.parseInt(var6);
               } else if (var5.equals("pad.left")) {
                  this.paddingLeft = Integer.parseInt(var6);
               } else if (var5.equals("pad.advance.x")) {
                  this.paddingAdvanceX = Integer.parseInt(var6);
               } else if (var5.equals("pad.advance.y")) {
                  this.paddingAdvanceY = Integer.parseInt(var6);
               } else if (var5.equals("glyph.page.width")) {
                  this.glyphPageWidth = Integer.parseInt(var6);
               } else if (var5.equals("glyph.page.height")) {
                  this.glyphPageHeight = Integer.parseInt(var6);
               } else if (var5.equals("effect.class")) {
                  try {
                     this.effects.add(Class.forName(var6).newInstance());
                  } catch (Exception var12) {
                     throw new SlickException(String.valueOf((new StringBuilder()).append("Unable to create effect instance: ").append(var6)), var12);
                  }
               } else if (var5.startsWith("effect.")) {
                  var5 = var5.substring(7);
                  ConfigurableEffect var7 = (ConfigurableEffect)this.effects.get(this.effects.size() - 1);
                  List var8 = var7.getValues();
                  Iterator var9 = var8.iterator();

                  while(var9.hasNext()) {
                     ConfigurableEffect.Value var10 = (ConfigurableEffect.Value)var9.next();
                     if (var10.getName().equals(var5)) {
                        var10.setString(var6);
                        break;
                     }
                  }

                  var7.setValues(var8);
               }
            }
         }
      } catch (Exception var13) {
         throw new SlickException("Unable to load Hiero font file", var13);
      }
   }

   public void setFontSize(int var1) {
      this.fontSize = var1;
   }

   public void setPaddingRight(int var1) {
      this.paddingRight = var1;
   }

   public void setPaddingAdvanceY(int var1) {
      this.paddingAdvanceY = var1;
   }

   public boolean isItalic() {
      return this.italic;
   }

   public HieroSettings(String var1) throws SlickException {
      this(ResourceLoader.getResourceAsStream(var1));
   }

   public HieroSettings() {
      this.fontSize = 12;
      this.bold = false;
      this.italic = false;
      this.glyphPageWidth = 512;
      this.glyphPageHeight = 512;
      this.effects = new ArrayList();
   }

   public int getPaddingRight() {
      return this.paddingRight;
   }

   public int getPaddingAdvanceX() {
      return this.paddingAdvanceX;
   }

   public int getFontSize() {
      return this.fontSize;
   }

   public void setPaddingBottom(int var1) {
      this.paddingBottom = var1;
   }

   public void setGlyphPageHeight(int var1) {
      this.glyphPageHeight = var1;
   }

   public void setItalic(boolean var1) {
      this.italic = var1;
   }

   public int getGlyphPageWidth() {
      return this.glyphPageWidth;
   }

   public int getPaddingTop() {
      return this.paddingTop;
   }

   public int getGlyphPageHeight() {
      return this.glyphPageHeight;
   }

   public void setBold(boolean var1) {
      this.bold = var1;
   }
}
