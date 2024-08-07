package org.newdawn.slick;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.StringTokenizer;
import java.util.Map.Entry;
import org.newdawn.slick.opengl.renderer.Renderer;
import org.newdawn.slick.opengl.renderer.SGL;
import org.newdawn.slick.util.Log;
import org.newdawn.slick.util.ResourceLoader;

public class AngelCodeFont implements Font {
   // $FF: synthetic field
   private AngelCodeFont.CharDef[] chars;
   // $FF: synthetic field
   private AngelCodeFont.DisplayList eldestDisplayList;
   // $FF: synthetic field
   private int lineHeight;
   // $FF: synthetic field
   private static final int DISPLAY_LIST_CACHE_SIZE = 200;
   // $FF: synthetic field
   private int eldestDisplayListID;
   // $FF: synthetic field
   private final LinkedHashMap displayLists = new LinkedHashMap(200, 1.0F, true) {
      protected boolean removeEldestEntry(Entry var1) {
         AngelCodeFont.this.eldestDisplayList = (AngelCodeFont.DisplayList)var1.getValue();
         AngelCodeFont.this.eldestDisplayListID = AngelCodeFont.this.eldestDisplayList.id;
         return false;
      }
   };
   // $FF: synthetic field
   private static final int MAX_CHAR = 255;
   // $FF: synthetic field
   private int baseDisplayListID = -1;
   // $FF: synthetic field
   private static SGL GL = Renderer.get();
   // $FF: synthetic field
   private boolean displayListCaching = true;
   // $FF: synthetic field
   private Image fontImage;

   private AngelCodeFont.CharDef parseChar(String var1) throws SlickException {
      AngelCodeFont.CharDef var2 = new AngelCodeFont.CharDef();
      StringTokenizer var3 = new StringTokenizer(var1, " =");
      var3.nextToken();
      var3.nextToken();
      var2.id = Short.parseShort(var3.nextToken());
      if (var2.id < 0) {
         return null;
      } else if (var2.id > 255) {
         throw new SlickException(String.valueOf((new StringBuilder()).append("Invalid character '").append(var2.id).append("': AngelCodeFont does not support characters above ").append(255)));
      } else {
         var3.nextToken();
         var2.x = Short.parseShort(var3.nextToken());
         var3.nextToken();
         var2.y = Short.parseShort(var3.nextToken());
         var3.nextToken();
         var2.width = Short.parseShort(var3.nextToken());
         var3.nextToken();
         var2.height = Short.parseShort(var3.nextToken());
         var3.nextToken();
         var2.xoffset = Short.parseShort(var3.nextToken());
         var3.nextToken();
         var2.yoffset = Short.parseShort(var3.nextToken());
         var3.nextToken();
         var2.xadvance = Short.parseShort(var3.nextToken());
         var2.init();
         if (var2.id != 32) {
            this.lineHeight = Math.max(var2.height + var2.yoffset, this.lineHeight);
         }

         return var2;
      }
   }

   public AngelCodeFont(String var1, InputStream var2, InputStream var3, boolean var4) throws SlickException {
      this.fontImage = new Image(var3, var1, false);
      this.displayListCaching = var4;
      this.parseFnt(var2);
   }

   public AngelCodeFont(String var1, Image var2, boolean var3) throws SlickException {
      this.fontImage = var2;
      this.displayListCaching = var3;
      this.parseFnt(ResourceLoader.getResourceAsStream(var1));
   }

   private void render(String var1, int var2, int var3) {
      GL.glBegin(7);
      int var4 = 0;
      int var5 = 0;
      AngelCodeFont.CharDef var6 = null;
      char[] var7 = var1.toCharArray();

      for(int var8 = 0; var8 < var7.length; ++var8) {
         char var9 = var7[var8];
         if (var9 == '\n') {
            var4 = 0;
            var5 += this.getLineHeight();
         } else if (var9 < this.chars.length) {
            AngelCodeFont.CharDef var10 = this.chars[var9];
            if (var10 != null) {
               if (var6 != null) {
                  var4 += var6.getKerning(var9);
               }

               var6 = var10;
               if (var8 >= var2 && var8 <= var3) {
                  var10.draw((float)var4, (float)var5);
               }

               var4 += var10.xadvance;
            }
         }
      }

      GL.glEnd();
   }

   public AngelCodeFont(String var1, InputStream var2, InputStream var3) throws SlickException {
      this.fontImage = new Image(var3, var1, false);
      this.parseFnt(var2);
   }

   public AngelCodeFont(String var1, String var2) throws SlickException {
      this.fontImage = new Image(var2);
      this.parseFnt(ResourceLoader.getResourceAsStream(var1));
   }

