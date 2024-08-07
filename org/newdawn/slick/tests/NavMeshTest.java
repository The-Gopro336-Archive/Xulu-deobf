package org.newdawn.slick.tests;

import java.io.IOException;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.util.Bootstrap;
import org.newdawn.slick.util.ResourceLoader;
import org.newdawn.slick.util.pathfinding.Mover;
import org.newdawn.slick.util.pathfinding.PathFindingContext;
import org.newdawn.slick.util.pathfinding.TileBasedMap;
import org.newdawn.slick.util.pathfinding.navmesh.Link;
import org.newdawn.slick.util.pathfinding.navmesh.NavMesh;
import org.newdawn.slick.util.pathfinding.navmesh.NavMeshBuilder;
import org.newdawn.slick.util.pathfinding.navmesh.NavPath;
import org.newdawn.slick.util.pathfinding.navmesh.Space;

public class NavMeshTest extends BasicGame implements PathFindingContext {
   // $FF: synthetic field
   private float ey;
   // $FF: synthetic field
   private NavPath path;
   // $FF: synthetic field
   private NavMeshTest.DataMap dataMap;
   // $FF: synthetic field
   private boolean showSpaces = true;
   // $FF: synthetic field
   private NavMeshBuilder builder;
   // $FF: synthetic field
   private NavMesh navMesh;
   // $FF: synthetic field
   private float sy;
   // $FF: synthetic field
   private float ex;
   // $FF: synthetic field
   private float sx;
   // $FF: synthetic field
   private boolean showLinks = true;

   public NavMeshTest() {
      super("Nav-mesh Test");
   }

   public int getSourceY() {
      return 0;
   }

   public static void main(String[] var0) {
      Bootstrap.runAsApplication(new NavMeshTest(), 600, 600, false);
   }

   public int getSourceX() {
      return 0;
   }

   public void render(GameContainer var1, Graphics var2) throws SlickException {
      var2.translate(50.0F, 50.0F);

      int var3;
      for(var3 = 0; var3 < 50; ++var3) {
         for(int var4 = 0; var4 < 50; ++var4) {
            if (this.dataMap.blocked(this, var3, var4)) {
               var2.setColor(Color.gray);
               var2.fillRect((float)(var3 * 10 + 1), (float)(var4 * 10 + 1), 8.0F, 8.0F);
            }
         }
      }

      if (this.showSpaces) {
         for(var3 = 0; var3 < this.navMesh.getSpaceCount(); ++var3) {
            Space var9 = this.navMesh.getSpace(var3);
            if (this.builder.clear(this.dataMap, var9)) {
               var2.setColor(new Color(1.0F, 1.0F, 0.0F, 0.5F));
               var2.fillRect(var9.getX() * 10.0F, var9.getY() * 10.0F, var9.getWidth() * 10.0F, var9.getHeight() * 10.0F);
            }

            var2.setColor(Color.yellow);
            var2.drawRect(var9.getX() * 10.0F, var9.getY() * 10.0F, var9.getWidth() * 10.0F, var9.getHeight() * 10.0F);
            if (this.showLinks) {
               int var5 = var9.getLinkCount();

               for(int var6 = 0; var6 < var5; ++var6) {
                  Link var7 = var9.getLink(var6);
                  var2.setColor(Color.red);
                  var2.fillRect(var7.getX() * 10.0F - 2.0F, var7.getY() * 10.0F - 2.0F, 5.0F, 5.0F);
               }
            }
         }
      }

      if (this.path != null) {
         var2.setColor(Color.white);

         for(var3 = 0; var3 < this.path.length() - 1; ++var3) {
            var2.drawLine(this.path.getX(var3) * 10.0F, this.path.getY(var3) * 10.0F, this.path.getX(var3 + 1) * 10.0F, this.path.getY(var3 + 1) * 10.0F);
         }
      }

   }

   public void mousePressed(int var1, int var2, int var3) {
      float var4 = (float)(var2 - 50) / 10.0F;
      float var5 = (float)(var3 - 50) / 10.0F;
      if (var1 == 0) {
         this.sx = var4;
         this.sy = var5;
      } else {
         this.ex = var4;
         this.ey = var5;
      }

      this.path = this.navMesh.findPath(this.sx, this.sy, this.ex, this.ey, true);
   }

   public void update(GameContainer var1, int var2) throws SlickException {
      if (var1.getInput().isKeyPressed(2)) {
         this.showLinks = !this.showLinks;
      }

      if (var1.getInput().isKeyPressed(3)) {
         this.showSpaces = !this.showSpaces;
      }

   }

   public void init(GameContainer var1) throws SlickException {
      var1.setShowFPS(false);

      try {
         this.dataMap = new NavMeshTest.DataMap("testdata/map.dat");
      } catch (IOException var3) {
         throw new SlickException("Failed to load map data", var3);
      }

      this.builder = new NavMeshBuilder();
      this.navMesh = this.builder.build(this.dataMap);
      System.out.println(String.valueOf((new StringBuilder()).append("Navmesh shapes: ").append(this.navMesh.getSpaceCount())));
   }

   public int getSearchDistance() {
      return 0;
   }

   public Mover getMover() {
      return null;
   }

   private class DataMap implements TileBasedMap {
      // $FF: synthetic field
      private byte[] map = new byte[2500];

      public void pathFinderVisited(int var1, int var2) {
      }

      public DataMap(String var2) throws IOException {
         ResourceLoader.getResourceAsStream(var2).read(this.map);
      }

      public boolean blocked(PathFindingContext var1, int var2, int var3) {
         if (var2 >= 0 && var3 >= 0 && var2 < 50 && var3 < 50) {
            return this.map[var2 + var3 * 50] != 0;
         } else {
            return false;
         }
      }

      public int getWidthInTiles() {
         return 50;
      }

      public int getHeightInTiles() {
         return 50;
      }

      public float getCost(PathFindingContext var1, int var2, int var3) {
         return 1.0F;
      }
   }
}
