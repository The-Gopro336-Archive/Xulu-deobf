package com.elementars.eclient.friend;

import java.util.HashMap;
import java.util.Map;

public class Nicknames {
   // $FF: synthetic field
   private static Map aliases = new HashMap();

   public static void removeNickname(String var0) {
      aliases.remove(var0);
   }

   public static void addNickname(String var0, String var1) {
      aliases.put(var0, var1);
   }

   public static boolean hasNickname(String var0) {
      return aliases.containsKey(var0);
   }

   public static Map getAliases() {
      return aliases;
   }

   public static String getNickname(String var0) {
      return (String)aliases.get(var0);
   }
}
