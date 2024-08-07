package org.newdawn.slick.tiled;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Properties;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.util.Log;
import org.newdawn.slick.util.ResourceLoader;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class TileSet {
   // $FF: synthetic field
   protected int tileMargin = 0;
   // $FF: synthetic field
   public SpriteSheet tiles;
   // $FF: synthetic field
   private final TiledMap map;
   // $FF: synthetic field
   private HashMap props = new HashMap();
   // $FF: synthetic field
   public int tilesAcross;
   // $FF: synthetic field
   public int tileHeight;
   // $FF: synthetic field
   public String name;
   // $FF: synthetic field
   public int index;
   // $FF: synthetic field
   public int lastGID = Integer.MAX_VALUE;
   // $FF: synthetic field
   public int firstGID;
   // $FF: synthetic field
   public int tileWidth;
   // $FF: synthetic field
   public int tilesDown;
   // $FF: synthetic field
   protected int tileSpacing = 0;

   public int getTileSpacing() {
      return this.tileSpacing;
   }

   public void setLimit(int var1) {
      this.lastGID = var1;
   }

   public int getTileX(int var1) {
      return var1 % this.tilesAcross;
   }

   public Properties getProperties(int var1) {
      return (Properties)this.props.get(new Integer(var1));
   }

   public int getTileY(int var1) {
      return var1 / this.tilesAcross;
   }

   public void setTileSetImage(Image var1) {
      this.tiles = new SpriteSheet(var1, this.tileWidth, this.tileHeight, this.tileSpacing, this.tileMargin);
      this.tilesAcross = this.tiles.getHorizontalCount();
      this.tilesDown = this.tiles.getVerticalCount();
      if (this.tilesAcross <= 0) {
         this.tilesAcross = 1;
      }

      if (this.tilesDown <= 0) {
         this.tilesDown = 1;
      }

      this.lastGID = this.tilesAcross * this.tilesDown + this.firstGID - 1;
   }

   public int getTileWidth() {
      return this.tileWidth;
   }

   public int getTileHeight() {
      return this.tileHeight;
   }

   public int getTileMargin() {
      return this.tileMargin;
   }

   public TileSet(TiledMap var1, Element var2, boolean var3) throws SlickException {
      this.map = var1;
      this.name = var2.getAttribute("name");
      this.firstGID = Integer.parseInt(var2.getAttribute("firstgid"));
      String var4 = var2.getAttribute("source");
      if (var4 != null && !var4.equals("")) {
         try {
            InputStream var5 = ResourceLoader.getResourceAsStream(String.valueOf((new StringBuilder()).append(var1.getTilesLocation()).append("/").append(var4)));
            DocumentBuilder var6 = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document var7 = var6.parse(var5);
            Element var8 = var7.getDocumentElement();
            var2 = var8;
         } catch (Exception var26) {
            Log.error((Throwable)var26);
            throw new SlickException(String.valueOf((new StringBuilder()).append("Unable to load or parse sourced tileset: ").append(this.map.tilesLocation).append("/").append(var4)));
         }
      }

      String var27 = var2.getAttribute("tilewidth");
      String var28 = var2.getAttribute("tileheight");
      if (var27.length() != 0 && var28.length() != 0) {
         this.tileWidth = Integer.parseInt(var27);
         this.tileHeight = Integer.parseInt(var28);
         String var29 = var2.getAttribute("spacing");
         if (var29 != null && !var29.equals("")) {
            this.tileSpacing = Integer.parseInt(var29);
         }

         String var30 = var2.getAttribute("margin");
         if (var30 != null && !var30.equals("")) {
            this.tileMargin = Integer.parseInt(var30);
         }

         NodeList var9 = var2.getElementsByTagName("image");
         Element var10 = (Element)var9.item(0);
         String var11 = var10.getAttribute("source");
         Color var12 = null;
         String var13 = var10.getAttribute("trans");
         if (var13 != null && var13.length() > 0) {
            int var14 = Integer.parseInt(var13, 16);
            var12 = new Color(var14);
         }

         if (var3) {
            Image var31 = new Image(String.valueOf((new StringBuilder()).append(var1.getTilesLocation()).append("/").append(var11)), false, 2, var12);
            this.setTileSetImage(var31);
         }

         NodeList var32 = var2.getElementsByTagName("tile");

         for(int var15 = 0; var15 < var32.getLength(); ++var15) {
            Element var16 = (Element)var32.item(var15);
            int var17 = Integer.parseInt(var16.getAttribute("id"));
            var17 += this.firstGID;
            Properties var18 = new Properties();
            Element var19 = (Element)var16.getElementsByTagName("properties").item(0);
            NodeList var20 = var19.getElementsByTagName("property");

            for(int var21 = 0; var21 < var20.getLength(); ++var21) {
               Element var22 = (Element)var20.item(var21);
               String var23 = var22.getAttribute("name");
               String var24 = var22.getAttribute("value");
               var18.setProperty(var23, var24);
            }

            this.props.put(new Integer(var17), var18);
         }

      } else {
         throw new SlickException("TiledMap requires that the map be created with tilesets that use a single image.  Check the WiKi for more complete information.");
      }
   }

   public boolean contains(int var1) {
      return var1 >= this.firstGID && var1 <= this.lastGID;
   }
}
