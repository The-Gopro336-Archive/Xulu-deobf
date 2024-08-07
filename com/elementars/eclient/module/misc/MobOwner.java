package com.elementars.eclient.module.misc;

import com.elementars.eclient.command.Command;
import com.elementars.eclient.module.Category;
import com.elementars.eclient.module.Module;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.UUID;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.AbstractHorse;

public class MobOwner extends Module {
   // $FF: synthetic field
   private Map uuidToName = new HashMap();
   // $FF: synthetic field
   private List mobs = new ArrayList();

   private static String requestName(String var0) {
      try {
         String var1 = String.valueOf((new StringBuilder()).append("https://api.mojang.com/user/profiles/").append(var0).append("/names"));
         URL var2 = new URL(var1);
         HttpURLConnection var3 = (HttpURLConnection)var2.openConnection();
         var3.setConnectTimeout(5000);
         var3.setRequestMethod("GET");
         BufferedInputStream var4 = new BufferedInputStream(var3.getInputStream());
         String var5 = convertStreamToString(var4);
         var4.close();
         var3.disconnect();
         return var5;
      } catch (Exception var6) {
         var6.printStackTrace();
         return null;
      }
   }

   public void onUpdate() {
      if (mc.world != null) {
         Iterator var1 = mc.world.loadedEntityList.iterator();

         while(true) {
            while(true) {
               AbstractHorse var3;
               do {
                  Entity var2;
                  do {
                     if (!var1.hasNext()) {
                        return;
                     }

                     var2 = (Entity)var1.next();
                  } while(!(var2 instanceof AbstractHorse));

                  var3 = (AbstractHorse)var2;
               } while(this.mobs.contains(var3));

               this.mobs.add(var3);
               UUID var4 = var3.getOwnerUniqueId();
               if (var4 == null) {
                  var3.setCustomNameTag("Not tamed");
               } else {
                  String var5 = var4.toString().replace("-", "");
                  String var6 = "";
                  if (this.uuidToName.get(var6) != null) {
                     var6 = (String)this.uuidToName.get(var6);
                  } else {
                     try {
                        String var7 = requestName(var5);
                        JsonElement var8 = (new JsonParser()).parse(var7);
                        JsonArray var9 = var8.getAsJsonArray();
                        if (var9.size() == 0) {
                           Command.sendChatMessage("Couldn't find player name. (1)");
                           continue;
                        }

                        var6 = var9.get(var9.size() - 1).getAsJsonObject().get("name").getAsString();
                        this.uuidToName.put(var5, var6);
                     } catch (Exception var10) {
                        var10.printStackTrace();
                        Command.sendChatMessage("Couldn't find player name. (2)");
                        continue;
                     }
                  }

                  var3.setCustomNameTag(String.valueOf((new StringBuilder()).append("Owner: ").append(var6)));
               }
            }
         }
      }
   }

   public MobOwner() {
      super("MobOwner", "Displays the owners of tamed mobs", 0, Category.MISC, true);
   }

   private static String convertStreamToString(InputStream var0) {
      Scanner var1 = (new Scanner(var0)).useDelimiter("\\A");
      String var2 = var1.hasNext() ? var1.next() : "/";
      return var2;
   }
}
