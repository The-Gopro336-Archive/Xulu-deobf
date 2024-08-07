package org.newdawn.slick;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import org.newdawn.slick.util.Log;
import org.newdawn.slick.util.ResourceLoader;

public class PackedSpriteSheet {
   // $FF: synthetic field
   private int filter;
   // $FF: synthetic field
   private HashMap sections;
   // $FF: synthetic field
   private Image image;
   // $FF: synthetic field
   private String basePath;

   private void loadDefinition(String var1, Color var2) throws SlickException {
      BufferedReader var3 = new BufferedReader(new InputStreamReader(ResourceLoader.getResourceAsStream(var1)));

      try {
         this.image = new Image(String.valueOf((new StringBuilder()).append(this.basePath).append(var3.readLine())), false, this.filter, var2);

         while(var3.ready() && var3.readLine() != null) {
            PackedSpriteSheet.Section var4 = new PackedSpriteSheet.Section(var3);
            this.sections.put(var4.name, var4);
            if (var3.readLine() == null) {
               break;
            }
         }

      } catch (Exception var5) {
         Log.error((Throwable)var5);
         throw new SlickException("Failed to process definitions file - invalid format?", var5);
      }
   }

   public PackedSpriteSheet(String var1) throws SlickException {
      this(var1, (Color)null);
   }

   public PackedSpriteSheet(String var1, int var2) throws SlickException {
      this(var1, var2, (Color)null);
   }

   public PackedSpriteSheet(String var1, int var2, Color var3) throws SlickException {
      this.sections = new HashMap();
      this.filter = 2;
      this.filter = var2;
      var1 = var1.replace('\\', '/');
      this.basePath = var1.substring(0, var1.lastIndexOf("/") + 1);
      this.loadDefinition(var1, var3);
   }

   public SpriteSheet getSpriteSheet(String var1) {
      Image var2 = this.getSprite(var1);
      PackedSpriteSheet.Section var3 = (PackedSpriteSheet.Section)this.sections.get(var1);
      return new SpriteSheet(var2, var3.width / var3.tilesx, var3.height / var3.tilesy);
   }

   public Image getSprite(String var1) {
      PackedSpriteSheet.Section var2 = (PackedSpriteSheet.Section)this.sections.get(var1);
      if (var2 == null) {
         throw new RuntimeException(String.valueOf((new StringBuilder()).append("Unknown sprite from packed sheet: ").append(var1)));
      } else {
         return this.image.getSubImage(var2.x, var2.y, var2.width, var2.height);
      }
   }

   public Image getFullImage() {
      return this.image;
   }

   public PackedSpriteSheet(String var1, Color var2) throws SlickException {
      this.sections = new HashMap();
      this.filter = 2;
      var1 = var1.replace('\\', '/');
      this.basePath = var1.substring(0, var1.lastIndexOf("/") + 1);
      this.loadDefinition(var1, var2);
   }

   private class Section {
      // $FF: synthetic field
      public int y;
      // $FF: synthetic field
      public int tilesx;
      // $FF: synthetic field
      public int tilesy;
      // $FF: synthetic field
      public int width;
      // $FF: synthetic field
      public int x;
      // $FF: synthetic field
      public int height;
      // $FF: synthetic field
      public String name;

      public Section(BufferedReader var2) throws IOException {
         this.name = var2.readLine().trim();
         this.x = Integer.parseInt(var2.readLine().trim());
         this.y = Integer.parseInt(var2.readLine().trim());
         this.width = Integer.parseInt(var2.readLine().trim());
         this.height = Integer.parseInt(var2.readLine().trim());
         this.tilesx = Integer.parseInt(var2.readLine().trim());
         this.tilesy = Integer.parseInt(var2.readLine().trim());
         var2.readLine().trim();
         var2.readLine().trim();
         this.tilesx = Math.max(1, this.tilesx);
         this.tilesy = Math.max(1, this.tilesy);
      }
   }
}
