package com.elementars.eclient.util;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.URL;
import java.net.URLConnection;
import java.util.Calendar;
import java.util.LinkedHashMap;

public class PlayerIdentity implements Serializable {
   // $FF: synthetic field
   private String stringUuid;
   // $FF: synthetic field
   private String displayName;
   // $FF: synthetic field
   private Long lastUpdated;

   public String getDisplayName() {
      return this.displayName;
   }

   public boolean shouldUpdate() {
      return (double)(System.currentTimeMillis() - this.lastUpdated) >= 6.048E8D;
   }

   private static String getName(String var0) {
      LinkedHashMap var1 = new LinkedHashMap();

      try {
         URL var2 = new URL(String.valueOf((new StringBuilder()).append("https://api.mojang.com/user/profiles/").append(var0.replace("-", "")).append("/names")));
         URLConnection var3 = var2.openConnection();
         BufferedReader var4 = new BufferedReader(new InputStreamReader(var3.getInputStream()));
         StringBuilder var5 = new StringBuilder();

         String var6;
         while((var6 = var4.readLine()) != null) {
            var5.append(String.valueOf((new StringBuilder()).append(var6).append("\n")));
         }

         String var7 = String.valueOf(var5);
         var4.close();
         JsonArray var8 = (new JsonParser()).parse(var7).getAsJsonArray();
         JsonObject var9 = var8.get(var8.size() - 1).getAsJsonObject();
         String var10 = var9.get("name").getAsString();

         try {
            var9.get("changedToAt");
            Calendar var11 = Calendar.getInstance();
            var11.setTimeInMillis(var9.get("changedToAt").getAsLong());
            long var12 = var9.get("changedToAt").getAsLong();
            int var14 = var11.get(1);
            int var15 = var11.get(2);
            int var16 = var11.get(5);
            var1.put(var10, var12);
            return var10;
         } catch (Exception var18) {
            return var10;
         }
      } catch (Exception var19) {
         var19.printStackTrace();
         System.out.print("fuck");
         return var0;
      }
   }

   public PlayerIdentity(String var1) {
      String var2 = var1.replace("-", "");
      this.stringUuid = var1;
      this.displayName = "Loading...";
      (new Thread(() -> {
         this.displayName = getName(var2);
         DataManager.identityCacheMap.put(this.getStringUuid(), this);

         try {
            DataManager.savePlayerIdentity(this, false);
         } catch (IOException var3) {
            var3.printStackTrace();
         }

      })).start();
      this.lastUpdated = System.currentTimeMillis();
   }

   public void updateDisplayName() {
      (new Thread(() -> {
         PlayerIdentity var1 = new PlayerIdentity(this.stringUuid);
         this.displayName = var1.getDisplayName();
         this.lastUpdated = System.currentTimeMillis();
         var1 = null;
      })).start();
   }

   public String getStringUuid() {
      return this.stringUuid;
   }

   private static String getDateFormat(int var0, int var1, int var2) {
      return String.valueOf((new StringBuilder()).append(var0).append("/").append(var1).append("/").append(var2));
   }
}
