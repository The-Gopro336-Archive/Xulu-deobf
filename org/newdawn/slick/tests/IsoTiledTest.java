package org.newdawn.slick.tests;

import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;
import org.newdawn.slick.util.Bootstrap;

public class IsoTiledTest extends BasicGame {
   // $FF: synthetic field
   private TiledMap tilemap;

   public IsoTiledTest() {
      super("Isometric Tiled Map Test");
   }

   public void init(GameContainer var1) throws SlickException {
      this.tilemap = new TiledMap("testdata/isoexample.tmx", "testdata/");
   }

   public void render(GameContainer var1, Graphics var2) throws SlickException {
      this.tilemap.render(350, 150);
   }

   public static void main(String[] var0) {
      Bootstrap.runAsApplication(new IsoTiledTest(), 800, 600, false);
   }

   public void update(GameContainer var1, int var2) throws SlickException {
   }
}
