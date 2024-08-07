package com.elementars.eclient.module.player;

import com.elementars.eclient.event.EventTarget;
import com.elementars.eclient.event.events.EventReceivePacket;
import com.elementars.eclient.module.Category;
import com.elementars.eclient.module.Module;
import dev.xulu.settings.Value;
import java.util.HashSet;
import java.util.Set;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.server.SPacketDisconnect;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent.ClientConnectedToServerEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent.ClientDisconnectionFromServerEvent;

public class AntiVoid extends Module {
   // $FF: synthetic field
   private boolean wasElytraFlying;
   // $FF: synthetic field
   private final Value y_level = this.register(new Value("Y Level", this, 100, 0, 256));
   // $FF: synthetic field
   private final Value mode = this.register(new Value("Mode", this, "Normal", new String[]{"AFK Elytra", "Normal"}));
   // $FF: synthetic field
   public Set ipList = new HashSet();
   // $FF: synthetic field
   public static AntiVoid INSTANCE;
   // $FF: synthetic field
   int delay;

   public void onEnable() {
      FMLCommonHandler.instance().bus().register(this);
   }

   private boolean isOverVoid() {
      return mc.world.getBlockState(new BlockPos((int)mc.player.posX, 0, (int)mc.player.posZ)).getBlock() == Blocks.AIR;
   }

   @EventTarget
   public void onPacket(EventReceivePacket var1) {
      if (var1.getPacket() instanceof SPacketDisconnect && mc.getCurrentServerData() != null && mc.player != null) {
         this.ipList.add(mc.getCurrentServerData().serverIP);
      }

   }

   public AntiVoid() {
      super("AntiVoid", "Prevents death from afk flying", 0, Category.PLAYER, true);
      INSTANCE = this;
   }

   public void onUpdate() {
      if (mc.player != null && mc.world != null) {
         if (((String)this.mode.getValue()).equalsIgnoreCase("Normal")) {
            boolean var1 = true;

            for(int var2 = (int)mc.player.posY; var2 > -1; --var2) {
               if (mc.world.getBlockState(new BlockPos(mc.player.posX, (double)var2, mc.player.posZ)).getBlock() != Blocks.AIR) {
                  var1 = false;
                  break;
               }
            }

            if (mc.player.posY < (double)(Integer)this.y_level.getValue() && var1) {
               mc.player.motionY = 0.0D;
            }
         } else if (mc.player.isElytraFlying()) {
            this.wasElytraFlying = true;
         } else if (mc.player.posY < (double)(Integer)this.y_level.getValue() && this.wasElytraFlying && this.isOverVoid()) {
            mc.world.sendQuittingDisconnectingPacket();
            this.wasElytraFlying = false;
         }
      }

   }

   public void onDisable() {
      FMLCommonHandler.instance().bus().unregister(this);
   }

   @SubscribeEvent
   public void onConnect(ClientConnectedToServerEvent var1) {
      if (mc.getCurrentServerData() != null) {
         this.ipList.remove(mc.getCurrentServerData().serverIP);
      }

   }

   @SubscribeEvent
   public void onDisconnect(ClientDisconnectionFromServerEvent var1) {
      if (mc.getCurrentServerData() != null && mc.player != null) {
         this.ipList.add(mc.getCurrentServerData().serverIP);
      }

   }
}
