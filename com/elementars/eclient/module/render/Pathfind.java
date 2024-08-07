package com.elementars.eclient.module.render;

import com.elementars.eclient.command.Command;
import com.elementars.eclient.event.events.RenderEvent;
import com.elementars.eclient.module.Category;
import com.elementars.eclient.module.Module;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.BlockFence;
import net.minecraft.block.BlockFenceGate;
import net.minecraft.block.BlockRailBase;
import net.minecraft.block.BlockWall;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.init.Blocks;
import net.minecraft.pathfinding.Path;
import net.minecraft.pathfinding.PathFinder;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.pathfinding.PathPoint;
import net.minecraft.pathfinding.WalkNodeProcessor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import org.lwjgl.opengl.GL11;

public class Pathfind extends Module {
   // $FF: synthetic field
   public static ArrayList points = new ArrayList();
   // $FF: synthetic field
   static PathPoint to = null;

   public Pathfind() {
      super("Pathfind", "Finds a path lol", 0, Category.RENDER, true);
   }

   public void onUpdate() {
      PathPoint var1 = (PathPoint)points.stream().min(Comparator.comparing((var0) -> {
         return mc.player.getDistance((double)var0.x, (double)var0.y, (double)var0.z);
      })).orElse((Object)null);
      if (var1 != null) {
         if (!(mc.player.getDistance((double)var1.x, (double)var1.y, (double)var1.z) > 0.8D)) {
            Iterator var2 = points.iterator();

            while(var2.hasNext()) {
               if (var2.next() == var1) {
                  var2.remove();
                  break;
               }

               var2.remove();
            }

            if (points.size() <= 1 && to != null) {
               boolean var3 = createPath(to);
               boolean var4 = points.size() <= 4;
               if (var3 && var4 || var4) {
                  points.clear();
                  to = null;
                  if (var3) {
                     Command.sendChatMessage("Arrived!");
                  } else {
                     Command.sendChatMessage("Can't go on: pathfinder has hit dead end");
                  }
               }
            }

         }
      }
   }

   public static boolean createPath(PathPoint var0) {
      to = var0;
      Pathfind.AnchoredWalkNodeProcessor var1 = new Pathfind.AnchoredWalkNodeProcessor(new PathPoint((int)mc.player.posX, (int)mc.player.posY, (int)mc.player.posZ));
      EntityZombie var2 = new EntityZombie(mc.world);
      var2.setPathPriority(PathNodeType.WATER, 16.0F);
      var2.posX = mc.player.posX;
      var2.posY = mc.player.posY;
      var2.posZ = mc.player.posZ;
      PathFinder var3 = new PathFinder(var1);
      Path var4 = var3.findPath(mc.world, var2, new BlockPos(var0.x, var0.y, var0.z), Float.MAX_VALUE);
      var2.setPathPriority(PathNodeType.WATER, 0.0F);
      if (var4 == null) {
         Command.sendChatMessage("Failed to create path!");
         return false;
      } else {
         points = new ArrayList(Arrays.asList(var4.points));
         return ((PathPoint)points.get(points.size() - 1)).distanceTo(var0) <= 1.0F;
      }
   }

   public void onWorldRender(RenderEvent var1) {
      if (!points.isEmpty()) {
         GL11.glDisable(3042);
         GL11.glDisable(3553);
         GL11.glDisable(2896);
         GL11.glLineWidth(1.5F);
         GL11.glColor3f(1.0F, 1.0F, 1.0F);
         GlStateManager.disableDepth();
         GL11.glBegin(1);
         PathPoint var2 = (PathPoint)points.get(0);
         GL11.glVertex3d((double)var2.x - mc.getRenderManager().renderPosX + 0.5D, (double)var2.y - mc.getRenderManager().renderPosY, (double)var2.z - mc.getRenderManager().renderPosZ + 0.5D);

         for(int var3 = 0; var3 < points.size() - 1; ++var3) {
            PathPoint var4 = (PathPoint)points.get(var3);
            GL11.glVertex3d((double)var4.x - mc.getRenderManager().renderPosX + 0.5D, (double)var4.y - mc.getRenderManager().renderPosY, (double)var4.z - mc.getRenderManager().renderPosZ + 0.5D);
            if (var3 != points.size() - 1) {
               GL11.glVertex3d((double)var4.x - mc.getRenderManager().renderPosX + 0.5D, (double)var4.y - mc.getRenderManager().renderPosY, (double)var4.z - mc.getRenderManager().renderPosZ + 0.5D);
            }
         }

         GL11.glEnd();
         GlStateManager.enableDepth();
      }
   }

