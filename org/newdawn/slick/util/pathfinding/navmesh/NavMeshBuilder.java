package org.newdawn.slick.util.pathfinding.navmesh;

import java.util.ArrayList;
import org.newdawn.slick.util.pathfinding.Mover;
import org.newdawn.slick.util.pathfinding.PathFindingContext;
import org.newdawn.slick.util.pathfinding.TileBasedMap;

public class NavMeshBuilder implements PathFindingContext {
   // $FF: synthetic field
   private int sy;
   // $FF: synthetic field
   private boolean tileBased;
   // $FF: synthetic field
   private int sx;
   // $FF: synthetic field
   private float smallestSpace = 0.2F;

   private void linkSpaces(ArrayList var1) {
      for(int var2 = 0; var2 < var1.size(); ++var2) {
         Space var3 = (Space)var1.get(var2);

         for(int var4 = var2 + 1; var4 < var1.size(); ++var4) {
            Space var5 = (Space)var1.get(var4);
            if (var3.hasJoinedEdge(var5)) {
               var3.link(var5);
               var5.link(var3);
            }
         }
      }

   }

   private void subsection(TileBasedMap var1, Space var2, ArrayList var3) {
      if (!this.clear(var1, var2)) {
         float var4 = var2.getWidth() / 2.0F;
         float var5 = var2.getHeight() / 2.0F;
         if (var4 < this.smallestSpace && var5 < this.smallestSpace) {
            return;
         }

         this.subsection(var1, new Space(var2.getX(), var2.getY(), var4, var5), var3);
         this.subsection(var1, new Space(var2.getX(), var2.getY() + var5, var4, var5), var3);
         this.subsection(var1, new Space(var2.getX() + var4, var2.getY(), var4, var5), var3);
         this.subsection(var1, new Space(var2.getX() + var4, var2.getY() + var5, var4, var5), var3);
      } else {
         var3.add(var2);
      }

   }

   public int getSearchDistance() {
      return 0;
   }

   private boolean mergeSpaces(ArrayList var1) {
      for(int var2 = 0; var2 < var1.size(); ++var2) {
         Space var3 = (Space)var1.get(var2);

         for(int var4 = var2 + 1; var4 < var1.size(); ++var4) {
            Space var5 = (Space)var1.get(var4);
            if (var3.canMerge(var5)) {
               var1.remove(var3);
               var1.remove(var5);
               var1.add(var3.merge(var5));
               return true;
            }
         }
      }

      return false;
   }

   public NavMesh build(TileBasedMap var1) {
      return this.build(var1, true);
   }

   public int getSourceX() {
      return this.sx;
   }

   public boolean clear(TileBasedMap var1, Space var2) {
      if (this.tileBased) {
         return true;
      } else {
         float var3 = 0.0F;
         boolean var4 = false;

         while(var3 < var2.getWidth()) {
            float var5 = 0.0F;
            boolean var6 = false;

            while(var5 < var2.getHeight()) {
               this.sx = (int)(var2.getX() + var3);
               this.sy = (int)(var2.getY() + var5);
               if (var1.blocked(this, this.sx, this.sy)) {
                  return false;
               }

               var5 += 0.1F;
               if (var5 > var2.getHeight() && !var6) {
                  var5 = var2.getHeight();
                  var6 = true;
               }
            }

            var3 += 0.1F;
            if (var3 > var2.getWidth() && !var4) {
               var3 = var2.getWidth();
               var4 = true;
            }
         }

         return true;
      }
   }

   public int getSourceY() {
      return this.sy;
   }

   public NavMesh build(TileBasedMap var1, boolean var2) {
      this.tileBased = var2;
      ArrayList var3 = new ArrayList();
      if (var2) {
         for(int var4 = 0; var4 < var1.getWidthInTiles(); ++var4) {
            for(int var5 = 0; var5 < var1.getHeightInTiles(); ++var5) {
               if (!var1.blocked(this, var4, var5)) {
                  var3.add(new Space((float)var4, (float)var5, 1.0F, 1.0F));
               }
            }
         }
      } else {
         Space var7 = new Space(0.0F, 0.0F, (float)var1.getWidthInTiles(), (float)var1.getHeightInTiles());
         this.subsection(var1, var7, var3);
      }

      while(this.mergeSpaces(var3)) {
      }

      this.linkSpaces(var3);
      return new NavMesh(var3);
   }

   public Mover getMover() {
      return null;
   }
}
