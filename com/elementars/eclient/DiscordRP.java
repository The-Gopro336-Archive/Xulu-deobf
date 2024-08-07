package com.elementars.eclient;

import club.minnced.discord.rpc.DiscordEventHandlers;
import club.minnced.discord.rpc.DiscordRPC;
import club.minnced.discord.rpc.DiscordRichPresence;
import net.minecraft.client.Minecraft;

public class DiscordRP {
   // $FF: synthetic field
   private boolean running = true;
   // $FF: synthetic field
   private long created = 0L;

   public void shutdown() {
      DiscordRPC var1 = DiscordRPC.INSTANCE;
      var1.Discord_Shutdown();
   }

   public void start() {
      Minecraft var1 = Minecraft.getMinecraft();
      DiscordRPC var3 = DiscordRPC.INSTANCE;
      String var4 = "671154973274275850";
      String var5 = "";
      DiscordEventHandlers var6 = new DiscordEventHandlers();
      var6.ready = (var0) -> {
         System.out.println("Ready!");
      };
      var3.Discord_Initialize(var4, var6, true, var5);
      DiscordRichPresence var7 = new DiscordRichPresence();
      var7.startTimestamp = System.currentTimeMillis() / 1000L;
      var7.details = "Playing epicly";
      var7.state = "lol";
      var7.largeImageKey = "xulu2";
      var3.Discord_UpdatePresence(var7);
      (new Thread(() -> {
         while(!Thread.currentThread().isInterrupted()) {
            var3.Discord_RunCallbacks();

            try {
               var7.largeImageKey = "xulurevamp3";
               var7.largeImageText = "Xulu v1.5.2";
               if (var1.isIntegratedServerRunning()) {
                  var7.details = "Singleplayer";
                  var7.state = "In Game";
               } else if (var1.getCurrentServerData() != null) {
                  if (!var1.getCurrentServerData().serverIP.equals(var7.state)) {
                     var7.details = "Playing a server";
                     var7.state = var1.getCurrentServerData().serverIP;
                  }
               } else {
                  var7.details = "Menu";
                  var7.state = "Idle";
               }

               var3.Discord_UpdatePresence(var7);
            } catch (Exception var5) {
               var5.printStackTrace();
            }

            try {
               Thread.sleep(2000L);
            } catch (InterruptedException var4) {
            }
         }

      }, "RPC-Callback-Handler")).start();
   }
}
