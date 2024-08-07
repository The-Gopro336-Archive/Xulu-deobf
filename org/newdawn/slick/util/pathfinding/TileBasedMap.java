package org.newdawn.slick.util.pathfinding;

public interface TileBasedMap {
   int getWidthInTiles();

   int getHeightInTiles();

   boolean blocked(PathFindingContext var1, int var2, int var3);

   void pathFinderVisited(int var1, int var2);

   float getCost(PathFindingContext var1, int var2, int var3);
}
