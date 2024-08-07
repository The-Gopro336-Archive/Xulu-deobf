package com.elementars.eclient.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class DataManager {
   // $FF: synthetic field
   public static LinkedHashMap identityCacheMap = new LinkedHashMap();
   // $FF: synthetic field
   private static final Lock identityLock = new ReentrantLock();
   // $FF: synthetic field
   private final Lock threadLock = new ReentrantLock();
   // $FF: synthetic field
   private final Lock waypointLock = new ReentrantLock();

   public static PlayerIdentity getPlayerIdentity(String var0) {
      return identityCacheMap.containsKey(var0) ? (PlayerIdentity)identityCacheMap.get(var0) : new PlayerIdentity(var0);
   }

   public static synchronized void savePlayerIdentity(PlayerIdentity var0, boolean var1) throws IOException {
      identityLock.lock();

      try {
         File var2 = new File("playeridentitycache");
         if (!var2.exists()) {
            var2.mkdir();
         }

         File var3 = new File(String.valueOf((new StringBuilder()).append("playeridentitycache/").append(var0.getStringUuid()).append(".mcid")));
         if (var3.exists() || var1) {
            var3.delete();
            if (var1) {
               return;
            }
         }

         FileOutputStream var4 = new FileOutputStream(var3);
         ObjectOutputStream var5 = new ObjectOutputStream(var4);
         var5.writeObject(var0);
         var5.close();
         var4.close();
      } finally {
         identityLock.unlock();
      }
   }

   public synchronized void loadPlayerIdentities() throws IOException {
      identityLock.lock();

      try {
         File var1 = new File("playeridentitycache");
         if (!var1.exists()) {
            return;
         }

         if (!var1.isDirectory()) {
            var1.delete();
            return;
         }

         List var2 = Arrays.asList(var1.listFiles());
         var2.stream().filter((var0) -> {
            return var0.getName().endsWith(".mcid");
         }).forEach((var0) -> {
            try {
               FileInputStream var1 = new FileInputStream(var0);
               ObjectInputStream var2 = new ObjectInputStream(var1);
               Object var3 = var2.readObject();
               if (var3 instanceof PlayerIdentity) {
                  identityCacheMap.put(((PlayerIdentity)var3).getStringUuid(), (PlayerIdentity)var3);
                  var2.close();
                  var1.close();
               } else {
                  var2.close();
                  var1.close();
               }
            } catch (ClassNotFoundException | IOException var5) {
               var5.printStackTrace();
            }
         });
      } finally {
         identityLock.unlock();
      }

   }
}
