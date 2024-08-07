package org.newdawn.slick.tiled;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Properties;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.util.Log;
import org.newdawn.slick.util.ResourceLoader;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class TiledMap {
   // $FF: synthetic field
   protected int tileHeight;
   // $FF: synthetic field
   protected ArrayList tileSets;
   // $FF: synthetic field
   protected Properties props;
   // $FF: synthetic field
   protected ArrayList objectGroups;
   // $FF: synthetic field
   protected int width;
   // $FF: synthetic field
   protected String tilesLocation;
   // $FF: synthetic field
   protected static final int ORTHOGONAL = 1;
   // $FF: synthetic field
   protected int orientation;
   // $FF: synthetic field
   protected int tileWidth;
   // $FF: synthetic field
   protected ArrayList layers;
   // $FF: synthetic field
   private static boolean headless;
   // $FF: synthetic field
   private boolean loadTileSets;
   // $FF: synthetic field
   protected static final int ISOMETRIC = 2;
   // $FF: synthetic field
   protected int height;

   public String getMapProperty(String var1, String var2) {
      return this.props == null ? var2 : this.props.getProperty(var1, var2);
   }

   public TiledMap(InputStream var1) throws SlickException {
      this.tileSets = new ArrayList();
      this.layers = new ArrayList();
      this.objectGroups = new ArrayList();
      this.loadTileSets = true;
      this.load(var1, "");
   }

   public TileSet findTileSet(int var1) {
      for(int var2 = 0; var2 < this.tileSets.size(); ++var2) {
         TileSet var3 = (TileSet)this.tileSets.get(var2);
         if (var3.contains(var1)) {
            return var3;
         }
      }

      return null;
   }

   public void render(int var1, int var2, int var3, int var4, int var5, int var6, int var7, boolean var8) {
      Layer var9 = (Layer)this.layers.get(var7);
      switch(this.orientation) {
      case 1:
         for(int var10 = 0; var10 < var6; ++var10) {
            var9.render(var1, var2, var3, var4, var5, var10, var8, this.tileWidth, this.tileHeight);
         }

         return;
      case 2:
         this.renderIsometricMap(var1, var2, var3, var4, var5, var6, var9, var8);
      }

   }

   protected void renderIsometricMap(int var1, int var2, int var3, int var4, int var5, int var6, Layer var7, boolean var8) {
      ArrayList var9 = this.layers;
      if (var7 != null) {
         var9 = new ArrayList();
         var9.add(var7);
      }

      int var10 = var5 * var6;
      int var11 = 0;
      boolean var12 = false;
      int var13 = var1;
      int var14 = var2;
      int var15 = 0;
      int var16 = 0;

      while(!var12) {
         int var17 = var15;
         int var18 = var16;
         int var19 = var13;
         boolean var20 = false;
         int var24;
         if (var6 > var5) {
            var24 = var16 < var5 - 1 ? var16 : (var5 - var15 < var6 ? var5 - var15 - 1 : var5 - 1);
         } else {
            var24 = var16 < var6 - 1 ? var16 : (var5 - var15 < var6 ? var5 - var15 - 1 : var6 - 1);
         }

         for(int var21 = 0; var21 <= var24; ++var21) {
            for(int var22 = 0; var22 < var9.size(); ++var22) {
               Layer var23 = (Layer)var9.get(var22);
               var23.render(var19, var14, var17, var18, 1, 0, var8, this.tileWidth, this.tileHeight);
            }

            var19 += this.tileWidth;
            ++var11;
            ++var17;
            --var18;
         }

         if (var16 < var6 - 1) {
            ++var16;
            var13 -= this.tileWidth / 2;
            var14 += this.tileHeight / 2;
         } else {
            ++var15;
            var13 += this.tileWidth / 2;
            var14 += this.tileHeight / 2;
         }

         if (var11 >= var10) {
            var12 = true;
         }
      }

   }

   public String getObjectName(int var1, int var2) {
      if (var1 >= 0 && var1 < this.objectGroups.size()) {
         TiledMap.ObjectGroup var3 = (TiledMap.ObjectGroup)this.objectGroups.get(var1);
         if (var2 >= 0 && var2 < var3.objects.size()) {
            TiledMap.GroupObject var4 = (TiledMap.GroupObject)var3.objects.get(var2);
            return var4.name;
         }
      }

      return null;
   }

   public int getTileId(int var1, int var2, int var3) {
      Layer var4 = (Layer)this.layers.get(var3);
      return var4.getTileID(var1, var2);
   }

   private static void setHeadless(boolean var0) {
      headless = var0;
   }

   public String getObjectImage(int var1, int var2) {
      if (var1 >= 0 && var1 < this.objectGroups.size()) {
         TiledMap.ObjectGroup var3 = (TiledMap.ObjectGroup)this.objectGroups.get(var1);
         if (var2 >= 0 && var2 < var3.objects.size()) {
            TiledMap.GroupObject var4 = (TiledMap.GroupObject)var3.objects.get(var2);
            if (var4 == null) {
               return null;
            }

            return var4.image;
         }
      }

      return null;
   }

   public int getTileWidth() {
      return this.tileWidth;
   }

   public TiledMap(String var1) throws SlickException {
      this(var1, true);
   }

   public String getObjectType(int var1, int var2) {
      if (var1 >= 0 && var1 < this.objectGroups.size()) {
         TiledMap.ObjectGroup var3 = (TiledMap.ObjectGroup)this.objectGroups.get(var1);
         if (var2 >= 0 && var2 < var3.objects.size()) {
            TiledMap.GroupObject var4 = (TiledMap.GroupObject)var3.objects.get(var2);
            return var4.type;
         }
      }

      return null;
   }

   public TiledMap(InputStream var1, String var2) throws SlickException {
      this.tileSets = new ArrayList();
      this.layers = new ArrayList();
      this.objectGroups = new ArrayList();
      this.loadTileSets = true;
      this.load(var1, var2);
   }

   public void render(int var1, int var2) {
      this.render(var1, var2, 0, 0, this.width, this.height, false);
   }

   public int getTileHeight() {
      return this.tileHeight;
   }

   public TiledMap(String var1, String var2) throws SlickException {
      this.tileSets = new ArrayList();
      this.layers = new ArrayList();
      this.objectGroups = new ArrayList();
      this.loadTileSets = true;
      this.load(ResourceLoader.getResourceAsStream(var1), var2);
   }

   public TileSet getTileSetByGID(int var1) {
      for(int var2 = 0; var2 < this.tileSets.size(); ++var2) {
         TileSet var3 = (TileSet)this.tileSets.get(var2);
         if (var3.contains(var1)) {
            return var3;
         }
      }

      return null;
   }

   public String getLayerProperty(int var1, String var2, String var3) {
      Layer var4 = (Layer)this.layers.get(var1);
      return var4 != null && var4.props != null ? var4.props.getProperty(var2, var3) : var3;
   }

   public int getObjectWidth(int var1, int var2) {
      if (var1 >= 0 && var1 < this.objectGroups.size()) {
         TiledMap.ObjectGroup var3 = (TiledMap.ObjectGroup)this.objectGroups.get(var1);
         if (var2 >= 0 && var2 < var3.objects.size()) {
            TiledMap.GroupObject var4 = (TiledMap.GroupObject)var3.objects.get(var2);
            return var4.width;
         }
      }

      return -1;
   }

   public int getWidth() {
      return this.width;
   }

   private void load(InputStream var1, String var2) throws SlickException {
      this.tilesLocation = var2;

      try {
         DocumentBuilderFactory var3 = DocumentBuilderFactory.newInstance();
         var3.setValidating(false);
         DocumentBuilder var4 = var3.newDocumentBuilder();
         var4.setEntityResolver(new EntityResolver() {
            public InputSource resolveEntity(String var1, String var2) throws SAXException, IOException {
               return new InputSource(new ByteArrayInputStream(new byte[0]));
            }
         });
         Document var5 = var4.parse(var1);
         Element var6 = var5.getDocumentElement();
         if (var6.getAttribute("orientation").equals("orthogonal")) {
            this.orientation = 1;
         } else {
            this.orientation = 2;
         }

         this.width = this.parseInt(var6.getAttribute("width"));
         this.height = this.parseInt(var6.getAttribute("height"));
         this.tileWidth = this.parseInt(var6.getAttribute("tilewidth"));
         this.tileHeight = this.parseInt(var6.getAttribute("tileheight"));
         Element var7 = (Element)var6.getElementsByTagName("properties").item(0);
         NodeList var8;
         int var9;
         Element var10;
         if (var7 != null) {
            var8 = var7.getElementsByTagName("property");
            if (var8 != null) {
               this.props = new Properties();

               for(var9 = 0; var9 < var8.getLength(); ++var9) {
                  var10 = (Element)var8.item(var9);
                  String var11 = var10.getAttribute("name");
                  String var12 = var10.getAttribute("value");
                  this.props.setProperty(var11, var12);
               }
            }
         }

         if (this.loadTileSets) {
            var8 = null;
            TileSet var16 = null;
            NodeList var17 = var6.getElementsByTagName("tileset");

            for(int var20 = 0; var20 < var17.getLength(); ++var20) {
               Element var22 = (Element)var17.item(var20);
               TileSet var15 = new TileSet(this, var22, !headless);
               var15.index = var20;
               if (var16 != null) {
                  var16.setLimit(var15.firstGID - 1);
               }

               var16 = var15;
               this.tileSets.add(var15);
            }
         }

         var8 = var6.getElementsByTagName("layer");

         for(var9 = 0; var9 < var8.getLength(); ++var9) {
            var10 = (Element)var8.item(var9);
            Layer var21 = new Layer(this, var10);
            var21.index = var9;
            this.layers.add(var21);
         }

         NodeList var18 = var6.getElementsByTagName("objectgroup");

         for(int var19 = 0; var19 < var18.getLength(); ++var19) {
            Element var23 = (Element)var18.item(var19);
            TiledMap.ObjectGroup var24 = new TiledMap.ObjectGroup(var23);
            var24.index = var19;
            this.objectGroups.add(var24);
         }

      } catch (Exception var14) {
         Log.error((Throwable)var14);
         throw new SlickException("Failed to parse tilemap", var14);
      }
   }

   public TiledMap(String var1, boolean var2) throws SlickException {
      this.tileSets = new ArrayList();
      this.layers = new ArrayList();
      this.objectGroups = new ArrayList();
      this.loadTileSets = true;
      this.loadTileSets = var2;
      var1 = var1.replace('\\', '/');
      this.load(ResourceLoader.getResourceAsStream(var1), var1.substring(0, var1.lastIndexOf("/")));
   }

   public int getTileSetCount() {
      return this.tileSets.size();
   }

   public int getLayerCount() {
      return this.layers.size();
   }

   public void render(int var1, int var2, int var3, int var4, int var5, int var6) {
      this.render(var1, var2, var3, var4, var5, var6, false);
   }

   public String getTilesLocation() {
      return this.tilesLocation;
   }

   public Image getTileImage(int var1, int var2, int var3) {
      Layer var4 = (Layer)this.layers.get(var3);
      int var5 = var4.data[var1][var2][0];
      if (var5 >= 0 && var5 < this.tileSets.size()) {
         TileSet var6 = (TileSet)this.tileSets.get(var5);
         int var7 = var6.getTileX(var4.data[var1][var2][1]);
         int var8 = var6.getTileY(var4.data[var1][var2][1]);
         return var6.tiles.getSprite(var7, var8);
      } else {
         return null;
      }
   }

   public int getObjectCount(int var1) {
      if (var1 >= 0 && var1 < this.objectGroups.size()) {
         TiledMap.ObjectGroup var2 = (TiledMap.ObjectGroup)this.objectGroups.get(var1);
         return var2.objects.size();
      } else {
         return -1;
      }
   }

   public String getTileProperty(int var1, String var2, String var3) {
      if (var1 == 0) {
         return var3;
      } else {
         TileSet var4 = this.findTileSet(var1);
         Properties var5 = var4.getProperties(var1);
         return var5 == null ? var3 : var5.getProperty(var2, var3);
      }
   }

   public void setTileId(int var1, int var2, int var3, int var4) {
      Layer var5 = (Layer)this.layers.get(var3);
      var5.setTileID(var1, var2, var4);
   }

   public int getObjectY(int var1, int var2) {
      if (var1 >= 0 && var1 < this.objectGroups.size()) {
         TiledMap.ObjectGroup var3 = (TiledMap.ObjectGroup)this.objectGroups.get(var1);
         if (var2 >= 0 && var2 < var3.objects.size()) {
            TiledMap.GroupObject var4 = (TiledMap.GroupObject)var3.objects.get(var2);
            return var4.y;
         }
      }

      return -1;
   }

   public void render(int var1, int var2, int var3) {
      this.render(var1, var2, 0, 0, this.getWidth(), this.getHeight(), var3, false);
   }

   public TileSet getTileSet(int var1) {
      return (TileSet)this.tileSets.get(var1);
   }

   public int getHeight() {
      return this.height;
   }

   public int getLayerIndex(String var1) {
      boolean var2 = false;

      for(int var3 = 0; var3 < this.layers.size(); ++var3) {
         Layer var4 = (Layer)this.layers.get(var3);
         if (var4.name.equals(var1)) {
            return var3;
         }
      }

      return -1;
   }

   private int parseInt(String var1) {
      try {
         return Integer.parseInt(var1);
      } catch (NumberFormatException var3) {
         return 0;
      }
   }

   public String getObjectProperty(int var1, int var2, String var3, String var4) {
      if (var1 >= 0 && var1 < this.objectGroups.size()) {
         TiledMap.ObjectGroup var5 = (TiledMap.ObjectGroup)this.objectGroups.get(var1);
         if (var2 >= 0 && var2 < var5.objects.size()) {
            TiledMap.GroupObject var6 = (TiledMap.GroupObject)var5.objects.get(var2);
            if (var6 == null) {
               return var4;
            }

            if (var6.props == null) {
               return var4;
            }

            return var6.props.getProperty(var3, var4);
         }
      }

      return var4;
   }

   public int getObjectHeight(int var1, int var2) {
      if (var1 >= 0 && var1 < this.objectGroups.size()) {
         TiledMap.ObjectGroup var3 = (TiledMap.ObjectGroup)this.objectGroups.get(var1);
         if (var2 >= 0 && var2 < var3.objects.size()) {
            TiledMap.GroupObject var4 = (TiledMap.GroupObject)var3.objects.get(var2);
            return var4.height;
         }
      }

      return -1;
   }

   public int getObjectGroupCount() {
      return this.objectGroups.size();
   }

   protected void renderedLine(int var1, int var2, int var3) {
   }

   public int getObjectX(int var1, int var2) {
      if (var1 >= 0 && var1 < this.objectGroups.size()) {
         TiledMap.ObjectGroup var3 = (TiledMap.ObjectGroup)this.objectGroups.get(var1);
         if (var2 >= 0 && var2 < var3.objects.size()) {
            TiledMap.GroupObject var4 = (TiledMap.GroupObject)var3.objects.get(var2);
            return var4.x;
         }
      }

      return -1;
   }

   public void render(int var1, int var2, int var3, int var4, int var5, int var6, boolean var7) {
      switch(this.orientation) {
      case 1:
         for(int var8 = 0; var8 < var6; ++var8) {
            for(int var9 = 0; var9 < this.layers.size(); ++var9) {
               Layer var10 = (Layer)this.layers.get(var9);
               var10.render(var1, var2, var3, var4, var5, var8, var7, this.tileWidth, this.tileHeight);
            }
         }

         return;
      case 2:
         this.renderIsometricMap(var1, var2, var3, var4, var5, var6, (Layer)null, var7);
      }

   }

   protected class GroupObject {
      // $FF: synthetic field
      public int height;
      // $FF: synthetic field
      public int index;
      // $FF: synthetic field
      public int y;
      // $FF: synthetic field
      public int width;
      // $FF: synthetic field
      public int x;
      // $FF: synthetic field
      private String image;
      // $FF: synthetic field
      public String name;
      // $FF: synthetic field
      public Properties props;
      // $FF: synthetic field
      public String type;

      public GroupObject(Element var2) throws SlickException {
         this.name = var2.getAttribute("name");
         this.type = var2.getAttribute("type");
         this.x = Integer.parseInt(var2.getAttribute("x"));
         this.y = Integer.parseInt(var2.getAttribute("y"));
         this.width = Integer.parseInt(var2.getAttribute("width"));
         this.height = Integer.parseInt(var2.getAttribute("height"));
         Element var3 = (Element)var2.getElementsByTagName("image").item(0);
         if (var3 != null) {
            this.image = var3.getAttribute("source");
         }

         Element var4 = (Element)var2.getElementsByTagName("properties").item(0);
         if (var4 != null) {
            NodeList var5 = var4.getElementsByTagName("property");
            if (var5 != null) {
               this.props = new Properties();

               for(int var6 = 0; var6 < var5.getLength(); ++var6) {
                  Element var7 = (Element)var5.item(var6);
                  String var8 = var7.getAttribute("name");
                  String var9 = var7.getAttribute("value");
                  this.props.setProperty(var8, var9);
               }
            }
         }

      }
   }

   protected class ObjectGroup {
      // $FF: synthetic field
      public int index;
      // $FF: synthetic field
      public int height;
      // $FF: synthetic field
      public ArrayList objects;
      // $FF: synthetic field
      public int width;
      // $FF: synthetic field
      public Properties props;
      // $FF: synthetic field
      public String name;

      public ObjectGroup(Element var2) throws SlickException {
         this.name = var2.getAttribute("name");
         this.width = Integer.parseInt(var2.getAttribute("width"));
         this.height = Integer.parseInt(var2.getAttribute("height"));
         this.objects = new ArrayList();
         Element var3 = (Element)var2.getElementsByTagName("properties").item(0);
         NodeList var4;
         int var5;
         Element var6;
         if (var3 != null) {
            var4 = var3.getElementsByTagName("property");
            if (var4 != null) {
               this.props = new Properties();

               for(var5 = 0; var5 < var4.getLength(); ++var5) {
                  var6 = (Element)var4.item(var5);
                  String var7 = var6.getAttribute("name");
                  String var8 = var6.getAttribute("value");
                  this.props.setProperty(var7, var8);
               }
            }
         }

         var4 = var2.getElementsByTagName("object");

         for(var5 = 0; var5 < var4.getLength(); ++var5) {
            var6 = (Element)var4.item(var5);
            TiledMap.GroupObject var9 = TiledMap.this.new GroupObject(var6);
            var9.index = var5;
            this.objects.add(var9);
         }

      }
   }
}
