package com.elementars.eclient.module.player;

import com.elementars.eclient.Xulu;
import com.elementars.eclient.event.EventTarget;
import com.elementars.eclient.event.events.EventReceivePacket;
import com.elementars.eclient.module.Category;
import com.elementars.eclient.module.Module;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.network.play.server.SPacketDisconnect;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent.ClientDisconnectionFromServerEvent;

public class SelfLogoutSpot extends Module {
   // $FF: synthetic field
   public static SelfLogoutSpot INSTANCE;
   // $FF: synthetic field
   public ConcurrentHashMap logoutMap = new ConcurrentHashMap();

   public void onEnable() {
      FMLCommonHandler.instance().bus().register(this);
   }

   @SubscribeEvent
   public void onDisconnect(ClientDisconnectionFromServerEvent var1) {
      if (mc.getCurrentServerData() != null && mc.player != null) {
         this.logoutMap.put(mc.getCurrentServerData().serverIP, String.valueOf((new StringBuilder()).append("X: ").append(Xulu.df.format(mc.player.posX)).append(", Y: ").append(Xulu.df.format(mc.player.posY)).append(", Z: ").append(Xulu.df.format(mc.player.posZ))));
      }

   }

   public SelfLogoutSpot() {
      super("SelfLogoutSpot", "Saves your logout spot in case you forget", 0, Category.PLAYER, true);
      INSTANCE = this;
   }

   @EventTarget
   public void onPacket(EventReceivePacket var1) {
      if (var1.getPacket() instanceof SPacketDisconnect && mc.getCurrentServerData() != null && mc.player != null) {
         this.logoutMap.put(mc.getCurrentServerData().serverIP, String.valueOf((new StringBuilder()).append("X: ").append(Xulu.df.format(mc.player.posX)).append(", Y: ").append(Xulu.df.format(mc.player.posY)).append(", Z: ").append(Xulu.df.format(mc.player.posZ))));
      }

   }

   public void onDisable() {
      FMLCommonHandler.instance().bus().unregister(this);
   }
}
