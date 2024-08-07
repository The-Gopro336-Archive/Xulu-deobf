package com.elementars.eclient.command.commands;

import com.elementars.eclient.command.Command;
import com.elementars.eclient.module.render.Waypoints;
import java.util.UUID;
import net.minecraft.util.math.BlockPos;

public class WaypointCommand extends Command {
   public WaypointCommand() {
      super("waypoints", "Manages Waypoints", new String[]{"add", "remove"});
   }

   public void execute(String[] var1) {
      if (var1.length < 2) {
         sendChatMessage("Use .waypoints help to see commands");
      } else if (var1[1].equalsIgnoreCase("add")) {
         if (var1.length < 3) {
            sendChatMessage("Please specify the name of the waypoint (.waypoints add name (X) (Y) (Z))");
         } else if (var1.length < 6) {
            sendChatMessage("Please specify coordinates (.waypoints add name (X) (Y) (Z))");
         } else {
            int var2 = (int)Double.parseDouble(var1[3]);
            int var3 = (int)Double.parseDouble(var1[4]);
            int var4 = (int)Double.parseDouble(var1[5]);
            Waypoints.WAYPOINTS.add(new Waypoints.Waypoint(UUID.randomUUID(), var1[2], new BlockPos(var2, var3, var4), mc.player.getEntityBoundingBox(), mc.player.dimension));
            sendChatMessage(String.valueOf((new StringBuilder()).append("Added waypoint with the name: ").append(var1[2])));
         }
      } else {
         if (var1[1].equalsIgnoreCase("remove")) {
            if (var1.length < 3) {
               sendChatMessage("Please specify the name of the waypoint (.waypoints remove name)");
               return;
            }

            Waypoints.WAYPOINTS.removeIf((var1x) -> {
               return var1x.getName().equalsIgnoreCase(var1[2]);
            });
            sendChatMessage(String.valueOf((new StringBuilder()).append("Removed waypoint(s) with the name: ").append(var1[2])));
         }

      }
   }
}