   public void drawString(float var1, float var2, String var3, Color var4, int var5, int var6) {
      this.fontImage.bind();
      var4.bind();
      GL.glTranslatef(var1, var2, 0.0F);
      if (this.displayListCaching && var5 == 0 && var6 == var3.length() - 1) {
         AngelCodeFont.DisplayList var7 = (AngelCodeFont.DisplayList)this.displayLists.get(var3);
         if (var7 != null) {
            GL.glCallList(var7.id);
         } else {
            var7 = new AngelCodeFont.DisplayList();
            var7.text = var3;
            int var8 = this.displayLists.size();
            if (var8 < 200) {
               var7.id = this.baseDisplayListID + var8;
            } else {
               var7.id = this.eldestDisplayListID;
               this.displayLists.remove(this.eldestDisplayList.text);
            }

            this.displayLists.put(var3, var7);
            GL.glNewList(var7.id, 4865);
            this.render(var3, var5, var6);
            GL.glEndList();
         }
      } else {
         this.render(var3, var5, var6);
      }

      GL.glTranslatef(-var1, -var2, 0.0F);
   }

   public int getYOffset(String var1) {
      AngelCodeFont.DisplayList var2 = null;
      if (this.displayListCaching) {
         var2 = (AngelCodeFont.DisplayList)this.displayLists.get(var1);
         if (var2 != null && var2.yOffset != null) {
            return var2.yOffset.intValue();
         }
      }

      int var3 = var1.indexOf(10);
      if (var3 == -1) {
         var3 = var1.length();
      }

      int var4 = 10000;

      for(int var5 = 0; var5 < var3; ++var5) {
         char var6 = var1.charAt(var5);
         AngelCodeFont.CharDef var7 = this.chars[var6];
         if (var7 != null) {
            var4 = Math.min(var7.yoffset, var4);
         }
      }

      if (var2 != null) {
         var2.yOffset = new Short((short)var4);
      }

      return var4;
   }

   private void parseFnt(InputStream var1) throws SlickException {
      if (this.displayListCaching) {
         this.baseDisplayListID = GL.glGenLists(200);
         if (this.baseDisplayListID == 0) {
            this.displayListCaching = false;
         }
      }

      try {
         BufferedReader var2 = new BufferedReader(new InputStreamReader(var1));
         String var3 = var2.readLine();
         String var4 = var2.readLine();
         String var5 = var2.readLine();
         HashMap var6 = new HashMap(64);
         ArrayList var7 = new ArrayList(255);
         int var8 = 0;
         boolean var9 = false;

         AngelCodeFont.CharDef var11;
         short var12;
         while(!var9) {
            String var10 = var2.readLine();
            if (var10 == null) {
               var9 = true;
            } else {
               if (!var10.startsWith("chars c") && var10.startsWith("char")) {
                  var11 = this.parseChar(var10);
                  if (var11 != null) {
                     var8 = Math.max(var8, var11.id);
                     var7.add(var11);
                  }
               }

               if (!var10.startsWith("kernings c") && var10.startsWith("kerning")) {
                  StringTokenizer var19 = new StringTokenizer(var10, " =");
                  var19.nextToken();
                  var19.nextToken();
                  var12 = Short.parseShort(var19.nextToken());
                  var19.nextToken();
                  int var13 = Integer.parseInt(var19.nextToken());
                  var19.nextToken();
                  int var14 = Integer.parseInt(var19.nextToken());
                  Object var15 = (List)var6.get(new Short(var12));
                  if (var15 == null) {
                     var15 = new ArrayList();
                     var6.put(new Short(var12), var15);
                  }

                  ((List)var15).add(new Short((short)(var14 << 8 | var13)));
               }
            }
         }

         this.chars = new AngelCodeFont.CharDef[var8 + 1];

         Iterator var18;
         for(var18 = var7.iterator(); var18.hasNext(); this.chars[var11.id] = var11) {
            var11 = (AngelCodeFont.CharDef)var18.next();
         }

         short[] var22;
         for(var18 = var6.entrySet().iterator(); var18.hasNext(); this.chars[var12].kerning = var22) {
            Entry var20 = (Entry)var18.next();
            var12 = (Short)var20.getKey();
            List var21 = (List)var20.getValue();
            var22 = new short[var21.size()];
            int var23 = 0;

            for(Iterator var16 = var21.iterator(); var16.hasNext(); ++var23) {
               var22[var23] = (Short)var16.next();
            }
         }

      } catch (IOException var17) {
         Log.error((Throwable)var17);
         throw new SlickException(String.valueOf((new StringBuilder()).append("Failed to parse font file: ").append(var1)));
      }
   }

