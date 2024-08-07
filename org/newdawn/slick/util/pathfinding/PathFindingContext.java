package org.newdawn.slick.util.pathfinding;

public interface PathFindingContext {
   int getSourceY();

   int getSearchDistance();

   Mover getMover();

   int getSourceX();
}
