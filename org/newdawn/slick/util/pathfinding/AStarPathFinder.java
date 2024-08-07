package org.newdawn.slick.util.pathfinding;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import org.newdawn.slick.util.pathfinding.heuristics.ClosestHeuristic;

public class AStarPathFinder implements PathFindingContext, PathFinder {
   // $FF: synthetic field
   private ArrayList closed;
   // $FF: synthetic field
   private Mover mover;
   // $FF: synthetic field
   private int sourceX;
   // $FF: synthetic field
   private boolean allowDiagMovement;
   // $FF: synthetic field
   private AStarHeuristic heuristic;
   // $FF: synthetic field
   private int maxSearchDistance;
   // $FF: synthetic field
   private AStarPathFinder.Node[][] nodes;
   // $FF: synthetic field
   private TileBasedMap map;
   // $FF: synthetic field
   private AStarPathFinder.PriorityList open;
   // $FF: synthetic field
   private int distance;
   // $FF: synthetic field
   private AStarPathFinder.Node current;
   // $FF: synthetic field
   private int sourceY;

   public int getSourceX() {
      return this.sourceX;
   }

   protected boolean isValidLocation(Mover var1, int var2, int var3, int var4, int var5) {
      boolean var6 = var4 < 0 || var5 < 0 || var4 >= this.map.getWidthInTiles() || var5 >= this.map.getHeightInTiles();
      if (!var6 && (var2 != var4 || var3 != var5)) {
         this.mover = var1;
         this.sourceX = var2;
         this.sourceY = var3;
         var6 = this.map.blocked(this, var4, var5);
      }

      return !var6;
   }

   protected void addToClosed(AStarPathFinder.Node var1) {
      var1.setClosed(true);
      this.closed.add(var1);
   }

   public int getCurrentX() {
      return this.current == null ? -1 : this.current.x;
   }

   protected AStarPathFinder.Node getFirstInOpen() {
      return (AStarPathFinder.Node)this.open.first();
   }

   public Path findPath(Mover var1, int var2, int var3, int var4, int var5) {
      this.current = null;
      this.mover = var1;
      this.sourceX = var4;
      this.sourceY = var5;
      this.distance = 0;
      if (this.map.blocked(this, var4, var5)) {
         return null;
      } else {
         int var6;
         int var7;
         for(var6 = 0; var6 < this.map.getWidthInTiles(); ++var6) {
            for(var7 = 0; var7 < this.map.getHeightInTiles(); ++var7) {
               this.nodes[var6][var7].reset();
            }
         }

         this.nodes[var2][var3].cost = 0.0F;
         this.nodes[var2][var3].depth = 0;
         this.closed.clear();
         this.open.clear();
         this.addToOpen(this.nodes[var2][var3]);
         this.nodes[var4][var5].parent = null;
         var6 = 0;

         while(var6 < this.maxSearchDistance && this.open.size() != 0) {
            var7 = var2;
            int var8 = var3;
            if (this.current != null) {
               var7 = this.current.x;
               var8 = this.current.y;
            }

            this.current = this.getFirstInOpen();
            this.distance = this.current.depth;
            if (this.current == this.nodes[var4][var5] && this.isValidLocation(var1, var7, var8, var4, var5)) {
               break;
            }

            this.removeFromOpen(this.current);
            this.addToClosed(this.current);

            for(int var9 = -1; var9 < 2; ++var9) {
               for(int var10 = -1; var10 < 2; ++var10) {
                  if ((var9 != 0 || var10 != 0) && (this.allowDiagMovement || var9 == 0 || var10 == 0)) {
                     int var11 = var9 + this.current.x;
                     int var12 = var10 + this.current.y;
                     if (this.isValidLocation(var1, this.current.x, this.current.y, var11, var12)) {
                        float var13 = this.current.cost + this.getMovementCost(var1, this.current.x, this.current.y, var11, var12);
                        AStarPathFinder.Node var14 = this.nodes[var11][var12];
                        this.map.pathFinderVisited(var11, var12);
                        if (var13 < var14.cost) {
                           if (this.inOpenList(var14)) {
                              this.removeFromOpen(var14);
                           }

                           if (this.inClosedList(var14)) {
                              this.removeFromClosed(var14);
                           }
                        }

                        if (!this.inOpenList(var14) && !this.inClosedList(var14)) {
                           var14.cost = var13;
                           var14.heuristic = this.getHeuristicCost(var1, var11, var12, var4, var5);
                           var6 = Math.max(var6, var14.setParent(this.current));
                           this.addToOpen(var14);
                        }
                     }
                  }
               }
            }
         }

         if (this.nodes[var4][var5].parent == null) {
            return null;
         } else {
            Path var15 = new Path();

            for(AStarPathFinder.Node var16 = this.nodes[var4][var5]; var16 != this.nodes[var2][var3]; var16 = var16.parent) {
               var15.prependStep(var16.x, var16.y);
            }

            var15.prependStep(var2, var3);
            return var15;
         }
      }
   }