   public int getHeight(String var1) {
      AngelCodeFont.DisplayList var2 = null;
      if (this.displayListCaching) {
         var2 = (AngelCodeFont.DisplayList)this.displayLists.get(var1);
         if (var2 != null && var2.height != null) {
            return var2.height.intValue();
         }
      }

      int var3 = 0;
      int var4 = 0;

      for(int var5 = 0; var5 < var1.length(); ++var5) {
         char var6 = var1.charAt(var5);
         if (var6 == '\n') {
            ++var3;
            var4 = 0;
         } else if (var6 != ' ') {
            AngelCodeFont.CharDef var7 = this.chars[var6];
            if (var7 != null) {
               var4 = Math.max(var7.height + var7.yoffset, var4);
            }
         }
      }

      var4 += var3 * this.getLineHeight();
      if (var2 != null) {
         var2.height = new Short((short)var4);
      }

      return var4;
   }

   public void drawString(float var1, float var2, String var3) {
      this.drawString(var1, var2, var3, Color.white);
   }

   public int getLineHeight() {
      return this.lineHeight;
   }

   public void drawString(float var1, float var2, String var3, Color var4) {
      this.drawString(var1, var2, var3, var4, 0, var3.length() - 1);
   }

   public int getWidth(String var1) {
      AngelCodeFont.DisplayList var2 = null;
      if (this.displayListCaching) {
         var2 = (AngelCodeFont.DisplayList)this.displayLists.get(var1);
         if (var2 != null && var2.width != null) {
            return var2.width.intValue();
         }
      }

      int var3 = 0;
      int var4 = 0;
      AngelCodeFont.CharDef var5 = null;
      int var6 = 0;

      for(int var7 = var1.length(); var6 < var7; ++var6) {
         char var8 = var1.charAt(var6);
         if (var8 == '\n') {
            var4 = 0;
         } else if (var8 < this.chars.length) {
            AngelCodeFont.CharDef var9 = this.chars[var8];
            if (var9 != null) {
               if (var5 != null) {
                  var4 += var5.getKerning(var8);
               }

               var5 = var9;
               if (var6 < var7 - 1) {
                  var4 += var9.xadvance;
               } else {
                  var4 += var9.width;
               }

               var3 = Math.max(var3, var4);
            }
         }
      }

      if (var2 != null) {
         var2.width = new Short((short)var3);
      }

      return var3;
   }

   public AngelCodeFont(String var1, Image var2) throws SlickException {
      this.fontImage = var2;
      this.parseFnt(ResourceLoader.getResourceAsStream(var1));
   }

   public AngelCodeFont(String var1, String var2, boolean var3) throws SlickException {
      this.fontImage = new Image(var2);
      this.displayListCaching = var3;
      this.parseFnt(ResourceLoader.getResourceAsStream(var1));
   }

   private class CharDef {
      // $FF: synthetic field
      public short xoffset;
      // $FF: synthetic field
      public short id;
      // $FF: synthetic field
      public short[] kerning;
      // $FF: synthetic field
      public short yoffset;
      // $FF: synthetic field
      public short x;
      // $FF: synthetic field
      public short width;
      // $FF: synthetic field
      public short xadvance;
      // $FF: synthetic field
      public short height;
      // $FF: synthetic field
      public short dlIndex;
      // $FF: synthetic field
      public short y;
      // $FF: synthetic field
      public Image image;

      public String toString() {
         return String.valueOf((new StringBuilder()).append("[CharDef id=").append(this.id).append(" x=").append(this.x).append(" y=").append(this.y).append("]"));
      }

      private CharDef() {
      }

      public void init() {
         this.image = AngelCodeFont.this.fontImage.getSubImage(this.x, this.y, this.width, this.height);
      }

      public int getKerning(int var1) {
         if (this.kerning == null) {
            return 0;
         } else {
            int var2 = 0;
            int var3 = this.kerning.length - 1;

            while(var2 <= var3) {
               int var4 = var2 + var3 >>> 1;
               short var5 = this.kerning[var4];
               int var6 = var5 & 255;
               if (var6 < var1) {
                  var2 = var4 + 1;
               } else {
                  if (var6 <= var1) {
                     return var5 >> 8;
                  }

                  var3 = var4 - 1;
               }
            }

            return 0;
         }
      }

      // $FF: synthetic method
      CharDef(Object var2) {
         this();
      }

      public void draw(float var1, float var2) {
         this.image.drawEmbedded(var1 + (float)this.xoffset, var2 + (float)this.yoffset, (float)this.width, (float)this.height);
      }
   }

   private static class DisplayList {
      // $FF: synthetic field
      String text;
      // $FF: synthetic field
      Short width;
      // $FF: synthetic field
      int id;
      // $FF: synthetic field
      Short yOffset;
      // $FF: synthetic field
      Short height;

      // $FF: synthetic method
      DisplayList(Object var1) {
         this();
      }

      private DisplayList() {
      }
   }
}