   private static class AnchoredWalkNodeProcessor extends WalkNodeProcessor {
      // $FF: synthetic field
      PathPoint from;

      public PathPoint getStart() {
         return this.from;
      }

      public PathNodeType getPathNodeType(IBlockAccess var1, int var2, int var3, int var4) {
         PathNodeType var5 = this.getPathNodeTypeRaw(var1, var2, var3, var4);
         if (var5 == PathNodeType.OPEN && var3 >= 1) {
            Block var6 = var1.getBlockState(new BlockPos(var2, var3 - 1, var4)).getBlock();
            PathNodeType var7 = this.getPathNodeTypeRaw(var1, var2, var3 - 1, var4);
            var5 = var7 != PathNodeType.WALKABLE && var7 != PathNodeType.OPEN && var7 != PathNodeType.LAVA ? PathNodeType.WALKABLE : PathNodeType.OPEN;
            if (var7 == PathNodeType.DAMAGE_FIRE || var6 == Blocks.MAGMA) {
               var5 = PathNodeType.DAMAGE_FIRE;
            }

            if (var7 == PathNodeType.DAMAGE_CACTUS) {
               var5 = PathNodeType.DAMAGE_CACTUS;
            }
         }

         var5 = this.checkNeighborBlocks(var1, var2, var3, var4, var5);
         return var5;
      }

      public AnchoredWalkNodeProcessor(PathPoint var1) {
         this.from = var1;
      }

      public boolean getCanEnterDoors() {
         return true;
      }

      public boolean getCanSwim() {
         return true;
      }

      protected PathNodeType getPathNodeTypeRaw(IBlockAccess var1, int var2, int var3, int var4) {
         BlockPos var5 = new BlockPos(var2, var3, var4);
         IBlockState var6 = var1.getBlockState(var5);
         Block var7 = var6.getBlock();
         Material var8 = var6.getMaterial();
         PathNodeType var9 = var7.getAiPathNodeType(var6, var1, var5);
         if (var9 != null) {
            return var9;
         } else if (var8 == Material.AIR) {
            return PathNodeType.OPEN;
         } else if (var7 != Blocks.TRAPDOOR && var7 != Blocks.IRON_TRAPDOOR && var7 != Blocks.WATERLILY) {
            if (var7 == Blocks.FIRE) {
               return PathNodeType.DAMAGE_FIRE;
            } else if (var7 == Blocks.CACTUS) {
               return PathNodeType.DAMAGE_CACTUS;
            } else if (var7 instanceof BlockDoor && var8 == Material.WOOD && !(Boolean)var6.getValue(BlockDoor.OPEN)) {
               return PathNodeType.DOOR_WOOD_CLOSED;
            } else if (var7 instanceof BlockDoor && var8 == Material.IRON && !(Boolean)var6.getValue(BlockDoor.OPEN)) {
               return PathNodeType.DOOR_IRON_CLOSED;
            } else if (var7 instanceof BlockDoor && (Boolean)var6.getValue(BlockDoor.OPEN)) {
               return PathNodeType.DOOR_OPEN;
            } else if (var7 instanceof BlockRailBase) {
               return PathNodeType.RAIL;
            } else if (!(var7 instanceof BlockFence) && !(var7 instanceof BlockWall) && (!(var7 instanceof BlockFenceGate) || (Boolean)var6.getValue(BlockFenceGate.OPEN))) {
               if (var8 == Material.WATER) {
                  return PathNodeType.WALKABLE;
               } else if (var8 == Material.LAVA) {
                  return PathNodeType.LAVA;
               } else {
                  return var7.isPassable(var1, var5) ? PathNodeType.OPEN : PathNodeType.BLOCKED;
               }
            } else {
               return PathNodeType.FENCE;
            }
         } else {
            return PathNodeType.TRAPDOOR;
         }
      }
   }
}