   protected boolean inClosedList(AStarPathFinder.Node var1) {
      return var1.isClosed();
   }

   public int getCurrentY() {
      return this.current == null ? -1 : this.current.y;
   }

   public float getHeuristicCost(Mover var1, int var2, int var3, int var4, int var5) {
      return this.heuristic.getCost(this.map, var1, var2, var3, var4, var5);
   }

   public AStarPathFinder(TileBasedMap var1, int var2, boolean var3, AStarHeuristic var4) {
      this.closed = new ArrayList();
      this.open = new AStarPathFinder.PriorityList();
      this.heuristic = var4;
      this.map = var1;
      this.maxSearchDistance = var2;
      this.allowDiagMovement = var3;
      this.nodes = new AStarPathFinder.Node[var1.getWidthInTiles()][var1.getHeightInTiles()];

      for(int var5 = 0; var5 < var1.getWidthInTiles(); ++var5) {
         for(int var6 = 0; var6 < var1.getHeightInTiles(); ++var6) {
            this.nodes[var5][var6] = new AStarPathFinder.Node(var5, var6);
         }
      }

   }

   public int getSourceY() {
      return this.sourceY;
   }

   public float getMovementCost(Mover var1, int var2, int var3, int var4, int var5) {
      this.mover = var1;
      this.sourceX = var2;
      this.sourceY = var3;
      return this.map.getCost(this, var4, var5);
   }

   protected void addToOpen(AStarPathFinder.Node var1) {
      var1.setOpen(true);
      this.open.add(var1);
   }

   protected void removeFromOpen(AStarPathFinder.Node var1) {
      var1.setOpen(false);
      this.open.remove(var1);
   }

   public AStarPathFinder(TileBasedMap var1, int var2, boolean var3) {
      this(var1, var2, var3, new ClosestHeuristic());
   }

   public Mover getMover() {
      return this.mover;
   }

   protected boolean inOpenList(AStarPathFinder.Node var1) {
      return var1.isOpen();
   }

   protected void removeFromClosed(AStarPathFinder.Node var1) {
      var1.setClosed(false);
      this.closed.remove(var1);
   }

   public int getSearchDistance() {
      return this.distance;
   }

   private class Node implements Comparable {
      // $FF: synthetic field
      private float cost;
      // $FF: synthetic field
      private boolean closed;
      // $FF: synthetic field
      private int y;
      // $FF: synthetic field
      private AStarPathFinder.Node parent;
      // $FF: synthetic field
      private int depth;
      // $FF: synthetic field
      private int x;
      // $FF: synthetic field
      private boolean open;
      // $FF: synthetic field
      private float heuristic;

      public int compareTo(Object var1) {
         AStarPathFinder.Node var2 = (AStarPathFinder.Node)var1;
         float var3 = this.heuristic + this.cost;
         float var4 = var2.heuristic + var2.cost;
         if (var3 < var4) {
            return -1;
         } else {
            return var3 > var4 ? 1 : 0;
         }
      }

      public void setOpen(boolean var1) {
         this.open = var1;
      }

      public int setParent(AStarPathFinder.Node var1) {
         this.depth = var1.depth + 1;
         this.parent = var1;
         return this.depth;
      }

      public boolean isOpen() {
         return this.open;
      }

      public Node(int var2, int var3) {
         this.x = var2;
         this.y = var3;
      }

      public void setClosed(boolean var1) {
         this.closed = var1;
      }

      public boolean isClosed() {
         return this.closed;
      }

      public String toString() {
         return String.valueOf((new StringBuilder()).append("[Node ").append(this.x).append(",").append(this.y).append("]"));
      }

      public void reset() {
         this.closed = false;
         this.open = false;
         this.cost = 0.0F;
         this.depth = 0;
      }
   }

   private class PriorityList {
      // $FF: synthetic field
      private List list;

      public void clear() {
         this.list.clear();
      }

      public int size() {
         return this.list.size();
      }

      // $FF: synthetic method
      PriorityList(Object var2) {
         this();
      }

      public Object first() {
         return this.list.get(0);
      }

      public void add(Object var1) {
         for(int var2 = 0; var2 < this.list.size(); ++var2) {
            if (((Comparable)this.list.get(var2)).compareTo(var1) > 0) {
               this.list.add(var2, var1);
               break;
            }
         }

         if (!this.list.contains(var1)) {
            this.list.add(var1);
         }

      }

      public boolean contains(Object var1) {
         return this.list.contains(var1);
      }

      private PriorityList() {
         this.list = new LinkedList();
      }

      public void remove(Object var1) {
         this.list.remove(var1);
      }

      public String toString() {
         String var1 = "{";

         for(int var2 = 0; var2 < this.size(); ++var2) {
            var1 = String.valueOf((new StringBuilder()).append(var1).append(this.list.get(var2).toString()).append(","));
         }

         var1 = String.valueOf((new StringBuilder()).append(var1).append("}"));
         return var1;
      }
   }
}
